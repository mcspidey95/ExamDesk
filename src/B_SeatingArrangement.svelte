<script>
	import { onMount } from 'svelte';
	import LandingPage from './App.svelte';
  
	let currentView = 'seating';
	
	let fileInput1;
	let fileInput2;
	let fileInput3;
	let fileInput4;
	let isDragActive = false;
	let StudentDataFile1 = null;
	let StudentDataFile2 = null;
	let RoomDataFile = null;
	let FacultyDataFile = null;
	let tsvData1 = "";
	let tsvData2 = "";
	let parsedData = [];
	let headers = [];
	let content = [];
	let rowCount = 0;
	let columnCount = 0;
	let isFileGenerating = false;
	let isSidebarOpen = false;
	let slotsList = "";
	let daysList = "";
	let saveFlag = false;
	let toastText = "";
	let isToastVisible = false;
	let isTimetableGenerated = false;
	let selectedTSVFile = 1;
  
	loadFileIfExists1('electron/functions/sources/StudentRegistration.txt')
	loadFileIfExists2('electron/functions/sources/FinalCourseData.txt')
	loadFileIfExists3('electron/functions/sources/Rooms.txt')
	loadFileIfExists4('electron/functions/sources/Invigilators.txt')
	
	  function onDrop1(e) {
		e.preventDefault();
		e.stopPropagation();
		isDragActive = false;
		if (e.target.closest('.dropzone') && e.dataTransfer.files && e.dataTransfer.files[0]) {
			StudentDataFile1 = e.dataTransfer.files[0];
  
		const fileExtension = StudentDataFile1.name.split('.').pop().toLowerCase();
  
	  // Restrict to .txt or .tsv files
		if (StudentDataFile1 && (fileExtension !== 'txt')) {
		  showToast('Please drop a .txt file');
		  StudentDataFile1 = null;
		}
  
		// Read file content
		const reader = new FileReader();
		reader.onload = () => {
		  const fileContent = reader.result;
  
		  // Send file content and name to the Electron main process
		  window.api.saveUploadedFile('electron/functions/sources', {
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
		  showToast('Please drop a .txt file');
		  StudentDataFile2 = null;
		}
  
		// Read file content
		const reader = new FileReader();
		reader.onload = () => {
		  const fileContent = reader.result;
  
		  // Send file content and name to the Electron main process
		  window.api.saveUploadedFile('electron/functions/sources', {
			name: "FinalCourseData.txt",
			content: fileContent,
		  });
		};
  
		reader.readAsText(StudentDataFile2);
		}
	}

	function onDrop3(e) {
		e.preventDefault();
		e.stopPropagation();
		isDragActive = false;
		if (e.target.closest('.dropzone') && e.dataTransfer.files && e.dataTransfer.files[0]) {
			RoomDataFile = e.dataTransfer.files[0];
  
		const fileExtension = RoomDataFile.name.split('.').pop().toLowerCase();
  
		// Restrict to .txt or .tsv files
		if (RoomDataFile && (fileExtension !== 'txt')) {
		  showToast('Please drop a .txt file');
		  RoomDataFile = null;
		}
  
		// Read file content
		const reader = new FileReader();
		reader.onload = () => {
		  const fileContent = reader.result;
  
		  // Send file content and name to the Electron main process
		  window.api.saveUploadedFile('electron/functions/sources', {
			name: "Rooms.txt",
			content: fileContent,
		  });
		};
  
		reader.readAsText(RoomDataFile);
		}
	  }

	  function onDrop4(e) {
		e.preventDefault();
		e.stopPropagation();
		isDragActive = false;
		if (e.target.closest('.dropzone') && e.dataTransfer.files && e.dataTransfer.files[0]) {
			FacultyDataFile = e.dataTransfer.files[0];
  
		const fileExtension = FacultyDataFile.name.split('.').pop().toLowerCase();
  
		// Restrict to .txt or .tsv files
		if (FacultyDataFile && (fileExtension !== 'txt')) {
		  showToast('Please drop a .txt file');
		  FacultyDataFile = null;
		}
  
		// Read file content
		const reader = new FileReader();
		reader.onload = () => {
		  const fileContent = reader.result;
  
		  // Send file content and name to the Electron main process
		  window.api.saveUploadedFile('electron/functions/sources', {
			name: "Invigiltors.txt",
			content: fileContent,
		  });
		};
  
		reader.readAsText(FacultyDataFile);
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
		window.api.saveUploadedFile('electron/functions/sources', {
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
		window.api.saveUploadedFile('electron/functions/sources', {
		  name: "FinalCourseData.txt",
		  content: fileContent,
		});
	  };
  
	  reader.readAsText(StudentDataFile2);
	  }

	  function onFileInputChange3(e) {
		if (e.target.files && e.target.files[0]) {
			RoomDataFile = e.target.files[0];
		}
  
	  // Read file content
	  const reader = new FileReader();
	  reader.onload = () => {
		const fileContent = reader.result;
  
		// Send file content and name to the Electron main process
		window.api.saveUploadedFile('electron/functions/sources', {
		  name: "Rooms.txt",
		  content: fileContent,
		});
	  };
  
	  reader.readAsText(RoomDataFile);
	  }

	  function onFileInputChange4(e) {
		if (e.target.files && e.target.files[0]) {
			FacultyDataFile = e.target.files[0];
		}
  
	  // Read file content
	  const reader = new FileReader();
	  reader.onload = () => {
		const fileContent = reader.result;
  
		// Send file content and name to the Electron main process
		window.api.saveUploadedFile('electron/functions/sources', {
		  name: "Invigilators.txt",
		  content: fileContent,
		});
	  };
  
	  reader.readAsText(FacultyDataFile);
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

	  function removeFile3() {
		RoomDataFile = null;
		if (fileInput3) {
		  fileInput3.value = '';
		}
	  }

	  function removeFile4() {
		FacultyDataFile = null;
		if (fileInput4) {
		  fileInput4.value = '';
		}
	  }
  
	async function loadTSVFile(filePath1, filePath2) {
	  try {
		tsvData1 = await window.api.readTsvFile(filePath1);
		tsvData2 = await window.api.readTsvFile(filePath2);

		parseTSVData(tsvData1);
	  } catch (error) {
		console.error("Error reading TSV file:", error);
	  }
	}

  
	async function saveTSVFile(fileName, tsvData) {
	  if(tsvData){
		try {
		  const result = await window.api.saveTsvFile(fileName, tsvData);
  
		  if (result.success) {
			  console.log(`TSV file saved successfully at ${result.filePath}`);
			  showToast(fileName + ' saved!')
		  } else {
			  console.log('Save operation canceled or failed.');
			  showToast(result.message || 'File save canceled.');
		  }
		} catch (error) {
		  console.error('Error saving TSV file:', error);
		  showToast('An error occurred while saving the file. Check the console for details.');
		}
	  }
	}
  
	// Parse the loaded TSV data
	function parseTSVData(tsvData) {
	  parsedData = tsvData.trim().split("\n").map((row) => row.split("\t"));
	  headers = parsedData[0] || [];
	  content = parsedData.slice(1,1001);
		columnCount = headers.length;
	  rowCount = content.length;
	}
  
	async function loadFileIfExists1(fileName) {
	  const result = await window.api.checkAndLoadFile(fileName);
  
	  if (result.exists) {
		  console.log(`File 1 exists. Content loaded.`);
		  showToast('Previous files loaded!')
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
		  console.log(`File 2 exists. Content loaded.`);
		  // Save the file content into a variable
		  StudentDataFile2 = {
			name: "FinalCourseData.txt",
			content: result.content,
		  };
		  console.log(`File saved to variable: ${StudentDataFile2}`);
	  } 
	}

	async function loadFileIfExists3(fileName) {
	  const result = await window.api.checkAndLoadFile(fileName);
  
	  if (result.exists) {
		  console.log(`File 3 exists. Content loaded.`);
		  // Save the file content into a variable
		  RoomDataFile = {
			name: "Rooms.txt",
			content: result.content,
		  };
		  console.log(`File saved to variable: ${RoomDataFile}`);
	  } 
	}

	async function loadFileIfExists4(fileName) {
	  const result = await window.api.checkAndLoadFile(fileName);
  
	  if (result.exists) {
		  console.log(`File 4 exists. Content loaded.`);
		  // Save the file content into a variable
		  FacultyDataFile = {
			name: "Invigilators.txt",
			content: result.content,
		  };
		  console.log(`File saved to variable: ${FacultyDataFile}`);
	  } 
	}
  
	async function generate() {
	  if(StudentDataFile1 && StudentDataFile2 && RoomDataFile && FacultyDataFile && isTimetableGenerated){
		try {
		  console.log('Running Java file...');
		  showToast('Generating Seating & Invigilator Assignment...')
		  tsvData1 = '';
		  tsvData2 = '';
		  isFileGenerating = true;
		
		  await window.api.runJavaFile('electron/functions/SeatingArrangement.class');
		
		  console.log('Java file execution finished.');
  
		  setTimeout(async () => {
			await loadTSVFile('electron/functions/documents/Seating_Arrangement.tsv','electron/functions/documents/Invigilator_Assignment.tsv');
		  }, 500);
  
		  showToast('Seating & Faculty Assignment Generated!')
		} catch (error) {
		  console.error('Error during Java file execution:', error);
		  showToast('Failed to execute Java file. Check the console for details.');
		}
	  }
	  else if(StudentDataFile1 && StudentDataFile2 && RoomDataFile && FacultyDataFile && !isTimetableGenerated) showToast('Please Generate ExamTimetable First!')
	  else showToast('Please Upload The Required Files!')
	}
  
	// Function to show the toast
	function showToast(message) {
	  toastText = message;
	  isToastVisible = true;
  
	  setTimeout(() => {
		isToastVisible = false;
	  }, 2000);
	}

	function toggleView(isView1) {
		const container = document.querySelector('.a4-container');
  		if (isView1) {
			selectedTSVFile = 1;
			parseTSVData(tsvData1);
  		} else {
			selectedTSVFile = 2;
			parseTSVData(tsvData2);
  		}
	}
  
	$: {
	  const hoverBg = document.querySelector('.hover-bg');
	  const button = document.querySelector('.button');
  
	  if (hoverBg && button) {
		if (StudentDataFile1 && StudentDataFile2 && RoomDataFile && FacultyDataFile && isTimetableGenerated) {
		  hoverBg.classList.add('translated');
		  button.classList.remove('disabled');
		} else {
		  hoverBg.classList.remove('translated');
		  button.classList.add('disabled');
		}
	  }
	}
  
	$: if(isSidebarOpen) {
	  saveFlag = true;
  
	  window.api.readTxtFile('electron/functions/metadata/slots.txt').then((data) => {
		slotsList = data;
	  }).catch((err) => {
		console.error(err);
	  });

	  window.api.readTxtFile('electron/functions/metadata/days.txt').then((data) => {
		daysList = data;
	  }).catch((err) => {
		console.error(err);
	  });
	}
  
	$: if(!isSidebarOpen) {
		if(saveFlag){
  
		  window.api.writeToTxtFile('electron/functions/metadata/slots.txt', slotsList)
		  .then((message) => {
			console.log(message);
		  })
		  .catch((err) => {
			console.error(err);
		  });

		  window.api.writeToTxtFile('electron/functions/metadata/days.txt', daysList)
		  .then((message) => {
			console.log(message);
		  })
		  .catch((err) => {
			console.error(err);
		  });
		}
	}
	
	  onMount(async () => {
		window.addEventListener('dragover', onDragOver);
		window.addEventListener('dragleave', onDragLeave);

		let tempTSVFile = await window.api.readTsvFile('electron/functions/documents/Exam_Timetable.tsv');
		if(tempTSVFile!=null) isTimetableGenerated = true;
	
		return () => {
			window.removeEventListener('dragover', onDragOver);
			window.removeEventListener('dragleave', onDragLeave);
		};
	  });
  </script>
  
  {#if currentView === 'seating'}
  <body>
	<div class="toast-container" class:toast-show={isToastVisible}>
	  {toastText}
	</div>

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
  
  
	  <div class="indicator">
		{#if tsvData1 && tsvData2 && !isSidebarOpen}
			<span id="warning">Seating & Invigilator Files Generated!</span>
		{:else if isTimetableGenerated && !isSidebarOpen}
			<span id="warning">Exam Timetable Fetched!</span>
		{:else if !isTimetableGenerated && !isSidebarOpen}
			<span id="alert">Exam Timetable NOT Generated!</span>
		{/if}
	  </div>
	  
	</div>
  
	<div class="overlay {isSidebarOpen ? 'show' : ''}" on:click={() => (isSidebarOpen = false)}></div>
  
	<div class="sidebar {isSidebarOpen ? 'open' : ''}">
	  <p></p>
	  <div class="parameters">
		<p>Exam Slots:</p>
	  </div>
  
	  <textarea class="scroll_enabled" bind:value={slotsList}></textarea>

	  <p></p>
	  <div class="parameters">
		<p>Exam Dates:</p>
	  </div>
  
	  <textarea class="scroll_enabled" bind:value={daysList}></textarea>
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
					Drop or Upload [Student Data]
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

		  <div class="upload-container">
			<div class="file-upload">
			  <div
				class="dropzone"
				class:active={isDragActive}
				on:click={() => fileInput3.click()}
				on:dragenter={onDragOver}
				on:dragleave={onDragLeave}
				on:dragover={onDragOver}
				on:drop={onDrop3}
			  >
				<input
				  type="file"
				  bind:this={fileInput3}
				  on:change={onFileInputChange3}
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
					Drop or Upload [Room Data]
				  {/if}
				</p>
			  </div>
			  {#if RoomDataFile}
				<div class="file-info">
				  <div class="file-details">
					<span class="file-name">{RoomDataFile.name}</span>
				  </div>
				  <button on:click={removeFile3} class="remove-button">
					Remove
				  </button>
				</div>
			  {/if}
			</div>
		  </div>

		  <div class="upload-container">
			<div class="file-upload">
			  <div
				class="dropzone"
				class:active={isDragActive}
				on:click={() => fileInput4.click()}
				on:dragenter={onDragOver}
				on:dragleave={onDragLeave}
				on:dragover={onDragOver}
				on:drop={onDrop4}
			  >
				<input
				  type="file"
				  bind:this={fileInput4}
				  on:change={onFileInputChange4}
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
					Drop or Upload [Invigilator Data]
				  {/if}
				</p>
			  </div>
			  {#if FacultyDataFile}
				<div class="file-info">
				  <div class="file-details">
					<span class="file-name">{FacultyDataFile.name}</span>
				  </div>
				  <button on:click={removeFile4} class="remove-button">
					Remove
				  </button>
				</div>
			  {/if}
			</div>
		  </div>

		</div>  
		<div class="generate-container">
			<a href="#_" class="button" on:click={generate}>
			  <span class="hover-bg"></span>
			  <span class="text">Generate</span>
			</a>
		</div>
	  </div>
  
  
  
  
	  <div class="right-section">
		{#if selectedTSVFile == 1}
		<div class="a4-container view1" on:click={() => saveTSVFile("SeatingArrangement.tsv",tsvData1)}>
		  <!-- Content inside the A4 container -->
		  {#if !tsvData1}
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

		  <div class="tsv-changer" on:click={() => toggleView(false)}>
			<img src="images/arrow.png" alt="next">
		  </div>
  
		  <div class="info-box">
			<p>Rows: {rowCount}, Cols: {columnCount}</p>
		  </div>
		  {/if}
		</div>

		{:else if selectedTSVFile == 2}
		<div class="a4-container view2" on:click={() => saveTSVFile("InvigilatorAssignment.tsv", tsvData2)}>
			<!-- Content inside the A4 container -->
			{#if !tsvData2}
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

			<div class="tsv-changer" on:click={() => toggleView(true)}>
				<img src="images/arrow.png" alt="next">
			  </div>
	
			<div class="info-box">
			  <p>Rows: {rowCount}, Cols: {columnCount}</p>
			</div>
			{/if}
		  </div>
		  {/if}
		
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
	user-select: none;
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
	transform: translateX(-50%) scale(1.04);
  }
  
  .home-button img {
	width: 30px;
	height: 30px;
	margin-right: 3px;
  }
  
  .indicator {
	margin-left: auto; /* Pushes the indicator to the right */
	padding-right: 2rem; /* Optional: Add some spacing from the right edge */
	padding-bottom: 0.5rem;
  }
  
  #warning{
	font-size: medium;
	color: olivedrab;
  }
  
  #alert{
	font-size: medium;
	color: rgb(230, 30, 30);
  }

  .icon img {
	width: 42px;
	height: 42px;
	margin-bottom: 1rem;
	color: #2c3e50;
}
  
.dropzone p {
	margin: 0;
	font-size: 0.8rem;
	color: #666;
}

.sidebar{
	padding-top: 30px;
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
	background: #fff;
  }
  
  .sidebar-toggle input[type="checkbox"]:checked ~ span:nth-of-type(2) {
	transform-origin: top;
	transform: rotateZ(-45deg);
	background: #fff;
  }
  
  .sidebar-toggle input[type="checkbox"]:checked ~ span:nth-of-type(3) {
	transform-origin: bottom;
	width: 50%;
	transform: translate(30px, -11px) rotateZ(45deg);
	background: #fff;
  }
  
  /* Main Content Layout */
  .main-content {
	display: grid;
	grid-template-columns: 3fr 2fr; /* Left section: 2/3, Right section: 1/3 */
	height: calc(100% - 60px); /* Take the remaining height after navbar */
	width: 100%;
	user-select: none;
  }
  
  /* Left Section */
  .left-section {
	padding: 20px;
	overflow: hidden; /* Add scroll if content overflows */
  }
  
  .upload-grid {
	height: 90%; /* Fill the container's height */
	display: grid;
	gap: 1rem; /* Space between upload boxes */
	grid-template-rows: 45% 45%; /* Top and middle 2/5th, bottom 1/5th */
	grid-template-columns: 50% 50%; /* Split first two rows into two columns */
	margin-right: 1rem;
	margin-left: 1rem;
  }
  
  .upload-container {
	/* Add any desired styling for individual upload boxes */
	padding: 1rem;
	border-radius: 8px;
	  background-color: #f8f9fa;
	  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .generate-container{
	height: 10%;
	transform: translateY(-0.75em);
  }
  
  
  /* Right Section */
  .right-section {
	padding: 20px;
	overflow-y: auto; /* Add scroll if content overflows */
  }
  
  </style>