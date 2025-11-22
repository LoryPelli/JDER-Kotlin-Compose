@file:OptIn(ExperimentalMaterial3Api::class)
package com.jder.ui.dialogs
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jder.domain.model.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityPropertiesDialog(
    entity: Entity,
    onDismiss: () -> Unit,
    onSave: (Entity) -> Unit
) {
    var name by remember { mutableStateOf(entity.name) }
    var isWeak by remember { mutableStateOf(entity.isWeak) }
    var documentation by remember { mutableStateOf(entity.documentation) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Proprietà Entità") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Entità Debole")
                    Switch(
                        checked = isWeak,
                        onCheckedChange = { isWeak = it }
                    )
                }
                OutlinedTextField(
                    value = documentation,
                    onValueChange = { documentation = it },
                    label = { Text("Documentazione") },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(entity.copy(
                        name = name,
                        isWeak = isWeak,
                        documentation = documentation
                    ))
                }
            ) {
                Text("Salva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}
@Composable
fun RelationshipPropertiesDialog(
    relationship: Relationship,
    onDismiss: () -> Unit,
    onSave: (Relationship) -> Unit
) {
    var name by remember { mutableStateOf(relationship.name) }
    var documentation by remember { mutableStateOf(relationship.documentation) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Proprietà Relazione") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = documentation,
                    onValueChange = { documentation = it },
                    label = { Text("Documentazione") },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(relationship.copy(
                        name = name,
                        documentation = documentation
                    ))
                }
            ) {
                Text("Salva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}
@Composable
fun AddAttributeDialog(
    onDismiss: () -> Unit,
    onAdd: (Attribute) -> Unit
) {
    AttributeDialog(
        title = "Aggiungi Attributo",
        initialAttribute = null,
        onDismiss = onDismiss,
        onConfirm = onAdd
    )
}
@Composable
fun EditAttributeDialog(
    attribute: Attribute,
    onDismiss: () -> Unit,
    onSave: (Attribute) -> Unit
) {
    AttributeDialog(
        title = "Modifica Attributo",
        initialAttribute = attribute,
        onDismiss = onDismiss,
        onConfirm = onSave
    )
}
@Composable
private fun AttributeDialog(
    title: String,
    initialAttribute: Attribute?,
    onDismiss: () -> Unit,
    onConfirm: (Attribute) -> Unit
) {
    var name by remember { mutableStateOf(initialAttribute?.name ?: "") }
    var isPrimaryKey by remember { mutableStateOf(initialAttribute?.isPrimaryKey ?: false) }
    var attributeType by remember { mutableStateOf(initialAttribute?.type ?: AttributeType.NORMAL) }
    var multiplicity by remember { mutableStateOf(initialAttribute?.multiplicity ?: "") }
    var components by remember { mutableStateOf(initialAttribute?.components ?: emptyList()) }
    var expanded by remember { mutableStateOf(false) }
    var showAddComponentDialog by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text("Chiave Primaria")
                    Switch(
                        checked = isPrimaryKey,
                        onCheckedChange = { isPrimaryKey = it },
                        enabled = attributeType != AttributeType.KEY // Disabilita se tipo è KEY
                    )
                }
                if (attributeType == AttributeType.KEY) {
                    Text(
                        text = "Gli attributi di tipo Chiave sono sempre chiavi primarie",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = when (attributeType) {
                            AttributeType.NORMAL -> "Normale"
                            AttributeType.KEY -> "Chiave"
                            AttributeType.MULTIVALUED -> "Multivalore"
                            AttributeType.DERIVED -> "Derivato"
                            AttributeType.COMPOSITE -> "Composto"
                        },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Normale") },
                            onClick = {
                                attributeType = AttributeType.NORMAL
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Chiave") },
                            onClick = {
                                attributeType = AttributeType.KEY
                                isPrimaryKey = true
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Multivalore") },
                            onClick = {
                                attributeType = AttributeType.MULTIVALUED
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Derivato") },
                            onClick = {
                                attributeType = AttributeType.DERIVED
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Composto") },
                            onClick = {
                                attributeType = AttributeType.COMPOSITE
                                isPrimaryKey = false
                                expanded = false
                            }
                        )
                    }
                }
                if (attributeType == AttributeType.MULTIVALUED) {
                    OutlinedTextField(
                        value = multiplicity,
                        onValueChange = { multiplicity = it },
                        label = { Text("Molteplicità (es: 1..N, 0..N)") },
                        placeholder = { Text("1..N") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (attributeType == AttributeType.COMPOSITE) {
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Text(
                            "Componenti (${components.size})",
                            style = MaterialTheme.typography.titleSmall
                        )
                        IconButton(onClick = { showAddComponentDialog = true }) {
                            Icon(Icons.Default.Add, "Aggiungi componente")
                        }
                    }
                    if (components.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            components.forEach { component ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                                    ) {
                                        Text(
                                            component.name,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        IconButton(
                                            onClick = {
                                                components = components.filter { it.id != component.id }
                                            }
                                        ) {
                                            Icon(
                                                Icons.Default.Delete,
                                                "Rimuovi componente",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            "Nessun componente. Aggiungi componenti per l'attributo composto.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val finalIsPrimaryKey = attributeType == AttributeType.KEY || isPrimaryKey
                        val newAttribute = Attribute(
                            id = initialAttribute?.id ?: java.util.UUID.randomUUID().toString(),
                            name = name,
                            type = attributeType,
                            isPrimaryKey = finalIsPrimaryKey,
                            components = if (attributeType == AttributeType.COMPOSITE) components else emptyList(),
                            multiplicity = if (attributeType == AttributeType.MULTIVALUED) multiplicity else ""
                        )
                        onConfirm(newAttribute)
                    }
                },
                enabled = name.isNotBlank() && (attributeType != AttributeType.COMPOSITE || components.isNotEmpty())
            ) {
                Text(if (initialAttribute != null) "Salva" else "Aggiungi")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
    if (showAddComponentDialog) {
        var componentName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddComponentDialog = false },
            title = { Text("Aggiungi Componente") },
            text = {
                OutlinedTextField(
                    value = componentName,
                    onValueChange = { componentName = it },
                    label = { Text("Nome componente") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (componentName.isNotBlank()) {
                            val newComponent = Attribute(
                                id = java.util.UUID.randomUUID().toString(),
                                name = componentName,
                                type = AttributeType.NORMAL,
                                isPrimaryKey = false,
                                components = emptyList()
                            )
                            components = components + newComponent
                            showAddComponentDialog = false
                        }
                    },
                    enabled = componentName.isNotBlank()
                ) {
                    Text("Aggiungi")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddComponentDialog = false }) {
                    Text("Annulla")
                }
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateConnectionDialog(
    entities: List<Entity>,
    onDismiss: () -> Unit,
    onCreate: (String, Cardinality) -> Unit
) {
    ConnectionDialog(
        title = "Crea Connessione",
        entities = entities,
        initialEntityId = null,
        initialCardinality = Cardinality.ONE,
        onDismiss = onDismiss,
        onConfirm = onCreate
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditConnectionDialog(
    entities: List<Entity>,
    currentEntityId: String,
    currentCardinality: Cardinality,
    onDismiss: () -> Unit,
    onSave: (String, Cardinality) -> Unit
) {
    ConnectionDialog(
        title = "Modifica Connessione",
        entities = entities,
        initialEntityId = currentEntityId,
        initialCardinality = currentCardinality,
        onDismiss = onDismiss,
        onConfirm = onSave
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConnectionDialog(
    title: String,
    entities: List<Entity>,
    initialEntityId: String?,
    initialCardinality: Cardinality,
    onDismiss: () -> Unit,
    onConfirm: (String, Cardinality) -> Unit
) {
    var selectedEntity by remember { mutableStateOf(entities.find { it.id == initialEntityId }) }
    var selectedCardinality by remember { mutableStateOf(initialCardinality) }
    var expandedEntity by remember { mutableStateOf(false) }
    var expandedCardinality by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedEntity,
                    onExpandedChange = { expandedEntity = it }
                ) {
                    OutlinedTextField(
                        value = selectedEntity?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Entità") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEntity) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEntity,
                        onDismissRequest = { expandedEntity = false }
                    ) {
                        entities.forEach { entity ->
                            DropdownMenuItem(
                                text = { Text(entity.name) },
                                onClick = {
                                    selectedEntity = entity
                                    expandedEntity = false
                                }
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = expandedCardinality,
                    onExpandedChange = { expandedCardinality = it }
                ) {
                    OutlinedTextField(
                        value = selectedCardinality.display,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Cardinalità") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCardinality) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCardinality,
                        onDismissRequest = { expandedCardinality = false }
                    ) {
                        Cardinality.values().forEach { cardinality ->
                            DropdownMenuItem(
                                text = { Text(cardinality.display) },
                                onClick = {
                                    selectedCardinality = cardinality
                                    expandedCardinality = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedEntity?.let { entity ->
                        onConfirm(entity.id, selectedCardinality)
                    }
                },
                enabled = selectedEntity != null
            ) {
                Text(if (initialEntityId != null) "Salva" else "Crea")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}
