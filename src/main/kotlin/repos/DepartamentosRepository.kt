package repos

import db.DataBaseManager
import models.Departamento
import models.Empleado
import java.util.UUID

class DepartamentosRepository : IDepartamentoRepository {
    override fun findAll(): List<Departamento> {
        val query = "SELECT * FROM departamentos"
        DataBaseManager.open()
        val result = DataBaseManager.select(query)
        val lista: MutableList<Departamento> = emptyList<Departamento>().toMutableList()
        result?.let { res ->
            while (result.next()) {
                Departamento(
                    uuid = res.getObject("uuid") as UUID,
                    nombre = res.getString("nombre"),
                    presupuesto = res.getDouble("presupuesto"),
                    emptyList<Empleado>().toMutableList()
                ).let {
                    lista.add(it)
                }
            }
        }
        DataBaseManager.close()
        println("Encontrados: ${lista.size} departamentos")
        return lista
    }

    override fun findById(id: UUID): Departamento? {
        val query = "SELECT * FROM departamentos WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var departamento: Departamento? = null
        result?.let {
            if (result.next()) {
                departamento = Departamento(
                    uuid = it.getObject("uuid") as UUID,
                    nombre = it.getString("nombre"),
                    presupuesto = it.getDouble("presupuesto"),
                    emptyList<Empleado>().toMutableList()
                )
            }
        }
        DataBaseManager.close()
        println("Encontrado departamento: $departamento")
        return departamento
    }

    override fun save(item: Departamento): Departamento {
        val departamento = findById(item.uuid)

        departamento?.let {
            return update(item)
        } ?: run {
            return insert(item)
        }
    }

    private fun insert(departamento: Departamento): Departamento {
        val query = """
            INSERT INTO departamentos
            (uuid, nombre, presupuesto) VALUES (?, ?, ?)
        """.trimIndent()
        DataBaseManager.open()
        DataBaseManager.insert(query, departamento.uuid, departamento.nombre, departamento.presupuesto)
        DataBaseManager.close()
        return departamento
    }

    private fun update(departamento: Departamento): Departamento {

        val query = """UPDATE departamentos 
            SET  nombre = ?, presupuesto = ? 
            WHERE uuid = ?"""
            .trimIndent()

        DataBaseManager.open()
        val result = DataBaseManager.update(
            query, departamento.nombre, departamento.presupuesto
        )
        DataBaseManager.close()
        // Devolvemos el tenista
        println("Tenista actualizado: $departamento - Resultado: ${result == 1}")
        return departamento
    }

    override fun delete(item: Departamento): Boolean {
        check(item.empleados.isNotEmpty()){"El departamento tiene empleados. No puedes eliminar un departamento con empleados."}
        val query = "DELETE FROM departamentos WHERE uuid = ?"
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, item.uuid)
        DataBaseManager.close()
        return result == -1
    }
}