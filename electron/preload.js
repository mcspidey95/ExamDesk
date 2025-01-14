const { ipcRenderer, contextBridge } = require('electron');

const WINDOW_API = {
    GetVersion: () => ipcRenderer.invoke('get-version'),

    runJavaFile: (filePath) => ipcRenderer.invoke('run-java-file', filePath),

    readTxtFile: (filePath) => ipcRenderer.invoke('read-txt-file', filePath),

    writeToTxtFile: (filePath, content) => ipcRenderer.invoke('write-txt-file', filePath, content),

    readTsvFile: (filePath) => ipcRenderer.invoke('read-tsv-file', filePath),

    saveTsvFile: (fileName, content) => ipcRenderer.invoke('save-tsv-file', { defaultFileName: fileName, content }),

    saveUploadedFile: (filePath, fileData) => ipcRenderer.send('save-file-to-functions', filePath, fileData),

    checkAndLoadFile: (fileName) => ipcRenderer.invoke('check-and-load-file', fileName),
}

// Expose API to the renderer process (Svelte)
contextBridge.exposeInMainWorld('api', WINDOW_API);