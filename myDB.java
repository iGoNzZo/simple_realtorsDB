public class myDB {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "cs157a";
	private static Connection conn = null;
	private static PreparedStatement preparedStatement = null;
	private static Statement statement = null;
	
	private static String[] f_names = {"Steven", "Carly", "Kloe", "Cody", "Danny", "Rob", "Eddie", "Nick", "Liz", "Maria" };
	private static String[] l_names = {"Gonzalez", "Harrell", "Fidone", "Gosponeditch", "Roye", "Baily", "Orozco", "Elmore", "Barrientos", "Guiterez"}; 
	private static String[] comp_name = {"Google", "Yahoo", "Apple", "Samsung", "Sony", "Lenovo", "Microsoft", "McAfee", "Norton" ,"PayPal"};
	private static String[] street = {"170 Miller Rd.", "250 San Lorenzo", "1 Memorial Dr", "1 Lincon St", "2 Chappel Ct", "3 Azul Ct", "2 Main St", "77 Santa Clara St", "22 San Fernando St", "33 4th St"};
	private static String[] city = {"Hollister", "Mountain View", "Santa Clara", "San Jose", "San Francisco", "Oakland", "Palo Alto", "Los Angeles", "Sacramento", "San Jose"};
 	private static String county= "Santa Clara";
 	private static String state = "CA"; 
 	private static int zip = 95111;
 	private static String[] number = {"831-801-8637", "987-654-3211", "987-321-6544", "654-831-9877", "831-207-0990", "831-099-0207", "408-842-7282", "842-408-0207", "831-329-0987", "831-879-4455"};
 	private static int[] salaries = {110, 115, 100, 70, 56, 66, 90, 50, 87, 78};
	
	public myDB() throws SQLException {
		try	{
			Class.forName(JDBC_DRIVER); //Register JDBC Driver
			createDatabase();
			createTable();
			loadIntoTable();
			prepInsert();
			getDbCount();
			getListingsByCity();
			getPayout();
			getNamesWithSalaries();
			getCityMinMaxAvgSalary();
			
		}
		catch(SQLException se){se.printStackTrace(); }
		catch(Exception e){ e.printStackTrace(); }	
	}
	
	private static void createDatabase() throws SQLException {
		System.out.println("Connecting to database...");	// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		String queryDrop = "DROP DATABASE IF EXISTS oneProjectDB;";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		System.out.println("Creating database...");	// Create a database named CS
		String sql = "CREATE DATABASE oneProjectDB;";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.executeUpdate();
		System.out.println("Database created successfully...");

		conn = DriverManager.getConnection(DB_URL+"oneProjectDB", USER, PASS);
	}
	
	private static void createTable() throws SQLException	{
		String queryDrop = "DROP TABLE IF EXISTS USRealtors";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		String createTableSQL = "CREATE TABLE USRealtors("
				+ "f_name VARCHAR(20), "
				+ "l_name VARCHAR(20), "
				+ "comp_name VARCHAR(45), "
				+ "street VARCHAR(30), "
				+ "city VARCHAR(20), "
				+ "county VARCHAR(30), "
				+ "c_state VARCHAR(3), "
				+ "zip INTEGER, "
				+ "phone VARCHAR(13), "
				+ "salary int, "
				+ "PRIMARY KEY(phone));";
		preparedStatement = conn.prepareStatement(createTableSQL);
		preparedStatement.executeUpdate(); 
		System.out.println("Table called USRealtors created successfully...");
	}
	
	public static void loadIntoTable() throws SQLException	{
		String path = System.getProperty("user.dir").replace("\\", "\\\\") + "/company.txt";
		System.out.println("Load data from a file company.txt");
		String loadDataSQL = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE USRealtors "
				+ "LINES TERMINATED BY '\r\n'"; // need to add lines terminated if on windows

		preparedStatement = conn.prepareStatement(loadDataSQL);
		preparedStatement.execute(); 
	}
	
	public static void prepInsert()	throws SQLException	{
		int i;
		for(i = 0; i < f_names.length; i++)	{
			insertIntoTable(f_names[i], l_names[i], comp_name[i], street[i], city[i], county, state, zip, number[i], salaries[i]);
		}
		System.out.println(i + " - Entries Added...");
		printTable();
	}
	
	public static void insertIntoTable(String fname, String lname, String cname, String street, String city, String county, String state, int zip, String phone, int salary) throws SQLException	{
		String sql = null;
		
		sql = "INSERT INTO usrealtors " 
				+ "(f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES"
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, fname);
			preparedStatement.setString(2, lname);
			preparedStatement.setString(3, cname);
			preparedStatement.setString(4, street);
			preparedStatement.setString(5, city);
			preparedStatement.setString(6, county);
			preparedStatement.setString(7, state);
			preparedStatement.setInt(8, zip);
			preparedStatement.setString(9, phone);
			preparedStatement.setInt(10, salary);
			preparedStatement.executeUpdate();			
		}catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void printTable()	throws SQLException{
		ResultSet rs = null;
		try	{
			Statement statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * from usrealtors");
			
		}catch (SQLException e)	{e.printStackTrace();}
		
		while(rs.next())	{
			String fname = rs.getString("f_name");
			String lname = rs.getString("l_name");
			String cname = rs.getString("comp_name");
			String street = rs.getString("street");
			String city = rs.getString("city");
			String county = rs.getString("county");
			String state = rs.getString("c_state");
			int zip = rs.getInt("zip");
			String phone = rs.getString("phone");
			int salary = rs.getInt("salary");
			
			System.out.println(fname + ", " + lname + ", " + cname + ", " + street + ", " + city + ", " + county + ", " + state + ", " + zip + ", " + phone + ", " + salary + "k");
		}
	}
	
	public static void getDbCount()	throws SQLException {
		String sql = null;
		ResultSet rs = null;
		
		sql = "SELECT COUNT(*) as total_realtors "
				+ "FROM usrealtors; ";

		try	{
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			System.out.println();
			while(rs.next())	{
				System.out.println("total realtors: " + rs.getInt("total_realtors"));
			}
		}catch(SQLException e)	{ e.printStackTrace(); }
	}
	
	public static void getListingsByCity() throws SQLException	{
		String sql = null;
		ResultSet rs = null;
		
		sql = "SELECT f_name, l_name, comp_name, street, city, c_state, zip, phone "
				+ "FROM usrealtors "
				+ "ORDER BY city ASC; ";

		try	{
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			System.out.println();
			while(rs.next())	{
				String fname = rs.getString("f_name");
				String lname = rs.getString("l_name");
				String cname = rs.getString("comp_name");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("c_state");
				int zip = rs.getInt("zip");
				String phone = rs.getString("phone");
				
				System.out.println(fname + ", " + lname + ", " + cname + ", " + street + ", " + city + ", " + state + ", " + zip + ", " + phone);
			}
		}catch(SQLException e)	{ e.printStackTrace(); }
	}
	
	public static void getPayout() throws SQLException {
		String sql = null;
		ResultSet rs = null;
		
		sql = "SELECT SUM(salary) as total_payout "
				+ "FROM usrealtors; ";

		try	{
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			System.out.println();
			while(rs.next())	{
				System.out.println("total Payout: " + rs.getInt("total_payout"));
			}
		}catch(SQLException e)	{ e.printStackTrace(); }
	}
	
	public static void getNamesWithSalaries() throws SQLException	{
		String sql = null;
		ResultSet rs = null;
		
		sql = "SELECT f_name, l_name, salary "
				+ "FROM usrealtors "
				+ "ORDER BY salary DESC; ";

		try	{
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			System.out.println();
			while(rs.next())	{
				System.out.println(rs.getString("f_name") + " " + rs.getString("l_name") + ", " + rs.getInt("salary") + "k");
			}
		}catch(SQLException e)	{ e.printStackTrace(); }
	}
	
	public static void getCityMinMaxAvgSalary() throws SQLException	{
		String sql = null;
		ResultSet rs = null;
		
		sql = "SELECT MIN(salary), MAX(salary), AVG(salary), city, c_state "
				+ "FROM usrealtors "
				+ "GROUP BY city "
				+ "ORDER by AVG(salary) ASC; ";

		try	{
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			System.out.println();
			while(rs.next())	{
				System.out.println("Min: " + rs.getInt("MIN(salary)") + "k, Max: " 
					+ rs.getInt("MAX(salary)") + ", AVG: " + rs.getInt("AVG(salary)") + "k, "
					+ rs.getString("city") + ", " + rs.getString("c_state"));
			}
		}catch(SQLException e)	{ e.printStackTrace(); }
	}
}
