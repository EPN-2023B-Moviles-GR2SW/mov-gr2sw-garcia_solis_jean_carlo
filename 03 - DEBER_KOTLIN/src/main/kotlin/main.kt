
import java.io.File

data class Field(val id: Int, var name: String, var date: String, var isActive: Boolean, var area: Double)

data class Well(val id: Int, var name: String, var date: String, var depth: Int, var fieldName: String)

class FieldManager(val fields: MutableList<Field> = mutableListOf()) {

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
        val file = File("C:/Users/JANKA312/Desktop/fields.txt")
        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach {
                    val parts = it.split(",")
                    val field = Field(parts[0].toInt(), parts[1], parts[2], parts[3].toBoolean(), parts[4].toDouble())
                    fields.add(field)
                }
            }
        }
    }
}

class WellManager(val wells: MutableList<Well> = mutableListOf()) {

    private val wellsFile = File("C:/Users/JANKA312/Desktop/wells.txt")

    fun createWell(well: Well, fieldName: String) {
        well.fieldName = fieldName
        wells.add(well)
        saveWellsToFile()
    }

    fun deleteWell(wellId: Int) {
        wells.removeIf { it.id == wellId }
        saveWellsToFile()
    }

    fun updateWell(wellId: Int, newName: String, newDate: String, newDepth: Int, newFieldName: String) {
        val well = wells.find { it.id == wellId }
        well?.apply {
            name = newName
            date = newDate
            depth = newDepth
            fieldName = newFieldName
        }
        saveWellsToFile()
    }

    fun listWells() {
        wells.forEach { println(it) }
    }

    private fun saveWellsToFile() {
        wellsFile.bufferedWriter().use { out ->
            wells.forEach {
                out.write("${it.id},${it.name},${it.date},${it.depth},${it.fieldName}\n")
            }
        }
    }

    init {
        // Load existing wells from the file
        val file = File("C:/Users/JANKA312/Desktop/wells.txt")
        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach {
                    val parts = it.split(",")
                    val well = Well(parts[0].toInt(), parts[1], parts[2], parts[3].toInt(), parts[4])
                    wells.add(well)
                }
            }
        }
    }
}

fun main() {
    val fieldManager = FieldManager()
    val wellManager = WellManager()

    while (true) {
        println("Choose an option:")
        println("1. Create Field")
        println("2. Delete Field")
        println("3. Update Field")
        println("4. List Fields")
        println("5. Create Well")
        println("6. Delete Well")
        println("7. Update Well")
        println("8. List Wells")
        println("0. Exit")

        when (readLine()?.toIntOrNull() ?: -1) {
            1 -> {
                print("Enter Field name: ")
                val name = readLine() ?: ""
                print("Enter Field date: ")
                val date = readLine() ?: ""
                print("Enter Field isActive (true/false): ")
                val isActive = readLine()?.toBoolean() ?: false
                print("Enter Field area: ")
                val area = readLine()?.toDoubleOrNull() ?: 0.0
                val field = Field(
                    id = fieldManager.fields.size + 1,
                    name = name,
                    date = date,
                    isActive = isActive,
                    area = area
                )
                fieldManager.createField(field)
            }
            2 -> {
                print("Enter Field ID to delete: ")
                val fieldId = readLine()?.toIntOrNull() ?: continue
                fieldManager.deleteField(fieldId)
            }
            3 -> {
                print("Enter Field ID to update: ")
                val fieldId = readLine()?.toIntOrNull() ?: continue
                print("Enter new name: ")
                val newName = readLine() ?: ""
                print("Enter new date: ")
                val newDate = readLine() ?: ""
                print("Enter new isActive (true/false): ")
                val newIsActive = readLine()?.toBoolean() ?: false
                print("Enter new area: ")
                val newArea = readLine()?.toDoubleOrNull() ?: 0.0
                fieldManager.updateField(fieldId, newName, newDate, newIsActive, newArea)
            }
            4 -> fieldManager.listFields()
            5 -> {
                print("Enter Well name: ")
                val name = readLine() ?: ""
                print("Enter Well date: ")
                val date = readLine() ?: ""
                print("Enter Well depth: ")
                val depth = readLine()?.toIntOrNull() ?: 0
                print("Enter Well field name: ")
                val fieldName = readLine() ?: ""
                val well = Well(
                    id = wellManager.wells.size + 1,
                    name = name,
                    date = date,
                    depth = depth,
                    fieldName = fieldName
                )
                wellManager.createWell(well, fieldName)
            }
            6 -> {
                print("Enter Well ID to delete: ")
                val wellId = readLine()?.toIntOrNull() ?: continue
                wellManager.deleteWell(wellId)
            }
            7 -> {
                print("Enter Well ID to update: ")
                val wellId = readLine()?.toIntOrNull() ?: continue
                print("Enter new name: ")
                val newName = readLine() ?: ""
                print("Enter new date: ")
                val newDate = readLine() ?: ""
                print("Enter new depth: ")
                val newDepth = readLine()?.toIntOrNull() ?: 0
                print("Enter new field ID: ")
                val newFieldName = readLine() ?: " "
                wellManager.updateWell(wellId, newName, newDate, newDepth, newFieldName)
            }
            8 -> wellManager.listWells()
            0 -> break
            else -> println("Invalid option. Please try again.")
        }
    }
}

