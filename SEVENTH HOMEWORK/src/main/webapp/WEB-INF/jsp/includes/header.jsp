<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="background:#eee;padding:8px;display:flex;justify-content:space-between;">
  <div><a href="products">Товары</a> | <a href="cart">Корзина</a></div>
  <div>
    <c:choose>
      <c:when test="${not empty sessionScope.USER_ID}">
        Привет, ${sessionScope.USERNAME}! <a href="logout">Выйти</a>
      </c:when>
      <c:otherwise>
        <a href="login">Войти</a> | <a href="register">Регистрация</a>
      </c:otherwise>
    </c:choose>
  </div>
</div>
<hr/>

