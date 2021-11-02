### Проект тестов для UI Cucumber

#### Сборка docker контейнера
Перейти в директорию проекта и выполнить команду

`docker build -t ui-bdd-tests .`

#### Запуск тестов
Windows

`docker run -e bsUser='Username' -e bsKey='AccessKey' -it --rm -v pathTo\build\allure-results:/home/gradle/build/allure-results ui-bdd-tests`

Unix

`docker run -e bsUser='Username' -e bsKey='AccessKey' -it --rm -v pathT/build/allure-results:/home/gradle/build/allure-results ui-bdd-tests`

#### Генерация отчета
Windows

`allure serve build\allure-results`

Unix

`allure serve build/allure-results`


