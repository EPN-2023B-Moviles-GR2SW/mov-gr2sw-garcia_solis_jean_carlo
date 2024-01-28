package com.example.deber_02.modelo

class Well(
    var id: Int?,
    var nombre: String,
    var date: String,
    var depth: Double,
    var isActive: Boolean = false
) {

    constructor() : this(null, "", "", 0.0)

    override fun toString(): String {
        return "$nombre"
    }

}