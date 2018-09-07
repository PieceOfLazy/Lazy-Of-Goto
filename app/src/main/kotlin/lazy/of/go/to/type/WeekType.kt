package lazy.of.go.to.type

enum class WeekType {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    companion object {
        val ENUMS = WeekType.values()
        val SIZE = ENUMS.size
    }
}