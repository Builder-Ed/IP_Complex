# IP_Complex

IP_Complex is an implementation targeted at producing portable IP synchronization services written in Java, allowing clients to log their IP on a deployed server with a specific client ID and retrieve recorded ones with the latter.

# Documentation

The current version only supports recording client IP on the server side, further documentation will be released when full functions are implemented.

## Building IP_Complex

### Dependencies

IP_Complex is built with [openjdk](https://openjdk.org/projects/jdk/), which is the only prequisite required.

### Building from source

To build the project, you may use the compile file enclosed, which will compile the project and pack the binary into [Build]IP_Complex.jar, outputted into the ./builds/build/ folder.

To do so, just simply execute:

```
./compile
```
## Usage

### Server
To start up a server, you may run:

```
java -jar IP_Complex.jar server <port>
```

If you have already a log which contains IPs of clients, you may also add the directory of the file to load them to the server using:

```
java -jar IP_Complex.jar server <port> <filepath>
```
### Client
To connect to the server, you have to choose a clientID to inform to the server, which you will use to fetch/append/delete IPs under the same clientID.

There are three valid options you may choose on the client side, fetch/append/delete.
Say now we want to add an IP to the server listening a specific port, we can do:

```
java -jar IP_Complex.jar client append <address> <port> <clientID> <IP>
```
You may also replace IP with "CURRENT" to specify the current IP.

For a clientID, multiple IPs can be added.
To fetch the IP(s) we have appended, we can run
```
java -jar IP_Complex.jar client fetch <address> <port> <clientID>
```
This should list all IPs appended under this clientID.

Deleting accounts is similar to appending, replace the "append" parameter with "delete" should do the job.



