package lazy.of.go.to.common

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun basedDayDate(date: Date): Date {
            val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val dayString = formatter.format(date)
            return formatter.parse(dayString)
        }
    }
}