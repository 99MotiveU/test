<%@ page import="java.util.*, com.mvc.model.*" %>
<%
    List<BbsDto> list = (List<BbsDto>) request.getAttribute("list");
    int currentPage = (Integer) request.getAttribute("currentPage");
    int totalPage = (Integer) request.getAttribute("totalPage");
%>

<html>
<body>
    <h2>게시판 목록</h2>
    <form action="search" method="get">
        <input type="text" name="keyword" placeholder="검색어">
        <input type="submit" value="검색">
    </form>
    <table border="1">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>날짜</th>
            <th>상세보기</th>
        </tr>
        <%
            for (BbsDto dto : list) {
        %>
            <tr>
                <td><%= dto.getNum() %></td>
                <td><a href="view.jsp?num=<%= dto.getNum() %>"><%= dto.getTitle() %></a></td>
                <td><%= dto.getId() %></td>
                <td><%= dto.getNalja() %></td>
            </tr>
        <%
            }
        %>
    </table>
    
    <!-- 페이징 링크 -->
    <div>
        <%
            if (currentPage > 1) {
        %>
            <a href="list?page=<%= currentPage - 1 %>">이전</a>
        <%
            }
        %>
        
        <%
            for (int i = 1; i <= totalPage; i++) {
        %>
            <a href="list?page=<%= i %>"><%= i %></a>
        <%
            }
        %>
        
        <%
            if (currentPage < totalPage) {
        %>
            <a href="list?page=<%= currentPage + 1 %>">다음</a>
        <%
            }
        %>
    </div>
    
    <a href="write.jsp">글쓰기</a>
</body>
</html>
