package server.accept;

import server.all.All;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Read extends Thread{
    private SocketChannel client;
    public SocketChannel getClient(){
        return client;
    }

    public void setClient(SocketChannel client) {
        this.client = client;
    }

    private All all;

    public All getAll() {
        return all;
    }

    public void setAll(All all) {
        this.all = all;
    }
    private int action;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Read(SocketChannel socketChannel, int action){
        this.client = socketChannel;
        this.action = action;
    }

    @Override
    public void run() {
        try {
            if (client.isOpen()) {
                ByteBuffer buffer = ByteBuffer.allocate(1000000);
                client.read(buffer);
                ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
                all = (All) in.readObject();
                action = 1;
            } else {
                System.out.println("client is closed");
            }
        }catch(IOException | ClassNotFoundException e){
        }
    }
}
