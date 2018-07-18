package lazy.of.go.to.db.fb

import com.google.firebase.firestore.FirebaseFirestore


class FbDbRecords constructor(private val db: FirebaseFirestore) {

    companion object {
        const val DB_NAME = "Records"
    }

}