<script>
  import { onMount } from 'svelte';
  import LandingPage from './App.svelte';

  let currentView = 'timetable';
  
  let fileInput1;
  let fileInput2;
	let isDragActive = false;
	let StudentDataFile1 = null;
  let StudentDataFile2 = null;
  let tsvData = ""; // Placeholder for the loaded TSV data
  let parsedData = [];
  let headers = [];
  let content = [];
  let rowCount = 0;
  let columnCount = 0;
  let isFileGenerating = false;
  let isSidebarOpen = false;

  loadFileIfExists1('functions/sources/StudentRegistration.txt')
  loadFileIfExists2('functions/sources/FinalCourseData.txt')
  
	function onDrop1(e) {
	  e.preventDefault();
	  e.stopPropagation();
	  isDragActive = false;
	  if (e.target.closest('.dropzone') && e.dataTransfer.files && e.dataTransfer.files[0]) {
		  StudentDataFile1 = e.dataTransfer.files[0];

      const fileExtension = StudentDataFile1.name.split('.').pop().toLowerCase();

    // Restrict to .txt or .tsv files
      if (StudentDataFile1 && (fileExtension !== 'txt')) {
        alert('Please drop a .txt file');
        StudentDataFile1 = null;
      }

      // Read file content
      const reader = new FileReader();
      reader.onload = () => {
        const fileContent = reader.result;

        // Send file content and name to the Electron main process
        window.api.saveUploadedFile({
          name: "StudentRegistration.txt",
          content: fileContent,
        });
      };

      reader.readAsText(StudentDataFile1);
	  }
	}

  function onDrop2(e) {
	  e.preventDefault();
	  e.stopPropagation();
	  isDragActive = false;
	  if (e.target.closest('.dropzone') && e.dataTransfer.files && e.dataTransfer.files[0]) {
		  StudentDataFile2 = e.dataTransfer.files[0];

      const fileExtension = StudentDataFile2.name.split('.').pop().toLowerCase();

      // Restrict to .txt or .tsv files
      if (StudentDataFile2 && (fileExtension !== 'txt')) {
        alert('Please drop a .txt file');
        StudentDataFile2 = null;
      }

      // Read file content
      const reader = new FileReader();
      reader.onload = () => {
        const fileContent = reader.result;

        // Send file content and name to the Electron main process
        window.api.saveUploadedFile({
          name: "FinalCourseData.txt",
          content: fileContent,
        });
      };

      reader.readAsText(StudentDataFile2);
	  }
	}
  
	function onDragOver(e) {
	  e.preventDefault();
	  e.stopPropagation();
	  isDragActive = true;
	}
  
	function onDragLeave(e) {
	  e.preventDefault();
	  e.stopPropagation();
	  isDragActive = false;
	}
  
	function onFileInputChange1(e) {
	  if (e.target.files && e.target.files[0]) {
		  StudentDataFile1 = e.target.files[0];
	  }

    // Read file content
    const reader = new FileReader();
    reader.onload = () => {
      const fileContent = reader.result;

      // Send file content and name to the Electron main process
      window.api.saveUploadedFile({
        name: "StudentRegistration.txt",
        content: fileContent,
      });
    };

    reader.readAsText(StudentDataFile1);
	}

  function onFileInputChange2(e) {
	  if (e.target.files && e.target.files[0]) {
		  StudentDataFile2 = e.target.files[0];
	  }

    // Read file content
    const reader = new FileReader();
    reader.onload = () => {
      const fileContent = reader.result;

      // Send file content and name to the Electron main process
      window.api.saveUploadedFile({
        name: "FinalCourseData.txt",
        content: fileContent,
      });
    };

    reader.readAsText(StudentDataFile2);
	}
  
	function removeFile1() {
	  StudentDataFile1 = null;
	  if (fileInput1) {
		fileInput1.value = '';
	  }
	}

  function removeFile2() {
	  StudentDataFile2 = null;
	  if (fileInput2) {
		fileInput2.value = '';
	  }
	}

  async function loadTSVFile(filePath) {
    try {
      const data = await window.api.readTsvFile(filePath);
      tsvData = data;
      parseTSVData();
    } catch (error) {
      console.error("Error reading TSV file:", error);
    }
  }

  async function saveTSVFile(fileName) {
    if(tsvData){
      try {
        const result = await window.api.saveTsvFile(fileName, tsvData);

        if (result.success) {
            console.log(`TSV file saved successfully at ${result.filePath}`);
            alert(`File saved successfully at: ${result.filePath}`);
        } else {
            console.log('Save operation canceled or failed.');
            alert(result.message || 'File save canceled.');
        }
      } catch (error) {
        console.error('Error saving TSV file:', error);
        alert('An error occurred while saving the file. Check the console for details.');
      }
    }
  }

  // Parse the loaded TSV data
  function parseTSVData() {
    parsedData = tsvData.trim().split("\n").map((row) => row.split("\t"));
    headers = parsedData[0] || [];
    content = parsedData.slice(1);
	  columnCount = headers.length;
    rowCount = content.length;
  }

  async function loadFileIfExists1(fileName) {
    const result = await window.api.checkAndLoadFile(fileName);

    if (result.exists) {
        console.log(`File exists. Content loaded.`);
        // Save the file content into a variable
        StudentDataFile1 = {
          name: "StudentRegistration.txt",
          content: result.content,
        };
        console.log(`File saved to variable: ${StudentDataFile1}`);
    } 
  }

  async function loadFileIfExists2(fileName) {
    const result = await window.api.checkAndLoadFile(fileName);

    if (result.exists) {
        console.log(`File exists. Content loaded.`);
        // Save the file content into a variable
        StudentDataFile2 = {
          name: "FinalCourseData.txt",
          content: result.content,
        };
        console.log(`File saved to variable: ${StudentDataFile1}`);
    } 
  }

  async function generate() {
    try {
      console.log('Running Java file...');
      tsvData = '';
      isFileGenerating = true;
      
      await window.api.runJavaFile('electron/functions/ExamTimetable.class');
      
      console.log('Java file execution finished.');

      setTimeout(() => {
          loadTSVFile('electron/functions/documents/Exam_Timetable.tsv');
      }, 1000);
    } catch (error) {
      console.error('Error during Java file execution:', error);
      alert('Failed to execute Java file. Check the console for details.');
    }
  }
  
	onMount(() => {
	  window.addEventListener('dragover', onDragOver);
	  window.addEventListener('dragleave', onDragLeave);
  
	  return () => {
		window.removeEventListener('dragover', onDragOver);
		window.removeEventListener('dragleave', onDragLeave);
	  };
	});

  window.api.readTxtFile('electron/functions/metadata/parameters.txt').then((data) => {
    console.log(data);
  }).catch((err) => {
    console.error(err);
  });
