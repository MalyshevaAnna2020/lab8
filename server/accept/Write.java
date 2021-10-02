package server.accept;

import server.all.All;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Write extends Thread{

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

    public Write(SocketChannel socketChannel){
        this.client = socketChannel;
    }

    @Override
    public void run(){
        try {
            if (action == 2) {
                System.out.println("write");
                writeAll();
                action = 0;
            }
        } catch (IOException e) {
        }
    }
    public void writeAll() throws IOException {
        if (client.isOpen()) {
            // Запись ответа сервера клиенту
            ByteBuffer buffer = ByteBuffer.allocate(1000000);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(all);
            out.flush();
            byte[] b = bos.toByteArray();
            buffer.clear();
            for (byte value : b) {
                buffer.put(value);
            }

            buffer.flip();
            client.write(buffer);
            if (buffer.hasRemaining()) {
                buffer.compact();
            } else {
                buffer.clear();
            }
        }
    }
}
