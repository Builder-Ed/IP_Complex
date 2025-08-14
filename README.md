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
To connect to the server, you have to choose a clientID to send to the server, which you will use to fetch IPs under the same clientID.

To do so, you may run
```
java -jar IP_Complex.jar client <address> <clientID>
```
or
```
java -jar IP_Complex.jar client <address> <port> <clientID>
```



