/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A demo for Java GC Tuning.
 * <p>
 * - sleep 1~100 ms
 * - run 90 s
 * - long-term-object / short-term-object = 1/3
 * </p>
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class GcDemoLowLoad extends Thread {

    public static void main(String[] args) {
        new GcDemoLowLoad().start();
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        long then = start;

        while (true) {

            // sleep random delay
            try {
                int delay = (int) Math.round(100 * Math.random());
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }

            // create random number of objects
            int count = (int) Math.round(Math.random() * 10 * 1000);
            for (int i = 0; i < count; i++) {
                if (i%3 == 0) {
                    new LongTermObject();
                } else {
                    new ShortTermObject();
                }
            }

            // log stats to console
            long now = System.currentTimeMillis();
            if (now - then > 1000) {
                System.out.println(
                        ((now - start) / 1000) + " s\t"
                        + LongTermObject.created + " created\t"
                        + LongTermObject.freed + " freed");
                then = now;
            }

            if (now - start > 90 * 1000) { // 90 s
                return;
            }
        }
    }

    static class LongTermObject {

        static long created;
        static long freed;
        static byte[] buf = new byte[8 * 1024];

        public LongTermObject() {
            created++;
            buf[0] = (byte)1;
            buf[buf.length -1] = (byte)1;
        }

        @Override
        public void finalize() {
            freed++;
        }
    }
    
    static class ShortTermObject {
        String s = "s";
        
        public ShortTermObject() {
        }
    }
}
