package com.example.a04_examen.modelo



data class BField(
    var id: String?,
    var nombre: String?,
    var date: String?,
    var isActive: Boolean = true,
    var area: String?

    ){
    // Constructor secundario sin parámetros con valores predeterminados
    constructor() : this(null, "", "", true, "")

}