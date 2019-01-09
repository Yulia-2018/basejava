package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private static Storage storage; //= (SqlStorage) Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid.isEmpty()) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.setSection(type, new TextSection(value));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String parameter = request.getParameter(type.name());
                    String[] values = parameter.split("\n");
                    List<String> result = new ArrayList<>();
                    for (String item : values) {
                        if (item != null && item.trim().length() != 0) {
                            result.add(item);
                        }
                    }
                    if (result.size() != 0) {
                        r.setSection(type, new ListSection(result));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    List<Organization> organizations = new ArrayList<>();
                    for (Map.Entry<String, String[]> entri : parameterMap.entrySet()) {
                        if (entri.getKey().contains(type.name())) {
                            String[] parameterValues = request.getParameterValues(entri.getKey());
                            String name = parameterValues[0];
                            if (name != null && name.trim().length() != 0) {
                                String url = parameterValues[1];
                                int length = parameterValues.length;
                                List<Organization.Position> positions = new ArrayList<>();
                                for (int i = 2; i < length - 3; i = i + 4) {
                                    if (!parameterValues[i + 2].isEmpty()) {
                                        Organization.Position position = new Organization.Position(DateUtil.parse(parameterValues[i]), DateUtil.parse(parameterValues[i + 1]), parameterValues[i + 2], parameterValues[i + 3]);
                                        positions.add(position);
                                    }
                                }
                                Organization organization = new Organization(new Link(name, url), positions);
                                organizations.add(organization);
                            }
                        }
                    }
                    if (organizations.size() != 0) {
                        r.setSection(type, new OrganizationSection(organizations));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
            }
        }
        if (uuid.isEmpty()) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                if (uuid == null) {
                    //r = Resume.EMPTY;
                    r = new Resume();
                } else {
                    r = storage.get(uuid);
                }
                for (SectionType type : SectionType.values()) {
                    final AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                r.setSection(type, TextSection.EMPTY);
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                r.setSection(type, ListSection.EMPTY);
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> organizations = new ArrayList<>();
                            if (orgSection != null) {
                                for (Organization organization : orgSection.getOrganizations()) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    positions.addAll(organization.getPositions());
                                    positions.add(Organization.Position.EMPTY);
                                    organizations.add(new Organization(organization.getHomePage(), positions));
                                }
                            }
                            organizations.add(Organization.EMPTY);
                            r.setSection(type, new OrganizationSection(organizations));
                            break;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
