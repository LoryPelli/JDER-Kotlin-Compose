package com.jder.ui.utils
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.jder.domain.model.AttributeType
import com.jder.domain.model.ERDiagram
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
fun renderDiagramToBitmap(diagram: ERDiagram): ImageBitmap {
    val padding = 150f
    val entities = diagram.entities
    val relationships = diagram.relationships
    val minX = (entities.minOfOrNull { it.x } ?: 0f).coerceAtMost(
        relationships.minOfOrNull { it.x } ?: 0f
    ) - padding
    val minY = (entities.minOfOrNull { it.y } ?: 0f).coerceAtMost(
        relationships.minOfOrNull { it.y } ?: 0f
    ) - padding
    val maxX = (entities.maxOfOrNull { it.x + it.width } ?: 1000f).coerceAtLeast(
        relationships.maxOfOrNull { it.x + it.width } ?: 1000f
    ) + padding
    val maxY = (entities.maxOfOrNull { it.y + it.height } ?: 1000f).coerceAtLeast(
        relationships.maxOfOrNull { it.y + it.height } ?: 1000f
    ) + padding
    val width = (maxX - minX).toInt().coerceAtLeast(800)
    val height = (maxY - minY).toInt().coerceAtLeast(600)
    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g2d = bufferedImage.createGraphics()
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    g2d.color = Color.WHITE
    g2d.fillRect(0, 0, width, height)
    val offsetX = -minX
    val offsetY = -minY
    g2d.color = Color(0xBDBDBD)
    g2d.stroke = BasicStroke(2f)
    relationships.forEach { relationship ->
        val centerX = (relationship.x + relationship.width / 2 + offsetX).toInt()
        val centerY = (relationship.y + relationship.height / 2 + offsetY).toInt()
        relationship.connections.forEach { conn ->
            val entity = entities.find { it.id == conn.entityId }
            entity?.let {
                val entityCenterX = (it.x + it.width / 2 + offsetX).toInt()
                val entityCenterY = (it.y + it.height / 2 + offsetY).toInt()
                g2d.drawLine(centerX, centerY, entityCenterX, entityCenterY)
                val labelX = (centerX + entityCenterX) / 2
                val labelY = (centerY + entityCenterY) / 2
                g2d.font = Font("Arial", Font.BOLD, 14)
                g2d.color = Color(0x222222)
                g2d.drawString(conn.cardinality.display, labelX - 10, labelY)
                g2d.color = Color(0xBDBDBD)
            }
        }
    }
    g2d.stroke = BasicStroke(2.5f)
    entities.forEach { entity ->
        val x = (entity.x + offsetX).toInt()
        val y = (entity.y + offsetY).toInt()
        val w = entity.width.toInt()
        val h = entity.height.toInt()
        g2d.color = Color.WHITE
        g2d.fillRect(x, y, w, h)
        g2d.color = Color(0x64B5F6)
        g2d.drawRect(x, y, w, h)
        g2d.color = Color.BLACK
        g2d.font = Font("Arial", Font.BOLD, 14)
        val fm = g2d.fontMetrics
        val textWidth = fm.stringWidth(entity.name)
        g2d.drawString(entity.name, x + (w - textWidth) / 2, y + h / 2 + fm.ascent / 2)
        entity.attributes.forEachIndexed { index, attribute ->
            val attrX = (entity.x + entity.width + offsetX + 50).toInt()
            val verticalSpacing = 45
            val offsetY2 = (index - entity.attributes.size / 2f) * verticalSpacing
            val attrY = (entity.y + entity.height / 2 + offsetY + offsetY2).toInt()
            val radius = 20
            g2d.color = Color(0xBDBDBD)
            g2d.drawLine(x + w, y + h / 2, attrX, attrY)
            g2d.color = Color.WHITE
            g2d.fillOval(attrX - radius, attrY - radius, radius * 2, radius * 2)
            val attrColor = when {
                attribute.isPrimaryKey -> Color(0xFFEB3B)
                attribute.type == AttributeType.COMPOSITE -> Color(0xFFA726)
                else -> Color(0x90CAF9)
            }
            g2d.color = attrColor
            g2d.drawOval(attrX - radius, attrY - radius, radius * 2, radius * 2)
            g2d.font = Font("Arial", Font.BOLD, 12)
            g2d.color = Color.BLACK
            g2d.drawString(attribute.name, attrX + radius + 10, attrY + 5)
        }
    }
    relationships.forEach { relationship ->
        val centerX = (relationship.x + relationship.width / 2 + offsetX).toInt()
        val centerY = (relationship.y + relationship.height / 2 + offsetY).toInt()
        val halfWidth = (relationship.width / 2).toInt()
        val halfHeight = (relationship.height / 2).toInt()
        val xPoints = intArrayOf(
            centerX,
            centerX + halfWidth,
            centerX,
            centerX - halfWidth
        )
        val yPoints = intArrayOf(
            centerY - halfHeight,
            centerY,
            centerY + halfHeight,
            centerY
        )
        g2d.color = Color.WHITE
        g2d.fillPolygon(xPoints, yPoints, 4)
        g2d.color = Color(0xE57373)
        g2d.drawPolygon(xPoints, yPoints, 4)
        g2d.color = Color.BLACK
        g2d.font = Font("Arial", Font.BOLD, 14)
        val fm = g2d.fontMetrics
        val textWidth = fm.stringWidth(relationship.name)
        g2d.drawString(relationship.name, centerX - textWidth / 2, centerY + fm.ascent / 2)
    }
    g2d.dispose()
    return bufferedImage.toComposeImageBitmap()
}
