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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.crudlogin.ui.theme.Black
import com.example.crudlogin.ui.theme.Green
import com.example.crudlogin.ui.theme.Gray
import com.example.crudlogin.ui.theme.LightGray

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {
    val uiState by registerViewModel.uiState.collectAsState()

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
            uiState = uiState,
            onRegisterChanged = registerViewModel::onRegisterChange,
            onRegisterClicked = { registerViewModel.registerUser() }
        )
    }
}

@Composable
fun Register(
    modifier: Modifier,
    uiState: RegisterUiState,
    onRegisterChanged: (String, String, String, String, String, String, String) -> Unit,
    onRegisterClicked: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        HeaderImageRegister(Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.padding(16.dp))
        NameField(uiState.name, uiState.nameError) { onRegisterChanged(it, uiState.lastName, uiState.dni, uiState.phone, uiState.email, uiState.password, uiState.confirmPassword) }
        Spacer(Modifier.padding(4.dp))
        LastNameField(uiState.lastName, uiState.lastNameError) { onRegisterChanged(uiState.name, it, uiState.dni, uiState.phone, uiState.email, uiState.password, uiState.confirmPassword) }
        Spacer(Modifier.padding(4.dp))
        DniField(uiState.dni, uiState.dniError) { onRegisterChanged(uiState.name, uiState.lastName, it, uiState.phone, uiState.email, uiState.password, uiState.confirmPassword) }
        Spacer(Modifier.padding(4.dp))
        PhoneField(uiState.phone, uiState.phoneError) { onRegisterChanged(uiState.name, uiState.lastName, uiState.dni, it, uiState.email, uiState.password, uiState.confirmPassword) }
        Spacer(Modifier.padding(4.dp))
        EmailField(uiState.email, uiState.emailError) { onRegisterChanged(uiState.name, uiState.lastName, uiState.dni, uiState.phone, it, uiState.password, uiState.confirmPassword) }
        Spacer(Modifier.padding(4.dp))
        PasswordField(uiState.password, uiState.passwordError) { onRegisterChanged(uiState.name, uiState.lastName, uiState.dni, uiState.phone, uiState.email, it, uiState.confirmPassword) }
        Spacer(Modifier.padding(4.dp))
        ConfirmPasswordField(uiState.confirmPassword, uiState.confirmPasswordError) { onRegisterChanged(uiState.name, uiState.lastName, uiState.dni, uiState.phone, uiState.email, uiState.password, it) }
        Spacer(Modifier.padding(8.dp))
        RegisterButton(uiState.isFormValid, onRegisterClicked)
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
fun NameField(value: String, error: String?, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Nombre") },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
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
fun LastNameField(value: String, error: String?, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Apellido") },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
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
fun DniField(value: String, error: String?, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "DNI") },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
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
fun PhoneField(value: String, error: String?, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Telefono") },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
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
fun EmailField(value: String, error: String?, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
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
fun PasswordField(value: String, error: String?, onValueChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) } 
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
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
fun ConfirmPasswordField(value: String, error: String?, onValueChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Confirmar contraseña") },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = { error?.let { Text(text = it, color = Color.Red) } },
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
fun RegisterButton(isEnabled: Boolean, onRegisterClicked: () -> Unit) {
    Button(
        onClick = onRegisterClicked,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = isEnabled,
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
