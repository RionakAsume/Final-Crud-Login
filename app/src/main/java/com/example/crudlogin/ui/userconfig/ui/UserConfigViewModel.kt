package com.example.crudlogin.ui.userconfig.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudlogin.data.local.User
import com.example.crudlogin.data.local.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserConfigUiState(
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
    val passwordError: String? = null,

    val isFormValid: Boolean = false,
    val userDeactivated: Boolean = false,
    val userUpdated: Boolean = false
)

class UserConfigViewModel(private val userDao: UserDao, private val userEmail: String) : ViewModel() {

    private val _uiState = MutableStateFlow(UserConfigUiState())
    val uiState: StateFlow<UserConfigUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val user = userDao.getUserByEmail(userEmail).first()
            user?.let {
                _uiState.update {
                    it.copy(
                        name = user.name,
                        lastName = user.lastName,
                        dni = user.dni,
                        phone = user.phone,
                        email = user.email,
                        isFormValid = true
                    )
                }
            }
        }
    }

    fun onFieldChange(
        name: String, lastName: String, dni: String, phone: String, password: String, confirm: String
    ) {
        val nameError = if (!name.matches(Regex("[a-zA-Z ]+"))) "El nombre solo debe contener letras" else null
        val lastNameError = if (!lastName.matches(Regex("[a-zA-Z ]+"))) "El apellido solo debe contener letras" else null
        val dniError = if (!dni.matches(Regex("\\d+"))) "El DNI debe ser numérico" else null
        val phoneError = if (!phone.matches(Regex("\\d+"))) "El teléfono debe ser numérico" else null
        val passwordError = if (password.isNotEmpty() && password.length < 4) "La contraseña debe tener al menos 4 caracteres" else null
        val confirmPasswordError = if (password != confirm) "Las contraseñas no coinciden" else null

        _uiState.update {
            it.copy(
                name = name, lastName = lastName, dni = dni, phone = phone, password = password, confirmPassword = confirm,
                nameError = nameError, lastNameError = lastNameError, dniError = dniError, phoneError = phoneError,
                passwordError = passwordError ?: confirmPasswordError,
                isFormValid = nameError == null && lastNameError == null && dniError == null && phoneError == null &&
                        (passwordError == null && confirmPasswordError == null)
            )
        }
    }

    fun updateUser() {
        if (!_uiState.value.isFormValid) return

        viewModelScope.launch {
            val state = _uiState.value
            val userToUpdate = User(
                id = userDao.getUserByEmail(userEmail).first()?.id ?: 0,
                name = state.name,
                lastName = state.lastName,
                dni = state.dni,
                phone = state.phone,
                email = state.email,
                password = if (state.password.isNotEmpty()) state.password else userDao.getUserByEmail(userEmail).first()?.password ?: ""
            )
            userDao.insertUser(userToUpdate)
            _uiState.update { it.copy(userUpdated = true) }
        }
    }

    fun deactivateUser() {
        viewModelScope.launch {
            userDao.getUserByEmail(userEmail).first()?.let {
                userDao.insertUser(it.copy(estado = true))
                _uiState.update { it.copy(userDeactivated = true) }
            }
        }
    }

    fun onUserUpdatedHandled() {
        _uiState.update { it.copy(userUpdated = false) }
    }
}
