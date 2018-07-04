package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.User

interface DbUser {
    fun set(user: User): Observable<Unit>

    fun get(): Observable<User>
    fun getUser(): User
}