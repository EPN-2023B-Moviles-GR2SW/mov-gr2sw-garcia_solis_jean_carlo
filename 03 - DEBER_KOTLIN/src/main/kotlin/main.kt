
import java.io.File


fun main() {
    val fieldSystem = FieldSystem()
    val wellSystem = WellSystem()

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
                    id = fieldSystem.fields.size + 1,
                    name = name,
                    date = date,
                    isActive = isActive,
                    area = area
                )
                fieldSystem.createField(field)
            }
            2 -> {
                print("Enter Field ID to delete: ")
                val fieldId = readLine()?.toIntOrNull() ?: continue
                fieldSystem.deleteField(fieldId)
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
                fieldSystem.updateField(fieldId, newName, newDate, newIsActive, newArea)
            }
            4 -> fieldSystem.listFields()
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
                    id = wellSystem.wells.size + 1,
                    name = name,
                    date = date,
                    depth = depth,
                    fieldName = fieldName
                )
                wellSystem.createWell(well, fieldName)
            }
            6 -> {
                print("Enter Well ID to delete: ")
                val wellId = readLine()?.toIntOrNull() ?: continue
                wellSystem.deleteWell(wellId)
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
                wellSystem.updateWell(wellId, newName, newDate, newDepth, newFieldName)
            }
            8 -> wellSystem.listWells()
            0 -> break
            else -> println("Invalid option. Please try again.")
        }
    }
}


