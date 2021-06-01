package com.gameofthree.repository;

import com.gameofthree.model.Player;

import java.util.HashMap;

public class PlayerRepository {
    private static final HashMap<String, Player> players = new HashMap<>();

    public synchronized void add(Player player) {
        players.put(player.getId(), player);
    }

    public synchronized void delete(String id) {
        players.remove(id);
    }

    public HashMap<String, Player> getAll() {
        return players;
    }
}
