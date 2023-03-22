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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetPlayerByIdUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;
    private GetPlayerByIdUseCase getPlayerByIdUseCase;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        getPlayerByIdUseCase = new GetPlayerByIdUseCase(playerRepository, modelMapper);
    }

    @Test
    public void successfulGetPlayerById() {
        //Arrange
        Player player = new Player();
        player.setId("1");
        player.setName("Pedro");
        player.setRaze("Human");

        //Mock
        Mockito.when(playerRepository.findById("1")).thenReturn(Mono.just(player));

        //Act
        var result = getPlayerByIdUseCase.apply("1");

        //Assert
        StepVerifier.create(result)
                .expectNextMatches(playerDTO -> playerDTO.getName().equals("Pedro"))
                .verifyComplete();
    }

}