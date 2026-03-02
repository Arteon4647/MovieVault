package com.example.movievault.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movievault.presentation.theme.MovieVaultTheme

@Composable
fun PrimaryMovieButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    val colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Button(
        onClick = onClick,
        colors = colors,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun SecondaryMovieButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    val colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Button(
        onClick = onClick,
        colors = colors,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

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

@ScreenSizesPreview
@Composable
fun MovieButtonsPreview() {
    MovieVaultTheme() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryMovieButton(
                text = "Primary Button",
                onClick = {}
            )
            SecondaryMovieButton(
                text = "Secondary Button",
                onClick = {}
            )
        }
    }
}