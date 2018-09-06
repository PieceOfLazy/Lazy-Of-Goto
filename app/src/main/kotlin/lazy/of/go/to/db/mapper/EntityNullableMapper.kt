package lazy.of.go.to.db.mapper

abstract class EntityNullableMapper<F, T> : EntityMapper<F, T> {
    fun fromNullableObject(obj: T?): F? {
        return if (obj != null)
            fromObject(obj)
        else
            null
    }

    fun toNullableObject(obj: F?): T? {
        return if (obj != null)
            toObject(obj)
        else
            null
    }
}