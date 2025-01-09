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

ipcMain.handle('save-uploaded-file', (event, file, newFileName, destinationPath) => {
    return new Promise((resolve, reject) => {
      try {
        if (!file || !newFileName || !destinationPath) {
          throw new Error('Invalid parameters');
        }
  
        // Ensure destination directory exists
        if (!fs.existsSync(destinationPath)) {
          fs.mkdirSync(destinationPath, { recursive: true });
        }
  
        // Create the new file path by appending the new file name
        const extname = path.extname(file.name); // Get the file extension
        const newFilePath = path.join(destinationPath, `${newFileName}${extname}`);
  
        // Create a writable stream to the new file
        const fileStream = fs.createWriteStream(newFilePath);
  
        // Use file stream to write file content
        const readerStream = file.stream();  // This is available from the File API
  
        readerStream.pipe(fileStream);
  
        fileStream.on('finish', () => {
          resolve('File successfully saved!');
        });
  
        fileStream.on('error', (err) => {
          reject(`Failed to save file: ${err.message}`);
        });
      } catch (error) {
        reject(`Error: ${error.message}`);
      }
    });
  });