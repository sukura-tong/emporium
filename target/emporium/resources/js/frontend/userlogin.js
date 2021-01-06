
$(function (){

    var userLoginUrl = '/emporium/frontend/getoperationlogin';

    var name = '';
    var password = '';
    var select = '';

    function getUserLoginOpetaion(uname,upwd){
        var url = userLoginUrl + '?name=' + uname + "&password=" + upwd + "&userselect=" + select;

        $.getJSON(url, function (data){
            if (data.success){
                // 根据登录的人员的不同选择不同的初始界面
                var tip = data.choosetip;
                    if (tip == 0){
                        //重定向到查询页面
                        $.toast('提交成功 !');
                        window.location.href = '/emporium/frontend/index?userId=' + data.userId;
                    }else if (tip == 1){
                        //重定向到查询页面
                        $.toast('提交成功 !');
                        window.location.href = '/emporium/shopadmin/shoplist?userId=' + data.userId;
                    }
            }else {
                //重新加载该页面
                $.toast('提交失败' + data.errMsg);
                // $.refreshScroller();
                window.location.href = '/emporium/frontend/login';
            }
        })
    }


    $('#submit').click(function (){
        name = $('#user-name').val();
        password = $('#user-id').val();
        select = $('#user-select').val();


        getUserLoginOpetaion(name,password);
    })
})