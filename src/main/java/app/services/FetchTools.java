package app.services;

import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.DiscoverMovieDTO;
import app.dtos.GenreDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FetchTools {
    String apiKey = System.getenv("API_KEY");
    String baseUrl = "https://api.themoviedb.org/3";


    public List<DiscoverMovieDTO> getAllDanishMovies() {
        System.out.println("Starting parallel fetch...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        HttpClient client = HttpClient.newHttpClient();
        List<DiscoverMovieDTO> allPages = new ArrayList<>();

        try {
            // --- Første request for at finde total_pages ---
            String firstUrl = baseUrl + "/discover/movie?include_adult=false&include_video=false&language=en-US" +
                    "&release_date.gte=2020-01-01&release_date.lte=2025-09-16" +
                    "&sort_by=popularity.desc&with_original_language=da" +
                    "&api_key=" + apiKey +
                    "&vote_average.gte=0.0&vote_average.lte=10.0&page=1" +
                    "&sort_by=release_date.desc";

            HttpResponse<String> firstResponse = client.send(
                    HttpRequest.newBuilder().uri(new URI(firstUrl)).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            if (firstResponse.statusCode() != 200) {
                throw new RuntimeException("Initial request failed: " + firstResponse.statusCode());
            }

            JsonNode rootNode = objectMapper.readTree(firstResponse.body());
            int totalPages = rootNode.path("total_pages").asInt();

            // --- Callable for sider ---
            List<Callable<DiscoverMovieDTO>> tasks = new ArrayList<>();
            for (int page = 1; page <= totalPages; page++) {
                final int currentPage = page;
                tasks.add(() -> {
                    String url = baseUrl + "/discover/movie?include_adult=false&include_video=false&language=en-US" +
                            "&release_date.gte=2020-01-01&release_date.lte=2025-09-16" +
                            "&sort_by=popularity.desc&with_original_language=da" +
                            "&api_key=" + apiKey +
                            "&vote_average.gte=0.0&vote_average.lte=10.0&page=" + currentPage +
                            "&sort_by=release_date.desc";

                    HttpResponse<String> response = client.send(
                            HttpRequest.newBuilder().uri(new URI(url)).GET().build(),
                            HttpResponse.BodyHandlers.ofString()
                    );

                    if (response.statusCode() == 200) {
                        return objectMapper.readValue(response.body(), DiscoverMovieDTO.class);
                    } else {
                        System.err.println("Page " + currentPage + " failed with status: " + response.statusCode());
                        return null;
                    }
                });
            }

            // --- Kør alle tasks i parallel ---
            int cores = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(cores);
            List<Future<DiscoverMovieDTO>> futures = executor.invokeAll(tasks);

            for (Future<DiscoverMovieDTO> f : futures) {
                try {
                    DiscoverMovieDTO dto = f.get();
                    if (dto != null) allPages.add(dto);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allPages;
    }

    public List<DirectorDTO.Director> getDirectorsForMovieByMovieId(Integer movieId) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<DirectorDTO.Director> directors = new ArrayList<>();

        try {

            String url = baseUrl + "/movie/" + movieId + "/credits?language=en-US&api_key=" + apiKey;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Failed request, status: " + response.statusCode());
                return directors;
            }

            DirectorDTO directorDTO = objectMapper.readValue(response.body(), DirectorDTO.class);

            List<DirectorDTO.Director> directorList = directorDTO.getDirectors();

            for (DirectorDTO.Director director : directorList) {
                if (director.getDepartment().equalsIgnoreCase("Directing")) directors.add(director);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return directors;

    }

    public List<ActorDTO.Actor> getActorsForMovieByMovieId(Integer movieId) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<ActorDTO.Actor> actors = new ArrayList<>();

        try {

            String url = baseUrl + "/movie/" + movieId + "/credits?language=en-US&api_key=" + apiKey;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Failed request, status: " + response.statusCode());
                return actors;
            }

            ActorDTO actorDTO = objectMapper.readValue(response.body(), ActorDTO.class);

            List<ActorDTO.Actor> actorList = actorDTO.getActors();


            for (ActorDTO.Actor actor : actorList) {
                actors.add(actor);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return actors;

    }

    public List<GenreDTO.Genre> getGenresForMovieByMovieId(Integer movieId) {
        //System.out.println("Starting genres for movie by movieid: " + movieId + " fetch..." );

        ObjectMapper objectMapper = new ObjectMapper();
        List<GenreDTO.Genre> genres = new ArrayList<>();

        try {
            String url = baseUrl + "/movie/" + movieId + "?language=en-US&api_key=" + apiKey;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Failed request, status: " + response.statusCode());
                return genres;
            }

            GenreDTO dto = objectMapper.readValue(response.body(), GenreDTO.class);

            if (dto.getGenres() != null) {
                genres.addAll(dto.getGenres());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }
}