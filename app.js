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

import fs from "fs";
import yaml from "yaml";
import {darken, lighten} from "polished";

// Replacements
let props = [
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
  ['acc2', 'acc2'],
  ['gray', 'comments'],
  ['white', 'vars'],
  ['cyan', 'operators'],
  ['purple', 'keywords'],
  ['blue', 'functions'],
  ['red', 'tags'],
  ['green', 'strings'],
  ['yellow', 'attributes'],
  ['orange', 'numbers'],
  ['error', 'error'],
  ['comments', 'comments'],
  ['vars', 'vars'],
  ['operators', 'operators'],
  ['keywords', 'keywords'],
  ['functions', 'functions'],
  ['tags', 'tags'],
  ['strings', 'strings'],
  ['attributes', 'attributes'],
  ['numbers', 'numbers'],
  ['parameters', 'parameters'],
  ['classes', 'classes'],
  ['links', 'links'],
];

let contrastProps = [
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
  ['acc2', 'acc2'],
  ['gray', 'comments'],
  ['white', 'vars'],
  ['cyan', 'operators'],
  ['purple', 'keywords'],
  ['blue', 'functions'],
  ['red', 'tags'],
  ['green', 'strings'],
  ['yellow', 'attributes'],
  ['orange', 'numbers'],
  ['error', 'error'],
  ['comments', 'comments'],
  ['vars', 'vars'],
  ['operators', 'operators'],
  ['keywords', 'keywords'],
  ['functions', 'functions'],
  ['tags', 'tags'],
  ['strings', 'strings'],
  ['attributes', 'attributes'],
  ['numbers', 'numbers'],
  ['parameters', 'parameters'],
  ['classes', 'classes'],
  ['links', 'links'],
];

let globalProps = [
  ['shadow', '#ffffff20', '#00000020'],
  ['notifError', '#ef9694', '#b71c1c'],
  ['notifWarn', '#ffeca0', '#5d4037'],
  ['notifInfo', '#87bb91', '#1b5e20'],
  ['parent', 'ExperimentalLight', 'ExperimentalDark'],
  ['FileColor.Green', '#89CC8E', '#27382875'],
  ['FileColor.Blue', '#6B9BFA', '#25324D75'],
  ['FileColor.Red', '#EB938D', '#472B2B75'],
  ['FileColor.Yellow', '#F7DE8B', '#5E4D3375'],
  ['FileColor.Purple', '#C4A0F3', '#3B314775'],
  ['FileColor.Orange', '#F5BD98', '#61443875'],
];

let sizesProps = [
  ['tabInsets', "-10,10,-10,10", "0,8,0,8"],
  ['rowHeight', "28", "20"],
  ['btnHeight', "80,34", "72,24"],
  ['tabCellBorder', "10,3,10,3", "2,3,2,3"],
]

// Function to replace placeholders in a text
const replacePlaceholders = (text, theme, props, {contrast, compact}) => {
  let result = text;
  let name = theme.name;

  // replace name
  if (compact) {
    name += ' Compact';
  }
  if (contrast) {
    name += ' Contrast';
  }
  result = result.replace(new RegExp(`%name`, 'g'), name);

  sizesProps.forEach(([prop, normalProp, compactProp]) => {
    result = result.replace(new RegExp(`%${prop}`, 'g'), compact ? compactProp : normalProp);
  });

  props.forEach(([placeholder, prop]) => {
    result = result.replace(new RegExp(`%${placeholder}`, 'g'), theme[prop]);
    result = result.replace(new RegExp(`"@${placeholder}"`, 'g'), theme[prop]);
  });

  // Foreground contrast
  const contrastedForeground = contrastifyForeground({dark: theme.dark, color: theme.foreground, contrast});
  result = result.replace(new RegExp(`%fc`, 'g'), contrastedForeground);

  // Background contrast
  const contrastedBackground = contrastifyBackground({dark: theme.dark, color: theme.background, contrast});
  result = result.replace(new RegExp(`%bc`, 'g'), contrastedBackground);

  // Dark/Light for Fleet
  result = result.replace(new RegExp(`-dark-`, 'g'), theme.dark ? 'Dark' : 'Light');

  globalProps.forEach(([prop, light, dark]) => {
    let isDark = theme.dark;
    result = result.replace(new RegExp(`%${prop}`, 'g'), isDark ? dark : light);
  });

  return result;
};

