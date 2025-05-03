<%@ page import="com.mvc.model.*" %>
<%
    int num = Integer.parseInt(request.getParameter("num"));
    BbsDao dao = new BbsDao();
    BbsDto dto = dao.selectOne(num);
%>

<html>
<body>
    <h2>글 수정</h2>
    <form action="edit?num=<%= dto.getNum() %>" method="post">
        <label>제목:</label><br>
        <input type="text" name="title" value="<%= dto.getTitle() %>"><br>
        <label>내용:</label><br>
        <textarea name="content"><%= dto.getContent() %></textarea><br>
        <input type="submit" value="수정">
    </form>
    <a href="view.jsp?num=<%= dto.getNum() %>">상세보기</a>
    <a href="list.jsp">목록</a>
</body>
</html>
