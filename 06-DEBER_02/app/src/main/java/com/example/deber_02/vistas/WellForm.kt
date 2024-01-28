package com.example.deber_02.vistas

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber_02.R
import com.example.deber_02.sql.BD
import com.google.android.material.snackbar.Snackbar

class WellForm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_well_form)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Well
        val well = BD.tWell!!.consultarWellByID(id)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val fecha = findViewById<EditText>(R.id.input_fecha)
        val depth = findViewById<EditText>(R.id.input_depth)
        val isActive = findViewById<EditText>(R.id.input_isActive)

        nombre.setText(well.nombre.toString())
        fecha.setText(well.date)
        depth.setText(well.depth.toString())
        isActive.setText(well.isActive.toString())

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_well)
        botonActualizar
            .setOnClickListener {
                well.nombre = nombre.text.toString()
                well.date  = fecha.text.toString()
                well.depth = depth.text.toString().toDouble()
                well.isActive = isActive.text.toString().toBoolean()
                BD.tWell!!.actualizarWell(well)
                mostrarSnackbar("Well Actualizado")
            }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.layout_well_form),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}