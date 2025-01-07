const path = require('path');
const fs = require('fs');

const { app, BrowserWindow, ipcMain } = require('electron');
const { exec } = require('child_process');
const { join } = require('path');

const isDev = !app.isPackaged;
app.whenReady().then(main);

function main() {
    const window = new BrowserWindow({
        width: 1200, 
        height: 700, 
        show: false,
        autoHideMenuBar: true,
        webPreferences: {
            preload: join(__dirname, './preload.js')
        }
    });

    window.loadFile(join(__dirname, '../public/index.html'));
    window.once('ready-to-show', window.show);

    if (isDev) {
        //window.webContents.openDevTools();
    }
}

ipcMain.handle('get-version', () => app.getVersion());

// Function to run a Java file
ipcMain.handle('run-java-file', (event, filePath) => {
    console.log('Received file path:', filePath);
    if (!filePath || typeof filePath !== 'string') {
        console.error('Invalid or missing file path:', filePath);
        return;
    }

    const absoluteFilePath = path.resolve(filePath);
    console.log('Resolved absolute file path:', absoluteFilePath);

    if (!fs.existsSync(absoluteFilePath)) {
        console.error('File does not exist:', absoluteFilePath);
        return;
    }

    const fileName = path.basename(absoluteFilePath, '.class');
    const dirPath = path.dirname(absoluteFilePath);

    // Log the command we're running
    console.log(`Running Java command: java -cp "${dirPath}" ${fileName}`);

    exec(`java -cp "${dirPath}" ${fileName}`, (error, stdout, stderr) => {
        if (error) {
            console.error(`exec error: ${error}`);
            return;
        }

        // Log both stdout and stderr for more visibility
        if (stdout) {
            console.log(`stdout: ${stdout}`);
        }

        if (stderr) {
            console.error(`stderr: ${stderr}`);
        }
    });
});

// Function to read a TXT file
ipcMain.handle('read-txt-file', (event, filePath) => {
    return new Promise((resolve, reject) => {
        fs.readFile(filePath, 'utf8', (err, data) => {
            if (err) reject(err);
            else resolve(data);
        });
    });
});

// Function to write to a TXT file
ipcMain.handle('write-txt-file', (event, filePath, content) => {
    return new Promise((resolve, reject) => {
        fs.writeFile(filePath, content, 'utf8', (err) => {
            if (err) reject(err);
            else resolve('File written successfully!');
        });
    });
});
