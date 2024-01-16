package com.example.a04_examen.modelo


data class BField(
    var nombre: String?,
    var date: String?,
    var isActive: Boolean?,
    var area: String?,
    var wells: MutableList<BWell> = mutableListOf()
 ){
}