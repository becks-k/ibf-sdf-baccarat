function csvToTable(csv) {
    var lines = csv.trim().split("\n");
    var tableHTML = "<tbody>";
    for (var i = 0; i < lines.length; i++) {
        tableHTML += "<tr>";
        var cells = lines[i].split(",");
        for (var j = 0; j < cells.length; j++) {
            tableHTML += "<td>" + cells[j] + "</td>";
        }
        tableHTML += "</tr>";
    }
    tableHTML += "</tbody>";
    return tableHTML;
}

function fetchDisplayCSV(filePath) {
    fetch(filePath)
        .then(response => response.text())
        .then(data => {
            document.getElementById('csv-table').innerHTML = csvToTable(data);
        })
        .catch(error => console.error('Error fetching csv file:', error));
}

fetchDisplayCSV('game_history.csv');

// https://github.com/leqing92/Baccarat/blob/main/script.js
// See Chiew's code for alternative!