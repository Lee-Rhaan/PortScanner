## Multi Threaded Port Scanner
|Java Core|Maven|Batch Scripting|
|---|---|---|
---
### What is a Port Scanner?

A Port Scanner is a tool that scans an IP in the local network by trying to access all ports of that machine.
It creates a new TCP socket and tries to connect to a specific port. 
If the connection is established - this port is marked as open, if not - it is marked as closed. 
> We repeat the same process for all the 65,535 possible port numbers.

### What are Port Scanners used for?

Security
> you might want to know which ports on your network are open, since every open port is a potential vulnerability in the system. 

Network Monitor
> There are many Network Monitor tools out there that use Port Scanners under the hood.

Application usage
> your application might need to be connected to different machines over TCP. For that, it needs to know which ports are available.

---
## Overview:

- I'm making use of Java's Socket class in order to establish communication between the machines.
- This port scanner determines the open/closed ports based of if a connection has been established or not.
- In order to provide sufficient performance, this Port Scanner is running in parallel - using the ExecutorService interface in Java which is an 
  asynchronous mechanism that allows you to run tasks simultaneously in the background.

---

## Additional Information:

### PortScanner JAR

- Contained in the target folder in this projects repo, you'll find an already packaged JAR file.
- Remove it and rebuild the project (enter this command in the project directory): mvn clean package 
> NOTE: I've added the JAR just for reference purposes.
> The same type of JAR file should appear once you've build the project successfuly.

### Batch Script

- Contained in this projects repo, you'll notice an already made batch script as well.
- Edit the script with notepad/notepad++
- Copy the location of the PortScanner JAR once it has been successfuly build.
- Place the path in the {place_holder) field and save the script.

> Now you're ready to go!!!

https://user-images.githubusercontent.com/81378094/199252504-c4a85492-4aaa-4f2b-9ccb-fc2713dc9894.mp4



