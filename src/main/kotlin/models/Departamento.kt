package models

import java.util.UUID

data class Departamento(
    val uuid : UUID,
    val nombre : String,
    val presupuesto : Double,
    val empleados : MutableList<Empleado>
    )