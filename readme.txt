Hotel Management System

Opis:
Hotel Management System to tekstowy system zarządzania hotelem, napisany w Javie i oparty na strukturze projektu Maven. System umożliwia zarządzanie pokojami hotelowymi, rejestrowanie i wymeldowywanie gości, przeglądanie szczegółowych informacji o pokojach oraz zapisywanie/odczytywanie danych do pliku CSV.

Funkcjonalność, komendy do zarządzania hotelem:
Ceny Pokoi (prices): Wyświetla wszystkie pokoje wraz z cenami za noc.
Szczegóły Pokoju (view): Umożliwia przeglądanie szczegółowych informacji o pokoju, w tym dane gości i status zajętości. W przypadku nieprawidłowego numeru pokoju wyświetlany jest komunikat błędu.
Zameldowanie (checkin): Rejestruje gości w wybranym pokoju, sprawdza dostępność i zapisuje szczegóły pobytu, takie jak data zameldowania i lista gości.
Wymeldowanie (checkout): Wymeldowuje gości i oblicza należność na podstawie daty zameldowania i daty wymeldowania. W przypadku nieprawidłowego numeru pokoju lub gdy pokój jest wolny, wyświetlany jest komunikat błędu.
Lista Pokoi (list): Wyświetla wszystkie pokoje wraz z informacjami o zajętości oraz dane gości, jeśli są obecni.
Zapis Danych (save): Zapisuje bieżący stan hotelu do pliku CSV.
Wyjście (exit): Zamyka program.


Struktura Projektu:

Projekt składa się z kilku modułów i jest podzielony na pakiety w sposób ułatwiający rozwój oraz utrzymanie:
main: Główna aplikacja hotelowa
src/main/java/pl.edu.agh.kis.pz1/commands: Pakiet zawierający klasy komend (CheckinCommand, CheckoutCommand, ListCommand, itp.), które realizują różne funkcjonalności systemu.
src/main/java/pl.edu.agh.kis.pz1/model: Pakiet zawierający klasy modelujące strukturę hotelu, takie jak Guest, Hotel, Reservation, Room itp.
src/main/java/pl.edu.agh.kis.pz1: klasy obsługujące operacje na danych, np. CsvReader, CsvWriter oraz klasa Main.
src/main/resources: Katalog z plikami zasobów, np. hotel-data.csv.
utils: Moduł zawierający pomocnicze klasy, w tym implementację struktury danych MyMap.
src/main/java: Kod źródłowy modułu utils.


Instalacja i Uruchomienie:

Klonowanie Repozytorium:
git clone <URL_repozytorium>

Budowanie Projektu: Wymagane jest zainstalowanie Javy 17 oraz Maven.
mvn clean install

Uruchomienie Aplikacji: Przejdź do katalogu głównego projektu i uruchom aplikację:
java -jar main/target/main-1.0-SNAPSHOT.jar


Raport SonarQube
Projekt został przeanalizowany przy użyciu SonarQube, uzyskując pozytywny wynik jakości kodu.

Pokrycie testami: 92.4%
Problemy: 0 otwartych problemów dotyczących bezpieczeństwa, niezawodności i utrzymywalności.

Technologie
Język: Java 17
System Budowania: Maven
Format Danych: CSV