package com.iharnoor.slackclone.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.iharnoor.slackclone.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun signUpBtnLoginClicked(view: View) {
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }

    fun loginBtnLoginClicked(view: View) {

    }
}
