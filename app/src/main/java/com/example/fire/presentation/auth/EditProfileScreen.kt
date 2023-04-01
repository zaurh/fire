package com.example.fire.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.example.fire.DestinationScreen
import com.example.fire.common.CircularProgressSpinner

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val focus = LocalFocusManager.current
    val isLoading = viewModel.isLoading.value
    val userData = viewModel.userData.value
    var usernameTf by rememberSaveable { mutableStateOf(userData?.username ?: "") }
    var bioTf by rememberSaveable { mutableStateOf(userData?.bio ?: "") }

    var noteTitleTf by rememberSaveable { mutableStateOf("") }
    var noteDescTf by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = userData?.image ?: "No image", modifier = Modifier.clickable { })
        TextField(value = usernameTf, onValueChange = { usernameTf = it }, label = {
            Text(
                text = "Username"
            )
        })
        TextField(
            value = bioTf,
            onValueChange = { bioTf = it },
            label = { Text(text = "Bio") })

        TextField(
            value = noteTitleTf,
            onValueChange = { noteTitleTf = it },
            label = { Text(text = "Title") })

        TextField(
            value = noteDescTf,
            onValueChange = { noteDescTf = it },
            label = { Text(text = "Description") })

        Button(onClick = {
            focus.clearFocus()
            viewModel.createNote(
                noteTitleTf,
                noteDescTf,
                onFailure = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() },
            )
        }) {
            Text(text = "Save Note")
        }

        Button(onClick = {
            focus.clearFocus()
            viewModel.updateProfileData(username = usernameTf, bio = bioTf)
//            navController.navigate(DestinationScreen.Main.route)
        }) {
            Text(text = "Save")
        }
    }


    if (isLoading) {
        CircularProgressSpinner()
    }
}