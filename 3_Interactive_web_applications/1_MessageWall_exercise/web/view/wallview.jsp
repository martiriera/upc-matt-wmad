<%@ page import="demo.spec.Message"%>
<%@ page import="demo.spec.UserAccess"%>
<%@ page import="java.util.List" %>

<head>
    <meta http-equiv="Expires" CONTENT="0">
    <meta http-equiv="Cache-Control" CONTENT="no-cache">
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Message Wall</title>
</head>

<%
    UserAccess useraccess = (UserAccess) request.getSession().getAttribute("useraccess");
%>

<script>
    setInterval(autoRefresh, 10000);
    function autoRefresh() {
        document.getElementById('refresh_button')[0].click();
    }
</script>

<body>

    <h3>user: <em><%=useraccess.getUser()%></em>
        <a href=logout.do>[Close session]</a></h3>

    <h2> <%=useraccess.getNumber()%> Messages shown:</h2>

    <table width="50%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">

        <td width="14%" valign="center" align="middle">
            Message
        </td>

        <td width="14%" valign="center" align="middle">
            Owner
        </td>

        <td width="14%" valign="center" align="middle">
            Click to:
        </td>

        <%
            List<Message> messages = (List<Message>) useraccess.getAllMessages();
            int messageIndex = 0;
            for (Message message : messages) {
        %>

        <tr> <font size="2" face="Verdana">

        <td width="14%" valign="center" align="middle">
            <%=message.getContent()%> 
        </td>

        <td width="14%" valign="center" align="middle">
            <%=message.getOwner()%>
        </td>

        <td width="14%" valign="center" align="middle">
            <form action="delete.do" method="post">
                <input type="hidden"
                       name="index"
                       value="<%=messageIndex%>">
                <input type="submit"
                       name="delete"
                       value="delete">
            </form>
        </td>

        </font> 
    </tr>

    <%          messageIndex = messageIndex + 1;
        }%>

</table>

</br>

<HR WIDTH="100%" SIZE="2">

<form action="put.do" method=POST>
    New message:<input type=text name=msg size=10>
    <input type=submit value="Send message"></form>

<HR WIDTH="100%" SIZE="2">

<form action="refresh.do" method=POST>
    <input type=submit value="Refresh wall view message" id="refresh_button"></form>

</body>