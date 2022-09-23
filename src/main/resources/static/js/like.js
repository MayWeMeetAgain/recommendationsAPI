function likeAction(reviewId) {
    let likeButton = document.getElementById('like-button' + reviewId);
    let likesAmount = document.getElementById('likes-amount' + reviewId);
    let url = "/review/like/" + reviewId;
    let type = likeButton.classList.contains("fa-regular") ? "POST" : "DELETE";
    sendLikeRequest(type, url, likesAmount, likeButton);
}

function sendLikeRequest(type, url, likesAmount, likeButton) {
    $.ajax({
        type: type,
        url: url,
        success: function(answer) { successHandler(answer, likesAmount, likeButton); },
        error: function(xhr, timeout, message) {
            if (xhr.status === 401)
                location.href="/login";
        }
    });
}

function successHandler(answer, likesAmount, likeButton) {
    likesAmount.textContent = answer;
    likeButton.classList.toggle("fa-regular");
    likeButton.classList.toggle("fa-solid");
}