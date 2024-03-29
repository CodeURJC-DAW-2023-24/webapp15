let currentPage = 0;

document.addEventListener('DOMContentLoaded', function() {
    filter(true);

    let anchors = document.getElementsByClassName('artist-info-anchor');
    Array.prototype.forEach.call(anchors, function(anchor) {
        anchor.addEventListener('click', function() {
            let artistName = anchor.getAttribute('artist');
            artistName = artistName.replace(" ", "-");
            anchor.href = ("/artist/" +  artistName);
        })
    });
});

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
        input.setAttribute('filter', 'location');
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
        input.setAttribute('filter', 'artist')
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
            currentPage = 0;
            filter(true);
        });
    });
}


function clearSearchList() {
    let concerts = document.querySelectorAll(".event-article");
    concerts.forEach(c => {
        c.remove();
    });
}

function getLocationsChecked() {
    let list = document.getElementById('location-list');
    let filters = list.querySelectorAll(".filter");
    return Array.from(filters).filter(x => x.checked).map(x => x.content);
}

function getArtistsChecked() {
    let list = document.getElementById('artist-list');
    let filters = list.querySelectorAll(".filter");
    return Array.from(filters).filter(x => x.checked).map(x => x.content);
}

function filter(isNewQuery) {
    getQuery(isNewQuery);
}

function getQuery(isNewQuery) {
    $.ajax({
        url: '/get-concerts',
        type: 'GET',
        data: { locations: JSON.stringify(getLocationsChecked()),
                artists: JSON.stringify(getArtistsChecked()),
                page: currentPage,
        },
        success: function(data) {
            const button = document.getElementById("more-results-button");
            if (button)
                button.remove();
            if (isNewQuery)
                clearSearchList();
            document.getElementById("search-results").insertAdjacentHTML('beforeend', data);
            addMoreResultsButtonListener();
            addTicketButtonListeners();
            addDeleteButtonListeners();
        },
        error: function() {
            alert('Error al cargar más conciertos');
        }
    });
}

function addMoreResultsButtonListener() {

    const button = document.getElementById("more-results-button");
    if (button) {
        button.addEventListener('click', function() {
            currentPage++;
            filter(false);
        });
    }

}

function addTicketButtonListeners() {

    const ticketButtons = document.querySelectorAll('.tickets-concert-list-button');

    ticketButtons.forEach(button => {
        let id = button.getAttribute("concertId");
        button.addEventListener('click', function() {
            window.location.href = "/payment/" + id;
        })
    })
}

function addDeleteButtonListeners() {
  
    const deleteButtons = document.querySelectorAll('.delete-btn');
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const concertId = button.getAttribute('data-id');
            const confirmation = confirm('¿Estás seguro de que quieres eliminar este concierto?');
            if (confirmation) {
                fetch(`/search/${concertId}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        // Successful removal
                        button.parentNode.remove();
                        alert('Concierto eliminado correctamente');
                    } else {
                        alert('Hubo un problema al intentar eliminar el concierto.');
                    }
                })
                .catch(error => {
                    console.error('Error en la solicitud DELETE:', error);
                    alert('Error en la solicitud DELETE');
                });
            }
        });
    });
}