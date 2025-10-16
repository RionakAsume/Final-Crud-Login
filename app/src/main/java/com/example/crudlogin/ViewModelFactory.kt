package com.example.crudlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crudlogin.data.local.UserDao
import com.example.crudlogin.ui.home.ui.HomeViewModel
import com.example.crudlogin.ui.login.ui.LoginViewModel
import com.example.crudlogin.ui.register.ui.RegisterViewModel
import com.example.crudlogin.ui.userconfig.ui.UserConfigViewModel

class ViewModelFactory(private val userDao: UserDao, private val email: String? = null) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userDao) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userDao) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel() as T
            }
            modelClass.isAssignableFrom(UserConfigViewModel::class.java) -> {
                UserConfigViewModel(userDao, email ?: "") as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
