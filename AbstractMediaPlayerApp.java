abstract public class AbstractMediaPlayerApp {
    protected AbstractMediaPlayerApp(){
        initialize();
        start();
    }

    protected abstract void initialize();
    protected abstract void start();
}
