<!DOCTYPE html>
<html>
<head>
    <title>
        Vacation Request
    </title>
    <style type="text/css">
        TABLE {
            width: 300px; /* Ширина таблицы */
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }

        TH {
            background: #b0e0e6; /* Цвет фона */
        }
    </style>
</head>
<body>
<h3>Панель администратора</h3>
<h5>Список пользователей</h5>
<table>
    <tr>
        <th>Email</th>
        <th>Имя</th>
        <th>Активность</th>
        <th>Уровень доступа</th>
        <th>Бан</th>
        <th>Редактирование</th>
        <th>Подробнее</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.email}</td>
            <td>${user.name}</td>
            <td><#if user.active><b>+</b><#else><b>-</b></#if></td>
            <td><#list user.roles as role>${role}<#sep>,&#160;</#list></td>
            <td>
                <form action="/admin/user/ban/${user.id}" method="post">

                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Бан">
                </form>
            </td>
            <td><a href="/admin/user/edit/${user.id}"><b>Edit</b></a></td>
            <td><a href="/user-info/${user.id}"><b>...</b></a></td>
        </tr>
    <#else>
        <h3>Пользователей нет</h3>
    </#list>
</table><br>
<h5>Список Всех запросов</h5>
<form action="/admin" method="get">
    <label for="status">Поиск по статусу запроса</label>
    <select id="status" name="status">
        <option value="All requests">Все запросы</option>
        <option value="In Progress">В обработке</option>
        <option value="Approved">Одобрена</option>
        <option value="Rejected">Отклонена</option>
    </select>
    <input type="submit" value="Поиск">
</form>
<table>
    <tr>
        <th>Эл. почта пользователя</th>
        <th>Дата запроса</th>
        <th>Дата начала</th>
        <th>Дата окончания</th>
        <th>Комментарии</th>
        <th>Статус</th>
        <th>Обработать</th>
    </tr>
    <#list requests as vacationrequest>
        <tr>
            <td>${vacationrequest.user.email}</td>
            <td nowrap>${vacationrequest.requestDate}</td>
            <td nowrap>${vacationrequest.startDate}</td>
            <td nowrap>${vacationrequest.endDate}</td>
            <td>${vacationrequest.comment}</td>
            <td nowrap>${vacationrequest.status}</td>
            <td>
                <form action="/admin/vacation-request/approve/${vacationrequest.id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="+/-">
                </form>
            </td>
        </tr>
    <#else>
        <h3>Запросов нет</h3>
    </#list>
</table>
</body>
</html>