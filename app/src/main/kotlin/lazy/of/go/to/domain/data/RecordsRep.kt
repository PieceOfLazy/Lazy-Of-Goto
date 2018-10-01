package lazy.of.go.to.domain.data

import io.reactivex.Observable
import lazy.of.go.to.domain.entity.EntitySet
import lazy.of.go.to.domain.entity.RecordEntity
import java.util.*

interface RecordsRep {
    fun set(idx: String, entity: RecordEntity): Observable<Unit>

    fun get(idx: String, date: Date): Observable<EntitySet<RecordEntity>>
    fun get(idx: String, startDate: Date, endDate: Date): Observable<List<RecordEntity>>
}