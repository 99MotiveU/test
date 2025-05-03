package com.mvc.controller;

import com.mvc.model.BbsDao;
import com.mvc.model.BbsDto;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ViewController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int num = Integer.parseInt(request.getParameter("num"));
        BbsDao dao = new BbsDao();
        BbsDto dto = dao.selectByNum(num);  // selectOne 대신 selectByNum 호출
        request.setAttribute("dto", dto);
        RequestDispatcher rd = request.getRequestDispatcher("view.jsp");
        rd.forward(request, response);
    }
}
