package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.Setting

interface DbSetting {
    fun set(setting: Setting): Observable<Unit>

    fun get(idx: String): Observable<Setting>
}