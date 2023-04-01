package com.example.fire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fire.presentation.auth.AuthViewModel
import com.example.fire.presentation.auth.EditNoteScreen
import com.example.fire.presentation.auth.EditProfileScreen
import com.example.fire.presentation.auth.main.MainScreen
import com.example.fire.presentation.auth.sign_in.SignInScreen
import com.example.fire.presentation.auth.sign_up.SignUpScreen
import com.example.fire.ui.theme.FireTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navig()
                }
            }
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object SignUp : DestinationScreen("sign_up")
    object SignIn : DestinationScreen("sign_in")
    object Main : DestinationScreen("main")
    object EditProfile: DestinationScreen("edit_profile")
    object EditNote: DestinationScreen("edit_note")
}

@Composable
fun Navig() {

    val authViewModel = hiltViewModel<AuthViewModel>()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = DestinationScreen.SignIn.route) {
        composable(DestinationScreen.SignUp.route) {
            SignUpScreen(navController = navController, authViewModel)
        }
        composable(DestinationScreen.SignIn.route) {
            SignInScreen(navController = navController, authViewModel)
        }
        composable(DestinationScreen.Main.route) {
            MainScreen(navController = navController, viewModel = authViewModel)
        }
        composable(DestinationScreen.EditProfile.route) {
            EditProfileScreen(navController = navController, authViewModel)
        }
        composable("${DestinationScreen.EditNote.route}/{id}") {
            EditNoteScreen(navController = navController, authViewModel)
        }

    }
}