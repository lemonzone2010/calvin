package com.apusic.ebiz.framework.core.job;


public interface ScheduleJob {
    /**
     * 执行Job，不抛出异常，出现异常时，记录到任务中，以便管理人员解决相关问题
     */
    public void perform();
}
