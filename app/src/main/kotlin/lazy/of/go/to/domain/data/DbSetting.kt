package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.SettingEntity

interface DbSetting {
    fun set(setting: SettingEntity): Observable<Unit>

    fun get(idx: String): Observable<SettingEntity>
}