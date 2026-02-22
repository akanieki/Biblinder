package com.biblinder.swipe

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlin.math.abs

enum class SwipeDirection { UP, DOWN, LEFT, RIGHT }
enum class AnimeAction { WATCHED, PLAN_TO_WATCH, WATCHING, DISCARD }

class SwipeViewModel : ViewModel() {
    var directionMap by mutableStateOf(
        mutableMapOf(
            SwipeDirection.UP to AnimeAction.WATCHED,
            SwipeDirection.DOWN to AnimeAction.DISCARD,
            SwipeDirection.LEFT to AnimeAction.PLAN_TO_WATCH,
            SwipeDirection.RIGHT to AnimeAction.WATCHING
        )
    )

    fun changeMapping(direction: SwipeDirection, action: AnimeAction) {
        directionMap[direction] = action
    }
}

@Composable
fun SwipeCard(
    modifier: Modifier = Modifier,
    viewModel: SwipeViewModel,
    onSwiped: (AnimeAction) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // toPx düzeltmesi: LocalDensity ile dp'yi px'e çeviriyoruz
    val density = LocalDensity.current
    val threshold = with(density) { 120.dp.toPx() }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, drag ->
                        offsetX += drag.x
                        offsetY += drag.y
                        change.consume()
                    },
                    onDragEnd = {
                        val action = when {
                            abs(offsetX) > abs(offsetY) && offsetX > threshold -> SwipeDirection.RIGHT
                            abs(offsetX) > abs(offsetY) && offsetX < -threshold -> SwipeDirection.LEFT
                            abs(offsetY) > abs(offsetX) && offsetY < -threshold -> SwipeDirection.UP
                            abs(offsetY) > abs(offsetX) && offsetY > threshold -> SwipeDirection.DOWN
                            else -> null
                        }?.let { dir -> viewModel.directionMap[dir] }

                        action?.let(onSwiped)
                        offsetX = 0f
                        offsetY = 0f
                    }
                )
            }
    ) {
        Text(
            text = "Swipe Me!",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
