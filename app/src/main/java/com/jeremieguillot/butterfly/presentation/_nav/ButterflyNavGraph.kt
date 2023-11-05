package com.jeremieguillot.butterfly.presentation._nav

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@NavGraph
@RootNavGraph(start = true)
annotation class ButterflyNavGraph(
    val start: Boolean = false
)