</script>

{#if currentView === 'timetable'}
<body>
  <!-- Navbar -->
  <div class="navbar">
    <label for="check" class="sidebar-toggle">
      <input type="checkbox" id="check" bind:checked={isSidebarOpen} />
      <span></span>
      <span></span>
      <span></span>
    </label>    

    <div class="home-button">
      <span class="home-button" on:click={() => currentView = 'home'}><img src="images/home.png" alt="home">Home</span>
    </div>
    
  </div>

  <div class="overlay {isSidebarOpen ? 'show' : ''}" on:click={() => (isSidebarOpen = false)}></div>

  <div class="sidebar {isSidebarOpen ? 'open' : ''}">
    <div class="parameters">
      <p>EndTerm Mode:</p>
      <label class="switch">
        <input type="checkbox" id="endTermMode">
        <span class="slider"></span>
      </label>
    </div>

    <div class="parameters">
      <p>Fixed Break:</p>
      <label class="switch">
        <input type="checkbox" id="fixedBreak">
        <span class="slider"></span>
      </label>
    </div>

    <div class="parameters">
      <p>Slots Per Day:</p>
      <label class="number">
        <input type="number" id="slotsPerDay">
      </label>
    </div>

    <div class="parameters">
      <p>Exams Per Slot:</p>
      <label class="number">
        <input type="number" id="examsPerSlot">
      </label>
    </div>

    <div class="parameters">
      <p>Students Per Slot:</p>
      <label class="number">
        <input type="number" id="studentsPerSlot">
      </label>
    </div>

    <div class="parameters">
      <p>BlackList Courses:</p>
    </div>

    <textarea id="blacklist"></textarea>
  </div>

  <!-- Main Content -->
  <div class="main-content">
    <div class="left-section">
      <div class="upload-grid">
        <!-- First Upload Box -->
        <div class="upload-container">
          <div class="file-upload">
            <div
              class="dropzone"
              class:active={isDragActive}
              on:click={() => fileInput1.click()}
              on:dragenter={onDragOver}
              on:dragleave={onDragLeave}
              on:dragover={onDragOver}
              on:drop={onDrop1}
            >
              <input
                type="file"
                bind:this={fileInput1}
                on:change={onFileInputChange1}
                accept=".txt"
                style="display: none;"
              />
              <div class="icon">
                <img src="images/upload.png" alt="Upload Icon" />
              </div>
              <p>
                {#if isDragActive}
                  Drop the file here
                {:else}
                  Drop or Upload [Student Registration Data]
                {/if}
              </p>
            </div>
            {#if StudentDataFile1}
              <div class="file-info">
                <div class="file-details">
                  <span class="file-name">{StudentDataFile1.name}</span>
                </div>
                <button on:click={removeFile1} class="remove-button">
                  Remove
                </button>
              </div>
            {/if}
          </div>
        </div>
      
        <!-- Second Upload Box -->
        <div class="upload-container">
          <div class="file-upload">
            <div
              class="dropzone"
              class:active={isDragActive}
              on:click={() => fileInput2.click()}
              on:dragenter={onDragOver}
              on:dragleave={onDragLeave}
              on:dragover={onDragOver}
              on:drop={onDrop2}
            >
              <input
                type="file"
                bind:this={fileInput2}
                on:change={onFileInputChange2}
                accept=".txt"
                style="display: none;"
              />
              <div class="icon">
                <img src="images/upload.png" alt="Upload Icon" />
              </div>
              <p>
                {#if isDragActive}
                  Drop the file here
                {:else}
                  Drop or Upload [Course Data]
                {/if}
              </p>
            </div>
            {#if StudentDataFile2}
              <div class="file-info">
                <div class="file-details">
                  <span class="file-name">{StudentDataFile2.name}</span>
                </div>
                <button on:click={removeFile2} class="remove-button">
                  Remove
                </button>
              </div>
            {/if}
          </div>
        </div>

        <div>
          <div class="generate-container">
            <a href="#_" class="button" on:click={generate}>
              <span class="hover-bg"></span>
              <span class="text">Generate</span>
            </a>
          </div>
        </div>
      </div>  
    </div>




    <div class="right-section">
      <div class="a4-container" on:click={saveTSVFile("ExamTimetable.tsv")}>
        <!-- Content inside the A4 container -->
        {#if !tsvData}
          {#if isFileGenerating}
            <div class="loader"></div>
          {:else}
            <p>Output will be displayed here!</p>
          {/if}
        {:else}
        <div class="tsv-container scroll_enabled">
          <table>
            <thead>
            <tr>
              {#each headers as header}
              <th>{header}</th>
              {/each}
            </tr>
            </thead>
            <tbody>
            {#each content as row}
              <tr>
              {#each row as cell}
                <td>{cell}</td>
              {/each}
              </tr>
            {/each}
            </tbody>
          </table>
        </div>

        <div class="info-box">
          <p>Rows: {rowCount}, Cols: {columnCount}</p>
        </div>
        {/if}
      </div>
      
    </div>
  </div>
</body>
{:else if currentView === 'home'}
  <LandingPage />
{/if}


<style>
body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  background-size: cover;
  font-family: "DMSerif";
}

/* Navbar Styling */
.navbar {
  width: 100%;
  height: 60px; /* Adjust height as needed */
  color: #2c3e50;
  display: flex;
  justify-content: center;   /* Centers all content horizontally */
  align-items: center;  
  font-size: 1.5rem;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  position: relative;  /* Ensures positioning within navbar */
  z-index: 3000;
}

.home-button {
  position: absolute;  /* Allows centering of home button */
  left: 50%;  /* Centers the button horizontally */
  transform: translateX(-50%);  /* Adjusts for the exact center */
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
}

.home-button:hover{
  transform: scale(1.04);
}

.home-button img {
  width: 30px;
  height: 30px;
  margin-right: 3px;
}

/* Add a class to scope the styles */
.sidebar-toggle {
  position: absolute;  /* Position label to the left */
  left: 0;
  display: flex;
  flex-direction: column;
  width: 70px;
  cursor: pointer;
  transform: scale(0.3);  /* Keep this if you need to shrink the label */
  transition: transform 0.2s ease-in-out;  /* Smooth transition for scaling */
}

.sidebar-toggle:hover {
  transform: scale(0.36);
}

.sidebar-toggle span {
  background: #2c3e50;
  border-radius: 10px;
  height: 7px;
  margin: 7px 0;
  transition: 0.3s cubic-bezier(0.68, -0.6, 0.32, 1.6);
}

.sidebar-toggle span:nth-of-type(1) {
  width: 50%;
}

.sidebar-toggle span:nth-of-type(2) {
  width: 100%;
}

.sidebar-toggle span:nth-of-type(3) {
  width: 75%;
}

.sidebar-toggle input[type="checkbox"] {
  display: none;
}

.sidebar-toggle input[type="checkbox"]:checked ~ span:nth-of-type(1) {
  transform-origin: bottom;
  transform: rotateZ(45deg) translate(8px, 0px);
}

.sidebar-toggle input[type="checkbox"]:checked ~ span:nth-of-type(2) {
  transform-origin: top;
  transform: rotateZ(-45deg);
}

.sidebar-toggle input[type="checkbox"]:checked ~ span:nth-of-type(3) {
  transform-origin: bottom;
  width: 50%;
  transform: translate(30px, -11px) rotateZ(45deg);
}

/* Main Content Layout */
.main-content {
  display: grid;
  grid-template-columns: 3fr 2fr; /* Left section: 2/3, Right section: 1/3 */
  height: calc(100% - 60px); /* Take the remaining height after navbar */
  width: 100%;
}

/* Left Section */
.left-section {
  padding: 20px;
  overflow: hidden; /* Add scroll if content overflows */
}

.upload-grid {
  height: 100%; /* Fill the container's height */
  display: grid;
  gap: 1rem; /* Space between upload boxes */
  grid-template-rows: 2fr 2fr 1fr; /* Top and middle 2/5th, bottom 1/5th */
  margin-right: 4rem;
  margin-left: 4rem;
}

.upload-container {
  /* Add any desired styling for individual upload boxes */
  padding: 1rem;
  border-radius: 8px;
	background-color: #f8f9fa;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}


/* Right Section */
.right-section {
  padding: 20px;
  overflow-y: auto; /* Add scroll if content overflows */
}

</style>
  