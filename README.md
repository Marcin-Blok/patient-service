# Przykład użycia metody POST - dodanie pacjenta(dane pacjenta zawarte w pliku example.json):  
curl -d @example.json -H "Content-Type: application/json" -X POST http://localhost:8080/patient
