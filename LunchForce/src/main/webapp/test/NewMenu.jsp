<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<form action="NewMenuProc.jsp">
	메뉴명 : <input type="text" name="menuName"/><br>
	가격 : <input type="text" name="price"/><br>
	설명 : <input type="text" name="notice"/><br>
	사진 : <input type="text" name="pic"/><br>
	
	알러지 : <br>
	메밀<input type="checkbox" name="allergy" value="1"/>
	밀<input type="checkbox" name="allergy" value="10"/>
	대두<input type="checkbox" name="allergy" value="100"/>
	호두<input type="checkbox" name="allergy" value="1000"/>
	땅콩<input type="checkbox" name="allergy" value="10000"/>
	복숭아<input type="checkbox" name="allergy" value="100000"/>
	토마토<input type="checkbox" name="allergy" value="1000000"/>
	돼지고기<input type="checkbox" name="allergy" value="10000000"/>
	난류<input type="checkbox" name="allergy" value="100000000"/>
	우유<input type="checkbox" name="allergy" value="1000000000"/>
	닭고기<input type="checkbox" name="allergy" value="10000000000"/>
	쇠고기<input type="checkbox" name="allergy" value="100000000000"/>
	새우<input type="checkbox" name="allergy" value="1000000000000"/>
	고등어<input type="checkbox" name="allergy" value="10000000000000"/>
	홍합<input type="checkbox" name="allergy" value="100000000000000"/>
	전복<input type="checkbox" name="allergy" value="1000000000000000"/>
	굴<input type="checkbox" name="allergy" value="10000000000000000"/>
	조개류<input type="checkbox" name="allergy" value="100000000000000000"/>
	게<input type="checkbox" name="allergy" value="1000000000000000000"/>
	오징어<input type="checkbox" name="allergy" value="10000000000000000000"/>
	아황산 포함식품<input type="checkbox" name="allergy" value="100000000000000000000"/><br>
	
	기분 : <br>
	즐거워요<input type="checkbox" name="feeling" value="1"/>
	보통이예요<input type="checkbox" name="feeling" value="10"/>
	나빠요<input type="checkbox" name="feeling" value="100"/>
	우울해요<input type="checkbox" name="feeling" value="1000"/>
	불안해요<input type="checkbox" name="feeling" value="10000"/><br>
	
	컨디션 : <br>
	기운차요<input type="checkbox" name="condition" value="1"/>
	보통이예요<input type="checkbox" name="condition" value="10"/>
	기운이없어요<input type="checkbox" name="condition" value="100"/>
	졸려요<input type="checkbox" name="condition" value="1000"/>
	몽롱해요<input type="checkbox" name="condition" value="10000"/><br>
	
	날씨 : <br>
	맑은날<input type="checkbox" name="weather" value="1"/>
	흐린날<input type="checkbox" name="weather" value="10"/>
	비와요<input type="checkbox" name="weather" value="100"/>
	눈와요<input type="checkbox" name="weather" value="1000"/><br>
	
	온도 : <br>
	추워요<input type="checkbox" name="temperature" value="1"/>
	선선해요<input type="checkbox" name="temperature" value="10"/>
	따듯해요<input type="checkbox" name="temperature" value="100"/>
	더워요<input type="checkbox" name="temperature" value="1000"/><br>
	
	먼지 : <br>
	맑아요<input type="checkbox" name="dust" value="1"/>
	보통<input type="checkbox" name="dust" value="10"/>
	심해요<input type="checkbox" name="dust" value="100"/><br>
	
	습도 : <br>
	건조해요<input type="checkbox" name="humidity" value="1"/>
	적당해요<input type="checkbox" name="humidity" value="10"/>
	습해요<input type="checkbox" name="humidity" value="100"/><br>
	
	맛 : <br>
	단맛<input type="checkbox" name="favor" value="1"/>
	짠맛<input type="checkbox" name="favor" value="10"/>
	신맛<input type="checkbox" name="favor" value="100"/>
	쓴맛<input type="checkbox" name="favor" value="1000"/>
	매운맛<input type="checkbox" name="favor" value="10000"/>
	떫은맛<input type="checkbox" name="favor" value="100000"/>
	불맛<input type="checkbox" name="favor" value="1000000"/>
	깊은맛<input type="checkbox" name="favor" value="10000000"/>
	감칠맛<input type="checkbox" name="favor" value="100000000"/>
	지방맛<input type="checkbox" name="favor" value="1000000000"/>
	탄수화물 맛<input type="checkbox" name="favor" value="10000000000"/>
	고소한 맛<input type="checkbox" name="favor" value="100000000000"/><br>
	
	칼로리 : <input type="number" name="calorie" value="1"/><br>
	
	건강 : <br>
	목아파요<input type="checkbox" name="health" value="1"/>
	머리아파요<input type="checkbox" name="health" value="10"/>
	건강해요<input type="checkbox" name="health" value="100"/>
	열나요<input type="checkbox" name="health" value="1000"/>
	기운이없어요<input type="checkbox" name="health" value="10000"/>
	근육통<input type="checkbox" name="health" value="100000"/>
	몸살<input type="checkbox" name="health" value="1000000"/><br>
	
	카테고리 : <br>
	한식<input type="checkbox" name="category" value="1"/>
	중식<input type="checkbox" name="category" value="10"/>
	일식<input type="checkbox" name="category" value="100"/>
	양식<input type="checkbox" name="category" value="1000"/>
	패스트푸드<input type="checkbox" name="category" value="10000"/>
	치킨<input type="checkbox" name="category" value="100000"/>
	피자<input type="checkbox" name="category" value="1000000"/>
	아시안<input type="checkbox" name="category" value="10000000"/>
	카페/디저트<input type="checkbox" name="category" value="100000000"/><br>
	
	영양분(주요 영양소 2개 이하) : <br>
	탄수화물<input type="checkbox" name="nutrient" value="1"/>
	단백질<input type="checkbox" name="nutrient" value="10"/>
	지방<input type="checkbox" name="nutrient" value="100"/>
	무기질<input type="checkbox" name="nutrient" value="1000"/>
	비타민<input type="checkbox" name="nutrient" value="10000"/><br>
	
	<input type="submit"/>
</form>