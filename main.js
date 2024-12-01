const { app, BrowserWindow } = require('electron');
const path = require('path');

let mainWindow;

app.on('ready', () => {
    mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            preload: path.join(__dirname, 'script.js') // Optional if you want Node.js in your JS file
        }
    });

    mainWindow.loadFile('index.html'); // Loads your HTML file
});

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') app.quit();
});