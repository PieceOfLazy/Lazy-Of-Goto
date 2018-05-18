package lazy.of.go.to.common

import javax.inject.Inject

/**
 * @author piece.of.lazy
 */
class LogImpl @Inject constructor(tag: String?): Log {

    private val log = lazy.of.framework.library.util.Log(tag)

    override fun v(msg: String) {
        log.v(msg)
    }

    override fun d(msg: String) {
        log.d(msg)
    }

    override fun e(msg: String) {
        log.e(msg)
    }
}