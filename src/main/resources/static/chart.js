const ctx = document.getElementById('chart');

let months = getNext6Months()

new Chart(ctx, {
  type: 'bar',
  data: {
    labels: months,
    datasets: [{
      label: '# of concerts',
      data: [12, 19, 3, 5, 2, 3],
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

getNext6Months();