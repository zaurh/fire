package com.example.fire.presentation.auth.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fire.DestinationScreen
import com.example.fire.presentation.auth.AuthViewModel
import com.example.fire.presentation.auth.components.NoteCardItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val userData = viewModel.userData.value
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(cells = GridCells.Fixed(2), modifier = Modifier.weight(1f)){
                items(viewModel.notes.value){ note ->
                    NoteCardItem(noteData = note, navController = navController)
                }
            }
            Text(text = userData?.username ?: "No username")
            Text(text = userData?.email ?: "No email")
            Text(text = userData?.password ?: "No password")
            Text(text = userData?.bio ?: "No bio")

            Button(onClick = {
                viewModel.signOut()
                navController.navigate(DestinationScreen.SignIn.route){
                    popUpTo(0)
                }
            }) {
                Text(text = "Sign Out")
            }

            Button(onClick = {
                viewModel.deleteUser()
                navController.navigate(DestinationScreen.SignIn.route){
                    popUpTo(0)
                }
            }) {
                Text(text = "Delete")
            }

            Button(onClick = {
                navController.navigate(DestinationScreen.EditProfile.route)
            }) {
                Text(text = "Edit")
            }

        }
    }
}