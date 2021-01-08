
$(function (){
    var productId = getQueryString('productId');

    var productListUrl = '/emporium/frontend/showproduct';

    searchProduct();

    function searchProduct(){
        var url = productListUrl + '?productId=' + productId;
        $.getJSON(url, function (data){
            if (data.success){
                var product = data.product;

                if (product.point != undefined){
                    $('#product-point').text('购买可获得' + product.point + '积分');
                }
                $('#product-name').html(product.productName);
                $('#product-desc').html(product.productDesc);
                $('#product-time').html(
                    new Date(product.lastEditTime)
                    .Format("yyyy-MM-dd"));
                $('#product-img').attr('src',product.imgAddr);

                $('#promotion-price').html(product.promotionPrice);
                // $('#normal-price').html(product.normalPrice);

                var imgListHtml = '';
                product.productImgList.map(function(item, index) {
                    imgListHtml += '<div> <img src="'
                        + item.imgAddr + '"/></div>';
                });
                $('#imgList').html(imgListHtml);
            }
        })
    }

})