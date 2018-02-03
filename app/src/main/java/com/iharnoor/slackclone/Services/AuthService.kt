package com.iharnoor.slackclone.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.iharnoor.slackclone.Utilities.URL_REGISTER
import org.json.JSONObject

object AuthService {

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {

        val JSONdata = JSONObject()
        JSONdata.put("email", email)
        JSONdata.put("password", password)
        val requestBody = JSONdata.toString()
        // creating an object below
        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response ->
            println(response)
            complete(true)
        }, Response.ErrorListener { error ->
            Log.d("Error", "Could not register user: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }
        Volley.newRequestQueue(context).add(registerRequest)
    }
}
//String request: Expected a string.