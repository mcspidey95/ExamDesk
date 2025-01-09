<script>
  let currentView = 'seatingArrangement';  
  
  let tsvData = ""; // Placeholder for the loaded TSV data
  let parsedData = [];
  let headers = [];
  let content = [];
  let rowCount = 0;
  let columnCount = 0;

  // Function to load the TSV file from a specific file path
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

  // Example of passing the file path when calling loadTSVFile
  // You can replace this with the actual path or pass it dynamically
  loadTSVFile("functions/documents/Exam_Timetable.tsv");
  </script>
  
  <div class="tsvViewer scroll_enabled">
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

	<div class="info-box">
		<p>Rows: {rowCount}</p>
		<p>Columns: {columnCount}</p>
	  </div>
  </div>
  
  <style>
	.tsvViewer{
	  background-color: white;
	  border-radius: 8px;
	  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	  margin: 20px;
	  padding: 20px;
	  max-height: 80vh;
	  overflow: auto;
	}
  
	table {
	  width: 100%;
	  border-collapse: collapse;
	}
  
	th, td {
	  border: 1px solid #ddd;
	  padding: 8px;
	  text-align: left;
	}
  
	th {
	  background-color: #f2f2f2;
	  font-weight: bold;
	}
  
	tr:nth-child(even) {
	  background-color: #f9f9f9;
	}

	.info-box {
    position: fixed;
    bottom: 10px;
    left: 10px;
    z-index: 1000;
    background-color: rgba(0, 0, 0, 0.8);
    color: white;
    padding: 10px;
    border-radius: 5px;
    font-size: 14px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  }




  </style>