<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<c:choose>
  <c:when test="${empty product}"><p>Товар не найден</p></c:when>
  <c:otherwise>
    <h1>${product.name}</h1>
    <p>Цена: ${product.price} руб.</p>
    <p>Описание: <c:out value="${product.description}"/></p>
    <c:if test="${not empty sessionScope.USER_ID}">
      <p><a href="cart-add?id=${product.id}">В корзину</a></p>
    </c:if>
  </c:otherwise>
</c:choose>
<p><a href="products">Назад</a></p>
<%@ include file="includes/footer.jsp" %>

