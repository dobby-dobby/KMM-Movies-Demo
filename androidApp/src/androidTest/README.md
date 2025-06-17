# UI Tests for Movies KMM App

This directory contains comprehensive UI tests for the Movies KMM Android application.

## Test Structure

### Test Files

1. **MainActivityTest.kt** - Tests the main activity and basic app functionality
2. **MovieDetailScreenTest.kt** - Tests the movie detail screen functionality
3. **MoviesAppUITest.kt** - Comprehensive test suite covering complete user journeys
4. **TestHelper.kt** - Helper utilities for common test operations
5. **TestConfig.kt** - Test configuration and constants

### Test Coverage

The UI tests cover the following functionality:

#### Main Activity Tests
- ✅ App launches successfully
- ✅ Search field interaction
- ✅ Movies list display
- ✅ Movie item click navigation
- ✅ Search functionality
- ✅ Loading state handling

#### Movie Detail Screen Tests
- ✅ Navigation to detail screen
- ✅ Movie detail content display
- ✅ Homepage link functionality
- ✅ Detail screen loading state
- ✅ Back navigation

#### Comprehensive App Tests
- ✅ Complete user journey (launch → search → navigate → back)
- ✅ Search functionality with various queries
- ✅ Navigation flow
- ✅ App responsiveness
- ✅ Error handling
- ✅ Accessibility features
- ✅ Performance testing

## Running the Tests

### Prerequisites

1. **Android Emulator or Device**: Make sure you have an Android emulator running or a physical device connected
2. **Internet Connection**: Tests require internet access to fetch movie data from the API
3. **Gradle Sync**: Ensure all dependencies are synced

### Running All UI Tests

```bash
# From project root
./gradlew :androidApp:connectedAndroidTest

# Or from androidApp directory
./gradlew connectedAndroidTest
```

### Running Specific Test Classes

```bash
# Run only MainActivity tests
./gradlew :androidApp:connectedAndroidTest --tests "com.sopa.movieskmm.android.MainActivityTest"

# Run only MovieDetailScreen tests
./gradlew :androidApp:connectedAndroidTest --tests "com.sopa.movieskmm.android.MovieDetailScreenTest"

# Run only comprehensive app tests
./gradlew :androidApp:connectedAndroidTest --tests "com.sopa.movieskmm.android.MoviesAppUITest"
```

### Running Specific Test Methods

```bash
# Run a specific test method
./gradlew :androidApp:connectedAndroidTest --tests "com.sopa.movieskmm.android.MainActivityTest.testAppLaunchesSuccessfully"
```

## Test Configuration

### Timeouts

The tests use configurable timeouts defined in `TestConfig.kt`:

- `APP_LOAD_TIMEOUT`: 5000ms - Time to wait for app to load
- `MOVIES_LOAD_TIMEOUT`: 10000ms - Time to wait for movies to load
- `DETAIL_LOAD_TIMEOUT`: 5000ms - Time to wait for detail screen to load
- `SEARCH_TIMEOUT`: 5000ms - Time to wait for search results
- `NAVIGATION_TIMEOUT`: 3000ms - Time to wait for navigation

### Test Data

Test queries are defined in `TestConfig.kt`:

- `TEST_SEARCH_QUERY`: "Avengers"
- `TEST_SEARCH_QUERY_2`: "Batman"
- `TEST_INVALID_QUERY`: "NonExistentMovie12345"
- `TEST_SPECIAL_CHARS_QUERY`: "Spider-Man"

## Test Helper Functions

The `TestHelper` object provides reusable functions:

- `waitForAppToLoad()` - Wait for app to load and show search field
- `waitForMoviesToLoad()` - Wait for movies to load or error to appear
- `waitForMovieDetailToLoad()` - Wait for movie detail screen to load
- `navigateToMovieDetail()` - Navigate to movie detail screen
- `performSearch()` - Perform search with given query
- `assertMoviesOrErrorDisplayed()` - Assert movies or error are displayed
- `assertMovieDetailsOrErrorDisplayed()` - Assert movie details or error are displayed
- `assertAppIsResponsive()` - Assert app is responsive

## Troubleshooting

### Common Issues

1. **Tests Fail Due to Network Issues**
   - Ensure internet connection is stable
   - Check if the movie API is accessible
   - Tests are designed to handle network errors gracefully

2. **Tests Fail Due to Timeout**
   - Increase timeout values in `TestConfig.kt`
   - Check device/emulator performance
   - Ensure device is not in power saving mode

3. **Tests Fail Due to UI Changes**
   - Update text constants in `TestConfig.kt`
   - Update content descriptions in the app
   - Update test selectors if UI structure changes

4. **Emulator Issues**
   - Use `TestConfig.isEmulator()` to detect emulator
   - Restart emulator if tests become unstable
   - Use physical device for more reliable results

### Debug Information

The tests include debug information:

```kotlin
// Get device info
println(TestConfig.getDeviceInfo())

// Check if running on emulator
println("Is Emulator: ${TestConfig.isEmulator()}")
```

## Best Practices

1. **Use TestHelper Functions**: Reuse common test logic to maintain consistency
2. **Handle Network Errors**: Tests should work even when network is unavailable
3. **Use Meaningful Timeouts**: Set appropriate timeouts for different operations
4. **Test Error Scenarios**: Include tests for error handling and edge cases
5. **Accessibility Testing**: Verify that UI elements have proper content descriptions

## Continuous Integration

These tests can be integrated into CI/CD pipelines:

```yaml
# Example GitHub Actions workflow
- name: Run UI Tests
  run: ./gradlew :androidApp:connectedAndroidTest
```

## Contributing

When adding new UI tests:

1. Use existing `TestHelper` functions when possible
2. Add new constants to `TestConfig.kt`
3. Follow the existing test naming conventions
4. Include both success and error scenarios
5. Add appropriate timeouts for new operations 