package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        //response.setHeader("Content-Type", "text/html; charset=UTF-8");

        /*final String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!");*/

        try {
            Class.forName("org.postgresql.Driver");    // load the driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final String uuid = request.getParameter("uuid");
        final SqlStorage sqlStorage = (SqlStorage) Config.get().getStorage();
        String html = "";
        if (uuid != null) {
            try {
                final Resume resume = sqlStorage.get(uuid);
                html = addRecord(resume);
            } catch (NotExistStorageException e) {
                response.getWriter().write("Resume " + uuid + " not exist");
            }
        } else {
            final List<Resume> resumes = sqlStorage.getAllSorted();
            for (Resume resume : resumes) {
                html = html + addRecord(resume);
            }
        }
        if (html != "") {
            html = "" +
                    "<html>" +
                    "  <head>" +
                    "    <title>Резюме</title>" +
                    "  </head>" +
                    "  <body>" +
                    "    <table border=1>" +
                    html +
                    "    </table>" +
                    "  </body>" +
                    "</html >";
            response.getWriter().write(html);
        }
    }

    private String addRecord(Resume resume) {
        return "       <tr>" +
                "        <td>" + resume.getUuid() + "</td>" +
                "        <td>" + resume.getFullName() + "</td>" +
                "      </tr>";
    }
}
