package com.iharnoor.slackclone.Utilities

const val BASE_URL = "https://slackclonechatapp.herokuapp.com/v1/"
//const val BASE_URL = "http://10.0.2.2:3005/v1/"
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_CREATE_USER = "${BASE_URL}user/add"

//Broadcasting constants
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"