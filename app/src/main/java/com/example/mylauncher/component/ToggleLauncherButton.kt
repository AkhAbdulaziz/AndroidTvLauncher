package com.example.mylauncher.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.example.mylauncher.utils.toggleLauncher

@Composable
fun ToggleLauncherButton(context: Context) {
    var isEnabled by remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isFocused) Color.Green else Color.White)
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .clickable {
                isEnabled = !isEnabled
                toggleLauncher(context, isEnabled)
            }
            .padding(10.dp)
    ) {
        Text("Disable Launcher", color = if (isFocused) Color.White else Color.Black, fontSize = 16.sp)
    }
}
