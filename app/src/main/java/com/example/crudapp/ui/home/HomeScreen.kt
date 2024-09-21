package com.example.crudapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crudapp.R
import com.example.crudapp.TopAppBar
import com.example.crudapp.data.Miranha
import com.example.crudapp.ui.AppViewModelProvider
import com.example.crudapp.ui.navigation.Destination
import com.example.crudapp.ui.theme.CRUDAppTheme

object HomeDestination : Destination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToMiranhaEntry: () -> Unit,
    navigateToMiranhaUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToMiranhaEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.miranha_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            miranhaList = homeUiState.miranhaList,
            onMiranhaClick = navigateToMiranhaUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@Composable
private fun HomeBody(
    miranhaList: List<Miranha>,
    onMiranhaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (miranhaList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_miranha_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            InventoryList(
                miranhaList = miranhaList,
                onMiranhaClick = { onMiranhaClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun InventoryList(
    miranhaList: List<Miranha>,
    onMiranhaClick: (Miranha) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = miranhaList, key = { it.id }) { miranha ->
            InventoryMiranha(miranha = miranha,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onMiranhaClick(miranha) })
        }
    }
}

@Composable
private fun InventoryMiranha(
    miranha: Miranha, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = miranha.name,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                text = stringResource(R.string.in_stock, miranha.earth),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    CRUDAppTheme {
        HomeBody(listOf(
            Miranha(1, "Game", 20), Miranha(2, "Pen",  30), Miranha(3, "TV",  50)
        ), onMiranhaClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    CRUDAppTheme {
        HomeBody(listOf(), onMiranhaClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryMiranhaPreview() {
    CRUDAppTheme {
        InventoryMiranha(
            Miranha(1, "Game", 20),
        )
    }
}