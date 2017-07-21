import java.sql.*;
import java.util.Vector;

public class DBConnection {
	
	private Connection Conn;
	
	DBConnection(String db_url) {
		String url1 = "jdbc:mysql://127.0.0.1:3306/nutritiondb";
		String user = "root";
		String password = "password";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Conn = DriverManager.getConnection(url1, user, password);
			System.out.println("connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User[] getAllUsers() throws SQLException {
		User userArray[];
		Vector<User> vUser = new Vector<User>();
		
		Statement sm = Conn.createStatement();
		String queryString = "select * from user;";
		ResultSet rset = sm.executeQuery(queryString);
		while (rset.next()) {
			User u = new User();
			u.id = rset.getInt("id");
			u.fname = rset.getString("fname");
			u.lname = rset.getString("lname");
			u.username = rset.getString("username");
			u.password = rset.getString("password");
			u.trainer_id = rset.getInt("trainer_id");
			u.plan_id = rset.getInt("plan_id");
			vUser.add(u);
		}
		userArray = new User[vUser.size()];
		vUser.toArray(userArray);
		return userArray;
	}
	
	public User[] getAllUsersWithoutTrainer() throws SQLException {
		User userArray[];
		Vector<User> vUser = new Vector<User>();
		
		Statement sm = Conn.createStatement();
		String queryString = "select * from user where trainer_id = 1;";
		ResultSet rset = sm.executeQuery(queryString);
		while (rset.next()) {
			User u = new User();
			u.id = rset.getInt("id");
			u.fname = rset.getString("fname");
			u.lname = rset.getString("lname");
			u.username = rset.getString("username");
			u.password = rset.getString("password");
			u.trainer_id = rset.getInt("trainer_id");
			u.plan_id = rset.getInt("plan_id");
			vUser.add(u);
		}
		userArray = new User[vUser.size()];
		vUser.toArray(userArray);
		return userArray;
	}
	
	public void newUser(String theFirst, String theLast, String theUser, String thePass) 
			throws SQLException{
		String updateString = "insert into user (fname, lname, username, password, "
				+ "trainer_id, plan_id) values (?, ?, ?, ?, 1, 1)";
		PreparedStatement sm = Conn.prepareStatement(updateString);
		sm.setString(1, theFirst);
		sm.setString(2, theLast);
		sm.setString(3, theUser);
		sm.setString(4, thePass);
		sm.executeUpdate();
	}
	
	public User[] getMyUsers(int theID) throws SQLException {
		User userArray[];
		Vector<User> vUser = new Vector<User>();
		String queryString = "select u.* from user u join trainer t on u.trainer_id = t.id"
					+ " where trainer_id = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, Integer.toString(theID));
		ResultSet rset = sm.executeQuery();
		while (rset.next()) {
			User u = new User();
			u.id = rset.getInt("id");
			u.fname = rset.getString("fname");
			u.lname = rset.getString("lname");
			u.username = rset.getString("username");
			u.password = rset.getString("password");
			u.trainer_id = rset.getInt("trainer_id");
			u.plan_id = rset.getInt("plan_id");
			vUser.add(u);
		}
		userArray = new User[vUser.size()];
		vUser.toArray(userArray);
		return userArray;
	}
	
	public int getMyUserID(String theUser) throws SQLException {
		String queryString = "select id from user where username = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theUser);
		ResultSet rset = sm.executeQuery();
		rset.next();
		int result = rset.getInt("id");
		return result;
	}
	
	public int getUsersTrainerID(String theUser) throws SQLException {
		String queryString = "select trainer_id from user where username = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theUser);
		ResultSet rset = sm.executeQuery();
		rset.next();
		int result = rset.getInt("trainer_id");
		return result;
	}
	
	public int getMyTrainerID(String theTrainer) throws SQLException {
		String queryString = "select id from trainer where username = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theTrainer);
		ResultSet rset = sm.executeQuery();
		rset.next();
		int result = rset.getInt("id");
		return result;
	}
	
	public Trainer[] getAllTrainers() throws SQLException {
		Trainer trainerArray[];
		Vector<Trainer> vTrainer = new Vector<Trainer>();
		
		Statement sm = Conn.createStatement();
		String queryString = "select * from trainer;";
		ResultSet rset = sm.executeQuery(queryString);
		while (rset.next()) {
			Trainer t = new Trainer();
			t.id = rset.getInt("id");
			t.fname = rset.getString("fname");
			t.lname = rset.getString("lname");
			t.username = rset.getString("username");
			t.password = rset.getString("password");
			vTrainer.add(t);
		}
		trainerArray = new Trainer[vTrainer.size()];
		vTrainer.toArray(trainerArray);
		return trainerArray;
	}
	
	public void addTrainer(int theUserID, int theTrainerID) throws SQLException {
		String updateString = "update user set trainer_id = ? where id = ?";
		PreparedStatement sm = Conn.prepareStatement(updateString);
		sm.setString(1, Integer.toString(theTrainerID));
		sm.setString(2, Integer.toString(theUserID));
		sm.executeUpdate();
		String updateString2 = "update plan set trainer_id = ? where id = "
				+ "(select plan_id from user u where u.id = ?)";
		PreparedStatement sm2 = Conn.prepareStatement(updateString2);
		sm2.setString(1, Integer.toString(theTrainerID));
		sm2.setString(2, Integer.toString(theUserID));
		sm2.executeUpdate();
	}
	
	public DietDiary[] getMyDietDiaries(String theUsername) throws SQLException {
		DietDiary diaryArray[];
		Vector<DietDiary> vDiary = new Vector<DietDiary>();
		String queryString = "select d.id, d.entry_date, d.food, sum(d.calories * d.quantity) "
				+ "as calories, sum(d.protein * d.quantity) as protein, sum(d.carbs * "
				+ "d.quantity) as carbs, sum(d.fat * d.quantity) as fat, d.quantity, "
				+ "d.food_id, d.user_id from diet_diary d join user u on u.id = d.user_id "
				+ "where username = ? group by d.id order by d.id desc";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theUsername);
		ResultSet rset = sm.executeQuery();
		while (rset.next()) {
			DietDiary d = new DietDiary();
			d.id = rset.getInt("id");
			d.entry_date = rset.getDate("entry_date");
			d.food = rset.getString("food");
			d.calories = rset.getShort("calories");
			d.protein = rset.getShort("protein");
			d.carbs = rset.getShort("carbs");
			d.fat = rset.getShort("fat");
			d.quantity = rset.getShort("quantity");
			d.food_id = rset.getInt("food_id");
			d.user_id = rset.getInt("user_id");
			vDiary.add(d);
		}
		diaryArray = new DietDiary[vDiary.size()];
		vDiary.toArray(diaryArray);
		return diaryArray;
	}
	
