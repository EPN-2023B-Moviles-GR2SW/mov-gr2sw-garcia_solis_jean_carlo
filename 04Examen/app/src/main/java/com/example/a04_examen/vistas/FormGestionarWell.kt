package com.example.a04_examen.vistas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.a04_examen.R
import com.example.a04_examen.modelo.BWell
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FormGestionarWell : AppCompatActivity() {

    var well = BWell()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_gestionar_well)

        // Recupera el ID
        val intent = intent
        val idField = intent.getStringExtra("idField")
        val idWell = intent.getStringExtra("idWell")
        // Buscar Condominio
        consultarDocumento(idField!!, idWell!!)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.lb_nombre2)
        val date = findViewById<EditText>(R.id.lb_Fecha2)
        val isActive = findViewById<EditText>(R.id.lb_Estado2)
        val depth = findViewById<EditText>(R.id.lb_depth)


        val botonGuardarWell = findViewById<Button>(R.id.btn_guardar2)
        botonGuardarWell
            .setOnClickListener{
                well.nombre = nombre.text.toString()
                well.date = nombre.text.toString()
                well.isActive = nombre.text.toString().toBoolean()
                well.depth = nombre.text.toString()
                actualizarWell(well, idField!!)
        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar2)
        botonCancelar.setOnClickListener({finish()})

    }

    fun consultarDocumento(idField: String, idWell: String) {
        val db = Firebase.firestore
        val wellsRef = db.collection("field/${idField}/wells")

        wellsRef
            .document(idWell)
            .get()
            .addOnSuccessListener {
                well = BWell(
                    it.id as String?,
                    it.data?.get("nombre") as String,
                    it.data?.get("date") as String,
                    it.data?.get("isActive") as Boolean,
                    it.data?.get("depth") as String
                )
                notificarActualizacionWell()
            }
            .addOnFailureListener {
                // salio Mal
            }
    }

    fun notificarActualizacionWell() {
        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.lb_nombre2)
        val date = findViewById<EditText>(R.id.lb_Fecha2)
        val isActive = findViewById<EditText>(R.id.lb_Estado2)
        val depth = findViewById<EditText>(R.id.lb_depth)

        nombre.setText(well.nombre)
        date.setText(well.date)
        isActive.setText(well.isActive.toString())
        depth.setText(well.depth)
    }



    fun actualizarWell(well: BWell, idField: String) {
        val db = Firebase.firestore
        val wellsRef = db.collection("field/${idField}/wells")

        // Crear un mapa con los nuevos datos del condominio
        val datosField = hashMapOf(
            "nombre" to well.nombre,
            "date" to well.date,
            "isActive" to well.isActive,
            "depth" to well.depth
        )
        // Actualizar el documento en Firestore
        wellsRef
            .document(well.id!!)
            .update(datosField as Map<String, Any>)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }
}