package com.jeremieguillot.butterfly.presentation.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DetailScreen(
    butterfly: ButterflyModel
) {

    Text(text = butterfly.commonName)
}