package org.example;

import java.io.Serializable;


    public class Message implements Serializable {
        private final MyMessageTypes type;
        private final Object payload;

        public Message(MyMessageTypes type, Object payload) {
            this.type = type;
            this.payload = payload;
        }

        public MyMessageTypes getType() { return type; }
        public Object getPayload() { return payload; }

}
