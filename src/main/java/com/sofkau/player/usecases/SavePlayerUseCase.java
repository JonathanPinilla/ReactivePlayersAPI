package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class SavePlayerUseCase implements Function<PlayerDTO, Mono<PlayerDTO>> {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public SavePlayerUseCase(PlayerRepository playerRepository, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<PlayerDTO> apply(PlayerDTO playerDTO) {
        return playerRepository.save(toEntity(playerDTO))
                .map(this::toDto)
                .onErrorResume(Mono::error);
    }

    private Player toEntity(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }

    private PlayerDTO toDto(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

}
