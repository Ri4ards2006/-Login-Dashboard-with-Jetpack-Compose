import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog

val DarkThemeColors = darkColorScheme(
    primary = Color(0xFF00C853),
    secondary = Color(0xFF69F0AE),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

@Composable
fun CoolIDE() {
    var currentScreen by remember { mutableStateOf(Screen.EDITOR) }
    var dialogOpen by remember { mutableStateOf(false) }
    var titleClicks by remember { mutableStateOf(0) }

    MaterialTheme(colorScheme = DarkThemeColors) {
        Scaffold(
            topBar = { CoolTopBar(
                onTitleClick = {
                    titleClicks++
                    if(titleClicks % 5 == 0) dialogOpen = true
                }
            )},
            content = { innerPadding ->
                Box(Modifier.padding(innerPadding)) {
                    Row {
                        NavigationRail(
                            modifier = Modifier.fillMaxHeight(),
                            header = { /* Optional header */ }
                        ) {
                            Screen.values().forEach { screen ->
                                NavigationRailItem(
                                    icon = { Icon(screen.icon, "") },
                                    label = { Text(screen.title) },
                                    selected = currentScreen == screen,
                                    onClick = { currentScreen = screen }
                                )
                            }
                        }

                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = { fadeIn() with fadeOut() }
                        ) { screen ->
                            when(screen) {
                                Screen.EDITOR -> EditorScreen()
                                Screen.TERMINAL -> TerminalScreen()
                                Screen.PROJECT -> ProjectScreen()
                            }
                        }
                    }

                    if(dialogOpen) {
                        EasterEggDialog { dialogOpen = false }
                    }
                }
            }
        )
    }
}

@Composable
fun CoolTopBar(onTitleClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Text(
            text = "IntelliJ Genius IDE",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(rotationZ = if (angle < 180) angle else 0f)
                .clickable { onTitleClick() }
        )
    }
}

@Composable
fun EditorScreen() {
    var code by remember { mutableStateOf("fun main() {\n    println(\"Hello Matrix!\")\n}") }

    Row(Modifier.padding(16.dp)) {
        Column(Modifier.width(40.dp)) {
            repeat(code.lines().size) { index ->
                Text(
                    "${index + 1}",
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        BasicTextField(
            value = code,
            onValueChange = { code = it },
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF262626))
                .padding(8.dp)
        )
    }
}

@Composable
fun TerminalScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column {
            Text("Welcome to Genius Terminal", color = DarkThemeColors.primary)
            Spacer(Modifier.height(8.dp))
            Text("> git commit -m 'Initial awesome commit'", color = Color.White)
        }
    }
}

@Composable
fun ProjectScreen() {
    val projectStructure = listOf(
        "src/main/kotlin" to listOf("Main.kt", "Utils.kt"),
        "build.gradle.kts" to emptyList()
    )

    Column(Modifier.padding(16.dp)) {
        projectStructure.forEach { (name, files) ->
            FileTreeItem(name, isFolder = true)
            files.forEach { fileName ->
                FileTreeItem(fileName, isFolder = false)
            }
        }
    }
}

@Composable
fun FileTreeItem(name: String, isFolder: Boolean) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp)
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (isFolder) Icons.Default.Folder else Icons.Default.InsertDriveFile,
            "",
            tint = if (isFolder) DarkThemeColors.primary else Color.Gray
        )
        Text(name, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun EasterEggDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(DarkThemeColors.surface)
                .border(2.dp, DarkThemeColors.primary),
            contentAlignment = Alignment.Center
        ) {
            Text("YOU FOUND THE SECRET!", color = DarkThemeColors.primary)
        }
    }
}

enum class Screen(val title: String, val icon: ImageVector) {
    EDITOR("Editor", Icons.Default.Code TERMINAL("Terminal", Icons.Default.Terminal),
    Icons.Default.Folder)
}

// Preview
@Preview
@Composable
fun PreviewCoolIDE():

        1. Eine dunkle Material-3 Theme mit grünen Akzenten
2. Animierte Titelleiste mit rotierendem Text
3. Drei Hauptbereiche:
- Code-Editor mit Zeilennummern
- Terminal-Simulation
- Projektbaum-Struktur
4. Secret Easter Egg Dialog nach 5 Klicks auf den Titel
5. Fließende Animationen beim Wechsel zwischen den Bereichen
6. Modernes NavigationRail-Menü
7. IDE-typische Icons und Monospace-Schriftarten
8. Responsive Layout mit sauberen Abständen

Die Oberfläche ist interaktiv genug, um realistisch zu wirken, aber nicht überladen. Du kannst die Farben, Animationen und Inhalte leicht anpassen. Das Terminal und der Editor sind aktuell nur zur Demonstration - du könntest sie natürlich um echte Funktionalität erweitern.