import java.sql.SQLException;

public class DatabaseAccess {
	
	static DBConnection db = new DBConnection(""
			+ "jdbc:sqlserver://127.0.0.1:3306;"
			+ "databaseName=nutritiondb;"
			+ "user=root;"
			+ "password=password;"
			);
	
	public static User[] getAllUsers() {
		try {
			return db.getAllUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User[] getAllUsersWithoutTrainer() {
		try {
			return db.getAllUsersWithoutTrainer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void newUser(String theFirst, String theLast, String theUser, 
			String thePass) {
		try {
			db.newUser(theFirst, theLast, theUser, thePass);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static User[] getMyUsers(int theID) {
		try {
			return db.getMyUsers(theID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getMyUserID(String theUser) {
		try {
			return db.getMyUserID(theUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int getUsersTrainerID(String theUser) {
		try {
			return db.getUsersTrainerID(theUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int getMyTrainerID(String theTrainer) {
		try {
			return db.getMyTrainerID(theTrainer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static Trainer[] getTrainers() {
		try {
			return db.getAllTrainers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addTrainer(int theUserID, int theTrainerID) {
		try {
			db.addTrainer(theUserID, theTrainerID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DietDiary[] getMyDietDiaries(String theUsername) {
		try {
			return db.getMyDietDiaries(theUsername);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addDietDiaryEntry(int theFoodID, short theQuantity, String theUser) {
		try {
			db.addDietDiaryEntry(theFoodID, theQuantity, theUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DietDiary getDietDiaryEntry(int theDiaryID) {
		try {
			return db.getDietDiaryEntry(theDiaryID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void editDietDiaryEntry(int theID, String theFood, short theCalories, 
			short theProtein, short theCarbs, short theFat, short theQuantity) {
		try {
			db.editDietDiaryEntry(theID, theFood, theCalories, theProtein, theCarbs, theFat, 
					theQuantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateDietDiaryEntryQuantity(int theDiaryID, short theQuantity) {
		try {
			db.updateDietDiaryEntryQuantity(theDiaryID, theQuantity); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteDietDiaryEntry(int theDiaryID) {
		try {
			db.deleteDietDiaryEntry(theDiaryID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DietDiary viewDailyPortions(String theUsername) {
		try {
			return db.viewDailyPortions(theUsername);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Plan getUserPlan(String theUsername) {
		try {
			return db.getUserPlan(theUsername);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void updateUserPlan(int theTrainerID, String theUsername, short theCalories, 
			short theProtein, short theCarbs, short theFat, short theWater) {
		try {
			db.updateUserPlan(theTrainerID, theUsername, theCalories, theProtein, theCarbs, 
					theFat, theWater);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static FoodDB[] searchFoods(String theSearch) {
		try {
			return db.searchForFood(theSearch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static FoodDB[] getFoods() {
		try {
			return db.getAllFoods();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static WaterDiary viewDailyWater(String theUsername) {
		try {
			return db.viewDailyWater(theUsername);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static WaterDiary[] viewMyWaterDiary(String theUsername) {
		try {
			return db.viewMyWaterDiary(theUsername);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addWaterDiaryEntry(String theUsername, short theQuantity) {
		try {
			db.addWaterDiaryEntry(theUsername, theQuantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteWaterDiaryEntry(int theID) {
		try {
			db.deleteWaterDiaryEntry(theID);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void editWaterDiaryEntry(int theID, short theQuantity) {
		try {
			db.editWaterDiaryEntry(theID, theQuantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
