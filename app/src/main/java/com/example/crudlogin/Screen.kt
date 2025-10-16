package com.example.crudlogin

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login")
    object RegisterScreen : Screen("register")
    object HomeScreen : Screen("home/{email}") {
        fun createRoute(email: String) = "home/$email"
    }
    object UserConfigScreen : Screen("user_config/{email}") {
        fun createRoute(email: String) = "user_config/$email"
    }
}
