/**
 * ****************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2024 Elior "Mallowigi" Boukhobza
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ****************************************************************************
 */

@file:Suppress("SpellCheckingInspection", "HardCodedStringLiteral")

import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = providers.gradleProperty(key).get()
fun environment(key: String) = providers.environmentVariable(key)
fun fileContents(filePath: String) = providers.fileContents(layout.projectDirectory.file(filePath)).asText

val platformVersion: String by project

val pluginName: String by project
val pluginID: String by project
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
  id("java")

  alias(libs.plugins.kotlin)
  alias(libs.plugins.gradleIntelliJPlugin)
  alias(libs.plugins.changelog)
  alias(libs.plugins.detekt)
  alias(libs.plugins.ktlint)
}

group = pluginID
version = pluginVersion

repositories {
  mavenCentral()
  mavenLocal()
  gradlePluginPortal()

  intellijPlatform {
    defaultRepositories()
  }
}

dependencies {
  intellijPlatform {
    intellijIdeaUltimate(platformVersion, useInstaller = false)
    instrumentationTools()
//    local(properties("idePath"))

    pluginVerifier()
    zipSigner()
  }

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
}

intellijPlatform {
  buildSearchableOptions = false
  instrumentCode = true

  projectName = pluginName

  pluginConfiguration {
    id = pluginID
    name = pluginName
    version = pluginVersion
    // description = pluginDescription

    // Get the latest available change notes from the changelog file
    val changelog = project.changelog // local variable for configuration cache compatibility
    // Get the latest available change notes from the changelog file
    val pluginVersion = pluginVersion
    changeNotes.set(provider {
      with(changelog) {
        renderItem(
          (getOrNull(pluginVersion) ?: getUnreleased()).withHeader(false).withEmptySections(false),
          Changelog.OutputType.HTML,
        )
      }
    })

    ideaVersion {
      sinceBuild = pluginSinceBuild
      untilBuild = pluginUntilBuild
    }

    vendor {
      name = pluginVendorName
      email = pluginVendorEmail
      url = pluginVendorUrl
    }
  }

  publishing {
    token = environment("INTELLIJ_PUBLISH_TOKEN")
    channels = pluginChannels.split(',').map { it.trim() }
  }

  signing {
    certificateChain = fileContents("./chain.crt")
    privateKey = fileContents("./private.pem")
    password = fileContents("./private_encrypted.pem")
  }

  pluginVerification {
    ides {
      recommended()
      select {
        sinceBuild = pluginSinceBuild
        untilBuild = pluginUntilBuild
      }
    }
  }
}

changelog {
  path.set("${project.projectDir}/docs/CHANGELOG.md")
  version.set(pluginVersion)
  // header.set(provider { version })
  itemPrefix.set("-")
  keepUnreleasedSection.set(true)
  unreleasedTerm.set("Changelog")
  groups.set(listOf("Features", "Fixes", "Removals", "Other"))
}

detekt {
  config.from(files("./detekt-config.yml"))
  buildUponDefaultConfig = true
  autoCorrect = true
}

tasks {
  javaVersion.let {
    // Set the compatibility versions to 1.8
    withType<JavaCompile> {
      sourceCompatibility = it
      targetCompatibility = it
    }
    withType<KotlinCompile> {
      kotlinOptions.jvmTarget = it
      kotlinOptions.freeCompilerArgs += listOf("-Xskip-prerelease-check", "-Xjvm-default=all")
    }
  }

  wrapper {
    gradleVersion = properties("gradleVersion")
  }

  withType<Detekt> {
    jvmTarget = javaVersion
    reports.xml.required.set(true)
  }

  withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
  }

  sourceSets {
    main {
      java.srcDirs("src/main/java")
      resources.srcDirs("src/main/resources")
    }
  }

  register("markdownToHtml") {
    val input = File("./docs/CHANGELOG.md")
    File("./docs/CHANGELOG.html").run {
      writeText(markdownToHTML(input.readText()))
    }
  }
}
