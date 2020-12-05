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
package com.mallowigi.idea;

import com.intellij.ide.BrowserUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;
import java.net.URL;

/**
 * Component for showing update notification
 */
public final class MTUpdatesComponent implements StartupActivity {
  private static final String VERSION = "MTUILite.version";

  /**
   * Open Paypal/OpenCollective link and add event
   *
   * @param notification The notification
   * @param event        The click to link event
   */
  private static void onPaypalClick(final Notification notification, final HyperlinkEvent event) {
    final URL url = event.getURL();

    if (url == null) {
      BrowserUtil.browse(event.getDescription());
    } else {
      BrowserUtil.browse(url);
    }

    notification.expire();
  }

  private static void showUpdates(final Project project) {
    final String pluginVersion = PropertiesComponent.getInstance().getValue(VERSION, "0.0");
    // Show new version notification
    @NonNls final String newVersion = MaterialThemeBundle.message("plugin.version");
    final boolean updated = !pluginVersion.equals(newVersion);

    // Show notification update
    if (updated) {
      PropertiesComponent.getInstance().setValue(VERSION, newVersion);
      Notify.showUpdate(project, MTUpdatesComponent::onPaypalClick);
    }
  }

  @Override
  public void runActivity(@NotNull final Project project) {
    showUpdates(project);
  }
}
