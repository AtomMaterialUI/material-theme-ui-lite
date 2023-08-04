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
package com.mallowigi.idea

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.util.registry.Registry

/** Runs at start. */
class MTStartup : ProjectActivity, Disposable {
  override suspend fun execute(project: Project) = runActivity()

  override fun dispose() = onClose()

  /** Modify the registry at start. */
  private fun runActivity() {
    modifyRegistry()
    ApplicationManager.getApplication().messageBus.connect().also {
      it.subscribe(AppLifecycleListener.TOPIC, object : AppLifecycleListener {
        override fun appClosing() {
          onClose()
          it.disconnect()
        }
      })
    }
  }

  private fun onClose() {
    Registry.get(IDE_BALLOON_SHADOW_SIZE).setValue(15)
    Registry.get(IDE_INTELLIJ_LAF_ENABLE_ANIMATION).setValue(false)
  }

  private fun modifyRegistry() {
    Registry.get(IDE_BALLOON_SHADOW_SIZE).setValue(0)
    Registry.get(IDE_INTELLIJ_LAF_ENABLE_ANIMATION).setValue(true)
  }

  companion object {
    private const val IDE_BALLOON_SHADOW_SIZE = "ide.balloon.shadow.size"
    private const val IDE_INTELLIJ_LAF_ENABLE_ANIMATION = "ide.intellij.laf.enable.animation"
  }
}
