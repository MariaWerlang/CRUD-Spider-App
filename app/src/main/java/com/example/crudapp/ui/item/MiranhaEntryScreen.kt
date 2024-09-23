package com.example.crudapp.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crudapp.R
import com.example.crudapp.TopAppBar
import com.example.crudapp.ui.AppViewModelProvider
import com.example.crudapp.ui.navigation.Destination
import com.example.crudapp.ui.theme.Aranha88
import com.example.crudapp.ui.theme.CRUDAppTheme
import com.example.crudapp.ui.theme.Teia
import com.example.crudapp.ui.theme.Venom
import com.example.crudapp.ui.theme.Venom2
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object MiranhaEntryDestination : Destination {
    override val route = "item_entry"
    override val titleRes = R.string.miranha_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiranhaEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: MiranhaEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(MiranhaEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        contentColor = Teia,
        containerColor = Venom,
    ) { innerPadding ->
        MiranhaEntryBody(
            miranhaUiState = viewModel.miranhaUiState,
            onMiranhaValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveMiranha()
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
                .fillMaxWidth()
        )
    }
}

@Composable
fun MiranhaEntryBody(
    miranhaUiState: MiranhaUiState,
    onMiranhaValueChange: (MiranhaDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        MiranhaInputForm(
            miranhaDetails = miranhaUiState.miranhaDetails,
            onValueChange = onMiranhaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = miranhaUiState.isEntryValid,
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
                text = stringResource(R.string.save_action),
                color = Teia
            )
        }
    }
}

@Composable
fun MiranhaInputForm(
    miranhaDetails: MiranhaDetails,
    modifier: Modifier = Modifier,
    onValueChange: (MiranhaDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = miranhaDetails.name,
            onValueChange = { onValueChange(miranhaDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.miranha_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Venom2,
                unfocusedContainerColor = Venom2,
                disabledContainerColor = Venom2,
                focusedTextColor = Aranha88,
                focusedLabelColor = Aranha88,
                focusedBorderColor = Aranha88,
                focusedPlaceholderColor = Aranha88,
                unfocusedTextColor = Aranha88,
                unfocusedPlaceholderColor = Aranha88,
                unfocusedLabelColor = Aranha88,
                unfocusedBorderColor = Aranha88,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = miranhaDetails.earth,
            onValueChange = { onValueChange(miranhaDetails.copy(earth = it)) },
            label = { Text(stringResource(R.string.miranha_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Venom2,
                unfocusedContainerColor = Venom2,
                disabledContainerColor = Venom2,
                focusedTextColor = Aranha88,
                focusedLabelColor = Aranha88,
                focusedBorderColor = Aranha88,
                focusedPlaceholderColor = Aranha88,
                unfocusedTextColor = Aranha88,
                unfocusedPlaceholderColor = Aranha88,
                unfocusedLabelColor = Aranha88,
                unfocusedBorderColor = Aranha88,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    CRUDAppTheme {
        MiranhaEntryBody(miranhaUiState = MiranhaUiState(
            MiranhaDetails(
                name = "Item name", earth = ""
            )
        ), onMiranhaValueChange = {}, onSaveClick = {})
    }
}