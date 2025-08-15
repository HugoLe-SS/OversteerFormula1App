// File: supabase/functions/sendRaceReminder/index.ts

import { serve } from "https://deno.land/std@0.168.0/http/server.ts";
import { createClient } from "https://esm.sh/@supabase/supabase-js@2";
import serviceAccount from "../service-account.json" with { type: "json" };
import { JWT } from "https://esm.sh/google-auth-library@9.0.0";

const SUPABASE_URL = Deno.env.get("SUPABASE_URL")!;
const SUPABASE_SERVICE_ROLE_KEY = Deno.env.get("SUPABASE_SERVICE_ROLE_KEY")!;
const supabase = createClient(SUPABASE_URL, SUPABASE_SERVICE_ROLE_KEY);

const TIME_WINDOW_START_MINUTES = 115;
const TIME_WINDOW_END_MINUTES = 125;

function log(message: string, data?: unknown) {
  console.log(`[LOG]: ${message}`);
  if (data) console.log(data);
}

function getAccessToken(): Promise<string> {
  return new Promise((resolve, reject) => {
    const jwtClient = new JWT({
      email: serviceAccount.client_email,
      key: serviceAccount.private_key,
      scopes: ["https://www.googleapis.com/auth/firebase.messaging"],
    });

    jwtClient.authorize((err, tokens) => {
      if (err || !tokens?.access_token) {
        reject(err || new Error("No access token"));
        return;
      }
      resolve(tokens.access_token);
    });
  });
}

async function getUpcomingRaces() {
  const now = new Date();
  const startTime = new Date(now.getTime() + TIME_WINDOW_START_MINUTES * 60000).toISOString();
  const endTime = new Date(now.getTime() + TIME_WINDOW_END_MINUTES * 60000).toISOString();

  const { data, error } = await supabase
    .from("races")
    .select("id, name")
    .gte("start_time", startTime)
    .lte("start_time", endTime)
    .eq("reminder_sent", false);

  if (error) throw new Error(`Supabase error: ${error.message}`);
  return data || [];
}

async function getValidFcmTokens() {
  const { data, error } = await supabase.from("fcm_tokens").select("token");
  if (error) throw new Error(`Token fetch error: ${error.message}`);
  return (data || []).map((row) => row.token);
}

async function sendReminderFCM(race: { id: string; name: string }, tokens: string[], accessToken: string) {
  const fcmUrl = `https://fcm.googleapis.com/v1/projects/${serviceAccount.project_id}/messages:send`;

  const results: string[] = [];

  for (const token of tokens) {
    const message = {
      message: {
        token,
        notification: {
          title: "Race Reminder",
          body: `Get ready! ${race.name} is starting soon!`,
        },
        data: {
          type: "race_reminder",
          raceId: race.id,
        },
      },
    };

    const res = await fetch(fcmUrl, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(message),
    });

    if (!res.ok) {
      console.warn(`Failed to send to token: ${token}`);
      results.push(token);
    }
  }

  return results;
}

async function markReminderSent(raceId: string) {
  await supabase.from("races").update({ reminder_sent: true }).eq("id", raceId);
}

async function cleanInvalidTokens(tokensToDelete: string[]) {
  if (tokensToDelete.length === 0) return;
  await supabase.from("fcm_tokens").delete().in("token", tokensToDelete);
}

// Serve Edge Function
serve(async () => {
  try {
    const races = await getUpcomingRaces();
    if (races.length === 0) return new Response("No races found", { status: 200 });

    const tokens = await getValidFcmTokens();
    if (tokens.length === 0) return new Response("No tokens found", { status: 200 });

    const accessToken = await getAccessToken();

    for (const race of races) {
      const invalidTokens = await sendReminderFCM(race, tokens, accessToken);
      await markReminderSent(race.id);
      await cleanInvalidTokens(invalidTokens);
    }

    return new Response("Reminders sent", { status: 200 });
  } catch (err) {
    console.error("[ERROR]:", err);
    return new Response("Internal Server Error", { status: 500 });
  }
});