	public void addDietDiaryEntry(int theFoodID, short theQuantity, String theUser) 
			throws SQLException {
		String queryString = "select f.* from food_db f where id = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, Integer.toString(theFoodID));
		ResultSet rset = sm.executeQuery();
		rset.next();
		String updateString = "insert into diet_diary (entry_date, food, calories, protein, "
				+ "carbs, fat, quantity, food_id, user_id) values (CURDATE(), ?, ?, ?, ?, ?,"
				+ " ?, (select id from food_db where name = ?), "
				+ "(select id from user where username = ?))";
		PreparedStatement sm2 = Conn.prepareStatement(updateString);
		sm2.setString(1, rset.getString("name"));
		sm2.setString(2, Short.toString(rset.getShort("calories")));
		sm2.setString(3, Short.toString(rset.getShort("protein")));
		sm2.setString(4, Short.toString(rset.getShort("carbs")));
		sm2.setString(5, Short.toString(rset.getShort("fat")));
		sm2.setString(6, Short.toString(theQuantity));
		sm2.setString(7, rset.getString("name"));
		sm2.setString(8, theUser);
		sm2.executeUpdate();
	}
	
	public DietDiary getDietDiaryEntry(int theDiaryID) throws SQLException{
		String queryString = "select d.* from diet_diary d where id = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, Integer.toString(theDiaryID));
		ResultSet rset = sm.executeQuery();
		rset.next();
		DietDiary d = new DietDiary();
		d.id = rset.getInt("id");
		d.entry_date = rset.getDate("entry_date");
		d.food = rset.getString("food");
		d.calories = rset.getShort("calories");
		d.protein = rset.getShort("protein");
		d.carbs = rset.getShort("carbs");
		d.fat = rset.getShort("fat");
		d.quantity = rset.getShort("quantity");
		d.food_id = rset.getInt("food_id");
		d.user_id = rset.getInt("user_id");
		return d;
	}
	
