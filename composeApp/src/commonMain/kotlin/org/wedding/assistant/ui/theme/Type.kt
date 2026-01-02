package org.wedding.assistant.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import weddingassistant.composeapp.generated.resources.Res
import weddingassistant.composeapp.generated.resources.inter_medium
import weddingassistant.composeapp.generated.resources.inter_regular
import weddingassistant.composeapp.generated.resources.inter_semibold


@Composable
fun rememberInterFamily(): FontFamily {
    val regular = Font(Res.font.inter_regular, weight = FontWeight.Normal)
    val medium = Font(Res.font.inter_medium, weight = FontWeight.Medium)
    val semiBold = Font(Res.font.inter_semibold, weight = FontWeight.SemiBold)

    return remember(regular, medium, semiBold) {
        FontFamily(regular, medium, semiBold)
    }
}

@Composable
fun rememberAppTypography(): Typography {
    val inter = rememberInterFamily()
    val base = remember { Typography() }

    return remember(inter) {
        Typography(
            displayLarge = base.displayLarge.copy(fontFamily = inter),
            displayMedium = base.displayMedium.copy(fontFamily = inter),
            displaySmall = base.displaySmall.copy(fontFamily = inter),
            headlineLarge = base.headlineLarge.copy(fontFamily = inter),
            headlineMedium = base.headlineMedium.copy(fontFamily = inter),
            headlineSmall = base.headlineSmall.copy(fontFamily = inter),
            titleLarge = base.titleLarge.copy(fontFamily = inter),
            titleMedium = base.titleMedium.copy(fontFamily = inter),
            titleSmall = base.titleSmall.copy(fontFamily = inter),
            bodyLarge = base.bodyLarge.copy(fontFamily = inter),
            bodyMedium = base.bodyMedium.copy(fontFamily = inter),
            bodySmall = base.bodySmall.copy(fontFamily = inter),
            labelLarge = base.labelLarge.copy(fontFamily = inter),
            labelMedium = base.labelMedium.copy(fontFamily = inter),
            labelSmall = base.labelSmall.copy(fontFamily = inter),
        )
    }
}