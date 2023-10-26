// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);

  document.querySelector(".qna-comment-count strong").innerText = json.answerCount;
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

let formDeletes = document.querySelectorAll(".qna-comment .link-delete-article");

formDeletes.forEach((element) => {
  element.addEventListener("click", (event) => {
    event.preventDefault();
    let currentTarget = event.target;
    console.log(currentTarget);
    let form = currentTarget.parentElement;

    let queryString = $(form).serialize();

    $.ajax({
      type: 'post',
      url: '/api/qna/deleteAnswer',
      data: queryString,
      dataType: 'json',
      error: onError,
      success: function (json, status) {
        $(currentTarget).closest("article").remove();
        document.querySelector(".qna-comment-count strong").innerText = document.querySelector(".qna-comment-count strong").innerText - 1;
      },
    });
  });
});
//
//
// let articleDeleteButton = document.querySelector(".content-main .link-delete-article");
// articleDeleteButton.addEventListener("click", (e) => {
//   e.preventDefault();
//
//   let deleteForm = articleDeleteButton.closest("form");
//
//   $(deleteForm);
// });