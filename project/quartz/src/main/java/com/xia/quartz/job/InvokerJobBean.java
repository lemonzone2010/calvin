package com.xia.quartz.job;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.StatefulJob;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.scheduling.quartz.JobMethodInvocationFailedException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MethodInvoker;

/**
 * 从别的地儿复制过来的，对已经实现了未继承job的任务，要用到MethodInvokingJobDetailFactoryBean包装成job时
 * xiayong
 * @see org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean
 */
public class InvokerJobBean extends ArgumentConvertingMethodInvoker
	implements  BeanNameAware, BeanClassLoaderAware, BeanFactoryAware {

	private String name;

	private String group = Scheduler.DEFAULT_GROUP;

	private boolean concurrent = true;

	private String targetBeanName;

	private String[] jobListenerNames;

	private String beanName;

	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	private BeanFactory beanFactory;

	private JobDetail jobDetail;

	/**
	 * Set the name of the job.
	 * <p>Default is the bean name of this FactoryBean.
	 * @see org.quartz.JobDetail#setName
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the group of the job.
	 * <p>Default is the default group of the Scheduler.
	 * @see org.quartz.JobDetail#setGroup
	 * @see org.quartz.Scheduler#DEFAULT_GROUP
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Specify whether or not multiple jobs should be run in a concurrent
	 * fashion. The behavior when one does not want concurrent jobs to be
	 * executed is realized through adding the {@link StatefulJob} interface.
	 * More information on stateful versus stateless jobs can be found
	 * <a href="http://www.opensymphony.com/quartz/tutorial.html#jobsMore">here</a>.
	 * <p>The default setting is to run jobs concurrently.
	 */
	public void setConcurrent(boolean concurrent) {
		this.concurrent = concurrent;
	}

	/**
	 * Set the name of the target bean in the Spring BeanFactory.
	 * <p>This is an alternative to specifying {@link #setTargetObject "targetObject"},
	 * allowing for non-singleton beans to be invoked. Note that specified
	 * "targetObject" and {@link #setTargetClass "targetClass"} values will
	 * override the corresponding effect of this "targetBeanName" setting
	 * (i.e. statically pre-define the bean type or even the bean object).
	 */
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	/**
	 * Set a list of JobListener names for this job, referring to
	 * non-global JobListeners registered with the Scheduler.
	 * <p>A JobListener name always refers to the name returned
	 * by the JobListener implementation.
	 * @see SchedulerFactoryBean#setJobListeners
	 * @see org.quartz.JobListener#getName
	 */
	public void setJobListenerNames(String[] names) {
		this.jobListenerNames = names;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	protected Class resolveClassName(String className) throws ClassNotFoundException {
		return ClassUtils.forName(className, this.beanClassLoader);
	}

	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
		prepare();

		// Use specific name if given, else fall back to bean name.
		String name = (this.name != null ? this.name : this.beanName);

		// Consider the concurrent flag to choose between stateful and stateless job.
		Class jobClass = (this.concurrent ? MethodInvokingJob.class : StatefulMethodInvokingJob.class);

		// Build JobDetail instance.
		this.jobDetail = new JobDetail(name, this.group, jobClass);
		this.jobDetail.getJobDataMap().put("methodInvoker", this);
		this.jobDetail.setVolatility(true);
		this.jobDetail.setDurability(true);

		// Register job listener names.
		if (this.jobListenerNames != null) {
			for (String jobListenerName : this.jobListenerNames) {
				this.jobDetail.addJobListener(jobListenerName);
			}
		}

		postProcessJobDetail(this.jobDetail);
	}

	/**
	 * Callback for post-processing the JobDetail to be exposed by this FactoryBean.
	 * <p>The default implementation is empty. Can be overridden in subclasses.
	 * @param jobDetail the JobDetail prepared by this FactoryBean
	 */
	protected void postProcessJobDetail(JobDetail jobDetail) {
	}


	/**
	 * Overridden to support the {@link #setTargetBeanName "targetBeanName"} feature.
	 */
	@Override
	public Class getTargetClass() {
		Class targetClass = super.getTargetClass();
		if (targetClass == null && this.targetBeanName != null) {
			Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
			targetClass = this.beanFactory.getType(this.targetBeanName);
		}
		return targetClass;
	}

	/**
	 * Overridden to support the {@link #setTargetBeanName "targetBeanName"} feature.
	 */
	@Override
	public Object getTargetObject() {
		Object targetObject = super.getTargetObject();
		if (targetObject == null && this.targetBeanName != null) {
			Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
			targetObject = this.beanFactory.getBean(this.targetBeanName);
		}
		return targetObject;
	}


	public JobDetail getObject() {
		return this.jobDetail;
	}

	public Class<?> getObjectType() {
		return JobDetail.class;
	}

	public boolean isSingleton() {
		return false;
	}


	/**
	 * Quartz Job implementation that invokes a specified method.
	 * Automatically applied by MethodInvokingJobDetailFactoryBean.
	 */
	public static class MethodInvokingJob extends AbstractJob {

		protected static final Log logger = LogFactory.getLog(MethodInvokingJob.class);

		private MethodInvoker methodInvoker;

		/**
		 * Set the MethodInvoker to use.
		 */
		public void setMethodInvoker(MethodInvoker methodInvoker) {
			this.methodInvoker = methodInvoker;
		}

		/**
		 * Invoke the method via the MethodInvoker.
		 */

		@Override
		public void execute(JobDataMap jobDataMap) throws Exception {
			try {
				this.methodInvoker.invoke();
			}
			catch (InvocationTargetException ex) {
				if (ex.getTargetException() instanceof JobExecutionException) {
					// -> JobExecutionException, to be logged at info level by Quartz
					throw (JobExecutionException) ex.getTargetException();
				}
				else {
					// -> "unhandled exception", to be logged at error level by Quartz
					throw new JobMethodInvocationFailedException(this.methodInvoker, ex.getTargetException());
				}
			}
			catch (Exception ex) {
				// -> "unhandled exception", to be logged at error level by Quartz
				throw new JobMethodInvocationFailedException(this.methodInvoker, ex);
			}
			
		}
	}


	/**
	 * Extension of the MethodInvokingJob, implementing the StatefulJob interface.
	 * Quartz checks whether or not jobs are stateful and if so,
	 * won't let jobs interfere with each other.
	 */
	public static class StatefulMethodInvokingJob extends MethodInvokingJob implements StatefulJob {

		// No implementation, just an addition of the tag interface StatefulJob
		// in order to allow stateful method invoking jobs.
	}

}
