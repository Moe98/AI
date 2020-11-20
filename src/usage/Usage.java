package usage;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;

import com.sun.management.OperatingSystemMXBean;

public class Usage {
	private MBeanServerConnection mbsc;
	private OperatingSystemMXBean osMBean;
	private double nanoBefore;
	private double nanoAfter;
	private double cpuBefore;
	private double cpuAfter;
	private long memoryBeforeUsage;
	private long memoryAfterUsage;

	public Usage() throws IOException {
		mbsc = ManagementFactory.getPlatformMBeanServer();
		osMBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
				OperatingSystemMXBean.class);
	}

	public void startMeasure() {
		nanoBefore = System.nanoTime();
		cpuBefore = osMBean.getProcessCpuTime();
		memoryBeforeUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	public void endMeasure() {
		nanoAfter = System.nanoTime();
		cpuAfter = osMBean.getProcessCpuTime();
		memoryAfterUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	public void printResults() {
		long actualMemoryUsed = memoryAfterUsage - memoryBeforeUsage;
		double memoryUsage = ((actualMemoryUsed * 1.0) / (Runtime.getRuntime().totalMemory() * 1.0)) * 100;
		System.out.println("Memory Usage: " + memoryUsage + "%");

		double cpuUsage;
		if (nanoAfter > nanoBefore)
			cpuUsage = ((cpuAfter-cpuBefore*1.0)/(nanoAfter-nanoBefore));
		else
			cpuUsage = 0;

		System.out.println("CPU Usage: " + cpuUsage + "%");
	}

//	public static void main(String[] args) throws IOException {
//		Usage usage = new Usage();
//		usage.startMeasure();
//		usage.endMeasure();
//		usage.printResults();
//	}
}
