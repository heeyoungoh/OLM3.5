<%@ page import="nets.sso.agent.web.v2020.authcheck.AuthCheck" %>
<%@ page import="nets.sso.agent.web.v2020.common.exception.AgentException" %>
<%@ page import="nets.sso.agent.web.v2020.common.logging.SsoLogger" %>
<%@ page import="nets.sso.agent.web.v2020.conf.AgentConf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    try
    {
        // �������� �ڵ����� �̵��Ǵ�, ������ �̵��� �߰��� ������� �ʽ��ϴ�.
        AuthCheck auth = new AuthCheck(request, response);
        boolean logonYN = auth.logon(true);
        if (logonYN)
        {
            //������
            String userid = auth.getUserID();
            // ���⿡ �� Application ���� �ʿ��� �α��� ó���� �ִٸ� ����մϴ�.
        }
    }
    catch (AgentException e)
    {
        SsoLogger.log(AgentConf.LogLevel.ERROR, e, request);
    }
%>
