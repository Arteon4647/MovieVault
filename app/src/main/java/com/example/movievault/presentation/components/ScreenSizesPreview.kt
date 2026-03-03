package com.example.movievault.presentation.components

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
@Preview(
    group = "Screen Size",
    name = "Small Light",
    device = "id:Galaxy Nexus",
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    group = "Screen Size",
    name = "Small Dark",
    device = "id:Galaxy Nexus",
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Screen Size",
    name = "Medium Light",
    device = "id:pixel_2",
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    group = "Screen Size",
    name = "Medium Dark",
    device = "id:pixel_2",
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Screen Size",
    name = "Large Light",
    device = "id:pixel_8",
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    group = "Screen Size",
    name = "Large Dark",
    device = "id:pixel_8",
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ScreenSizesPreview

