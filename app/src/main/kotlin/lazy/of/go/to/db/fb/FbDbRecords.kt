package lazy.of.go.to.db.fb

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import lazy.of.go.to.common.StringUtil


class FbDbRecords constructor(private val db: FirebaseFirestore) {

    companion object {
        private const val DB_NAME = "Records"

        fun getIdx(db: FirebaseFirestore, idx: String): String {
            return if (StringUtil.isEmpty(idx)) {
                db.collection(FbDbSetting.DB_NAME).document().id
            } else {
                idx
            }
        }

        fun getDocument(db: FirebaseFirestore, idx: String = ""): DocumentReference {
            return if(StringUtil.isEmpty(idx)) {
                db.collection(DB_NAME).document()
            } else {
                db.collection(DB_NAME).document(idx)
            }
        }
    }

}