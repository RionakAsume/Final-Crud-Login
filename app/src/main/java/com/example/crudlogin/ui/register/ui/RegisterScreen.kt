package com.example.crudlogin.ui.register.ui

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crudlogin.R
import com.example.crudlogin.ui.theme.Black
import com.example.crudlogin.ui.theme.Green
import com.example.crudlogin.ui.theme.Gray
import com.example.crudlogin.ui.theme.LightGray

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {
    val uiState by registerViewModel.uiState

    LaunchedEffect(key1 = registerViewModel.registrationStatus) {
        registerViewModel.registrationStatus.collect {
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        ) {
        Register(
            Modifier.align(Alignment.TopCenter),
            uiState,
            onRegisterChanged = { name, lastName, dni, phone, email, password ->
                registerViewModel.onRegisterChanged(name, lastName, dni, phone, email, password)
            },
            onRegisterClicked = { registerViewModel.registerUser() }
        )
    }
}

@Composable
fun Register(
    modifier: Modifier,
    uiState: RegisterUiState,
    onRegisterChanged: (String, String, String, String, String, String) -> Unit,
    onRegisterClicked: () -> Unit
) {
    Column(modifier = modifier) {
        HeaderImageRegister(Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.padding(16.dp))
        NameField(uiState.name) { onRegisterChanged(it, uiState.lastName, uiState.dni, uiState.phone, uiState.email, uiState.password) }
        Spacer(Modifier.padding(4.dp))
        LastNameField(uiState.lastName) { onRegisterChanged(uiState.name, it, uiState.dni, uiState.phone, uiState.email, uiState.password) }
        Spacer(Modifier.padding(4.dp))
        DniField(uiState.dni) { onRegisterChanged(uiState.name, uiState.lastName, it, uiState.phone, uiState.email, uiState.password) }
        Spacer(Modifier.padding(4.dp))
        PhoneField(uiState.phone) { onRegisterChanged(uiState.name, uiState.lastName, uiState.dni, it, uiState.email, uiState.password) }
        Spacer(Modifier.padding(4.dp))
        EmailField(uiState.email) { onRegisterChanged(uiState.name, uiState.lastName, uiState.dni, uiState.phone, it, uiState.password) }
        Spacer(Modifier.padding(4.dp))
        PasswordField(uiState.password) { onRegisterChanged(uiState.name, uiState.lastName, uiState.dni, uiState.phone, uiState.email, it) }
        Spacer(Modifier.padding(8.dp))
        RegisterButton(onRegisterClicked)
    }
}

@Composable
fun HeaderImageRegister(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.worksign_engranaje),
        contentDescription = "tuerca",
        modifier = modifier.size(80.dp)
    )
}

@Composable
fun NameField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Nombre") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
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
fun LastNameField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Apellido") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
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
fun DniField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "DNI") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        maxLines = 1,
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
fun PhoneField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Telefono") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        maxLines = 1,
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
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
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
        placeholder = { Text(text = "Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "show password")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
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
fun RegisterButton(onRegisterClicked: () -> Unit) {
    Button(
        onClick = onRegisterClicked,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green,
            contentColor = Color.White,
            disabledContainerColor = Gray,
            disabledContentColor = Color.White
        ),

        ) {
        Text(text = "Registrarse")
    }
}

