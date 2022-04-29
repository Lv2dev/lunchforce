<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<!DOCTYPE html>
<html>
<head>
<%
	MemberDTO memDTO = (MemberDTO)session.getAttribute("memberDTO");
	MemberDAO memDAO = MemberDAO.getInstance();
	StoreDAO storeDAO = StoreDAO.getInstance();
	//StoreDTO storeDTO = storeDAO.getStoreInfo(Integer.parseInt(request.getParameter("storeId")));
	//session.setAttribute("storeDTO", storeDTO);
%>
<meta charset="UTF-8">
<title>가게추가페이지</title>
</head>
<body>
<form action="NewStoreProc.jsp" method="post">
     <h1>가게</h1>
    <table border=1>
         <tr>
            <td>가게명</td>
            <td><input type="text" name="storeName"></td>
         </tr>
         <tr>
            <td>카테고리</td>
            <td>
                <input type="radio" name="category" value="한식">한식
                <input type="radio" name="category" value="중식">중식
                <input type="radio" name="category" value="치킨">치킨
                <input type="radio" name="category" value="카페">카페
                <input type="radio" name="category" value="분식">분식
                <input type="radio" name="category" value="피자">피자
                <input type="radio" name="category" value="양식">양식
                <input type="radio" name="category" value="고기">고기
                <input type="radio" name="category" value="아시안">아시안
                <input type="radio" name="category" value="패스트푸드">패스트푸드
                <input type="radio" name="category" value="야식">야식
                <input type="radio" name="category" value="일식">일식
            </td>
        </tr>
        <tr>
            <td>가게 공지사항</td>
            <td><input type="text" name="notice"></td>
        </tr>
        <tr>
            <td>가게 전화번호</td>
            <td><input type="tel" name="tel"></td>
        </tr>
        <tr>
            <td>가게 이미지</td>
            <td><input type="image" src="" name="thumb"></td>
        </tr>
        <tr>
            <td>가게 오픈 시간</td>
            <td>
            <select name="openTime">
                <option value="99">선택안함</option>
            	<option value="1">1</option>
            	<option value="2">2</option>
            	<option value="3">3</option>
            	<option value="4">4</option>
            	<option value="5">5</option>
            	<option value="6">6</option>
            	<option value="7">7</option>
            	<option value="8">8</option>
            	<option value="9">9</option>
            	<option value="10">10</option>
            	<option value="11">11</option>
            	<option value="12">12</option>
            	<option value="13">13</option>
            	<option value="14">14</option>
            	<option value="15">15</option>
            	<option value="16">16</option>
            	<option value="17">17</option>
            	<option value="18">18</option>
            	<option value="19">19</option>
            	<option value="20">20</option>
            	<option value="21">21</option>
            	<option value="22">22</option>
            	<option value="23">23</option>
            	<option value="24">24</option>
            </select>
            </td>
        </tr>
        <tr>
            <td>가게 닫는 시간</td>
            <td>
            	<select name="closeTime">
                <option value="99">선택안함</option>
            	<option value="1">1</option>
            	<option value="2">2</option>
            	<option value="3">3</option>
            	<option value="4">4</option>
            	<option value="5">5</option>
            	<option value="6">6</option>
            	<option value="7">7</option>
            	<option value="8">8</option>
            	<option value="9">9</option>
            	<option value="10">10</option>
            	<option value="11">11</option>
            	<option value="12">12</option>
            	<option value="13">13</option>
            	<option value="14">14</option>
            	<option value="15">15</option>
            	<option value="16">16</option>
            	<option value="17">17</option>
            	<option value="18">18</option>
            	<option value="19">19</option>
            	<option value="20">20</option>
            	<option value="21">21</option>
            	<option value="22">22</option>
            	<option value="23">23</option>
            	<option value="24">24</option>
            	</select>
            </td>
        </tr>
        <tr>
            <td>가게 쉬는 날</td>
            <td>월<input type="radio"  name="rest_day" value="1"></td>
            <td>화<input type="radio"  name="rest_day" value="2"></td>
            <td>수<input type="radio"  name="rest_day" value="3"></td>
            <td>목<input type="radio"  name="rest_day" value="4"></td>
            <td>금<input type="radio"  name="rest_day" value="5"></td>
            <td>토<input type="radio"  name="rest_day" value="6"></td>
            <td>일<input type="radio"  name="rest_day" value="7"></td>
        </tr>
        <tr>
            <td>가게 쉬는 시간 시작</td>
            <td>
				<select name="brakeTimeStart">
                <option value="99">선택안함</option>
            	<option value="1">1</option>
            	<option value="2">2</option>
            	<option value="3">3</option>
            	<option value="4">4</option>
            	<option value="5">5</option>
            	<option value="6">6</option>
            	<option value="7">7</option>
            	<option value="8">8</option>
            	<option value="9">9</option>
            	<option value="10">10</option>
            	<option value="11">11</option>
            	<option value="12">12</option>
            	<option value="13">13</option>
            	<option value="14">14</option>
            	<option value="15">15</option>
            	<option value="16">16</option>
            	<option value="17">17</option>
            	<option value="18">18</option>
            	<option value="19">19</option>
            	<option value="20">20</option>
            	<option value="21">21</option>
            	<option value="22">22</option>
            	<option value="23">23</option>
            	<option value="24">24</option>
				</select>
			</td>   
        </tr>
        <tr>
            <td>가게 쉬는 시간 끝</td>
            <td>
            	<select name="brakeTimeEnd">
                <option value="99">선택안함</option>
            	<option value="1">1</option>
            	<option value="2">2</option>
            	<option value="3">3</option>
            	<option value="4">4</option>
            	<option value="5">5</option>
            	<option value="6">6</option>
            	<option value="7">7</option>
            	<option value="8">8</option>
            	<option value="9">9</option>
            	<option value="10">10</option>
            	<option value="11">11</option>
            	<option value="12">12</option>
            	<option value="13">13</option>
            	<option value="14">14</option>
            	<option value="15">15</option>
            	<option value="16">16</option>
            	<option value="17">17</option>
            	<option value="18">18</option>
            	<option value="19">19</option>
            	<option value="20">20</option>
            	<option value="21">21</option>
            	<option value="22">22</option>
            	<option value="23">23</option>
            	<option value="24">24</option>
            	</select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
               <input type="submit" value="추가">
               <input type="reset" value="취소">         
            </td>
         </tr>
      </table>
   </form>
</body>
</html>