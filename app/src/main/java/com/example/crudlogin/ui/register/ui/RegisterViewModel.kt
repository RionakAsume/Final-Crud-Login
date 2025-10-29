package com.example.crudlogin.ui.register.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudlogin.data.local.User
import com.example.crudlogin.data.local.UserDao
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val lastName: String = "",
    val dni: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val nameError: String? = null,
    val lastNameError: String? = null,
    val dniError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val isFormValid: Boolean = false
)

class RegisterViewModel(private val userDao: UserDao) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _registrationStatus = MutableSharedFlow<Boolean>()
    val registrationStatus = _registrationStatus.asSharedFlow()

    fun onRegisterChange(
        name: String, lastName: String, dni: String,
        phone: String, email: String, password: String, confirm: String
    ) {
        val nameError = if (!name.matches(Regex("[a-zA-Z ]+"))) "El nombre solo debe contener letras" else null
        val lastNameError = if (!lastName.matches(Regex("[a-zA-Z ]+"))) "El apellido solo debe contener letras" else null
        val dniError = if (!dni.matches(Regex("\\d+"))) "El DNI debe ser numérico" else null
        val phoneError = if (!phone.matches(Regex("\\d+"))) "El teléfono debe ser numérico" else null
        val emailError = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email no válido" else null
        val passwordError = if (password.length < 4) "La contraseña debe tener al menos 4 caracteres" else null
        val confirmPasswordError = if (password != confirm) "Las contraseñas no coinciden" else null

        _uiState.update {
            it.copy(
                name = name, lastName = lastName, dni = dni, phone = phone, email = email, password = password, confirmPassword = confirm,
                nameError = nameError, lastNameError = lastNameError, dniError = dniError, phoneError = phoneError,
                emailError = emailError, passwordError = passwordError, confirmPasswordError = confirmPasswordError,
                isFormValid = nameError == null && lastNameError == null && dniError == null && phoneError == null &&
                        emailError == null && passwordError == null && confirmPasswordError == null
            )
        }
    }

    fun registerUser() {
        if (!_uiState.value.isFormValid) return

        viewModelScope.launch {
            val user = User(
                name = _uiState.value.name,
                lastName = _uiState.value.lastName,
                dni = _uiState.value.dni,
                phone = _uiState.value.phone,
                email = _uiState.value.email,
                password = _uiState.value.password
            )
            userDao.insertUser(user)
            _registrationStatus.emit(true)
        }
    }
}