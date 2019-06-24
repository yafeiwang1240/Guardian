package com.githup.yafeiwang1240;

import com.githup.yafeiwang1240.obrien.uitls.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello World!
 */
public class App {

    static Process process;

    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
//            Ping("ping", "-t", "14.215.177.39");
//            Ping("java -jar D:/test.jar");
            WaitThread thread = new WaitThread();
            new Thread(thread).start();
            Thread.sleep(1000);
            synchronized (lock) {
                System.out.println("我想获得锁");
                lock.notify();
            }
            Thread.sleep(15000);
            synchronized (lock) {
                System.out.println("我想获得锁");
                lock.notify();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static class WaitThread implements Runnable {

        @Override
        public void run() {
            while(true) {
                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(i + ":" + "HelloWorld");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        lock.wait();
                        System.out.println("I am wait");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("I am really wait");
            }

        }
    }

    public static void Ping(String command) throws IOException, InterruptedException {
        process = Runtime.getRuntime().exec(command);
//        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        new Thread(new Reader(process.getErrorStream(), "error")).start();
        new Thread(new Reader(process.getInputStream(), "info")).start();

        System.out.println("退出："+ process.waitFor());
    }

    public static void end(String info) {
        if(process.isAlive()) {
            System.err.println(info + ": " + "******************isAlive***************");
        } else {
            System.out.println(info + ": " + "******************isEnd***************");
        }
        System.out.println("------------" + process.exitValue() + "------------");
    }

    public static class Reader implements Runnable {
        String info;
        InputStream inputStream;
        public Reader(InputStream inputStream, String info) {
            this.inputStream = inputStream;
            this.info = info;
        }
        @Override
        public void run() {
            BufferedReader bufferedReader;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String buffer;
                if(info.equals("info")) {
                    System.out.println("Start-------------------------");
                }
                while ((buffer = bufferedReader.readLine()) != null) {
                    System.out.println(info + ": " + buffer);
                }
                if(info.equals("info")) {
                    System.out.println("End-------------------------");
                }
                end(info);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(bufferedReader);
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(inputStreamReader);
            }
        }
    }
}
