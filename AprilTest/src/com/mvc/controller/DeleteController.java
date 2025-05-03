package com.mvc.controller;

import com.mvc.model.BbsDao;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int num = Integer.parseInt(request.getParameter("num"));
        BbsDao dao = new BbsDao();
        dao.delete(num);
        response.sendRedirect("list.do");
    }
}