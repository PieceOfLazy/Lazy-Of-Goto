package lazy.of.go.to.db

import io.reactivex.Observable
import lazy.of.go.to.db.data.User

interface DbUser {

    fun observableSetUser(user: User): Observable<Unit>

    fun observableGetUser(user: User): Observable<User>

    fun observableGetUser(): Observable<User>

    fun getUser(): User
}