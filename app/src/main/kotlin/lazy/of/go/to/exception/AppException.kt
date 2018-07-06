package lazy.of.go.to.exception

class AppException constructor(val exceptionCode: AppExceptionCode, cause: Throwable? = null) : Exception(cause)