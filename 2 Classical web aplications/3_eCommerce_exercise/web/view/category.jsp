<%@ page import="entity.Category" %>
<%@ page import="entity.Product" %>
<%@ page import="cart.ShoppingCart" %>
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
        Category category = (Category) request.getSession().getAttribute("lastCategory");
    %>
    <h2> Category <%=category.getName()%></h2>
    <%
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("sessionCart");
        if (cart != null){
    %>
    <h3> <i class="glyphicon glyphicon-shopping-cart"></i> <%=cart.getNumberOfItems()%></h3>
        <% if(cart.getNumberOfItems() != 0) {%>
            <a href="viewcart.do" style="font-size:25px" >View shopping cart</a>
        <%}
        }%>
    <br>
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
            List<Product> products = (List<Product>) request.getAttribute("products");
            for (Product product : products) {
        %>
        <tr> <font size="2" face="Verdana" >
        <td width="14%" align="middle"> <b> <%=product.getName()%></b> </td>
            <td width="14%" align="middle"> <%=product.getDescription()%> </td>
            <td width="14%" align="middle"> <%=product.getPrice()%>$ </td>
            <td width="14%" align="middle"> <img src="img/products/<%=product.getName()%>.png" alt="<%=product.getName()%>" > </td>
            <td width="14%" align="middle"> 
                <a href="neworder.do?productId=<%=product.getId()%>"> ADD TO CART </a>
            </td>
        </tr>
    <% }%>
    </table>
    
    <br>
    <h3>Other categories</h3>
    <table width="50%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">
        <tr>
        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            for (Category otherCategory : categories) {
                if(otherCategory.getName() != category.getName()) {
        %>
        
            <td width="14%">
                <a href="category.do?categoryId=<%=otherCategory.getId()%>"> <%=otherCategory.getName()%> </a>
            </td>
        <%      }
            }%>
        </tr>
    </table>

</body>
