package com.example.a04_examen.modelo

data class BWell (
    var id: String?,
    var nombre: String?,
    var date: String?,
    var isActive: Boolean?,
    var depth: String?
){
    // Constructor secundario sin parámetros con valores predeterminados
    constructor() : this(null, null, null, true, null)

}