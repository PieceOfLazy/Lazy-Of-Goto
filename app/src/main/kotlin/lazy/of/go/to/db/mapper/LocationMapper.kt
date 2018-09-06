package lazy.of.go.to.db.mapper

import com.google.firebase.firestore.GeoPoint
import lazy.of.go.to.domain.entity.LocationEntity

class LocationMapper {

    companion object : EntityNullableMapper<LocationEntity, GeoPoint>() {
        override fun fromObject(obj: GeoPoint): LocationEntity {
            return LocationEntity(obj.latitude, obj.longitude)
        }

        override fun toObject(obj: LocationEntity): GeoPoint {
            return GeoPoint(obj.latitude, obj.longitude)
        }
    }
}