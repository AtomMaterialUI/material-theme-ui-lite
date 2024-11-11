/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2022 Elior "Mallowigi" Boukhobza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */
package com.mallowigi.idea.notifications

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsContexts
import com.mallowigi.idea.MaterialThemeBundle.message
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull

/** Service for sending notifications. */
object MTNotifications {
  /** Notification channel ID. */
  private val CHANNEL: String = message("material.theme.lite.notifications")

  @NonNls
  private const val GROUP_ID = "MaterialThemeLite"

  /**
   * Show a simple notification
   *
   * @param project the project concerned
   * @param content the content text
   */
  @JvmStatic
  fun showSimple(
    project: Project,
    @NlsContexts.NotificationContent content: String,
  ) {
    val notification = createNotification("", content, NotificationType.INFORMATION)
    Notifications.Bus.notify(notification, project)
  }

  /**
   * Shows [Notification] in [MTNotifications.CHANNEL] group.
   *
   * @param project current project
   * @param title notification title
   * @param content notification text
   * @param type notification type
   * @param action action
   */
  @JvmStatic
  fun showWithListener(
    project: Project,
    @NlsContexts.NotificationTitle title: String,
    @NlsContexts.NotificationContent content: String,
    type: NotificationType,
    action: AnAction,
  ) {
    val notification = createNotification(title, content, type, action)
    Notifications.Bus.notify(notification, project)
  }

  /**
   * Create a notification
   *
   * @param title notification title
   * @param content the content
   * @param type the type (sticky...)
   * @return new notification to be displayed
   */
  private fun createNotification(
    @NlsContexts.NotificationTitle title: String,
    @NlsContexts.NotificationContent content: String,
    type: NotificationType,
  ): Notification {
    return Notification(GROUP_ID, title, content, type)
  }

  /**
   * Create a notification
   *
   * @param title notification title
   * @param content the content
   * @param type the type (sticky…)
   * @param action action
   * @return new notification to be displayed
   */
  private fun createNotification(
    @NlsContexts.NotificationTitle title: String,
    @NlsContexts.NotificationContent content: String,
    type: NotificationType,
    @NotNull action: AnAction,
  ): Notification = createNotification(title, content, type).addAction(action)

}
