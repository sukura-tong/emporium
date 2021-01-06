$(function (){

    // 获取shopId
    var shopId = getQueryString('shopId');
    var shopListUrl = '/emporium/frontend/listdetailshoppageinfo';


    getSearchDivData();

    function getSearchDivData(){
        url = shopListUrl + '?shopId=' + shopId;
        $.getJSON(url,function (data){
            if (data.success){
                //获取数据并输入到html控件内
                var shop = data.shop;

                $('#shop-cover-pic').attr('src',shop.shopImg);

                $('#shop-update-time').html(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
                $('#shop-name').html(shop.shopName);
                $('#shop-desc').html(shop.shopDesc);
                $('#shop-addr').html(shop.shopAddr);
                $('#shop-phone').html(shop.phone);




                var productCategoryList = data.productCategoryList;
                var productCategoryHtml = '';
                // 遍历
                productCategoryList.map(function (item,index){
                    productCategoryHtml += ''
                    + '<a href="#" class="button" data-product-search-id='
                    + item.productCategoryId
                    + '>'
                    + item.productCategoryName
                    + '</a>';
                });

                $('#shopdetail-button-div').html(productCategoryHtml);
            }
        });
    }

    // 查询数据
    var loading = false;
    var maxItems = 20;
    var pageSize = 2;

    var listShopUrl = '/emporium/frontend/listproductsbyshop';

    var pageNum = 1;
    var productCategoryId = '';
    var productName = '';

    // 预先加载
    addItems(pageSize,pageNum)

    function addItems(pageSize,pageIndex){
        // 拼接查询url
        url = listShopUrl + '?shopId=' + shopId + '&pageIndex=' + pageIndex
            + '&pageSize=' + pageSize + '&productName=' + productName
            + '&productCategoryId=' + productCategoryId;

        loading = true;

        $.getJSON(url,function (data){
            if (data.success){
                var maxItems = data.count;
                var productHtml = '';

                var productList = data.productList;

                productList.map(function (item,index){

                    productHtml += ''
                        + '<div class="card" data-product-id='
                        + item.productId
                        + '>'
                        + '<div class="card-header">'
                        + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">'
                        + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">'
                        + '<img src="'
                        + item.imgAddr
                        + '" width="44">'
                        + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">'
                        + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>'
                        + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>'
                        + '<span>点击查看</span>'
                        + '</div>'
                        + '</div>';
                });

                $('.list-div').append(productHtml);

                // 处理更新信息
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 隐藏加载提示符
                    $('.infinite-scroll-preloader').hide();
                }else{
                    $('.infinite-scroll-preloader').show();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }


    // 绑定更新事件
        //选择类别 重置页码 清空之前列表 按照新的类别去查询
        $('#shopdetail-button-div').on('click','.button',function (e){
            productCategoryId = e.target.dataset.productSearchId;
            // 如果存在更新绑定效果
            if (productCategoryId){
                if ($(e.target).hasClass('button-fill')){
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                }else {
                    $(e.target)
                        .addClass('button-fill')
                        .siblings()
                        .removeClass('button-fill');
                }
                // 清空内容
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize,pageNum);
            }
        });



    // 下拉屏幕自动分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });
    $.init();

        // 输入框更新后 更换搜索条件
        $('#search').on('input',function (e){
            productName = e.target.value;
            // 将原始数据清空
            $('.list-div').empty();
            pageNum = 1;
            addItems(pageSize, pageNum);
        });

    // 给图片绑定链接
    $('.list-div').on('click','.card',function (e){
        var productId = e.currentTarget.dataset.productId;
        window.location.href = '/emporium/frontend/productdetail?productId=' + productId;
    })




    $('#me').click(function() {
        $.openPanel('#panel-left-demo');
    });
})