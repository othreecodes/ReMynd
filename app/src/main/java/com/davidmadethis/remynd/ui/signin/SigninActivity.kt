package com.davidmadethis.remynd.ui.signin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.davidmadethis.remynd.R
import com.davidmadethis.remynd.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_signin.*
import ng.apk.instantemploy.ui.base.BaseActivity

class SigninActivity : BaseActivity() {
    val RC_SIGN_IN = 9090

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        sign_in_button.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = mGoogleSignInClient.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            showToast("Welcome, ${account.displayName}")
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("err", "signInResult:failed code=" + e.statusCode)

            showToast("You need to sign into your Google account to use this app.", "error")


        }

    }

}
