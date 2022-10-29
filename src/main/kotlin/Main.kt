import controller.Controller
import db.DataBaseManager
import models.Departamento
import models.Empleado
import models.Rol
import repos.DepartamentosRepository
import repos.EmpleadoRepository
import java.io.File
import java.time.LocalDate
import java.util.*

fun main(args: Array<String>) {


    DataBaseManager.open()
    val sqlScript = System.getProperty("user.dir") + File.separator + "db" + File.separator + "datos.sql"
    DataBaseManager.initData(sqlScript)

    val departamentosRepository = DepartamentosRepository()
    val empleadosRepository = EmpleadoRepository(departamentosRepository)

    val controller: Controller = Controller(departamentosRepository, empleadosRepository)
    val departamento1 = Departamento(
        UUID.fromString("3f73c518-49f3-4c6c-b76e-6a91f60b5838"),
        "Ventas",
        17619.23, emptyList<Empleado>().toMutableList()
    )

    val departamento2 = Departamento(
        UUID.fromString("207caa76-e32f-46f0-8f20-6ab4393aa409"),
        "RRHH",
        12545.23, emptyList<Empleado>().toMutableList()
    )
    controller.createDepartamento(
        departamento1
    )
    controller.createDepartamento(
        departamento2
    )


    controller.createEmpleado(
        Empleado(
            UUID.fromString("6D1642C4-60CB-B764-D257-5466D531ABAC"), "Dai Booker",
            LocalDate.parse("1969-02-11"), departamento1, Rol.TESTER
        )
    )



    controller.createEmpleado(
        Empleado(
            UUID.fromString("4D2C33E6-1BC1-0D48-8050-77D624665F5C"), "Hedley Webb",
            LocalDate.parse("1983-04-04"), departamento1, Rol.ANALISTA
        )
    )

    controller.createEmpleado(
        Empleado(
            UUID.fromString("77572429-310E-8993-589F-5E67BC835BD3"), "Hedley Osborn",
            LocalDate.parse("1973-09-07"), null, Rol.PROGRAMADOR
        )
    )

    controller.createEmpleado(
        Empleado(
            UUID.fromString("B9191782-819F-03EB-5B35-4C5BCE044682"), "Melodie Wyatt",
            LocalDate.parse("1972-04-08"), departamento2, Rol.ANALISTA
        )
    )


    controller.createEmpleado(
        Empleado(
            UUID.fromString("71C58E53-B6DA-6634-9584-CBC9E7F9551D"), "Zane Forbes",
            LocalDate.parse("1971-12-30"), departamento2, Rol.TESTER
        )
    )

    controller.createEmpleado(
        Empleado(
            UUID.randomUUID(), "John Jones",
            LocalDate.parse("1986-12-30"), null, Rol.PROGRAMADOR
        )
    )

    val emmanuele = Empleado(
        UUID.fromString("70e4d2f1-4fd9-4823-8ee2-d102f604f991"), "Emmanuele Pierce",
        LocalDate.parse("1999-12-30"), departamento1, Rol.ANALISTA
    )

    controller.createEmpleado(emmanuele)

    println("////////////////////////////")
    println(emmanuele)
    println("////////////////////////////")
    controller.getEmpleados()
    println("////////////////////////////")
    controller.deleteEmpleado(emmanuele)
    controller.getEmpleados()


    DataBaseManager.close()
}

