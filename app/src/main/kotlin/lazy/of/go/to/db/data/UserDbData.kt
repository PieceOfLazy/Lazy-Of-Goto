package lazy.of.go.to.db.data

data class UserDbData (
        val isAnonymous: Boolean = false,
        val providerId: String = "",
        val name: String = "",
        val email: String = "",
        val photoUrl: String = "")