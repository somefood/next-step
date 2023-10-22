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

  let queryString = $(e.target).parent().serialize();

  console.log(queryString);

  $.ajax({
    type: "post",
    url: "/api/qna/deleteAnswer",
    data: queryString,
    dataType: "json",
    error: onError,
    success: function (json, status) {
      console.log(json);
      $(e.target).closest("article").remove();
    },
  })
}
