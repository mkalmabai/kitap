package com.example.kitap.ui.fragments.registration

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kitap.utils.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel
@Inject
 constructor(
    private val firebaseAuth: FirebaseAuth
)
 : ViewModel() {

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    var _status = MutableLiveData<Resource<String>>()
    val status: LiveData<Resource<String>> = _status
    fun setGoogleSignInLauncher( launcher: ActivityResultLauncher<Intent>) {
        googleSignInLauncher = launcher
    }
    fun signInWithGoogle(activity:Activity) {
        _status.value = Resource.Loading()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("171827503043-6hcnps09krkodi6372lk1ouvtgjuqbak.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
    fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            signInWithGoogle(account!!)
        } catch (e: ApiException) {
            _status.value = Resource.Error("Google sign-in failed")
        }
    }
    private fun signInWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                if (authResult.user != null) {
                    _status.postValue(Resource.Success(String()))
                } else {
                    _status.postValue(Resource.Error("Google sign-in failed"))
                }
            } catch (e: Exception) {
                _status.postValue(Resource.Error("Error: ${e.message}"))
            }
        }
    }


}