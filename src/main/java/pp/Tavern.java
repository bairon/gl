package pp;

import hlp.MyHttpClient;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Tavern {
	public static void main(String[] args) throws SchedulerException, ParseException {
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

		Scheduler sched = schedFact.getScheduler();

		sched.start();

		// define the job and tie it to our HelloJob class
		JobDetail job = newJob(TavernJob.class)
				.withIdentity("myJob", "group1")
				.build();

		// Trigger the job to run now, and then every 40 seconds
		Trigger trigger = newTrigger()
				.withIdentity("myTrigger", "group1")
				.startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 21 2 * * ?")).build();


		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger);
	}
	public static class TavernJob implements Job {
		public static final String domain = "s2.gladiators.ru";
		@Override
		public void execute(final JobExecutionContext context) throws JobExecutionException {
			MyHttpClient client = new MyHttpClient();
			client.appendInitialCookie("cookie_lang_3", "rus", domain);
			GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
			try {
                Properties props = new Properties();
                props.load(new FileInputStream("settings.txt"));
                service.login(props.get("USER").toString(), props.get("PASSW").toString());
                Utils.sleep(1000);
				while (true) {
					RecruitsActivity gm = new RecruitsActivity(service);
					gm.doSome();
					Utils.sleep(10000);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("executed");
		}
	}

}
