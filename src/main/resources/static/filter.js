// Get data of concerts
fetch('/concert-list-data')
    .then(function(response) {
        return response.json();
    })
    .then(function(json) {
        let locations = json.locations;
        let artists = json.artists;
        generateLocationCheckboxes(locations);
        generateArtistsCheckboxes(artists);
        addFiltersListeners();
    })


function generateLocationCheckboxes(locations) {
    let list = document.getElementById('location-list');
    locations.forEach(l => {
        let li = list.appendChild(document.createElement('li'));
        let label = li.appendChild(document.createElement('label'));
        let input = label.appendChild(document.createElement('input'));
        input.setAttribute('class', 'filter');
        input.setAttribute('class', 'filter-location');
        input.setAttribute('type', 'checkbox');
        input.content = l;
        let span = label.appendChild(document.createElement('span'));
        span.textContent = l;
    });
}

function generateArtistsCheckboxes(artists) {
    let list = document.getElementById('artist-list');
    artists.forEach(a => {
        let li = list.appendChild(document.createElement('li'));
        let label = li.appendChild(document.createElement('label'));
        let input = label.appendChild(document.createElement('input'));
        input.setAttribute('class', 'filter');
        input.setAttribute('class', 'filter-artist')
        input.setAttribute('type', 'checkbox');
        input.content = a;
        let span = label.appendChild(document.createElement('span'));
        span.textContent = a;
    })
}

// Add listeners to filters checkboxes
function addFiltersListeners() {
    let filters = document.querySelectorAll(".filter");
    filters.forEach(f => {
        f.addEventListener("change", function(){
            clearSearchList();
            generateQueryParameters();
        });
    });
}


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