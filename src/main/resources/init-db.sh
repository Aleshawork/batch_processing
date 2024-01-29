#!/bin/bash
# Для выполнения скрипта на Windows переключить Shell Path на C:\Program Files\Git\bin\bash.exe
# Открываем новый терминал в Ide
chmod +x
echo Очистка от предыдущих запусков
rm -rf changelog
docker-compose rm --stop --force
echo Формируем changelog
mkdir changelog
cp -vR liquibase changelog
echo Запуск локальной БД
docker-compose up -d
echo Лог выполнения liquibase скриптов
docker-compose logs --tail=25 -f liquibase
echo Удаляем changelog
rm -rf changelog
