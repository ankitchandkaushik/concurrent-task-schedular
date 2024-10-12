package gpt;

public class Task {
  private final Runnable task;
  private final long period;
  private final int iterations;
  private long nextExecutionTime;
  private int executedCount;

  public Task(Runnable task, long nextExecutionTime, int iterations, long period) {
    this.task = task;
    this.nextExecutionTime = nextExecutionTime;
    this.iterations = iterations;
    this.period = period;
  }

  public void run() {
    task.run();
    executedCount++;
    if(iterations == 0 || executedCount < iterations) {
      nextExecutionTime+=period;
    }
  }

  public boolean isDone() {
    return iterations > 0 && executedCount >= iterations;
  }

  public long getNextExecutionTime() {
    return this.nextExecutionTime;
  }


}
