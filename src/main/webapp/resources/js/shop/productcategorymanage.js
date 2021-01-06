
$(function (){

    var listUrl = '/emporium/shopadmin/getproductcategorylist';
    var addUrl = '/emporium/shopadmin/addproductcategorys';
    var deleteUrl = '/emporium/shopadmin/removeproductcategory';

    getList()

    // 根据用户信息获取数据
    function getList(){
        $.getJSON(listUrl,function (data){
            if (data.success) {
                var dataList = data.productCategoryList;
                handleList(dataList)
            }
        });
    }

    function handleList(data){
        var html = '';
        data.map(function (item, index) {
            // 原始数据是now
            // 新增数据temp
            html +=''
                +'<div class="row row-product-category now">'
                + '<div class="col-33">'
                + item.productCategoryName
                +'</div>'
                + '<div class="col-33">'
                + item.priority
                +'</div>'
                + '<div class="col-33"><a href="#" class="button delete" data-id="'
                + item.productCategoryId
                + '">删除</a></div>'
                + '</div>';

        });
        $('.category-wrap').html(html);
    }

    $('#new').click(function (){
        var tempHtml = '<div class="row row-product-category temp">'
            + '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
            + '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        // append代表以相同格式追加
        $('.category-wrap').append(tempHtml);
    });
    //submit
    $('#submit').click(function (){
        // 遍历新增的行
        var tempAddr = $('.temp')
        var productCategoryList = [];
        tempAddr.map(function (item, index){
            var tempObj = {};
            tempObj.productCategoryName = $(index).find('.category').val();
            tempObj.priority = $(index).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority){
                productCategoryList.push(tempObj);
            }
        });
        // 注意ajax写的传入数据是data
        $.ajax({
            url : addUrl,
            type : 'POST',
            data : JSON.stringify(productCategoryList),
            contentType : 'application/json',
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    getList();
                } else {
                    $.toast('提交失败！');
                }
            }
        });
    });

    // 两种不同的删除
        //  前端直接生成的 直接删除即可
        //  后端数据删除 需要走数据库

    // 事件绑定
    $('.category-wrap').on('click', '.row-product-category.temp .delete', function (e){
        console.log($(this).parent().parent());
        // 从ID节点向上找到父目录div标签进行数据移除
        $(this).parent().parent().remove();
    });

    $('.category-wrap').on('click','.row-product-category.now .delete',function (e){
        var target = e.currentTarget;
        // var deleteProductCategoryId = 0
        $.confirm('sure?',function (){
            $.ajax({
                url : deleteUrl,
                type : 'POST',
                data: {
                    productCategoryId : target.dataset.id
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('删除成功！');
                        getList();
                    } else {
                        $.toast('删除失败！');
                    }
                }
            });
        })
    })

})