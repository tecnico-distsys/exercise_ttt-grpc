package pt.ulisboa.tecnico.distsys.ttt.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.ulisboa.tecnico.distsys.ttt.*;

import java.util.Scanner;

public class TTTClient {

    public static void main(String[] args) {
        System.out.println(TTTClient.class.getSimpleName());

        // receive and print arguments
        System.out.printf("Received %d arguments%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }

        // check arguments
        if (args.length < 2) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s host port%n", TTTClient.class.getName());
            return;
        }

        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        final String target = host + ":" + port;

        // Channel is the abstraction to connect to a service endpoint
        // Let us use plaintext communication because we do not have certificates
        final ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

        // It is up to the client to determine whether to block the call
        // Here we create a blocking stub, but an async stub,
        // or an async stub with Future are always possible.
        TTTGrpc.TTTBlockingStub stub = TTTGrpc.newBlockingStub(channel);

        playGame(stub);

        System.exit(0);
    }

    private static void playGame(TTTGrpc.TTTBlockingStub stub) {
        System.out.println(stub.currentBoard(CurrentBoardRequest.getDefaultInstance()).getBoard());
    }
}
