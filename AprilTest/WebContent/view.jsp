<%@ page import="com.mvc.model.*" %>
<%
    int num = Integer.parseInt(request.getParameter("num"));
    BbsDao dao = new BbsDao();
    BbsDto dto = dao.selectOne(num);
%>

<html>
<body>
    <h2>상세보기</h2>
    <p><strong>제목:</strong> <%= dto.getTitle() %></p>
    <p><strong>작성자:</strong> <%= dto.getId() %></p>
    <p><strong>내용:</strong> <%= dto.getContent() %></p>
    <p><strong>작성일:</strong> <%= dto.getNalja() %></p>
    <a href="edit.jsp?num=<%= dto.getNum() %>">수정</a>
    <a href="delete.jsp?num=<%= dto.getNum() %>">삭제</a>
    <a href="list.jsp">목록</a>
</body>
</html>
