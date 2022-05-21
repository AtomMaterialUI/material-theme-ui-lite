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
@file:Suppress("KDocMissingDocumentation")

package com.mallowigi.idea.themes

import com.mallowigi.idea.ThemesBundle.message
import org.jetbrains.annotations.NonNls

/** Enum for theme names. */
enum class MTThemes(@param:NonNls private val id: String, @field:Transient val themeName: String) {
  OCEANIC("OCEANIC", message("mt.oceanic.name")),
  DARKER("DARKER", message("mt.darker.name")),
  LIGHTER("LIGHTER", message("mt.lighter.name")),
  PALENIGHT("PALENIGHT", message("mt.palenight.name")),
  DEEPOCEAN("DEEPOCEAN", message("mt.deepocean.name")),
  SKYBLUE("SKYBLUE", message("mt.skyblue.name")),
  SANDYBEACH("SANDYBEACH", message("mt.sandybeach.name")),
  FOREST("FOREST", message("mt.forest.name")),
  VOLCANO("VOLCANO", message("mt.volcano.name")),
  MONOKAI("MONOKAI", message("monokai.name")),
  ARC_DARK("ARC_DARK", message("arc.dark.name")),
  ONE_DARK("ONE_DARK", message("one.dark.name")),
  ONE_LIGHT("ONE_LIGHT", message("one.light.name")),
  SOLARIZED_DARK("SOLARIZED_DARK", message("solarized.dark.name")),
  SOLARIZED_LIGHT("SOLARIZED_LIGHT", message("solarized.light.name")),
  DRACULA("DRACULA", message("dracula.name")),
  NIGHT_OWL("NIGHT_OWL", message("nightowl.name")),
  LIGHT_OWL("LIGHT_OWL", message("lightowl.name")),
  GITHUB("GITHUB", message("github.name")),
  GITHUB_DARK("GITHUB_DARK", message("github_dark.name")),
  MOONLIGHT("MOONLIGHT", message("moonlight.name")),
  SYNTHWAVE("SYNTHWAVE", message("synthwave.name")),
}
