package lazy.of.go.to.db.firebase

import com.google.firebase.firestore.FirebaseFirestore
import lazy.of.framework.library.util.Log
import lazy.of.go.to.db.DbUser
import lazy.of.go.to.db.OnDbListener
import lazy.of.go.to.db.data.User

class FbDbUser constructor(private val db: FirebaseFirestore, private val listener: OnListener): DbUser {

    interface OnListener {
        fun getUserUUID(): String
    }

    private var _user: User? = null

    override fun setUser(user: User, listener: OnDbListener<User>) {
        Log.i("Lazy", "this.listener.getUserUUID() : "+this.listener.getUserUUID())
        val docRef = db.collection("User").document(this.listener.getUserUUID())
        docRef
                .set(user)
                .addOnSuccessListener {
                    getUser(listener)
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    listener.onFail(0)
                }
    }

    override fun getUser(): User {
        return _user ?: User()
    }

    override fun getUser(listener: OnDbListener<User>) {
        val docRef = db.collection("User").document(this.listener.getUserUUID())
        docRef.get()
                .addOnSuccessListener {
                    val data = it.toObject(User::class.java)
                    data?.let {
                        listener.onSuccess(data)
                    } ?: run {
                        listener.onFail(0)
                    }
                }
                .addOnFailureListener {
                    listener.onFail(0)
                }
    }
}