import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.Button
import androidx.compose.material.darkColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

// Eigene Farbpalette für ein modernes, dunkles Design
private val CoolDarkColorPalette = darkColors(
    primary = Color(0xFFBB86FC),      // sanftes Lila
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

// Definition der verfügbaren Screens
sealed class Screen {
    object Login : Screen()
    data class Dashboard(val name: String, val accountType: String) : Screen()
}

@Composable
fun LoginScreen(onLoginSuccess: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var accountType by remember { mutableStateOf<String?>(null) } // "gewerblich" oder "privat"

    // Buttons aktivieren, wenn Name, Passwort und Accounttyp gefüllt sind
    val buttonsEnabled = name.isNotBlank() && password.isNotBlank() && accountType != null

    // Hintergrund mit vertical gradient
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(CoolDarkColorPalette.background, CoolDarkColorPalette.surface)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Card als stilvoller Container
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
                // Überschrift
                Text("Login", style = MaterialTheme.typography.h5, color = CoolDarkColorPalette.primary)

                // Name-Feld inkl. Label
                Column {
                    Text("Name", style = MaterialTheme.typography.body1, color = Color.LightGray)
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Gib deinen Namen ein") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                }

                // Passwort-Feld inkl. Label
                Column {
                    Text("Passwort", style = MaterialTheme.typography.body1, color = Color.LightGray)
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Gib dein Passwort ein") },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                }

                // Checkbox: Passwort anzeigen
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = showPassword,
                        onCheckedChange = { showPassword = it }
                    )
                    Text("Passwort anzeigen", color = Color.LightGray)
                }

                // Radio-Buttons für Accounttyp
                Column {
                    Text("Accounttyp:", style = MaterialTheme.typography.body1, color = Color.LightGray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = accountType == "gewerblich",
                            onClick = { accountType = "gewerblich" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = CoolDarkColorPalette.primary,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text("gewerblich", color = Color.LightGray, modifier = Modifier.padding(end = 16.dp))
                        RadioButton(
                            selected = accountType == "privat",
                            onClick = { accountType = "privat" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = CoolDarkColorPalette.primary,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text("privat", color = Color.LightGray)
                    }
                }

                // Zeile mit den Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onLoginSuccess(name, accountType!!) },
                        enabled = buttonsEnabled
                    ) {
                        Text("Anmelden")
                    }
                    Button(
                        onClick = { onLoginSuccess(name, accountType!!) },
                        enabled = buttonsEnabled
                    ) {
                        Text("Registrieren")
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(name: String, accountType: String, onLogout: () -> Unit) {
    // Einfacher Dashboard-Bildschirm mit Willkommensnachricht und Logout-Button
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(CoolDarkColorPalette.background, CoolDarkColorPalette.surface)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Willkommen, $name!",
                style = MaterialTheme.typography.h4,
                color = CoolDarkColorPalette.primary
            )
            Text(
                text = "Accounttyp: $accountType",
                style = MaterialTheme.typography.h6,
                color = Color.LightGray
            )
            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}

fun main() = application {
    // Zustandsverwaltung für den aktuellen Screen
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    // Erstelle ein WindowState mit einer Startgröße und setze eine Mindestgröße
    val windowState = rememberWindowState(width = 600.dp, height = 500.dp)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Login Screen",
        state = windowState
    ) {
        // Mindestgröße des Fensters
        window.minimumSize = Dimension(600, 500)
        MaterialTheme(colors = CoolDarkColorPalette) {
            when (val screen = currentScreen) {
                is Screen.Login -> LoginScreen { name, accountType ->
                    // Bei erfolgreichem Login/Registrierung wechseln wir zur Dashboard-Seite
                    currentScreen = Screen.Dashboard(name, accountType)
                }
                is Screen.Dashboard -> DashboardScreen(name = screen.name, accountType = screen.accountType) {
                    // Beim Logout wechseln wir zurück zum Login
                    currentScreen = Screen.Login
                }
            }
        }
    }
}
