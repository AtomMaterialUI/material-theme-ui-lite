const fs = require('fs');
const yaml = require('yaml');

// Replacements
let props = [
  ['name', 'name'],
  ['dark', 'dark'],
  ['scheme', 'scheme'],
  ['bg', 'background'],
  ['fg', 'foreground'],
  ['text', 'text'],
  ['selBg', 'selectBg'],
  ['selFg', 'selectFg'],
  ['button', 'button'],
  ['second', 'second'],
  ['dis', 'disabled'],
  ['cs', 'contrast'],
  ['hc', 'background'],
  ['table', 'table'],
  ['border', 'misc1'],
  ['hl', 'misc2'],
  ['tree', 'tree'],
  ['notif', 'notif'],
  ['accent', 'accent'],
  ['excl', 'excluded']
];

let contrastProps = [
  ['name', 'name2'],
  ['dark', 'dark'],
  ['scheme', 'scheme'],
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
  ['border', 'misc1'],
  ['hl', 'misc2'],
  ['tree', 'tree'],
  ['notif', 'notif'],
  ['accent', 'accent'],
  ['excl', 'excluded']
];

// Function to replace placeholders in a text
const replacePlaceholders = (text, theme, props) => {
  let result = text;
  props.forEach(([placeholder, prop]) => {
    console.log(`Replacing ${placeholder} with property ${prop}: ${theme[prop]}`);
    result = result.replace(new RegExp(`%${placeholder}`, 'g'), theme[prop]);
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
