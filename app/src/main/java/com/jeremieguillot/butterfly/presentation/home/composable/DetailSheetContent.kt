@file:OptIn(ExperimentalFoundationApi::class)

package com.jeremieguillot.butterfly.presentation.home.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeremieguillot.butterfly.presentation.detail.DetailScreen
import com.jeremieguillot.butterfly.presentation.home.HomeViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun DetailSheetContent(
    viewModel: HomeViewModel,
    navigator: DestinationsNavigator,
    onClose: () -> Unit
) {

    val initialPage = viewModel.state.selectedIndexButterfly
    val pagerState = rememberPagerState(
        pageCount = {
            viewModel.state.butterflies.size
        },
        initialPage = initialPage
    )
    Column {

        // ce sont les petits points en haut de chaque vues
//                Row(
//                    Modifier
//                        .height(10.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    repeat(pagerState.pageCount) { iteration ->
//                        val color =
//                            if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
//                        Box(
//                            modifier = Modifier
//                                .padding(2.dp)
//                                .clip(CircleShape)
//                                .background(color)
//                                .size(20.dp)
//
//                        )
//                    }
//                }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) { index ->
            DetailScreen(
                butterfly = viewModel.state.butterflies[index],
                viewModel.state.confusionButterflies,
                navigator = navigator,
                onClose,
            )
        }

    }
}