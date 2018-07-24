package lazy.of.go.to.db

import com.google.firebase.firestore.FirebaseFirestore
import lazy.of.go.to.common.LocalPreferences
import lazy.of.go.to.db.fb.FbDbSetting
import lazy.of.go.to.db.fb.FbDbSettingReference
import lazy.of.go.to.db.fb.FbDbUser
import lazy.of.go.to.domain.data.DbSetting
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.data.DbUser
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class DbInjection @Inject constructor() {

    @Inject
    lateinit var localPreferences: LocalPreferences

    private val db = FirebaseFirestore.getInstance()

    private val fbDbUser: FbDbUser by lazy {
        FbDbUser(db, object : DbListener {
            override fun getUserUUID(): String {
                return localPreferences.getValue(LocalPreferences.KEY_USER_UUID, "")
            }
        })
    }

    private val fbDbSettingReference: FbDbSettingReference by lazy {
        FbDbSettingReference(db, object : DbListener {
            override fun getUserUUID(): String {
                return localPreferences.getValue(LocalPreferences.KEY_USER_UUID, "")
            }
        })
    }

    private val fbDbSetting: FbDbSetting by lazy {
        FbDbSetting(db)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getDB(type: KClass<T>): T {
        when(type) {
            DbUser::class -> {
                return fbDbUser as T
            }
            DbSettingReference::class -> {
                return fbDbSettingReference as T
            }
            DbSetting::class -> {
                return fbDbSetting as T
            }
        }

        TODO("not implemented db manager") //To change body of created functions use File | Settings | File Templates.
    }
}