package lazy.of.go.to.db.data

data class RecordTimeDbData(
        val holiday: Boolean = false,

        val goCheckTime: Int? = null,
        val goRecordTime: Int? = null,
        val goPhotoUrl: String = "",

        val backCheckTime: Int? = null,
        val backRecordTime: Int? = null,
        val backPhotoUrl: String = ""
)