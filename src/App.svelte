<script>
	let version;
	import ExamTimetable from './B_ExamTimetable.svelte';
	import SeatingArrangement from './B_SeatingArrangement.svelte';
    let currentView = 'home'; // Track the current view

	const get_version = async () => {
		version = await api.GetVersion();
	};

	get_version();

	window.api.runJavaFile('functions/ExamTimetable.class');
</script>

<svelte:head>
    <title>ExamDesk v{version}</title>
</svelte:head>

<main>
	{#if currentView === 'home'}
    <div class="container">
        <h1>ExamDesk</h1>
        <div class="buttons-container">
            <button class='button' on:click={() => currentView = 'timetable'}>
				<img src="images/timetable.png" alt="Timetable" />
                Exam Timetable
            </button>
			<button class='button' on:click={() => currentView = 'seating'}>
				<img src="images/seating.png" alt="Seating Arrangement" />
                Seating Arrangement
            </button>
        </div>
    </div>
	{:else if currentView === 'timetable'}
		<ExamTimetable />
	{:else if currentView === 'seating'}
		<SeatingArrangement />
	{/if}
</main>

<style>
	main {
        font-family: 'Iosevka', sans-serif;
        background-color: white;
        color: black;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
	}

    @font-face {
        font-family: 'Iosevka';
        src: url('fonts/IosevkaTermSlabNerdFontMono-Bold.ttf');
        font-weight: normal;
        font-style: normal;
    }

    .container {
        text-align: center;
        margin-top: 250px;
    }

    h1 {
        font-size: 8rem;
        font-weight: bold;
        color: black;
        position: absolute;
        top: 50px;
        left: 50%;
        transform: translateX(-50%);
    }

    .buttons-container {
        display: flex;
        justify-content: center;
        margin-top: 100px;
    }

    .button {
        font-family: 'Iosevka', sans-serif;
        font-size: 1.5rem;
        padding: 20px;
        border: none;
        border-radius: 10px;
        background-color: #f8f8f8;
        color: black;
        text-decoration: none;
        cursor: pointer;
        transition: transform 0.2s ease, background-color 0.2s ease, box-shadow 0.2s ease;
        margin: 15px;
        width: 245px;
        height: 225px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    }

    .button:hover {
        transform: scale(1.05);
        background-color: #e0e0e0;
    }

    .button img {
        width: 72px;
        height: 72px;
        margin-bottom: 20px;
    }
</style>