package lazy.of.go.to.db

import javax.inject.Singleton

@Singleton
interface DbListener {

    fun getUserUUID(): String

}