package lazy.of.go.to.db

interface OnDbListener<T> {
    fun onSuccess(data: T)
    fun onFail(code: Int)
}