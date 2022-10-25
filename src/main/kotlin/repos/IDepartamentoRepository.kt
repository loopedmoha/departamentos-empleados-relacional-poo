package repos

import models.Departamento
import java.util.UUID

interface IDepartamentoRepository : ICRUDRepository<Departamento, UUID> {
}