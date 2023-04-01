package com.example.fire.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fire.data.NoteData
import kotlin.math.sign
import kotlin.math.sin

@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var singleNote = ""

    val note = viewModel.notes.value
    Box(modifier = Modifier.fillMaxSize()) {
        val noteTitle by remember { mutableStateOf("") }
        val noteDescription by remember { mutableStateOf( "" ) }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = singleNote)
        }
    }
}