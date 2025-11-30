package com.jder.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val md_theme_light_primary = Color(0xFF2196F3)
private val md_theme_light_onPrimary = Color(0xFFFFFFFF)
private val md_theme_light_primaryContainer = Color(0xFFBBDEFB)
private val md_theme_light_onPrimaryContainer = Color(0xFF0D47A1)
private val md_theme_light_secondary = Color(0xFF03A9F4)
private val md_theme_light_onSecondary = Color(0xFFFFFFFF)
private val md_theme_light_secondaryContainer = Color(0xFFB3E5FC)
private val md_theme_light_onSecondaryContainer = Color(0xFF01579B)
private val md_theme_light_tertiary = Color(0xFF009688)
private val md_theme_light_onTertiary = Color(0xFFFFFFFF)
private val md_theme_light_tertiaryContainer = Color(0xFFB2DFDB)
private val md_theme_light_onTertiaryContainer = Color(0xFF004D40)
private val md_theme_light_error = Color(0xFFB00020)
private val md_theme_light_errorContainer = Color(0xFFFCD8DF)
private val md_theme_light_onError = Color(0xFFFFFFFF)
private val md_theme_light_onErrorContainer = Color(0xFF8C0009)
private val md_theme_light_background = Color(0xFFFAFAFA)
private val md_theme_light_onBackground = Color(0xFF1A1A1A)
private val md_theme_light_surface = Color(0xFFFFFFFF)
private val md_theme_light_onSurface = Color(0xFF1A1A1A)
private val md_theme_light_surfaceVariant = Color(0xFFE0E0E0)
private val md_theme_light_onSurfaceVariant = Color(0xFF424242)
private val md_theme_light_outline = Color(0xFF757575)
private val md_theme_light_inverseOnSurface = Color(0xFFF5F5F5)
private val md_theme_light_inverseSurface = Color(0xFF2F2F2F)
private val md_theme_light_inversePrimary = Color(0xFF90CAF9)
private val md_theme_dark_primary = Color(0xFF90CAF9)
private val md_theme_dark_onPrimary = Color(0xFF0D47A1)
private val md_theme_dark_primaryContainer = Color(0xFF1565C0)
private val md_theme_dark_onPrimaryContainer = Color(0xFFBBDEFB)
private val md_theme_dark_secondary = Color(0xFF81D4FA)
private val md_theme_dark_onSecondary = Color(0xFF01579B)
private val md_theme_dark_secondaryContainer = Color(0xFF0277BD)
private val md_theme_dark_onSecondaryContainer = Color(0xFFB3E5FC)
private val md_theme_dark_tertiary = Color(0xFF80CBC4)
private val md_theme_dark_onTertiary = Color(0xFF004D40)
private val md_theme_dark_tertiaryContainer = Color(0xFF00695C)
private val md_theme_dark_onTertiaryContainer = Color(0xFFB2DFDB)
private val md_theme_dark_error = Color(0xFFCF6679)
private val md_theme_dark_errorContainer = Color(0xFF93000A)
private val md_theme_dark_onError = Color(0xFF690005)
private val md_theme_dark_onErrorContainer = Color(0xFFFCD8DF)
private val md_theme_dark_background = Color(0xFF121212)
private val md_theme_dark_onBackground = Color(0xFFE0E0E0)
private val md_theme_dark_surface = Color(0xFF1E1E1E)
private val md_theme_dark_onSurface = Color(0xFFE0E0E0)
private val md_theme_dark_surfaceVariant = Color(0xFF424242)
private val md_theme_dark_onSurfaceVariant = Color(0xFFBDBDBD)
private val md_theme_dark_outline = Color(0xFF8D8D8D)
private val md_theme_dark_inverseOnSurface = Color(0xFF1A1A1A)
private val md_theme_dark_inverseSurface = Color(0xFFE0E0E0)
private val md_theme_dark_inversePrimary = Color(0xFF2196F3)
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
)
private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
)
@Composable
fun JDERTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
