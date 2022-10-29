package models

import java.time.LocalDate
import java.util.UUID

data class Ordenador(
    val uuid: UUID,
    val modelo : String,
    val alta : LocalDate
)
