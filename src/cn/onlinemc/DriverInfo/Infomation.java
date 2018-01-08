package cn.onlinemc.DriverInfo;

import java.net.MalformedURLException;
import java.util.Map; 
import java.util.Properties; 
import org.hyperic.sigar.CpuInfo; 
import org.hyperic.sigar.CpuPerc; 
import org.hyperic.sigar.FileSystem; 
import org.hyperic.sigar.FileSystemUsage; 
import org.hyperic.sigar.Mem; 
import org.hyperic.sigar.NetFlags; 
import org.hyperic.sigar.NetInterfaceConfig; 
import org.hyperic.sigar.NetInterfaceStat; 
import org.hyperic.sigar.OperatingSystem; 
import org.hyperic.sigar.Sigar; 
import org.hyperic.sigar.SigarException; 
import org.hyperic.sigar.Swap; 
import org.hyperic.sigar.Who;


public class Infomation { 
	private String str = "";
	
	public String getInfoString(){
		return str;
	}
	
	public void addText(String str){
		this.str = this.str+str+"\n";
	}
	
	
	
    public void init() { 
        try { 
            // System��Ϣ����jvm��ȡ 
            property(); 
            addText("----------------------------------"); 
            // cpu��Ϣ 
            cpu(); 
            addText("----------------------------------"); 
            // �ڴ���Ϣ 
            memory(); 
            addText("----------------------------------"); 
            // ����ϵͳ��Ϣ 
            os(); 
            addText("----------------------------------"); 
            // �û���Ϣ 
            who(); 
            addText("----------------------------------"); 
            // �ļ�ϵͳ��Ϣ 
            file(); 
            addText("----------------------------------"); 
            // ������Ϣ 
            net(); 
            addText("----------------------------------"); 
            // ��̫����Ϣ 
            ethernet(); 
            addText("----------------------------------"); 
        } catch (ClassNotFoundException e){
        	e.printStackTrace();
        } catch (Exception e1) { 
        	e1.printStackTrace(); 
        }
    } 

    private void property(){ 
        Runtime r = Runtime.getRuntime(); 
        Properties props = System.getProperties(); 
        Map<String, String> map = System.getenv(); 
        String userName = map.get("USERNAME");// ��ȡ�û��� 
        String computerName = map.get("COMPUTERNAME");// ��ȡ������� 
        String userDomain = map.get("USERDOMAIN");// ��ȡ��������� 
        addText("�û���:    " + userName); 
        addText("�������:    " + computerName); 
        addText("���������:    " + userDomain); 
        addText("JVM����ʹ�õ����ڴ�:    " + r.totalMemory()); 
        addText("JVM����ʹ�õ�ʣ���ڴ�:    " + r.freeMemory()); 
        addText("JVM����ʹ�õĴ���������:    " + r.availableProcessors()); 
        addText("Java�����л����汾��    " + props.getProperty("java.version")); 
        addText("Java�����л�����Ӧ�̣�    " + props.getProperty("java.vendor")); 
        addText("Java��Ӧ�̵�URL��    " + props.getProperty("java.vendor.url")); 
        addText("Java�İ�װ·����    " + props.getProperty("java.home")); 
        addText("Java��������淶�汾��    " + props.getProperty("java.vm.specification.version")); 
        addText("Java��������淶��Ӧ�̣�    " + props.getProperty("java.vm.specification.vendor")); 
        addText("Java��������淶���ƣ�    " + props.getProperty("java.vm.specification.name")); 
        addText("Java�������ʵ�ְ汾��    " + props.getProperty("java.vm.version")); 
        addText("Java�������ʵ�ֹ�Ӧ�̣�    " + props.getProperty("java.vm.vendor")); 
        addText("Java�������ʵ�����ƣ�    " + props.getProperty("java.vm.name")); 
        addText("Java����ʱ�����淶�汾��    " + props.getProperty("java.specification.version")); 
        addText("Java����ʱ�����淶��Ӧ�̣�    " + props.getProperty("java.specification.vender")); 
        addText("Java����ʱ�����淶���ƣ�    " + props.getProperty("java.specification.name")); 
        addText("Java�����ʽ�汾�ţ�    " + props.getProperty("java.class.version")); 
        addText("Java����·����    " + props.getProperty("java.class.path")); 
        addText("���ؿ�ʱ������·���б�    " + props.getProperty("java.library.path")); 
        addText("Ĭ�ϵ���ʱ�ļ�·����    " + props.getProperty("java.io.tmpdir")); 
        addText("һ��������չĿ¼��·����    " + props.getProperty("java.ext.dirs")); 
        addText("����ϵͳ�����ƣ�    " + props.getProperty("os.name")); 
        addText("����ϵͳ�Ĺ��ܣ�    " + props.getProperty("os.arch")); 
        addText("����ϵͳ�İ汾��    " + props.getProperty("os.version")); 
        addText("�ļ��ָ�����    " + props.getProperty("file.separator")); 
        addText("·���ָ�����    " + props.getProperty("path.separator")); 
        addText("�зָ�����    " + props.getProperty("line.separator")); 
        addText("�û����˻����ƣ�    " + props.getProperty("user.name")); 
        addText("�û�����Ŀ¼��    " + props.getProperty("user.home")); 
        addText("�û��ĵ�ǰ����Ŀ¼��    " + props.getProperty("user.dir")); 
    } 

