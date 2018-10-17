package com.lambadam.donteatalone.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.lambadam.domain.auth.AuthType
import com.lambadam.domain.auth.AuthType.GOOGLE
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.post.PostActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this,
                LoginViewModelFactory.getInstance(applicationContext))
                .get(LoginViewModel::class.java)
    }

    private val facebookCallback by lazy{ CallbackManager.Factory.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeFacebookButton()
        initializeGoogleButton()
        viewModelLoginObserver()
    }

    private fun initializeFacebookButton(){
        login_button.setReadPermissions("email", "public_profile")
        login_button.registerCallback(facebookCallback, object: FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                login(AuthType.FACEBOOK,loginResult.accessToken.token)
            }
            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }
            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
    }

    private fun login(authType: AuthType,token: String) {
        viewModel.login(authType,token)
    }

    private fun initializeGoogleButton() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build()

        val googleSignInClient= GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener{
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun viewModelLoginObserver() {
        viewModel.loginSuccess.observe(this, Observer{ navigateToPostActivity()})
        viewModel.failure.observe(this,Observer{ notify(it)})
    }

    private fun navigateToPostActivity() {
        startActivity(Intent(this, PostActivity::class.java))
    }

    private fun notify(messageId: Int?) {
        messageId?.let {
            Snackbar.make(root, getString(it), Snackbar.LENGTH_SHORT).show()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let {
                    login(GOOGLE, it)
                }
            }
            catch (e: ApiException) {
                Log.e(TAG, e.toString())
            }
        } else{
            facebookCallback.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "LoginActivity"
    }
}
