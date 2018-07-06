package lazy.of.go.to.auth

/**
 * @author lazy.of.zpdl
 */
interface LazyAuth {
    fun currentUser(): AuthUser?

    fun signOut()
}
