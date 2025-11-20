# Seventh Homework – JSP + JSTL + Include + Пагинация

Основа: 6-я домашка (CRUD + аутентификация + корзина). Переписано на JSP + JSTL.

## Требования выполнены
- Весь фронтенд на JSP (никаких HTML из сервлетов).
- JSTL (`c:if`, `c:forEach`, `c:choose`, `c:out`) для динамики.
- Общие части (header/footer) через директиву `include`.
- Пагинация списка товаров (`/products?page=...&size=...`).

## Маршруты
- /register, /login, /logout
- /products (GET: пагинация, POST: создать товар) параметры: page (>=1), size (>0)
- /product?id=ID
- /product-new, /product-edit?id=ID, /product-delete?id=ID
- /cart, /cart-add?id=ID, /checkout, /thankyou

## Пагинация
Сервлет считает `totalPages = (count + size - 1)/size`. Коррекция page если больше totalPages.

## Структура JSP
`/WEB-INF/jsp/`:
- includes/header.jsp, includes/footer.jsp
- login.jsp, register.jsp
- productList.jsp (список + форма создания + пагинация)
- productView.jsp
- productForm.jsp (новый/редактирование)
- cart.jsp
- thankyou.jsp

## DAO
- Добавлен метод `findPaged(page,size)` и `count()`.

## Сборка / Запуск
```bash
cd "SEVENTH HOMEWORK"
mvn jetty:run
```
Открыть: http://localhost:8080/products

## Тесты
- ProductDaoTest (CRUD + пагинация)
- UserDaoTest (создание пользователя)

## Идеи улучшений
- Выделить layout через тег-файл или JSP fragments.
- Добавить сортировку (name, price).
- Улучшить экранирование (JSTL c:out уже экранирует).
- Добавить удаление / изменение количества в корзине.
- CSRF-токены, защита от повторной отправки.
- Flyway миграции для схемы.

