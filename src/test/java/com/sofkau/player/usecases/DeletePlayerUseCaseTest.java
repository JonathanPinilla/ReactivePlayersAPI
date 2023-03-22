package com.sofkau.player.usecases;

import com.sofkau.player.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeletePlayerUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;
    private DeletePlayerUseCase deletePlayerUseCase;

    @BeforeEach
    public void setup() {
        deletePlayerUseCase = new DeletePlayerUseCase(playerRepository);
    }

    @Test
    public void successfulDeletePlayer() {
        //Arrange
        var id = "1";

        //Mock
        Mockito.when(playerRepository.deleteById(id)).thenReturn(Mono.empty());

        //Act
        var result = deletePlayerUseCase.apply(id);

        //Assert
        StepVerifier.create(result)
                .expectSubscription()
                .verifyComplete();
    }

}