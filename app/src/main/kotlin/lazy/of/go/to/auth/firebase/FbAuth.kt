package lazy.of.go.to.auth.firebase

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import lazy.of.go.to.R
import lazy.of.go.to.auth.LazyAuth
import lazy.of.go.to.auth.LazyUser
import lazy.of.go.to.configure.ActivityRequestCode
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lazy.of.zpdl
 */
@Singleton
class FbAuth @Inject constructor() : LazyAuth {
    init {
        Log.d("Lazy:","Create FbAuth")
    }
    private val auth = FirebaseAuth.getInstance()

    private var googleSignInClient: GoogleSignInClient? = null

    override fun currentUser(): LazyUser? {
        auth.currentUser?.let {
            return FbUser(it)
        }
        return null
    }

    fun signInActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?, listener: OnFbAuthCompleteListener): Boolean {
        when(ActivityRequestCode.generate(requestCode)) {
            ActivityRequestCode.AUTH_GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    authWithGoogle(account, activity, listener)
                } catch (e: ApiException) {
                    listener.onComplete(true, currentUser())
                }
                return true
            }
            else -> {
                return false
            }
        }
    }

    fun signInWithGoogle(activity: Activity) {
        Log.i("FbAuth", "1 signInWithGoogle ${auth.currentUser?.uid}")
        if(googleSignInClient == null) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            googleSignInClient = GoogleSignIn.getClient(activity, gso)
        }
//
//        googleApiClient?.let {
//            Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
//        }

        Log.i("FbAuth", "2 signInWithGoogle ${auth.currentUser?.uid}")
        val signInIntent = googleSignInClient?.signInIntent
        signInIntent.let {
            activity.startActivityForResult(signInIntent,
                    ActivityRequestCode.AUTH_GOOGLE_SIGN_IN.ordinal)
        }
    }

    private fun authWithGoogle(acct: GoogleSignInAccount, activity: Activity, listener: OnFbAuthCompleteListener) {
        listener.onStart()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, { task ->
                    if (task.isSuccessful) {
                        listener.onComplete(false, currentUser())
                    } else {
                        listener.onComplete(false, null)
                    }
                })
    }

    interface OnFbAuthCompleteListener {
        fun onStart()
        fun onComplete(Immediately: Boolean, user: LazyUser?)
    }
}

