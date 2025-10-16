package com.example.crudlogin.ui.register.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudlogin.data.local.User
import com.example.crudlogin.data.local.UserDao
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val lastName: String = "",
    val dni: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = ""
)

class RegisterViewModel(private val userDao: UserDao) : ViewModel() {

    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    private val _registrationStatus = MutableSharedFlow<Boolean>()
    val registrationStatus = _registrationStatus.asSharedFlow()

    fun onRegisterChanged(
        name: String,
        lastName: String,
        dni: String,
        phone: String,
        email: String,
        password: String
    ) {
        _uiState.value = RegisterUiState(name, lastName, dni, phone, email, password)
    }

    fun registerUser() {
        viewModelScope.launch {
            val user = User(
                name = uiState.value.name,
                lastName = uiState.value.lastName,
                dni = uiState.value.dni,
                phone = uiState.value.phone,
                email = uiState.value.email,
                password = uiState.value.password
            )
            userDao.insertUser(user)
            _registrationStatus.emit(true)
        }
    }
}