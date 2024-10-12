package gpt;
import java.util.ArrayList;
import java.util.List;

public class TaskScheduler {
  private final List<Task> tasks = new ArrayList<>();
  private final Object lock = new Object();
  private boolean running = true;

  public TaskScheduler() {
    Thread schedularThread = new Thread(this::schedule);
    schedularThread.start();

  }

  public void scheduleSingleExecution(Runnable task, long delay) {
    synchronized (lock) {
      tasks.add(new Task(task, System.currentTimeMillis() + delay, 1, 0));
      lock.notifyAll();
    }
  }

  public void schedulePeriodicExecution(Runnable task, long initialDelay, long period, int iterations) {
    synchronized (lock) {
      tasks.add(new Task(task, System.currentTimeMillis()+initialDelay, iterations, period));
      lock.notifyAll();
    }
  }

  private void schedule() {
    while(running) {
      synchronized (lock) {
        long currentTime = System.currentTimeMillis();
        tasks.removeIf(Task::isDone);

        Task nextTask = null;
        for(Task task: tasks) {
          if(task.getNextExecutionTime() <= currentTime) {
            nextTask = task;
            break;
          }
        }

        if(nextTask != null) {
          nextTask.run();
        } else {
          try {
            lock.wait(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  public void stop() {
    synchronized (lock) {
      running = false;
      lock.notifyAll();
    }
  }
}
