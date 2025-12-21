package com.jder.ui.dialogs
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun NotePropertiesDialog(
    noteText: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(noteText) }
    val noteWidth = 200f
    val noteHeight = 150f
    val padding = 10f
    val availableWidth = (noteWidth - padding * 2).toInt()
    val availableHeight = (noteHeight - padding * 2).toInt()
    val maxCharacters = remember {
        val avgCharWidth = 7
        val lineHeight = 16
        val maxCharsPerLine = availableWidth / avgCharWidth
        val maxLines = availableHeight / lineHeight
        (maxCharsPerLine * maxLines).coerceAtLeast(50)
    }
    val remainingChars = maxCharacters - text.length
    val isOverLimit = remainingChars < 0
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Proprietà Nota") },
        text = {
            Box {
                Column {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                        },
                        label = { Text("Testo") },
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        maxLines = 10
                    )
                }
                Text(
                    text = remainingChars.toString(),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 8.dp, bottom = 8.dp),
                    color = if (isOverLimit) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(text)
                    onDismiss()
                },
                enabled = !isOverLimit
            ) {
                Text("Conferma")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}
