package com.mkiperszmid.emptyapp.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
            //No se permite hacer click a elementos debajo
            .clickable(indication = null, interactionSource = remember { MutableInteractionSource() },
                onClick = {})
        ,
        contentAlignment = Alignment.Center
    ) {
        val grosorCirculo = 10.dp
        val sizeCirculo = 75.dp

        CircularProgressIndicator(
            modifier = Modifier.size(sizeCirculo)
                .drawBehind {
                    drawCircle(
                        color = Color.Gray,
                        radius = size.width / 2 - grosorCirculo.toPx() / 2,
                        style = Stroke(grosorCirculo.toPx())
                    )
                },
            color = Color.Blue,
            strokeWidth = grosorCirculo
        )
    }
}