package org.gefsu;

public class App
{
    public static void main(String[] args) {

        if (1 > args.length) {
            System.out.println("Pass a port number as an argument");
            return;
        }

        Server server = new Server();
        server.start(Integer.parseInt(args[0]));
    }
}
