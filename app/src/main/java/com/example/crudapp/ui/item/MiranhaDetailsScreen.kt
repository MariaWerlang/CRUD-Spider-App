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
import androidx.compose.material3.ButtonColors
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
import com.example.crudapp.ui.theme.Aranha88
import com.example.crudapp.ui.theme.CRUDAppTheme
import com.example.crudapp.ui.theme.Teia
import com.example.crudapp.ui.theme.Venom
import com.example.crudapp.ui.theme.Venom2
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
        },
        contentColor = Teia,
        containerColor = Venom,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditMiranha(uiState.value.miranhaDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                contentColor = Teia,
                containerColor = Aranha88

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_miranha_title),
                    tint = Teia
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
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = Aranha88,
                contentColor = Teia,
                disabledContentColor = Teia,
                disabledContainerColor = Aranha88
            )
        ) {
            Text(
                text = stringResource(R.string.delete),
                color = Teia
            )
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
            containerColor = Venom2,
            contentColor = Teia
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
        Text(
            text = stringResource(labelResID),
            color = Teia
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = miranhaDetail,
            fontWeight = FontWeight.Bold,
            color = Teia
        )
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
        containerColor = Venom2,
        iconContentColor = Teia,
        titleContentColor = Teia,
        textContentColor = Teia,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(
                    text = stringResource(R.string.no),
                    color = Teia
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(
                    text = stringResource(R.string.yes),
                    color = Teia
                )
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