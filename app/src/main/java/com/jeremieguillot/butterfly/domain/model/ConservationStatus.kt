package com.jeremieguillot.butterfly.domain.model

import androidx.compose.ui.graphics.Color

enum class ConservationStatus(
    val title: String,
    val color: Color,
    val textColor: Color = Color.Black
) {
    CO("Effrondré", Color.Black, Color.White),
    CR("En danger critique", Color(211, 44, 31)),
    EN("En danger", Color(246, 191, 66)),
    VU("Vulnérable", Color(250, 236, 79)),
    NT("Quasi menacé", Color(250, 242, 204)),
    LC("Préoccupation mineure", Color(122, 181, 75)),
    DD("Données insuffisantes", Color(211, 211, 211)),
    NE("Non évalué", Color.White),
}