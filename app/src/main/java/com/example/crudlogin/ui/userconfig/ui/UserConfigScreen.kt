package com.example.crudlogin.ui.userconfig.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crudlogin.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserConfigScreen(userConfigViewModel: UserConfigViewModel, navController: NavController) {
    val uiState by userConfigViewModel.uiState.collectAsState()
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.user) {
        uiState.user?.let {
            name = it.name
            lastName = it.lastName
            dni = it.dni
            phone = it.phone
        }
    }

    LaunchedEffect(uiState.userDeleted) {
        if (uiState.userDeleted) {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.HomeScreen.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(uiState.userUpdated) {
        if (uiState.userUpdated) {
            scope.launch {
                snackbarHostState.showSnackbar("Guardado exitoso")
                navController.navigate(Screen.HomeScreen.createRoute(uiState.user?.email ?: "")) {
                    popUpTo(Screen.UserConfigScreen.route) { inclusive = true }
                }
                userConfigViewModel.onUserUpdatedHandled()
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar baja") },
            text = { Text("¿Estás seguro de que quieres dar de baja tu cuenta? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = { userConfigViewModel.deleteUser() }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            uiState.user?.let { user ->
                TextField(value = name, onValueChange = { newName -> name = newName }, label = { Text("Nombre") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = lastName, onValueChange = { newLastName -> lastName = newLastName }, label = { Text("Apellido") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = dni, onValueChange = { newDni -> dni = newDni }, label = { Text("DNI") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = phone, onValueChange = { newPhone -> phone = newPhone }, label = { Text("Teléfono") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = uiState.password,
                    onValueChange = { newPassword -> userConfigViewModel.onPasswordChange(newPassword, uiState.confirmPassword) },
                    label = { Text("Nueva contraseña") },
                    isError = uiState.passwordError != null
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = uiState.confirmPassword,
                    onValueChange = { newConfirm -> userConfigViewModel.onPasswordChange(uiState.password, newConfirm) },
                    label = { Text("Confirmar contraseña") },
                    isError = uiState.passwordError != null
                )
                uiState.passwordError?.let {
                    Text(text = it)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { userConfigViewModel.updateUser(user.copy(name = name, lastName = lastName, dni = dni, phone = phone)) }) {
                    Text(text = "Guardar cambios")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showDeleteDialog = true }) {
                    Text(text = "Dar de baja")
                }
            }
        }
    }
}
