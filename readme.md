## Второе ИДЗ по КПО

### Как запустить?
**1. Установить docker**

**2. Установить Postgres**

**3. Создать контейнер postgres со следующими параметрами:**
```
user:postgres
password: 123
db_name = restaurant 
db_host = localhost
db_port = 5432
```

**4. Запустить kotlin приложение**


### Внимание, прежде чем тестить приложение, добавьте в db некоторые блюда!

```
INSERT INTO dishschema (identifier, name, description, cooking_time, available_count, cost) VALUES (123, 'test_dish', 'test_description', 15.0, 18, 1500.0);
```


### Доступные эндпоинты:

```
Просмотр списка гостей

https://127.0.0.1:8080/quest
```

```
Просмотр списка администраторов

https://127.0.0.1:8080/administration
```

```
Просмотр информации о госте

https://127.0.0.1:8080/quest/{id}
```

```
Просмотр информации об администраторе

https://127.0.0.1:8080/administration/{id}
```

```
Регистрация гостя

https://127.0.0.1:8080/quest/register

{
    "name": "Roman",
    "surname": "Gromov",
    "email": "testmail123@gmail.com",
    "passsword": "raW123PassW__",
    "age": 19,
    "balance": 1245.0
}
```

```
Авторизация гостя

https://127.0.0.1:8080/quest/login

{
    "email": "testmail123@gmail.com",
    "passsword": "raW123PassW__"
}

Ответ: Jwt токен, который стоит перенести в Headers запроса
```

```
Выход из акаунта гостя

https://127.0.0.1:8080/quest/logout
```


```
Просмотр меню (ТОЛЬКО ДЛЯ АВТОРИЗИРОВАННЫХ)

https://127.0.0.1:8080/dish/menu
```

```
Просмотр информации о блюде (ТОЛЬКО ДЛЯ АВТОРИЗИРОВАННЫХ)

https://127.0.0.1:8080/dish/{id}
```

```
Создание заказа (ТОЛЬКО ДЛЯ АВТОРИЗИРОВАННЫХ)

https://127.0.0.1:8080/order/make

{
    "dishes": "Name1 Name2 NameN"
}
```

```
Получение информации о своём заказе (ТОЛЬКО ДЛЯ АВТОРИЗИРОВАННЫХ)

https://127.0.0.1:8080/order/my

```

```
Оплата заказа (ТОЛЬКО ДЛЯ АВТОРИЗИРОВАННЫХ)

https://127.0.0.1:8080/order/pay

```