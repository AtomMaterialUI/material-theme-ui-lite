package com.mallowigi.idea;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public final class MTStartup implements StartupActivity {
  @Override
  public void runActivity(@NotNull final Project project) {
    modifyRegistry();

    final MessageBusConnection connect = ApplicationManager.getApplication().getMessageBus().connect();
    connect.subscribe(AppLifecycleListener.TOPIC, new AppLifecycleListener() {
      @Override
      public void appClosing() {
        Registry.get("ide.balloon.shadow.size").setValue(15);
        Registry.get("ide.intellij.laf.enable.animation").setValue(false);
        connect.disconnect();
      }
    });
  }

  private static void modifyRegistry() {
    Registry.get("ide.balloon.shadow.size").setValue(0);
    Registry.get("ide.intellij.laf.enable.animation").setValue(true);
  }
}
