@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.jeremieguillot.butterfly.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.presentation.destinations.DetailScreenDestination
import com.jeremieguillot.butterfly.presentation.home.composable.ButterflyItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Destination
@RootNavGraph(start = true)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    HomeScreen(viewModel.state, navigator, viewModel::onEvent, viewModel.errorEvents)
}


@Composable
fun HomeScreen(
    state: HomeContract.State,
    navigator: DestinationsNavigator,
    onEvent: (HomeContract.Event) -> Unit,
    errorEvents: Flow<HomeContract.Error>
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = snackbarHostState) {
        errorEvents.collect { error ->
            when (error) {
                is HomeContract.Error.UnknownIssue -> scope.launch {
                    snackbarHostState.showSnackbar(
                        message = error.message.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.isSearchBarVisible) {
                        BasicTextField(
                            value = state.searchText,
                            onValueChange = { onEvent(HomeContract.Event.SearchButterflies(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp),
                        )
                    } else {
                        Text(text = stringResource(R.string.home_screen_title))
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onEvent(HomeContract.Event.ToggleSearchBar) }
                    ) {
                        Icon(
                            imageVector =
                            if (state.isSearchBarVisible) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {

        if (!state.isViewLoading) {
            LazyColumn(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(state.filteredButterflies) { butterfly ->
                    ButterflyItem(butterfly = butterfly) {
                        navigator.navigate(DetailScreenDestination(butterfly))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
        if (state.isViewLoading) {
            CircularProgressIndicator()
        }
    }
}