package com.example.crudlogin.ui.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.crudlogin.R
import com.example.crudlogin.Screen

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navController: NavController, email: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        HeaderImagehome(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = "¡Bienvenido a la pantalla de inicio!")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.UserConfigScreen.createRoute(email)) }) {
            Text(text = "Configuración de usuario")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
        }) {
            Text(text = "Cerrar sesión")
        }
    }
}

@Composable
fun HeaderImagehome(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.worksign_logo_v),
        contentDescription = "tuerca",
        modifier = modifier.size(250.dp)
    )
}
