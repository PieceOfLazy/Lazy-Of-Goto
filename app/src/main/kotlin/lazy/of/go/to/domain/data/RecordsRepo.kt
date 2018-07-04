package lazy.of.go.to.domain.data

import io.reactivex.Observable

interface RecordsRepo {
    fun get(): Observable<List<RecordRepo>>
}