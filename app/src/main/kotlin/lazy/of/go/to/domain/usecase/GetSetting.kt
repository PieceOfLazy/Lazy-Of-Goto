package lazy.of.go.to.domain.usecase

import lazy.of.go.to.domain.data.SettingRep
import lazy.of.go.to.domain.entity.SettingEntity
import lazy.of.go.to.usecase.UseCase

class GetSetting constructor(idx: String, private val dbSetting: SettingRep): UseCase<String, SettingEntity>(idx) {

    override fun executeUseCase(request: String) {
        getLoadingFeature()?.loadingStart()
        dbSetting
                .get(request)
                .subscribe({
                    success(it)
                }, {
                    error(it)
                }, {
                    getLoadingFeature()?.loadingEnd()
                })
    }
}