    private void memory() throws SigarException, MalformedURLException { 
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem(); 
        // �ڴ����� 
        addText("�ڴ�����:    " + mem.getTotal() / 1024L + "K av"); 
        // ��ǰ�ڴ�ʹ���� 
        addText("��ǰ�ڴ�ʹ����:    " + mem.getUsed() / 1024L + "K used"); 
        // ��ǰ�ڴ�ʣ���� 
        addText("��ǰ�ڴ�ʣ����:    " + mem.getFree() / 1024L + "K free"); 
        Swap swap = sigar.getSwap(); 
        // ���������� 
        addText("����������:    " + swap.getTotal() / 1024L + "K av"); 
        // ��ǰ������ʹ���� 
        addText("��ǰ������ʹ����:    " + swap.getUsed() / 1024L + "K used"); 
        // ��ǰ������ʣ���� 
        addText("��ǰ������ʣ����:    " + swap.getFree() / 1024L + "K free"); 
    } 

    private void cpu() throws SigarException, MalformedURLException { 
        Sigar sigar = new Sigar();
    	
        CpuInfo infos[] = sigar.getCpuInfoList(); 
        CpuPerc cpuList[] = null; 
        cpuList = sigar.getCpuPercList(); 
        for (int i = 0; i < infos.length; i++) {// �����ǵ���CPU���Ƕ�CPU������ 
            CpuInfo info = infos[i]; 
            addText("��" + (i + 1) + "��CPU��Ϣ"); 
            addText("CPU������MHz:    " + info.getMhz());// CPU������MHz 
            addText("CPU������:    " + info.getVendor());// ���CPU���������磺Intel 
            addText("CPU���:    " + info.getModel());// ���CPU������磺Celeron 
            addText("CPU��������:    " + info.getCacheSize());// ����洢������ 
            printCpuPerc(cpuList[i]); 
        } 
    } 

    private void printCpuPerc(CpuPerc cpu) { 
        addText("CPU�û�ʹ����:    " + CpuPerc.format(cpu.getUser()));// �û�ʹ���� 
        addText("CPUϵͳʹ����:    " + CpuPerc.format(cpu.getSys()));// ϵͳʹ���� 
        addText("CPU��ǰ�ȴ���:    " + CpuPerc.format(cpu.getWait()));// ��ǰ�ȴ��� 
        addText("CPU��ǰ������:    " + CpuPerc.format(cpu.getNice()));// 
        addText("CPU��ǰ������:    " + CpuPerc.format(cpu.getIdle()));// ��ǰ������ 
        addText("CPU�ܵ�ʹ����:    " + CpuPerc.format(cpu.getCombined()));// �ܵ�ʹ���� 
    } 

    private void os() { 
        OperatingSystem OS = OperatingSystem.getInstance(); 
        // ����ϵͳ�ں������磺 386��486��586��x86 
        addText("����ϵͳ:    " + OS.getArch()); 
        addText("����ϵͳCpuEndian():    " + OS.getCpuEndian());// 
        addText("����ϵͳDataModel():    " + OS.getDataModel());// 
        // ϵͳ���� 
        addText("����ϵͳ������:    " + OS.getDescription()); 
        // ����ϵͳ���� 
        // addText("OS.getName():    " + OS.getName()); 
        // addText("OS.getPatchLevel():    " + OS.getPatchLevel());// 
        // ����ϵͳ������ 
        addText("����ϵͳ������:    " + OS.getVendor()); 
        // �������� 
        addText("����ϵͳ��������:    " + OS.getVendorCodeName()); 
        // ����ϵͳ���� 
        addText("����ϵͳ����:    " + OS.getVendorName()); 
        // ����ϵͳ�������� 
        addText("����ϵͳ��������:    " + OS.getVendorVersion()); 
        // ����ϵͳ�İ汾�� 
        addText("����ϵͳ�İ汾��:    " + OS.getVersion()); 
    } 

    private void who() throws SigarException, MalformedURLException { 
        Sigar sigar = new Sigar();
        Who who[] = sigar.getWhoList(); 
        if (who != null && who.length > 0) { 
            for (int i = 0; i < who.length; i++) { 
                // addText("��ǰϵͳ���̱��е��û���" + String.valueOf(i)); 
                Who _who = who[i]; 
                addText("�û�����̨:    " + _who.getDevice()); 
                addText("�û�host:    " + _who.getHost()); 
                // addText("getTime():    " + _who.getTime()); 
                // ��ǰϵͳ���̱��е��û��� 
                addText("��ǰϵͳ���̱��е��û���:    " + _who.getUser()); 
            } 
        } 
    } 

