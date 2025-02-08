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

//    primary = Color(0xFFD8D8D8),      // Botões, campos de texto, etc.
//    onPrimary = Color(0xFF031E46),    // Texto/ícones sobre primary
//
//    secondary = Color(0xFF096ECA),    // Bar's
//    onSecondary = Color(0xFFD8D8D8),  // Texto/ícones sobre secondary
//
//    //tertiary = Color(0xFF04427F),     // Drawer?
//    //onTertiary = Color(0xFF04427F),   // Texto/ícones sobre tertiary
//
//    background = Color(0xFF04427F),
//    onBackground = Color(0xFF1C1B1F),       // Texto/ícones sobre background
//
//    surface = Color(0xFF04567F),            // background só que mais claro
//    onSurface = Color(0xFF1C1B1F),
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
