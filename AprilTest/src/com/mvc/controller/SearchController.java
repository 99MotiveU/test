package com.mvc.controller;

import com.mvc.model.BbsDao;
import com.mvc.model.BbsDto;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SearchController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        BbsDao dao = new BbsDao();
        List<BbsDto> list = dao.search(keyword);
        request.setAttribute("list", list);
        RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
        rd.forward(request, response);
    }
}