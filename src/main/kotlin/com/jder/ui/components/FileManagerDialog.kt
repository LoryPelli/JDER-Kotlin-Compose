package com.jder.ui.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SdStorage
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.io.File
enum class FileManagerMode {
    SAVE, OPEN
}
data class QuickAccessFolder(
    val name: String,
    val file: File,
    val icon: ImageVector
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileManagerDialog(
    mode: FileManagerMode,
    initialDirectory: File = File(System.getProperty("user.home")),
    fileExtension: String = ".json",
    title: String = if (mode == FileManagerMode.SAVE) "Salva file" else "Apri file",
    onDismiss: () -> Unit,
    onFileSelected: (File) -> Unit
) {
    var currentDirectory by remember { mutableStateOf(initialDirectory) }
    var fileName by remember { mutableStateOf("") }
    var selectedFile by remember { mutableStateOf<File?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showQuickAccessMenu by remember { mutableStateOf(false) }

    val quickAccessFolders = remember {
        buildList {
            val userHome = System.getProperty("user.home")
            add(QuickAccessFolder("Home", File(userHome), Icons.Default.Home))
            File(userHome, "Desktop").takeIf { it.exists() }?.let {
                add(QuickAccessFolder("Desktop", it, Icons.Default.Computer))
            }
            File(userHome, "Documents").takeIf { it.exists() }?.let {
                add(QuickAccessFolder("Documenti", it, Icons.Default.Description))
            }
            File(userHome, "Downloads").takeIf { it.exists() }?.let {
                add(QuickAccessFolder("Download", it, Icons.Default.Download))
            }
            File(userHome, "Pictures").takeIf { it.exists() }?.let {
                add(QuickAccessFolder("Immagini", it, Icons.Default.Image))
            }
            File(userHome, "Music").takeIf { it.exists() }?.let {
                add(QuickAccessFolder("Musica", it, Icons.Default.MusicNote))
            }
            File(userHome, "Videos").takeIf { it.exists() }?.let {
                add(QuickAccessFolder("Video", it, Icons.Default.VideoLibrary))
            }
            if (System.getProperty("os.name").lowercase().contains("win")) {
                add(QuickAccessFolder("Disco C:", File("C:\\"), Icons.Default.SdStorage))
            } else {
                add(QuickAccessFolder("Root", File("/"), Icons.Default.Storage))
            }
        }
    }
    LaunchedEffect(currentDirectory) {
        if (!currentDirectory.exists() || !currentDirectory.isDirectory) {
            currentDirectory = File(System.getProperty("user.home"))
        }
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.width(700.dp).height(600.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            currentDirectory.parentFile?.let { parent ->
                                if (parent.exists() && parent.canRead()) {
                                    currentDirectory = parent
                                    selectedFile = null
                                }
                            }
                        },
                        enabled = currentDirectory.parentFile != null
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Cartella superiore")
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { showQuickAccessMenu = !showQuickAccessMenu },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Folder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = currentDirectory.absolutePath,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                if (showQuickAccessMenu) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = "Menu cartelle rapide"
                            )
                        }

                        DropdownMenu(
                            expanded = showQuickAccessMenu,
                            onDismissRequest = { showQuickAccessMenu = false },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            Text(
                                text = "Accesso rapido",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                            quickAccessFolders.forEach { folder ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Icon(
                                                folder.icon,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp),
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                            Column {
                                                Text(
                                                    text = folder.name,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = folder.file.absolutePath,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        if (folder.file.exists() && folder.file.canRead()) {
                                            currentDirectory = folder.file
                                            selectedFile = null
                                            fileName = ""
                                        }
                                        showQuickAccessMenu = false
                                    }
                                )
                            }
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Folder,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text("Cartella corrente")
                                    }
                                },
                                onClick = { showQuickAccessMenu = false }
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            selectedFile = null
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Aggiorna")
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth()
                ) {
                    val files = remember(currentDirectory) {
                        try {
                            currentDirectory.listFiles()?.sortedWith(
                                compareBy<File> { !it.isDirectory }
                                    .thenBy { it.name.lowercase() }
                            ) ?: emptyList()
                        } catch (_: Exception) {
                            emptyList()
                        }
                    }
                    if (files.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.FolderOpen,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                )
                                Text(
                                    "Cartella vuota",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(files) { file ->
                                FileItem(
                                    file = file,
                                    isSelected = selectedFile == file,
                                    fileExtension = fileExtension,
                                    mode = mode,
                                    onClick = {
                                        if (file.isDirectory) {
                                            if (file.canRead()) {
                                                currentDirectory = file
                                                selectedFile = null
                                                fileName = ""
                                            }
                                        } else {
                                            selectedFile = file
                                            fileName = file.nameWithoutExtension
                                        }
                                    },
                                    onDoubleClick = {
                                        if (file.isDirectory) {
                                            if (file.canRead()) {
                                                currentDirectory = file
                                                selectedFile = null
                                                fileName = ""
                                            }
                                        } else if (mode == FileManagerMode.OPEN && file.extension == fileExtension.removePrefix(".")) {
                                            onFileSelected(file)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                if (mode == FileManagerMode.SAVE) {
                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                    OutlinedTextField(
                        value = fileName,
                        onValueChange = {
                            fileName = it
                            errorMessage = null
                        },
                        label = { Text("Nome file") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            Text(
                                text = fileExtension,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                        },
                        isError = errorMessage != null
                    )
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Annulla")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            when (mode) {
                                FileManagerMode.SAVE -> {
                                    if (fileName.isBlank()) {
                                        errorMessage = "Inserisci un nome per il file"
                                        return@Button
                                    }
                                    val file = File(currentDirectory, "$fileName$fileExtension")
                                    onFileSelected(file)
                                }
                                FileManagerMode.OPEN -> {
                                    selectedFile?.let { file ->
                                        if (file.exists() && file.isFile) {
                                            onFileSelected(file)
                                        }
                                    }
                                }
                            }
                        },
                        enabled = when (mode) {
                            FileManagerMode.SAVE -> fileName.isNotBlank()
                            FileManagerMode.OPEN -> selectedFile != null && selectedFile?.isFile == true
                        }
                    ) {
                        Icon(
                            if (mode == FileManagerMode.SAVE) Icons.Default.Save else Icons.Default.FileOpen,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (mode == FileManagerMode.SAVE) "Salva" else "Apri")
                    }
                }
            }
        }
    }
}
@Composable
private fun FileItem(
    file: File,
    isSelected: Boolean,
    fileExtension: String,
    mode: FileManagerMode,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit
) {
    var clickCount by remember { mutableStateOf(0) }
    var lastClickTime by remember { mutableStateOf(0L) }
    val isValidFile = if (mode == FileManagerMode.OPEN) {
        file.isDirectory || file.extension == fileExtension.removePrefix(".")
    } else {
        true
    }
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }
    val contentColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        !isValidFile -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        else -> MaterialTheme.colorScheme.onSurface
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isValidFile) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < 500 && clickCount == 1) {
                    onDoubleClick()
                } else {
                    onClick()
                }
                clickCount = if (currentTime - lastClickTime < 500 && clickCount == 1) 0 else 1
                lastClickTime = currentTime
            },
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = when {
                    file.isDirectory -> Icons.Default.Folder
                    file.extension == "json" -> Icons.Default.Description
                    file.extension == "png" -> Icons.Default.Image
                    else -> Icons.Default.InsertDriveFile
                },
                contentDescription = null,
                tint = when {
                    file.isDirectory -> MaterialTheme.colorScheme.primary
                    isSelected -> contentColor
                    !isValidFile -> contentColor
                    else -> MaterialTheme.colorScheme.secondary
                },
                modifier = Modifier.size(24.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor,
                    fontWeight = if (file.isDirectory) FontWeight.Medium else FontWeight.Normal
                )

                if (!file.isDirectory) {
                    Text(
                        text = formatFileSize(file.length()),
                        style = MaterialTheme.typography.bodySmall,
                        color = contentColor.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
private fun formatFileSize(bytes: Long): String {
    return when {
        bytes < 1024 -> "$bytes B"
        bytes < 1024 * 1024 -> "${bytes / 1024} KB"
        else -> "${bytes / (1024 * 1024)} MB"
    }
}

