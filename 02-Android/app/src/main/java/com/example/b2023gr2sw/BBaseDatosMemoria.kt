package com.example.b2023gr2sw

class BBaseDatosMemoria {

    companion object{
        val arregloEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloEntrenador
                .add(
                    BEntrenador(1, "Adrian", "a@a.com")
                )
            arregloEntrenador
                .add(
                    BEntrenador(2, "Vicente", "b@b.com")
                )
            arregloEntrenador
                .add(
                    BEntrenador(2, "Carolina", "c@c.com")
                )
        }
    }
}