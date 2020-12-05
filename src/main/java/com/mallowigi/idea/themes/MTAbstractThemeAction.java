/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Elior "Mallowigi" Boukhobza
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

package com.mallowigi.idea.themes;

import com.intellij.ide.ui.LafManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.util.containers.ContainerUtil;
import com.mallowigi.idea.MaterialThemeBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * Abstract Material Theme switch action
 */
public abstract class MTAbstractThemeAction extends MTToggleAction implements DumbAware {

  @Override
  public final void setSelected(@NotNull final AnActionEvent e, final boolean state) {
    // Find LAF theme and trigger a theme change
    final LafManager lafManager = LafManager.getInstance();
    final UIManager.LookAndFeelInfo lafInfo = ContainerUtil.find(lafManager.getInstalledLookAndFeels(),
        lookAndFeelInfo -> lookAndFeelInfo.getName().equals(getThemeName(e)));

    if (lafInfo != null) {
      lafManager.setCurrentLookAndFeel(lafInfo);
    }
  }

  @Override
  public final boolean isSelected(@NotNull final AnActionEvent e) {
    return Objects.requireNonNull(LafManager.getInstance().getCurrentLookAndFeel()).getName().equals(getThemeName(e));
  }

  /**
   * Returns the theme to apply
   *
   * @return the theme
   */
  protected abstract MTThemes getTheme();

  @NonNls
  private String getThemeName(@NotNull final AnActionEvent e) {
    final String contrast = MaterialThemeBundle.message("contrast");

    final boolean isContrast = e.getPresentation().getText() != null && e.getPresentation().getText().contains(contrast);
    final String name = getTheme().getName();

    return isContrast ? MessageFormat.format("{0} {1}", name, contrast) : name;
  }
}
