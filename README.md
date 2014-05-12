Set
===

A multiplayer implementation of the card game Set. Provided is the code for both the client and the server side computing. A server is started on either a separate machine or localhost. These configurations are hard-coded, but can be altered to accept arguments at run time instead which specify the host name and port number.

A single server can wait for multiple client connections. Whenever a client connects, he is directed to provide a username and password, so as to log in with his own account, otherwise he is directed to create such an account.

Lobby
-----

After having logged in successfully, the user can see who is currently waiting to start the game, and can either join or leave the current session. Once 4 players have started the game, the session begins.

Game
----

The game follows the rules of the card game set, and additionally includes a chat box for players to communicate with each other.

Communication
-------------

In order to handle the communication between client and server, a client pushed messages to a `ServerThread` object, which then processed those commands and pushed the results back to a `ConcurrentLinkedQueue` on the Server. The Server itself is constantly checking for any messages present in the queue and popping the top message of the queue, to be sent back to all clients. In this way, client to client communication was established thread-safely.
