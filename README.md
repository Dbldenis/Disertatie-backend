# Aplicatie Disertație – MedFlow+ (Backend)

Acest proiect reprezintă componenta **backend** a aplicației realizate pentru lucrarea mea de disertație, dezvoltată în cadrul masteratului de **Informatică Medicală**.  
Scopul principal este gestionarea informațiilor medicale ale pacienților, programărilor și fișelor medicale, oferind o bază solidă pentru analiză și raportare ulterioară.

---

## Funcționalități principale

- 👤 Gestionarea datelor pacienților (CRUD complet)
- 🩺 Administrarea fișelor medicale 
- 📅 Crearea și modificarea programărilor medicale
- 🔐 Autentificare și autorizare utilizatori
- 🧩 Integrare cu frontend (React)

---

## Tehnologii utilizate

- **Java 17**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **pgAdmin**
- **Gradle**
- **Postman**

---

## Configurare și Instalare

### Prerequisite

- Java 17 sau superior
- PostgreSQL 12+ instalat și rulând
- Gradle (inclus prin wrapper)

### Pași de instalare

1. **Clonează repository-ul:**
   ```bash
   git clone https://github.com/Dbldenis/disertatie.git
   cd disertatie
   ```

2. **Configurează baza de date:**
   - Creează o bază de date PostgreSQL numită `Disertatie`
   - Setează utilizatorul și parola pentru PostgreSQL

3. **Configurează variabilele de mediu:**
   
   Setează următoarele variabile de mediu înainte de a rula aplicația:
   
   ```bash
   export DB_USERNAME=postgres
   export DB_PASSWORD=parola_ta_db
   export MAIL_USERNAME=email_tau@gmail.com
   export MAIL_PASSWORD=parola_aplicatie_gmail
   ```
   
   Sau creează un fișier `application-local.properties` în `src/main/resources/` cu:
   ```properties
   spring.datasource.username=postgres
   spring.datasource.password=parola_ta_db
   spring.mail.username=email_tau@gmail.com
   spring.mail.password=parola_aplicatie_gmail
   ```

4. **Construiește și rulează aplicația:**
   ```bash
   ./gradlew build
   ./gradlew bootRun
   ```

5. **Accesează aplicația:**
   - Backend API: `http://localhost:9090/api`

### Note importante

- **Nu comite niciodată credențialele reale** în repository
- Fișierul `application.properties.example` conține un template pentru configurație
- Pentru Gmail, folosește [App Passwords](https://support.google.com/accounts/answer/185833) în loc de parola ta principală

---

## Licență

Acest proiect este licențiat sub licența MIT - vezi fișierul [LICENSE](LICENSE) pentru detalii.
