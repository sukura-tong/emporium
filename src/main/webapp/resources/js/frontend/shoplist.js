// 该方法需要实现功能
    // 加载shoplist页面时从后台查询数据 并自动装配到响应html控件内
$(function (){
    var loading = false;
    // 每页最大条数
    var maxItems = 999;
    // 一页最大条数
    var pageSize = 2;
    // 获取店铺类别的url
    var listUrl = '/emporium/frontend/listshops';
    // 获取店铺类别和区域类别的url
    var searchDivUrl = '/emporium/frontend/listshopspageinfo';

    // 页码
    var pageNum = 1;
    // 从地址栏的url里尝试获取parent shop category Id
    var parentId = getQueryString('parentId');

    var areaId = '';
    var shopCategoryId= '';
    var shopName = '';

    // 渲染店铺类别列表与区域列表
    getSearchDivData();
    // 预先装载10条信息
    addItems(pageSize,pageNum);


    // 获取店铺列表

    function getSearchDivData(){
        var url = searchDivUrl + '?' + 'parentId=' + parentId;
        $.getJSON(url,function (data){
            if (data.success){
             // 获取后台信息拼接html语句
             var shopCategoryList = data.shopCategoryList;
             var html = '';
             html += '<a href="#" class="button" data-category-id="">全部类别</a>';

                // 遍历
                shopCategoryList.map(function (item,index){
                    html += ''
                        + '<a href="#" class="button" data-category-id='
                        + item.shopCategoryId
                        + '>'
                        + item.shopCategoryName
                        + '</a>';
                });
                $('#shoplist-search-div').html(html);

                // 区域列表选取控件
                var selectOptions = '<option value="">全部街道</option>';

                var areaList = data.areaList;
                areaList.map(function (item, index){
                    selectOptions += ''
                            + '<option value="'
                            + item.areaId
                            + '">'
                            + item.areaName
                            + '</option>'
                });
                $('#area-search').html(selectOptions);
            }
        })
    }

    // 查找店铺
    function addItems(pageSize,pageIndex){
        // 拼接url
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&' + 'pageSize=' + pageSize
                + '&' + 'parentId=' + parentId + '&' + 'areaId=' + areaId + '&' + 'shopCategoryId='
                + shopCategoryId + '&' + 'shopName=' + shopName;
        // 设置后台加载符 若还在后台查找书籍则无法再次加载 防止重复访问
        loading = true;
        // search
        $.getJSON(url, function (data){
           if (data.success){
               // 获取最大店铺数目
               var maxItems = data.count;
               var html = '';

               var shopList = data.shopList;

               shopList.map(function (item,index){
                   // 每条信息拼接出一个card
                   html += ''
                       + '<div class="card" data-shop-id="'
                       + item.shopId
                       + '">'

                           + '<div class="card-header">'
                           + item.shopName
                           + '</div>'

                           + '<div class="card-content">'
                             + '<div class="list-block media-list">'
                                + '<ul>'
                                    + '<li class="item-content">'

                                        + '<div class="item-media">'
                                             + '<img src="'
                                                + item.shopImg
                                                + '" width="44">'
                                        + '</div>'

                                        + '<div class="item-inner">'
                                            + '<div class="item-subtitle">'
                                                  + item.shopDesc
                                            + '</div>'
                                        + '</div>'

                                     + '</li>'
                                + '</ul>'
                             + '</div>'
                           + '</div>'

                           + '<div class="card-footer">'
                                 + '<p class="color-gray">'
                                    + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                                 + '更新'
                                 + '</p>'

                           + '<span>点击查看</span>'
                           + '</div>'
                       + '</div>';
               });
               // 将信息追加到html控件内
               $('.list-div').append(html);

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

    // 下拉屏幕自动分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });


    // 点击店铺卡片进入详情页 给图片绑定事件
    $('.shop-list').on('click','.card',function (e){
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/emporium/frontend/shopdetail?shopId=' + shopId;
    });
    // 启动下拉刷新控件
    $.init();

    // 选择新的店铺类别 重置页码 清空之前的店铺列表 按照新的类别去查询
    $('#shoplist-search-div').on('click','.button',function (e){
        if (parentId){
            shopCategoryId = e.target.dataset.categoryId;
            // 若之前选定了category，则移除其选定的效果，改成选定新的
            if ($(e.target).hasClass('button-fill')){
                $(e.target).removeClass('button-fill');
                shopCategoryId = '';
            }else {
                $(e.target)
                    .addClass('button-fill')
                    .siblings()
                    .removeClass('button-fill');
            }
            //由于查询条件改变 清空店铺列表再进行查询
            $('.list-div').empty();
            //重置页码
            pageNum = 1;
            addItems(pageSize,pageNum);
        }else {
            // 传递父类为空 则靠父类查询
            parentId = e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')){
                $(e.target).removeClass('button-fill');
                parentId = '';
            }else {
                $(e.target)
                    .addClass('button-fill')
                    .siblings()
                    .removeClass('button-fill');
            }
            // 如果查询条件改变 清空列表再查询
            $('.list-div').empty();
            //重置页码
            pageNum = 1;
            addItems(pageSize,pageNum);
            parentId = '';
        }
    });

    // 查询名称发生改变 清空信息 查询
    $('#search').on('input',function (e){
        shopName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 区域信息发生变化 重置页码 查询
    $('#area-search').on('change',function (){
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize,pageNum);
    })

    $('#me').click(function() {
        $.openPanel('#panel-left-demo');
    });


})