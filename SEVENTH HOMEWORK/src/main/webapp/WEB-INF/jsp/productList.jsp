<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<h1>Список товаров</h1>
<c:if test="${empty products}"><p>Нет товаров</p></c:if>
<c:if test="${not empty products}">
  <ul>
    <c:forEach var="p" items="${products}">
      <li>
        <a href="product?id=${p.id}">${p.name}</a> - ${p.price} руб.
        <c:choose>
          <c:when test="${not empty userId}">
            <a href="cart-add?id=${p.id}">В корзину</a> | <a href="product-edit?id=${p.id}">Редактировать</a> | <a href="product-delete?id=${p.id}" onclick="return confirm('Удалить?')">Удалить</a>
          </c:when>
          <c:otherwise>(войдите для действий)</c:otherwise>
        </c:choose>
      </li>
    </c:forEach>
  </ul>
</c:if>
<c:if test="${not empty userId}">
<h2>Добавить товар</h2>
<form method="post" action="products">
  Название: <input name="name"/> Цена: <input name="price"/> <br/>
  Описание:<br/>
  <textarea name="description" rows="3" cols="40"></textarea><br/>
  <input type="submit" value="Создать"/>
</form>
</c:if>
<hr/>
<div>
  Страница:
  <c:forEach var="p" begin="1" end="${totalPages}">
    <c:choose>
      <c:when test="${p == currentPage}"><strong>[${p}]</strong></c:when>
      <c:otherwise><a href="products?page=${p}&size=${size}">${p}</a></c:otherwise>
    </c:choose>
  </c:forEach>
</div>
<%@ include file="includes/footer.jsp" %>

