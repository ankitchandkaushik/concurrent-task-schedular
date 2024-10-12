package gpt;

public class Application {
  public static void main(String[] args) {

    TaskScheduler schedular = new TaskScheduler();

    schedular.schedulePeriodicExecution(() -> System.out.println("Periodic task 1 executed"), 2000, 500, 5);
    schedular.scheduleSingleExecution(() -> System.out.println("Single task 1 executed"), 1000);
    schedular.schedulePeriodicExecution(() -> System.out.println("Periodic task 2 executed"), 3000, 800, 3);
    schedular.scheduleSingleExecution(() -> System.out.println("Single task 2 executed"), 2500);

    try {
      Thread.sleep(50000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    schedular.stop();
    System.out.println("Scheduler stopped");



  }
}
