# --- Faza 1: Budowanie (Build Stage) ---
# Używamy oficjalnego obrazu Maven (który ma Javę) do zbudowania aplikacji
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Ustawiamy katalog roboczy wewnątrz kontenera
WORKDIR /build

# Kopiujemy tylko plik pom.xml, aby pobrać zależności
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiujemy resztę kodu źródłowego
COPY src ./src

# Uruchamiamy build Mavena (tworzy plik .jar)
# Używamy -DskipTests, aby przyspieszyć build na serwerze
RUN mvn clean package -DskipTests

# --- Faza 2: Uruchomienie (Run Stage) ---
# Używamy lekkiego, bezpiecznego obrazu Javy (Java Runtime)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# BARDZO WAŻNE: Skopiuj plik .jar ze sceny budowania (build stage)
# Sprawdź w swoim folderze /target/, czy nazwa pliku .jar się zgadza!
COPY --from=build /build/target/fitmate-0.0.1-SNAPSHOT.jar app.jar

# Ustawiamy port, na którym nasłuchuje Spring Boot
EXPOSE 8080

# Komenda uruchamiająca aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]
