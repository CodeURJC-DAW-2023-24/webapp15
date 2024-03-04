let currentArtistPage = 0;

//AJAX in /index
$(document).ready(function() {
    $('#moreArtistButton').click(function() {
        currentArtistPage++;
        var existingCount = $('.listArtist li').length; 

        $.ajax({
            url: '/moreArtists',
            type: 'GET',
            data: { page: currentArtistPage },
            success: function(data) {
                if (data.trim() === '') {
                    $('#moreArtistButton').hide();
                } else {
                    $('.listArtist').append(data);
                }
            },
            error: function() {
                alert('Error al cargar más artistas');
            }
        });
    });
});