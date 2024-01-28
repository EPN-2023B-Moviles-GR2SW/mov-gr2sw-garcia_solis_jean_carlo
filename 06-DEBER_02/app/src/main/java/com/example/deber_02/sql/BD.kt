package com.example.deber_02.sql

import android.content.Context

class BD {
    companion object {
        var tField: SQLiteHelperField? = null
            private set
        var tWell: SQLiteHelperWell? = null
            private set

        fun inicializar(contexto: Context) {
            tField = SQLiteHelperField(contexto)
            tWell = SQLiteHelperWell(contexto)
        }

        fun cerrarConexiones() {
            tField?.close()
            tWell?.close()
        }
    }
}