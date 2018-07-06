package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.Setting
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.domain.entity.User

interface DbSettings {
    fun set(settingReference: SettingReference): Observable<Unit>

    fun get(): Observable<SettingReference>
    fun count()
}