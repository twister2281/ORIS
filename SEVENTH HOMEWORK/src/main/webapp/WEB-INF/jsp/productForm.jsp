<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<c:choose>
  <c:when test="${empty product}"><h1>Новый товар</h1></c:when>
  <c:otherwise><h1>Редактировать товар #${product.id}</h1></c:otherwise>
</c:choose>
<c:if test="${not empty error}"><p style="color:red;">${error}</p></c:if>
<form method="post" action="<c:choose><c:when test='${empty product}'>product-new</c:when><c:otherwise>product-edit</c:otherwise></c:choose>">
  <c:if test="${not empty product}"><input type="hidden" name="id" value="${product.id}"/></c:if>
  Название: <input name="name" value="<c:out value='${product.name}'/>"/><br/>
  Цена: <input name="price" value="<c:out value='${product.price}'/>"/><br/>
  Описание:<br/>
  <textarea name="description" rows="4" cols="50"><c:out value='${product.description}'/></textarea><br/>
  <input type="submit" value="Сохранить"/>
</form>
<p><a href="products">Назад</a></p>
<%@ include file="includes/footer.jsp" %>

