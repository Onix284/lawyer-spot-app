package com.example.lawyerspot.AllViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerspot.Data.KtorClient
import com.example.lawyerspot.Data.Model.UserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val ktorClient: KtorClient
) : ViewModel() {

    private val _signupResult = MutableStateFlow<String?>(null)
    val signupResult : StateFlow<String?> = _signupResult

    fun signUp(userRequest: UserRequest){
        viewModelScope.launch {
            try {
                val response = ktorClient.signUp(userRequest)
                _signupResult.value = response.message
            }
            catch (e : Exception){
                _signupResult.value = "Sign up Failed ${e.message}"
            }
        }
    }

}