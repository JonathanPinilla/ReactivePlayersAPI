package com.sofkau.player.usecases;

import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.domain.external.Armor;
import com.sofkau.player.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class EquipArmorUseCase implements BiFunction<Armor, String, Mono<PlayerDTO>> {

    private final ModelMapper modelMapper;
    private final PlayerRepository playerRepository;

    public EquipArmorUseCase(ModelMapper modelMapper, PlayerRepository playerRepository) {
        this.modelMapper = modelMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<PlayerDTO> apply(Armor armor, String playerId) {
        return this.playerRepository.findById(playerId)
                .map(player -> {
                    player.updateArmor(armor);
                    return player;
                })
                .flatMap(playerRepository::save)
                .map(player -> modelMapper.map(player, PlayerDTO.class));
    }
}
