package com.example.a04_examen.vistas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.a04_examen.R
import com.example.a04_examen.modelo.BBaseDatosMemoria
import com.example.a04_examen.modelo.BWell

class FormGestionarWell : AppCompatActivity() {

    var fieldManager = BBaseDatosMemoria;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_gestionar_well)

        llenarFormWell();

        val botonGuardarWell = findViewById<Button>(R.id.btn_guardar2)
        botonGuardarWell.setOnClickListener{
            guardarW();
            finish()
            //val nuevoIntent = Intent(this, FieldActivity::class.java)
           // startActivity(nuevoIntent);
        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar2)
        botonCancelar.setOnClickListener({finish()})

        val idField = intent.getIntExtra("idField",-1);
        val idWell = intent.getIntExtra("idWell",-1);
    }

    fun guardarW(){
        val nombreInput = findViewById<EditText>(R.id.lb_nombre2)
        val fechaInput = findViewById<EditText>(R.id.lb_Fecha2)
        val estadoInput = findViewById<EditText>(R.id.lb_Estado2)
        val depthInput = findViewById<EditText>(R.id.lb_depth)
        var well = BWell(
            nombre = nombreInput.text.toString(),
            date = fechaInput.text.toString(),
            isActive = estadoInput.text.toString().toBoolean(),
            depth  = depthInput.text.toString()
        )

        val idField = intent.getIntExtra("idField",-1);
        val idWell = intent.getIntExtra("idWell",-1);
        if(idField != -1 && idWell !=-1){

            fieldManager.actualizarWell(idField,idWell, well);
            setResult(RESULT_OK);

        }else{
            fieldManager.agregarWell(idField,well)
            setResult(RESULT_OK);
        }
    }

    fun llenarFormWell(){

        val formWell = findViewById<TextView>(R.id.txt_view_form_well)
        val nombreInput = findViewById<EditText>(R.id.lb_nombre2)
        val fechaInput = findViewById<EditText>(R.id.lb_Fecha2)
        val estadoInput = findViewById<EditText>(R.id.lb_Estado2)
        val depthInput = findViewById<EditText>(R.id.lb_depth)
        val idField = intent.getIntExtra("idField",-1);
        val idWell = intent.getIntExtra("idWell",-1);

        if(idField != -1 && idWell !=-1){
            formWell.text = "Actualizar Well"
            val fieldE = fieldManager.buscarFieldById(idField);
            val well = fieldE!!.wells.get(idWell);
            nombreInput.setText(well.nombre.toString())
            fechaInput.setText(well.date.toString())
            estadoInput.setText(well.isActive.toString())
            depthInput.setText(well.depth.toString())
        }

    }
}