package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class GetAllPlayersUseCase implements Supplier<Flux<PlayerDTO>> {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public GetAllPlayersUseCase(PlayerRepository playerRepository, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Flux<PlayerDTO> get() {
        return playerRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
    }

    private PlayerDTO toDto(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }
}
