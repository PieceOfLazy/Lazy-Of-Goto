package lazy.of.go.to.domain.entity

data class User (
        val isAnonymous: Boolean = false,
        val providerId: String = "",
        val name: String = "",
        val email: String = "",
        val photoUrl: String = "")