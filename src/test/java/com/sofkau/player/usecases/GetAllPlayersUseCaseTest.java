package com.sofkau.player.usecases;

import com.sofkau.player.domain.collection.Player;
import com.sofkau.player.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllPlayersUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;
    private GetAllPlayersUseCase getAllPlayersUseCase;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        getAllPlayersUseCase = new GetAllPlayersUseCase(playerRepository, modelMapper);
    }

    @Test
    public void successfulGetAllPlayers() {
        //Arrange
        Player player = new Player();
        player.setId("1");
        player.setName("Juan");
        player.setRaze("Human");

        Player player2 = new Player();
        player2.setId("2");
        player2.setName("Pedro");
        player2.setRaze("Human");

        //Mock
        Mockito.when(playerRepository.findAll()).thenReturn(Flux.just(player, player2));

        //Act
        var result = getAllPlayersUseCase.get();

        //Assert
        StepVerifier.create(result)
                .expectSubscription()
                .expectNextMatches(playerDTO -> Objects.equals(playerDTO.getId(), player.getId()))
                .expectNextMatches(playerDTO2 -> Objects.equals(playerDTO2.getId(), player2.getId()))
                .verifyComplete();
    }

}