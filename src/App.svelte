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
  <title>ExamDesk</title>
</svelte:head>

{#if currentView === 'home'}
<body>
  <!-- Landing Page -->
  <div class="container">
    <!-- Main Content -->
    <div class="content">
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

      <div class="copyright">
        <a href="https://github.com/mcspidey95/ExamDesk/releases">v{version}</a>
      </div>
    </div>

    <!-- Vertical Banner -->
    <div class="banner">
      <img src="images/banner.jpg" alt="Banner" />
    </div>
  </div>
</body>
{:else if currentView === 'exam-timetable'}
  <ExamTimetable />
{:else if currentView === 'seating-arrangement'}
  <SeatingArrangement />
{/if}

<style>
  /* General Styling */
  body {
    margin: 0;
    font-family: "DMSerif", 'Times New Roman';
    background-color: #f4f6f8;
    color: #333;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center; /* Align items towards the top */
    min-height: 100v;
    overflow: hidden;
  }

  .container {
  display: grid;
  grid-template-columns: 2fr 1fr; /* Content takes 1 fraction, banner is auto-sized */
  height: 100%;
  width: 100%;
}

.content {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  padding: 20px;
  user-select: none;
}

  /* Header Styling */
  .header {
    font-size: 7vw; /* Larger for professional look */
    color: #2c3e50;
    top : 0;
    margin-top: 18vh; /* Add spacing at the top */
    text-align: center;
    text-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    cursor: default;
  }

  .copyright {
  position: fixed; /* Fixes the position relative to the viewport */
  bottom: 1em; /* 10px from the bottom of the screen */
  left: 1em; /* 10px from the right of the screen */
  font-size: 20px; /* Optional: adjust the font size */
  color: #2c3e50; /* Optional: set text color */
}

.copyright a {
  color: inherit; /* Inherit color from parent */
  text-decoration: none; /* Remove underline from the link */
  transition: transform 0.2s ease;
  display: inline-block; /* Make the link an inline-block so it can be scaled */
}

.copyright a:hover {
  transform: scale(1.1); /* Adjust scale factor to avoid overflow */
}

  .cursor {
    font-size: 6vw;
    color: #2c3e50;
    visibility: hidden;
    position: relative; /* Allows adjustment with top */
    top: -0.1em; /* Moves the cursor slightly upward */
    text-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    cursor: default;
  }

  .cursor.visible {
    visibility: visible;
  }

  /* Buttons Styling */
  .buttons-container {
    margin-top: 5em; /* Space between header and buttons */
    display: flex;
    gap: 2rem;
    flex-wrap: wrap;
    justify-content: center;
  }

  .banner {
  background-color: #2b2d42;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  overflow: hidden;
  width: 100%; /* Automatically takes the remaining 1/3rd */
}

.banner img {
  background-size: contain;
  max-width: 100%;
  height: 100%;
  transition: transform 0.2s ease-in-out;
}

.banner img:hover{
  transform: scale(1.05);
}

  .sqircle-button {
  display: flex;
  flex-direction: column; /* Stack image and text vertically */
  align-items: center;
  justify-content: center;
  width: 15vw; /* Adjusted size for accommodating text */
  height: 15vw;
  max-width: 1800px;
  max-height: 1800px;
  min-width: 90px;
  min-height: 90px;
  opacity: 0.9;
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