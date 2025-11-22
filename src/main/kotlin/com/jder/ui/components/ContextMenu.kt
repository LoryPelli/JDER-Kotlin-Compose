package com.jder.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
@Composable
fun ContextMenu(
    position: Offset,
    isEntity: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onAddAttribute: () -> Unit,
    onAddConnection: (() -> Unit)? = null
) {
    Popup(
        alignment = androidx.compose.ui.Alignment.TopStart,
        offset = IntOffset(position.x.toInt(), position.y.toInt()),
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = true)
    ) {
        Surface(
            modifier = Modifier.width(220.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = if (isEntity) "Azioni Entità" else "Azioni Relazione",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Divider()
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(Icons.Default.Edit, null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(12.dp))
                            Text("Modifica Proprietà")
                        }
                    },
                    onClick = onEdit,
                    leadingIcon = null
                )
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(12.dp))
                            Text("Aggiungi Attributo")
                        }
                    },
                    onClick = onAddAttribute,
                    leadingIcon = null
                )
                if (onAddConnection != null) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                Icon(Icons.Default.Link, null, modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(12.dp))
                                Text("Aggiungi Connessione")
                            }
                        },
                        onClick = onAddConnection,
                        leadingIcon = null
                    )
                }
                Divider()
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Delete,
                                null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Elimina",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onClick = onDelete,
                    leadingIcon = null
                )
            }
        }
    }
}
