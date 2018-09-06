package lazy.of.go.to.db.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import lazy.of.go.to.domain.entity.SettingTime

data class RecordDbData(
        val date: Timestamp,
        val name: String = "",
        val location: GeoPoint? = null,
        val settingTime: SettingTime? = null,
        val recordTime: RecordTimeDbData? = null
)