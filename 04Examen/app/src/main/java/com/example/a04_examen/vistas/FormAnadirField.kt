package com.example.a04_examen.vistas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.a04_examen.R
import com.example.a04_examen.modelo.BField
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FormAnadirField : AppCompatActivity(){

    var field = BField()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_anadir_field)

        // Recupera el ID
        val intent = intent
        val id = intent.getStringExtra("id")

        // Buscar Condominio
        consultarDocumento(id!!)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.lb_nombre)
        val date = findViewById<EditText>(R.id.lb_Fecha)
        val isActive = findViewById<EditText>(R.id.lb_Estado)
        val area = findViewById<EditText>(R.id.lb_area)


        val botonGuardar = findViewById<Button>(R.id.btn_guardar)
        botonGuardar
            .setOnClickListener(){
                field.nombre = nombre.text.toString()
                field.date = date.text.toString()
                field.isActive = isActive.text.toString().toBoolean()
                field.area = area.text.toString()
                actualizarField(field)
        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar)
        botonCancelar.setOnClickListener({finish()})
    }


    fun consultarDocumento(id: String) {
        val db = Firebase.firestore
        val fieldRef = db.collection("field")

        fieldRef
            .document(id)
            .get()
            .addOnSuccessListener {
                field = BField(
                    it.id as String?,
                    it.data?.get("nombre") as String,
                    it.data?.get("date") as String,
                    it.data?.get("isActive") as Boolean,
                    it.data?.get("area") as String
                )
                notificarActualizacionField()
            }
            .addOnFailureListener {
                // salio Mal
            }
    }


    fun notificarActualizacionField() {
        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.lb_nombre)
        val date = findViewById<EditText>(R.id.lb_Fecha)
        val isActive = findViewById<EditText>(R.id.lb_Estado)
        val area = findViewById<EditText>(R.id.lb_area)

        nombre.setText(field.nombre)
        date.setText(field.date)
        isActive.setText(field.isActive.toString())
        area.setText(field.area)
    }

    fun actualizarField(field: BField) {
        val db = Firebase.firestore
        val fieldRef = db.collection("field")

        // Crear un mapa con los nuevos datos del condominio
        val datosField = hashMapOf(
            "nombre" to field.nombre,
            "date" to field.date,
            "isActive" to field.isActive,
            "area" to field.area
        )
        // Actualizar el documento en Firestore
        fieldRef
            .document(field.id!!)
            .update(datosField as Map<String, Any>)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

}