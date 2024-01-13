package com.example.a04_examen

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

class MainActivity : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<String>;
    var posicionItemSeleccionado = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonAnadirField= findViewById<Button>(R.id.btn_anadir_field)
        botonAnadirField.setOnClickListener{
            irActividad(FormAnadirField::class.java)
        }

        actualizarListViewFields();
    }

    fun actualizarListViewFields(){
        val listViewLibros = findViewById<ListView>(R.id.lvl_list_view_field)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BBaseDatosMemoria.fields.mapIndexed { index, field ->
                "${field.nombre}"
            }
        )
        listViewLibros.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewLibros)
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
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_ver -> {
                val idField = posicionItemSeleccionado;

                irActividad(FieldActivity::class.java, idField)

                return true
            }
            R.id.mi_editar -> {
                val idField = posicionItemSeleccionado;
                irActividad(FormAnadirField::class.java, idField)
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

    fun irActividad(clase: Class<*>, idField: Int = -1) {
        val intent = Intent(this, clase)
        intent.putExtra("idField", idField)
        startActivity(intent)
    }


    fun abrirDialogEliminar(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Deseas eliminarlo?")
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
                dialog,_ ->
            if(posicionItemSeleccionado.let { BBaseDatosMemoria.eliminarField(it) }){
                actualizarListViewFields()
            }
        }
        val dialog = builderDialog.create();
        dialog.show();
    }

    override fun onRestart() {
        super.onRestart()
        actualizarListViewFields()
    }

    override fun onResume() {
        super.onResume()
        actualizarListViewFields()
    }



}