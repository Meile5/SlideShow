package easv;

public class ImageThread extends Thread {

    @Override
    public void run()  {
        while(true)
        {
            System.out.println("Ping");
            try
            {
                Thread.sleep(3000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();;
            }
        }
    }
}
