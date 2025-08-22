package com.jule.studentmanagement;

import com.jule.studentmanagement.infrastructure.dto.ExternalPostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
public class ExternalRestService {

    private final WebClient webClient;
    private final String jsonPlaceholderUrl;

    public ExternalRestService(WebClient.Builder webClientBuilder,
                               @Value("${external.api.jsonplaceholder.url}") String jsonPlaceholderUrl) {
        this.webClient = webClientBuilder.baseUrl(jsonPlaceholderUrl).build();
        this.jsonPlaceholderUrl = jsonPlaceholderUrl;
    }

    public ExternalPostDTO getPostById(Long postId) {
        try {
            log.debug("Obteniendo post con ID: {}", postId);

            ExternalPostDTO post = webClient.get()
                    .uri("/posts/{id}", postId)
                    .retrieve()
                    .bodyToMono(ExternalPostDTO.class)
                    .block();

            log.debug("Post obtenido: {}", post);
            return post;

        } catch (Exception e) {
            log.error("Error al obtener post con ID {}: {}", postId, e.getMessage());
            return null;
        }
    }

    public ExternalPostDTO[] getAllPosts() {
        try {
            log.debug("Obteniendo todos los posts");

            ExternalPostDTO[] posts = webClient.get()
                    .uri("/posts")
                    .retrieve()
                    .bodyToMono(ExternalPostDTO[].class)
                    .block();

            log.debug("Posts obtenidos: {} elementos", posts != null ? posts.length : 0);
            return posts;

        } catch (Exception e) {
            log.error("Error al obtener posts: {}", e.getMessage());
            return new ExternalPostDTO[0];
        }
    }
}
