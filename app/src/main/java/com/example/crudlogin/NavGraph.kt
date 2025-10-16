package com.example.crudlogin

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.crudlogin.data.local.AppDatabase
import com.example.crudlogin.ui.home.ui.HomeScreen
import com.example.crudlogin.ui.home.ui.HomeViewModel
import com.example.crudlogin.ui.login.ui.LoginScreen
import com.example.crudlogin.ui.login.ui.LoginViewModel
import com.example.crudlogin.ui.register.ui.RegisterScreen
import com.example.crudlogin.ui.register.ui.RegisterViewModel
import com.example.crudlogin.ui.userconfig.ui.UserConfigScreen
import com.example.crudlogin.ui.userconfig.ui.UserConfigViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            val loginViewModel: LoginViewModel = viewModel(factory = ViewModelFactory(database.userDao()))
            LoginScreen(navController, loginViewModel)
        }
        composable(Screen.RegisterScreen.route) {
            val registerViewModel: RegisterViewModel = viewModel(factory = ViewModelFactory(database.userDao()))
            RegisterScreen(navController, registerViewModel)
        }
        composable(
            route = Screen.HomeScreen.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val homeViewModel: HomeViewModel = viewModel(factory = ViewModelFactory(database.userDao(), email))
            HomeScreen(homeViewModel, navController, email)
        }
        composable(
            route = Screen.UserConfigScreen.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val userConfigViewModel: UserConfigViewModel = viewModel(factory = ViewModelFactory(database.userDao(), email))
            UserConfigScreen(userConfigViewModel, navController)
        }
    }
}
