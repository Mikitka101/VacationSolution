<!DOCTYPE html>
<html>
<head>
    <title>
        Vacation Request
    </title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
        }

        h2 {
            color: #fff;
            padding: 18px 2px;
            margin: 0;
        }

        hr {
            border: none;
            height: 1px;
            color: #333;
            background-color: #333;
            margin: 10px 0;
        }

        #myForm, #filterForm form {
            padding: 20px;
            margin: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"], input[type="date"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }

        #logout-form input[type="submit"] {
            background-color: #333;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"] {
            background-color: #333;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #444;
        }

        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }

        a {
            display: block;
            text-align: center;
            padding: 10px;
            margin: 14px 15px 14px 0;
            text-decoration: none;
            color: #eee;
            background-color: #333;
            border-radius: 4px;
            width: fit-content;
            cursor: pointer;
        }

        #myForm input[type="text"]#comment {
            width: 50%;
        }

        #myForm label {
            display: inline-block;
            width: 200px;
            text-align: right;
            margin-right: 10px;
            vertical-align: top;
        }

        #myForm input[type="date"] {
            width: 300px;
        }

        #myForm input[type="submit"] {
            width: 100%;
        }

        #myForm input[type="hidden"] {
            display: none;
        }

        #myForm label[for="requestDate"] {
            display: none;
        }

        #myForm label[for="status"] {
            display: none;
        }

        #myForm label[for="id"] {
            display: none;
        }

        #myForm label[for="comment"] {
            width: auto;
            text-align: left;
            margin-right: 0;
            vertical-align: middle;
        }

        #logout-form {
            float: right;
            margin-top: 12px;
            margin-right: 12px;
        }

        .avatar {
            border: 2px solid dimgrey; /* width, style, color */
            border-radius: 50%; /* makes the border rounded */
            height: 50px;
            margin: 6px 20px;
        }

        .title {
            float: left;
            color: #fff;
            padding: 13px 2px;
            margin: 0 0 0 13px;
        }

        .requests-table {
            width: 600px; /* set the desired width */
            border-collapse: collapse;
            border: 1px solid #ccc;
            margin: 13px 0 0 15px;
        }
        .requests-table th,.requests-table td {
            border: 1px solid #ccc;
            padding: 10px;
        }
    </style>
</head>
<body>
<div style="background: #444444;height: 65px; width:100%;display: flex">
    <h1 class="title">Vacation Solution</h1>
    <h2 style="margin-left: auto;">${user.name}</h2>
    <hr>
    <img src="/image/${user.getAvatar().getId()}" alt="User Avatar" class="avatar">
    <#if user.isAdmin()>
        <a href="/admin">Панель администратора</a>
    </#if>
    <form action="/logout" method="post" id="logout-form">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Выйти">
    </form>
</div>
<div style="display: flex">
    <h3  style="margin: 25px 0 0 15px">Ваши запросы на отпуск</h3>
    <form action="/vacation-requests" method="get" id="filterForm" style="display: flex;">
        <select id="status" name="status" style="margin: 7px 0 0 17px; font-size: 16px; height: 60px">
            <option value="All requests">Все запросы</option>
            <option value="In Progress">В обработке</option>
            <option value="Approved">Одобрена</option>
            <option value="Rejected">Отклонена</option>
        </select>
        <input type="submit" value="Примелнить фильтр"
               style="
                    background-color: #333;
                    border-radius: 4px;
                    cursor: pointer;
                    font-size: 16px;
                    margin-top: 7px;
                    margin-left: 17px;
                    height: 60px"
        >
    </form>
</div>
<table id="requestsTable" class="requests-table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Status</th>
        <th>Details</th>
    </tr>
    </thead>
    <tbody>
    <#list requests as vacationrequest>
        <tr>
            <td><a href="/user-info/${vacationrequest.user.id}"><b>${vacationrequest.user.name}</b></a></td>
            <td>${vacationrequest.startDate}</td>
            <td>${vacationrequest.endDate}</td>
            <td><u>${vacationrequest.status}</u></td>
            <td><a href="/vacation-request/${vacationrequest.id}">Подробнее...</a></td>
        </tr>
    <#else>
        <tr>
            <td colspan="5"><p style="width: 100%; text-align: center">Запросов на отпуск нет</p></td>
        </tr>
    </#list>
    </tbody>
</table>
<hr>
<h4>Создать новый запрос:</h4>
<form id="myForm" action="/vacation-request/create" method="post">

    <input style="display: none" type="number" id="id" value=-1 name="id" readonly>
    <input style="display: none" type="text" id="requestDate" name="requestDate" value="01-01-2000" readonly>
    <input style="display: none" type="text" id="status" name="status" value="In Progress" readonly>


    <label for="startDate">Start Date:</label>
    <input type="text" id="startDate" name="startDate" required><br><br>

    <label for="endDate">End Date:</label>
    <input type="text" id="endDate" name="endDate" required><br><br>

    <label for="comment">Comment:</label>
    <input type="text" id="comment" name="comment" value="-"><br><br>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Отправить запрос">
</form>
</body>
<script>
    document.getElementById('myForm').addEventListener('submit', function (event) {
        const commentInput = document.getElementById('comment');
        if (commentInput.value.length === 0) {
            commentInput.value = "-";
        }
    });
</script>
</html>