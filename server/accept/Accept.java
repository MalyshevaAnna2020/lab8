package server.accept;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Accept extends Thread{
    private final SocketChannel client;

    public Accept(SocketChannel socketChannel){
        this.client = socketChannel;
    }

    @Override
    public void run() {
        try {
            ExecutorService executorRead = Executors.newCachedThreadPool();
            ExecutorService executorWork = Executors.newCachedThreadPool();
            ExecutorService executorWrite = Executors.newCachedThreadPool();
            Thread thread_read;
            Thread thread_work;
            Thread thread_write;

            Action action = new Action(client);

            while(true) {
                if (action.getAction() == 0) {
                    thread_read = new Thread(action::read);
                    executorRead.submit(thread_read).get();
                }else{
                    break;
                }

                if (action.getAction() == 1) {
                    thread_work = new Thread(action::work);
                    executorWork.submit(thread_work).get();
                }else{
                    break;
                }

                if (action.getAction() == 2) {
                    thread_write = new Thread(action::write);
                    executorWrite.submit(thread_write).get();
                }else{
                    break;
                }
            }
        }
        catch (ClassCastException | NullPointerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
