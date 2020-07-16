package model_utility;

import java.sql.SQLException;

import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
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

public class Evaluator {

	private static List<RecommendedItem> recommendations;

	public static void getRecommendCategory(Long user_idx, String weather_type) {
		// TODO Auto-generated method stub

		try {
			MysqlDataSource dbsource = new MysqlDataSource();
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			BasicDataSource ds = (BasicDataSource) envCtx.lookup("jdbc/final_ango");
			dbsource = unwrap(ds);
			final DataModel model = new MySQLJDBCDataModel(dbsource, weather_type, "user_idx", "cg_idx", "score", "");

			RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
			RecommenderBuilder builder = new RecommenderBuilder() {

				@Override
				public Recommender buildRecommender(DataModel datamodel) throws TasteException {
					// TODO Auto-generated method stub
					//UserSimilarity similarity = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
					UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
					UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
					return new GenericUserBasedRecommender(model, neighborhood, similarity);
				}
			};

			double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
			System.out.println("평가 값 : " + result);

		} catch (NoSuchUserException e) {
			// TODO: handle exception
			System.out.println("cold start");
			recommendations = null;
			e.printStackTrace();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

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
