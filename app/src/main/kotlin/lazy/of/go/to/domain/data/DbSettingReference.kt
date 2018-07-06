package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.SettingReference

interface DbSettingReference {
    fun add(userUUID: String): Observable<Unit>
    fun set(settingReference: SettingReference): Observable<Unit>

    fun get(): Observable<List<SettingReference>>
}