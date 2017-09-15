import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Created by dmaslov on 15/09/2017.
 */
public class Consumer implements Runnable {
    private List<String> buffer;
    private final Exchanger<List<String>> exchanger;


    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {

        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int cycle = 1; cycle <= 10; cycle++) {
            System.out.printf("Consumer: Cycle %d \n", cycle);
            try {
                buffer = exchanger.exchange(buffer);

                System.out.println("Ух... Просрались....");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Consumer: %d \n", buffer.size());
            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.println("Consumer: " + message);
                buffer.remove(0);
            }
        }
    }
}
