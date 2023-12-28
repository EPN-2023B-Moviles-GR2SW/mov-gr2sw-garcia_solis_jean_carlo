// WellSystem.kt

import java.io.File

data class Well(val id: Int, var name: String, var date: String, var depth: Int, var fieldName: String)

class WellSystem(val wells: MutableList<Well> = mutableListOf()) {

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
        if (wellsFile.exists()) {
            wellsFile.bufferedReader().useLines { lines ->
                lines.forEach {
                    val parts = it.split(",")
                    val well = Well(parts[0].toInt(), parts[1], parts[2], parts[3].toInt(), parts[4])
                    wells.add(well)
                }
            }
        }
    }
}
