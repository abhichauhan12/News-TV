package com.abhishek.newsapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.view.KeyEvent as AndroidKeyEvent

@Composable
fun Modifier.onDpadDownLongPress(action: () -> Unit): Modifier {
    var isLongPressing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    return this.onKeyEvent { event ->
        if (event.nativeKeyEvent.keyCode == AndroidKeyEvent.KEYCODE_DPAD_DOWN) {
            when (event.nativeKeyEvent.action) {
                AndroidKeyEvent.ACTION_DOWN -> {
                    isLongPressing = true
                    scope.launch {
                        delay(2000L) // Adjust duration for long press detection
                        if (isLongPressing) {
                            action()
                        }
                    }
                }
                AndroidKeyEvent.ACTION_UP -> {
                    isLongPressing = false
                }
            }
            true
        } else {
            false
        }
    }
}
