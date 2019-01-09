<%@ page import="com.urise.webapp.model.ContactType" %>
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
    <script type="text/javascript">
        function removeOrganization(clicked_id) {
            document.getElementById("organization" + clicked_id.substr(6)).remove();
            document.getElementById(clicked_id).remove();
        }
    </script>
    <script type="text/javascript">
        function removePosition(clicked_id) {
            document.getElementById("positionOrganization" + clicked_id.substr(14)).remove();
            document.getElementById(clicked_id).remove();
        }
    </script>
    <%--<script type="text/javascript">
        function addPosition(clicked_id) {
            alert('removePosition' + clicked_id.substr(11));
            alert('positionOrganization' + clicked_id.substr(11));
            var newbutton = document.getElementById('removePosition' + clicked_id.substr(11)).cloneNode(true);
            document.getElementById(clicked_id).parentNode.insertBefore(newbutton, document.getElementById(clicked_id));

            var newPosition = document.getElementById('positionOrganization' + clicked_id.substr(11)).cloneNode(true);
            document.getElementById(clicked_id).parentNode.insertBefore(newPosition, document.getElementById(clicked_id));
        }
    </script>--%>
    <%--<script type="text/javascript">
        function addOrganization(clicked_id) {
            var newOrganization = document.getElementById('organization${type.name()}${idOrganization}').cloneNode(true);
            //newOrganization.
            document.getElementById(clicked_id).parentNode.insertBefore(newOrganization, document.getElementById(clicked_id));
            <c:set var="idOrganization" value="${idOrganization+1}"/>
        }
    </script>--%>
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
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <c:choose>
                    <c:when test="${(type.equals(SectionType.OBJECTIVE)) || (type.equals(SectionType.PERSONAL))}">
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=97 value="${resume.getSection(type)}"></dd>
                    </c:when>
                    <c:when test="${(type.equals(SectionType.ACHIEVEMENT)) || (type.equals(SectionType.QUALIFICATIONS))}">
                        <dt>${type.title}</dt>
                        <br/>
                        <textarea
                                name=${type} style="display:block;width:900px;height:100px">${resume.getSection(type)}</textarea>
                        <br/>
                    </c:when>
                    <c:when test="${(type.equals(SectionType.EXPERIENCE)) || (type.equals(SectionType.EDUCATION))}">
                        <dt><h3>${type.title}</h3></dt>
                        <br/>
                        <c:set var="idOrganization" value="0"/>
                        <c:forEach var="organization" items="${resume.getSection(type).organizations}"
                                   varStatus="loopOrganization">
                            <dl id="organization${type.name()}${loopOrganization.index}">
                                <dl>
                                    <br/>
                                    <dt>Название учреждения:</dt>
                                    <dd><input type="text" name="${type.name()}${loopOrganization.index}" size=97
                                               value="${organization.homePage.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Сайт учреждения:</dt>
                                    <dd><input type="text" name="${type.name()}${loopOrganization.index}" size=97
                                               value="${organization.homePage.url}"></dd>
                                </dl>
                                <c:set var="idPosition" value="0"/>
                                <c:forEach var="position" items="${organization.positions}" varStatus="loopPosition">
                                    <jsp:useBean id="position"
                                                 type="com.urise.webapp.model.Organization.Position"/>
                                    <dl id="positionOrganization${type.name()}${loopOrganization.index}${loopPosition.index}">
                                        <dl>
                                            <dt>Дата начала:</dt>
                                            <dd><input placeholder="YYYY-MM-DD" type="text"
                                                       name="${type.name()}${loopOrganization.index}"
                                                       size=10
                                                       value="<%=DateUtil.format(position.getStartDate())%>">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Дата окончания:</dt>
                                            <dd><input placeholder="YYYY-MM-DD" type="text"
                                                       name="${type.name()}${loopOrganization.index}"
                                                       size=10
                                                       value="<%=DateUtil.format(position.getEndDate())%>">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text" name="${type.name()}${loopOrganization.index}"
                                                       size=97
                                                       value="${position.title}">
                                            </dd>
                                        </dl>
                                        <c:if test="${type.equals(SectionType.EXPERIENCE)}">
                                            <dt>Описание:</dt>
                                            <dd><input type="text" name="${type.name()}${loopOrganization.index}"
                                                       size=97
                                                       value="${position.description}"></dd>
                                        </c:if>
                                        <c:if test="${type.equals(SectionType.EDUCATION)}">
                                            <dd><input type="hidden" name="${type.name()}${loopOrganization.index}"
                                                       size=97
                                                       value=""></dd>
                                        </c:if>
                                        <br/>
                                        <input id="removePosition${type.name()}${loopOrganization.index}${loopPosition.index}"
                                               type="button"
                                               value="Удалить позицию в организации"
                                               onClick="removePosition(this.id)">
                                        <c:set var="idPosition" value="${loopPosition.index}"/>
                                    </dl>
                                </c:forEach>
                                    <%--<input id="addPosition${type.name()}${loopOrganization.index}${idPosition}"
                                           type="button"
                                           value="Добавить позицию в организации"
                                           onClick="addPosition(this.id)">--%>
                            </dl>
                            <input id="button${type.name()}${loopOrganization.index}" type="button"
                                   value="Удалить организацию со свеми позициями"
                                   onClick="removeOrganization(this.id)">
                            <c:set var="idOrganization" value="${loopOrganization.index}"/>
                        </c:forEach>
                        <br/>
                        <%--<input id="add${type.name()}" type="button"
                               value="Добавить организацию"
                               onClick="addOrganization(this.id)">--%>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit" style="height:30px; width:100px">Сохранить</button>
        <button type="reset" onclick="window.history.back()" style="height:30px; width:100px">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>