package Serialization;

import java.io.*;

// TODO: Topic: @Serial annotation

/**
 * It is purely a documentation and compile-time validation tool — it does not change runtime behavior at all.
 * These are the only six serialization-related members @Serial is valid on.
 *
 * <p>
 *
 * Before @Serial, it was very easy to accidentally write a serialization method with the
 * wrong signature and have it silently ignored by the JVM with no error or warning:
 */


public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;  

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }  

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }  

    @Serial
    private Object readResolve() {
        return this;
    }  

    @Serial
    private Object writeReplace() {
        return this;
    }  

    @Serial
    private void readObjectNoData() throws ObjectStreamException {
    }  
}
