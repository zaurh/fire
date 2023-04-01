package com.example.fire.presentation.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fire.DestinationScreen
import com.example.fire.data.NoteData

@Composable
fun NoteCardItem(
    navController: NavController,
    noteData: NoteData
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.LightGray)
            .clickable {
                navController.navigate("${DestinationScreen.EditNote.route}/${noteData.noteId}")
            }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = noteData.title ?: "No title")
            Text(text = noteData.description ?: "No desc")
        }


    }
}