	public void editDietDiaryEntry(int theID, String theFood, short theCalories, 
			short theProtein, short theCarbs, short theFat, short theQuantity) 
					throws SQLException {
		String queryString = "select d.* from diet_diary d where id = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, Integer.toString(theID));
		ResultSet rset = sm.executeQuery();
		rset.next();
		String updateString = "update diet_diary set food = ?, calories = ?, protein = ?, "
				+ "carbs = ?, fat = ?, quantity = ?, food_id = null where id = ?";
		PreparedStatement sm2 = Conn.prepareStatement(updateString);
		sm2.setString(1, theFood);
		sm2.setString(2, Short.toString(theCalories));
		sm2.setString(3, Short.toString(theProtein));
		sm2.setString(4, Short.toString(theCarbs));
		sm2.setString(5, Short.toString(theFat));
		sm2.setString(6, Short.toString(theQuantity));
		sm2.setString(7, Integer.toString(theID));
		sm2.executeUpdate();
	}
	
	public void updateDietDiaryEntryQuantity(int theDiaryID, short theQuantity) 
			throws SQLException {
		String queryString = "select d.* from diet_diary d where id = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, Integer.toString(theDiaryID));
		ResultSet rset = sm.executeQuery();
		rset.next();
		String updateString = "update diet_diary set quantity = ? where id = ?";
		PreparedStatement sm2 = Conn.prepareStatement(updateString);
		sm2.setString(1, Short.toString(theQuantity));
		sm2.setString(2, Integer.toString(theDiaryID));
		sm2.executeQuery();
	}
	
	public void deleteDietDiaryEntry(int theDiaryID) throws SQLException {
		String updateString = "delete from diet_diary where id = ?";
		PreparedStatement sm = Conn.prepareStatement(updateString);
		sm.setString(1, Integer.toString(theDiaryID));
		sm.executeUpdate();
	}
	
