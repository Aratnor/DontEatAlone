package com.lambadam.donteatalone.post

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lambadam.donteatalone.R
import com.lambadam.donteatalone.login.LoginActivity

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        val changePage = Intent(this, LoginActivity::class.java)

        startActivity(changePage)
    }
}
