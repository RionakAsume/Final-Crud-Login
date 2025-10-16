package com.example.crudlogin.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crudlogin.R
import com.example.crudlogin.Screen
import com.example.crudlogin.ui.theme.Black
import com.example.crudlogin.ui.theme.Green
import com.example.crudlogin.ui.theme.Gray
import com.example.crudlogin.ui.theme.LightGray

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val uiState by loginViewModel.uiState.collectAsState()

    if (uiState.loginSuccess) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.HomeScreen.createRoute(uiState.email)) {
                popUpTo(Screen.LoginScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), uiState, onLoginChanged = {
            loginViewModel.onLoginChange(
                it.first,
                it.second
            )
        }, onLoginClick = { loginViewModel.login() },
            navController = navController
        )
    }
}

@Composable
fun Login(
    modifier: Modifier,
    uiState: LoginUiState,
    onLoginChanged: (Pair<String, String>) -> Unit,
    onLoginClick: () -> Unit,
    navController: NavController
) {
    Column(modifier = modifier) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.padding(16.dp))
        EmailField(uiState.email, uiState.emailError) { onLoginChanged(Pair(it, uiState.password)) }
        Spacer(Modifier.padding(4.dp))
        PasswordField(uiState.password) { onLoginChanged(Pair(uiState.email, it)) }
        Spacer(Modifier.padding(8.dp))
        uiState.loginError?.let {
            Text(text = it, color = Color.Red)
        }
        Spacer(Modifier.padding(8.dp))
        LoginButton(uiState.isLoading, uiState.emailError == null && uiState.password.isNotEmpty(), onLoginClick)
        Spacer(Modifier.padding(16.dp))
        RegisterText(navController)

    }
}

@Composable
fun RegisterText(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Screen.RegisterScreen.route) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "¿No tienes cuenta? Crea una")
    }
}


@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.worksign_engranaje),
        contentDescription = "tuerca",
        modifier = modifier.size(120.dp)
    )

}

@Composable
fun EmailField(value: String, error: String?, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Black,
            focusedTextColor = Black,
            unfocusedContainerColor = LightGray,
            focusedContainerColor = LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Password") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "show password")
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Black,
            focusedTextColor = Black,
            unfocusedContainerColor = LightGray,
            focusedContainerColor = LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun LoginButton(isLoading: Boolean, isEnabled: Boolean, onLoginClick: () -> Unit) {
    Button(
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green,
            contentColor = Color.White,
            disabledContainerColor = Gray,
            disabledContentColor = Color.White
        ),
        enabled = isEnabled

    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(text = "Iniciar sesión")
        }
    }
}
