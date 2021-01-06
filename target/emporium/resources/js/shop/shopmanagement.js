$(function (){
    var shopId = getQueryString('shopId');
    var shopInfoUrl = '/emporium/shopadmin/getshopmanagementinfo?shopId=' + shopId;

    $.getJSON(shopInfoUrl, function (data){
        if (data.redirect){
            window.location.href = data.url;
        }else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            // 给标签追加shopId属性
            var initUrl = '/emporium/shopadmin/shopoperation?shopId='+shopId;
            $('#shopInfo').attr('href', initUrl);

        }
    });
});