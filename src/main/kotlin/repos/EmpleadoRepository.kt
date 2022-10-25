package repos

import db.DataBaseManager
import models.Empleado
import java.time.LocalDate
import java.util.*

class EmpleadoRepository : IEmpleadoRepository {
    override fun findAll(): List<Empleado> {
        val query = "SELECT * FROM Empleados"
        DataBaseManager.open()
        val result = DataBaseManager.select(query)
        val lista: MutableList<Empleado> = emptyList<Empleado>().toMutableList()
        result?.let { res ->
            while (result.next()) {
                Empleado(
                    uuid = res.getObject("uuid") as UUID,
                    nombre = res.getString("nombre"),
                    nacimiento = LocalDate.parse(res.getString("nacimiento")),
                    departamentoId = res.getObject("departamentoId") as UUID
                ).let {
                    lista.add(it)
                }
            }
        }
        DataBaseManager.close()
        println("Encontrados: ${lista.size} Empleados")
        return lista
    }

    override fun findById(id: UUID): Empleado? {
        val query = "SELECT * FROM empleados WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var empleado: Empleado? = null
        result?.let {
            if (result.next()) {
                empleado = Empleado(
                    uuid = it.getObject("uuid") as UUID,
                    nombre = it.getString("nombre"),
                    nacimiento = LocalDate.parse(it.getString("nacimiento")),
                    departamentoId = it.getObject("departamentoId") as UUID
                )

            }
        }
        DataBaseManager.close()
        println("Encontrado Empleado: $empleado")
        return empleado
    }

    override fun save(item: Empleado): Empleado {

        val empleado = findById(item.uuid)

        empleado?.let {
            return update(item)
        } ?: run {
            return insert(item)
        }
    }

    private fun insert(empleado: Empleado): Empleado {
        val query = """
            INSERT INTO Empleados
            (uuid, nombre, nacimiento, departamentoId) VALUES (?, ?, ?, ?)
        """.trimIndent()
        DataBaseManager.open()
        DataBaseManager.insert(query, empleado.uuid, empleado.nombre, empleado.nacimiento, empleado.departamentoId)
        DataBaseManager.close()
        return empleado
    }

    private fun update(empleado: Empleado): Empleado {

        val query = """UPDATE Empleados 
            SET  nombre = ?, nacimiento = ? 
            WHERE uuid = ?"""
            .trimIndent()

        DataBaseManager.open()
        val result = DataBaseManager.update(
            query, empleado.nombre, empleado.nacimiento.toString()
        )
        DataBaseManager.close()
        // Devolvemos el tenista
        println("Tenista actualizado: $empleado - Resultado: ${result == 1}")
        return empleado
    }

    override fun delete(item: Empleado): Boolean {

        val query = "DELETE FROM empleados WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, item.uuid)
        DataBaseManager.close()
        return result == -1
    }
}