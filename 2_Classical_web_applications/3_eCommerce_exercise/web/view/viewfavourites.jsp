<%@ page import="entity.Category" %>
<%@ page import="entity.Product" %>
<%@ page import="cart.ShoppingCart" %>
<%@ page import="cart.ShoppingCartItem" %>
<%@ page import="java.util.List" %>

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
        List<Product> favourites = (List<Product>) request.getSession().getAttribute("favourites");
    %>
    <button onclick="goBack()">Go Back</button>
        <script>
        function goBack() {
          window.history.back();
        }
        </script>
    <h2> Your favourite products are <%=favourites.size()%>:</h2>    
    <br>
    <table width="50%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">
        <tr> <font size="2" face="Verdana" >
            <th>Product</th>
            <th>Description</th>
            <th>Price</th>
            <th>Photo</th>
        </tr>
        <%
            for (Product favouriteProduct : favourites) {
        %>
        <tr> <font size="2" face="Verdana" >
            <td width="14%" align="middle"> <b> <%=favouriteProduct.getName()%></b> </td>
            <td width="14%" align="middle"> <%=favouriteProduct.getDescription()%> </td>
            <td width="14%" align="middle"> <%=favouriteProduct.getPrice()%>$ </td>
            <td width="14%" align="middle"> <img src="img/products/<%=favouriteProduct.getName()%>.png" alt="<%=favouriteProduct.getName()%>" > </td>
        </tr>
        <% }%>
        </table>
        <br>
        <h3>Logged as: <%=request.getSession().getAttribute("username")%> </h3>
        <form action="logout.do" method="post" class="loginInfo">
            <div class="container">
                <button type="submit">Logout</button>
            </div>
        </form>
</body>