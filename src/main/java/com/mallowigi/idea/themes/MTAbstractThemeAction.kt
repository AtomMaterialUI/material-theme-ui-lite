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

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAware
import com.mallowigi.idea.MaterialThemeBundle.message
import org.jetbrains.annotations.NonNls
import java.text.MessageFormat

/** Abstract class for switching themes. */
abstract class MTAbstractThemeAction : MTToggleAction(), DumbAware {
  /** Set selected theme. */
  override fun setSelected(e: AnActionEvent, state: Boolean) {
    super.setSelected(e, state)
    // Find LAF theme and trigger a theme change
    val lafManager = LafManager.getInstance()
    val lafInfo = lafManager.installedThemes.first { it.name == getThemeName(e) }
    if (lafInfo != null) lafManager.currentUIThemeLookAndFeel = lafInfo

    ApplicationManager.getApplication().messageBus
      .syncPublisher(LafManagerListener.TOPIC)
      .lookAndFeelChanged(lafManager)
  }

  /** Whether theme is selected. */
  override fun isSelected(e: AnActionEvent): Boolean =
    LafManager.getInstance().currentUIThemeLookAndFeel!!.name == getThemeName(e)

  /** The theme. */
  protected abstract val theme: MTThemes

  @NonNls
  private fun getThemeName(e: AnActionEvent): String {
    val contrast = message("contrast")
    val compact = message("compact")
    val text = e.presentation.text ?: return theme.themeName

    val isContrast = text.contains(contrast)
    val isCompact = text.contains(compact)
    val name = theme.themeName
    return when {
      isContrast && isCompact  -> MessageFormat.format("{0} {1} {2}", name, compact, contrast)
      isContrast && !isCompact -> MessageFormat.format("{0} {1}", name, contrast)
      !isContrast && isCompact -> MessageFormat.format("{0} {1}", name, compact)
      else                     -> name
    }
  }
}
