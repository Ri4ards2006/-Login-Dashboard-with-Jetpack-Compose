/* 
==============================================
KOTLIN/JETPACK COMPOSE LOGIN-APP - MEGA DOKUMENTATION
==============================================

STRUKTUR:
1. Import-Bereich
2. Farbpalette-Definition
3. Screen-Definition (Sealed Class)
4. Login Screen Komponente
5. Dashboard Screen Komponente
6. Main-Funktion & App-Initialisierung

JEDER ABSCHNITT WIRD AUSFÜHRLICH ERKLÄRT MIT:
- Funktionsweise der einzelnen Komponenten
- Zusammenspiel der Elemente
- Besonderheiten der Jetpack Compose API
- State-Management Erklärungen
- UI-Layout Details
*/

// ======================================================================
// 1. IMPORT-BEREICH 
// (Hier werden alle benötigten Bibliotheken und Komponenten importiert)
// ======================================================================

// Foundation Komponenten für Basis-Layouts und Gestaltung
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions

// Material Design Komponenten
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.darkColors

// Compose Runtime und UI-Elemente
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Text-Input-Optionen
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

// Größenangaben (Density-independent Pixels)
import androidx.compose.ui.unit.dp

// Window-Management für Desktop-Apps
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

// ======================================================================
// 2. FARBPALETTE - CUSTOM DARK THEME
// (Definition des dunklen Designs mit modernen Farben)
// ======================================================================

/**
 * Eigene dunkle Farbpalette für ein modernes UI-Design.
 *
 * Annotation: Die Farbwerte sind im ARGB-Format (Alpha, Red, Green, Blue)
 * mit hexadezimalen Werten definiert.
 *
 * @property primary       Hauptfarbe für Akzente (Lila)
 * @property primaryVariant Dunklere Variante der Hauptfarbe
 * @property secondary     Sekundärfarbe für Hervorhebungen (Türkis)
 * @property background    Hintergrundfarbe der App
 * @property surface       Farbe für Oberflächenelemente wie Cards
 */
private val CoolDarkColorPalette = darkColors(
    primary = Color(0xFFBB86FC),      // Sanftes Lila für primäre Elemente
    primaryVariant = Color(0xFF3700B3), // Dunkleres Lila für Kontraste
    secondary = Color(0xFF03DAC6),     // Türkis für sekundäre Akzente
    background = Color(0xFF121212),    // Dunkler Hintergrund (Fast Schwarz)
    surface = Color(0xFF1E1E1E)        // Leicht aufgehellte Oberflächen
)

// ======================================================================
// 3. SCREEN-DEFINITION (SEALED CLASS)
// (Navigation zwischen verschiedenen App-Bildschirmen)
// ======================================================================

/**
 * Versiegelte Klasse zur Repräsentation der verschiedenen App-Screens.
 *
 * Annotation: Sealed Classes ermöglichen eine eingeschränkte Vererbungshierarchie,
 * was ideal für Navigation zwischen einer festen Anzahl von Screens ist.
 *
 * @object Login          : Repräsentiert den Login-Bildschirm
 * @data class Dashboard  : Datenklasse für den Dashboard-Screen mit Nutzerdaten
 */
sealed class Screen {
    object Login : Screen()
    data class Dashboard(val name: String, val accountType: String) : Screen()
}

// ======================================================================
// 4. LOGIN SCREEN KOMPONENTE
// (UI und Logik für den Anmeldebildschirm)
// ======================================================================

/**
 * Composable-Funktion für den Login-Screen.
 *
 * @param onLoginSuccess Callback-Funktion, die bei erfolgreicher Anmeldung/Registrierung aufgerufen wird
 *
 * Annotation: Diese Funktion verwendet Jetpack Compose's State-Hoisting-Prinzip,
 * wo der State im übergeordneten Component verwaltet wird.
 */
