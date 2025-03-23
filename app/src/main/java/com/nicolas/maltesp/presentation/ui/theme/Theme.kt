package com.nicolas.maltesp.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(

)

// Esquema de cores para o tema claro utilizando Material3
private val LightColorScheme = lightColorScheme(

    primary = Color(0xFF04427F),      // Botões, campos de texto, etc.
    onPrimary = Color(0xFFFFFFFF),    // Texto/ícones sobre primary

    secondary = Color(0x9F9AC6EA),    // Bar's
    onSecondary = Color(0xFFFFFFFF),  // Texto/ícones sobre secondary

    tertiary = Color(0xFFFBDA6F),     // Drawer?
    onTertiary = Color(0xFFFFFFFF),   // Texto/ícones sobre tertiary
//
//    background = Color(0xFF04427F),
//    onBackground = Color(0xFF1C1B1F),       // Texto/ícones sobre background
//
)

@Composable
fun MaltEspTheme(
    darkTheme: Boolean = false, // Se não quer tema escuro, fixe "false"
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // Opcional, se tiver tipografia customizada
        content = content
    )
}
