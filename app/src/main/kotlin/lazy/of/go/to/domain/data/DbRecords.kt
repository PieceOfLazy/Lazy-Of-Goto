package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.SettingEntity
import lazy.of.go.to.domain.entity.SettingReference

interface DbRecords {
    fun set(setting: SettingEntity): Observable<Unit>

    fun get(): Observable<SettingReference>
    fun count()
}