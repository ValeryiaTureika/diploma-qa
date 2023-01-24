# Дипломный проект по профессии «Инженер по тестированию»

## Описание проекта 

В рамках данного проекта поставлены задачи, такие как:
- проведение ручного тестирования мобильного приложения «Мобильный хоспис»;
- составление чек-листа для проверки приложения;
- составление тест-кейсов для проверки приложения;
- автоматизация проверки тест-кейсов по чек-листу;
- составление отчётов о тестировании. 

![](https://github.com/ller4ik/diploma-qa/blob/master/reports/pictures/app.png)

Приложение предоставляет функционал по работе с претензиями хосписа и включает в себя:
- информацию о претензиях и функционал для работы с ними;
- новостную сводку хосписа;
- тематические цитаты.

## Документация 

Вся документация по проекту находится в папке [reports](reports).

## Запуск приложения 

### Предусловия

1. Требуется изучить пречень используемых инструментов, описаных в [плане автоматизации тестирования](https://github.com/ller4ik/diploma-qa/blob/master/reports/Plan.md). Обеспечить их дальнейшее использование в проекте; 
2. Клонировать [репозиторий](https://github.com/ller4ik/diploma-qa) командой;
> git clone
3. Открыть проект fmh-android в Android Studio;

### Запуск 

1. Запустить приложение на эмуляторе API 29;
2. Во вкладке Project выделить каталог app.

### Запуск тестов 

1. Запустить тесты - во вкладке Run нажать Run all tests.
2. Подождать пока пройдут все тесты и посмотреть результат.

### Формирование отчета AllureReport по результатам тестирования

1. Выгрузить каталог /data/data/ru.iteco.fmhandroid/files/allure-results с эмулятора;
2. Выполнить команду allure serve, находясь на уровень выше allure-results.
3. Подождать генерации отчета и посмотреть его в открывшемся браузере.
