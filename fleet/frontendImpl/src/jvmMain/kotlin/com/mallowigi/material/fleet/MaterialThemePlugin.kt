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

package com.mallowigi.material.fleet

import fleet.dock.api.ThemeId
import fleet.frontend.theme.registerTheme
import fleet.kernel.plugins.ContributionScope
import fleet.kernel.plugins.Plugin
import fleet.kernel.plugins.PluginScope
import org.yaml.snakeyaml.Yaml
import java.io.InputStream

class MaterialThemePlugin : Plugin<Unit> {
  override val key: Plugin.Key<Unit> = MaterialThemePlugin

  override fun ContributionScope.load(pluginScope: PluginScope) {
    val themeNames = loadThemesFromYaml()

    themeNames.forEach {
      registerTheme(ThemeId(id = "${it}.theme"))
      registerTheme(ThemeId(id = "${it} Contrast.theme"))
      // registerTheme(ThemeId(id = "${it} Compact.theme"))
      // registerTheme(ThemeId(id = "${it} Contrast Compact.theme"))
    }
  }

  private fun loadThemesFromYaml(): List<String> {
    val inputStream: InputStream = this::class.java.getResourceAsStream("/themes.yml")
      ?: throw IllegalArgumentException("Resource not found: themes.yml")

    val yaml = Yaml()
    val data: Map<String, List<Map<String, String>>> = yaml.load(inputStream)
    val themes = data.values.flatten()

    return themes.mapNotNull { it["name"] }
  }

  companion object : Plugin.Key<Unit>

}
