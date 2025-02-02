package com.bizilabs.streeek.lib.common.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE

enum class AppNotificationChannel(
    val id: String,
    val label: String,
    val importance: Int,
    val group: AppNotificationGroup,
    val description: String,
) {
    GENERAL(
        id = "streeek.general",
        label = "General",
        importance = NotificationManager.IMPORTANCE_DEFAULT,
        group = AppNotificationGroup.UPDATES,
        description = "general updates",
    ),
    TEAM_REQUESTS(
        id = "streeek.team.requests",
        label = "Team Requests",
        importance = NotificationManager.IMPORTANCE_HIGH,
        group = AppNotificationGroup.USER,
        description = "updates on joining a team",
    ),
}

enum class AppNotificationGroup(
    val id: String,
) {
    UPDATES(id = "streeek.group.updates"),
    USER(id = "streeek.group.user"),
}

private fun AppNotificationChannel.asNotificationChannel() =
    NotificationChannel(id, label, importance).apply {
        description = this@asNotificationChannel.description
    }

fun Context.initNotificationChannels() {
    val channels = AppNotificationChannel.entries.map { it.asNotificationChannel() }
    val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannels(channels)
}
