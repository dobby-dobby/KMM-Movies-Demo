# KMM-Movies-Demo

A Kotlin Multiplatform (KMM) app showcasing trending movies and search functionality using the TMDB API, built with Android Jetpack libraries and iOS-compatible shared logic.

![](https://github.com/dobby-dobby/KMM-Movies-Demo/blob/master/gif_demo_app.gif)
## Features
- **Movies List Screen**: Displays trending movies (cached for offline use) or online search results with movie poster, title, year, and vote average. Dynamic header toggles between "Trending Movies" and "Search Results".
- **Search**: Real-time movie search using TMDB's search endpoint.
- **Movie Detail Screen**: Shows detailed movie info, supports offline access, and opens hyperlinks.
- **Offline Support**: Caches trending movies and movie details with timestamp validation.
- **KMM**: Shares API calls and caching logic between Android and iOS. iOS logs fetched/stored data (no UI).
- **Architecture**: Uses Jetpack (MVVM, Room, Retrofit, Coroutines, Hilt) and Compose for Android.
- **Testing**: Includes unit tests for caching and a UI test for core functionality.
- **Error Handling**: Manages offline/online states and API errors with user-friendly messages.

## Tech Stack
- **Android**: Kotlin, Jetpack Compose, Room, Retrofit, Hilt, Coroutines
- **KMM**: Shared networking (Ktor), caching (SQLDelight), and business logic
- **iOS**: Swift, KMM integration for logging
- **Testing**: JUnit, Espresso, MockK

## Setup
1. Clone the repository: `git clone https://github.com/dobby-dobby/KMM-Movies-Demo`
2. Build and run the Android app in Android Studio.
3. For iOS, open the `iosApp` project in Xcode and run.

## Notes
- Trending movies are cached with a 24-hour validity period.
- Search functionality requires an internet connection.
