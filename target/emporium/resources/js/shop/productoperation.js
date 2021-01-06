$(function (){
    // 从url获取Id参数的值

    var productId = getQueryString('productId');
    // var productId = 8;
    // 通过productId获取产品信息url
    var infoUrl = '/emporium/shopadmin/getproductbyid?productId=' + productId;
    // 获取当前店铺设定的商品类别列表的url
    var categoryUrl = '/emporium/shopadmin/getproductcategorylist';
    // 更新商品信息的url
    var productPostUrl = '/emporium/shopadmin/modifyproduct';
    // 根据标识符判断是编辑页面还是注册页面
    var isEdit = false;



    if (productId){
        // 获取产品信息
        getInfo(productId);
        isEdit = true;
    }else {
        // 新增  获取固定内容
        getCategory();
        // 更正提交路径信息
        productPostUrl = '/emporium/shopadmin/addproduct';
    }

    // 获取后台信息
    function getInfo(id){
        $.getJSON(infoUrl, function (data){
            if (data.success){
                // 从JSON中获取当前查询到的product对象 并赋值给表单控件
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(product.promotionPrice);

                // 获取原始商品类别以及该店铺下的所有商品类别列表
                var optionHtml = '';
                var operationArr = data.productCategoryList;
                var operationSelected = product.productCategory.productCategoryId

                // 生成前端的HTML商品列表 并默认选择控件的选取属性
                operationArr.map(function (item, index){
                    var isSelect = operationSelected === item.productCategoryId ? 'selected' : '';

                    // 拼接表单
                    optionHtml += ''
                        + '<option data-value="'
                        + item.productCategoryId
                        + '"'
                        + isSelect
                        + '>'
                        + item.productCategoryName
                        + '</option>';
                });
                // 将数据写入前端
                $('#category').html(optionHtml)
            }
        });
    }

    // 为商品添加操作提供店铺下的所有商品类别列表
    function getCategory(){
        // 功能实现
            // 1 从后台数据库查询出需要的内容
            // 2 将数据写入到前端html控件内
            $.getJSON(categoryUrl, function (data){
                if (data.success){
                    var productCategoryList = data.productCategoryList;
                    var optionHtml = '';
                    productCategoryList.map(function (item, index){
                        optionHtml += ''
                            + '<option data-value="'
                            + item.productCategoryId
                            + '">'
                            + item.productCategoryName
                            + '</option>';
                    });
                    $('#category').html(optionHtml);
                }
            });
    }

    // 详情图片控件事件绑定 仅在对最后一个控件操作时 进行响应
    // 若该控件组最后一个元素发生变化 即上传了图片 则进行响应
    $('.detail-img-div').on('change','.detail-img:last-child',function (){
        if ($('.detail-img').length < 6){
            $('#detail-img').append('<input type="file" class="detail-img">')
        }
    })

    // 提交响应事件
    $('#submit').click(function (){
        // 创建商品JSON对象
        var product = {};
        // 获取基本属性
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority    = $('#priority').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        // 获取选定的商品类别值
        product.productCategory = {
               productCategoryId : $('#category').find('option').not(function (){
                   return !this.selected;
               }).data('value')
        };

        product.productId = productId;
        // 获取缩略图文件流
        var thumbnail = $('#small-img')[0].files[0];
        console.log(thumbnail);

        //生成表单对象 用于接收参数并传递给后台
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        //遍历详情图控件
        $('.detail-img').map(function (item, index){
            //判断控件是否已经选择文件
            if ($('.detail-img')[item].files.length > 0){
                // 将第 i 个文件流赋值给key 为productImgi的表单键值对里
                formData.append('productImg' + item, $('.detail-img')[item].files[0]);
            }
        })

        // 将product JSON 里面的对象转换成字符流保存到表单对象key，并为productStr的键值对里
        formData.append("productStr", JSON.stringify(product));
        // 获取验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual){
            $.toast('请输入验证码');
            return ;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        // 需要保证ajax传入的数据格式与后台接收的数据格式相同
        // 使用Ajax 进行路径请求
        $.ajax({
            url:productPostUrl,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data){
                if (data.success){
                    $.toast('提交成功 !');
                }else {
                    $.toast('提交失败' + data.errMsg);
                }
                // 每次执行ok后更换验证码
                $('#captcha_img').click();
            }
        })

    });

    // 访问页面查询元素信息
    function getList(){
        $.getJSON(categoryUrl, function (data){
            if (data.success){
                var productCategoryList = data.productCategoryList;
                var optionHtml = '';

                productCategoryList.map(function (item, index) {
                    optionHtml += '<div class="row row-product"><div class="col-30">'+ item.productCategoryName +'</div><div class="col-20">'+ item.priority +'</div><div class="col-50">'+ item.productCategoryName+'</div></div>';

                });
                $('.shop-wrap').html(optionHtml);
            }
        })
    }
})