    private void file() throws Exception { 
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList(); 
        for (int i = 0; i < fslist.length; i++) { 
            addText("�������̷�����" + i); 
            FileSystem fs = fslist[i]; 
            // �������̷����� 
            addText("�̷�����:    " + fs.getDevName()); 
            // �������̷����� 
            addText("�̷�·��:    " + fs.getDirName()); 
            addText("�̷���־:    " + fs.getFlags());// 
            // �ļ�ϵͳ���ͣ����� FAT32��NTFS 
            addText("�̷�����:    " + fs.getSysTypeName()); 
            // �ļ�ϵͳ�����������籾��Ӳ�̡������������ļ�ϵͳ�� 
            addText("�̷�������:    " + fs.getTypeName()); 
            // �ļ�ϵͳ���� 
            addText("�̷��ļ�ϵͳ����:    " + fs.getType()); 
            FileSystemUsage usage = null; 
            usage = sigar.getFileSystemUsage(fs.getDirName()); 
            switch (fs.getType()) { 
            case 0: // TYPE_UNKNOWN ��δ֪ 
                break; 
            case 1: // TYPE_NONE 
                break; 
            case 2: // TYPE_LOCAL_DISK : ����Ӳ�� 
                // �ļ�ϵͳ�ܴ�С 
                addText(fs.getDevName() + "�ܴ�С:    " + usage.getTotal() + "KB"); 
                // �ļ�ϵͳʣ���С 
                addText(fs.getDevName() + "ʣ���С:    " + usage.getFree() + "KB"); 
                // �ļ�ϵͳ���ô�С 
                addText(fs.getDevName() + "���ô�С:    " + usage.getAvail() + "KB"); 
                // �ļ�ϵͳ�Ѿ�ʹ���� 
                addText(fs.getDevName() + "�Ѿ�ʹ����:    " + usage.getUsed() + "KB"); 
                double usePercent = usage.getUsePercent() * 100D; 
                // �ļ�ϵͳ��Դ�������� 
                addText(fs.getDevName() + "��Դ��������:    " + usePercent + "%"); 
                break; 
            case 3:// TYPE_NETWORK ������ 
                break; 
            case 4:// TYPE_RAM_DISK ������ 
                break; 
            case 5:// TYPE_CDROM ������ 
                break; 
            case 6:// TYPE_SWAP ��ҳ�潻�� 
                break; 
            } 
            addText(fs.getDevName() + "������    " + usage.getDiskReads()); 
            addText(fs.getDevName() + "д�룺    " + usage.getDiskWrites()); 
        } 
        return; 
    } 

    private void net() throws Exception { 
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList(); 
        for (int i = 0; i < ifNames.length; i++) { 
            String name = ifNames[i]; 
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name); 
            addText("�����豸��:    " + name);// �����豸�� 
            addText("IP��ַ:    " + ifconfig.getAddress());// IP��ַ 
            addText("��������:    " + ifconfig.getNetmask());// �������� 
            if ((ifconfig.getFlags() & 1L) <= 0L) { 
                addText("!IFF_UP...skipping getNetInterfaceStat"); 
                continue; 
            } 
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name); 
            addText(name + "���յ��ܰ�����:" + ifstat.getRxPackets());// ���յ��ܰ����� 
            addText(name + "���͵��ܰ�����:" + ifstat.getTxPackets());// ���͵��ܰ����� 
            addText(name + "���յ������ֽ���:" + ifstat.getRxBytes());// ���յ������ֽ��� 
            addText(name + "���͵����ֽ���:" + ifstat.getTxBytes());// ���͵����ֽ��� 
            addText(name + "���յ��Ĵ������:" + ifstat.getRxErrors());// ���յ��Ĵ������ 
            addText(name + "�������ݰ�ʱ�Ĵ�����:" + ifstat.getTxErrors());// �������ݰ�ʱ�Ĵ����� 
            addText(name + "����ʱ�����İ���:" + ifstat.getRxDropped());// ����ʱ�����İ��� 
            addText(name + "����ʱ�����İ���:" + ifstat.getTxDropped());// ����ʱ�����İ��� 
        } 
    } 

    private void ethernet() throws SigarException, MalformedURLException { 
        Sigar sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList(); 
        for (int i = 0; i < ifaces.length; i++) { 
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]); 
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0 
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) { 
                continue; 
            } 
            addText(cfg.getName() + "IP��ַ:" + cfg.getAddress());// IP��ַ 
            addText(cfg.getName() + "���ع㲥��ַ:" + cfg.getBroadcast());// ���ع㲥��ַ 
            addText(cfg.getName() + "����MAC��ַ:" + cfg.getHwaddr());// ����MAC��ַ 
            addText(cfg.getName() + "��������:" + cfg.getNetmask());// �������� 
            addText(cfg.getName() + "����������Ϣ:" + cfg.getDescription());// ����������Ϣ 
            addText(cfg.getName() + "��������" + cfg.getType());// 
        } 
    } 
}