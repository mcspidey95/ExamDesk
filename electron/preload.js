const { ipcRenderer, contextBridge } = require('electron');

const WINDOW_API = {
    GetVersion: () => ipcRenderer.invoke('get-version'),

    runJavaFile: (filePath) => ipcRenderer.invoke('run-java-file', filePath),

    readTxtFile: (filePath) => ipcRenderer.invoke('read-txt-file', filePath),

    writeToTxtFile: (filePath, content) => ipcRenderer.invoke('write-txt-file', filePath, content),
}

// Expose API to the renderer process (Svelte)
contextBridge.exposeInMainWorld('api', WINDOW_API);