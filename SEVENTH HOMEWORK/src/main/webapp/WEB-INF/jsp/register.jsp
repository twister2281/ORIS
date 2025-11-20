<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<h1>Регистрация</h1>
<c:if test="${not empty error}"><p style="color:red;">${error}</p></c:if>
<form method="post" action="register">
  Логин: <input name="username"/><br/>
  Пароль: <input type="password" name="password"/><br/>
  Повторите пароль: <input type="password" name="password2"/><br/>
  <input type="submit" value="Зарегистрироваться"/>
</form>
<p><a href="login">Вход</a></p>
<%@ include file="includes/footer.jsp" %>

