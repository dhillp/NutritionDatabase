import java.sql.Date;

public class DietDiary {
	
	public int id;
	public Date entry_date;
	public String food;
	public short calories;
	public short protein;
	public short carbs;
	public short fat;
	public short quantity;
	public int food_id;
	public int user_id;
	
	public String toString() {
		return entry_date.toString() + ' ' + food;
	}
}
