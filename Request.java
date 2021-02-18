public class Request extends Thread{
    private boolean available;

    public Request() {
        available = false;
        start();
    }

    @Override
    public void run() {
        while(true) {makeWaiting();}
    }

    private synchronized void makeWaiting() {
        while (available) {
            try { 
                wait(); 
            }
            catch (InterruptedException requestIException) {
                System.err.println("Request Error Occurred");
            }
            try {
                sleep(100);
            }
            catch (InterruptedException requestIException) {
                System.err.println("Request Error Occurred");
            }
            available = true;
            notifyAll();
        }
    }

    public synchronized void takeRequest() {
        while (!available) {
           try { 
               wait(); 
            } 
            catch (InterruptedException requestIException) {
                System.err.println("Request Error Occurred");
            }
           available = false;
           notifyAll();
        }
   }

    public static void main(String[] args) {
        Request request = new Request();

        while(true) {
            request.takeRequest();
        }
    }

    
}
