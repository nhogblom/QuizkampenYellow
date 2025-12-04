package org.example.shared;

import java.io.Serializable;


    public class Message implements Serializable {
        private final MessageTypes type;
        private final Object payload;

        public Message(MessageTypes type, Object payload) {
            this.type = type;
            this.payload = payload;
        }

        public MessageTypes getType() { return type; }
        public Object getPayload() { return payload; }

}
