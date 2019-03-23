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
  ['table', 'table'],
  ['hl', 'misc2'],
  ['border', 'misc1'],
  ['tree', 'tree'],
  ['notif', 'notif'],
  ['accent', 'accent'],
];

// Function to replace placeholders in a text
const replacePlaceholders = (text, theme) => {
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
  const result = replacePlaceholders(template, theme);
  fs.writeFileSync(`./src/main/resources/themes/${theme.name}.theme.json`, result, 'utf8');
});
