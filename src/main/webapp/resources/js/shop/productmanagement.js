$(function (){

    // 获取店铺列表的url
    var listUrl = '/emporium/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
    // 商品下架url
    var statusUrl = '/emporium/shopadmin/changeproductstatus';

    getList();
    // 获取初始列表信息
    function getList(){
        $.getJSON(listUrl, function (data){
            if (data.success){
                var productList = data.productList;
                var tempHtml = '';

                // item代表每一项
                productList.map(function (item,index){
                    var textOp = "下架";
                    var contrayStatus = 0; // 上架
                    if (item.enableStatus == 0){
                        textOp = "上架";
                        contrayStatus = 1;
                    }else {
                        contrayStatus = 0;
                    }

                    // 拼接html信息
                    tempHtml += ''
                        + '<div class="row row-product">'
                        + ' <div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">'
                        + '编辑'
                        + '</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contrayStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">'
                        + '预览'
                        + '</a>'
                        + '</div>'
                        + '</div>';
                });
                // 给product-wrap的div标签内部插入信息
                $('.product-wrap').html(tempHtml)
            }
        });

    }

    // 事件绑定 将class为product-warp的a标签绑定上相应的点击事件
    $('.product-wrap')
        .on('click','a',function (e){
            var target = $(e.currentTarget);
            // if (target.hasClass('edit')){
                // 如果class edit 则点击进入店铺信息编辑页面 并带有producrId参数
                if (target.hasClass('edit')){
                    // 如果有class edit 则点击进入店铺信息编辑页面 并传递productId 参数
                    window.location.href = '/emporium/shopadmin/productoperation?productId='
                        + e.currentTarget.dataset.id;
                }else if (target.hasClass('status')){
                    // 标识产品上下架信息
                    changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
                }else if (target.hasClass('preview')){
                    // 如果有class preview则标识前端展示系统详情页面预览该商品情况
                    window.location.href = '/emporium/frontend/productdeatil?productId='
                        + e.currentTarget.dataset.id;
                }
            // }
        });

    // 转换状态
    function changeItemStatus(id, enableStatus){
        // 定义 product JSON对象 并添加productID及其状态
        var product = {};
        product.productId= id;
        product.enableStatus = enableStatus;
        $.confirm('sure',function (){
            $.ajax({
                url : statusUrl,
                type : 'POST',
                data : {
                    productStr : JSON.stringify(product),
                    // 跳过验证码
                    statusChange : true
                },
                dataType : 'json',
                success : function (data){
                    if (data.success){
                        $.toast('操作成功')
                        getList();
                    }else {
                        $.toast('操作失败')
                    }
                }
            })
        })
    }
})