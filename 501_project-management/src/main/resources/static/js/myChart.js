const chartJSON = JSON.parse(decodeHtml(chartData));

const len = chartJSON.length;
const labelData = [];
const numericData = [];

for (const [index, data] of chartJSON.entries()) {
	labelData[index] = data.label;
	numericData[index] = data.value;
}

new Chart(document.getElementById("myPieChart"), {
	type: "pie",
	data: {
		labels: labelData,
		datasets: [{
			data: numericData,
			backgroundColor: [
				'rgba(255, 99, 132, 0.2)',
				'rgba(54, 162, 235, 0.2)',
				'rgba(255, 206, 86, 0.2)',
				'rgba(75, 192, 192, 0.2)',
				'rgba(153, 102, 255, 0.2)',
				'rgba(255, 159, 64, 0.2)'
			],
			borderColor: [
				'rgba(255,99,132,1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			],
			borderWidth: 1
		}]
	},
	options: {
		title: {
			display: true,
			text: 'Project Status'
		}
	}
})

function decodeHtml(html) {
	const txt = document.createElement("textarea");
	txt.innerHTML = html;
	return txt.value;
}