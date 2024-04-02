let data = [];
let months = [];

fetch('/amount-of-concerts-by-month')
    .then(function(response) {
        return response.json();
    })
    .then(function(json) {
        console.log(json)
        data = Array.from(json).map((x) => x.count);
        months = Array.from(json).map(x => new Date(x.date.year, (x.date.month - 1)).toLocaleString('default', { month: 'long' }));
        console.log(months);

        createChart();
    })

function createChart() {
    const ctx = document.getElementById('chart');

    let months_old = getNext6Months()

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: months,
            datasets: [{
                label: '# of concerts',
                data: data,
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

}

function getNext6Months() {
    let months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    let currentMonth = new Date().getMonth();
    
    let list = [];
    for (let i = 0; i < 6; i++) {
        list.push(months[(currentMonth + i) % 12]);
    }

    console.log(list);
    return list;
}