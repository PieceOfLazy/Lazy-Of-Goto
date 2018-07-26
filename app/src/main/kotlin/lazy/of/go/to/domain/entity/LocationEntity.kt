package lazy.of.go.to.domain.entity

data class LocationEntity(
        val latitude: Double = Double.NaN,
        val longitude: Double = Double.NaN) {

    fun isValid(): Boolean = latitude in -90..90 && longitude in -180..180
}