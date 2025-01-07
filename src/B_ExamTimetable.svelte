<script>
    import { onMount } from 'svelte';
    import LandingPage from './App.svelte';

    let currentView = 'timetable';
  
    let sidebarOpen = false;
    let popupVisible = false;
    let popupMessage = '';
    let slotsPerDay = '';
    let examsPerSlot = '';
    let studentsPerSlot = '';
    let fileName = 'Drag & Drop or Click to Upload Timetable File';
    let fileContent = '';

    window.api.readTxtFile('functions/metadata/parameters.txt').then((data) => {
    console.log(data);
  }).catch((err) => {
    console.error(err);
  });
  
    function toggleSidebar() {
      sidebarOpen = !sidebarOpen;
    }
  
    function goBack() {
      currentView = 'home';
    }
  
    function saveInputs() {
      const data = `SLOTS_PER_DAY: ${slotsPerDay}\nEXAMS_PER_SLOT: ${examsPerSlot}\nSTUDENTS_PER_SLOT: ${studentsPerSlot}`;
      const blob = new Blob([data], { type: 'text/plain' });
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = 'inputs.txt';
      link.click();
      URL.revokeObjectURL(link.href);
    }
  
    function updatePopup(sliderName, isChecked) {
      popupMessage = `${sliderName} is ${isChecked ? 'ON' : 'OFF'}`;
      popupVisible = true;
      setTimeout(() => (popupVisible = false), 3000);
    }
  
    function handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        fileName = file.name;
        const reader = new FileReader();
        reader.onload = (e) => (fileContent = e.target.result);
        reader.readAsText(file);
      }
    }
  </script>
  
  <style>
            body {
            font-family: 'Iosevka', sans-serif;
            background-color: white;
            color: black;
            margin: 0;
            overflow-x: hidden;
        }

        /* Sidebar */
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
            height: 100%;
            background-color: #f8f8f8;
            padding: 20px;
            z-index: 1000;
            transform: translateX(-250px);
            transition: transform 0.3s ease, visibility 0.3s ease, opacity 0.3s ease;
            visibility: hidden;
            opacity: 0;
            box-shadow: 2px 0px 8px rgba(0, 0, 0, 0.1);
        }

        .sidebar.open {
            transform: translateX(0);
            visibility: visible;
            opacity: 1;
        }

        .back-btn {
            display: block;
            width: 100%;
            padding: 10px;
            font-size: 1.2rem;
            text-align: center;
            background-color: #f8f8f8;
            border: none;
            color: black;
            cursor: pointer;
            margin-bottom: 20px;
        }

        .back-btn:hover {
            background-color: #e0e0e0;
        }

        .text-viewer {
            margin-top: 20px;
        }

        .text-viewer textarea {
            width: 100%;
            height: 150px;
            resize: none;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
            padding: 10px;
            background-color: #f8f8f8;
        }

        .save-btn {
            display: block;
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            font-size: 1rem;
            text-align: center;
            background-color: #4caf50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .save-btn:hover {
            background-color: #45a049;
        }

        .input-container {
            margin-top: 20px;
        }

        .input-container label {
            display: block;
            font-size: 1rem;
            margin-bottom: 5px;
        }

        .input-container input {
            width: 100%;
            padding: 5px;
            font-size: 1rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        .slider-container {
            margin-top: 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .slider-label {
            font-size: 1rem;
        }

        .slider {
            appearance: none;
            width: 50px;
            height: 25px;
            background: #ccc;
            border-radius: 15px;
            outline: none;
            cursor: pointer;
            position: relative;
            transition: background 0.3s ease;
        }

        .slider::before {
            content: '';
            position: absolute;
            left: 3px;
            top: 3px;
            width: 20px;
            height: 20px;
            background: white;
            border-radius: 50%;
            transition: transform 0.3s ease;
        }

        .slider:checked::before {
            transform: translateX(25px);
        }

        .slider:checked {
            background: #4caf50;
        }

        /* Popup Message */
        .popup {
            position: absolute;
            top: 50px;
            left: 350px;
            width: 300px;
            padding: 15px;
            background-color: #f8f8f8;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            font-size: 1rem;
            color: black;
            display: none; /* Hidden by default */
        }

        .popup.visible {
            display: block;
        }

        /* Navigation Button */
        .nav-btn {
            position: fixed;
            top: 20px;
            left: 20px;
            background-color: #f8f8f8;
            padding: 10px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            z-index: 1100;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .nav-btn span {
            display: block;
            width: 25px;
            height: 3px;
            background: black;
            margin: 4px 0;
        }

        /* Main Content */
        .main-content {
            margin-left: 0;
            display: flex;
            padding: 20px;
            height: 100vh;
            transition: margin-left 0.3s ease;
        }

        .main-content.shift {
            margin-left: 250px;
        }

        /* Left Section (Upload Area) */
        .upload-container {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .upload-box {
            width: 80%;
            height: 300px;
            border: 2px dashed black;
            border-radius: 10px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: black;
            font-size: 1.2rem;
            cursor: pointer;
            background-color: #f8f8f8;
            position: relative;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .upload-box:hover {
            background-color: #e0e0e0;
        }

        .upload-box img {
            width: 50px;
            height: 50px;
            margin-bottom: 10px;
        }

        /* Right Section (Preview Area) */
        .preview-container {
            flex: 2;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .file-preview {
            width: 80%;
            height: 300px;
            background-color: #f8f8f8;
            border: 2px solid black;
            border-radius: 10px;
            color: black;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 20px;
            overflow: hidden;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .file-preview p {
            text-align: center;
        }

        /* Generate Button */
        .generate-btn {
            padding: 10px 20px;
            font-size: 1.2rem;
            color: black;
            background-color: #f8f8f8;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .generate-btn:hover {
            background-color: #e0e0e0;
        }
  </style>
  
  {#if currentView === 'timetable'}
  <div>
    <!-- Navigation Button -->
    <button class="nav-btn" on:click={toggleSidebar}>
      <span></span>
      <span></span>
      <span></span>
    </button>
  
    <!-- Sidebar -->
    <div class="sidebar {sidebarOpen ? 'open' : ''}">
      <button class="back-btn" on:click={goBack}>&#8592; Back to Home</button>
  
      <div class="slider-container">
        <label class="slider-label">ENDTERM MODE</label>
        <input
          type="checkbox"
          class="slider"
          on:change={(e) => updatePopup('ENDTERM MODE', e.target.checked)}
        />
      </div>
      <div class="slider-container">
        <label class="slider-label">FIXED BREAK</label>
        <input
          type="checkbox"
          class="slider"
          on:change={(e) => updatePopup('FIXED BREAK', e.target.checked)}
        />
      </div>
  
      <div class="input-container">
        <label for="slotsPerDay">SLOTS_PER_DAY:</label>
        <input
          type="number"
          id="slotsPerDay"
          bind:value={slotsPerDay}
          placeholder="Enter slots per day"
        />
  
        <label for="examsPerSlot">EXAMS_PER_SLOT:</label>
        <input
          type="number"
          id="examsPerSlot"
          bind:value={examsPerSlot}
          placeholder="Enter exams per slot"
        />
  
        <label for="studentsPerSlot">STUDENTS_PER_SLOT:</label>
        <input
          type="number"
          id="studentsPerSlot"
          bind:value={studentsPerSlot}
          placeholder="Enter students per slot"
        />
  
        <button class="save-btn" on:click={saveInputs}>Save Inputs</button>
      </div>
  
      <div class="text-viewer">
        <textarea
          bind:value={fileContent}
          placeholder="Text file content will appear here..."
        ></textarea>
        <button
          class="save-btn"
          on:click={() => {
            const blob = new Blob([fileContent], { type: 'text/plain' });
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = 'updated_file1.txt';
            link.click();
            URL.revokeObjectURL(link.href);
          }}
        >
          Save Text 1
        </button>
      </div>
    </div>
  
    <!-- Popup -->
    {#if popupVisible}
      <div class="popup">
        <p>{popupMessage}</p>
      </div>
    {/if}
  
    <!-- Main Content -->
    <div class="main-content {sidebarOpen ? 'shift' : ''}">
      <div class="upload-container">
        <div
          class="upload-box"
          on:click={() => document.getElementById('fileInput').click()}
        >
          <img src="images/doc_icon.png" alt="Document Icon" />
          <span>{fileName}</span>
          <input
            type="file"
            id="fileInput"
            on:change={handleFileUpload}
            hidden
          />
        </div>
      </div>
  
      <div class="preview-container">
        <div class="file-preview">
          <p>{fileContent ? fileContent : 'Preview Not Available'}</p>
        </div>
        <button class="generate-btn">Generate Timetable</button>
      </div>
    </div>
  </div>
  {:else if currentView === 'home'}
    <LandingPage />
  {/if}
  