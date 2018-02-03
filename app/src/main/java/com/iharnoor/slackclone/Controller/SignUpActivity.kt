package com.iharnoor.slackclone.Controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.iharnoor.slackclone.R
import com.iharnoor.slackclone.Services.AuthService
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*

class SignUpActivity : AppCompatActivity() {
    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun avatarImgClicked(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0)
            userAvatar = "light$avatar"
        else
            userAvatar = "dark$avatar"
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        avatarImgSignup.setImageResource(resourceId)
    }

    fun generateBackgroundClicked(view: View) {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        avatarImgSignup.setBackgroundColor(Color.rgb(r, g, b))
        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255
        avatarColor = "[$savedR, $savedG, $savedB]"
    }

    fun createUserClicked(view: View) {
        val email = emailIDSignup.text.toString()
        val password = passwordTxtSignup.text.toString()
        AuthService.registerUser(this, email, password) { registerSuccess ->
            if (registerSuccess) {
                AuthService.loginUser(this, email, password) { loginSuccess ->
                    if (loginSuccess) {
                        println("Token=" + AuthService.authToken)
                        println("EmailId=" + AuthService.emailId)
                    }
                }
            }
        }
    }
}
