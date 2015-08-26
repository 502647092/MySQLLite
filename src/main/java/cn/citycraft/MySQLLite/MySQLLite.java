/**
 *
 */
package cn.citycraft.MySQLLite;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import cn.citycraft.MySQLLite.config.FileConfig;
import cn.citycraft.MySQLLite.utils.MySqlHelper;

/**
 * MySQLLite主类
 *
 * @author 蒋天蓓 2015年8月25日上午9:30:12
 *
 */
public class MySQLLite extends JavaPlugin {
	public static MySqlHelper mysql = null;
	public FileConfig config;

	String dbHost = null;
	String dbPort = null;
	String dbName = null;
	String dbuserName = null;
	String dbpwd = null;

	public void loadcfg() {
		dbHost = config.getString("Data.MySQL.ip");
		dbPort = config.getString("Data.MySQL.port");
		dbName = config.getString("Data.MySQL.database");
		dbuserName = config.getString("Data.MySQL.username");
		dbpwd = config.getString("Data.MySQL.password");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, final String[] args) {
		switch (args.length) {
		case 0:
			break;
		case 1:
			if (args[0].equalsIgnoreCase("reload")) {
				config.reload();
				loadcfg();
			}
			break;
		case 2:
			if (args[0].equalsIgnoreCase("connect")) {
				dbName = args[1];
				mysql = new MySqlHelper(dbHost, dbPort, dbName, dbuserName, dbpwd);
				sender.sendMessage("开始连接数据库 " + dbName + " ...");
				if (mysql.dbConnection()) {
					sender.sendMessage("数据库" + dbName + "连接成功!");
				} else {
					sender.sendMessage("数据库" + dbName + "连接失败!");
					mysql = null;
				}
				return true;
			}
			if (mysql == null) {
				sender.sendMessage("未连接到数据库...");
				return true;
			}
			switch (args[0]) {
			case "run":
				mysql.runSql(args[1]);
				break;
			case "runfile":
				this.getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
					@Override
					public void run() {
						mysql.runSqlfile(new File(getDataFolder(), args[1]));
					}
				});
				break;
			}
			break;
		}
		return true;
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onLoad() {
		config = new FileConfig(this, new File(getDataFolder(), "config.yml"));
		loadcfg();
	}

}
