package com.example.a04_examen



class BBaseDatosMemoria {

    companion object{
        val fields = arrayListOf<BField>()

        fun eliminarField(id:   Int):Boolean{
            val field = fields.getOrNull(id);
            if(field != null){
                fields.remove(field);
                println("Field eliminado: $field");
                return true;
            }else{
                println("Field no encontrado ${id}");
                return false;
            }
        }

        init {
            fields
                .add(
                    BField(1, "Auca Central", "13/01/2024", true, 2000.00 )
                )
            fields
                .add(
                    BField(2, "Auca Sur", "13/01/2024", true, 4000.00 )
                )
            fields
                .add(
                    BField(3, "Yuca", "13/01/2024", true, 1500.00 )
                )

        }

    }
}