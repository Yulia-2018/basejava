<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <h2>${sectionEntry.key.title}<br/></h2>
        <c:choose>
            <c:when test="${(sectionEntry.key.equals(SectionType.OBJECTIVE)) || (sectionEntry.key.equals(SectionType.PERSONAL))}">
                ${sectionEntry.value.content}
            </c:when>
            <c:when test="${(sectionEntry.key.equals(SectionType.ACHIEVEMENT)) || (sectionEntry.key.equals(SectionType.QUALIFICATIONS))}">
                <ul>
                    <c:forEach var="item" items="${sectionEntry.value.items}">
                        <li>
                            <p>${item}</p>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${(sectionEntry.key.equals(SectionType.EXPERIENCE)) || (sectionEntry.key.equals(SectionType.EDUCATION))}">
                <table>
                    <c:forEach var="organization" items="${sectionEntry.value.organizations}">
                        <tr>
                            <td colspan="2">
                                <c:if test="${empty organization.homePage.url}">
                                    <h3>${organization.homePage.name}</h3>
                                </c:if>
                                <c:if test="${not empty organization.homePage.url}">
                                    <h3><a href="${organization.homePage.url}">${organization.homePage.name}</a></h3>
                                </c:if>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position"
                                         type="com.urise.webapp.model.Organization.Position"/>
                            <tr>
                                <td width="20%" style="vertical-align: top"><%=DateUtil.format(position.getStartDate())%> - <%=DateUtil.format(position.getEndDate())%>
                                </td>
                                <td><b>${position.title}</b><br>${position.description}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                Section not exists
            </c:otherwise>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>