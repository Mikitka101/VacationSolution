<!DOCTYPE html>
<html>
<head>
    <title>Vacation Solution</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
        }

        h1 {
            background-color: #333;
            color: #fff;
            padding: 10px 20px;
            margin: 0;
        }

        hr {
            border: none;
            height: 1px;
            color: #333;
            background-color: #333;
            margin: 10px 0;
        }

        h4 {
            color: #333;
            padding: 10px 20px;
            margin: 0;
            background-color: #ddd;
        }

        form {
            padding: 20px;
            margin: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="email"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
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

        a {
            display: block;
            text-align: center;
            padding: 10px;
            text-decoration: none;
            color: #333;
            background-color: #ddd;
            border-radius: 4px;
        }

        a:hover {
            background-color: #eee;
        }
    </style>
</head>
<body>
<h1>Vacation Solution</h1><hr>
<h4>Авторизация</h4>
<form action="/login" method="post">
    Эл. почта: <input type="email" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>

</form>
<#if error>
    <div class="alert-danger" role="alert">Invalid Username or Password!</div>
</#if>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>