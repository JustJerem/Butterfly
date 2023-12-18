@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)

package com.jeremieguillot.butterfly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.jeremieguillot.butterfly.presentation.NavGraphs
import com.jeremieguillot.butterfly.presentation.home.HomeViewModel
import com.jeremieguillot.butterfly.ui.theme.ButterflyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButterflyTheme {
                DestinationsNavHost(navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = { //this: DependenciesContainerBuilder<*>

                        // 👇 To tie SettingsViewModel to "settings" nested navigation graph,
                        // making it available to all screens that belong to it
                        dependency(NavGraphs.root) {
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry(NavGraphs.root.route)
                            }
                            hiltViewModel<HomeViewModel>(parentEntry)
                        }
                    })
            }
        }
    }
}