package com.example.a04_examen

import java.util.Date

class BField (
    var id: Int,
    var nombre: String?,
    var date: String?,
    var isActive: Boolean?,
    var area: Double
){
    override fun toString(): String {
        return "${nombre}"
    }
}