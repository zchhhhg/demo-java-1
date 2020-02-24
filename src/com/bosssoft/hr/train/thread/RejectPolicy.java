package com.bosssoft.hr.train.thread;

public interface RejectPolicy {
    void reject(Runnable task, TestThreadPool myThreadPoolExecutor);
}
