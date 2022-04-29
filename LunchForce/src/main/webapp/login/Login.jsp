<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <form action="LoginCheck.jsp" method="post">
     <h1>로그인</h1>
    <table bodrer=1>
         <tr>
            <td width="100">아이디</td>
            <td width="200"><input type="text" name="id"></td>
         </tr>
         <tr>
            <td>비밀번호</td>
            <td><input type="password" name="pw"></td>
         </tr>
         <tr>
            <td colspan="2">
               <input type="submit" value="로그인">
               <input type="reset" value="취소"   >         
            </td>
         </tr>
      </table>
   </form>
</body>
</html>