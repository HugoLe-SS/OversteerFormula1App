# 📣 Notifications API – Oversteer F1

## Prerequisites / Dependencies
Before using the Notifications API, ensure the following are set up:
- **Firebase Cloud Messaging**
    - `com.google.firebase:firebase-messaging`
    - Ensure FCM is enabled in your Firebase project.
- ⚠️⚠️⚠️  ️️️**Google Services Setup** – Don’t forget to add your `google-services.json` to the app module and configure required entries if necessary in `AndroidManifest.xml` (permissions, metadata, and FCM service).
- **Supabase**
    - `io.github.jan:supabase-auth`
    - `io.github.jan:supabase-postgrest`
- **Kotlin & Coroutines**
    - Kotlin 1.8+
    - Coroutines Flow for observing state
- **Jetpack DataStore**
    - Preferences DataStore for local caching of user session, tokens, and settings
- **Dagger Hilt**
    - For dependency injection of repositories and preferences

---

## Overview
This document describes how the Oversteer F1 app handles push notifications:
It includes:
- Fetching and syncing FCM tokens
- Managing notification settings per device
- Receiving and displaying notifications
- Supporting race-specific deep links
- Local caching of user preferences for offline consistency
- Optionally, displaying local notifications for foreground messages

---

## NotificationRepository
Handles FCM token syncing, remote and local notification preferences.

### Main Methods

| Method                                                                                              | Description                                                               |
|-----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| `syncFcmToken(): Result<Unit>`                                                                      | Fetch FCM token, save locally, and upsert to Supabase `DeviceToken` table |
| `updateNotificationSetting(setting: NotificationSettingType, isEnabled: Boolean): Result<Unit>`     | Update remote and local notification settings per type                    |

---

### NotificationSettingType

| Type            | Supabase Column        | Local DataStore Key                  |
|-----------------|------------------------|--------------------------------------|
| `RACE_STARTS`   | `notif_race_starts`    | `PreferenceKeys.NOTIF_RACE_STARTS`   |
| `ALL_SESSIONS`  | `notif_all_sessions`   | `PreferenceKeys.NOTIF_ALL_SESSIONS`  |
| `BREAKING_NEWS` | `notif_breaking_news`  | `PreferenceKeys.NOTIF_BREAKING_NEWS` |
| `GENERAL`       | `notif_general`        | `PreferenceKeys.NOTIF_GENERAL`       |

---

### Observables / Local Cache
UserPreferences provides flows for real-time updates:

| Property                                                 | Type                         | Description                                     |
|----------------------------------------------------------|------------------------------|-------------------------------------------------|
| `getFcmToken(): String?`                                 | `String?`                    | Retrieves cached FCM token                      |
| `notificationSettingsFlow: Flow<NotificationSettings>`   | `Flow<NotificationSettings>` | Observe remote + local notification preferences |

---

### Notification Channels
- Android 8+ requires **notification channels**.
- `NotificationUtils.createNotificationChannel(context, channelId)` ensures channel exists.
- Example channel: `RACE_REMINDER_CHANNEL_ID` with high priority and public visibility.

---

### Receiving Notifications
- **Service:** `MyFirebaseMessagingService` (`FirebaseMessagingService`)
- **Key methods:**
    - `onNewToken(token: String)` → triggers `syncFcmToken()`
    - `onMessageReceived(remoteMessage: RemoteMessage)` → handles incoming notifications
        - **Data payload:** custom app notifications
        - **Notification payload:** Firebase-handled messages
- **Notification building includes:**
    - Title & body
    - Optional deep-linking via `PendingIntent`
    - Default sound and auto-cancel
    - Unique IDs per race (`raceId.hashCode()`)
  
#### Example: Handling Incoming Notification
```kotlin
override fun onMessageReceived(remoteMessage: RemoteMessage) {
    val title = remoteMessage.data["title"] ?: "Default title"
    val body = remoteMessage.data["body"] ?: "Check for F1 updates!"
    val raceId = remoteMessage.data["raceId"]

    sendNotification(title, body, raceId)
}
```

---

### Local vs Push Notifications

| Type                              | When Used                              | Notes                                                                                                                  |
|-----------------------------------|----------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| **Local Notifications(Optional)** | Foreground messages                    | Use `sendNotification()` to show notifications manually with custom behavior, deep-linking, or custom icons/sounds.    |
| **Push Notifications (FCM)**      | Background or killed app               | Firebase automatically displays messages sent with `notification` payload. `Data` payloads may require local handling. |

---
## Automated Notifications: FCM + Supabase Edge Functions
Oversteer F1 uses a combination of **Supabase Edge Functions**, **FCM**, and **cron jobs** to send automated push notifications for race reminders and updates.

### Flow Overview
- **FCM Tokens Storage:** 
  - When a user signs in or updates notification preferences, their device FCM token is synced to the Supabase **DeviceToken** table.
  - Each token is associated with a user ID and flags for notification types (race start, breaking news, etc.).
- **Supabase Edge Function**
  - A serverless function that queries upcoming races and filters users who opted into notifications.
  - Generates notification payloads for each device token (title, body, optional deep-link, custom data).
  - Sends push notifications via Firebase Cloud Messaging API.
- **Scheduled Cron Job**
  - Supabase supports scheduled triggers to run Edge Functions automatically at defined intervals (e.g., 7 days, 1 day, 15 minutes before a race)

#### End-to-End Flow Diagram
```mermaid
    flowchart TD

    A[Cron Job] --> B[Edge Function]
    B --> C[Filter Users by Preferences]
    C --> D[Send FCM Notifications]
    D --> E[Device Receives Notification]
    E --> F[App Handles Payload (data / deep-link)]
```






