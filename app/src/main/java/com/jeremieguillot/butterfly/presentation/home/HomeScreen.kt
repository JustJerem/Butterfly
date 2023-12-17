@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.jeremieguillot.butterfly.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.presentation._nav.HomeScreenNavArgs
import com.jeremieguillot.butterfly.presentation.destinations.DetailScreenDestination
import com.jeremieguillot.butterfly.presentation.destinations.FilterScreenPreviewDestination
import com.jeremieguillot.butterfly.presentation.home.composable.ButterflyCard
import com.jeremieguillot.butterfly.presentation.home.composable.HorizontalCategoryList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@RootNavGraph(start = true)
@Destination(
    navArgsDelegate = HomeScreenNavArgs::class
)
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

    val focusRequester = FocusRequester()


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

    var searchText by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.isSearchBarVisible) {

                        Box {
                            if (searchText.isEmpty()) {
                                Text(
                                    text = "Rechercher...",
                                    style = typography.bodySmall,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    letterSpacing = 0.25.sp,
                                    maxLines = 1,
                                )
                            }

                            BasicTextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(onSearch = {
                                    onEvent(HomeContract.Event.SearchButterflies(searchText))
                                }),

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 16.dp)
                                    .focusRequester(focusRequester),
                            )

                            LaunchedEffect(Unit) {
                                focusRequester.requestFocus()
                            }
                        }
                    } else {
                        Text(text = stringResource(id = R.string.app_name))
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            onClick = {
                                searchText = ""
                                onEvent(HomeContract.Event.ToggleSearchBar)
                            }
                        ) {
                            Icon(
                                imageVector = if (state.isSearchBarVisible) Icons.Default.Close else Icons.Default.Search,
                                contentDescription = null
                            )
                        }

                        IconButton(
                            onClick = {
                                navigator.navigate(FilterScreenPreviewDestination)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        val butterfly = state.filteredButterflies.collectAsLazyPagingItems()
        if (!state.isViewLoading) {
            Column(modifier = Modifier.padding(it)) {
                HorizontalCategoryList {
                    onEvent(HomeContract.Event.ButterflyCategorySelected(it))
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) {

                    if (butterfly.loadState.refresh is LoadState.Loading) {
                        item {
                            CircularProgressIndicator()
                        }
                    } else {

                        items(butterfly.itemCount) { index ->
                            val item = butterfly[index] ?: return@items

                            ButterflyCard(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                butterfly = item
                            ) {
                                navigator.navigate(DetailScreenDestination(item))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

        } else {
            Box {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }

        }
    }
}