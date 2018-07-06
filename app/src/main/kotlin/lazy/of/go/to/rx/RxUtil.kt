package lazy.of.go.to.rx

import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode

class RxUtil {
    companion object {
        fun appExceptionFromThrowable(throwable: Throwable) : AppException {
            return throwable as? AppException ?: AppException(AppExceptionCode.UNKNOWN)
        }
    }
}