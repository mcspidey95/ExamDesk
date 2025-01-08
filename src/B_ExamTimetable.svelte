<script>
  import { onMount } from 'svelte';
  import LandingPage from './App.svelte';

  let currentView = 'timetable';
  
  let fileInput1;
  let fileInput2;
	let isDragActive = false;
	let file = null;
  
	function onDrop(e) {
	  e.preventDefault();
	  e.stopPropagation();
	  isDragActive = false;
	  if (e.target.closest('.dropzone') && e.dataTransfer.files && e.dataTransfer.files[0]) {
		file = e.dataTransfer.files[0];
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
  
	function onFileInputChange(e) {
	  if (e.target.files && e.target.files[0]) {
		file = e.target.files[0];
	  }
	}
  
	function removeFile() {
	  file = null;
	  if (fileInput) {
		fileInput.value = '';
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
              on:drop={onDrop}
            >
              <input
                type="file"
                bind:this={fileInput1}
                on:change={onFileInputChange}
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
            {#if file}
              <div class="file-info">
                <div class="file-details">
                  <span class="file-name">{file.name}</span>
                </div>
                <button on:click={removeFile} class="remove-button">
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
              on:drop={onDrop}
            >
              <input
                type="file"
                bind:this={fileInput2}
                on:change={onFileInputChange}
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
          </div>
            {#if file}
              <div class="file-info">
                <div class="file-details">
                  <span class="file-name">{file.name}</span>
                </div>
                <button on:click={removeFile} class="remove-button">
                  Remove
                </button>
              </div>
            {/if}
        </div>

        <div>
          <div class="generate-container">
            <a href="#_" class="button">
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
        <p>Output will be displayed here!</p>
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
  