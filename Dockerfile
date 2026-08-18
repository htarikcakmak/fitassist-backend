# 1. Aşama: Maven ile projeyi derleme (Build)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Proje dosyalarını sunucuya kopyalıyoruz
COPY pom.xml .
COPY src ./src

# Kodu derleyip .jar dosyasını oluşturuyoruz
RUN mvn clean package -DskipTests

# 2. Aşama: Uygulamayı çalıştırma (Run)
FROM eclipse-temurin:21-jre
WORKDIR /app

# İlk aşamada üretilen jar dosyasını alıp ismini app.jar olarak değiştiriyoruz
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot'un dinleyeceği portu dışarıya açıyoruz
EXPOSE 8080

# Uygulamayı başlatıyoruz
ENTRYPOINT ["java", "-jar", "app.jar"]