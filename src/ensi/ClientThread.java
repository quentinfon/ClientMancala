package ensi;

import java.io.*;

public class ClientThread implements Runnable {

    private Thread _threadClient;
    private PrintWriter _out;
    private InputStream _in;

    ClientThread()
    {

        try
        {
            _out = new PrintWriter(Client.socket.getOutputStream());
            _in = Client.socket.getInputStream();
        }
        catch (IOException e){ }

        _threadClient = new Thread(this);
        _threadClient.start();
    }


    public void run()
    {

        System.out.println("Debut du Thread client");

        try
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(_in));
            String userInput = "";
            while((userInput = input.readLine()) != null)
            {
                System.out.println(userInput);


                /*
                InputStream is= Client.socket.getInputStream();
                ObjectInputStream ois=new ObjectInputStream(is);
                Client.joueur = (Joueur) ois.readObject();*/

            }
        } catch (IOException e) {
        } finally
                {
                    try
                    {
                        System.out.println("Fin du Thread client");
                        if (Client.socket != null){
                            Client.socket.close();
                            Client.socket = null;
                        }

            }
            catch (IOException e){ }
        }
    }


}
