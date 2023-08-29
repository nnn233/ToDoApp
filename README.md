# ToDoApp   

## Содержание
* [Общая информация](#general-info)
* [Функциональность](#technologies)
* [Детали реализации](#implementation_details)
* [Используемые технологии и архитектура](#used_technologies_and_architecture)
* [Планируемые расширения функциональности](#planning_teckhnologies_extention)
* [Планируемые изменения в реализции](#planning_implementation_changes)

## Общая информация
Данное приложение создано в рамках Открытого Лектория Летней школы мобильной разработки от компании Яндекс. Основная функция приложения состоит в хранении списка дел пользователя, каждое их которых имеет текст, приоритет, дедлайн, флаг выполнения, дату создания и дату изменения.   
<br />
![document_5415879646300354594-_1_](https://github.com/nnn233/ToDoApp/assets/126052177/d1cbe172-cb3a-44c2-a05d-ab1310e96f4f)   


## Функциональность
На главном экране находится список дел пользователя, если таковых нет - пользователю предлагается их создать. В правом верхнем угле экрана есть иконка, которая позволяет отображать/скрывать выполненные дела из списка. Прямо в списке пользователь может отмечать дела, как выполненные. Внизу экрана находится кнопка для перехода на экран создания дела.   
<br />
![photo_5415879646756590112_y](https://github.com/nnn233/ToDoApp/assets/126052177/b7cc021f-5415-4826-a375-c954164cc86e)&ensp;
![photo_5415879646756590111_y](https://github.com/nnn233/ToDoApp/assets/126052177/799c622d-c52a-46a2-8e4e-a077e82d5aad)&ensp;
![photo_5415879646756590113_y](https://github.com/nnn233/ToDoApp/assets/126052177/408637e8-9950-42db-92a9-443c29604f7d)   
<br />
  
На экране создания дела пользователь может ввести текст, с помощью диалога выбрать дату дедлайна. При нажатии на "Сохранить" произойдет автоматический переход на главный экран, и в список добавится новая заметка. При нажатии на иконку отмены, на экране отобразится диалог, уточняющий намерение пользователя.   
<br />
![photo_5415879646756590116_y](https://github.com/nnn233/ToDoApp/assets/126052177/9f8f84b1-591d-4523-949b-856708298fa4)&ensp;
![photo_5415879646756590119_y](https://github.com/nnn233/ToDoApp/assets/126052177/77c088f0-62b4-4a24-aa38-ad910ff190bb)&ensp;
![photo_5415879646756590118_y](https://github.com/nnn233/ToDoApp/assets/126052177/3622b397-fa98-4d8b-8d0d-285aba158600)   
<br />
   
При нажатии на элемент в общем списке происходит переход на экран создания/редактирования дела. Пользователь может внести изменения, закрыть окно или удалить заметку, при любом из этих действий будет выполнен переход на главный экран.   
<br />
![photo_5415879646756590114_y](https://github.com/nnn233/ToDoApp/assets/126052177/d4798f37-c981-460e-a4a9-ac37a8988803)   

## Детали реализации
Для локального хранения данных использована база данных Room  
Вместо данных, полученных от сервера, используются захардкоренные данные с некоторой задержкой   
Для доступа к данным -  ViewModel+LiveData

## Используемые технологии и архитектура
SingleActivity   
Разбиение слоев в соответствии с подходом CleanArchitecture

## Планируемые расширения функциональности
* Выполнение/удаление дела в списке по свайпу
* Появление информационного окна в списке для отбражения даты создания и даты изменения дела

## Планируемые изменения в реализации
* Внедрение DI
* Внедрение Jetpack Compose
* Рефакторинг
* Внедрение viewBinding
