package lazy.of.go.to.common

import javax.inject.Inject

/**
 * @author piece.of.lazy
 */
class LogImpl @Inject constructor(tag: String?): Log {

    private val log = lazy.of.framework.library.util.Log(tag)

    override fun d(msg: String) {
        log.d(msg)
    }

    override fun i(msg: String) {
        log.i(msg)
    }

    override fun e(msg: String) {
        log.e(msg)
    }
}