
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author pprun
 */
public class Oome {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //spawnTooManyThread();

        //allocateTooMany();

        //tooManyInternString();

        infinitelyMalloc();
    }

    private static void spawnTooManyThread() {
        while (true) {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e) {
                    }
                }
            }).start();
        }
    }

    private static void allocateTooMany() {
        List<ByteBuffer> list = new ArrayList<ByteBuffer>();
        while (true) {
            list.add(ByteBuffer.allocateDirect(1024 * 1024));
        }
    }

    private static void tooManyInternString() {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (true) {
            list.add(("too many intern String!" + (i++)).intern());
        }
    }

    private static void infinitelyMalloc() {
        List<String> list = new ArrayList<>();
        while (true) {
            list.add("I'm head to OOME!");
        }
    }
}
