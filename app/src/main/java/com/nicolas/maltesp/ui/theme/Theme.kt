package com.nicolas.maltesp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(

)

// Esquema de cores para o tema claro utilizando Material3
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryAmber,
    tertiary = TertiaryDeepAmber,
    background = BackgroundLight,
    surface = SurfaceWhite,
    onPrimary = Color.White,               // Texto/ícones sobre primary
    onSecondary = Color.Black,             // Texto/ícones sobre secondary
    onTertiary = Color.Black,              // Texto/ícones sobre tertiary
    onBackground = Color(0xFF1C1B1F),       // Texto/ícones sobre background
    onSurface = Color(0xFF1C1B1F)           // Texto/ícones sobre surface
)

@Composable
fun MaltEspTheme(
    darkTheme: Boolean = false, // Se não quer tema escuro, fixe "false"
    dynamicColor: Boolean = false, // Desativa cores dinâmicas (Android 12+)
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // Opcional, se tiver tipografia customizada
        content = content
    )
}
