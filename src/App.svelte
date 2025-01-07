<script>
  import { onMount } from 'svelte';
  import ExamTimetable from './B_ExamTimetable.svelte';
  import SeatingArrangement from './B_SeatingArrangement.svelte';

  let version;
  let currentView = 'home'; // Track the current view

  const get_version = async () => {
    version = await api.GetVersion();
  };

  get_version();

  let typingText = "ExamDesk";
  let displayedText = "";
  let cursorVisible = true;
  let currentIndex = 0;

  onMount(() => {
    const typeEffect = setInterval(() => {
      if (currentIndex < typingText.length) {
        displayedText += typingText[currentIndex];
        currentIndex++;
      } else {
        clearInterval(typeEffect);
      }
    }, 150);

    setInterval(() => {
      cursorVisible = !cursorVisible;
    }, 500);
  });
</script>

<svelte:head>
  <title>ExamDesk v{version}</title>
</svelte:head>

<style>
  /* General Styling */
  body {
    margin: 0;
    padding: 0;
    font-family: "DMSerif", 'Times New Roman';
    background-color: #f4f6f8;
    color: #333;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start; /* Align items towards the top */
    min-height: 100vh;
    overflow: hidden;
  }

  /* Header Styling */
  .header {
    font-size: 7vw; /* Larger for professional look */
    color: #2c3e50;
    margin-top: 9rem; /* Add spacing at the top */
    text-align: center;
  }

  .cursor {
    font-size: 6vw;
    color: #2c3e50;
    visibility: hidden;
    position: relative; /* Allows adjustment with top */
    top: -0.1em; /* Moves the cursor slightly upward */
  }

  .cursor.visible {
    visibility: visible;
  }

  /* Buttons Styling */
  .buttons-container {
    margin-top: 5rem; /* Space between header and buttons */
    display: flex;
    gap: 2rem;
    flex-wrap: wrap;
    justify-content: center;
  }

  .sqircle-button {
  display: flex;
  flex-direction: column; /* Stack image and text vertically */
  align-items: center;
  justify-content: center;
  width: 20vw; /* Adjusted size for accommodating text */
  height: 20vw;
  max-width: 180px;
  max-height: 180px;
  min-width: 90px;
  min-height: 90px;
  border-radius: 15%;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
}

.button-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem; /* Space between the icon and text */
}

.sqircle-button img {
  width: 60%; /* Resized to fit with text */
  height: auto; /* Maintain aspect ratio */
}

.sqircle-button span {
  font-size: 1.05rem; /* Adjust text size */
  color: #2c3e50; /* Match the design */
  text-align: center;
}


  .sqircle-button:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);
  }

  /* Responsive Adjustments */
  @media (max-width: 768px) {
    .header {
      font-size: 7vw;
    }

    .sqircle-button {
      width: 20vw;
      height: 20vw;
    }
  }

  @media (max-width: 480px) {
    .header {
      font-size: 8vw;
    }

    .sqircle-button {
      width: 25vw;
      height: 25vw;
    }
  }
</style>

{#if currentView === 'home'}
<body>
  <!-- Landing Page -->
  <div class="header">
    <span>{displayedText}<span class="cursor {cursorVisible ? 'visible' : ''}">|</span></span>
    
  </div>

  <div class="buttons-container">
    <div class="sqircle-button" on:click={() => currentView = 'exam-timetable'}>
      <div class="button-content">
        <img src="images/timetable.png" alt="Timetable" />
        <span>Exam Timetable</span>
      </div>
    </div>
    <div class="sqircle-button" on:click={() => currentView = 'seating-arrangement'}>
      <div class="button-content">
        <img src="images/seating.png" alt="Seating" />
        <span>Seating Arrangement</span>
      </div>
    </div>
  </div>
</body>
{:else if currentView === 'exam-timetable'}
  <ExamTimetable />
{:else if currentView === 'seating-arrangement'}
  <SeatingArrangement />
{/if}