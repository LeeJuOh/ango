
<%@page import="model_utility.*"%>
<%@page import="org.apache.mahout.cf.taste.recommender.RecommendedItem"%>
<%@page import="java.util.List"%>
<%@page import="model_utility.UserBaseRecommendation"%>
<%@page
	import="org.apache.mahout.cf.taste.recommender.UserBasedRecommender"%>
<%@page import="dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<body>
	<form name="frmName" method="post" enctype="multipart/form-data"
		action="viewPage.jsp">
		<input type="file" name="uploadFile"><br /> <input
			type="submit" value="UPLOAD"><br />
	</form>
</body>
</html>


<%
	Evaluator t = new Evaluator();
	t.getRecommendCategory((long)1, "type_test");
	
	UserBaseRecommendation u = new UserBaseRecommendation();
	u.getRecommendCategory((long)1, "type_test");
	
	
	
%>