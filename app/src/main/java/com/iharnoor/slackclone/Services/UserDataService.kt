package com.iharnoor.slackclone.Services

import android.graphics.Color
import java.util.*

/**
 * Created by hsingh9 on 2/4/2018.
 */
object UserDataService {
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun logout() {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        AuthService.isLoggedIn = false
        AuthService.authToken = ""
        AuthService.emailId = ""

    }

    fun parseAvatarColor(inputString: String): Int {
        val strippedColor = inputString.replace("[", "").replace("]", "").replace(",", "")

        var r = 0
        var g = 0
        var b = 0
        val scanner = Scanner(strippedColor)
        if (scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }
        return Color.rgb(r, g, b)
    }

}