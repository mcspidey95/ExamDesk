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
	}

  function onFileInputChange2(e) {
	  if (e.target.files && e.target.files[0]) {
		StudentDataFile2 = e.target.files[0];
	  }
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

  // Parse the loaded TSV data
  function parseTSVData() {
    parsedData = tsvData.trim().split("\n").map((row) => row.split("\t"));
    headers = parsedData[0] || [];
    content = parsedData.slice(1);
	  columnCount = headers.length;
    rowCount = content.length;
  }

  async function generate() {
    if (StudentDataFile1) {
      try {
        // Call the main process 'generate' function via IPC
        const result = await window.api.saveUploadedFile(StudentDataFile1, "testFile", "functions/metadata");
        console.log(result); // Success message
      } catch (error) {
        console.error('Error:', error);
      }
    } else {
      console.error('No file selected');
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

  window.api.readTxtFile('functions/metadata/parameters.txt').then((data) => {
    console.log(data);
  }).catch((err) => {
    console.error(err);
  });

  loadTSVFile("functions/documents/Exam_Timetable.tsv");
</script>

{#if currentView === 'timetable'}
<body>
  <!-- Navbar -->
  <div class="navbar">
    <label for="check">
      <input type="checkbox" id="check"/> 
      <span></span>
      <span></span>
      <span></span>
    </label>

    <div class="home-button">
      <span class="home-button" on:click={() => currentView = 'home'}><img src="images/home.png" alt="home">Home</span>
    </div>
    
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
                  Drag and drop a file here, or click to select a file
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
                  Drag and drop a file here, or click to select a file
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
      <div class="a4-container">
        <!-- Content inside the A4 container -->
        {#if !tsvData}
        <p>Output will be displayed here!</p>
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

label {
  position: absolute;  /* Position label to the left */
  left: 0;
  display: flex;
  flex-direction: column;
  width: 70px;
  cursor: pointer;
  transform: scale(0.3);  /* Keep this if you need to shrink the label */
  transition: transform 0.2s ease-in-out;  /* Smooth transition for scaling */
}

label:hover {
  transform: scale(0.36);
}

label span{
  background:#2c3e50;
  border-radius:10px;
  height:7px;
  margin: 7px 0;
  transition: .4s  cubic-bezier(0.68, -0.6, 0.32, 1.6);

}


span:nth-of-type(1){
  width:50%;
  
}

span:nth-of-type(2){
  width:100%;
}


span:nth-of-type(3){
  width:75%;
 
}


input[type="checkbox"]{
  display:none;
}


input[type="checkbox"]:checked ~ span:nth-of-type(1){
  transform-origin:bottom;
  transform:rotatez(45deg) translate(8px,0px)
}


input[type="checkbox"]:checked ~ span:nth-of-type(2){
  
  transform-origin:top;
  transform:rotatez(-45deg)
}


input[type="checkbox"]:checked ~ span:nth-of-type(3){
  
  transform-origin:bottom;
  width:50%;
  transform: translate(30px,-11px) rotatez(45deg);

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
  