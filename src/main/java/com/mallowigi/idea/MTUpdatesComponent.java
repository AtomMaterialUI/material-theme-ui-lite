/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Chris Magnussen and Elior Boukhobza
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
package com.mallowigi.idea;

import com.intellij.ide.BrowserUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.event.HyperlinkEvent;
import java.net.URL;

/**
 * Component for showing update notification
 */
public final class MTUpdatesComponent implements ProjectComponent {
  public static final String VERSION = "MTUILite.version";
  @NotNull
  private final Project myProject;

  private String pluginVersion;

  /**
   * Instantiates a new Mt updates component.
   *
   * @param project the project
   */
  private MTUpdatesComponent(@NotNull final Project project) {
    myProject = project;
  }

  /**
   * Open Paypal/OpenCollective link and add event
   *
   * @param notification The notification
   * @param event        The click to link event
   */
  private static void onPaypalClick(final Notification notification, final HyperlinkEvent event) {
    final URL url = event.getURL();

    try {
      @NonNls final JSONObject props = new JSONObject();
      props.put("Url", url);

    } catch (final JSONException ignored) {
    }

    if (url == null) {
      BrowserUtil.browse(event.getDescription());
    } else {
      BrowserUtil.browse(url);
    }

    notification.expire();
  }

  @Override
  public void initComponent() {
    pluginVersion = PropertiesComponent.getInstance().getValue(VERSION, "0.0");
  }

  @SuppressWarnings("FeatureEnvy")
  @Override
  public void projectOpened() {
    // Show new version notification
    final boolean updated = !pluginVersion.equals(MaterialThemeBundle.message("plugin.version"));

    // Show notification update
    if (updated) {
      PropertiesComponent.getInstance().setValue(VERSION, pluginVersion);
      Notify.showUpdate(myProject, MTUpdatesComponent::onPaypalClick);
    }
  }

  @Override
  public void disposeComponent() {
  }

  @NonNls
  @NotNull
  @Override
  public String getComponentName() {
    return "MTUpdatesComponent";
  }

}
