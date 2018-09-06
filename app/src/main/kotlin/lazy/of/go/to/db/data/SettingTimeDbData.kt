package lazy.of.go.to.db.data

data class SettingTimeDbData(
        val holiday: Boolean = false,
        val goTime: Int? = null,
        val backTime: Int? = null
)