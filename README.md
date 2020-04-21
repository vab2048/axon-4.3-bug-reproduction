# axon-4.3-bug-reproduction

This repo exists to provide an example project in which an error occurs when attempting to use Axon 4.3 and
retrieve a `MultipleInstancesResponseType` query response. The exact same code worked fine in Axon 4.2.1. 

Environment description to reproduce the bug:
- Axon 4.3
- Axon Server 4.3.3 (probably would occur with other versions too)
- Java 14

N.B. you should be able to import this directly into IntelliJ and get it to work. If you face problems importing
please make sure the gradle JDK version matches the project JDK version (I have had issues with this before). 

To reproduce the bug:
1. Have a Postgres DB running with a database created called: `axon_bug_reproduction`.
    - Set the `spring.datasource.*` properties so that the application can connect to the DB.
2. Run Axon Server
3. Run this spring boot app
    - The app will execute `resources/schema.sql` to create the schema.
4. Go to `localhost:8080/swagger-ui.html` - this will present a UI for easily making HTTP requests to reproduce the bug.
5. Issue a GET `/getLatest` request.
    - Since no aggregates have been created this should return an empty list (expected behaviour).
6. Issue a POST `/create` request.
    - This should return a valid response. 
    - Take the orderId.id value e.g. d8a13c6d-b730-4daa-8799-f9b2bb2602b2
7. Issue a GET `/get/{orderAggregateId}` request.
    - Input the orderId.id value you got from step `6`.
    - This should work and return the expected result.
8. Issue a GET `/getLatest` request.
    - This is the same as step `5`, but now we actually have a value the request fails.
    - Expected behaviour: for the list containing the value to be returned.  
    - Actual behaviour: error returned: ```"Retrieved response [class java.util.ArrayList] is not convertible to a List of the expected response type [class com.example.axon.bug.query.model.OrderState]",``` 
    
An example stacktrace is shown below for reference:
    
```
      
2020-04-21 19:57:22.348 ERROR 8316 --- [nio-8080-exec-7] o.a.c.c.C.[.[.[.[dispatcherServlet]      : Servlet.service() for servlet [dispatcherServlet] threw exception

java.lang.IllegalArgumentException: Retrieved response [class java.util.ArrayList] is not convertible to a List of the expected response type [class com.example.axon.bug.query.model.OrderState]
	at org.axonframework.messaging.responsetypes.MultipleInstancesResponseType.convert(MultipleInstancesResponseType.java:113) ~[axon-messaging-4.3.jar:4.3]
	at org.axonframework.messaging.responsetypes.MultipleInstancesResponseType.convert(MultipleInstancesResponseType.java:44) ~[axon-messaging-4.3.jar:4.3]
	at org.axonframework.messaging.responsetypes.ConvertingResponseMessage.getPayload(ConvertingResponseMessage.java:77) ~[axon-messaging-4.3.jar:4.3]
	at org.axonframework.queryhandling.DefaultQueryGateway.lambda$query$1(DefaultQueryGateway.java:87) ~[axon-messaging-4.3.jar:4.3]
	at java.util.concurrent.CompletableFuture$UniAccept.tryFire(CompletableFuture.java:714) ~[?:?]
	at java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:506) ~[?:?]
	at java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2137) ~[?:?]
	at org.axonframework.axonserver.connector.query.AxonServerQueryBus$1.onNext(AxonServerQueryBus.java:343) ~[axon-server-connector-4.3.jar:4.3]
	at org.axonframework.axonserver.connector.query.AxonServerQueryBus$1.onNext(AxonServerQueryBus.java:337) ~[axon-server-connector-4.3.jar:4.3]
	at io.grpc.stub.ClientCalls$StreamObserverToCallListenerAdapter.onMessage(ClientCalls.java:429) ~[grpc-stub-1.22.1.jar:1.22.1]
	at io.grpc.ForwardingClientCallListener.onMessage(ForwardingClientCallListener.java:33) ~[grpc-api-1.22.1.jar:1.22.1]
	at io.grpc.ForwardingClientCallListener.onMessage(ForwardingClientCallListener.java:33) ~[grpc-api-1.22.1.jar:1.22.1]
	at io.grpc.internal.ClientCallImpl$ClientStreamListenerImpl$1MessagesAvailable.runInternal(ClientCallImpl.java:596) ~[grpc-core-1.22.1.jar:1.22.1]
	at io.grpc.internal.ClientCallImpl$ClientStreamListenerImpl$1MessagesAvailable.runInContext(ClientCallImpl.java:581) ~[grpc-core-1.22.1.jar:1.22.1]
	at io.grpc.internal.ContextRunnable.run(ContextRunnable.java:37) ~[grpc-core-1.22.1.jar:1.22.1]
	at io.grpc.internal.SerializingExecutor.run(SerializingExecutor.java:123) ~[grpc-core-1.22.1.jar:1.22.1]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130) ~[?:?]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:630) ~[?:?]
	at java.lang.Thread.run(Thread.java:832) [?:?]
```
    
    
To view expected behaviour:
- change the axon version dependency to 4.2.1
- refresh gradle dependencies
- do as above - you will be able to get the list as a result without any problem. 
