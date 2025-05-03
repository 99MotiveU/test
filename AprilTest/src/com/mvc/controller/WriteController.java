package com.mvc.controller;

import com.mvc.model.BbsDao;
import com.mvc.model.BbsDto;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class WriteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        BbsDto dto = new BbsDto();
        dto.setTitle(request.getParameter("title"));
        dto.setId(request.getParameter("id"));
        dto.setContent(request.getParameter("content"));
        BbsDao dao = new BbsDao();
        dao.insert(dto);
        response.sendRedirect("list.do");
    }
}
