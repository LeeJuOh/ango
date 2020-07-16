package model_utility;

import java.sql.SQLException;

import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class UserBaseRecommendation {

	private static List<RecommendedItem> recommendations;

	public static List<RecommendedItem> getRecommendCategory(Long user_idx, String weather_type) {
		// TODO Auto-generated method stub

		try {
			MysqlDataSource dbsource = new MysqlDataSource();
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			BasicDataSource ds = (BasicDataSource) envCtx.lookup("jdbc/final_ango");
			dbsource = unwrap(ds);
			DataModel model = new MySQLJDBCDataModel(dbsource, weather_type, "user_idx", "cg_idx", "score", "");


			UserSimilarity similarity = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);


//			for (int i = 0; i < neighborhood.getUserNeighborhood(user_idx).length; i++) {
//				System.out.println("이웃 : " + neighborhood.getUserNeighborhood(user_idx)[i]);
//				System.out.println(
//						"유사도 : " + similarity.userSimilarity(user_idx, neighborhood.getUserNeighborhood(user_idx)[i]));
//			}

			Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			Recommender cachingRecommender = new CachingRecommender(recommender);

			recommendations = cachingRecommender.recommend(1, 10);

			for (RecommendedItem recommendation : recommendations) {

				System.out.println(recommendation);
			}

			System.out.println("recommend finish");


		} catch (NoSuchUserException e) {
			// TODO: handle exception
			System.out.println("cold start");
			recommendations = null;
			e.printStackTrace();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return recommendations;

	}

	protected static MysqlDataSource unwrap(BasicDataSource ds) throws SQLException {
		MysqlDataSource mysqlDs = new MysqlDataSource();

		mysqlDs.setUser(ds.getUsername());
		mysqlDs.setPassword(ds.getPassword());
		mysqlDs.setURL(ds.getUrl());
		mysqlDs.setCachePreparedStatements(true);
		mysqlDs.setCachePrepStmts(true);
		mysqlDs.setCacheResultSetMetadata(true);
		mysqlDs.setAlwaysSendSetIsolation(false);
		mysqlDs.setElideSetAutoCommits(true);

		return mysqlDs;
	}

}