@Composable
fun LoginScreen(onLoginSuccess: (String, String) -> Unit) {
    // ************ STATE MANAGEMENT ************
    // (Verwaltung des Komponenten-Zustands)

    // Eingabefeld für den Namen
    var name by remember { mutableStateOf("") }

    // Eingabefeld für das Passwort
    var password by remember { mutableStateOf("") }

    // Zustand für die Passwort-Sichtbarkeit
    var showPassword by remember { mutableStateOf(false) }

    // Ausgewählter Accounttyp (null = keine Auswahl)
    var accountType by remember { mutableStateOf<String?>(null) }

    // ************ VALIDIERUNG ************
    // (Aktivierung der Buttons nur bei erfüllten Bedingungen)
    val buttonsEnabled = name.isNotBlank() &&
            password.isNotBlank() &&
            accountType != null

    // ************ UI-AUFBAU ************
    // (Hierarchische Struktur der UI-Elemente)

    // Hintergrundcontainer mit vertikalem Farbverlauf
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CoolDarkColorPalette.background,
                        CoolDarkColorPalette.surface
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Visuell hervorgehobene Karte für Eingabefelder
        Card(
            elevation = 12.dp,  // Schattenstärke
            modifier = Modifier.padding(16.dp)
        ) {
            // Vertikale Anordnung der Eingabeelemente
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 300.dp),  // Minimale Breite
                verticalArrangement = Arrangement.spacedBy(16.dp)  // Abstand zwischen Elementen
            ) {
                // ************ TITEL ************
                Text(
                    "Login",
                    style = MaterialTheme.typography.h5,
                    color = CoolDarkColorPalette.primary
                )

                // ************ NAME-EINGABE ************
                Column {
                    // Label über dem Eingabefeld
                    Text(
                        "Name",
                        style = MaterialTheme.typography.body1,
                        color = Color.LightGray
                    )

                    // TextField für Namenseingabe
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Gib deinen Namen ein") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next  // Tastatur-"Weiter"-Button
                        )
                    )
                }

                // ************ PASSWORT-EINGABE ************
                Column {
                    Text(
                        "Passwort",
                        style = MaterialTheme.typography.body1,
                        color = Color.LightGray
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Gib dein Passwort ein") },
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None  // Klartext
                        } else {
                            PasswordVisualTransformation()  // Punkte
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done  // Tastatur abschließen
                        )
                    )
                }

                // ************ PASSWORT ANZEIGEN CHECKBOX ************
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = showPassword,
                        onCheckedChange = { showPassword = it }
                    )
                    Text(
                        "Passwort anzeigen",
                        color = Color.LightGray
                    )
                }

                // ************ ACCOUNTTYP AUSWAHL ************
                Column {
                    Text(
                        "Accounttyp:",
                        style = MaterialTheme.typography.body1,
                        color = Color.LightGray
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Gewerblich Radio-Button
                        RadioButton(
                            selected = accountType == "gewerblich",
                            onClick = { accountType = "gewerblich" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = CoolDarkColorPalette.primary,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            "gewerblich",
                            color = Color.LightGray,
                            modifier = Modifier.padding(end = 16.dp)
                        )

                        // Privat Radio-Button
                        RadioButton(
                            selected = accountType == "privat",
                            onClick = { accountType = "privat" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = CoolDarkColorPalette.primary,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            "privat",
                            color = Color.LightGray
                        )
                    }
                }

                // ************ BUTTONS ************
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Anmelde-Button
                    Button(
                        onClick = { onLoginSuccess(name, accountType!!) },
                        enabled = buttonsEnabled
                    ) {
                        Text("Anmelden")
                    }

                    // Registrierungs-Button
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

// ======================================================================
// 5. DASHBOARD SCREEN KOMPONENTE
// (Willkommensbildschirm nach erfolgreichem Login)
// ======================================================================

/**
 * Composable-Funktion für den Dashboard-Screen.
 *
 * @param name          Name des angemeldeten Benutzers
 * @param accountType   Ausgewählter Accounttyp
 * @param onLogout      Callback für Logout-Aktion
 */
@Composable
fun DashboardScreen(
    name: String,
    accountType: String,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CoolDarkColorPalette.background,
                        CoolDarkColorPalette.surface
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Willkommensnachricht
            Text(
                text = "Willkommen, $name!",
                style = MaterialTheme.typography.h4,
                color = CoolDarkColorPalette.primary
            )

            // Accounttyp-Anzeige
            Text(
                text = "Accounttyp: $accountType",
                style = MaterialTheme.typography.h6,
                color = Color.LightGray
            )

            // Logout-Button
            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}

// ======================================================================
// 6. MAIN-FUNKTION & APP-INITIALISIERUNG
// (Startpunkt der Anwendung und Fensterkonfiguration)
// ======================================================================

/**
 * Hauptfunktion der Anwendung.
 *
 * Annotation: Die application-Funktion ist der Einstiegspunkt
 * für Compose-Desktop-Anwendungen.
 */
fun main() = application {
    // ************ APP STATE MANAGEMENT ************
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    // Fenstereinstellungen
    val windowState = rememberWindowState(
        width = 600.dp,
        height = 500.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Login Screen",
        state = windowState
    ) {
        // Setzen der minimalen Fenstergröße
        window.minimumSize = Dimension(600, 500)

        // Anwendung des Custom Themes
        MaterialTheme(colors = CoolDarkColorPalette) {
            // Screen-Navigation basierend auf currentScreen
            when (val screen = currentScreen) {
                is Screen.Login -> {
                    LoginScreen { name, accountType ->
                        // Navigation zum Dashboard bei Erfolg
                        currentScreen = Screen.Dashboard(name, accountType)
                    }
                }
                is Screen.Dashboard -> {
                    DashboardScreen(
                        name = screen.name,
                        accountType = screen.accountType
                    ) {
                        // Zurück zum Login bei Logout
                        currentScreen = Screen.Login
                    }
                }
            }
        }
    }
}

/* 
==============================================
ZUSAMMENFASSENDE ERKLÄRUNGEN & ANNOTATIONEN
==============================================

WICHTIGE KONZEPTE:
1. State Hoisting: 
   - Zustände werden in Elternkomponenten gehalten
   - Ermöglicht bessere Wiederverwendbarkeit und Testbarkeit

2. Composable-Funktionen:
   - Beschreiben UI-Zustand als Funktion des States
   - Automatisches Recomposition bei State-Änderungen

3. MaterialTheme:
   - Zentrale Stelle für Design-Einstellungen
   - Konsistente Look & Feel über die ganze App

ERWEITERUNGSMÖGLICHKEITEN:
- Input-Validierung mit Fehlermeldungen
- API-Anbindung für echte Login-Funktionalität
- Animierte Übergänge zwischen Screens
- Persistierung der Login-Daten
- Passwort-Stärke-Anzeige

HÄUFIGE FEHLERQUELLEN:
- Vergessen von .fillMaxWidth() bei TextFields
- Nicht-beachtung der Fenster-Mindestgröße
- Fehlende State-Handling bei RadioButtons
- Falsche ImeAction-Einstellungen bei Tastatur

BEST PRACTICES:
- Farbwerte in zentraler Palette definieren
- States immer mit remember/mutableStateOf verwalten
- Komponenten in kleine wiederverwendbare Teile aufteilen
- Modifier-Reihenfolge beachten (padding vor size etc.)
*/