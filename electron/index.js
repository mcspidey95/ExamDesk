const path = require('path');
const fs = require('fs');

const { app, BrowserWindow, ipcMain, dialog } = require('electron');
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
ipcMain.handle('run-java-file', async (event, filePath) => {
    console.log('Received file path:', filePath);
    if (!filePath || typeof filePath !== 'string') {
        console.error('Invalid or missing file path:', filePath);
        throw new Error('Invalid file path'); // Throw an error so the renderer knows something went wrong
    }

    const absoluteFilePath = path.resolve(filePath);
    console.log('Resolved absolute file path:', absoluteFilePath);

    if (!fs.existsSync(absoluteFilePath)) {
        console.error('File does not exist:', absoluteFilePath);
        throw new Error('File does not exist'); // Throw an error if the file isn't found
    }

    const fileName = path.basename(absoluteFilePath, '.class');
    const dirPath = path.dirname(absoluteFilePath);

    // Return a Promise that resolves or rejects based on the execution result
    return new Promise((resolve, reject) => {
        console.log(`Running Java command: java -cp "${dirPath}" ${fileName}`);

        exec(`java -cp "${dirPath}" ${fileName}`, (error, stdout, stderr) => {
            if (error) {
                console.error(`Execution error: ${error}`);
                reject(error); // Reject the promise if there's an error
                return;
            }

            if (stdout) {
                console.log(`stdout: ${stdout}`);
            }

            if (stderr) {
                console.error(`stderr: ${stderr}`);
            }

            resolve(stdout); // Resolve the promise when execution completes
        });
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

ipcMain.handle('read-tsv-file', async (event, filePath) => {
    try {
        // Explicitly use fs.promises.readFile
        const data = await fs.promises.readFile(filePath, 'utf8');
        return data;
    } catch (err) {
        console.error("Error reading TSV file:", err);
        throw err;
    }
});

ipcMain.handle('save-tsv-file', async (event, { defaultFileName, content }) => {
    try {
        // Open a save dialog to get the file path
        const { filePath } = await dialog.showSaveDialog({
            title: 'Save TSV File',
            defaultPath: path.join(__dirname, defaultFileName),
            filters: [
                { name: 'TSV Files', extensions: ['tsv'] },
                { name: 'All Files', extensions: ['*'] },
            ],
        });

        // If the user cancels, filePath will be undefined
        if (!filePath) {
            return { success: false, message: 'Save operation canceled.' };
        }

        // Write the content to the selected file
        fs.writeFileSync(filePath, content, 'utf-8');
        console.log(`TSV file saved successfully at ${filePath}`);
        return { success: true, filePath };
    } catch (error) {
        console.error('Error saving TSV file:', error);
        return { success: false, message: error.message };
    }
});

ipcMain.on('save-file-to-functions', (event, folderPath, { name, content }) => {
    // Path to the 'functions' folder
    const savePath = path.resolve(folderPath);
  
    // Ensure the 'functions' directory exists
    if (!fs.existsSync(savePath)) {
      fs.mkdirSync(savePath);
    }
  
    // Save the file in the 'functions' folder
    const filePath = path.join(savePath, name);
  
    fs.writeFile(filePath, content, (err) => {
      if (err) {
        console.error('Error saving file:', err);
        event.reply('save-file-response', { success: false, error: err.message });
      } else {
        console.log('File saved successfully at:', filePath);
        event.reply('save-file-response', { success: true, path: filePath });
      }
    });
});

ipcMain.handle('check-and-load-file', async (event, fileName) => {
    const filePath = path.resolve(fileName);

    // Check if the file exists
    if (!fs.existsSync(filePath)) {
        console.log(`File not found: ${filePath}`);
        return { exists: false };
    }

    // Read the file contents
    try {
        const fileContent = fs.readFileSync(filePath, 'utf-8');
        console.log(`File loaded successfully: ${filePath}`);
        return { exists: true, content: fileContent };
    } catch (error) {
        console.error(`Error reading file: ${error.message}`);
        throw error;
    }
});