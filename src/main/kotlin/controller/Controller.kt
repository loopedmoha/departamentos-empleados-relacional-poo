package controller

import models.Departamento
import models.Empleado
import repos.DepartamentosRepository
import repos.EmpleadoRepository

/**
 * Controlador 1-M
 * @property departamentosRepository DepartamentosRepository
 * @property empleadoRepository EmpleadoRepository
 * @property departamentos MutableList<Departamento>
 * @property empleados MutableList<Empleado>
 * @constructor
 */
class Controller(var departamentosRepository: DepartamentosRepository, var empleadoRepository: EmpleadoRepository) {

    var departamentos : MutableList<Departamento> = departamentosRepository.findAll() as MutableList<Departamento>
    var empleados : MutableList<Empleado> = empleadoRepository.findAll() as MutableList<Empleado>

    fun deleteDepartamento(departamento: Departamento): Boolean {
        check(departamento.empleados.isNotEmpty()) { "El departamento tiene empleados." }
        /*if(departamento.empleados.isNotEmpty()){
            departamento.empleados.forEach{deleteEmpleado(it)}
        }*/
        departamentosRepository.findById(departamento.uuid)?.let {
            return departamentosRepository.delete(departamento)
        } ?: run {
            println("No se encontró un departamento con ese ID")
            return false
        }

    }

    fun getEmpleados(){
        println(empleadoRepository.findAll())
    }
    fun deleteEmpleado(empleado: Empleado): Boolean {

        if (empleadoRepository.findById(empleado.uuid) != null) {
            departamentosRepository.findById(empleado.departamento!!.uuid)?.let {
                if (it.empleados.size == 1) {
                    it.empleados.removeFirst()
                    departamentosRepository.delete(it)
                    empleadoRepository.delete(empleado)
                    return true
                } else {
                    it.empleados.remove(empleado)
                    empleadoRepository.delete(empleado)
                    return true
                }
            } ?: run {
                println("Los datos del empleado no son correctos")
                return false
            }
        }
        println("El empleado no existe")
        return false
    }

    fun createDepartamento(departamento: Departamento): Boolean {
        if (departamentosRepository.findById(departamento.uuid) == null) {
            departamentosRepository.save(departamento)
            println("Departamento creado $departamento")
            departamentos.add(departamento)
            return true
        }
        println("Ya existe un departamento con ese id")
        return false
    }

    fun createEmpleado(empleado: Empleado) : Boolean{
        require(empleadoRepository.findById(empleado.uuid) == null){"Ya existe ese empleado con ese id"}
        empleadoRepository.save(empleado)
        return true
    }


}