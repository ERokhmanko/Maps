# Карты и геопозиционирование:  Google Maps

## Задача:
Приложение предназначено для путешествующих по России.

Нужно сделать так, чтобы пользователь мог кликать на карте, а в месте клика создавать маркер с описанием (например, "Нулевая верста").

Все точки должны храниться на устройстве, чтобы потом человек мог приехать в город и быстро по карте соориентироваться, какие достопримечательности он хотел посмотреть. 

Приложение должно поддерживать:
* Создание точки
* Редактирование точки
* Удаление точки
* Просмотр всех точек (на отдельном экране - пользователь кликает в существующую точку и переходит к ней на карте)

## Инструменты:
Kotlin, MVVM,  RecyclerView, Room, Coroutines, LiveData, Google Maps

## Результат:
Первый запуск приложения начинается с запроса на разрешения отображения местоположения. <br>
 [![Разрешение](https://raw.githubusercontent.com/ERokhmanko/Maps/be49837b3dc8c62ef73c54e1d922234567bf6703/%D1%80%D0%B0%D0%B7%D1%80%D0%B5%D1%88%D0%B5%D0%BD%D0%B8%D0%B5.jpg)]


После разрешения отображается текущее местоположение. Также текущее место положение можно узнать с помощью кнопки на карте. <br>
[![Геопозиция](https://raw.githubusercontent.com/ERokhmanko/Maps/be49837b3dc8c62ef73c54e1d922234567bf6703/%D0%BC%D0%BE%D0%B5%20%D0%BC%D0%B5%D1%81%D1%82%D0%BE%D0%BF%D0%BE%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5.jpg)]


Нажав на кнопку в верхнем левом углу мы перейдем к списку мест. Если список пустой, приложение сообщит об этом. <br>
[![Вариант списков](https://raw.githubusercontent.com/ERokhmanko/Maps/be49837b3dc8c62ef73c54e1d922234567bf6703/%D0%92%D0%B0%D1%80%D0%B8%D0%B0%D0%BD%D1%82%20%D1%81%D0%BF%D0%B8%D1%81%D0%BA%D0%BE%D0%B2.png)]

Для того, чтобы создать новое место, необходимо нажать на кнопку "плюс" в правом нижнем углу. <br>
[![Экран создания места](https://raw.githubusercontent.com/ERokhmanko/Maps/be49837b3dc8c62ef73c54e1d922234567bf6703/%D1%8D%D0%BA%D1%80%D0%B0%D0%BD%20%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F%20%D0%BC%D0%B5%D1%81%D1%82%D0%B0.jpg)]

Посмотреть координаты места можно в списке. При нажатии на место, приложение отображает карту и красный маркер. <br>
[![Маркер](https://raw.githubusercontent.com/ERokhmanko/Maps/be49837b3dc8c62ef73c54e1d922234567bf6703/%D0%BC%D0%B0%D1%80%D0%BA%D0%B5%D1%80%20%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%BD%D0%BE%D0%B3%D0%BE%20%D0%BC%D0%B5%D1%81%D1%82%D0%B0.jpg)]

Вы можете отмечать те места, в которых уже побывали. Отмеченные места опускатся вниз списка. <br>
[![Сортировка списка](https://raw.githubusercontent.com/ERokhmanko/Maps/be49837b3dc8c62ef73c54e1d922234567bf6703/%D0%A1%D0%BE%D1%80%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0%20%D1%81%D0%BF%D0%B8%D1%81%D0%BA%D0%B0!.png)]

Вы можете редактировать созданные места или удалять их из списка. <br>
[![Меню](https://raw.githubusercontent.com/ERokhmanko/Maps/be49837b3dc8c62ef73c54e1d922234567bf6703/%D0%BC%D0%B5%D0%BD%D1%8E%20%D0%BC%D0%B5%D1%81%D1%82%D0%B0.jpg)]
