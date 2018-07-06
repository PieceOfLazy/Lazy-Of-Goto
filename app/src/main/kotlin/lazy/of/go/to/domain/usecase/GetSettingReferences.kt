package lazy.of.go.to.domain.usecase

import io.reactivex.Observable
import lazy.of.framework.library.tree.TreeNode
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.usecase.UseCase

class GetSettingReferences constructor(userUUID: String, private val dbSettingReference: DbSettingReference): UseCase<String, List<SettingReference>>(userUUID) {

    override fun executeUseCase(request: String) {
        getLoadingFeature()?.loadingStart()
        dbSettingReference
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

    private fun getCheckMySettingReferences(userUUID: String, list: List<SettingReference>): Observable<List<SettingReference>> {
        return if(containMine(userUUID, list)) {
            Observable.just(list)
        } else {
            dbSettingReference
                    .add(userUUID)
                    .flatMap {
                        dbSettingReference.get()
                    }
        }
    }

    private fun containMine(userUUID: String, list: List<SettingReference>): Boolean {
        list.filter {
            it.userUUID == userUUID
        }.first {
            return true
        }
        return false
    }
}