# Compose Brand App

A modern Android application built with Jetpack Compose that allows users to search and explore brand information using the Brandfetch API.

## Features ✨

- **🔍 Real-time Brand Search**: Auto-search functionality with debounced input (searches after 3+ characters)
- **📱 Brand Details**: Detailed brand information including logos, banners, descriptions, and colors
- **🎨 Modern UI**: Built with Material Design 3 and Jetpack Compose
- **⚡ Smooth Performance**: Shimmer loading effects and optimized scrolling
- **🌙 Theme Support**: Light and dark theme compatibility
- **📊 Clean Architecture**: MVVM pattern with Repository and UseCase layers

## Screenshots 📸

| Brand Search | Brand Details | Loading State |
|-------------|---------------|---------------|
| Search brands by typing in the search bar | View detailed brand information | Shimmer effects during loading |

## Tech Stack 🛠️

### Architecture & Patterns
- **MVVM (Model-View-ViewModel)** - Separation of concerns
- **Repository Pattern** - Data layer abstraction
- **Use Cases** - Business logic encapsulation
- **Clean Architecture** - Modular and testable code structure

### UI & Design
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern declarative UI toolkit
- **[Material Design 3](https://m3.material.io/)** - Google's latest design system
- **[Coil](https://coil-kt.github.io/coil/)** - Image loading and caching
- **Custom Shimmer Effects** - Loading state animations

### Networking & Data
- **[Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html)** - HTTP client for API calls
- **[Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)** - JSON parsing
- **[Brandfetch API v2](https://docs.brandfetch.com/)** - Brand data source

### Dependency Injection
- **[Koin](https://insert-koin.io/)** - Lightweight dependency injection

### Asynchronous Programming
- **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Asynchronous operations
- **[Flow](https://kotlinlang.org/docs/flow.html)** - Reactive streams

### Navigation
- **[Compose Navigation](https://developer.android.com/jetpack/compose/navigation)** - Screen navigation

## API Integration 🌐

This app uses the **Brandfetch API v2** to fetch brand information:

- **Base URL**: `https://api.brandfetch.io/v2`
- **Search Endpoint**: `/search/{query}` - Search brands by name or domain
- **Details Endpoint**: `/brands/{domain}` - Get detailed brand information

### API Features Used:
- Brand search functionality
- Brand logos and icons
- Brand banners and images
- Brand descriptions and metadata
- Brand color palettes
- Company information (employee count, etc.)

## Getting Started 🚀

### Prerequisites
- Android Studio Hedgehog or newer
- Android SDK 24+
- Kotlin 2.2.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/compose-brand-app.git
   cd compose-brand-app
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Build and Run**
   - Wait for Gradle sync to complete
   - Click the "Run" button

## Usage Guide 📋

### Searching for Brands
1. **Open the app** - You'll see the brand search screen
2. **Type in the search bar** - Start typing a brand name or domain
3. **Auto-search** - After typing 3+ characters, the app automatically searches
4. **View results** - Brand results appear in a scrollable list
5. **Tap a brand** - Navigate to detailed brand information

### Viewing Brand Details
- **Brand Header** - Logo, banner image, and basic info
- **Brand Information** - Name, domain, and descriptions
- **Scrollable Content** - All content is vertically scrollable
- **Back Navigation** - Use the back button to return to search

### Key Features
- **Debounced Search** - Optimized to avoid excessive API calls
- **Loading States** - Shimmer effects show loading progress
- **Error Handling** - User-friendly error messages
- **Responsive Design** - Adapts to different screen sizes

## Project Structure 📁

```
app/src/main/java/com/bksd/brandapp/
├── component/           # Reusable UI components
├── core_domain/         # Domain layer contracts
├── core_ui/             # UI utilities and base components
├── data/                # Data layer implementations
├── domain/              # Business logic and use cases
├── network/             # Network layer (Ktor client)
├── repository/          # Repository implementations
├── ui/                  # UI screens and ViewModels
│   ├── brand_detail/    # Brand details screen
│   └── brand_list/      # Brand search screen
└── extension/           # Kotlin extensions
```

## License 📄

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments 🙏

- [Brandfetch](https://brandfetch.com/) for providing the brand data API
- [Jetpack Compose](https://developer.android.com/jetpack/compose) team for the amazing UI toolkit
- [Ktor](https://ktor.io/) team for the excellent HTTP client

## Contact 📧

If you have any questions or suggestions, feel free to reach out!

---

**Built with ❤️ using Jetpack Compose and Kotlin**
