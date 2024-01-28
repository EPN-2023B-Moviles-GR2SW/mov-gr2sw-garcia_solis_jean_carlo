package com.example.deber_02.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.deber_02.modelo.Field
import com.example.deber_02.sql.BD
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.deber_02.R
import com.google.android.material.snackbar.Snackbar


class Fields : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var idFieldSeleccionado = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Field>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fields)
        listView = findViewById<ListView>(R.id.lv_field)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BD.tField!!.consultarAllFields()
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearField = findViewById<Button>(R.id.btn_crear_field)
        botonCrearField
            .setOnClickListener {
                crearField()
            }
        registerForContextMenu(listView)
    }

    fun crearField() {
        val field =
            Field(
                null,
                "AUCA CENTRAL",
                "27/1/2024",
                false
            )
        BD.tField!!.crearField(field)
        adaptador.clear()
        adaptador.addAll(BD.tField!!.consultarAllFields())
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_field, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        val fieldSeleccionado = BD.tField!!.consultarAllFields()[posicion]
        idFieldSeleccionado = fieldSeleccionado.id!!
    }

    fun irActividadId(
        clase: Class<*>,
        id: Int
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id)
        startActivity(intent)
    }
    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.layout_field),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Esta seguro de que desea elimnar?")
        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            BD.tField!!.eliminarFieldByID(idFieldSeleccionado)
            mostrarSnackbar("Elemento:${idFieldSeleccionado} eliminado")
            adaptador.clear()
            adaptador.addAll(BD.tField!!.consultarAllFields())
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
            R.id.mi_editar_field -> {
                irActividadId(FieldForm::class.java, idFieldSeleccionado)
                return true
            }

            R.id.mi_eliminar_field -> {
                abrirDialogo()
                return true
            }

            R.id.mi_ver_wells -> {
                irActividadId(Wells::class.java, idFieldSeleccionado)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        adaptador.clear()
        adaptador.addAll(BD.tField!!.consultarAllFields())
        adaptador.notifyDataSetChanged()
    }
}