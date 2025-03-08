package com.example.mylauncher.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import com.example.mylauncher.MainActivity

fun toggleLauncher(context: Context, enable: Boolean) {
    val componentName = ComponentName(context, MainActivity::class.java)
    val packageManager = context.packageManager

    if (enable) {
        // Enable as launcher
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        // Restart home screen
        restartHomeScreen(context)
    } else {
        // Show launcher chooser first
        showLauncherChooser(context)

        // Wait before disabling launcher function
        Handler(Looper.getMainLooper()).postDelayed({
            packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, // Keep app visible but remove launcher role
                PackageManager.DONT_KILL_APP
            )
        }, 2000)
    }
}


fun showLauncherChooser(context: Context) {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(homeIntent)
}

fun restartHomeScreen(context: Context) {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}