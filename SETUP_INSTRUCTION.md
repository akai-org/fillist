# Setup Instruction
## Backend
### Prerequisites
- Gradle 
- Java 17
- Kotlin
### Setup
#### Spotify
1. Sign in to your [Spotify Dashboard](https://developer.spotify.com/dashboard/)
2. Create new app
3. Add to redirect url `http://localhost:4200/callback`
4. Save Client ID and Client Secret
#### application.yml - main
1. Go to `backend/src/main/resources/application.yml` directory
2. Copy file `application.yml` as `application-dev.yml`
3. Change properties:
   - `fillist.oauth2.client.registration.spotify.client-id` - Client ID from Spotify Dashboard
   - `fillist.oauth2.client.registration.spotify.client-secret` - Client Secret from Spotify Dashboard
   - `fillist.allow-origin` - `http://localhost:4200`
   - `fillist.oauth2.jwt-secret` - random string
#### application.yml - test
1. Go to `backend/src/test/resources/application.yml` directory
2. Copy file `application.yml` as `application-dev.yml`
3. Change properties the same as in `application-dev.yml` in `main` directory
4. TODO SCRIPT FOR GETTING REFRESH TOKEN

**IMPORTANT - to use new created `application-dev.yml` file, you need to set spring profile to `dev` (e.g. in IntelliJ IDEA: `Edit Configurations...` -> `Environment variables` -> `SPRING_PROFILES_ACTIVE=dev`)**

#### Run
1. Go to `backend` directory
2. Run `gradle bootRun --args='--spring.profiles.active=dev'`
