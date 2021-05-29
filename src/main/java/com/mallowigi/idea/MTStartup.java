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

package com.mallowigi.idea;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public final class MTStartup implements StartupActivity {
  public static final String IDE_BALLOON_SHADOW_SIZE = "ide.balloon.shadow.size";
  public static final String IDE_INTELLIJ_LAF_ENABLE_ANIMATION = "ide.intellij.laf.enable.animation";

  @Override
  public void runActivity(@NotNull final Project project) {
    modifyRegistry();

    final MessageBusConnection connect = ApplicationManager.getApplication().getMessageBus().connect();
    connect.subscribe(AppLifecycleListener.TOPIC, new AppLifecycleListener() {
      @Override
      public void appClosing() {
        Registry.get(IDE_BALLOON_SHADOW_SIZE).setValue(15);
        Registry.get(IDE_INTELLIJ_LAF_ENABLE_ANIMATION).setValue(false);
        connect.disconnect();
      }
    });
  }

  private static void modifyRegistry() {
    Registry.get(IDE_BALLOON_SHADOW_SIZE).setValue(0);
    Registry.get(IDE_INTELLIJ_LAF_ENABLE_ANIMATION).setValue(true);
  }
}
