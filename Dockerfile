# Используем официальный JDK 17 образ
FROM eclipse-temurin:22-jdk

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем скомпилированный JAR-файл из `build/libs/` в контейнер
COPY build/libs/*.jar app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Запускаем Spring Boot приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
