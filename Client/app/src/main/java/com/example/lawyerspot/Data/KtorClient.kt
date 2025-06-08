package com.example.lawyerspot.Data

import com.example.lawyerspot.Data.Model.ApiResponse
import com.example.lawyerspot.Data.Model.UserRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import javax.inject.Inject

class KtorClient @Inject constructor(){

    private val client = HttpClient(Android){
        install(ContentNegotiation){
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private val baseUrl = "http://10.0.2.2:3000/"

    suspend fun signUp(user : UserRequest) : ApiResponse {

        val response : HttpResponse = client.post("$baseUrl/auth/signup"){
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(user)
        }
        return if(response.status.isSuccess()){
            response.body()
        } else{
            ApiResponse(false, "Signup Failed ${response.status.value}")
        }
    }

}