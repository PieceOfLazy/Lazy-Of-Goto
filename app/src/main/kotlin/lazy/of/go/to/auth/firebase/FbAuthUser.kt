package lazy.of.go.to.auth.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import lazy.of.go.to.auth.AuthUser
/**
 * @author lazy.of.zpdl
 */
class FbAuthUser(private val user: FirebaseUser): AuthUser {

    override val uuid: String
        get() = user.uid

    override val email: String?
        get() = user.email

    override val emailVerified: Boolean
        get() = user.isEmailVerified

    override val phoneNumber: String?
        get() = user.phoneNumber

    override val displayName: String?
        get() = user.displayName

    override val photoURL: Uri?
        get() = user.photoUrl

    override val isAnonymous: Boolean
        get() = user.isAnonymous

    override val providerId: String
        get() = user.providerId

    fun getUser(): FirebaseUser = user
}