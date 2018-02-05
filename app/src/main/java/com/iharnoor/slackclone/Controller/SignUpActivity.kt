package com.iharnoor.slackclone.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.iharnoor.slackclone.R
import com.iharnoor.slackclone.Services.AuthService
import com.iharnoor.slackclone.Services.UserDataService
import com.iharnoor.slackclone.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*

class SignUpActivity : AppCompatActivity() {
    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        spinnerSignup.visibility = View.INVISIBLE
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
        enableSpinner(true)
        val userName = userNameSignup.text.toString()
        val email = emailIDSignup.text.toString()
        val password = passwordTxtSignup.text.toString()
        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess)
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess)
                            AuthService.createUser(this, userName, email, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess) {
//                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
//                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else reportError()
                            }
                        else reportError()
                    }
                else reportError()
            }
        else {
            Toast.makeText(this, "Make sure user name, email and password are filled in", Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }
    }

    fun reportError() {
        Toast.makeText(this, "Error in processing with the API. Please try again", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean) {
        if (enable)
            spinnerSignup.visibility = View.VISIBLE
        else
            spinnerSignup.visibility = View.INVISIBLE
        createUserSignup.isEnabled = !enable
        avatarImgSignup.isEnabled = !enable
        generateBckgrdColBtn.isEnabled = !enable
    }
}
