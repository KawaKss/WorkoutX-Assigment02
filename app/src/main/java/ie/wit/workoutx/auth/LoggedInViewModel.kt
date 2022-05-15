package ie.wit.workoutx.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import ie.wit.workoutx.firebase.FirebaseAuthManager


class LoggedInViewModel(app: Application) : AndroidViewModel(app) {

    var firebaseAuthManager : FirebaseAuthManager = FirebaseAuthManager(app)
    var liveFirebaseUser : MutableLiveData<FirebaseUser> = firebaseAuthManager.liveFirebaseUser
    var loggedOut : MutableLiveData<Boolean> = firebaseAuthManager.loggedOut

    fun logOut(context:Context) {
        firebaseAuthManager.logOut()
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener {
                // ...
            }
    }
}