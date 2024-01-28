package com.example.deber_02.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View

import android.widget.ListView
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.AdapterView

import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.deber_02.R
import com.example.deber_02.modelo.Well
import com.example.deber_02.sql.BD
import com.google.android.material.snackbar.Snackbar


class Wells : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var idWellSeleccionado = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Well>
    var idField = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wells)
        // Recupera el ID
        val intent = intent
        idField = intent.getIntExtra("id", 1)
        // Buscar Departamentos
        val field = BD.tField!!.consultarFieldByID(idField)

        val nombreField = findViewById<TextView>(R.id.tv_nombre_field)
        nombreField.setText("${field.nombre}")

        listView = findViewById<ListView>(R.id.lv_wells)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            BD.tWell!!.consultarWellsByFieldId(idField)
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearWell = findViewById<Button>(R.id.btn_crear_well)
        botonCrearWell
            .setOnClickListener {
                crearWell()
            }
        registerForContextMenu(listView)

    }

    fun crearWell() {
        val well =
            Well(
                null,
                "ACA-001",
                "28/1/2024",
                20.0,
                false
            )
        BD.tWell!!.crearWell(well, idField!!)
        adaptador.clear()
        adaptador.addAll(BD.tWell!!.consultarWellsByFieldId(idField))
        adaptador.notifyDataSetChanged()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_well, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        val wellSeleccionado =
            BD.tWell!!.consultarWellsByFieldId(idField)[posicion]
        idWellSeleccionado = wellSeleccionado.id!!
    }

    fun irActividadId(
        clase: Class<*>,
        id: Int
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id);
        startActivity(intent)
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Esta seguro que desea eliminarlo?")
        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            BD.tWell!!.eliminarWellByID(idWellSeleccionado)
            mostrarSnackbar("Elemento:${idWellSeleccionado} eliminado")
            adaptador.clear()
            adaptador.addAll(BD.tWell!!.consultarWellsByFieldId(idField))
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_departamento -> {
                irActividadId(WellForm::class.java, idWellSeleccionado)
                return true
            }

            R.id.mi_eliminar_departamento -> {
                abrirDialogo()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.layout_wells),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.clear()
        adaptador.addAll(BD.tWell!!.consultarWellsByFieldId(idField))
        adaptador.notifyDataSetChanged()
    }



}