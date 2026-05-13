package Serialization;

import java.io.*;

//TODO: Topic: readObject() and readResolve() of serialisation

/**
 *
 * When you define a custom readObject() method, you are essentially overriding the default deserialization behavior entirely.
 * The JVM hands you full control and does nothing automatically. So if you want the normal field restoration to happen,
 * you have to explicitly ask for it by calling defaultReadObject():
 *
 **/

public class Config implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Config INSTANCE = new Config();

    private String host;
    private int port;
    private transient String connectionString; // not serialized

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        ois.defaultReadObject(); // restores host and port from byte stream

        // validate data coming from byte stream
        if (port < 0 || port > 65535) {
            throw new InvalidObjectException("Invalid port: " + port);
        }

        // rebuild transient field that was not in byte stream
        // this runs on the newly created object from byte stream
        this.connectionString = host + ":" + port;

        System.out.println("readObject — host: " + host + ", port: " + port);
    }

    @Serial
    private Object readResolve() {
        // readObject already validated and enriched the deserialized object
        // but for singleton we discard it and return existing instance
        return INSTANCE;
        // the object readObject() ran on is now garbage collected
    }
}
