function rate(rate, reviewId) {
    let url = "/review/rate/" + reviewId;
    $.post(url, {rate : rate})
        .done(function(msg){
            showRating(msg,'user_rating' + reviewId);
            changeRatingNumbers(msg, reviewId);
        })
        .fail(function(xhr, status, error) {
            if (xhr.status === 401)
                location.href="/login";
        });
}


function changeRatingNumbers(rating, id) {
    let card_id = "card" + id;
    let user_rating_id = 'user_rating' + id;
    let preStarsRating = document.getElementById(user_rating_id).getElementsByClassName("rating-number")[0];
    preStarsRating.textContent = rating;

    let preTitleRating = document.getElementById(card_id).getElementsByClassName("title-rating")[0];
    preTitleRating.textContent = rating;
}

function showRating(rating, id) {
    var stars_in = document.getElementById(id).getElementsByClassName("stars__in");
    var stars = document.getElementById(id).getElementsByClassName("star");

    let full = Math.trunc(rating);
    let partly = rating - full;
    for (let i = 4; i > -1; i--) {
        let star_in = stars_in[i];
        let star = stars[i];

        star.classList.remove('star')
        star_in.style.width = i > (4 - full) ? '100%' : i === (4 - full) ? (partly * 100 + '%') : 0 + '%';
    }
}

