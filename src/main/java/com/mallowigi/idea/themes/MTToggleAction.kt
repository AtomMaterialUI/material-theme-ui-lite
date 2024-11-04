/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Elior "Mallowigi" Boukhobza
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
 */
package com.mallowigi.idea.themes

import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.intellij.openapi.actionSystem.Toggleable
import com.intellij.openapi.util.NlsActions
import com.intellij.ui.LayeredIcon
import com.intellij.util.IconUtil
import com.intellij.util.ObjectUtils
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI
import com.mallowigi.idea.MaterialThemeBundle.message
import com.mallowigi.idea.notifications.MTNotifications.showSimple
import java.awt.Component
import java.awt.Graphics
import javax.swing.Icon
import javax.swing.UIManager

/**
 * Main class for toggle actions that set icons, check licenses and show
 * notification among others.
 */
abstract class MTToggleAction(
  @NlsActions.ActionText text: String = "",
  @NlsActions.ActionDescription description: String = "",
  icon: Icon? = null,
) : ToggleAction(text, description, icon) {

  private val defaultIconSize = 18
  private val iconRadius = 4

  /** Whether the action is toggled. */
  abstract override fun isSelected(e: AnActionEvent): Boolean

  private fun getLayeredIcon(vararg icons: Icon): LayeredIcon {
    val layeredIcon = LayeredIcon(icons.size)
    icons.forEachIndexed { index, icon ->
      layeredIcon.setIcon(icon, index)
    }
    return layeredIcon
  }

  /**
   * Update the action preentation according to config, license, etc
   *
   * @param e event data
   */
  @Suppress("UnstableApiUsage")
  override fun update(e: AnActionEvent) {
    val selected = isSelected(e)
    val presentation = e.presentation
    val icon = presentation.icon

    Toggleable.setSelected(presentation, selected)
    val fallbackIcon = selectedFallbackIcon(icon)
    val actionButtonIcon = ObjectUtils.notNull(UIManager.getIcon("ActionButton.backgroundIcon"), fallbackIcon)

    if (ActionPlaces.isMacSystemMenuAction(e)) {
      // force showing check marks instead of toggle icons in the context menu
      presentation.icon = null
    } else {
      // Recreate the action button look
      when {
        selected -> e.presentation.icon = getLayeredIcon(actionButtonIcon, regularIcon(icon!!))
        else     -> e.presentation.icon = regularIcon(icon!!)
      }
    }
  }

  /**
   * Show a notification once toggled
   *
   * @param e the event data
   * @param state selected state
   */
  override fun setSelected(e: AnActionEvent, state: Boolean) {
    val notificationMessage = e.presentation.text
    val restText = message(if (state) "action.toggle.enabled" else "action.toggle.disabled")
    showSimple(e.project ?: return, message("notification.message", notificationMessage, restText))
  }

  private fun regularIcon(icon: Icon): Icon =
    IconUtil.toSize(icon, JBUI.scale(defaultIconSize), JBUI.scale(defaultIconSize))

  private fun selectedFallbackIcon(icon: Icon?): Icon = object : Icon {
    override fun paintIcon(component: Component, g: Graphics, x: Int, y: Int) {
      val g2d = g.create()
      try {
        GraphicsUtil.setupAAPainting(g2d)
        g2d.color = JBUI.CurrentTheme.ActionButton.pressedBackground()
        g2d.fillRoundRect(0, 0, iconWidth, iconHeight, iconRadius, iconRadius)
      } finally {
        g2d.dispose()
      }
    }

    override fun getIconWidth(): Int = icon?.iconWidth ?: JBUI.scale(defaultIconSize)

    override fun getIconHeight(): Int = icon?.iconHeight ?: JBUI.scale(defaultIconSize)
  }

  /** Make actions dumb aware. */
  override fun isDumbAware(): Boolean = true
}
