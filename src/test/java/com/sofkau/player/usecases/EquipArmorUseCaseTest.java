package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
import com.sofkau.player.domain.external.Armor;
import com.sofkau.player.domain.external.ArmorType;
import com.sofkau.player.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EquipArmorUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;
    private EquipArmorUseCase equipArmorUseCase;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup(){
        modelMapper = new ModelMapper();
        equipArmorUseCase = new EquipArmorUseCase(modelMapper, playerRepository);
    }

    @Test
    public void successfulEquipArmor(){

        //Arrange
        Armor armor = new Armor();
        armor.setId("1");
        armor.setArmorType(ArmorType.CHEST);
        armor.setArmorFamily("family");
        armor.setArmor(10.0);
        armor.setDamage(5.0);
        armor.setHealth(100.0);
        armor.setMana(50.0);
        armor.setSpeed(20.0);

        Player player = new Player();
        player.setId("1");
        player.setName("Juan");
        player.setRaze("raze");
        player.setArmors(new ArrayList<>());

        Player player2 = new Player();
        player2.setId("1");
        player2.setName("Juan");
        player2.setRaze("raze");
        player2.setArmors(new ArrayList<>());


        Mockito.when(playerRepository.findById("1")).thenReturn(Mono.just(player));
        Mockito.when(playerRepository.save(player)).thenReturn(Mono.just(player));

        player2.updateArmor(armor);
        PlayerDTO expectedResult = toDto(player2);

        //Act
        var response = equipArmorUseCase.apply(armor, "1");

        //Assert
        StepVerifier.create(response)
                .expectNext(expectedResult)
                .verifyComplete();
    }

    PlayerDTO toDto(Player player){
        return modelMapper.map(player, PlayerDTO.class);
    }

}