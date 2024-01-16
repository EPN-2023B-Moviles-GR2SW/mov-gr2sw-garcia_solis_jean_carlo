package com.example.a04_examen.modelo


class BBaseDatosMemoria {

    companion object{
        val fields = arrayListOf<BField>()

//---------------------------FIELD CRUD-----------------------------------
        fun agregarField(field: BField){
            fields.add(field);
        }

        fun buscarFieldById(idField: Int): BField?{
            val field = fields.getOrNull(idField);
            if(field != null){
                return field
            }else{
                println("Field No Encontrado ${idField}");
                return null;
            }
        }

        fun actualizarField(id:Int, newField: BField){
            val field = fields.getOrNull(id);
            if(field != null){
                println("Field Seleccionado: $field")
                fields[id] = newField
                println("Field Atualizado: $newField")
            }else{
                println("Field No Encontrado: ${id}")
            }

        }

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

        //---------------------------WELL CRUD-----------------------------------
        fun obtenerWells(field: BField):MutableList<BWell>{
            var listWells: MutableList<BWell> = mutableListOf();
            for(idx in field.wells.indices){
                println("${idx}: ${field.wells.get(idx)}");
                listWells.add(field.wells.get(idx))
            }
            return listWells;
        }

        fun agregarWell(idField:Int, well: BWell){
            val field = fields.getOrNull(idField);
            if(field != null){
                field.wells.add(well);
                println("Well Agregado '${field.nombre}'.")
            }else{
                println("Field No Encontrado con:  ${idField}");
            }
        }

        fun actualizarWell(idField: Int,idWell: Int,wellNew: BWell){
            val field = fields.getOrNull(idField);
            if(field != null){
                val well = field.wells.getOrNull(idWell);
                if(well != null){
                    field.wells[idWell] = wellNew;
                }else{
                    println("Well no encontrado con id: ${idWell}'.")
                }
            }else{
                println("Field no encontrado con id:  ${idField}");
            }
        }


        fun eliminarWell(idField:Int, idWell:Int ):Boolean{
            val field = fields.getOrNull(idField);
            if(field != null){
                val well = field.wells.getOrNull(idWell);
                if(well != null){
                    field.wells.remove(well);
                    println("Se ah eliminado el registro de '${well.nombre}' del field '${field.nombre}'.")
                    return true;
                }else{
                    println("Well no encontrado con id: ${idWell}'.")
                    return false;
                }
            }else{
                println("Field no encontrado con id:  ${idField}");
                return false;
            }
        }





        init {
            fields
                .add(
                    BField(
                        "Auca Central", "13/01/2024", true, "2000", wells = mutableListOf(
                            BWell(
                                "ACA-001",
                                "13/01/24",
                                true,
                                "80"
                            )
                        )
                    )
                )
            fields
                .add(
                    BField(
                         "Auca Sur", "13/01/2024", true, "4000", wells = mutableListOf(
                            BWell(
                                "AUCA-001",
                                "13/01/24",
                                true,
                                "80"
                            )
                        )
                    )
                )
            fields
                .add(
                    BField(
                         "Yuca", "13/01/2024", true, "1500", wells = mutableListOf(
                            BWell(
                                "YCA-001",
                                "13/01/24",
                                true,
                                "40"
                            )
                        )
                    )
                )
        }
    }
}