package models

import java.time.LocalDate
import java.util.*

data class Empleado(
    val uuid: UUID,
    val nombre: String,
    val nacimiento: LocalDate,
    val departamento: Departamento?,
    val rol: Rol
) {
    override fun toString(): String {
        return "Empleado(uuid=$uuid, nombre='$nombre', nacimiento=$nacimiento, departamentoId=" +
                "${departamento?.uuid ?: run { "NO EXISTE" }}, rol=$rol\n"
    }
}

enum class Rol(var value: String) {
    ANALISTA("analista"), PROGRAMADOR("programador"), TESTER("tester")
}
