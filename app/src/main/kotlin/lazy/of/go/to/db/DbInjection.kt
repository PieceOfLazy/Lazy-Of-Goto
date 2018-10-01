package lazy.of.go.to.db

import com.google.firebase.firestore.FirebaseFirestore
import lazy.of.go.to.common.LocalPreferences
import lazy.of.go.to.db.fb.SettingDb
import lazy.of.go.to.db.fb.SettingRefDb
import lazy.of.go.to.db.fb.UserDb
import lazy.of.go.to.domain.data.SettingRefRep
import lazy.of.go.to.domain.data.SettingRep
import lazy.of.go.to.domain.data.UserRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class DbInjection @Inject constructor() {

    @Inject
    lateinit var localPreferences: LocalPreferences

    private val db = FirebaseFirestore.getInstance()

    private val fbDbUser: UserDb by lazy {
        UserDb(db, object : DbListener {
            override fun getUserUUID(): String {
                return localPreferences.getValue(LocalPreferences.KEY_USER_UUID, "")
            }
        })
    }

    private val fbDbSettingReference: SettingRefDb by lazy {
        SettingRefDb(db, object : DbListener {
            override fun getUserUUID(): String {
                return localPreferences.getValue(LocalPreferences.KEY_USER_UUID, "")
            }
        })
    }

    private val fbDbSetting: SettingDb by lazy {
        SettingDb(db)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getDB(type: KClass<T>): T {
        when(type) {
            UserRepository::class -> {
                return fbDbUser as T
            }
            SettingRefRep::class -> {
                return fbDbSettingReference as T
            }
            SettingRep::class -> {
                return fbDbSetting as T
            }
        }

        TODO("not implemented db manager") //To change body of created functions use File | Settings | File Templates.
    }
}