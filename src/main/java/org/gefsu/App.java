package org.gefsu;

public class App
{
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Pass a port number as an argument");
            return;
        }

        Server server = new Server();
        server.start(Integer.parseInt(args[0]));
    }
}
