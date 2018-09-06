package lazy.of.go.to.db.mapper

interface EntityMapper<F, T> {
    fun fromObject(obj: T): F

    fun toObject(obj: F): T
}