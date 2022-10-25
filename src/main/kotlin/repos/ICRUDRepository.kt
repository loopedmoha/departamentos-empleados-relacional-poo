package repos

interface ICRUDRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(id: ID): T?
    fun save(item: T): T
    fun delete(item: T): Boolean
}