package com.example.crudapp.ui.item

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crudapp.R
import com.example.crudapp.TopAppBar
import com.example.crudapp.data.Miranha
import com.example.crudapp.ui.AppViewModelProvider
import com.example.crudapp.ui.navigation.Destination
import com.example.crudapp.ui.theme.CRUDAppTheme
import kotlinx.coroutines.launch

object MiranhaDetailsDestination : Destination {
    override val route = "item_details"
    override val titleRes = R.string.miranha_detail_title
    const val miranhaIdArg = "miranhaId"
    val routeWithArgs = "$route/{$miranhaIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiranhaDetailsScreen(
    navigateToEditMiranha: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MiranhaDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(MiranhaDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditMiranha(uiState.value.miranhaDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_miranha_title),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        MiranhaDetailsBody(
            miranhaDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteMiranha()
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

@Composable
private fun MiranhaDetailsBody(
    miranhaDetailsUiState: MiranhaDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        MiranhaDetails(
            miranha = miranhaDetailsUiState.miranhaDetails.toMiranha(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun MiranhaDetails(
    miranha: Miranha, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            MiranhaDetailsRow(
                labelResID = R.string.miranha,
                miranhaDetail = miranha.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            MiranhaDetailsRow(
                labelResID = R.string.earth,
                miranhaDetail = miranha.earth.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
private fun MiranhaDetailsRow(
    @StringRes labelResID: Int, miranhaDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = miranhaDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}

@Preview(showBackground = true)
@Composable
fun MiranhaDetailsScreenPreview() {
    CRUDAppTheme {
        MiranhaDetailsBody(
            MiranhaDetailsUiState(
                miranhaDetails = MiranhaDetails(1, "Pen", "10")
            ),
            onDelete = {}
        )
    }
}