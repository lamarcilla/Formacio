<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Reglas de Seguridad</title>
    </head>
    <body>
        <label>CODIGO: </label>findsecbugs-jsp:JSP_XSLT<br>
        <label>REGLA: </label>Security - A malicious XSLT could be provided<br>
        <label>SEVERIDAD: </label>Crítica<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
            <x:transform xml="${xmlData}" xslt="${RutaProcedenteParametroURL}" />  <%-- Incumple --%>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
            <x:transform xml="${xmlData}" xslt="${getFichero(IdFicheroProcedenteParametroURL)}" />  <%-- Cumple --%>
            
        <hr>
    
        <label>CODIGO: </label>findsecbugs-jsp:JSP_INCLUDE<br>
        <label>REGLA: </label>Security - Dynamic JSP inclusion<br>
        <label>SEVERIDAD: </label>Crítica<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
            <jsp:include page="${param.secret_param}" />  <%-- Incumple --%>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
            <c:if test="${param.secret_param == 'page1'}">
                <jsp:include page="page1.jsp" />  <%-- Cumple --%>
            </c:if>
            
        <hr>
    
        <label>CODIGO: </label>findsecbugs-jsp:JSP_SPRING_EVAL<br>
        <label>REGLA: </label>Security - Dynamic variable in Spring expression<br>
        <label>SEVERIDAD: </label>Crítica<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
            <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <spring:eval expression="'${param.lang}'=='fr'" var="languageIsFrench" />   <%-- Incumple --%>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:set var="languageIsFrench" value="${param.lang == 'fr'}"/>   <%-- Cumple --%>
            
        <hr>
    
        <label>CODIGO: </label>indsecbugs-jsp:JSP_JSTL_OUT<br>
        <label>REGLA: </label>Security - Escaping of special XML characters is disabled<br>
        <label>SEVERIDAD: </label>Mayor<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:out value="${param.test_param}" escapeXml="false" />  <%-- Incumple --%>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:out value="${param.test_param}" escapeXml="true" />  <%-- Cumple --%>
            
        <hr>
    
        <label>CODIGO: </label>findsecbugs-jsp:XSS_JSP_PRINT<br>
        <label>REGLA: </label>Security - Potential XSS in JSP<br>
        <label>SEVERIDAD: </label>Mayor<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
            <%
                String taintedInput = (String) request.getAttribute("input");
            %>
            [...]
            <%= taintedInput %>  <%-- Incumple --%>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
            <%
                String taintedInput2 = (String) request.getAttribute("input");
            %>
            [...]
            <%= Encode.forHtml(taintedInput2) %>  <%-- Cumple --%>
            
        <hr>
    
    </body>
</html>