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
            // System信息，从jvm获取 
            property(); 
            addText("----------------------------------"); 
            // cpu信息 
            cpu(); 
            addText("----------------------------------"); 
            // 内存信息 
            memory(); 
            addText("----------------------------------"); 
            // 操作系统信息 
            os(); 
            addText("----------------------------------"); 
            // 用户信息 
            who(); 
            addText("----------------------------------"); 
            // 文件系统信息 
            file(); 
            addText("----------------------------------"); 
            // 网络信息 
            net(); 
            addText("----------------------------------"); 
            // 以太网信息 
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
        String userName = map.get("USERNAME");// 获取用户名 
        String computerName = map.get("COMPUTERNAME");// 获取计算机名 
        String userDomain = map.get("USERDOMAIN");// 获取计算机域名 
        addText("用户名:    " + userName); 
        addText("计算机名:    " + computerName); 
        addText("计算机域名:    " + userDomain); 
        addText("JVM可以使用的总内存:    " + r.totalMemory()); 
        addText("JVM可以使用的剩余内存:    " + r.freeMemory()); 
        addText("JVM可以使用的处理器个数:    " + r.availableProcessors()); 
        addText("Java的运行环境版本：    " + props.getProperty("java.version")); 
        addText("Java的运行环境供应商：    " + props.getProperty("java.vendor")); 
        addText("Java供应商的URL：    " + props.getProperty("java.vendor.url")); 
        addText("Java的安装路径：    " + props.getProperty("java.home")); 
        addText("Java的虚拟机规范版本：    " + props.getProperty("java.vm.specification.version")); 
        addText("Java的虚拟机规范供应商：    " + props.getProperty("java.vm.specification.vendor")); 
        addText("Java的虚拟机规范名称：    " + props.getProperty("java.vm.specification.name")); 
        addText("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version")); 
        addText("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor")); 
        addText("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name")); 
        addText("Java运行时环境规范版本：    " + props.getProperty("java.specification.version")); 
        addText("Java运行时环境规范供应商：    " + props.getProperty("java.specification.vender")); 
        addText("Java运行时环境规范名称：    " + props.getProperty("java.specification.name")); 
        addText("Java的类格式版本号：    " + props.getProperty("java.class.version")); 
        addText("Java的类路径：    " + props.getProperty("java.class.path")); 
        addText("加载库时搜索的路径列表：    " + props.getProperty("java.library.path")); 
        addText("默认的临时文件路径：    " + props.getProperty("java.io.tmpdir")); 
        addText("一个或多个扩展目录的路径：    " + props.getProperty("java.ext.dirs")); 
        addText("操作系统的名称：    " + props.getProperty("os.name")); 
        addText("操作系统的构架：    " + props.getProperty("os.arch")); 
        addText("操作系统的版本：    " + props.getProperty("os.version")); 
        addText("文件分隔符：    " + props.getProperty("file.separator")); 
        addText("路径分隔符：    " + props.getProperty("path.separator")); 
        addText("行分隔符：    " + props.getProperty("line.separator")); 
        addText("用户的账户名称：    " + props.getProperty("user.name")); 
        addText("用户的主目录：    " + props.getProperty("user.home")); 
        addText("用户的当前工作目录：    " + props.getProperty("user.dir")); 
    } 

    private void memory() throws SigarException, MalformedURLException { 
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem(); 
        // 内存总量 
        addText("内存总量:    " + mem.getTotal() / 1024L + "K av"); 
        // 当前内存使用量 
        addText("当前内存使用量:    " + mem.getUsed() / 1024L + "K used"); 
        // 当前内存剩余量 
        addText("当前内存剩余量:    " + mem.getFree() / 1024L + "K free"); 
        Swap swap = sigar.getSwap(); 
        // 交换区总量 
        addText("交换区总量:    " + swap.getTotal() / 1024L + "K av"); 
        // 当前交换区使用量 
        addText("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used"); 
        // 当前交换区剩余量 
        addText("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free"); 
    } 

    private void cpu() throws SigarException, MalformedURLException { 
        Sigar sigar = new Sigar();
    	
        CpuInfo infos[] = sigar.getCpuInfoList(); 
        CpuPerc cpuList[] = null; 
        cpuList = sigar.getCpuPercList(); 
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用 
            CpuInfo info = infos[i]; 
            addText("第" + (i + 1) + "块CPU信息"); 
            addText("CPU的总量MHz:    " + info.getMhz());// CPU的总量MHz 
            addText("CPU生产商:    " + info.getVendor());// 获得CPU的卖主，如：Intel 
            addText("CPU类别:    " + info.getModel());// 获得CPU的类别，如：Celeron 
            addText("CPU缓存数量:    " + info.getCacheSize());// 缓冲存储器数量 
            printCpuPerc(cpuList[i]); 
        } 
    } 

    private void printCpuPerc(CpuPerc cpu) { 
        addText("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率 
        addText("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率 
        addText("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率 
        addText("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));// 
        addText("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率 
        addText("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));// 总的使用率 
    } 

    private void os() { 
        OperatingSystem OS = OperatingSystem.getInstance(); 
        // 操作系统内核类型如： 386、486、586等x86 
        addText("操作系统:    " + OS.getArch()); 
        addText("操作系统CpuEndian():    " + OS.getCpuEndian());// 
        addText("操作系统DataModel():    " + OS.getDataModel());// 
        // 系统描述 
        addText("操作系统的描述:    " + OS.getDescription()); 
        // 操作系统类型 
        // addText("OS.getName():    " + OS.getName()); 
        // addText("OS.getPatchLevel():    " + OS.getPatchLevel());// 
        // 操作系统的卖主 
        addText("操作系统的卖主:    " + OS.getVendor()); 
        // 卖主名称 
        addText("操作系统的卖主名:    " + OS.getVendorCodeName()); 
        // 操作系统名称 
        addText("操作系统名称:    " + OS.getVendorName()); 
        // 操作系统卖主类型 
        addText("操作系统卖主类型:    " + OS.getVendorVersion()); 
        // 操作系统的版本号 
        addText("操作系统的版本号:    " + OS.getVersion()); 
    } 

    private void who() throws SigarException, MalformedURLException { 
        Sigar sigar = new Sigar();
        Who who[] = sigar.getWhoList(); 
        if (who != null && who.length > 0) { 
            for (int i = 0; i < who.length; i++) { 
                // addText("当前系统进程表中的用户名" + String.valueOf(i)); 
                Who _who = who[i]; 
                addText("用户控制台:    " + _who.getDevice()); 
                addText("用户host:    " + _who.getHost()); 
                // addText("getTime():    " + _who.getTime()); 
                // 当前系统进程表中的用户名 
                addText("当前系统进程表中的用户名:    " + _who.getUser()); 
            } 
        } 
    } 

    private void file() throws Exception { 
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList(); 
        for (int i = 0; i < fslist.length; i++) { 
            addText("分区的盘符名称" + i); 
            FileSystem fs = fslist[i]; 
            // 分区的盘符名称 
            addText("盘符名称:    " + fs.getDevName()); 
            // 分区的盘符名称 
            addText("盘符路径:    " + fs.getDirName()); 
            addText("盘符标志:    " + fs.getFlags());// 
            // 文件系统类型，比如 FAT32、NTFS 
            addText("盘符类型:    " + fs.getSysTypeName()); 
            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等 
            addText("盘符类型名:    " + fs.getTypeName()); 
            // 文件系统类型 
            addText("盘符文件系统类型:    " + fs.getType()); 
            FileSystemUsage usage = null; 
            usage = sigar.getFileSystemUsage(fs.getDirName()); 
            switch (fs.getType()) { 
            case 0: // TYPE_UNKNOWN ：未知 
                break; 
            case 1: // TYPE_NONE 
                break; 
            case 2: // TYPE_LOCAL_DISK : 本地硬盘 
                // 文件系统总大小 
                addText(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB"); 
                // 文件系统剩余大小 
                addText(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB"); 
                // 文件系统可用大小 
                addText(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB"); 
                // 文件系统已经使用量 
                addText(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB"); 
                double usePercent = usage.getUsePercent() * 100D; 
                // 文件系统资源的利用率 
                addText(fs.getDevName() + "资源的利用率:    " + usePercent + "%"); 
                break; 
            case 3:// TYPE_NETWORK ：网络 
                break; 
            case 4:// TYPE_RAM_DISK ：闪存 
                break; 
            case 5:// TYPE_CDROM ：光驱 
                break; 
            case 6:// TYPE_SWAP ：页面交换 
                break; 
            } 
            addText(fs.getDevName() + "读出：    " + usage.getDiskReads()); 
            addText(fs.getDevName() + "写入：    " + usage.getDiskWrites()); 
        } 
        return; 
    } 

    private void net() throws Exception { 
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList(); 
        for (int i = 0; i < ifNames.length; i++) { 
            String name = ifNames[i]; 
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name); 
            addText("网络设备名:    " + name);// 网络设备名 
            addText("IP地址:    " + ifconfig.getAddress());// IP地址 
            addText("子网掩码:    " + ifconfig.getNetmask());// 子网掩码 
            if ((ifconfig.getFlags() & 1L) <= 0L) { 
                addText("!IFF_UP...skipping getNetInterfaceStat"); 
                continue; 
            } 
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name); 
            addText(name + "接收的总包裹数:" + ifstat.getRxPackets());// 接收的总包裹数 
            addText(name + "发送的总包裹数:" + ifstat.getTxPackets());// 发送的总包裹数 
            addText(name + "接收到的总字节数:" + ifstat.getRxBytes());// 接收到的总字节数 
            addText(name + "发送的总字节数:" + ifstat.getTxBytes());// 发送的总字节数 
            addText(name + "接收到的错误包数:" + ifstat.getRxErrors());// 接收到的错误包数 
            addText(name + "发送数据包时的错误数:" + ifstat.getTxErrors());// 发送数据包时的错误数 
            addText(name + "接收时丢弃的包数:" + ifstat.getRxDropped());// 接收时丢弃的包数 
            addText(name + "发送时丢弃的包数:" + ifstat.getTxDropped());// 发送时丢弃的包数 
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
            addText(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址 
            addText(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址 
            addText(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址 
            addText(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码 
            addText(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息 
            addText(cfg.getName() + "网卡类型" + cfg.getType());// 
        } 
    } 
}