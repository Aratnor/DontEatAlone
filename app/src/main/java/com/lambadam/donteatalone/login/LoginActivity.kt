package com.lambadam.donteatalone.login

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.lambadam.domain.auth.AuthType
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.post.PostActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this
                ,LoginViewModelFactory.getInstance()).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var TAG = "Facebook Login"
        val mCallbackManager : CallbackManager

        val mGoogleSignInClient : GoogleSignInClient

        val login_button = findViewById<LoginButton>(R.id.login_button)

        val signInButton = findViewById<SignInButton>(R.id.signInButton)

        viewModel.login.observe(this, Observer { navigateToPostActivity() })

        viewModel.failure.observe(this,Observer { Toast.makeText(this,this.getString(it),Toast.LENGTH_SHORT).show() })

        mCallbackManager = CallbackManager.Factory.create()

        login_button.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                login(AuthType.FACEBOOK,loginResult.accessToken.token);
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                // ...
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener{
            Log.i("Google","Google Login Clicked")
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
            /*val task = mGoogleSignInClient.silentSignIn()
            if (task.isSuccessful) {
                // There's immediate result available.
                val signInAccount = task.result!!
                login(AuthType.GOOGLE, signInAccount.idToken!!)
            }
            else {
                task.addOnCompleteListener {
                        try {
                            task.getResult(ApiException::class.java)
                        } catch (apiException: ApiException) {
                            throw apiException
                        }

                }
            }*/
        }
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebas
                val account = task.getResult(ApiException::class.java)!!
                login(AuthType.GOOGLE,account.idToken!!)
            }
            catch (e: ApiException) {
                Log.e("Error Google Login",e.toString());
            }
        }


    }

    private fun navigateToPostActivity() {
        startActivity(Intent(this,PostActivity::class.java))
    }

    fun login(authType: AuthType,token: String) {
        viewModel.login(authType,token)
    }

    companion object {
        private val RC_SIGN_IN = 9001
    }
}
