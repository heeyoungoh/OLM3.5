<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:url value="/" var="root"/><c:if test="${! empty resultMap.ALERT}">${resultMap.ALERT}</c:if><c:if test="${! empty resultMap.SCRIPT}">${resultMap.SCRIPT};</c:if>