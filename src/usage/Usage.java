package usage;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;

import com.sun.management.OperatingSystemMXBean;

public class Usage {
	private MBeanServerConnection mbsc;
	private OperatingSystemMXBean osMBean;
	private long nanoBefore;
	private long nanoAfter;
	private long cpuBefore;
	private long cpuAfter;
	private long memoryBeforeUsage;
	private long memoryAfterUsage;
	private long totalMemory;

	public Usage() throws IOException {
		mbsc = ManagementFactory.getPlatformMBeanServer();
		osMBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
				OperatingSystemMXBean.class);
	}

	public void startMeasure() {
		nanoBefore = System.nanoTime();
		cpuBefore = osMBean.getProcessCpuTime();
		memoryBeforeUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		totalMemory = Runtime.getRuntime().totalMemory();
	}

	public void endMeasure() {
		nanoAfter = System.nanoTime();
		cpuAfter = osMBean.getProcessCpuTime();
		memoryAfterUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	public void printResults() {
		long actualMemoryUsed = memoryAfterUsage - memoryBeforeUsage;
		double memoryUsage = ((actualMemoryUsed * 1.0) / (totalMemory * 1.0)) * 100;
		System.out.println("Memory Usage: " + memoryUsage + "%");

		long cpuUsage;
		if (nanoAfter > nanoBefore)
			cpuUsage = ((cpuAfter - cpuBefore) * 100L) / (nanoAfter - nanoBefore);
		else
			cpuUsage = 0;

		System.out.println("CPU Usage: " + cpuUsage + "%");
	}
}
