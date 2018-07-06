package lazy.of.go.to.exception

class AppException constructor(val exceptionCode: AppExceptionCode, cause: Throwable? = null) : Exception(cause) {
    constructor(exceptionCode: AppExceptionCode) : this(exceptionCode, null)
}