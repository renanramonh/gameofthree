package com.gameofthree.repository;

import com.gameofthree.model.Player;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import javax.websocket.Session;

import static org.junit.jupiter.api.Assertions.*;

class PlayerRepositoryTest {
    private static PlayerRepository repository;

    @Mock
    Session session;

    @BeforeAll
    static void beforeAll() {
        repository = new PlayerRepository();
    }

    @AfterEach
    void tearDown() {
        repository.delete("foo");
        repository.delete("bar");
    }

    @Test
    void add() {
        repository.add(new Player("foo", session));
        repository.add(new Player("bar", session));

        assertEquals(2, repository.getAll().size());
    }

    @Test
    void delete() {
        repository.add(new Player("foo", session));
        repository.add(new Player("bar", session));
        repository.delete("bar");

        assertEquals(1, repository.getAll().size());
    }

    @Test
    void getAll() {
        repository.add(new Player("bar", session));
        assertEquals(1, repository.getAll().size());
    }
}
