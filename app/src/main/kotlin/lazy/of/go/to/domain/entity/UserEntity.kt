package lazy.of.go.to.domain.entity

data class UserEntity (
        val isAnonymous: Boolean = false,
        val providerId: String = "",
        val name: String = "",
        val email: String = "",
        val photoUrl: String = "")