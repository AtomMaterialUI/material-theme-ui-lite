/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 Elior "Mallowigi" Boukhobza
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


fun properties(key: String) = providers.gradleProperty(key).get()
fun environment(key: String) = providers.environmentVariable(key)
fun fileContents(filePath: String) = providers.fileContents(layout.projectDirectory.file(filePath)).asText

val platformVersion: String by project

val pluginName: String by project
val fleetPluginID: String by project
val pluginVersion: String by project
val pluginDescription: String by project
val pluginSinceBuild: String by project
val pluginUntilBuild: String by project

val pluginVendorName: String by project
val pluginVendorEmail: String by project
val pluginVendorUrl: String by project

val pluginChannels: String by project

val javaVersion: String by project
val gradleVersion: String by project

plugins {
  base
  alias(libs.plugins.fleet.plugin)
}

version = pluginVersion

fleetPlugin {
  id = fleetPluginID

  metadata {
    readableName = pluginName
    description = pluginDescription

    icons {
      default.set(project.layout.projectDirectory.file("pluginIcon.svg"))
      dark.set(project.layout.projectDirectory.file("pluginIcon.svg"))
    }
  }

  fleetRuntime {
    version = libs.versions.fleet.runtime
  }

  publishing {
    vendorId = pluginVendorName
  }
}
