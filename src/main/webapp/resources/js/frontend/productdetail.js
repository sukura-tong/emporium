
$(function (){
    var productId = getQueryString('productId');

    var productListUrl = '/emporium/frontend/showproduct';

    searchProduct();

    function searchProduct(){
        var url = productListUrl + '?productId=' + productId;
        $.getJSON(url, function (data){
            if (data.success){
                var product = data.product;

                $('#product-name').html(product.productName);
                $('#product-desc').html(product.productDesc);
                $('#promotion-price').html(product.promotionPrice);
                // $('#normal-price').html(product.normalPrice);
                $('#product-time').html(
                    new Date(product.lastEditTime)
                    .Format("yyyy-MM-dd"));
                $('#product-img').attr('src',product.imgAddr);
            }
        })
    }

})