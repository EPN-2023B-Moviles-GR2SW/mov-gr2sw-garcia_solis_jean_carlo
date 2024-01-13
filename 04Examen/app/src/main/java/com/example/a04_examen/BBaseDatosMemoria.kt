package com.example.a04_examen

class BBaseDatosMemoria {

    companion object{
        val arregloField = arrayListOf<BField>()

        init {
            arregloField
                .add(
                    BField(1, "Auca Central", "13/01/2024", true, 2000.00 )
                )
            arregloField
                .add(
                    BField(2, "Auca Sur", "13/01/2024", true, 4000.00 )
                )
            arregloField
                .add(
                    BField(3, "Yuca", "13/01/2024", true, 1500.00 )
                )

        }

    }
}