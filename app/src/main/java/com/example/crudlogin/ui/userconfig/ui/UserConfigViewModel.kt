package com.example.crudlogin.ui.userconfig.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudlogin.data.local.User
import com.example.crudlogin.data.local.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserConfigUiState(
    val user: User? = null,
    val userDeleted: Boolean = false,
    val userUpdated: Boolean = false,
    val password: String = "",
    val confirmPassword: String = "",
    val passwordError: String? = null
)

class UserConfigViewModel(private val userDao: UserDao, private val userEmail: String) : ViewModel() {

    private val _uiState = MutableStateFlow(UserConfigUiState())
    val uiState: StateFlow<UserConfigUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userDao.getUserByEmail(userEmail).collect {
                _uiState.value = _uiState.value.copy(user = it)
            }
        }
    }

    fun onPasswordChange(password: String, confirm: String) {
        val error = if (password != confirm) "Las contraseñas no coinciden" else null
        _uiState.update { it.copy(password = password, confirmPassword = confirm, passwordError = error) }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val userToUpdate = if (_uiState.value.password.isNotEmpty() && _uiState.value.passwordError == null) {
                user.copy(password = _uiState.value.password)
            } else {
                user
            }
            userDao.insertUser(userToUpdate)
            _uiState.update { it.copy(userUpdated = true) }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            _uiState.value.user?.let {
                userDao.deleteUser(it)
                _uiState.value = _uiState.value.copy(userDeleted = true)
            }
        }
    }

    fun onUserUpdatedHandled() {
        _uiState.update { it.copy(userUpdated = false) }
    }
}
