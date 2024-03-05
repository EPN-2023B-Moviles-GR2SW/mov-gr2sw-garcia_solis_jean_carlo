package com.example.a04_examen.vistas

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a04_examen.R
import com.example.a04_examen.modelo.BField
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class MainActivity : AppCompatActivity() {


    var posicionItemSeleccionado = 0
    var idFieldSeleccionado = ""
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<BField>
    var query: Query? = null
    val arreglo: ArrayList<BField> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.lvl_list_view_field)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arreglo
        )

        listView.adapter = adaptador
        //consultarColeccion()
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
        val botonAnadirField = findViewById<Button>(R.id.btn_anadir_field)
        botonAnadirField
            .setOnClickListener{
                anadirField()
        }
    }

    fun consultarColeccion(){
        val db = Firebase.firestore
        val fieldsRef = db.collection("field")
        var tarea: Task<QuerySnapshot>? = null
        tarea = fieldsRef.get()
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, fieldsRef)
                    for (field in documentSnapshots) {
                        anadirAArreglo(field)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener { }
        }
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        ref: Query
    ) {
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = ref
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun anadirAArreglo(
        document: QueryDocumentSnapshot
    ) {
        val field = BField(
            document.id as String?,
            document.data.get("nombre") as String,
            document.data.get("date") as String,
            document.data.get("isActive") as Boolean,
            document.data.get("area") as String
        )
        arreglo.add(field)
    }

    fun anadirField() {
        val db = Firebase.firestore
        val fieldRef = db.collection("field")
        val datosField = hashMapOf(
            "nombre" to "Yuca",
            "date" to "02/03/2024",
            "isActive" to false,
            "area" to "2000",
        )
        val identificador = Date().time
        fieldRef // (crear/actualizar)
            .document(identificador.toString())
            .set(datosField)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //Obtener id del arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Condominio en la posición seleccionada
        val fieldSeleccionado = arreglo[posicion]
        // Obtener el id del Condominio seleccionado
        idFieldSeleccionado = fieldSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_ver -> {
                val idField = posicionItemSeleccionado;

                irActividadConId(FieldActivity::class.java, idFieldSeleccionado)

                return true
            }
            R.id.mi_editar -> {
                val idField = posicionItemSeleccionado;
                irActividadConId(FormAnadirField::class.java, idFieldSeleccionado)
                return true
            }
            R.id.mi_eliminar -> {
                "${posicionItemSeleccionado}"
                abrirDialogEliminar()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividadConId(
        clase: Class<*>,
        id: String
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    fun abrirDialogEliminar(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Deseas eliminarlo?")
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
                dialog, _ ->
            eliminarRegistro(idFieldSeleccionado)
        }
        val dialog = builderDialog.create();
        dialog.show();
    }

    fun eliminarRegistro(id: String) {
        val db = Firebase.firestore
        val fieldRef = db.collection("field")

        fieldRef
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { }
            .addOnFailureListener { }
        consultarColeccion()
        //adaptador.notifyDataSetChanged()
    }

    override fun onRestart() {
        super.onRestart()
        consultarColeccion()
    }

    override fun onResume() {
        super.onResume()
        consultarColeccion()
    }



}