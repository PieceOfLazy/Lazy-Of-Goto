package lazy.of.go.to.domain.entity

data class RecordTimeEntity(
        val goCheckTime: Int? = null,
        val goRecordTime: Int? = null,
        val goPhotoUrl: String = "",

        val backCheckTime: Int? = null,
        val backRecordTime: Int? = null,
        val backPhotoUrl: String = ""
)