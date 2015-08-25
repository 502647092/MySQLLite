package cn.citycraft.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main

{
	private static String tableName;
	private static String columnName;
	private static String columnPassword;
	private static String columnIp;
	private static String columnLastLogin;
	private static String lastlocX;
	private static String lastlocY;
	private static String lastlocZ;
	private static String lastlocWorld;
	private static String columnEmail;
	private static String columnLogged;
	private static String columnID;
	private static File source;
	private static File output;

	public static void main(String[] args) {
		// mySQLColumnName: username
		// mySQLTablename: authme
		// mySQLUsername: authme
		// backend: file
		// mySQLColumnLastLogin: lastlogin
		// mySQLDatabase: authme
		// mySQLPort: '3306'
		// mySQLColumnIp: ip
		// mySQLHost: 127.0.0.1
		// mySQLColumnPassword: password
		// mySQLPassword: '12345'
		// caching: true
		// mySQLlastlocX: x
		// mySQLlastlocY: y
		// mySQLlastlocZ: z
		// mySQLlastlocWorld: world
		// mySQLColumnEmail: email
		// mySQLColumnId: id
		// mySQLColumnLogged: isLogged

		tableName = "authme";
		columnName = "username";
		columnPassword = "password";
		columnIp = "ip";
		columnLastLogin = "lastlogin";
		lastlocX = "x";
		lastlocY = "y";
		lastlocZ = "z";
		lastlocWorld = "world";
		columnEmail = "email";
		columnLogged = "isLogged";
		columnID = "id";

		try {
			source = new File("auths.db");
			source.createNewFile();
			output = new File("authme.sql");
			output.createNewFile();
			BufferedReader br = new BufferedReader(new FileReader(source));
			BufferedWriter sql = new BufferedWriter(new FileWriter(output));
			String createDB = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnID + " INTEGER AUTO_INCREMENT," + columnName
					+ " VARCHAR(255) NOT NULL UNIQUE," + columnPassword + " VARCHAR(255) NOT NULL," + columnIp
					+ " VARCHAR(40) NOT NULL DEFAULT '127.0.0.1'," + columnLastLogin + " BIGINT NOT NULL DEFAULT '" + System.currentTimeMillis()
					+ "'," + lastlocX + " DOUBLE NOT NULL DEFAULT '0.0'," + lastlocY + " DOUBLE NOT NULL DEFAULT '0.0'," + lastlocZ
					+ " DOUBLE NOT NULL DEFAULT '0.0'," + lastlocWorld + " VARCHAR(255) DEFAULT 'world'," + columnEmail
					+ " VARCHAR(255) DEFAULT 'your@email.com'," + columnLogged + " SMALLINT NOT NULL DEFAULT '0',"
					+ "CONSTRAINT table_const_prim PRIMARY KEY (" + columnID + "));";
			sql.write(createDB);

			int count = 0;
			String line;
			while ((line = br.readLine()) != null) {
				sql.newLine();
				String[] arg = line.split(":");
				String newline;
				if (arg.length == 4) {
					newline = "INSERT INTO " + tableName + "(" + columnName + "," + columnPassword + "," + columnIp + "," + columnLastLogin + ","
							+ lastlocX + "," + lastlocY + "," + lastlocZ + "," + lastlocWorld + "," + columnEmail + "," + columnLogged
							+ ") VALUES ('" + arg[0] + "', '" + arg[1] + "', '" + arg[2] + "', " + arg[3]
							+ ", 0.0, 0.0, 0.0, 'world', 'your@email.com', 0);";
				} else {
					if (arg.length == 7) {
						newline = "INSERT INTO " + tableName + "(" + columnName + "," + columnPassword + "," + columnIp + "," + columnLastLogin + ","
								+ lastlocX + "," + lastlocY + "," + lastlocZ + "," + lastlocWorld + "," + columnEmail + "," + columnLogged
								+ ") VALUES ('" + arg[0] + "', '" + arg[1] + "', '" + arg[2] + "', " + arg[3] + ", " + arg[4] + ", " + arg[5] + ", "
								+ arg[6] + ", 'world', 'your@email.com', 0);";
					} else {
						if (arg.length == 8) {
							newline = "INSERT INTO " + tableName + "(" + columnName + "," + columnPassword + "," + columnIp + "," + columnLastLogin
									+ "," + lastlocX + "," + lastlocY + "," + lastlocZ + "," + lastlocWorld + "," + columnEmail + "," + columnLogged
									+ ") VALUES ('" + arg[0] + "', '" + arg[1] + "', '" + arg[2] + "', " + arg[3] + ", " + arg[4] + ", " + arg[5]
									+ ", " + arg[6] + ", '" + arg[7] + "', 'your@email.com', 0);";
						} else {
							if (arg.length == 9)
								newline = "INSERT INTO " + tableName + "(" + columnName + "," + columnPassword + "," + columnIp + ","
										+ columnLastLogin + "," + lastlocX + "," + lastlocY + "," + lastlocZ + "," + lastlocWorld + "," + columnEmail
										+ "," + columnLogged + ") VALUES ('" + arg[0] + "', '" + arg[1] + "', '" + arg[2] + "', " + arg[3] + ", "
										+ arg[4] + ", " + arg[5] + ", " + arg[6] + ", '" + arg[7] + "', '" + arg[8] + "', 0);";
							else
								newline = "";
						}
					}
				}
				if (newline != "")
					sql.write(newline);
				if (arg.length != 0) {
					count++;
					System.out.println("转换用户: " + arg[0] + " 已经转换: " + count + "个用户...");
				}
			}
			System.out.println("成功转换 " + count + " 个用户数据!");
			sql.close();
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("数据库文件未找到!");
		} catch (IOException ex) {
			System.out.println("IO数据错误!");
		}
	}
}
