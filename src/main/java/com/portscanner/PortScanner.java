package com.portscanner;

 import java.io.IOException;
 import java.net.InetSocketAddress;
 import java.net.Socket;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.concurrent.ConcurrentLinkedQueue;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.concurrent.TimeUnit;
 import java.util.concurrent.atomic.AtomicInteger;


public class PortScanner {

    static int closedPorts = 0;
    
    public static void main(String[] args) {
        List openPorts = portScan(args[0]);
        openPorts.forEach(port -> System.out.println("Port: " + port + " is OPEN"));
    }
    
    public static List portScan(String ipAddress) {
        //thread safe FIFO linked list queue (First In First Out)
        ConcurrentLinkedQueue openPorts = new ConcurrentLinkedQueue<>();
        //this program will run in a parallelism of 1500
        ExecutorService executorService = Executors.newFixedThreadPool(1500); //1500 threads
        //thread safe mutable value -> volatile variable which gets read/written in memory to persist changes in this multi threaded environment
        //used to track current port i'm testing
        AtomicInteger port = new AtomicInteger(0);
        
        //running port scan on all 65535 ports of target machine
        while (port.get() < 65535) {
            //atomically increments by one the current value
            final int currentPort = port.getAndIncrement();
            //running this port scan concurrently on each iteration
            executorService.submit(() -> {
                //socket object is used to create a connection between two machines
                try (Socket socket = new Socket();) {
                    //creates a socket address from the ip and port and tries to establish connection to remote host on specified port
                    //with timeout of 200 ms
                    socket.connect(new InetSocketAddress(ipAddress, currentPort), 200);
                    //connection has been established (port is open) -> so open port is added to queue
                    //since it runs in parallel, open ports cannot be added to a regular list -> it needs to use a thread safe list in order to avoid race conditions
                    openPorts.add(currentPort);
                } catch (IOException e) {
                    //increment for everytime a connection to a port fails to establish (port is closed)
                    closedPorts += 1;
                }
            });
        }
        
        //in order to make this portScan method synchronized, the concurrent block needs to wait for the runnable task to be returned.
        //we achieve that by calling this shutdown method
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        List openPortList = new ArrayList<>();
        System.out.println("openPortsQueue: " + openPorts.size());
        System.out.println("closedPortsQueue: " + closedPorts);
        
        while (!openPorts.isEmpty()) {
            //remove and returns the head of this concurrentLinkedQueue (or null if it's empty)
            openPortList.add(openPorts.poll());
        }
        
        return openPortList;
    }
    
}
