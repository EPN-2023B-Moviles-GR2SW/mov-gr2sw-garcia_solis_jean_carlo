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
import com.example.a04_examen.modelo.BBaseDatosMemoria
import com.example.a04_examen.modelo.BWell

class FieldActivity : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<String>;
    lateinit var wells:MutableList<BWell>;
    private  var posicionItemSeleccionado = -1
    var managerField = BBaseDatosMemoria;
    var idField=-1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field)

        val botonAnadirWells = findViewById<Button>(R.id.btn_anadir_well)
        botonAnadirWells.setOnClickListener{
            irActividad(FormGestionarWell::class.java)
        }

        idField = intent.getIntExtra("idField",-1)

        actualizarlistViewWells();
    }

    fun actualizarlistViewWells(){
        var field =  managerField.buscarFieldById(idField)!!;
        wells = managerField.obtenerWells(field);
        val listViewWells = findViewById<ListView>(R.id.lvl_list_view_wells)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            wells.mapIndexed { index, well ->
                "${well.nombre}"
            }
        )
        listViewWells.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewWells);
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
        if(posicion2 != null){
            posicionItemSeleccionado = posicion2;
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar2 ->{
                try {
                    val idWell = posicionItemSeleccionado;
                    irActividad(FormGestionarWell::class.java, idWell, idField)
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

    fun irActividad(clase:Class<*>){
        val intent = Intent(this,clase);
        intent.putExtra("idField",idField);
        actualizarlistViewWells()
        startActivity(intent)
    }
    fun irActividad(clase:Class<*>,idWell:Int?,idField: Int?) {
        val intent = Intent(this, clase)
        intent.putExtra("idWell", idWell)
        intent.putExtra("idField", idField)
        startActivity(intent)
    }

    fun abrirDialogEliminar(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Deseas eliminarlo?")
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
                dialog,_ ->
            if(posicionItemSeleccionado.let { BBaseDatosMemoria.eliminarWell(idField, it) }){
                actualizarlistViewWells()
            }
        }
        val dialog = builderDialog.create();
        dialog.show();
    }

    override fun onRestart() {
        super.onRestart()
        actualizarlistViewWells()
    }

    override fun onResume() {
        super.onResume()
        actualizarlistViewWells()
    }

}