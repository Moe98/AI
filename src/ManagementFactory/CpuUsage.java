package ManagementFactory;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;

import com.sun.management.OperatingSystemMXBean;

public class CpuUsage {

	
	public static void main(String[] args) throws IOException {
		MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

		OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(
		mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);

		long nanoBefore = System.nanoTime();
		long cpuBefore = osMBean.getProcessCpuTime();
		long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		long totalMem=Runtime.getRuntime().totalMemory();

		// Call an expensive task, or sleep if you are monitoring a remote process
		String s = "";
		for(int i=0;i<100000;i++) {
//			s+=" ";
		}
		long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		long actualMemUsed=afterUsedMem-beforeUsedMem;
		double usage = ((actualMemUsed*1.0)/(totalMem*1.0))*100;
		System.out.println(usage+" %");

		long cpuAfter = osMBean.getProcessCpuTime();
		long nanoAfter = System.nanoTime();

		long percent;
		if (nanoAfter > nanoBefore)
		 percent = ((cpuAfter-cpuBefore)*100L)/
		   (nanoAfter-nanoBefore);
		else percent = 0;

		System.out.println("Cpu usage: "+percent+"%");
	}
}
