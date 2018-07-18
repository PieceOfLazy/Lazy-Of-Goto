package lazy.of.go.to.common

class StringUtil {

    companion object {
        fun isEmpty(str: CharSequence?): Boolean {
            return str == null || str.isEmpty()
        }
    }
}