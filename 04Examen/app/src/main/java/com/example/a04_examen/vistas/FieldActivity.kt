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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a04_examen.R
import com.example.a04_examen.modelo.BWell
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class FieldActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<BWell>
    var posicionItemSeleccionado = 0
    var idWellSeleccionado = ""
    var query: Query? = null
    val arreglo: ArrayList<BWell> = arrayListOf()
    var idField: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field)

        // Recupera el ID
        val intent = intent
        // Buscar Departamentos
        buscarField(intent.getStringExtra("id")!!)
        val botonAnadirWells = findViewById<Button>(R.id.btn_anadir_well)
        botonAnadirWells
            .setOnClickListener{
                anadirWell()
        }
    }

    fun anadirWell(){
        if (idField == null){
            return
        }
        val db = Firebase.firestore
        val wellsRef = db.collection("field/${idField}/wells")
        val datosDepartamento = hashMapOf(
            "nombre" to "YCA-001",
            "date" to "05/03/2024",
            "isActive" to true,
            "depth" to "80"
        )
        val identificador = Date().time
        wellsRef
            .document(identificador.toString())
            .set(datosDepartamento)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }


    fun buscarField(id: String) {
        val db = Firebase.firestore
        val fieldRef = db.collection("field")

        fieldRef
            .document(id)
            .get()
            .addOnSuccessListener {
                idField = id
                val nombreField = findViewById<TextView>(R.id.tv_nombre_field)
                nombreField.setText(it.data?.get("nombre") as String)

                listView = findViewById<ListView>(R.id.lvl_list_view_field)
                adaptador = ArrayAdapter(
                    this, // Contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    arreglo
                )
                listView.adapter = adaptador
                consultarColeccion()
                registerForContextMenu(listView)
            }
            .addOnFailureListener { }
    }




    fun consultarColeccion() {
        if (idField == null) {
            return
        }
        val db = Firebase.firestore
        val departamentosRef = db.collection("condominio/${idField}/departamentos")
        var tarea: Task<QuerySnapshot>? = null
        tarea = departamentosRef.get() // 1era vez
        limpiarArreglo()
        //adaptador.notifyDataSetChanged()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, departamentosRef)
                    for (departamento in documentSnapshots) {
                        anadirAArreglo(departamento)
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
        val well = BWell(
            document.id as String?,
            document.data.get("nombre") as String,
            document.data.get("date") as String,
            document.data.get("isActive") as Boolean,
            document.data.get("depth") as String
        )
        arreglo.add(well)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu2, menu)
        val info2 = menuInfo as AdapterView.AdapterContextMenuInfo;
        val posicion2 = info2.position;
        posicionItemSeleccionado = posicion2
        // Acceder al objeto Condominio en la posición seleccionada
        val wellSeleccionado = arreglo[posicion2]
        // Obtener el id del Condominio seleccionado
        idWellSeleccionado = wellSeleccionado.id!!

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar2 ->{
                try {
                    irActividadConId(FormGestionarWell::class.java, idWellSeleccionado)
                }catch (e: Throwable){}
                return true;

            }
            R.id.mi_eliminar2 ->{
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
        intent.putExtra("idField", idField);
        intent.putExtra("idWell", id);
        startActivity(intent)
    }

    fun abrirDialogEliminar(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Deseas eliminarlo?")
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
                dialog, which ->
            eliminarRegistro(idWellSeleccionado)
        }
        val dialog = builderDialog.create();
        dialog.show();
    }

    fun eliminarRegistro(id: String) {
        if (idField == null) {
            return
        }
        val db = Firebase.firestore
        val departamentosRef = db.collection("field/${idField}/wells")

        departamentosRef
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { }
            .addOnFailureListener { }
        consultarColeccion()
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