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
import com.lambadam.domain.auth.AuthType
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.post.PostActivity

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


        val login_button = findViewById<LoginButton>(R.id.login_button);

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
        });
    }

    private fun navigateToPostActivity() {
        startActivity(Intent(this,PostActivity::class.java))
    }

    fun login(authType: AuthType,token: String) {
        viewModel.login(authType,token)
    }
}
