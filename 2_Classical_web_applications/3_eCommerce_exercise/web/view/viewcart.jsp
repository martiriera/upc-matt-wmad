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
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("sessionCart");
    %>
    <h3> <i class="glyphicon glyphicon-shopping-cart"></i> <%=cart.getNumberOfItems()%></h3>
    <h2> Your shopping cart contains <%=cart.getNumberOfItems()%> items</h2>
    <a href="clearcart.do" style="font-size:25px" >Clear shopping cart</a><br>
    <% Category lastCategory = (Category) request.getSession().getAttribute("lastCategory");%>
    <a href="category.do?categoryId=<%=lastCategory.getId()%>" style="font-size:25px" >Continue shopping </a><br>
    <a href="checkout.do?amountToPay=<%=cart.getTotal()%>" style="font-size:25px" >Proceed to checkout</a><br>
    
    <br>
    <table width="50%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">
        <tr> <font size="2" face="Verdana" >
            <th>Product</th>
            <th>Description</th>
            <th>Price</th>
            <th>Photo</th>
            <th>Quantity</th>
        </tr>
        <%
            List<ShoppingCartItem> cartItems = (List<ShoppingCartItem>) cart.getItems();
            for (ShoppingCartItem cartItem : cartItems) {
                Product product = cartItem.getItem();
        %>
        <tr> <font size="2" face="Verdana" >
            <td width="14%" align="middle"> <b> <%=product.getName()%></b> </td>
            <td width="14%" align="middle"> <%=product.getDescription()%> </td>
            <td width="14%" align="middle"> <%=product.getPrice()%>$ </td>
            <td width="14%" align="middle"> <img src="img/products/<%=product.getName()%>.png" alt="<%=product.getName()%>" > </td>
            <td width="14%" align="middle"> 
                <form action="updatecart.do">
                    <input type="text" id="fquant" name="fquant" value="<%=cartItem.getQuantity()%>">
                    <input type="hidden" id="fpid" name="fpid" value="<%=cartItem.getItem().getId()%>"><br>
                    <input type="submit" value="Update">
                </form> </td>
        </tr>
        <% }%>
        </table>
        <br>
        <h2 style="font-family:verdana;"><b>Total price: <%=cart.getTotal()%> $</b></h2>
        <h3>Logged as: <%=request.getSession().getAttribute("username")%> </h3>
        <a href="viewfavourites.do" style="font-size:25px" >My favourite products</a><br>
         <form action="logout.do" method="post" class="loginInfo">
            <div class="container">
                <button type="submit">Logout</button>
            </div>
        </form>
</body>