package com.example.movievault.presentation.components

import androidx.compose.foundation.layout.Column
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

@Composable
private fun PreviewWrapper(darkTheme: Boolean, content: @Composable () -> Unit){
    MovieVaultTheme(darkTheme = darkTheme) {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Composable
fun ButtonPreviewLight(){
    PreviewWrapper(darkTheme = false) {
        PrimaryMovieButton(text = "Primary", onClick = {})
        SecondaryMovieButton(text = "Secondary", onClick = {})
    }
}

@Preview(name = "Dark", showBackground = true)
@Composable
fun ButtonPreviewDark(){
    PreviewWrapper(darkTheme = true) {
        PrimaryMovieButton(text = "Primary", onClick = {})
        SecondaryMovieButton(text = "Secondary", onClick = {})
    }
}
