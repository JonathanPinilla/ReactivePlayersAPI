package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.domain.dto.PlayerDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SavePlayerUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;
    private SavePlayerUseCase savePlayerUseCase;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        savePlayerUseCase = new SavePlayerUseCase(playerRepository, modelMapper);
    }

    @Test
    public void successfulSavePlayer() {
        //Arrange
        var id = "1";
        var name = "Juan";
        var raze = "Human";

        PlayerDTO player = new PlayerDTO();
        player.setId(id);
        player.setName(name);
        player.setRaze(raze);

        Player player2 = new Player();
        player2.setId(id);
        player2.setName(name);
        player2.setRaze(raze);

        //Mock
        Mockito.when(playerRepository.save(Mockito.any(Player.class))).thenReturn(Mono.just(player2));

        //Act
        var result = savePlayerUseCase.apply(player);

        //Assert
        StepVerifier.create(result)
                .expectSubscription()
                .expectNextMatches(playerDTO -> playerDTO.getName().equals(name))
                .verifyComplete();
    }
}