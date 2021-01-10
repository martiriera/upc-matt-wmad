
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<head>
    <meta http-equiv="Expires" CONTENT="0">
    <meta http-equiv="Cache-Control" CONTENT="no-cache">
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>eCommerce</title>
</head>

<body>
    <%
        String amount = request.getParameter("amountToPay");
    %>

    <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
        <input type="hidden" name="cmd" value="_xclick">
        <input type="hidden" name="business" value="wmad@etsetb.com">
        <input type="hidden" name="item_name" value="Grocery">
        <input type="hidden" name="currency_code" value="USD">
        <input type="hidden" name="amount" value=<%=amount%>>
        <input type="image" src="http://www.paypal.com/en_US/i/btn/x-click-but01.gif" name="submit" alt="Make payments with PayPal - it's fast, free and secure!">
    </form>
</body>