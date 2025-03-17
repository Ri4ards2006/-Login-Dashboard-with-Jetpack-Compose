import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

// Eigene Farbpalette für ein modernes, dunkles Design
private val CoolDarkColorPalette = darkColors(
    primary = Color(0xFFBB86FC),      // sanftes Lila
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6)
)

@Composable
fun LoginScreen() {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Hintergrund mit vertical gradient für einen "coolen" Look
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF121212), Color(0xFF1E1E1E))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Card als stilvoller Container
        Card(
            shape = MaterialTheme.shapes.medium,
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
                Text("Login", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primary)

                // Name-Feld inkl. Label
                Column {
                    Text("Name", style = MaterialTheme.typography.body1, color = Color.LightGray)
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Gib deinen Namen ein") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { })
                    )
                }

                // Passwort-Feld inkl. Label
                Column {
                    Text("Passwort", style = MaterialTheme.typography.body1, color = Color.LightGray)
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Gib dein Passwort ein") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { })
                    )
                }

                // Zeile mit den Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { println("Anmelden: Name = $name, Passwort = $password") }
                    ) {
                        Text("Anmelden")
                    }
                    Button(
                        onClick = { println("Registrieren: Name = $name, Passwort = $password") }
                    ) {
                        Text("Registrieren")
                    }
                }
            }
        }
    }
}

fun main() = application {
    // Erstelle ein WindowState mit einer Startgröße
    val windowState = rememberWindowState(width = 600.dp, height = 400.dp)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Login Screen",
        state = windowState
    ) {
        // Stelle sicher, dass das Fenster niemals kleiner als 600x400 wird:
        window.minimumSize = Dimension(600, 400)
        MaterialTheme(colors = CoolDarkColorPalette) {
            LoginScreen()
        }
    }
}
