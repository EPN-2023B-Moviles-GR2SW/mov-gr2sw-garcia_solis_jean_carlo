package com.example.deber_02.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber_02.R
import com.example.deber_02.sql.BD
import com.google.android.material.snackbar.Snackbar


class FieldForm : AppCompatActivity() {
    lateinit var fecha: EditText
    //val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_form)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Field
        val field = BD.tField!!.consultarFieldByID(id)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val fecha = findViewById<EditText>(R.id.input_fecha)
        val isActive = findViewById<EditText>(R.id.input_isActive)

        nombre.setText(field.nombre)
        fecha.setText(field.date)
        isActive.setText(field.isActive.toString())

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_field)
        botonActualizar
            .setOnClickListener {
                field.nombre = nombre.text.toString()
                field.date = fecha.text.toString()
                field.isActive = isActive.text.toString().toBoolean()
                BD.tField!!.actualizarField(field)
                mostrarSnackbar("Field Actualizado")
            }

    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.layout_field_form),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}


