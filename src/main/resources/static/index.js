let currentArtistPage = 0;

//AJAX in /index
$(document).ready(function() {
    $('#more-results-button').click(function() {
        currentArtistPage++;
        console.log("WAAAAA");

        $.ajax({
            url: '/moreArtists',
            type: 'GET',
            data: { page: currentArtistPage },
            success: function(data) {
                document.getElementById("more-results-button").style.display = 'block';
                    if (data == null)
                document.getElementById("more-results-button").style.display = 'none';
                    if (!data.hasNext)
                document.getElementById("more-results-button").style.display = 'none';
                    if (isNewQuery)
                    document.getElementById("secondary-artist-list").insertAdjacentHTML('beforeend', data.content);
            },
            error: function() {
                alert('Error al cargar más artistas');
            }
        });
    });
});