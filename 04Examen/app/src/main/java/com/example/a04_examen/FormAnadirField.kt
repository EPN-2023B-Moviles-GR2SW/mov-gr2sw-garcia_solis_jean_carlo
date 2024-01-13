package com.example.a04_examen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class FormAnadirField() : AppCompatActivity(){

    var fieldManager = BBaseDatosMemoria;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_anadir_field)

        llenarFormField();
        val botonGuardar = findViewById<Button>(R.id.btn_guardar)
        botonGuardar.setOnClickListener(){
            guardar();
            val nuevoIntent = Intent(this, MainActivity::class.java)
            startActivity(nuevoIntent)
        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar)
        botonCancelar.setOnClickListener({finish()})
    }


    fun guardar(){
        val nombreInput =findViewById<EditText>(R.id.lb_nombre);
        val fechaInput =findViewById<EditText>(R.id.lb_Fecha);
        val estadoInput =findViewById<EditText>(R.id.lb_Estado);
        val areaInput =findViewById<EditText>(R.id.lb_area);
        val idField = intent.getIntExtra("idField",-1);
        var field = BField(
            nombre = nombreInput.text.toString(),
            date = fechaInput.text.toString(),
            isActive = estadoInput.text.toString().toBoolean(),
            area = areaInput.text.toString()
        )
        if(idField == -1){
            fieldManager.agregarField(field);
        }else{
            fieldManager.actualizarField(idField,field);
        }

    }

    fun llenarFormField(){
        val formField = findViewById<TextView>(R.id.txt_view_form_field)
        val nombreInput =findViewById<EditText>(R.id.lb_nombre);
        val fechaInput =findViewById<EditText>(R.id.lb_Fecha);
        val estadoInput =findViewById<EditText>(R.id.lb_Estado);
        val areaInput =findViewById<EditText>(R.id.lb_area);
        val idField = intent.getIntExtra("idField",-1);
        if(idField != -1){
            val fieldE = fieldManager.buscarFieldById(idField);
            if(fieldE!==null){
                formField.text = "Actualizar Libro"
                nombreInput.setText(fieldE.nombre.toString());
                fechaInput.setText(fieldE.date.toString());
                estadoInput.setText(fieldE.isActive.toString());
                areaInput.setText(fieldE.area.toString());
            }
        }

    }
}