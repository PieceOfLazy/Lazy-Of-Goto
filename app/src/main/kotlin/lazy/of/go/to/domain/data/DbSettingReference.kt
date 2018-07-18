package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.SettingReference

interface DbSettingReference {
    fun add(settingReference: SettingReference): Observable<Unit>
    fun set(settingReference: SettingReference): Observable<Unit>

    fun get(): Observable<List<SettingReference>>
}