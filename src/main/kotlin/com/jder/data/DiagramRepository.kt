package com.jder.data
import com.jder.domain.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
class DiagramRepository {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    fun saveDiagram(diagram: ERDiagram, file: File): Result<Unit> {
        return try {
            val jsonString = json.encodeToString(diagram)
            file.writeText(jsonString)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    fun loadDiagram(file: File): Result<ERDiagram> {
        return try {
            val jsonString = file.readText()
            val diagram = json.decodeFromString<ERDiagram>(jsonString)
            Result.success(diagram)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
