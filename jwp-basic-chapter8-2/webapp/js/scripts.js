String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

function addAnswer(e) {
  e.preventDefault();

  let queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: "post",
    url: "/api/qna/addAnswer",
    data: queryString,
    dataType: "json",
    error: onError,
    success: onSuccess,
  })
}


function onSuccess(json, status) {
  let answerTemplate = $("#answerTemplate").html();
  let template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}

$(".answerWrite input[type=submit]").click(addAnswer);

$(".qna-comment").click(deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  let serialize = $(e.target).children(".form-delete").serialize();

  console.log(serialize);

  // $.ajax({
  //   type: "post",
  //   url: "/api/qna/deleteAnswer",
  //   data: queryString,
  //   dataType: "json",
  //   error: onError,
  //   success: onSuccess,
  // })
}
