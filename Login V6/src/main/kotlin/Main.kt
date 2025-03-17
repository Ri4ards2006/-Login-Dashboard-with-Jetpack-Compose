// IMPORTS
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

// THEME UND FARBPALETTE
private val MyDarkPalette = darkColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

// SCREEN-ENUM UND NAVIGATIONSKLASSEN
sealed class Screen {
    object Login : Screen()
    object Main : Screen()
    data class Category(val categoryName: String) : Screen()
    // Hier können bei Bedarf weitere Screens definiert werden.
}

// LOGIN SCREEN
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val loginEnabled = username.isNotBlank() && password.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = 12.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 300.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Login", style = MaterialTheme.typography.h5, color = MyDarkPalette.primary)

                // Username-Feld
                Text("Username", color = Color.LightGray)
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("Enter username") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Passwort-Feld
                Text("Password", color = Color.LightGray)
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter password") },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Checkbox: Passwort anzeigen
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = showPassword,
                        onCheckedChange = { showPassword = it }
                    )
                    Text("Show Password", color = Color.LightGray)
                }

                // Login Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { if (loginEnabled) onLoginSuccess(username) },
                        enabled = loginEnabled
                    ) {
                        Text("Login")
                    }
                }
            }
        }
    }
}

// MAIN SCREEN (Amazon-Stil Dashboard)
// Zeigt einen TopBar, eine Suchleiste, ein Promotionsbanner, und ein Raster mit Kategorie-Cards.
@Composable
fun MainScreen(onCategorySelected: (String) -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        TopAppBar(
            title = { Text("Amazon Style Dashboard", color = Color.White) },
            actions = {
                Button(onClick = { onLogout() }) {
                    Text("Logout")
                }
            },
            backgroundColor = MyDarkPalette.surface
        )
        SearchBar(onSearch = { query -> println("Searching for: $query") })
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            PromotionalBanner("Welcome to Our Store!")
            Spacer(modifier = Modifier.height(16.dp))
            // Kategorie-Raster: Zwei Reihen mit je zwei Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CategoryCard("Electronics", onCategorySelected)
                CategoryCard("Books", onCategorySelected)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CategoryCard("Clothing", onCategorySelected)
                CategoryCard("Home & Kitchen", onCategorySelected)
            }
            Spacer(modifier = Modifier.height(16.dp))
            ExtraContentArea()
        }
    }
}

// CATEGORY SCREEN: Zeigt Details zu der ausgewählten Kategorie
@Composable
fun CategoryScreen(categoryName: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        TopAppBar(
            title = { Text("$categoryName", color = Color.White) },
            navigationIcon = {
                Button(onClick = { onBack() }) {
                    Text("Back")
                }
            },
            backgroundColor = MyDarkPalette.surface
        )
        // Simuliert eine Gitteranzeige von Items
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            for (i in 1..5) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (j in 1..2) {
                        ItemCard(itemName = "$categoryName Item ${((i - 1) * 2 + j)}")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// KATEGORIE-CARD: Eine klickbare Karte, die zu einer Kategorie führt
@Composable
fun CategoryCard(categoryName: String, onCategorySelected: (String) -> Unit) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .size(150.dp)
            .clickable { onCategorySelected(categoryName) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MyDarkPalette.background),
            contentAlignment = Alignment.Center
        ) {
            Text(categoryName, style = MaterialTheme.typography.h6, color = MyDarkPalette.primary)
        }
    }
}

// ITEM CARD: Simuliert ein einzelnes Produkt-Item
@Composable
fun ItemCard(itemName: String) {
    Card(
        elevation = 6.dp,
        modifier = Modifier
            .size(150.dp)
            .clickable { /* Weitere Aktionen möglich */ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(itemName, color = Color.White)
        }
    }
}

// SUCHLEISTE
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search...") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// PROMOTIONAL BANNER
@Composable
fun PromotionalBanner(bannerText: String) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Magenta, Color.Cyan)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(bannerText, style = MaterialTheme.typography.h5, color = Color.Black)
        }
    }
}

// DIVIDER MIT ABSTAND
@Composable
fun SpacerDivider() {
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = Color.Gray, thickness = 1.dp)
    Spacer(modifier = Modifier.height(8.dp))
}

// FOOTER
@Composable
fun AppFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("© 2025 Your Company", color = Color.Gray)
        Text("All rights reserved.", color = Color.Gray)
    }
}

// DUMMY-SEKTIONEN FÜR EXTRA INHALTE
@Composable
fun DummySection1() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Section 1", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        for (i in 1..3) {
            Text("Dummy content line $i", color = Color.LightGray)
        }
    }
}

@Composable
fun DummySection2() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Section 2", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        for (i in 1..3) {
            Text("More dummy content line $i", color = Color.LightGray)
        }
    }
}

@Composable
fun DummySection3() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Section 3", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        for (i in 1..3) {
            Text("Even more dummy content line $i", color = Color.LightGray)
        }
    }
}

// EXTRA INHALTSBEREICH, DER DIE DUMMY-SEKTIONEN UND DEN FOOTER VEREINT
@Composable
fun ExtraContentArea() {
    Column(modifier = Modifier.fillMaxWidth()) {
        PromotionalBanner("Special Offer: Up to 50% Off!")
        SpacerDivider()
        DummySection1()
        SpacerDivider()
        DummySection2()
        SpacerDivider()
        DummySection3()
        SpacerDivider()
        AppFooter()
    }
}

// EXTRA DUMMY-INHALTE ZUR Erweiterung des Codes
@Composable
fun DummyContentA() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dummy Content A", style = MaterialTheme.typography.h6, color = Color.White)
        for (i in 1..10) {
            Text("Line A$i", color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

@Composable
fun DummyContentB() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dummy Content B", style = MaterialTheme.typography.h6, color = Color.White)
        for (i in 1..10) {
            Text("Line B$i", color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

@Composable
fun DummyContentC() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dummy Content C", style = MaterialTheme.typography.h6, color = Color.White)
        for (i in 1..10) {
            Text("Line C$i", color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

@Composable
fun ExtraDummySections() {
    Column(modifier = Modifier.fillMaxWidth()) {
        DummyContentA()
        DummyContentB()
        DummyContentC()
    }
}

// Komplett zusammengeführte Hauptseite, die den vollständigen Inhalt anzeigt
@Composable
fun CompleteMainPage(onCategorySelected: (String) -> Unit, onLogout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        MainScreen(onCategorySelected = onCategorySelected, onLogout = onLogout)
        ExtraDummySections()
    }
}

// MAIN-FUNKTION UND NAVIGATION
fun main() = application {
    // Navigation über einen mutable State
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }
    val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Amazon Style Compose App",
        state = windowState
    ) {
        // Mindestgröße des Fensters festlegen
        window.minimumSize = Dimension(1024, 768)
        MaterialTheme(colors = MyDarkPalette) {
            when (val screen = currentScreen) {
                is Screen.Login -> {
                    LoginScreen { userName ->
                        // Bei erfolgreichem Login wechsle zum Main Screen
                        currentScreen = Screen.Main
                    }
                }
                is Screen.Main -> {
                    CompleteMainPage(
                        onCategorySelected = { categoryName ->
                            currentScreen = Screen.Category(categoryName)
                        },
                        onLogout = {
                            currentScreen = Screen.Login
                        }
                    )
                }
                is Screen.Category -> {
                    CategoryScreen(categoryName = screen.categoryName, onBack = {
                        currentScreen = Screen.Main
                    })
                }
            }
        }
    }
}
