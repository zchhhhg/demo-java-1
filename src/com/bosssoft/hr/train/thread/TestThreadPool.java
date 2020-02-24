/*
 * 自码线程池
 * author：张璐思
 * 2020-02-24
 * */

package com.bosssoft.hr.train.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class TestThreadPool implements Executor {

    public static void main(String[] args) {
            Executor threadPool = new TestThreadPool("test", 5, 10, new ArrayBlockingQueue<>(15), new DiscardRejectPolicy());
            AtomicInteger num = new AtomicInteger(0);
            //加入线程进行测试
            for (int i = 0; i < 100; i++) {
                threadPool.execute(()->{
                    try {
                        Thread.sleep(1000);
                        System.out.println("running: " + System.currentTimeMillis() + ": " + num.incrementAndGet());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
    }
        //线程池的名称
        private String name;
        // 线程序列号
        private AtomicInteger sequence = new AtomicInteger(0);
        //核心线程数
        private int coreSize;
        //大线程数
        private int maxSize;
        //任务队列
        private BlockingQueue<Runnable> taskQueue;
        //拒绝策略
        private RejectPolicy rejectPolicy;
        private AtomicInteger runningCount = new AtomicInteger(0);

        public TestThreadPool(String name, int coreSize, int maxSize, BlockingQueue<Runnable> taskQueue, RejectPolicy rejectPolicy) {
            this.name = name;
            this.coreSize = coreSize;
            this.maxSize = maxSize;
            this.taskQueue = taskQueue;
            this.rejectPolicy = rejectPolicy;
        }

        @Override
        public void execute(Runnable task) {
            int count = runningCount.get();
            if (count < coreSize) {
                if (addWorker(task, true)) {
                    return;
                }
            }

            if (taskQueue.offer(task)) {
            } else {
                if (!addWorker(task, false)) {
                    rejectPolicy.reject(task, this);
                }
            }
        }

        private boolean addWorker(Runnable newTask, boolean core) {
            while (true){
                int count = runningCount.get();
                int max = core ? coreSize : maxSize;
                if (count >= max) {
                    return false;
                }
                if (runningCount.compareAndSet(count, count + 1)) {
                    String threadName = (core ? "core_" : "") + name + sequence.incrementAndGet();
                    new Thread(() -> {
                        System.out.println("thread name: " + Thread.currentThread().getName());
                        Runnable task = newTask;
                        while (task != null || (task = getTask()) != null) {
                            try {
                                task.run();
                            } finally {
                                task = null;
                            }
                        }
                    }, threadName).start();

                    break;
                }
            }
            return true;
        }

        private Runnable getTask() {
            try {
                return taskQueue.take();
            } catch (InterruptedException e) {
                runningCount.decrementAndGet();
                return null;
            }
        }

}
