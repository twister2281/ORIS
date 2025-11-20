<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<h1>Вход</h1>
<c:if test="${not empty param.msg}"><p style="color:green;">${param.msg}</p></c:if>
<c:if test="${not empty error}"><p style="color:red;">${error}</p></c:if>
<form method="post" action="login">
  Логин: <input name="username"/><br/>
  Пароль: <input type="password" name="password"/><br/>
  <input type="submit" value="Войти"/>
</form>
<p><a href="register">Регистрация</a></p>
<%@ include file="includes/footer.jsp" %>

