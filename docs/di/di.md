# 🧩 Dependency Injection – Oversteer F1

## Prerequisites / Dependencies
- **Dagger Hilt**
    - `implementation("com.google.dagger:hilt-android:2.56.1")`
    - `kapt("com.google.dagger:hilt-compiler:2.56.1")`
- **Hilt Navigation Compose** (for navigation + Compose integration)
    - `implementation("androidx.hilt:hilt-navigation-compose:1.2.0")`

---

## Overview
Oversteer F1 uses **Dagger Hilt** for dependency injection.
Hilt simplifies DI by providing:
- **Automatic integration with Jetpack** (ViewModel, Navigation, Compose).
- **Scoped dependencies** (Application, Activity, ViewModel).
- **Separation of concerns** (modules for network, repositories, Supabase, etc.)..

---

## Why Dependency Injection?
Oversteer F1 leverages **Dagger Hilt** to manage dependencies efficiently. The main benefits include:


| Benefit                                 | Description                                                                                         |
|-----------------------------------------|-----------------------------------------------------------------------------------------------------|
| **Testability**                         | Dependencies can be replaced with mocks or fakes for unit and integration tests.                    |
| **Loose Coupling**                      | Classes depend on abstractions (interfaces) instead of concrete implementations.                    |
| **Reusability**                         | Shared dependencies (Retrofit, SupabaseClient, etc.) are reused across the app without duplication. | 
| **Lifecycle Awareness / Scoping**       | Hilt scopes objects to Singleton, Activity, Fragment, or ViewModel lifecycles automatically.        |
| **Separation of Concerns**              | Object creation is moved outside of the class, allowing classes to focus on their main logic.       | 
| **Consistency / Boilerplate Reduction** | Hilt generates DI code, reducing manual factory or provider boilerplate.                            |


## Hilt Annotations in Use

| Annotation                              | Purpose                                                          |
|-----------------------------------------|------------------------------------------------------------------|
| `@HiltAndroidApp`                       | Placed on **Application** class to trigger Hilt code generation. |
| `@AndroidEntryPoint`                    | Marks Activities/Fragments/Composables for injection.            |
| `@HiltViewModel`                        | For injecting dependencies into ViewModels.                      | 
| `@Module`                               | Declares a Hilt module (container for providers/bindings).       |
| `@InstallIn(SingletonComponent::class)` | Specifies lifetime scope (singleton across app).                 | 
| `@Provides`                             | Supplies concrete instances (e.g., Retrofit, Supabase).          |
| `@Binds`                                | Maps interfaces to implementations.                              |
| `@Singleton`                            | Ensures only one instance is created and shared app-wide.        |

---

## Example Modules

### **AuthBindingModule – Interface Bindings**
Uses `@Binds` to map repository interfaces to implementations.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindingModule {

    @Binds
    @Singleton
    abstract fun bindGoogleAuthRepository(
        googleAuthRepositoryImpl: GoogleAuthRepositoryImpl
    ): GoogleAuthRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl
    ): UserProfileRepository
}
```

---
### **StandingsModule – Retrofit + Repository Provider**
Uses `@Provides` for Retrofit API and repository.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
class StandingsModule {

    @Provides
    @Singleton
    fun provideF1StandingsApi(): F1StandingsApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1StandingsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideF1StandingsRepository(
        api: F1StandingsApi,
        supabase: SupabaseClient,
        localDataSource: LocalDataSource
    ): IF1StandingsRepository {
        return F1StandingRepositoryImpl(api, supabase, localDataSource)
    }
}
```

---

### **SupabaseModule – Supabase Core Clients**
Central place for Supabase setup (Auth, Postgrest, Storage, Functions).

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = AppConstants.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_API_KEY
        ) {
            install(Postgrest)
            install(Auth)
            install(Storage)
            install(Functions)
        }
    }

    @Provides @Singleton fun provideSupabaseAuth(client: SupabaseClient): Auth = client.auth
    @Provides @Singleton fun provideSupabasePostgrest(client: SupabaseClient): Postgrest = client.postgrest
    @Provides @Singleton fun provideSupabaseStorage(client: SupabaseClient): Storage = client.storage
    @Provides @Singleton fun provideSupabaseFunctions(client: SupabaseClient): Functions = client.functions
}
```

---

## Example: ViewModel Injection

```kotlin
@HiltViewModel
class StandingsViewModel @Inject constructor(
  private val standingsRepository: IF1StandingsRepository
) : ViewModel() {

  val standings = standingsRepository.getStandingsFlow()
}
```
The **StandingsRepository** and its dependencies (Retrofit API + SupabaseClient + LocalDataSource) are automatically injected through the modules defined above.

---

## Hilt DI flow
```mermaid
    subgraph App[Oversteer F1 Application]
        A[@HiltAndroidApp Application]
        B[@AndroidEntryPoint Activity / Composable]
        C[@HiltViewModel ViewModel]
    end

    subgraph Modules[Hilt Modules]
        M1[AuthBindingModule]
        M2[StandingsModule]
        M3[SupabaseModule]
    end

    subgraph Providers[Provided Dependencies]
        P1[GoogleAuthRepositoryImpl]
        P2[UserProfileRepositoryImpl]
        P3[F1StandingsApi (Retrofit)]
        P4[F1StandingRepositoryImpl]
        P5[SupabaseClient + (Auth, Postgrest, Storage, Functions)]
    end

    %% Connections
    A --> B
    B --> C

    M1 --> P1
    M1 --> P2
    M2 --> P3
    M2 --> P4
    M3 --> P5

    P1 --> C
    P2 --> C
    P4 --> C
    P5 --> C
```

