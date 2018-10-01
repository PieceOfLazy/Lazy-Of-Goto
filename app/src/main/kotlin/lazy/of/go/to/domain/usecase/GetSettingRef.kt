package lazy.of.go.to.domain.usecase

import io.reactivex.Observable
import lazy.of.go.to.domain.data.SettingRefRep
import lazy.of.go.to.domain.entity.SettingRefEntity
import lazy.of.go.to.usecase.UseCase

class GetSettingRef constructor(userUUID: String, private val settingRefRep: SettingRefRep): UseCase<String, List<SettingRefEntity>>(userUUID) {

    override fun executeUseCase(request: String) {
        getLoadingFeature()?.loadingStart()
        settingRefRep
                .get()
                .flatMap {
                    getCheckMySettingReferences(request, it)
                }
                .subscribe({
                    success(it)
                }, {
                    error(it)
                }, {
                    getLoadingFeature()?.loadingEnd()
                })
    }

    private fun getCheckMySettingReferences(userUUID: String, list: List<SettingRefEntity>): Observable<List<SettingRefEntity>> {
        return if(containMine(userUUID, list)) {
            Observable.just(list)
        } else {
            settingRefRep
                    .create(userUUID)
                    .flatMap {
                        settingRefRep.get()
                    }
        }
    }

    private fun containMine(userUUID: String, list: List<SettingRefEntity>): Boolean {
        return !list.none {
            it.userUUID == userUUID
        }
    }
}