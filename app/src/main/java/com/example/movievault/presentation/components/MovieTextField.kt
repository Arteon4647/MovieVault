package com.example.movievault.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movievault.presentation.theme.MovieVaultTheme

@Composable
fun MovieTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            cursorColor = MaterialTheme.colorScheme.tertiary
        )
    )
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
fun TextFiledPreviewLight(){
    PreviewWrapper(darkTheme = false) {
        MovieTextField(value = "", onValueChange = {}, label = "Search")
    }
}

@Preview(name = "Dark", showBackground = true)
@Composable
fun TextFieldPreviewDark(){
    PreviewWrapper(darkTheme = true) {
        MovieTextField(value = "", onValueChange = {}, label = "Search")
    }
}