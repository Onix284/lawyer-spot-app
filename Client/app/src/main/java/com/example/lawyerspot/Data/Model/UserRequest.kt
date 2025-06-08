package com.example.lawyerspot.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name : String,
    val email : String,
    val password : String,
    val role : String,
    val imageURI : String
)