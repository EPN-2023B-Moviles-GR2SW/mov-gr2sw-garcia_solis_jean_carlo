package com.example.deber_02.sql

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.deber_02.modelo.Field
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelperField(
    val contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "FieldBD",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaField =
            """
               CREATE TABLE FIELD(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(100),
               date TEXT,  
               isActive VARCHAR(50)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaField)
        val scriptSQLCrearTablaWell =
            """
               CREATE TABLE WELL(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(100),
               date TEXT,
               depth REAL,
               isActive VARCHAR(50),
               fieldId INTEGER,
               FOREIGN KEY (fieldId) REFERENCES FIELD(id)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaWell)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearField(field: Field): Boolean {
        val bdEscritura = writableDatabase
        val vAGuardar = ContentValues()
        vAGuardar.put("nombre", field.nombre)
        vAGuardar.put("date", field.date)
        vAGuardar.put("isActive", field.isActive)

        val resultadoGuardar = bdEscritura.insert("FIELD", null, vAGuardar)
        bdEscritura.close()

        return resultadoGuardar.toInt() != -1
    }

    fun eliminarFieldByID(id: Int): Boolean {
        val bdEscritura = writableDatabase
        val parametroDelete = arrayOf(id.toString())
        val resultadoDelete = bdEscritura.delete(
            "FIELD",
            "id=?",
            parametroDelete
        )
        bdEscritura.close()

        return resultadoDelete.toInt() != -1
    }


    fun actualizarField(
        field: Field
    ): Boolean {
        val bdEscritura = writableDatabase
        val vAActualizar = ContentValues()
        vAActualizar.put("nombre", field.nombre)
        vAActualizar.put("date", field.date)
        vAActualizar.put("isActive", field.isActive)

        val parametrosUpdate = arrayOf(field.id.toString())
        val resultadoUpdate= bdEscritura.update(
            "FIELD",
            vAActualizar,
            "id=?",
            parametrosUpdate
        )
        bdEscritura.close()

        return resultadoUpdate.toInt() != -1
    }

    fun consultarFieldByID(id: Int): Field {
        val bdLectura = readableDatabase
        val scriptCLectura = """
        SELECT * FROM FIELD WHERE ID = ?
        """.trimIndent()
        val parametrosLectura = arrayOf(id.toString())
        val resultadoLectura = bdLectura.rawQuery(
            scriptCLectura,
            parametrosLectura
        )

        // lógica de búsqueda
        val existeField = resultadoLectura.moveToFirst()
        val fieldEncontrado = Field()

        if (existeField) {
            do {
                val id = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val date = resultadoLectura.getString(2)
                val isActiveString= resultadoLectura.getString(3)


                fieldEncontrado.id = id
                fieldEncontrado.nombre = nombre
                fieldEncontrado.date = date
                fieldEncontrado.isActive = isActiveString.toBoolean()

            } while (resultadoLectura.moveToNext())
        }

        resultadoLectura.close()
        bdLectura.close()
        return fieldEncontrado
    }

    fun consultarAllFields(): ArrayList<Field> {
        val fields = arrayListOf<Field>()
        val bdLectura = readableDatabase
        val scriptLectura = """
        SELECT * FROM FIELD
        """.trimIndent()

        val resultadoLectura = bdLectura.rawQuery(scriptLectura, null)

        if (resultadoLectura.moveToFirst()) {
            do {
                val id = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val date = resultadoLectura.getString(2)
                val isActiveString = resultadoLectura.getString(3)

                val field = Field(
                    id,
                    nombre,
                    date,
                    isActiveString.toBoolean()
                )
                fields.add(field)
            } while (resultadoLectura.moveToNext())
        }

        resultadoLectura.close()
        bdLectura.close()
        return fields
    }

}