package com.mvc.controller;

import com.mvc.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ListController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage = 1;  // 기본 페이지는 1
        int pageSize = 10;    // 한 페이지에 보여줄 게시글 수
        int totalCount = 0;   // 전체 게시글 수

        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        BbsDao dao = new BbsDao();
        totalCount = dao.getTotalCount();  // 전체 게시글 수 가져오기
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);  // 전체 페이지 수 계산

        int startRow = (currentPage - 1) * pageSize;
        int endRow = currentPage * pageSize;

        List<BbsDto> list = dao.selectPaged(startRow, endRow);  // 페이징된 데이터 가져오기

        request.setAttribute("list", list);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPage", totalPage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        dispatcher.forward(request, response);
    }
}
