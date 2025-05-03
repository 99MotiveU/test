<html>
<body>
    <h2>글쓰기</h2>
    <form action="write" method="post">
        <label>제목:</label><br>
        <input type="text" name="title"><br>
        <label>작성자:</label><br>
        <input type="text" name="id"><br>
        <label>내용:</label><br>
        <textarea name="content"></textarea><br>
        <input type="submit" value="작성">
    </form>
    <a href="list.jsp">목록으로</a>
</body>
</html>
