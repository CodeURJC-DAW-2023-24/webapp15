// Get data of concerts
fetch('/concert-list-data')
    .then(function(response) {
        return response.json();
    })
    .then(function(json) {
        let locations = json.locations;
        let artists = json.artists;
        console.log(locations, artists);
    });

// Add listeners to filters checkboxes
let filters = document.querySelectorAll(".filter");
filters.forEach(f => {
    f.addEventListener("change", function(){
        clearSearchList();
        generateQueryParameters();
    });
});

function clearSearchList() {
    let concerts = document.querySelectorAll(".event-article");
    concerts.forEach(c => {
        c.remove();
    });
}

function generateQueryParameters() {
    let filters = document.querySelectorAll(".filter");
    filters.forEach(f => {
        console.log(f.checked);
    }) 
}


// // AJAX in /search filters
// $(document).ready(function() {
//     $('#moreConcertButton').click(function() {
//         currentConcertPage++;
//         var existingCount = $('.event-article').length;
//         console.log($('.event-article').length, existingCount); 

//         $.ajax({
//             url: '/moreConcerts',
//             type: 'GET',
//             data: { page: currentConcertPage },
//             success: function(data) {
//                 if (!data.hasNext)
//                     $('#moreConcertButton').hide();
//                 $('#search-results2').append(data.content);
//             },
//             error: function() {
//                 alert('Error al cargar más conciertos');
//             }
//         });
//     });
// });