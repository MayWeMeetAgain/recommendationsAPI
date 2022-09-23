function deleteReview(reviewId) {
    let url = "/review/" + reviewId;
    let cardId = "card" + reviewId;
    $.ajax({
        type: "DELETE",
        url: url,
        success: function(answer) { document.getElementById(cardId).remove()},
        error: function(jqXHR, timeout, message) {
            if (jqXHR.status === 401)
                location.href="/login";
        }
    });
}