package models

import java.time.LocalDate
import java.util.*

data class Empleado(
    val uuid : UUID,
    val nombre : String,
    val nacimiento : LocalDate,
    val departamentoId : UUID
)