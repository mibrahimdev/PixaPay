

# PixaPay - Images Search client

## Getting started
1. Signup or Login into [Pixabay](https://pixabay.com/api/docs/#api_search_images) for getting API_KEY
2. Add `APY_KEY="Your_Key_Here"` and `BASE_URL="https://pixabay.com/api/"` to `gradle.properties` file.

## Libraries and Frameworks
- [Jetpack Compose](https://developer.android.com/jetpack/compose?) - Declarative UI.
- [Navigation component](https://developer.android.com/guide/navigation) - Navigaton with Compose.
- [Material Design](https://material.io/blog/android-material-theme-color)  - Design System.
- [Coil](https://github.com/coil-kt) - Image laoding.
- [Retrofit & OkHttp](https://github.com/square/retrofit) Networking.
- [Room](https://developer.android.com/jetpack/androidx/releases/room) Sqlite database.
- [Hilt](http://google.github.io/hilt/) - Dependency injection.
- [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html) - Reactive programming.
- [Timber](https://github.com/JakeWharton/timber) - Logging.
- [Mockk](https://github.com/mockk/mockk) Kotlin Testing.

## Architecture - Clean Architecture + Offline first
- Data (database, API and preferences code)
- Domain (business logic)
- UI (for presentation logic, using MVVM with finite state machine pattern)

## Tests
- Retrofit and backend Response using MockWebServer
- Use cases and Repositories using Mockk


## TODO
- [ ] Explore full MVI implementation.
- [ ] Show Image in full screen.
