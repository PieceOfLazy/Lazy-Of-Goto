package lazy.of.go.to.db

import com.google.firebase.firestore.FirebaseFirestore
import lazy.of.go.to.common.LocalPreferences
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class DbInjection @Inject constructor() {

    @Inject
    lateinit var localPreferences: LocalPreferences

    private val db = FirebaseFirestore.getInstance()

    private val fbDbUser: DbUser by lazy {
        DbUser(db, object : DbListener {
            override fun getUserUUID(): String {
                return localPreferences.getValue(LocalPreferences.KEY_USER_UUID, "")
            }
        })
    }

    fun <T : Any> getDB(type: KClass<T>): T {
        when(type) {
            DbUser::class -> {
                @Suppress("UNCHECKED_CAST")
                return fbDbUser as T
            }
        }
//        if (type.is is FbDbUser) {
//            @Suppress("UNCHECKED_CAST")
//            return this as T
//        }
        TODO("not implemented db manager") //To change body of created functions use File | Settings | File Templates.
    }
}