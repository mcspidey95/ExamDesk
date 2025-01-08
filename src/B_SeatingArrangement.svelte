<script>
	export let tsvData = `Name	Age	City	Occupation
John Doe	32	New York	Software Engineer
Jane Smith	28	San Francisco	Designer
Mike Johnson	45	Chicago	Manager
Emily Brown	39	Los Angeles	Teacher
David Lee	52	Boston	Doctor
Sarah Wilson	31	Seattle	Marketing Specialist
Tom Davis	41	Austin	Entrepreneur
Lisa Taylor	36	Denver	Accountant
Chris Anderson	29	Miami	Sales Representative
Karen Martinez	47	Phoenix	Lawyer`;
  
	// Parse TSV data into an array of arrays
	$: parsedData = tsvData.trim().split('\n').map(row => row.split('\t'));
  
	// Extract headers (first row) and content (remaining rows)
	$: headers = parsedData[0] || [];
	$: content = parsedData.slice(1);
  </script>
  
  <div class="tsv-container">
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
  
  <style>
	.tsv-container {
	  background-color: white;
	  border-radius: 8px;
	  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	  margin: 20px;
	  padding: 20px;
	  max-height: 80vh;
	  overflow-y: auto;
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
  </style>