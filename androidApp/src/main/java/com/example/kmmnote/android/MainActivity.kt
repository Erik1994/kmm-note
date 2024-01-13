package com.example.kmmnote.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kmmnote.android.navigation.Route
import com.example.kmmnote.android.note.NoteScreen
import com.example.kmmnote.android.notedetail.NoteDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Route.NOTE_SCREEN
                    ) {
                        composable(route = Route.NOTE_SCREEN) {
                            NoteScreen { route, noteId ->
                                navController.navigate("$route/$noteId")
                            }
                        }
                        composable(
                            route = "${Route.NOTE_DETAIL_SCREEN}/{${Route.NOTE_ID}}",
                            arguments = listOf(
                                navArgument(name = Route.NOTE_ID) {
                                    type = NavType.LongType
                                    defaultValue = -1L
                                }
                            )) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getLong(Route.NOTE_ID) ?: -1L
                            NoteDetailScreen(noteId = noteId) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}
