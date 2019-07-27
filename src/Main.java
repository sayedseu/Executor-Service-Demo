import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    private Main() {

      int availableOfProcessor = Utils.getAvailableOfProcessor;
      BufferedImage image = Utils.getBufferedImage();
      int width = image.getWidth();
      int dividedWidth = width/availableOfProcessor;
      ExecutorService executorService = Executors.newFixedThreadPool(availableOfProcessor);
      List<Future<int[]>> futureList = new ArrayList<>();
      int[] histogram = new int[256];

      for (int i=0 ; i<availableOfProcessor; i++){
          Task task = null;
          if ( i != availableOfProcessor-1){
              task = new Task(Utils.getBufferedImage(),i*dividedWidth,(i+1)*dividedWidth);
          }else {
              task = new Task(Utils.getBufferedImage(),i*dividedWidth,width);
          }
          Future<int[]> submit = executorService.submit(task);
          futureList.add(submit);
      }
      executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       for (int j=0; j<availableOfProcessor; j++){
           try {
               int[] currentHistogram = futureList.get(j).get();
               for (int h=0; h<histogram.length; h++){
                   histogram[h] += currentHistogram[h];
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (ExecutionException e) {
               e.printStackTrace();
           }
       }

        for (int k=0; k<histogram.length; k++){
            System.out.println(k+"  "+histogram[k]);
        }
    }



}
