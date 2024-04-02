let currentArtistPage = 0;

$(document).ready(function() {
    loadMoreArtists(currentArtistPage);
});

//AJAX in /index
function loadMoreArtists(page) {
    $.ajax({
        url: '/more-artists',
        type: 'GET',
        data: { page: page },
        success: function(data) {
            const button = document.getElementById("more-artists-button");
            if (button)
                button.remove();
            document.getElementById("secondary-artist-list").insertAdjacentHTML('beforeend', data);
            addMoreResultsButtonListener();
            
        },
        error: function() {
            alert('Error al cargar más artistas');
        }
    });
}

function addMoreResultsButtonListener() {

    const button = document.getElementById('more-artists-button');
    if (button) {
        button.addEventListener('click', function() {
            currentArtistPage++;
            loadMoreArtists(currentArtistPage);
        });
    }
}