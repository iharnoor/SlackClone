package com.iharnoor.slackclone.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.iharnoor.slackclone.Utilities.URL_CREATE_USER
import com.iharnoor.slackclone.Utilities.URL_LOGIN
import com.iharnoor.slackclone.Utilities.URL_REGISTER
import org.json.JSONException
import org.json.JSONObject

object AuthService {
    var isLoggedIn = false
    var emailId = ""
    var authToken = ""

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

    fun loginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {
        val JSONdata = JSONObject()
        JSONdata.put("email", email)
        JSONdata.put("password", password)
        val requestBody = JSONdata.toString()

        val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener { response ->
            //            parsing
            try {
                emailId = response.getString("user")
                authToken = response.getString("token")
                isLoggedIn = true
                complete(true)
            } catch (e: JSONException) {
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(false)
            }
//            println(response)
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
        Volley.newRequestQueue(context).add(loginRequest)
    }

    fun createUser(context: Context, name: String, email: String, avatarName: String, avatarColor: String, complete: (Boolean) -> Unit) {
        val JSONdata = JSONObject()
        JSONdata.put("name", name)
        JSONdata.put("email", email)
        JSONdata.put("avatarName", avatarName)
        JSONdata.put("avatarColor", avatarColor)
        val requestBody = JSONdata.toString()

        val createRequest = object : JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->
            //            parsing
            try {
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")
                complete(true)
            } catch (e: JSONException) {
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(false)
            }
//            println(response)
        }, Response.ErrorListener { error ->
            Log.d("Error", "Could not add user: $error")
            complete(false)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $authToken")
                return headers
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }
        Volley.newRequestQueue(context).add(createRequest)
    }
}
//String request: Expected a string.