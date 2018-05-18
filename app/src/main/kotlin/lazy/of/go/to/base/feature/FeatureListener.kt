package lazy.of.go.to.base.feature

import kotlin.reflect.KClass

/**
 * @author lazy.of.zpdl
 */
interface FeatureListener {
    fun <T: Any> onGetFeature(type: KClass<T>): T?
}