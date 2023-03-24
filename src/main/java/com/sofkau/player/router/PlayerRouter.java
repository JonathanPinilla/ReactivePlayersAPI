package com.sofkau.player.router;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PlayerRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllPlayers(GetAllPlayersUseCase getAllPlayersUseCase) {
        return route(GET("/api/player"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(getAllPlayersUseCase.get(), Player.class)
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getPlayerById(GetPlayerByIdUseCase getPlayerByIdUseCase) {
        return route(GET("/api/player/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(getPlayerByIdUseCase.apply(request.pathVariable("id")), Player.class)
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> savePlayer(SavePlayerUseCase savePlayerUseCase) {
        return route(POST("/api/player").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(PlayerDTO.class)
                        .flatMap(playerDTO -> savePlayerUseCase.apply(playerDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> updatePlayer(ChangeNameUseCase changeNameUseCase) {
        return route(PUT("/api/player/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(PlayerDTO.class)
                        .flatMap(playerDTO -> changeNameUseCase.apply(request.pathVariable("id"), playerDTO.getName())
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> deletePlayer(DeletePlayerUseCase deletePlayerUseCase) {
        return route(DELETE("/api/player/{id}"),
                request -> deletePlayerUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Player deleted successfully"))
                        .onErrorResume(error -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Collections.singletonMap("error", error.getMessage()))
                        ));
    }

}
