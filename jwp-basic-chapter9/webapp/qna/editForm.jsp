<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="kr">
<head>
	<%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<c:if test="${empty user}">
    <script>
      alert("로그인 해주세요!");
      window.location.href = "/users/loginForm";
    </script>
</c:if>

<c:if test="${question.writer ne user.name}">
    <script>
        alert("해당 유저가 아닙니다.");
        window.location.href = "/";
    </script>
</c:if>

<div class="container" id="main">
   <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
      <div class="panel panel-default content-main">
          <form name="question" method="post" action="/qna/edit">
              <div class="form-group">
                  <label for="writer">글쓴이</label>
                  <input class="form-control" id="writer" name="writer" readonly value="${user.name}" placeholder="글쓴이"/>
              </div>
              <div class="form-group">
                  <label for="title">제목</label>
                  <input type="text" class="form-control" id="title" name="title" value="${question.title}" placeholder="제목"/>
              </div>
              <div class="form-group">
                  <label for="contents">내용</label>
                  <textarea name="contents" id="contents" rows="5" class="form-control">
                      ${question.contents}
                  </textarea>
              </div>
              <input type="hidden" name="questionId" value="${question.questionId}" />
              <button type="submit" id="submit-button" class="btn btn-success clearfix pull-right">수정하기</button>
              <div class="clearfix" />
          </form>
        </div>
    </div>
</div>
<script>
    let submitButton = document.querySelector("#submit-button");
    submitButton.addEventListener("click", (e) => {
        e.preventDefault();
        const form = submitButton.closest("form");

        let queryString = $(form).serialize();

        $.ajax({
            type : 'post',
            url : '/qna/edit',
            data : queryString,
            dataType : 'json',
            error: onError,
            success : function (json, status) {
                alert("수정되었습니다.");
                window.location.href = "/qna/show?questionId=${question.questionId}"
            },
        });
    });
</script>
<%@ include file="/include/footer.jspf" %>
</body>
</html>