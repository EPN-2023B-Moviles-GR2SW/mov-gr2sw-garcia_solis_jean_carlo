package com.example.a04_examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<String>;
    var posicionItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actualizarListViewFields();
    }

    fun actualizarListViewFields(){
        val listViewLibros = findViewById<ListView>(R.id.lvl_list_view_field)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BBaseDatosMemoria.fields.mapIndexed { index, field ->
                "${field.id}. ${field.nombre}"
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
                "${posicionItemSeleccionado}"
                return true
            }
            R.id.mi_editar -> {
                "${posicionItemSeleccionado}"
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