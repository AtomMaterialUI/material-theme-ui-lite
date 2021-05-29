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

const fs = require('fs');
const yaml = require('yaml');

// Replacements
let props = [
  ['name', 'name'],
  ['dark', 'dark'],
  ['scheme', 'scheme'],
  ['id', 'className'],
  ['bg', 'background'],
  ['fg', 'foreground'],
  ['text', 'text'],
  ['selBg', 'selectBg'],
  ['selFg', 'selectFg'],
  ['activeFg', 'selectFg2'],
  ['button', 'button'],
  ['second', 'second'],
  ['dis', 'disabled'],
  ['cs', 'contrast'],
  ['hc', 'background'],
  ['table', 'table'],
  ['border', 'border'],
  ['hl', 'hl'],
  ['tree', 'tree'],
  ['notif', 'notif'],
  ['accent', 'accent'],
  ['excl', 'excluded'],
  ['accent2', 'links'],
  ['gray', 'comments'],
  ['white', 'vars'],
  ['cyan', 'operators'],
  ['purple', 'keywords'],
  ['blue', 'functions'],
  ['red', 'tags'],
  ['green', 'strings'],
  ['yellow', 'attributes'],
  ['orange', 'numbers'],
];

let contrastProps = [
  ['name', 'name2'],
  ['dark', 'dark'],
  ['scheme', 'scheme'],
  ['id', 'className'],
  ['bg', 'background'],
  ['fg', 'foreground'],
  ['text', 'text'],
  ['selBg', 'selectBg'],
  ['selFg', 'selectFg'],
  ['activeFg', 'selectFg2'],
  ['button', 'button'],
  ['second', 'second'],
  ['dis', 'disabled'],
  ['cs', 'contrast'],
  ['hc', 'contrast'],
  ['table', 'table'],
  ['border', 'border'],
  ['hl', 'hl'],
  ['tree', 'tree'],
  ['notif', 'notif'],
  ['accent', 'accent'],
  ['excl', 'excluded'],
  ['accent2', 'links'],
  ['gray', 'comments'],
  ['white', 'vars'],
  ['cyan', 'operators'],
  ['purple', 'keywords'],
  ['blue', 'functions'],
  ['red', 'tags'],
  ['green', 'strings'],
  ['yellow', 'attributes'],
  ['orange', 'numbers'],
];

// Function to replace placeholders in a text
const replacePlaceholders = (text, theme, props) => {
  let result = text;
  props.forEach(([placeholder, prop]) => {
    console.log(`Replacing ${placeholder} with property ${prop}: ${theme[prop]}`);
    result = result.replace(new RegExp(`%${placeholder}`, 'g'), theme[prop]);
    result = result.replace(new RegExp(`"@${placeholder}"`, 'g'), theme[prop]);
  });
  return result;
};

// Read the themes
const themesFile = fs.readFileSync('./src/main/resources/themes.yml', 'utf8');
const themes = yaml.parse(themesFile);
const {material, other} = themes;
const allThemes = [...material, ...other];

// Output files
const template = fs.readFileSync('./src/main/resources/template.theme.json', 'utf8');

allThemes.forEach((theme) => {
  const result = replacePlaceholders(template, theme, props);
  fs.writeFileSync(`./src/main/resources/themes/${theme.name}.theme.json`, result, 'utf8');

  const result2 = replacePlaceholders(template, theme, contrastProps);
  fs.writeFileSync(`./src/main/resources/themes/${theme.name} Contrast.theme.json`, result2, 'utf8');

});
