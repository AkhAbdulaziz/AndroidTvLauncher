package com.example.mylauncher.component

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Text

@Composable
fun AppListScreen(context: Context) {
    val packageManager = context.packageManager
    val apps = remember {
        val intent = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        intent.filter { appInfo ->
            packageManager.getLaunchIntentForPackage(appInfo.packageName) != null
        }
    }

    Column(Modifier.fillMaxWidth()) {
        Text(text = "Apps", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        TvLazyRow(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            items(apps + apps + apps + apps + apps) { app ->
                AppItem(app, packageManager, context)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppItem(app: ApplicationInfo, packageManager: PackageManager, context: Context) {
    var isFocused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(if (isFocused) 150.dp else 140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .then(if (isFocused) Modifier.border(4.dp, Color.Green, RoundedCornerShape(16.dp)) else Modifier)
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .clickable {
                val intent = packageManager.getLaunchIntentForPackage(app.packageName)
                context.startActivity(intent)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val icon = packageManager.getApplicationIcon(app.packageName)
        val bitmap = icon.toBitmap()

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = packageManager.getApplicationLabel(app).toString(),
            color = Color.White,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(if (isFocused) Color.Green else Color.Gray)
                .basicMarquee(delayMillis = 10000, velocity = 50.dp)
                .padding(8.dp),
            fontSize = 18.sp
        )
    }
}
