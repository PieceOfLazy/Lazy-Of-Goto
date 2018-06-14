package lazy.of.go.to.db

import lazy.of.go.to.db.data.User

interface DbUser {

    fun setUser(user: User, listener: OnDbListener<User>)

    fun getUser(): User

    fun getUser(listener: OnDbListener<User>)
}