package lazy.of.go.to.db

import kotlin.reflect.KClass

interface DbMng {
    interface OnListener<T> {
        fun onSuccess(data: T)
        fun onFail(code: Int)
    }

    fun <T : Any> getDB(type: KClass<T>): T
}