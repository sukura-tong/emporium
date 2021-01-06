$(function (){
    // 定义访问后台 获取头条列表以及一级类别列表的URL
    var url = '/emporium/frontend/listmainpageinfo';

    // 访问
    $.getJSON(url,function (data){
        if (data.success){
           var headLineList = data.headLineList;
           var swuperHtml = '';

           headLineList.map(function (item, index){

               // swuperHtml += ''
               //     + '<div class="swiper-slide img-wrap">'
               //     + '<img class="banner-img" src="'
               //     + item.lineImg
               //     + '" alt="'
               //     + item.lineName +'">'
               //     + '</div>';
               //
               swuperHtml += ''
                    + '<div class="swiper-slide img-wrap">'
                    + '<a href="'
                    + item.lineLink
                    + '" external> <img class="banner-img" src="'
                    + item.lineImg
                    + '" alt="'
                    + item.lineName
                    + '">'
                    + '</a>'
                    + '</div>';
           });

            // 将轮播图赋值给前端html控件
            $('.swiper-wrapper').html(swuperHtml);
            // 设定轮播图转换时间为3秒
            $('.swiper-container').swiper({
                autoplay: 3000,
                // 用户对轮播图操作时是否停止自动轮播
                autoplayDisableOnInteraction: false
            });
            // 获取后台传递的大类列表
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';

            shopCategoryList.map(function (item, index){
                // categoryHtml += ''
                //             + '<div class="col-50 shop-classify" data-category='
                //             + item.shopCategoryId
                //             + '>'
                //             + '<div class="word">'
                //             + '<p class="shop-title">'
                //             + item.shopCategoryName
                //             + '</p>'
                //             + '<p class="shop-desc">'
                //             + item.shopCategoryDesc
                //             + '</p>'
                //             + '</div>'
                //             + '<div class="shop-classify-img-warp">'
                //             + '<img class="shop-img" src=">'
                //             + item.shopCategoryImg
                //             + '">'
                //             + '</div>'
                //             + '</div>'
                categoryHtml += ''
                    +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                    +      '<div class="word">'
                    +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                    +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                    +      '</div>'
                    +      '<div class="shop-classify-img-warp">'
                    +          '<img class="shop-img" src="'+ item.shopCategoryImg +'">'
                    +      '</div>'
                    +  '</div>';
            });
            $('.row').html(categoryHtml)
        }
    });

    // 更新侧边栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    // 给类别按钮绑定点击事件 一级查询区域
    $('.row').on('click','.shop-classify', function (e){
        // dataset.category 是因为绑定的元素是data-category
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = '/emporium/frontend/shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    })
})