function contrastifyForeground({dark, color, contrast}) {
  let result;
  if (dark) {
    result = contrast ? lighten(0.1, color) : color;
  } else {
    result = contrast ? darken(0.1, color) : color;
  }

  if (result.length === 4) {
    result = convertShortHexToLongHex(result);
  }

  return result;
}

function contrastifyBackground({dark, color, contrast}) {
  let result;
  if (dark) {
    result = contrast ? darken(0.05, color) : color;
  } else {
    result = contrast ? lighten(0.05, color) : color;
  }

  if (result.length === 4) {
    result = convertShortHexToLongHex(result);
  }

  return result;
}

function convertShortHexToLongHex(shortHex) {
  return shortHex
    .split('')
    .map((char) => char.repeat(2))
    .join('')
    .slice(1);
}

console.log('Reading themes...');

// Read the themes
const themesFile = fs.readFileSync('./src/main/resources/themes.yml', 'utf8');
const themes = yaml.parse(themesFile);
const {material, other} = themes;
const allThemes = [...material, ...other];

console.log('Themes:', allThemes.map((theme) => theme.name).join(', '));

// Output files
const template = fs.readFileSync('./src/main/resources/template.theme.json', 'utf8');
const fleetTemplate = fs.readFileSync('./fleet-template.json', 'utf8');

console.log('Generating folders...');

// Directories
const themesDir = './src/main/resources/themes';
fs.mkdirSync(`${themesDir}/regular`, {recursive: true});
fs.mkdirSync(`${themesDir}/compact`, {recursive: true});
fs.mkdirSync(`${themesDir}/contrast`, {recursive: true});
fs.mkdirSync(`${themesDir}/compactContrast`, {recursive: true});

// Fleet Dirs
const fleetThemesDir = './fleet/frontendImpl/src/jvmMain/resources';

console.log('Generating themes...');

// Generate Themes
allThemes.forEach((theme) => {
  const themeStr = replacePlaceholders(template, theme, props, {contrast: false, compact: false});
  fs.writeFileSync(`./src/main/resources/themes/regular/${theme.name}.theme.json`, themeStr, 'utf8');

  const contrastThemeStr = replacePlaceholders(template, theme, contrastProps, {contrast: true, compact: false});
  fs.writeFileSync(`./src/main/resources/themes/contrast/${theme.name} Contrast.theme.json`, contrastThemeStr, 'utf8');

  const compactThemeStr = replacePlaceholders(template, theme, props, {contrast: false, compact: true});
  fs.writeFileSync(`./src/main/resources/themes/compact/${theme.name} Compact.theme.json`, compactThemeStr, 'utf8');

  const compactContrastThemeStr = replacePlaceholders(template, theme, contrastProps, {contrast: true, compact: true});
  fs.writeFileSync(`./src/main/resources/themes/compactContrast/${theme.name} Compact Contrast.theme.json`, compactContrastThemeStr, 'utf8');
});

console.log('Generating Fleet themes...');

// Generate Fleet Themes
allThemes.forEach((theme) => {
  const themeStr = replacePlaceholders(fleetTemplate, theme, props, {contrast: false, compact: false});
  fs.writeFileSync(`${fleetThemesDir}/${theme.name}.theme.json`, themeStr, 'utf8');

  const contrastThemeStr = replacePlaceholders(fleetTemplate, theme, contrastProps, {contrast: true, compact: false});
  fs.writeFileSync(`${fleetThemesDir}/${theme.name} Contrast.theme.json`, contrastThemeStr, 'utf8');

  // const compactThemeStr = replacePlaceholders(fleetTemplate, theme, props, {contrast: false, compact: true});
  // fs.writeFileSync(`${fleetThemesDir}/${theme.name} Compact.theme.json`, compactThemeStr, 'utf8');
  //
  // const compactContrastThemeStr = replacePlaceholders(fleetTemplate, theme, contrastProps, {contrast: true, compact: true});
  // fs.writeFileSync(`${fleetThemesDir}/${theme.name} Compact Contrast.theme.json`, compactContrastThemeStr, 'utf8');
});

console.log('Done!');
