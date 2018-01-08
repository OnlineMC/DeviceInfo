package cn.onlinemc.DriverInfo;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class DriverInfo extends JavaPlugin {

	private static DriverInfo driverInfo;
	private String infoListFolder = getDataFolder().getAbsolutePath() + "/InfoList/";
	private String libFolder = getDataFolder().getParent() + "/DriverInfo/lib/";
	private static boolean needReload = false;// 是否需要重载插件
	private final static String[] libListArr = new String[] { ".sigar_shellrc", "libsigar-amd64-freebsd-6.so",
			"libsigar-amd64-linux.so", "libsigar-amd64-solaris.so", "libsigar-ia64-hpux-11.sl",
			"libsigar-ia64-linux.so", "libsigar-pa-hpux-11.sl", "libsigar-ppc-aix-5.so", "libsigar-ppc-linux.so",
			"libsigar-ppc64-aix-5.so", "libsigar-ppc64-linux.so", "libsigar-s390x-linux.so",
			"libsigar-sparc-solaris.so", "libsigar-sparc64-solaris.so", "libsigar-universal-macosx.dylib",
			"libsigar-universal64-macosx.dylib", "libsigar-x86-freebsd-5.so", "libsigar-x86-freebsd-6.so",
			"libsigar-x86-linux.so", "libsigar-x86-solaris.so", "sigar-amd64-winnt.dll", "sigar-x86-winnt.dll",
			"sigar-x86-winnt.lib", "sigar.jar" };

	@Override
	public void onEnable() {
		driverInfo = this;
		new File(infoListFolder).mkdirs();// 创建信息表文件夹

		init();

	}

	public void init() {
		File lib = new File(libFolder);
		lib.mkdirs();// 创建library文件夹
		for (String str : libListArr) {
			File libFile = new File(libFolder + str);
			if (!libFile.exists()) {
				needReload = true;
				InputStream in = DriverInfo.class.getResourceAsStream("/libs/" + str);// 读入资源文件
				OutputStream out = null;
				try {
					out = new FileOutputStream(libFile);
					byte[] tmp = null;
					try {
						tmp = readStream(in);
						getLogger().info("解压资源文件" + libFile.getName() + "...");
					} catch (Exception e1) {
						getLogger().warning("解压资源文件" + libFile.getName() + "错误");
						getLogger().warning("插件卸载!");
						getPluginLoader().disablePlugin(this);
						e1.printStackTrace();
						return;
					}

					try {
						if (tmp == null) {
							throw new NullPointerException();
						}
						out.write(tmp);
						getLogger().info("ok copy did");
					} catch (IOException e) {
						getLogger().info("oh copy failed");
						e.printStackTrace();
						
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {

					try {
						in.close();
						out.close();
					} catch (IOException e) {
						in = null;
						out = null;
					}
				}

			}
		}
		if (needReload) {
			for (int i = 0; i < 3; i++) {
				getServer().getConsoleSender().sendMessage("§e检测到依赖文件更新,需要重启服务器生效");
			}
			getServer().shutdown();
		}
	}

	public static byte[] readStream(InputStream inStream) throws Exception {// 读取InputStream
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.isOp() | !sender.hasPermission("driverinfo.use")) {
			sender.sendMessage("§e你需要 driverinfo.use 权限来执行这个");
			return false;
		}
			
			String time = new SimpleDateFormat("YYYYMMDD_HHmmss").format(new Date());
			File outFile = new File(infoListFolder + time + ".txt");
			BufferedWriter output = null;
			try {
				if (!outFile.createNewFile()) {
					sender.sendMessage("§e文件已经存在");
				}
				output = new BufferedWriter(new FileWriter(outFile, true));

				Infomation info = null;
				if(args.length>0){
					info = new Infomation(args[0]);
				}else{
					info = new Infomation();
				}
				info.init();

				output.write(info.getInfoString());
				sender.sendMessage("§eDriver信息已保存至 §a" + outFile.toString());

			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					output.close();
				} catch (IOException e) {
					output = null;
				}
			}

			return true;
		
	}

	public static DriverInfo getInstance() {
		return driverInfo;
	}

}
