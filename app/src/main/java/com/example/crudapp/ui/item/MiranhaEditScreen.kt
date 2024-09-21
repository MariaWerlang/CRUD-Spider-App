package com.example.crudapp.ui.item

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crudapp.R
import com.example.crudapp.TopAppBar
import com.example.crudapp.ui.AppViewModelProvider
import com.example.crudapp.ui.navigation.Destination
import com.example.crudapp.ui.theme.CRUDAppTheme
import kotlinx.coroutines.launch

object MiranhaEditDestination : Destination {
    override val route = "miranha_edit"
    override val titleRes = R.string.edit_miranha_title
    const val miranhaIdArg = "miranhaId"
    val routeWithArgs = "$route/{$miranhaIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiranhaEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MiranhaEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(MiranhaEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        MiranhaEntryBody(
            miranhaUiState = viewModel.miranhaUiState,
            onMiranhaValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMiranha()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MiranhaEditScreenPreview() {
    CRUDAppTheme {
        MiranhaEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}
