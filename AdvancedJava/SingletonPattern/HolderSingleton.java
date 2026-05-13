package SingletonPattern;

public class HolderSingleton {

    private HolderSingleton() {}

    // This class is not loaded when HolderSingleton is loaded
    // It is only loaded when getInstance() is called for the first time
    private static class Holder {
        private static final HolderSingleton INSTANCE = new HolderSingleton();
    }

    public static HolderSingleton getInstance() {
        return Holder.INSTANCE; // triggers Holder class loading here
    }
}
