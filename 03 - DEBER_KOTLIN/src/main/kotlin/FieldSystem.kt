// FieldSystem.kt

import java.io.File

data class Field(val id: Int, var name: String, var date: String, var isActive: Boolean, var area: Double)

class FieldSystem(val fields: MutableList<Field> = mutableListOf()) {

    private val fieldsFile = File("C:/Users/JANKA312/Desktop/fields.txt")

    fun createField(field: Field) {
        fields.add(field)
        saveFieldsToFile()
    }

    fun deleteField(fieldId: Int) {
        fields.removeIf { it.id == fieldId }
        saveFieldsToFile()
    }

    fun updateField(fieldId: Int, newName: String, newDate: String, newIsActive: Boolean, newArea: Double) {
        val field = fields.find { it.id == fieldId }
        field?.apply {
            name = newName
            date = newDate
            isActive = newIsActive
            area = newArea
        }
        saveFieldsToFile()
    }

    fun listFields() {
        fields.forEach { println(it) }
    }

    private fun saveFieldsToFile() {
        fieldsFile.bufferedWriter().use { out ->
            fields.forEach {
                out.write("${it.id},${it.name},${it.date},${it.isActive},${it.area}\n")
            }
        }
    }

    init {
        // Load existing fields from the file
        if (fieldsFile.exists()) {
            fieldsFile.bufferedReader().useLines { lines ->
                lines.forEach {
                    val parts = it.split(",")
                    val field = Field(parts[0].toInt(), parts[1], parts[2], parts[3].toBoolean(), parts[4].toDouble())
                    fields.add(field)
                }
            }
        }
    }
}
