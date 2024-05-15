<!DOCTYPE html>
<html>
<head>
    <title>
        Vacation Request
    </title>
</head>
<body>
<h1>Информация</h1>
<p>UserID: ${vacationRequest.user.name}</p>
<p>Request Date: ${vacationRequest.requestDate}</p>
<p>Start Date: ${vacationRequest.startDate}</p>
<p>End Date: ${vacationRequest.endDate}</p>
<p>Comment: ${vacationRequest.comment}</p>
<p>Status: ${vacationRequest.status}</p>
<br>
<form action="/vacation-request/delete/${vacationRequest.id}" method="post">
    <input type="submit" value="Удалить запрос">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
</form>
</body>
</html>