# Project Architecture

## Overview
The project follows a **multi-module Clean Architecture (MVVM)** design, aiming for:
- Clear separation of concerns
- Independent feature modules
- Better testability and maintainability
- Faster Gradle build times

---

## Module Overview

### App Module
- **`app`**: The main entry point of the application.
    - Hosts navigation graph
    - Provides bottom navigation
    - Wires together feature modules

---  

### Feature Modules
These modules encapsulate user-facing features. Each is designed to be as independent as possible, communicating through shared/core modules.

| Module           | Responsibility                                      |
|------------------|-----------------------------------------------------|
| `authentication` | Handles login, user authentication, and sessions    |
| `standings`      | Displays constructors’ and drivers’ standings       |
| `news`           | Fetches and displays Formula 1 related news         |
| `schedule`       | Displays upcoming and past race schedules           |
| `results`        | Shows detailed race results                         |
| `settings`       | Manages app settings and preferences                |

---

### Shared/Core Modules
These modules provide foundational services, utilities, and design system components. They are reusable and consumed by multiple feature modules.

| Module           | Responsibility                                      |
|------------------|-----------------------------------------------------|
| `design`         | Centralized UI theming (colors, typography, shapes) |
| `datasource`     | Local persistence (Room, DataStore) + remote access |
| `network`        | Supabase API client and integration layer           |
| `notifications`  | Firebase Cloud Messaging and push notification logic|
| `utilities`      | Shared helpers, constants, and utility functions    |

---

## Architecture Layers
The clean architecture layers are represented as:

- **Presentation Layer (UI)**
    - Jetpack Compose screens & ViewModels
    - State handling (UIState, events)

- **Domain Layer**
    - UseCases for business logic
    - Repository
    - Dto model(if have)

- **Data Layer**
    - Repository implementations
    - Data sources (local with Room/DataStore, remote via Supabase)
    - DTO ↔ Domain model mapping (mapper)

---

## Module Dependency Diagram

```mermaid
flowchart TD

    app[App Module] --> authentication
    app --> standings
    app --> news
    app --> schedule
    app --> results
    app --> settings

    authentication --> design
    authentication --> datasource
    authentication --> network
    authentication --> utilities

    standings --> design
    standings --> datasource
    standings --> network
    standings --> utilities

    news --> design
    news --> datasource
    news --> network
    news --> utilities

    schedule --> design
    schedule --> datasource
    schedule --> network
    schedule --> utilities

    results --> design
    results --> datasource
    results --> network
    results --> utilities

    settings --> design
    settings --> datasource
    settings --> utilities

    notifications --> utilities
    app --> notifications
    
📌 Explanation:  
- The **App Module** orchestrates everything and connects to each feature module.  
- **Feature modules** depend on shared/core modules (`design`, `datasource`, `network`, `utilities`).  
- `notifications` is a special shared module that the app integrates directly.  
---

## Dependency Injection
- **Hilt** is used for dependency injection.
- Shared modules (like `network` and `datasource`) provide reusable bindings.
- Each feature module has its own Hilt setup for scoped dependencies.

---

## Navigation
- **Navigation-Compose** is used for screen-to-screen navigation.
- `AppNavGraph` manages routes for all feature modules.
- Bottom navigation in the `app` module connects the main screens.

---

## Offline Support
- **Room Database** for local caching
- **DataStore** for key-value persistence (e.g., user preferences)
- Network + cache strategies ensure offline-first experience

---

## Notifications
- **Supabase Edge Functions** trigger Firebase Cloud Messaging (FCM) events.
- Progressive notifications (e.g., 7 days, 1 day, 15 minutes before race).
 
---

## Future Enhancements
- Add instrumentation/UI tests with `compose-ui-test`
- Improve offline-first logic