
@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.crudapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.crudapp.ui.navigation.CRUDAppNavHost
import com.example.crudapp.R.string
import com.example.crudapp.ui.theme.Aranha88
import com.example.crudapp.ui.theme.Venom

@Composable
fun CRUDApp(navController: NavHostController = rememberNavController()) {
    CRUDAppNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = Venom,
            titleContentColor = Aranha88,
            navigationIconContentColor = Aranha88,
            actionIconContentColor = Aranha88,
            scrolledContainerColor = Aranha88
        )
    )
}