package lazy.of.go.to.db.fb

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import lazy.of.go.to.db.DbListener
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.exception.AppExceptionCode
import com.google.firebase.firestore.DocumentReference



class FbDbSetting constructor(private val db: FirebaseFirestore) {

    companion object {
        const val DB_NAME = "Setting"
    }

}