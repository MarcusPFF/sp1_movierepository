package app.services;

import app.dtos.DiscoverMovieDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

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

            // --- Callable for sidder ---
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
//    public List<DiscoverMovieDTO> getAllDanishMovies() {
//        System.out.println("Starting fetch...");
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        List<DiscoverMovieDTO> discoverMovieDTOS = new ArrayList<>();
//        int page = 1;
//        boolean hasMore = true;
//
//        try {
//            while (hasMore) {
//                String url = baseUrl + "/discover/movie?include_adult=false&include_video=false&language=en-" +
//                        "US&page=1&release_date.gte=2020-01-01&release_date.lte=2025-09-16&sort_by=popularity.desc&" +
//                        "with_original_language=da&api_key=" + apiKey +
//                        "&vote_average.gte=0.0&vote_average.lte=10.0&page=" + page +
//                        "&sort_by=release_date.desc";
//
//                HttpClient client = HttpClient.newHttpClient();
//
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(new URI(url))
//                        .GET()
//                        .build();
//
//                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//                JsonNode rootNode = objectMapper.readTree(response.body());
//
//                if (response.statusCode() == 200) {
//                    DiscoverMovieDTO discoverMovieDTO = objectMapper.readValue(response.body(), DiscoverMovieDTO.class);
//                    discoverMovieDTOS.add(discoverMovieDTO);
//
//                    if (page >= rootNode.path("total_pages").asInt()) {
//                        hasMore = false;
//                    } else {
//                        page++;
//                    }
//                } else {
//                    System.out.println("Failed request, status: " + response.statusCode());
//                    hasMore = false;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return discoverMovieDTOS;
//    }
}