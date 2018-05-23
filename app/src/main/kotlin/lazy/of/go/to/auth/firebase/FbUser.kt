package lazy.of.go.to.auth.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import lazy.of.go.to.auth.LazyUser
/**
 * @author lazy.of.zpdl
 */
class FbUser(private val user: FirebaseUser): LazyUser {

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

    fun getUser(): FirebaseUser = user
}