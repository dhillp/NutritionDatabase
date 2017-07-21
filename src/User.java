
public class User {
	
	public int id;
	public String fname;
	public String lname;
	public String username;
	public String password;
	public int trainer_id;
	public int plan_id;
	
	public String toString() {
		return username + ", " + lname + ", " + fname;
	}
}