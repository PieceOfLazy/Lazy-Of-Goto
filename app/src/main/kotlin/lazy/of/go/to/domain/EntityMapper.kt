package lazy.of.go.to.domain

interface EntityMapper<F, T> {
    fun fromObject(obj: T): F

    fun toObject(obj: F): T
}