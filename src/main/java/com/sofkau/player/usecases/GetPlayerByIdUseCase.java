package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class GetPlayerByIdUseCase implements Function<String, Mono<PlayerDTO>> {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public GetPlayerByIdUseCase(PlayerRepository playerRepository, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Mono<PlayerDTO> apply(String id) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    private PlayerDTO toDto(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }
}
