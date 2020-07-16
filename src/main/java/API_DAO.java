import dao.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;

public class API_DAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	private String dbURL;
	private String dbID;
	private String dbPassword;

	public API_DAO() throws ClassNotFoundException, SQLException {
		// 생성자
		Class.forName("com.mysql.jdbc.Driver");
		dbURL = "jdbc:mysql://localhost:3306/ango2?useUnicode=true&characterEncoding=utf8";
		dbID = "root";
		dbPassword = "1234";

	}

	public void insertPlaceID(PlaceObject placeObject) {

		try {

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

			for (int i = 0; i < placeObject.results.size(); i++) {
				String sql = "INSERT INTO place(place_id, place_nm, addr) VALUES(?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, placeObject.results.get(i).place_id);
				pstmt.setString(2, placeObject.results.get(i).name);
				pstmt.setString(3, placeObject.results.get(i).formatted_address);

				int sql_flag = pstmt.executeUpdate();
				if (sql_flag > 0) {

					System.out.println("insert " + placeObject.results.get(i).name);

				} else {

					System.out.println("insert fail");
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void insertPlaceID2(PlaceObject_lastPage placeObject) {

		try {

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

			for (int i = 0; i < placeObject.results.size(); i++) {
				String sql = "INSERT INTO place(place_id, place_nm, addr) VALUES(?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, placeObject.results.get(i).place_id);
				pstmt.setString(2, placeObject.results.get(i).name);
				pstmt.setString(3, placeObject.results.get(i).formatted_address);

				int sql_flag = pstmt.executeUpdate();
				if (sql_flag > 0) {

					System.out.println("insert " + placeObject.results.get(i).name);

				} else {

					System.out.println("insert fail");
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void insertReview(ArrayList<ReviewObject> reviewObjects, String place_id) {

		try {

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

			for (int i = 0; i < reviewObjects.size(); i++) {

				ReviewObject reviewObject = reviewObjects.get(i);

				for (int j = 0; j < reviewObject.reviews.size(); j++) {

					String sql = "INSERT INTO user_review(reviewer, datetime, rating, text, place_id) VALUES(?,?,?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, reviewObject.reviews.get(j).reviewer);
					pstmt.setString(2, reviewObject.reviews.get(j).datetime);
					pstmt.setFloat(3, Float.valueOf(reviewObject.reviews.get(j).rating));
					pstmt.setString(4, reviewObject.reviews.get(j).text);
					pstmt.setString(5, place_id);

					int sql_flag = pstmt.executeUpdate();
					if (sql_flag > 0) {

						System.out.println("insert " + reviewObject.reviews.get(j).reviewer);

					} else {

						System.out.println("insert fail");
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public ArrayList<String> getPlaceID() {

		ArrayList<String> place_list = new ArrayList<String>();

		try {

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

			String sql = "select distinct place_id from place";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				String place_id = rs.getString("place_id");
				place_list.add(place_id);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return place_list;

	}

}
