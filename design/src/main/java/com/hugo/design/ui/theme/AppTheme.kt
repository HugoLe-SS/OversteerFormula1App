package com.hugo.design.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorScheme = AppColorScheme(
    background = AppColors.background,
    onBackground = AppColors.onBackground,
    primary = AppColors.primary,
    onPrimary = AppColors.onPrimary,
    secondary = AppColors.secondary,
    onSecondary = AppColors.onSecondary,
)

private val lightColorScheme = AppColorScheme(
    background = Color.White,
    onBackground = AppColors.onBackground,
    primary = AppColors.primary,
    onPrimary = AppColors.onPrimary,
    secondary = AppColors.secondary,
    onSecondary = AppColors.onSecondary,
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = F1Font,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    titleNormal =  TextStyle(
        fontFamily = F1Font,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    body =  TextStyle(
        fontFamily = EvaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelLarge =  TextStyle(
        fontFamily = F1Font,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    labelNormal =  TextStyle(
        fontFamily = F1Font,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelSmall =  TextStyle(
        fontFamily = F1Font,
        fontWeight = FontWeight.Thin,
        fontSize = 18.sp
    )
)

private val shape = AppShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50)
)

private val size = AppSize(
    small = 8.dp,
    normal = 12.dp,
    medium = 16.dp,
    large = 24.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = if (isDarkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        LocalIndication provides rippleIndication,
        content = content
    )
}

object AppTheme {

    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current
    val typography: AppTypography
        @Composable get() = LocalAppTypography.current
    val shape: AppShape
        @Composable get() = LocalAppShape.current
    val size: AppSize
        @Composable get() = LocalAppSize.current
}
