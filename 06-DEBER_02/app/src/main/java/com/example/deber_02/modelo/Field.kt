package com.example.deber_02.modelo

import android.os.Build


class Field(
    var id: Int?,
    var nombre: String,
    var date: String,
    var isActive: Boolean = false
) {
    constructor() : this(null, "", "")

    override fun toString(): String {
        return "$nombre"
    }
}