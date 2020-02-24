package com.bosssoft.hr.train.thread;

    public class DiscardRejectPolicy implements RejectPolicy {
        @Override
        public void reject(Runnable task, TestThreadPool myThreadPoolExecutor) {
            // do nothing
            System.out.println("discard one task");
        }
}
