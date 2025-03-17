# Amazon-Style Login and Dashboard with Jetpack Compose

This project is a desktop application developed using **Jetpack Compose for Desktop** and **Kotlin**. It features a sleek login page, an Amazon-inspired dashboard with category browsing, and individual category screens populated with dummy content. The application showcases a modern dark theme, intuitive navigation, and reusable UI components, making it an excellent starting point for developers exploring Compose on desktop.

---

## Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Code Structure](#code-structure)
- [Dependencies](#dependencies)
- [Design Choices](#design-choices)
- [Screen Navigation](#screen-navigation)
- [UI Components](#ui-components)
- [Color Palette](#color-palette)
- [Resources](#resources)
- [Future Improvements](#future-improvements)
- [Known Issues](#known-issues)
- [Contributing](#contributing)
- [License](#license)

---

## Features
- **Login Screen**: Simple user authentication with username and password fields.
- **Password Visibility Toggle**: Checkbox to show or hide the password.
- **Amazon-Style Dashboard**: Main screen with a search bar, promotional banner, and category cards.
- **Category Browsing**: Clickable category cards leading to detailed screens with item grids.
- **Dark Theme**: Custom color palette for a modern, eye-friendly UI.
- **Scrollable Content**: Vertical scrolling for dashboard and category screens.
- **Dummy Content**: Placeholder sections to simulate a fully populated app.
- **State-Based Navigation**: Seamless transitions between screens using a mutable state.

---

## Screenshots
Below are descriptions of the key screens (screenshots can be added later):

- **Login Screen**: A centered card with username and password inputs, a "Show Password" checkbox, and a login button.
- **Main Dashboard**: Features a top app bar with logout option, a search bar, a colorful promotional banner, category cards in a grid, and additional dummy content sections.
- **Category Screen**: Displays a top app bar with a back button and a grid of dummy item cards specific to the selected category.

---

## Installation
To get started with the project, follow these steps:

1. **Install Kotlin**: Ensure you have the Kotlin compiler and runtime installed. Download it from [kotlinlang.org](https://kotlinlang.org/).
2. **Set Up Jetpack Compose for Desktop**: Follow the official setup guide at [JetBrains Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform#compose-multiplatform).
3. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo/amazon-style-compose-app.git
   ```
4. **Open in IDE**: Launch the project in IntelliJ IDEA or another compatible IDE.
5. **Build the Project**: Run the build process to download dependencies and compile the code.

---

## Usage
1. **Run the Application**: Execute the `main` function in `main.kt` from your IDE.
2. **Login Screen**: 
   - Enter any non-empty username and password (no real authentication is implemented).
   - Check "Show Password" to toggle visibility.
   - Click **Login** to proceed.
3. **Main Dashboard**:
   - Explore the search bar (placeholder functionality).
   - View the promotional banner and dummy content.
   - Click a category card (e.g., "Electronics") to navigate to its screen.
   - Use the **Logout** button to return to the login screen.
4. **Category Screen**:
   - Browse dummy items in a grid layout.
   - Click **Back** to return to the dashboard.

*The application launches in a window sized 1024x768 pixels with a minimum size constraint.*

---

## Code Structure
The entire project resides in `main.kt`, organized as follows:

- **Theme Definition**: Custom `MyDarkPalette` for dark mode styling.
- **Screen Enum**: `Screen` class defining `Login`, `Main`, and `Category` states.
- **Composables**:
  - `LoginScreen`: Login UI and logic.
  - `MainScreen` & `CompleteMainPage`: Dashboard with categories and extra content.
  - `CategoryScreen`: Category-specific item display.
  - Helper components like `CategoryCard`, `ItemCard`, `SearchBar`, etc.
- **Main Function**: Application entry point with window setup and navigation logic.

---

## Dependencies
The project relies on the following technologies:

| Dependency            | Purpose                       |
|-----------------------|-------------------------------|
| Jetpack Compose       | UI toolkit for desktop apps   |
| Kotlin Standard Library | Core language functionality |

*Note: Dependencies are typically managed via Gradle, but no build file is provided here.*

---

## Design Choices
- **Dark Theme**: Uses a custom palette (e.g., `#BB86FC` for primary) to align with modern UI trends and enhance readability.
- **Amazon-Inspired Layout**: Mimics e-commerce dashboards with a search bar, promotional banner, and grid-based categories.
- **Navigation**: Employs a `mutableStateOf` for simple, reactive screen transitions.

---

## Screen Navigation
Navigation is handled via a state machine. Here’s how screens transition:

| Current Screen | Action            | Next Screen |
|----------------|-------------------|-------------|
| Login          | Successful Login  | Main        |
| Main           | Select Category   | Category    |
| Main           | Logout            | Login       |
| Category       | Back              | Main        |

---

## UI Components
Key reusable components in the project:

| Component          | Description                                             |
|--------------------|---------------------------------------------------------|
| `LoginScreen`      | Form for username/password entry with login button      |
| `MainScreen`       | Dashboard with search, banner, and category grid        |
| `CategoryScreen`   | Grid of items for a selected category                   |
| `CategoryCard`     | Clickable card for category selection                   |
| `ItemCard`         | Dummy item representation in category screens           |
| `SearchBar`        | Placeholder search input field                          |
| `PromotionalBanner`| Gradient-styled banner with promotional text            |
| `AppFooter`        | Copyright notice at the bottom                          |
| `ExtraContentArea` | Combines dummy sections and footer for the dashboard    |

---

## Color Palette
The dark theme is defined with these colors:

| Color           | Hex Code   | Usage            |
|-----------------|------------|------------------|
| Primary         | #BB86FC    | Buttons, accents |
| Primary Variant | #3700B3    | Variations       |
| Secondary       | #03DAC6    | Highlights       |
| Background      | #121212    | App background   |
| Surface         | #1E1E1E    | Cards, surfaces  |

---

## Resources
- [Jetpack Compose for Desktop](https://github.com/JetBrains/compose-multiplatform) - Official documentation.
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html) - Language reference.
- [Material Design Guidelines](https://material.io/design) - UI design inspiration.

---

## Future Improvements
- Add real authentication with a backend.
- Implement functional search capabilities.
- Replace dummy content with real data or APIs.
- Enhance visuals with images and animations.
- Expand features (e.g., shopping cart, user profile).

---

## Known Issues
- **Search Bar**: Currently a placeholder with no actual search logic.
- **Dummy Data**: Category items and extra content are static placeholders.
- **No Persistence**: Login credentials are not stored or validated.

---

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a branch for your changes.
3. Submit a pull request with clear descriptions and tests.

Please follow the existing code style and structure.

---

## License
This project is licensed under the **MIT License**. Feel free to use, modify, and distribute it as needed.
