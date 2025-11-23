package org.example.server;

import org.example.Player;

import java.io.Serializable;

public class Package<T> implements Serializable {
    private TypeOfPackage type;
    private Player player;
    private T object;

    public Package(TypeOfPackage type, Player player, T object) {
        this.type = type;
        this.player = player;
        this.object = object;
    }
}

