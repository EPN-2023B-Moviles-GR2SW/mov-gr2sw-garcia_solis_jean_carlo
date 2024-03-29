package com.example.deber_02.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber_02.R
import com.example.deber_02.sql.BD


class FieldForm : AppCompatActivity() {


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

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_well)
        botonActualizar
            .setOnClickListener {
                field.nombre = nombre.text.toString()
                field.date = fecha.text.toString()
                field.isActive = isActive.text.toString().toBoolean()
                BD.tField!!.actualizarField(field)

                finish()
            }

    }

}


