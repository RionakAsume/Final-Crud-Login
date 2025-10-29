package com.example.crudlogin.ui.userconfig.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.crudlogin.Screen
import com.example.crudlogin.ui.theme.Green
import com.example.crudlogin.ui.theme.Gray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserConfigScreen(userConfigViewModel: UserConfigViewModel, navController: NavController) {
    val uiState by userConfigViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.userDeactivated) {
        if (uiState.userDeactivated) {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(uiState.userUpdated) {
        if (uiState.userUpdated) {
            scope.launch {
                snackbarHostState.showSnackbar("Guardado exitoso")
                navController.navigate(Screen.HomeScreen.createRoute(uiState.email)) {
                    popUpTo(Screen.UserConfigScreen.route) { inclusive = true }
                }
                userConfigViewModel.onUserUpdatedHandled()
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar desactivación") },
            text = { Text("¿Estás seguro de que quieres desactivar tu cuenta? No podrás iniciar sesión hasta que sea reactivada.") },
            confirmButton = {
                TextButton(onClick = {
                    userConfigViewModel.deactivateUser()
                    showDeleteDialog = false
                }) {
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = uiState.name,
                onValueChange = {
                    userConfigViewModel.onFieldChange(it, uiState.lastName, uiState.dni, uiState.phone, uiState.password, uiState.confirmPassword)
                },
                label = { Text("Nombre") },
                isError = uiState.nameError != null,
                supportingText = { uiState.nameError?.let { Text(text = it, color = Color.Red) } }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = uiState.lastName,
                onValueChange = {
                    userConfigViewModel.onFieldChange(uiState.name, it, uiState.dni, uiState.phone, uiState.password, uiState.confirmPassword)
                },
                label = { Text("Apellido") },
                isError = uiState.lastNameError != null,
                supportingText = { uiState.lastNameError?.let { Text(text = it, color = Color.Red) } }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = uiState.dni,
                onValueChange = {
                    userConfigViewModel.onFieldChange(uiState.name, uiState.lastName, it, uiState.phone, uiState.password, uiState.confirmPassword)
                },
                label = { Text("DNI") },
                isError = uiState.dniError != null,
                supportingText = { uiState.dniError?.let { Text(text = it, color = Color.Red) } }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = uiState.phone,
                onValueChange = {
                    userConfigViewModel.onFieldChange(uiState.name, uiState.lastName, uiState.dni, it, uiState.password, uiState.confirmPassword)
                },
                label = { Text("Teléfono") },
                isError = uiState.phoneError != null,
                supportingText = { uiState.phoneError?.let { Text(text = it, color = Color.Red) } }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = uiState.password,
                onValueChange = {
                    userConfigViewModel.onFieldChange(uiState.name, uiState.lastName, uiState.dni, uiState.phone, it, uiState.confirmPassword)
                },
                label = { Text("Nueva contraseña") },
                isError = uiState.passwordError != null,
                supportingText = { uiState.passwordError?.let { Text(text = it, color = Color.Red) } }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = uiState.confirmPassword,
                onValueChange = {
                    userConfigViewModel.onFieldChange(uiState.name, uiState.lastName, uiState.dni, uiState.phone, uiState.password, it)
                },
                label = { Text("Confirmar contraseña") },
                isError = uiState.passwordError != null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { userConfigViewModel.updateUser() },
                enabled = uiState.isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                    contentColor = Color.White,
                    disabledContainerColor = Gray,
                    disabledContentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar cambios")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showDeleteDialog = true }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Dar de baja")
            }
        }
    }
}
