package com.example.crudlogin.ui.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudlogin.data.local.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val loginError: String? = null
)

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginChange(email: String, pass: String) {
        val emailError = if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) null else "Email no válido"
        _uiState.value = LoginUiState(email = email, password = pass, emailError = emailError)
    }

    fun login() {
        if (!isFormValid()) {
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val user = userDao.getUserByEmail(_uiState.value.email).firstOrNull()
            if (user != null && user.password == _uiState.value.password) {
                if (!user.estado) { // Comprueba si el usuario está activo
                    _uiState.value = _uiState.value.copy(isLoading = false, loginSuccess = true)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, loginError = "La cuenta de usuario está inactiva")
                }
            } else {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, loginError = "Credenciales incorrectas")
            }
        }
    }

    private fun isFormValid(): Boolean {
        return _uiState.value.emailError == null && _uiState.value.password.isNotEmpty()
    }
}