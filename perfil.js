function editField(textId, inputId, saveBtnId, editBtnId) {
    var value = document.getElementById(textId).innerText;
    // Hide the text and edit button
    document.getElementById(textId).style.visibility = 'hidden';
    document.getElementById(editBtnId).style.visibility = 'hidden'; // Use the unique ID
    
    // Show the input and save button, ensure they don't disrupt layout
    var inputElement = document.getElementById(inputId);
    var saveBtnElement = document.getElementById(saveBtnId);
    inputElement.style.visibility = 'visible';
    inputElement.style.display = 'inline-block';
    saveBtnElement.style.visibility = 'visible';
    saveBtnElement.style.display = 'inline-block';

    inputElement.value = value;
}

function saveField(textId, inputId, saveBtnId, editBtnId) {
    var newValue = document.getElementById(inputId).value;
    document.getElementById(textId).innerText = newValue;
    // Revert visibility
    document.getElementById(textId).style.visibility = 'visible';
    document.getElementById(inputId).style.visibility = 'hidden';
    document.getElementById(saveBtnId).style.visibility = 'hidden';
    document.getElementById(editBtnId).style.visibility = 'visible'; // Show the correct edit button again

    // Optionally revert to display none
    document.getElementById(inputId).style.display = 'none';
    document.getElementById(saveBtnId).style.display = 'none';
}

function previewImage(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('profileImage').src = e.target.result;
            // Optionally, you can submit the new image to the server here or use another button for submission.
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function downloadTicket(ticketId) {
    // Construct the URL for the ticket PDF. This might involve calling a server-side script or API.
    var ticketUrl = 'https://wordtemplatesbundle.com/wp-content/uploads/2018/02/Concert-ticket-2-CRC.jpg' // + ticketId + '.pdf';
    window.open(ticketUrl, '_blank');
}