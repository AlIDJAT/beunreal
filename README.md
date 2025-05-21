# BeUnreal

Une application de messagerie géolocalisée où les utilisateurs peuvent :
- S'inscrire et se connecter avec validation d'âge (16-35 ans)
- Envoyer des messages (texte + image) à un ami ou à un groupe
- Découvrir les messages des utilisateurs à proximité via GPS
- Recherche d’amis
- Authentification JWT (login/register)
- CRUD


## 📦 Fonctionnalités

- Authentification avec JWT
- Envoi de messages privés et de groupe
- Découverte de messages dans un rayon de 10 km
- Structure backend propre avec Spring Boot, PostgreSQL, Spring Security


## 🛠️ Technologies

- **Backend :** Java 21, Spring Boot 3, PostgreSQL
- **Sécurité :** Spring Security + JWT
- **Frontend :** Android (Kotlin, Jetpack Compose) — En cours
- **Build :** Maven
- **API Test :** Postman

## 📁 Structure du projet

```
com.beunreal
├── config
├── controller
├── dto
├── model
├── repository
└── service
```

## 🚀 Lancement local

```bash
git clone https://github.com/TonUsername/beunreal.git
cd beunreal
./mvnw spring-boot:run
```

## 📌 Auteurs

- Réalisé par Ali Djatou.
