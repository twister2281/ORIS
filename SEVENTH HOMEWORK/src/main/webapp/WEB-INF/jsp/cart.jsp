<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<h1>Корзина</h1>
<c:if test="${empty lines}"><p>Корзина пуста</p></c:if>
<c:if test="${not empty lines}">
<table border="1" cellpadding="5">
  <tr><th>ID</th><th>Название</th><th>Кол-во</th><th>Цена</th><th>Сумма</th></tr>
  <c:forEach var="l" items="${lines}">
    <tr>
      <td>${l.product.id}</td><td><c:out value="${l.product.name}"/></td><td>${l.qty}</td><td>${l.product.price}</td><td>${l.lineTotal}</td>
    </tr>
  </c:forEach>
  <tr><td colspan="4"><strong>Итого</strong></td><td><strong>${total}</strong></td></tr>
</table>
<c:if test="${not empty sessionScope.USER_ID}">
<form method="post" action="checkout"><input type="submit" value="Оформить заказ"/></form>
</c:if>
</c:if>
<%@ include file="includes/footer.jsp" %>

