/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Chris Magnussen and Elior Boukhobza
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

package com.mallowigi.idea.themes;

import com.mallowigi.idea.ThemesBundle;
import org.jetbrains.annotations.NonNls;

/**
 * Facade for accessing internal theme's methods.
 * Contains a list of predefined themes and will contain all bundled themes
 */
public enum MTThemes {
  OCEANIC("OCEANIC", ThemesBundle.message("mt.oceanic.name")),
  DARKER("DARKER", ThemesBundle.message("mt.darker.name")),
  LIGHTER("LIGHTER", ThemesBundle.message("mt.lighter.name")),
  PALENIGHT("PALENIGHT", ThemesBundle.message("mt.palenight.name")),
  DEEPOCEAN("DEEPOCEAN", ThemesBundle.message("mt.deepocean.name")),
  MONOKAI("MONOKAI", ThemesBundle.message("monokai.name")),
  ARC_DARK("ARC_DARK", ThemesBundle.message("arc.dark.name")),
  ONE_DARK("ONE_DARK", ThemesBundle.message("one.dark.name")),
  ONE_LIGHT("ONE_LIGHT", ThemesBundle.message("one.light.name")),
  SOLARIZED_DARK("SOLARIZED_DARK", ThemesBundle.message("solarized.dark.name")),
  SOLARIZED_LIGHT("SOLARIZED_LIGHT", ThemesBundle.message("solarized.light.name")),
  DRACULA("DRACULA", ThemesBundle.message("dracula.name")),
  NIGHT_OWL("NIGHT_OWL", ThemesBundle.message("night_owl.name")),
  LIGHT_OWL("LIGHT_OWL", ThemesBundle.message("light_owl.name")),
  GITHUB("GITHUB", ThemesBundle.message("github.name")),
  MOONLIGHT("MOONLIGHT", ThemesBundle.message("moonlight.name"));


  private final String id;
  private final transient String name;
  /**
   * Represent a theme
   *
   * @param id    the theme name
   * @param name the themeable
   */
  MTThemes(@NonNls final String id, final String name) {
    this.id = id;
    this.name = name;
  }

  private String getId() {
    return id;
  }

  protected String getName() {
    return name;
  }
}
