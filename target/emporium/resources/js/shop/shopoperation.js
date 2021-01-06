// 获取初始信息填充到前端界面
// 当点击提交时通过Ajax将请求转发到后台

$(function (){
    // 解析get请求的url上的shopId
    var shopId = getQueryString('shopId');
    // 如果shopId为空则证明是注册店铺否则代表修改店铺信息
    var isEdit = shopId ? true : false;

    // 完成信息初始化 localhost:8888 + URL
    var initUrl = '/emporium/shopadmin/getshopinitinfo';
    // 注册信息
    var registerShopUrl = '/emporium/shopadmin/registershop';

    // 根据店铺Id获取店铺信息
    var shopInfoUrl = '/emporium/shopadmin/getshopbyid?shopId=' + shopId;

    // 更新店铺信息
    var editShopUrl = '/emporium/shopadmin/modifyshop';

    if (!isEdit){
        getShopInitInfo();
    }else {
        getShopInfo(shopId);
    }

    function getShopInfo(shopId){
        $.getJSON(shopInfoUrl,function (data){
            if (data.success){
                var shop = data.shop;
                // 根据查询到的值给html页面内控件赋值
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                // 将shopCategory的内容以option形式进行保存
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '"selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                // 获取下拉列表
                var tempAreahtml = '';
                data.areaList.map(function (item, index){
                    tempAreahtml += '<option data-id="' + item.areaId
                        + '">' + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);//控件绑定属性
                // 设置店铺信息无法进行修改
                $('#shop-category').attr('disabled','disabled');
                $('#area').html(tempAreahtml);
                // 默认选择
                $('#area option[data-id="' + shop.area.areaId + "']").attr('selected',"selected");
            }
        });
    }

    function getShopInitInfo(){
        $.getJSON(initUrl, function(data){
            if (data.success){
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item,index){
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">'
                        +item.shopCategoryName + '</option>>';
                });
                data.areaList.map(function (item,index){
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                    +item.areaName + '</option>';
                });
                // 将信息发送到html文件
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }

    // 点击提交时 进行响应
    $('#submit').click(function (){
        var shop = {};
        if (isEdit){
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();

        // 从列表中获取信息
        shop.shopCategory = {
            shopCategoryId:$('#shop-category').find('option').not(function (){
                return !this.selected;
            }).data('id')
        };

        shop.area = {
            areaId:$('#area').find('option').not(function (){
                return !this.selected;
            }).data('id')
        };

        var shopImg = $('#shop-img')[0].files[0];

        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));

        // 验证码
        var  verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual){
            $.toast('请输入验证码!');
            return ;
        }
        formData.append('verifyCodeActual', verifyCodeActual);
        $.ajax({
            url:isEdit?editShopUrl:registerShopUrl,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data){
                if (data.success){
                    alert('success！');
                    $.toast('提交成功 !');
                }else {
                    // alert('error！');
                    $.toast('提交失败' + data.errMsg);
                }
                // 每次执行ok后更换验证码
                $('#captcha_img').click();
            }
        })
    })
})