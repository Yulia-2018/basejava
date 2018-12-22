<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <%--<input type="text" name="section" size=30 value="1"><br/>
        <input type="text" name="section" size=30 value="2"><br/>
        <input type="text" name="section" size=30 value="3"><br/>--%>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <c:choose>
                    <c:when test="${(type == SectionType.OBJECTIVE) || (type == SectionType.PERSONAL)}">
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=50 value="${resume.getSection(type)}"></dd>
                    </c:when>
                    <c:when test="${(type == SectionType.ACHIEVEMENT) || (type == SectionType.QUALIFICATIONS)}">
                        <dt>${type.title}</dt>
                        <br/>
                        <c:forEach var="item" items="${resume.getSection(type).items}">
                            <dl>
                                <dd><input type="text" name="${type.name()}" size=100 value="${item}"></dd>
                            </dl>
                        </c:forEach>
                        <%--<label>
                            <textarea name="all_string" style="display:block;width:400px;height:100px"></textarea>
                        </label>--%>
                        <%--<c:if test="${resume.getSection(type).items.size() == null}">
                            <p><dd><input type="text" name="${type.name()}" size=100 value=""></dd><br/>
                            </p>
                        </c:if>--%>
                        <dd><input type="text" name="${type.name()}" size=100 value=""></dd>
                        <br/>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit" style="height:30px; width:100px">Сохранить</button>
    </form>
    <button onclick="window.history.back()" style="height:30px; width:100px">Отменить</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>