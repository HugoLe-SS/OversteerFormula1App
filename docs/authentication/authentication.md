# 🛡 Authentication API – Oversteer F1

## Prerequisites / Dependencies
Before using the authentication API, ensure the following dependencies and setup are in place:

- **Supabase SDK**
    - `io.github.jan:supabase-auth`
    - `io.github.jan:supabase-postgrest`
    - `io.github.jan:supabase-storage`
    - `io.github.jan:supabase-functions`
- **Google One Tap / Identity Services**
    - `com.google.android.libraries.identity:googleid`
    - Configure `WEB_CLIENT_ID` in your app (Google Cloud Console).
- ⚠️⚠️⚠️**Google Services Setup** – Don’t forget to add your `google-services.json` to the app module and configure required entries if necessary in `AndroidManifest.xml` (permissions, metadata, and FCM service).
- **Kotlin & Coroutines**
    - Kotlin 1.8+
    - Coroutines Flow for observing state
- **Jetpack DataStore**
    - Preferences DataStore for local caching of user session, tokens, and settings
- **Dagger Hilt**
    - For dependency injection of repositories and preferences

---

## Overview
This document describes authentication, user profile management, and local caching in the Oversteer F1 Android app. 
It includes:
- **Google Sign-In**, Supabase Auth integration ** for user authentication.
- **DataStore** for lightweight key-value storage (e.g., user preferences, tokens).

---

## GoogleAuthRepository
Handles Google Sign-In / Sign-Up, Supabase authentication, and user session management.

### Main Methods

| Method                                                               | Description                                            |
|----------------------------------------------------------------------|--------------------------------------------------------|
| `signInWithGoogle(activity: Activity):Result<GoogleSignInResult>`    | One-tap Google Sign-In and Sign-Up flow                |
| `signOut(): Result<Unit>`                                            | Sign out user from Supabase Auth and clear local cache |
| `isUserSignedIn(): Boolean`                                          | Check if user is signed in                             |
| `getCachedUser(): GoogleSignInResult?`                               | Retrieve cached user session                           |
| `observeAuthState(): Flow<Boolean>`                                  | Observe sign-in state                                  |
| `observeUserData(): Flow<GoogleSignInResult?>`                       | Observe cached user data                               |

---

### Internal Methods

| Method                                                     | Description                                       |
|------------------------------------------------------------|---------------------------------------------------|
| `attemptSignIn(activity: Activity)`                        | One-tap sign-in for returning users               |
| `attemptSignUp(activity: Activity)`                        | Sign-in + sign-up with account chooser            |
| `performGoogleAuth(googleIdOption,  rawNonce, activity)`   | Shared logic for Google authentication            |
| `handleGoogleCredential(result, rawNonce )`                | Converts Google credentials to GoogleSignInResult |
| `signInWithSupabaseAndSync(googleResult)`                  | Sign in to Supabase and sync profile & FCM token  |
| `hashNonce(raw: String)`                                   | SHA-256 hash for nonce                            |

---

## UserProfileRepository
Handles user profile retrieval, creation, updates, and avatar uploads.

### Main Methods

| Method                                                         | Description                                             |
|----------------------------------------------------------------|---------------------------------------------------------|
| `getOrCreateProfile(googleResult, userId): Result<ProfileDto>` | Fetch profile from Supabase or create if missing        |
| `syncUserProfile(user, supabaseUserId): Result<Unit>`          | Upsert user profile to Supabase                         |
| `updateUserProfile(profileUpdate): Result<GoogleSignInResult>` | Update profile fields and refresh local cache           |
| `uploadAvatar(uri: Uri): Result<String>`                       | Upload avatar to Supabase Storage and return public URL |
| `deleteAccount(): Result<Unit>`                                | Delete user account via Supabase Edge Function          |

---

## UserPreferences (Local Cache)
UserPreferences uses DataStore to cache user session, FCM token, notification settings, and theme preference.

### Observables

| Property                       | Type                          | Description                        |
|--------------------------------|-------------------------------|------------------------------------|
| **isUserSignedInFlow**         | `Flow<Boolean>`               | Observe if user is signed in       |
| **userDataFlow**               | `Flow<GoogleSignInResult?>`   | Observe cached user data           |
| **notificationSettingsFlow**   | `Flow<NotificationSettings>`  | Observe all notification settings  |
| **themePreferenceFlow**        | `Flow<ThemePreference>`       | Observe theme preference changes   |

