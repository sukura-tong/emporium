$(function (){
    var registerUrl = '/emporium/frontend/registerusermessage';


    $('#submit').click(function (){

        var userMessage = {};

        userMessage.uname = $('#user-name').val();
        userMessage.upwd = $('#user-pwd').val();


        userMessage.status = $('#user-select').find('option').not(function (){
                return !this.selected;
            }).data('id')


        var formData = new FormData();

        formData.append('userMessageStr', JSON.stringify(userMessage));
        // 验证码
        var  verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual){
            $.toast('请输入验证码!');
            return ;
        }
        // formData.append('verifyCodeActual', verifyCodeActual);

        $.ajax({
            url:registerUrl,
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

    })


})