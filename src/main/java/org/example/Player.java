package org.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {
    private String username;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Player(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }
}

// TODO Representerar en ansluten spelare; lagrar användarnamn och kommunikationsströmmar.