	public DietDiary viewDailyPortions(String theUsername) throws SQLException{
		String queryString = "select entry_date, sum(calories * quantity) as calories,"
				+ " sum(protein * quantity) as protein, sum(carbs * quantity)"
				+ " as carbs, sum(fat * quantity) as fat from diet_diary join user u on"
				+ " user_id = u.id where user_id = (select id from user where username = ?)"
				+ " and entry_date = CURDATE();";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theUsername);
		ResultSet rset = sm.executeQuery();
		rset.next();
		DietDiary d = new DietDiary();
		d.entry_date = rset.getDate("entry_date");
		d.calories = rset.getShort("calories");
		d.protein = rset.getShort("protein");
		d.carbs = rset.getShort("carbs");
		d.fat = rset.getShort("fat");
		return d;
	}
	
	public Plan getUserPlan(String theUsername) throws SQLException {
		String queryString = "select * from plan p join user u on p.id = u.plan_id where "
				+ "username = ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theUsername);
		ResultSet rset = sm.executeQuery();
		rset.next();
		Plan p = new Plan();
		p.id = rset.getInt("id");
		p.trainer_id = rset.getInt("trainer_id");
		p.calories = rset.getShort("calories");
		p.protein = rset.getShort("protein");
		p.carbs = rset.getShort("carbs");
		p.fat = rset.getShort("fat");
		p.water = rset.getShort("water");
		return p;
	}
	
	public void updateUserPlan(int theTrainerID, String theUsername, short theCalories, 
			short theProtein, short theCarbs, short theFat, short theWater) 
					throws SQLException {
		String queryString = "select plan_id from user where username = ?";
		PreparedStatement sm1 = Conn.prepareStatement(queryString);
		sm1.setString(1, theUsername);
		ResultSet rset = sm1.executeQuery();
		rset.next();
		int planID = rset.getInt("plan_id");
		if (planID == 1) {
			String updateString1 = "insert into plan (trainer_id) values (?);";
			String updateString2 = "update user set plan_id = LAST_INSERT_ID() where "
					+ "username = ?";
			PreparedStatement sm2 = Conn.prepareStatement(updateString1);
			sm2.setString(1, Integer.toString(theTrainerID));
			PreparedStatement sm3 = Conn.prepareStatement(updateString2);
			sm3.setString(1, theUsername);
			sm2.executeUpdate();
			sm3.executeUpdate();
		}
		String updateString3 = "update plan set calories = ?, protein = ?, carbs = ?, fat = ?,"
				+ " water = ? where id = (select plan_id from user where username = ?)";
		PreparedStatement sm4 = Conn.prepareStatement(updateString3);
		sm4.setString(1, Short.toString(theCalories));
		sm4.setString(2, Short.toString(theProtein));
		sm4.setString(3, Short.toString(theCarbs));
		sm4.setString(4, Short.toString(theFat));
		sm4.setString(5, Short.toString(theWater));
		sm4.setString(6, theUsername);
		sm4.executeUpdate();
	}
	
	public FoodDB[] searchForFood(String theSearch) throws SQLException {
		FoodDB FoodDBArray[];
		Vector<FoodDB> vFoodDB = new Vector<FoodDB>();
		String queryString = "select f.* from food_db f where f.name like ?";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, '%'+ theSearch +'%');
		ResultSet rset = sm.executeQuery();
		while (rset.next()) {
			FoodDB f = new FoodDB();
			f.id = rset.getInt("id");
			f.name = rset.getString("name");
			f.calories = rset.getShort("calories");
			f.protein = rset.getShort("protein");
			f.carbs = rset.getShort("carbs");
			f.fat = rset.getShort("fat");
			f.portion_size = rset.getString("portion_size");
			vFoodDB.add(f);
		}
		FoodDBArray = new FoodDB[vFoodDB.size()];
		vFoodDB.toArray(FoodDBArray);
		return FoodDBArray;
	}
	
	public FoodDB[] getAllFoods() throws SQLException {
		FoodDB FoodDBArray[];
		Vector<FoodDB> vFoodDB = new Vector<FoodDB>();
		
		Statement sm = Conn.createStatement();
		String queryString = "select * from food_db;";
		ResultSet rset = sm.executeQuery(queryString);
		while (rset.next()) {
			FoodDB f = new FoodDB();
			f.id = rset.getInt("id");
			f.name = rset.getString("name");
			f.calories = rset.getShort("calories");
			f.protein = rset.getShort("protein");
			f.carbs = rset.getShort("carbs");
			f.fat = rset.getShort("fat");
			f.portion_size = rset.getString("portion_size");
			vFoodDB.add(f);
		}
		FoodDBArray = new FoodDB[vFoodDB.size()];
		vFoodDB.toArray(FoodDBArray);
		return FoodDBArray;
	}
	
	public WaterDiary viewDailyWater(String theUsername) throws SQLException {
		WaterDiary w = new WaterDiary();
		String queryString = "select entry_date, sum(quantity) as quantity from water_diary "
				+ "where user_id = (select id from user where username = ?) and entry_date = "
				+ "CURDATE()";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theUsername);
		ResultSet rset = sm.executeQuery();
		rset.next();
		w.entry_date = rset.getDate("entry_date");
		w.quantity = rset.getShort("quantity");
		return w;
	}
	
	public WaterDiary[] viewMyWaterDiary(String theUsername) throws SQLException {
		WaterDiary waterArray[];
		Vector<WaterDiary> vWaterDiary = new Vector<WaterDiary>();
		String queryString = "select * from water_diary where user_id = (select id from user "
				+ "where username = ?) order by id desc";
		PreparedStatement sm = Conn.prepareStatement(queryString);
		sm.setString(1, theUsername);
		ResultSet rset = sm.executeQuery();
		while (rset.next()) {
			WaterDiary w = new WaterDiary();
			w.id = rset.getInt("id");
			w.entry_date = rset.getDate("entry_date");
			w.quantity = rset.getShort("quantity");
			w.user_id = rset.getInt("user_id");
			vWaterDiary.add(w);
		}
		waterArray = new WaterDiary[vWaterDiary.size()];
		vWaterDiary.toArray(waterArray);
		return waterArray;
	}
	
	public void addWaterDiaryEntry(String theUsername, short theQuantity) throws SQLException {
		String updateString = "insert into water_diary (entry_date, quantity, user_id) "
				+ "values (CURDATE(), ?, (select id from user where username = ?));";
		PreparedStatement sm = Conn.prepareStatement(updateString);
		sm.setString(1, Short.toString(theQuantity));
		sm.setString(2, theUsername);
		sm.executeUpdate();
	}
	
	public void deleteWaterDiaryEntry(int theID) throws SQLException {
		String updateString = "delete from water_diary where id = ?";
		PreparedStatement sm = Conn.prepareStatement(updateString);
		sm.setString(1, Integer.toString(theID));
		sm.executeUpdate();
	}
	
	public void editWaterDiaryEntry(int theID, short theQuantity) throws SQLException {
		String updateString = "update water_diary set quantity = ? where id = ?";
		PreparedStatement sm = Conn.prepareStatement(updateString);
		sm.setString(1, Short.toString(theQuantity));
		sm.setString(2, Integer.toString(theID));
		sm.executeUpdate();
	}
	
}
