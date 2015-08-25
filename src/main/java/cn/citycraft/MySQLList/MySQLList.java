/**
 * 
 */
package cn.citycraft.MySQLList;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import cn.citycraft.MySQLList.config.FileConfig;
import cn.citycraft.MySQLList.utils.MySqlHelper;

/**
 * MySQLLite主类
 * 
 * @author 蒋天蓓
 *         2015年8月25日上午9:30:12
 * 
 */
public class MySQLList extends JavaPlugin {
	public static MySqlHelper mysql = null;
	public FileConfig config;

	@Override
	public void onLoad() {
		config = new FileConfig(this, new File(getDataFolder(), "config.yml"));
	}

	@Override
	public void onEnable() {
		if (config.getString("Data.FileSystem").equalsIgnoreCase("MySQL")) {
			// 连接数据库用到的一些参数.
			String dbHost = config.getString("Data.MySQL.ip");
			String dbPort = config.getString("Data.MySQL.port");
			String dbName = config.getString("Data.MySQL.database");
			String dbuserName = config.getString("Data.MySQL.username");
			String dbpwd = config.getString("Data.MySQL.password");
			this.getLogger().info("开始连接数据库" + dbName + "...");
			mysql = new MySqlHelper(dbHost, dbPort, dbName, dbuserName, dbpwd);
			if (mysql.dbConnection()) {
				this.getLogger().warning("数据库" + dbName + "连接成功!");
			} else {
				this.getLogger().warning("数据库" + dbName + "连接失败!");
				Bukkit.getPluginManager().disablePlugin(this);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			switch (args[0]) {
			case "run":
				mysql.runSql(args[1]);
				break;
			case "runfile":
				mysql.runSqlfile(new File(getDataFolder(), args[1]));
				break;
			}
			break;
		}
		return true;
	}

}
