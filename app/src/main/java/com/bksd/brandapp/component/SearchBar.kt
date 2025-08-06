package com.bksd.brandapp.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bksd.brandapp.R
import com.bksd.brandapp.ui.theme.BrandAppTheme

@Composable
fun SearchBar(
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onClear: () -> Unit = {},
    placeholder: String = ""
) {
    var localQuery by remember(query) { mutableStateOf(query) }
    val localSoftwareKeyboardController =  LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = localQuery,
        onValueChange = { newQuery ->
            localQuery = newQuery
            onQueryChange(newQuery)
        },
        placeholder = {
            Text(
                text = placeholder.ifEmpty {
                    stringResource(R.string.search_bar_placeholder)
                }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        leadingIcon = { IconButton(onClick = { onSearch(localQuery) }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        trailingIcon = {
            if (localQuery.isNotEmpty()) {
                IconButton(onClick = {
                    onClear()
                    localQuery = ""
                    onQueryChange("")
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(localQuery)
                localSoftwareKeyboardController?.hide()
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    BrandAppTheme {
        SearchBar()
    }
}

@PreviewLightDark()
@Composable
private fun SearchBarWithQueryPreview() {
    BrandAppTheme {
        SearchBar(query = "apple")
    }
}