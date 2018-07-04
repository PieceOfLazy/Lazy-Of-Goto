package lazy.of.go.to.db

import com.google.firebase.firestore.FirebaseFirestore
import lazy.of.go.to.common.LocalPreferences
import lazy.of.go.to.db.fb.FbDbUser
import lazy.of.go.to.domain.data.DbUser
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class DbInjection @Inject constructor() {

    @Inject
    lateinit var localPreferences: LocalPreferences

    private val db = FirebaseFirestore.getInstance()

    private val userFbDB: FbDbUser by lazy {
        FbDbUser(db, object : DbListener {
            override fun getUserUUID(): String {
                return localPreferences.getValue(LocalPreferences.KEY_USER_UUID, "")
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getDB(type: KClass<T>): T {
        when(type) {
            DbUser::class -> {
                return userFbDB as T
            }
        }
//        if (type.is is FbDbUser) {
//            @Suppress("UNCHECKED_CAST")
//            return this as T
//        }
        TODO("not implemented db manager") //To change body of created functions use File | Settings | File Templates.
    }
}