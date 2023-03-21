package com.sofkau.player.usecases;

import com.sofkau.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class DeleteStudentUseCase implements Function<String, Mono<Void>> {

    private final PlayerRepository playerRepository;

    public DeleteStudentUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public Mono<Void> apply(String id) {
        return playerRepository.deleteById(id);
    }
}
