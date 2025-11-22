package com.jder.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jder.domain.model.DiagramState
import com.jder.domain.model.ToolMode
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagramToolbar(
    state: DiagramState,
    onNewDiagram: () -> Unit,
    onOpenDiagram: () -> Unit,
    onSaveDiagram: () -> Unit,
    onSaveAsDiagram: () -> Unit,
    onExportPNG: () -> Unit,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onResetZoom: () -> Unit,
    onUndo: () -> Unit = {},
    onRedo: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val title by remember {
        derivedStateOf {
            buildString {
                append("JDER - ")
                append(state.currentFile?.substringAfterLast("\\") ?: state.diagram.name)
                if (state.isModified) append(" *")
            }
        }
    }
    val zoomPercentage by remember {
        derivedStateOf {
            "${(state.zoom * 100).toInt()}%"
        }
    }
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text(text = title)
            },
            actions = {
                var showFileMenu by remember { mutableStateOf(false) }
                var showExportMenu by remember { mutableStateOf(false) }
                var showViewMenu by remember { mutableStateOf(false) }
                Box {
                    TextButton(onClick = { showFileMenu = true }) {
                        Text("File")
                    }
                    DropdownMenu(
                        expanded = showFileMenu,
                        onDismissRequest = { showFileMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Nuovo Diagramma") },
                            onClick = {
                                onNewDiagram()
                                showFileMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.Add, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Apri Diagramma...") },
                            onClick = {
                                onOpenDiagram()
                                showFileMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.FolderOpen, null) }
                        )
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Salva") },
                            onClick = {
                                onSaveDiagram()
                                showFileMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.Save, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Salva Come...") },
                            onClick = {
                                onSaveAsDiagram()
                                showFileMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.SaveAs, null) }
                        )
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Elimina Elemento Selezionato") },
                            onClick = {
                                when {
                                    state.selectedEntityId != null ->
                                        state.deleteEntity(state.selectedEntityId!!)
                                    state.selectedRelationshipId != null ->
                                        state.deleteRelationship(state.selectedRelationshipId!!)
                                }
                                showFileMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.Delete, null) },
                            enabled = state.selectedEntityId != null || state.selectedRelationshipId != null
                        )
                    }
                }
                Box {
                    TextButton(onClick = { showExportMenu = true }) {
                        Text("Esporta")
                    }
                    DropdownMenu(
                        expanded = showExportMenu,
                        onDismissRequest = { showExportMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Esporta come PNG...") },
                            onClick = {
                                onExportPNG()
                                showExportMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.Image, "Esporta diagramma come immagine PNG") }
                        )
                    }
                }
                Box {
                    TextButton(onClick = { showViewMenu = true }) {
                        Text("Vista")
                    }
                    DropdownMenu(
                        expanded = showViewMenu,
                        onDismissRequest = { showViewMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Zoom In") },
                            onClick = {
                                onZoomIn()
                                showViewMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.ZoomIn, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Zoom Out") },
                            onClick = {
                                onZoomOut()
                                showViewMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.ZoomOut, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Reset Zoom") },
                            onClick = {
                                onResetZoom()
                                showViewMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.CenterFocusWeak, null) }
                        )
                    }
                }
            }
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconToggleButton(
                    checked = state.toolMode == ToolMode.SELECT,
                    onCheckedChange = { state.toolMode = ToolMode.SELECT }
                ) {
                    Icon(Icons.Default.NearMe, "Seleziona e Sposta")
                }
                Divider(modifier = Modifier.width(1.dp).height(40.dp))
                IconButton(
                    onClick = onUndo,
                    enabled = state.canUndo()
                ) {
                    Icon(Icons.Default.Undo, "Annulla (Ctrl+Z)")
                }
                IconButton(
                    onClick = onRedo,
                    enabled = state.canRedo()
                ) {
                    Icon(Icons.Default.Redo, "Ripristina (Ctrl+Y)")
                }
                Divider(modifier = Modifier.width(1.dp).height(40.dp))
                IconToggleButton(
                    checked = state.toolMode == ToolMode.ENTITY,
                    onCheckedChange = { state.toolMode = ToolMode.ENTITY }
                ) {
                    Icon(CustomIcons.Rectangle, "Crea Entità")
                }
                IconToggleButton(
                    checked = state.toolMode == ToolMode.RELATIONSHIP,
                    onCheckedChange = { state.toolMode = ToolMode.RELATIONSHIP }
                ) {
                    Icon(CustomIcons.Diamond, "Crea Relazione")
                }
                Divider(modifier = Modifier.width(1.dp).height(40.dp))
                IconButton(
                    onClick = onSaveDiagram,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(Icons.Default.Save, "Salva")
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = zoomPercentage,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp).align(androidx.compose.ui.Alignment.CenterVertically)
                )
                IconButton(onClick = onZoomOut) {
                    Icon(Icons.Default.ZoomOut, "Zoom Out")
                }
                IconButton(onClick = onResetZoom) {
                    Icon(Icons.Default.CenterFocusWeak, "Reset Zoom")
                }
                IconButton(onClick = onZoomIn) {
                    Icon(Icons.Default.ZoomIn, "Zoom In")
                }
            }
        }
    }
}
