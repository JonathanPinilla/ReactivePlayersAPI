package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

public class ChangeNameUseCase implements BiFunction<String, String, Mono<PlayerDTO>> {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public ChangeNameUseCase(PlayerRepository playerRepository, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<PlayerDTO> apply(String id, String name) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(player -> {
                    player.setName(name);
                    return player;
                })
                .flatMap(playerRepository::save)
                .map(this::toDto);
    }

    private PlayerDTO toDto(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }
}
