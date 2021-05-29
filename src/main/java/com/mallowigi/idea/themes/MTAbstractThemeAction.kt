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
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.mallowigi.idea.MaterialThemeBundle.message
import org.jetbrains.annotations.NonNls
import java.text.MessageFormat

abstract class MTAbstractThemeAction : MTToggleAction(), DumbAware {
  override fun setSelected(e: AnActionEvent, state: Boolean) {
    // Find LAF theme and trigger a theme change
    val lafManager = LafManager.getInstance()
    val lafInfo = lafManager.installedLookAndFeels.find { it.name == getThemeName(e) }
    if (lafInfo != null) lafManager.currentLookAndFeel = lafInfo
  }

  override fun isSelected(e: AnActionEvent): Boolean = LafManager.getInstance().currentLookAndFeel!!.name == getThemeName(e)

  protected abstract val theme: MTThemes

  @NonNls
  private fun getThemeName(e: AnActionEvent): String {
    val contrast = message("contrast")
    val isContrast = e.presentation.text!!.contains(contrast)
    val name = theme.themeName
    return if (isContrast) MessageFormat.format("{0} {1}", name, contrast) else name
  }
}
