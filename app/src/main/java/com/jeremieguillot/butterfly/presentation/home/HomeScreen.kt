@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class,
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)

package com.jeremieguillot.butterfly.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.presentation.home.composable.ButterflyCard
import com.jeremieguillot.butterfly.presentation.home.composable.DetailSheetContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Destination
@RootNavGraph(start = true)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = SheetState(skipPartiallyExpanded = true))

//
//    LaunchedEffect(key1 = true) {
//        viewModel.uiEvent.collect { event ->
//            when (event) {
//                HomeContract.Navigation.OpenDetail -> {
//                    delay(20)
//                    scope.launch {
//                        scaffoldState.bottomSheetState.expand()
//                    }
//                }
//            }
//        }
//    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            DetailSheetContent(
                viewModel, navigator
            ) {
                scope.launch {
                    scaffoldState.bottomSheetState.hide()
                }
            }
        }
    ) { innerPadding ->
        HomeScreen(
            modifier = Modifier.padding(innerPadding),
            state = viewModel.state,
            navigator = navigator,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            errorEvents = viewModel.errorEvents
        ) {
            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }
}


@Composable
fun HomeScreen(
    modifier: Modifier,
    state: HomeContract.State,
    navigator: DestinationsNavigator,
    onEvent: (HomeContract.Event) -> Unit,
    uiEvent: Flow<HomeContract.Navigation>,
    errorEvents: Flow<HomeContract.Error>,
    openDetail: () -> Job
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



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.isSearchBarVisible) {

                        Box {
                            if (state.searchText.isEmpty()) {
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
                                value = state.searchText,
                                onValueChange = { onEvent(HomeContract.Event.SearchButterflies(it)) },
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
                        Text(text = stringResource(R.string.home_screen_title))
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onEvent(HomeContract.Event.ToggleSearchBar) }
                    ) {
                        Icon(
                            imageVector = if (state.isSearchBarVisible) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {

        if (!state.isViewLoading) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            ) {
                itemsIndexed(state.filteredButterflies) { index, butterfly ->
                    ButterflyCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        butterfly = butterfly
                    ) {
                        onEvent(HomeContract.Event.SetSelectedIndexButterfly(index))
                        openDetail()
                    }
                    Spacer(modifier = Modifier.height(16.dp))
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