import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.Button
import androidx.compose.material.TextButton
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun LoginScreen() {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Zentriert den Login-Bereich auf dem Bildschirm
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Card als Container für das Login-Fenster
        Card(
            elevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Überschrift
                Text("Login", style = MaterialTheme.typography.h5)

                // Eingabefeld für den Namen mit Label
                Column {
                    Text("Name", style = MaterialTheme.typography.body1)
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Gib deinen Namen ein") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Eingabefeld für das Passwort mit Label
                Column {
                    Text("Passwort", style = MaterialTheme.typography.body1)
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Gib dein Passwort ein") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Zeile mit zwei Buttons für Anmelden und Registrieren
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
    Window(onCloseRequest = ::exitApplication, title = "Login Screen") {
        MaterialTheme {
            LoginScreen()
        }
    }
}
