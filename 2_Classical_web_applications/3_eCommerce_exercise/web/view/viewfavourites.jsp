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
    <h2> Your favourite products are <%=favourites.size()%>:</h2>    
    <br>
    <table width="50%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">
        <tr> <font size="2" face="Verdana" >
            <th>Product</th>
            <th>Description</th>
            <th>Price</th>
            <th>Photo</th>
            <th>Action</th>
        </tr>
        <%
            for (Product favouriteProduct : favourites) {
        %>
        <tr> <font size="2" face="Verdana" >
            <td width="14%" align="middle"> <b> <%=favouriteProduct.getName()%></b> </td>
            <td width="14%" align="middle"> <%=favouriteProduct.getDescription()%> </td>
            <td width="14%" align="middle"> <%=favouriteProduct.getPrice()%>$ </td>
            <td width="14%" align="middle"> <img src="img/products/<%=favouriteProduct.getName()%>.png" alt="<%=favouriteProduct.getName()%>" > </td>
             <td width="14%" align="middle"> 
                <a href="neworder.do?productId=<%=favouriteProduct.getId()%>"> ADD TO CART </a>
                <br><br>
                <a href="removefavourite.do?productId=<%=favouriteProduct.getId()%>"> REMOVE FROM FAVOURITES </a>
        </td>
        </tr>
        <% }%>
        </table>
        <br>
        <button onclick="goBack()">Go Back</button>
        <br>
        <a href="init.do" style="font-size:25px" >Continue shopping</a><br>
        <script>
        function goBack() {
          window.history.back();
        }
        </script>
        <h3>Logged as: <%=request.getSession().getAttribute("username")%> </h3>
        <form action="logout.do" method="post" class="loginInfo">
            <div class="container">
                <button type="submit">Logout</button>
            </div>
        </form>
</body>