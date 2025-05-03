<%@ page import="com.mvc.model.*" %>
<%
    int num = Integer.parseInt(request.getParameter("num"));
    BbsDao dao = new BbsDao();
    dao.delete(num);
%>

<html>
<body>
    <h2>삭제 완료</h2>
    <a href="list.jsp">목록으로</a>
</body>
</html>
