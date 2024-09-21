package com.example.crudapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.crudapp.ui.home.HomeDestination
import com.example.crudapp.ui.home.HomeScreen
import com.example.crudapp.ui.item.MiranhaDetailsDestination
import com.example.crudapp.ui.item.MiranhaDetailsScreen
import com.example.crudapp.ui.item.MiranhaEditDestination
import com.example.crudapp.ui.item.MiranhaEditScreen
import com.example.crudapp.ui.item.MiranhaEntryDestination
import com.example.crudapp.ui.item.MiranhaEntryScreen

@Composable
fun CRUDAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToMiranhaEntry = { navController.navigate(MiranhaEntryDestination.route) },
                navigateToMiranhaUpdate = {
                    navController.navigate("${MiranhaDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = MiranhaEntryDestination.route) {
            MiranhaEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = MiranhaDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(MiranhaDetailsDestination.miranhaIdArg) {
                type = NavType.IntType
            })
        ) {
            MiranhaDetailsScreen(
                navigateToEditMiranha = { navController.navigate("${MiranhaEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = MiranhaEditDestination.routeWithArgs,
            arguments = listOf(navArgument(MiranhaEditDestination.miranhaIdArg) {
                type = NavType.IntType
            })
        ) {
            MiranhaEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}