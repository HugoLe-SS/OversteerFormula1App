# 📡 API Documentation – Oversteer F1

## Overview
- **Backend**: Supabase (REST + Edge Functions)
- **External Data Source**: Jolpica API
- **Client**: Retrofit (Android)

### Data Flow
Jolpica API → Supabase (storage + logic) → Oversteer F1 app (Retrofit client)

---

## Supabase API
Supabase auto-generates REST endpoints from PostgreSQL tables.  
Authentication is handled via Supabase Auth (JWT tokens).

### REST Endpoints
| Resource            | Endpoint                               | Method | Description                                |
|---------------------|----------------------------------------|--------|--------------------------------------------|
| **Users**           | `/rest/v1/auth_users`                  | GET    | Fetch all users                            |
|                     | `/rest/v1/auth_users?id=eq.{uuid}`     | GET    | Fetch a user by ID                         |
|                     | `/rest/v1/auth_users`                  | POST   | Create a new user                          |
| **Schedules**       | `/rest/v1/schedules`                   | GET    | Fetch all race schedules                   |
|                     | `/rest/v1/schedules?round=eq.3`        | GET    | Fetch schedule for round 3                 |
| **Standings**       | `/rest/v1/driver_standings`            | GET    | Fetch driver standings                     |
|                     | `/rest/v1/constructor_standings`       | GET    | Fetch constructor standings                |
| **Notifications**   | `/rest/v1/notifications`               | GET    | Get sent notifications                     |
|                     | `/rest/v1/notifications`               | POST   | Insert a new notification log              |

👉 **Headers required for requests:**
```http
apikey: YOUR_SUPABASE_ANON_KEY
Authorization: Bearer YOUR_USER_JWT
Content-Type: application/json
```

---

### Example Usage

#### Fetch Home Details
```kotlin

val result = supabaseClient
    .postgrest["HomeDetails"]
    .select()

val f1HomeDetails = result.decodeList<F1HomeDetails>()

```

#### Fetch Driver Details by driverId
```kotlin
val result = supabaseClient
    .postgrest["DriverDetails"]
    .select {
        filter {
            eq("driverId", driverId)
        }
    }

val driverDetails = result.decodeList<DriverDetails>()

```
#### Fetch Constructor Details by constructorId
```kotlin
val result = supabaseClient
    .postgrest["ConstructorDetails"]
    .select {
        filter {
            eq("constructorId", constructorId)
        }
    }

val constructorDetails = result.decodeList<ConstructorDetails>()

```

---

## Jolpica API

The Jolpica API provides raw Formula 1 data (standings, schedules, results, etc.).  
Our backend (Supabase) periodically pulls from Jolpica to persist data for offline-first usage.  

### Endpoints

| Resource                  | Endpoint                                                                                  | Description                                   |
|---------------------------|-------------------------------------------------------------------------------------------|-----------------------------------------------|
| **Constructor Standings** | `ergast/f1/{season}/constructorstandings`                                                 | Get constructor standings for a season        |
| **Driver Standings**      | `ergast/f1/{season}/driverstandings`                                                      | Get driver standings for a season             |
| **Driver Results**        | `ergast/f1/{season}/drivers/{driverId}/results`                                           | Get all race results for a specific driver    |
| **Driver Qualifying**     | `ergast/f1/{season}/drivers/{driverId}/qualifying`                                        | Get qualifying results for a specific driver  |
| **Constructor Results**   | `ergast/f1/{season}/constructors/{constructorId}/results`                                 | Get race results for a specific constructor   |
| **Constructor Qualifying**| `ergast/f1/{season}/constructors/{constructorId}/qualifying`                              | Get qualifying results for a constructor      |
| **Circuit Results**       | `ergast/f1/{season}/circuits/{circuitId}/results`                                         | Get results for all races at a circuit        |
| **F1 Calendar**           | `ergast/f1/{season}`                                                                      | Get F1 race calendar                          |

---

### Example Usage

#### Fetch Driver Standings (Season 2024)
```http
GET https://api.jolpica.com/ergast/f1/2024/driverstandings
```

#### Fetch Constructor Standings (Season 2023)
```http
GET https://api.jolpica.com/ergast/f1/2023/drivers/hamilton/results
```

---

## External API – ESPN News

ESPN provides the latest Formula 1 news through their public API.  
We fetch these articles directly and display them in the app.  

### Endpoint

| Resource     | Endpoint                                     | Description                  |
|--------------|----------------------------------------------|------------------------------|
| **F1 News**  | `https://site.api.espn.com/apis/site/v2/sports/racing/f1/news` | Get latest F1 news articles  |

---

### Example Usage

#### Fetch Latest ESPN News
```http
GET https://site.api.espn.com/apis/site/v2/sports/racing/f1/news
```

---

## Retrofit Client (Android)
- **Retrofit** is used for making HTTP requests from the app.
- **Moshi / Gson** handles JSON parsing.
- **OkHttp Interceptors** provide logging and error handling.
- **Hilt** provides dependency injection for `ApiService` instances.

### Example Retrofit Setup
```kotlin
interface F1StandingsApi {
    //Constructor Standings API
    @GET("ergast/f1/{season}/constructorstandings")
    suspend fun getConstructorStandings(
        @Path("season") season: String
    ): ConstructorStandingsDto

    //Driver Standings API
    @GET("ergast/f1/{season}/driverstandings")
    suspend fun getDriverStandings(
        @Path("season") season: String
    ): DriverStandingsDto
}

Inside NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val USER_AGENT = "OversteerF1App/1.0"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("User-Agent", USER_AGENT)
                    .build()
                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dummy-base-url.com/") // placeholder, override in feature module
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}

Inside StandingsModule.kt
@Module
@InstallIn(SingletonComponent::class)
class StandingsModule {

    @Provides
    @Singleton
    fun provideF1StandingsApi(retrofit: Retrofit): F1StandingsApi =
    retrofit.newBuilder()
        .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
        .build()
        .create(F1StandingsApi::class.java)


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

---

## Data Flow Diagram
flowchart LR
  ESPN[ESPN API] --> SUPABASE
  SUPABASE --> APP[Oversteer F1 App]
  APP -->|Retrofit| SUPABASE



