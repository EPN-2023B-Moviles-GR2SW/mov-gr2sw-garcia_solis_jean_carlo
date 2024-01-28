package com.example.deber_02.sql

    import android.content.ContentValues
    import android.content.Context
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper
    import com.example.deber_02.modelo.Well

class SQLiteHelperWell(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "FieldBD",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearWell(well: Well, fieldId: Int): Boolean {
        val bdEscritura = writableDatabase
        val vAGuardar = ContentValues()
        vAGuardar.put("nombre", well.nombre)
        vAGuardar.put("date", well.date)
        vAGuardar.put("depth", well.depth)
        vAGuardar.put("isActive", well.isActive)
        vAGuardar.put("fieldId", fieldId)

        val resultadoGuardar = bdEscritura.insert("WELL", null, vAGuardar)
        bdEscritura.close()

        return resultadoGuardar.toInt() != -1
    }

    fun eliminarWellByID(id: Int): Boolean {
        val bdEscritura = writableDatabase
        val parametroDelete = arrayOf(id.toString())
        val resultadoDelete = bdEscritura.delete(
            "WELL",
            "id=?",
            parametroDelete
        )
        bdEscritura.close()

        return resultadoDelete.toInt() != -1
    }

    fun actualizarWell(well: Well): Boolean {
        val bdEscritura = writableDatabase
        val vAActualizar = ContentValues()
        vAActualizar.put("nombre", well.nombre)
        vAActualizar.put("date", well.date)
        vAActualizar.put("depth", well.depth)
        vAActualizar.put("isActive", well.isActive)

        val parametroActualizar = arrayOf(well.id.toString())
        val resultadoActualizacion = bdEscritura.update(
            "WELL",
            vAActualizar,
            "id=?",
            parametroActualizar
        )
        bdEscritura.close()

        return resultadoActualizacion.toInt() != -1
    }

    fun consultarWellByID(id: Int): Well {
        val bdLectura = readableDatabase
        val scriptCLectura = """
            SELECT * FROM WELL WHERE ID = ?
            """.trimIndent()
        val parametrosLectura = arrayOf(id.toString())
        val resultadoLectura = bdLectura.rawQuery(
            scriptCLectura,
            parametrosLectura
        )

        // lógica de búsqueda
        val existeWell = resultadoLectura.moveToFirst()
        val wellEncontrado = Well()

        if (existeWell) {
            do {
                val id = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val date = resultadoLectura.getString(2)
                val depth = resultadoLectura.getDouble(3)
                val isActiveString = resultadoLectura.getString(4)
                val fieldId = resultadoLectura.getInt(5)

                wellEncontrado.id = id
                wellEncontrado.nombre = nombre
                wellEncontrado.date = date
                wellEncontrado.depth = depth
                wellEncontrado.isActive = isActiveString.toBoolean()

            } while (resultadoLectura.moveToNext())
        }

        resultadoLectura.close()
        bdLectura.close()
        return wellEncontrado
    }

    fun consultarAllWells(): ArrayList<Well> {
        val wells = arrayListOf<Well>()
        val bdLectura = readableDatabase
        val scriptCLectura = "SELECT * FROM WELL"
        val resultadoLectura = bdLectura.rawQuery(scriptCLectura, null)

        if (resultadoLectura.moveToFirst()) {
            do {
                val id = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val date = resultadoLectura.getString(2)
                val depth = resultadoLectura.getDouble(3)
                val isActiveString = resultadoLectura.getString(4)
                val fieldId = resultadoLectura.getInt(5)

                val isActive = isActiveString.toBoolean() ?: false

                val well = Well(id, nombre, date, depth, isActive)
                wells.add(well)
            } while (resultadoLectura.moveToNext())
        }

        resultadoLectura.close()
        bdLectura.close()

        return wells
    }

    fun consultarWellsByFieldId(fieldId: Int): ArrayList<Well> {
        val wells = arrayListOf<Well>()
        val bdLectura = readableDatabase

        val scriptCLectura = "SELECT * FROM WELL WHERE fieldId = ?"
        val parametrosLectura = arrayOf(fieldId.toString())

        val resultadoLectura = bdLectura.rawQuery(
            scriptCLectura,
            parametrosLectura
        )

        if (resultadoLectura.moveToFirst()) {
            do {
                val id = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val date = resultadoLectura.getString(2)
                val depth = resultadoLectura.getDouble(3)
                val isActiveString = resultadoLectura.getString(4)

                val isActive = isActiveString.toBoolean() ?: false

                val well = Well(id, nombre, date, depth, isActive)
                wells.add(well)
            } while (resultadoLectura.moveToNext())
        }

        resultadoLectura.close()
        bdLectura.close()

        return wells
    }



}