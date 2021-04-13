package com.daya.notification_prototype.view.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.daya.notification_prototype.R
import com.daya.notification_prototype.databinding.ActivityLoginBinding
import com.daya.notification_prototype.util.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding : ActivityLoginBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginWithSt3.setOnClickListener {
            val intent = requestGSO(DOMAIN_st3).signInIntent
            loginGso.launch(intent)
        }
        binding.loginWithItt.setOnClickListener {
            val intent = requestGSO(DOMAIN_ITT).signInIntent
            loginGso.launch(intent)
        }
    }

    private fun requestGSO(domain: String): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .setHostedDomain(domain)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this,gso)
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.i("signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    toast("login successful, ${user?.displayName}", Toast.LENGTH_LONG)

                } else {
                    // If sign in fails, display a message to the user.
                    Timber.i("signInWithCredential:failure ${task.exception}")
                    toast("login failed, ${task.exception?.localizedMessage}", Toast.LENGTH_LONG)
                }
            }
    }

    var loginGso = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.w("Google sign in failed $e")
                toast("google sign in failed ${e.localizedMessage}")
            }
        }
    }



    companion object {
        private const val DOMAIN_ITT = "ittelkom-pwt.ac.id"
        private const val DOMAIN_st3 = "st3telkom.ac.id"

        private const val GOOGLE_SIGNIN_REQUEST_CODE = 231
    }
}
