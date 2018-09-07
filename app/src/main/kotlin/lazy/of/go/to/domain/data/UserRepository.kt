package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.EntitySet
import lazy.of.go.to.domain.entity.UserEntity

interface UserRepository {
    fun set(user: UserEntity): Observable<Unit>

    fun get(): Observable<UserEntity>
    fun getUser(): UserEntity
}