package lazy.of.go.to.base.feature

import kotlin.reflect.KClass

/**
 * @author lazy.of.zpdl
 */
interface GetFeature {
    fun <T: Any> getFeature(type: KClass<T>): T?
}