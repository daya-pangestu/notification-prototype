package com.daya.notification_prototype.view.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import com.daya.notification_prototype.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

class GoogleSignInResultContract : ActivityResultContract<String, Task<GoogleSignInAccount>>() {

    override fun createIntent(context: Context, input: String?): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .setHostedDomain(input)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount> {
        val result = ActivityResult(resultCode,intent)
        return GoogleSignIn.getSignedInAccountFromIntent(result.data)
    }


}