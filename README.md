# The Movie Database Repo

## Om projektet
Dette projekt fungerer som et film-repositorium, der henter data fra [The Movie Database API](https://www.themoviedb.org/) og gemmer det i en lokal database.  
Det giver mulighed for at:

- **Hente og gemme filmdata** lokalt via Hibernate og JPA.
- **Søge og filtrere** film, genrer, instruktører og skuespillere.
- **Vise top 10-lister** baseret på popularitet eller vurdering.
- **Opbygge et fundament** til at arbejde med filmdata som en mini-udgave af IMDb.

Projektet er bygget i **Java**, bruger **Hibernate** til databasehåndtering og har et modulært design, der gør det nemt at udvide med nye forespørgsler og funktioner.

## Funktionalitet
- **Førstegangskørsel:** Hent og gem data fra The Movie Database i din lokale database.
- **Vis top 10-film:**
    - `showTop10HighestRatedMovies()` – de højest ratede film.
    - `showTop10LowestRatedMovies()` – de lavest ratede film.
    - `showTop10PopularMovies()` – de mest populære film.
- **Filtrer efter genre:**
    - `showMoviesByGenre("Romance")` – viser alle film i en bestemt genre.
- **Søg efter film:**
    - `searchMoviesByTitle("Batman")` – find film baseret på titel.
- **List alt indhold:**
    - `showAllMovies()` – alle film i databasen.
    - `showAllGenres()` – alle genrer.
    - `showAllDirectors()` – alle instruktører.
    - `showAllActors()` – alle skuespillere.

Denne funktionalitet gør det nemt at teste, udforske og udbygge din lokale film-database.

## Krav

For at køre projektet skal du have følgende installeret:

- **Java:** Version 17 eller nyere
- **Maven:** Til at håndtere afhængigheder og bygge projektet
- **Database:** PostgreSQL (eller kompatibel database, konfigureret i Hibernate)
- **Internetadgang:** For at hente data fra The Movie Database API
- **API-nøgle:** Personlig nøgle fra [The Movie Database](https://www.themoviedb.org/)

## Teknologier

- **Java 17** – Primært programmeringssprog
- **Hibernate (JPA)** – ORM til at håndtere databaseoperationer
- **PostgreSQL** – Database til lagring af filmdata
- **Jackson** – JSON-parsing af data fra API'et
- **Lombok** – Automatiseret generering af getters, setters, toString osv.
- **Maven** – Build- og dependency management

## Sådan kører du projektet
- Opret en ny database med navnet `movies`.
- Tilføj din egen API-nøgle i `Main`-konfigurationen.  
  Du kan få en API-nøgle ved at oprette en konto på [The Movie Database](https://www.themoviedb.org/).
- Kør denne metode først for at hente og gemme data i databasen:

```java
movieRepo.firstTimeRun();
```
- Derefter kan du kalde de andre udkommenteret metoder.

### Forfattere

- Jonathan Kudsk

- Marcus Forsberg