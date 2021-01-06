
$(function (){

    // 查询userId并追加到修改密码的a标签内
    var userId = getQueryString('userId');
    // 给标签追加shopId属性
    var changecodeurl = '/emporium/frontend/modifycode?userId='+userId;
    $('#change-code').attr('href', changecodeurl);

    getlist();

    // 根据用户信息获取用户列表
    function getlist(e){
        $.ajax({
            url:"/emporium/shopadmin/getshoplist",
            type:"get",
            dataType:"json",
            success: function (data){
                if (data.success){
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }
    function handleUser(data){
        $('#user-name').text(data.name)
    }

    function handleList(data) {
        var html = '';
        data.map(function (item, index) {
            html += '<div class="row row-shop">'
                    + '<div class="col-40">'
                          + item.shopName
                    +'</div>'
                    + '<div class="col-40">'
                        + shopStatus(item.enableStatus)
                    +'</div>'
                    + '<div class="col-20">'
                        + goShop(item.enableStatus, item.shopId)
                    +'</div>'
                + '</div>';

        });
        $('.shop-wrap').html(html);
    }

    function shopStatus(status){
        if (status == 0){
            return '审核中';
        }else if (status == -1){
            return '店铺非法';
        }else if (status == 1){
            return '审核通过';
        }
    }

    // 访问店铺管理
    function goShop(status,id){
        if (status == 1){
            return '<a href="/emporium/shopadmin/shopmanagement?shopId='
                + id
                + '">进入</a>';
        }else {
            return '';
        }
    }



});