---

### Methods

| Method                                                       | Description                           |
|--------------------------------------------------------------|---------------------------------------|
| `saveUser(signInResult: GoogleSignInResult)`                 | Save user session to local storage    |
| `getUserData(): GoogleSignInResult?`                         | Get cached user data once             |
| `isUserSignedIn(): Boolean`                                  | Get cached signed-in state            |
| `clearUser()`                                                | Clear user session from local storage |
| `updateBoolean(key, value)`                                  | Update any boolean preference         |
| `saveFcmToken(token: String)`                                | Save FCM token locally                |
| `getFcmToken(): String?`                                     | Retrieve cached FCM token             |
| `updateThemePreference(theme: ThemePreference)`              | Update theme preference               |

---

### Example Usage

#### Save user locally
```kotlin
userPreferences.saveUser(googleSignInResult)
```

#### Check if user is signed in
```kotlin
val signedIn = userPreferences.isUserSignedIn()
```

#### Observe user data
```kotlin
userPreferences.userDataFlow.collect { user ->
    println("Cached user: $user")
}
```

#### Clear user session
```kotlin
userPreferences.clearUser()
```

---

## Data Models and Mappers

### GoogleSignInResult
```kotlin
data class GoogleSignInResult(
    val idToken: String,
    val userId: String,
    val displayName: String?,
    val email: String,
    val profilePictureUrl: String?,
    val nonce: String?
)
```

### ProfileDto
```kotlin
@Serializable
data class ProfileDto(
    val id: String,
    val email: String?,
    val bio: String?,
    @SerialName("display_name") val displayName: String?,
    @SerialName("avatar_url") val avatarUrl: String?
)
```

### Mapper
```kotlin
fun ProfileDto.toGoogleSignInResult(idToken: String, email: String): GoogleSignInResult {
    return GoogleSignInResult(
        idToken = idToken,
        userId = this.id,
        displayName = this.displayName,
        email = email,
        profilePictureUrl = this.avatarUrl,
        nonce = null
    )
}
```

---

## Example Usage – Supabase API

### Fetch User Profile
```kotlin
val result = supabaseClient
    .postgrest["Profiles"]
    .select {
        filter {
            eq("id", userId)
        }
    }

val profile = result.decodeSingle<ProfileDto>()
```

### Update User Profile
```kotlin
val updatedProfile = postgrest.from("Profiles")
    .update(buildJsonObject {
        put("display_name", "New Name")
        put("avatar_url", "https://new.avatar/url.jpg")
    }) {
        filter {
            eq("id", userId)
        }
        select()
    }.decodeSingle<ProfileDto>()
```

### Upload Avatar & Fetch Public URL
```kotlin
val publicUrl = supabaseStorage.from("avatars").upload(
    path = "${user.id}/avatar.jpg",
    data = fileBytes
).let {
    supabaseStorage.from("avatars").publicUrl("${user.id}/avatar.jpg")
}
```

---

## Error Handling / Result Types
All major repository methods return Result<T> to handle success and failure in a consistent way.

### Common Error Types

| Repository / Method                               | Possible Failure Causes                                        |
|---------------------------------------------------|----------------------------------------------------------------|
| `signInWithGoogle`                                | Google One Tap canceled, network issues, Supabase auth failure |
| `signOut`                                         | Network issues during Supabase sign-out                        |
| `getOrCreateProfile`                              | Supabase PostgREST query fails, user does not exist            |
| `updateUserProfile`                               | Validation errors, network errors, Supabase update fails       |
| `uploadAvatar`                                    | File read failure, storage upload fails, network issues        |
| `deleteAccount`                                   | Edge Function fails, network issues                            |
| `UserPreferences` methods                         | DataStore read/write failure                                   |

---

### General Usage Pattern in ViewModel
```kotlin
val result: Result<GoogleSignInResult> = googleAuthRepository.signInWithGoogle(activity)

result.onSuccess { user ->
    println("Signed in user: $user")
}.onFailure { e ->
    Log.e("AuthError", "Sign-in failed", e)
}
```