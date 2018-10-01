package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.SettingRefEntity

interface SettingRefRep {
    fun create(userUUID: String): Observable<Unit>
    fun set(settingReference: SettingRefEntity): Observable<Unit>

    fun get(): Observable<List<SettingRefEntity>>
}