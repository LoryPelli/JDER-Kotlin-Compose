package com.jder.data
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.EncodedImageFormat
import java.io.File
class ImageExporter {
    fun exportToPNG(imageBitmap: ImageBitmap, file: File): Result<Unit> {
        return try {
            val skiaBitmap = imageBitmap.asSkiaBitmap()
            val image = org.jetbrains.skia.Image.makeFromBitmap(skiaBitmap)
            val data = image.encodeToData(EncodedImageFormat.PNG)
            if (data != null) {
                file.writeBytes(data.bytes)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Impossibile codificare l'immagine"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
