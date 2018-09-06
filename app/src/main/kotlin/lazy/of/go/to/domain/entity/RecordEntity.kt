package lazy.of.go.to.domain.entity

import lazy.of.go.to.db.data.RecordTimeDbData
import java.util.*

data class RecordEntity(
        val date: Date,
        val name: String = "",
        val location: LocationEntity? = null,
        val settingTime: SettingTime? = null,
        val recordTime: RecordTimeEntity? = null
)