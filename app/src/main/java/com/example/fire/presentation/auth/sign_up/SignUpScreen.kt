package com.example.fire.presentation.auth.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fire.DestinationScreen
import com.example.fire.common.CheckSignedIn
import com.example.fire.common.CircularProgressSpinner
import com.example.fire.presentation.auth.AuthViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val isLoading = viewModel.isLoading.value
    CheckSignedIn(navController = navController, viewModel = viewModel)

    Box(modifier = Modifier.fillMaxSize()) {

        var usernameTf by remember { mutableStateOf("zaurway") }
        var emailTf by remember { mutableStateOf("zaur@gmail.com") }
        var passwordTf by remember { mutableStateOf("zaur123") }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Qeydiyyat")
            TextField(value = usernameTf, onValueChange = { usernameTf = it })
            TextField(value = emailTf, onValueChange = { emailTf = it })
            TextField(value = passwordTf, onValueChange = { passwordTf = it })
            Button(onClick = {
                viewModel.signUp(usernameTf,emailTf, passwordTf)
            }) {
                Text(text = "Sign Up")
            }
            Text(text = "Go to Sign In", modifier = Modifier.clickable {
                navController.navigate(DestinationScreen.SignIn.route)
            })
        }
    }
    if (isLoading) {
        CircularProgressSpinner()
    }
}