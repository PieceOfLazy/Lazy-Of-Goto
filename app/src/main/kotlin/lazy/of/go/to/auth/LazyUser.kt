package lazy.of.go.to.auth

import android.net.Uri

/**
 * @author lazy.of.zpdl
 */
interface LazyUser {

    val uuid: String

    val email: String?

    val emailVerified: Boolean

    val phoneNumber: String?

    val displayName: String?

    val photoURL: Uri?

    val isAnonymous: Boolean

    val providerId: String
}