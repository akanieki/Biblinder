package com.biblinder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.biblinder.ui.screens.ListsScreen
import com.biblinder.ui.screens.MainScreen
import com.biblinder.ui.screens.TournamentScreen
import com.biblinder.viewmodel.MainViewModel
import com.biblinder.viewmodel.ListsViewModel

@Composable
fun BiblinderNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {

        composable("main") {
            val mainVM: MainViewModel = hiltViewModel()
            MainScreen(
                onNavigateLists = { navController.navigate("lists") },
                onNavigateTournament = { navController.navigate("tournament") },
                viewModel = mainVM
            )
        }

        composable("lists") {
            val listsVM: ListsViewModel = hiltViewModel()
            ListsScreen(viewModel = listsVM)
        }

        composable("tournament") {
            TournamentScreen(emptyList()) { navController.popBackStack() }
        }
    }
}
