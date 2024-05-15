<!DOCTYPE html>
<html>
<head>
    <title>Vacation Solution</title>
</head>
<body>
<h1>Vacation Solution</h1><hr>
<h3>Подробная информация о пользователе</h3>
<b>Имя пользователя: </b> ${user.name}<br>
<b>Адрес эл. почты: </b> ${user.email}<br>
<b>Номер телефона: </b> ${user.phoneNumber}<br>
<b>Дата рождения: </b> ${user.dateOfBirth}<br>

<h4>Запросы пользователя ${user.name}:</h4>
<#list requests as vacationrequest>
    <div>
        <p>${vacationrequest.startDate} - ${vacationrequest.endDate}
            (<u>${vacationrequest.status}</u>) | <a href="/vacation-request/${vacationrequest.id}">Подробнее...</a></p>
    </div>
    <#else>
        <h3>У данного пользователя запросов на отпуск <b>нет</b></h3>
</#list>
</body>
</html>