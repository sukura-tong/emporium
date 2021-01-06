
$(function (){
    // var userId = getQueryString('userId');

    var modifyCodeUrl =   '/emporium/frontend/updateusercodeinfo';

    var oldCode = '';
    var newCodeOne = '';
    var newCodeTwo = '';

    function modifyUserCode(oldCode,newCodeOne,newCodeTwo){
        var url = modifyCodeUrl + '?oldCode=' + oldCode + '&newCodeOne=' + newCodeOne + '&newCodeTwo=' + newCodeTwo;
        $.getJSON(url,function (data){
            if (data.success){
                $.toast('提交成功 !');
            }else {
                $.toast('提交失败 !');
            }
        })
    }


    $('#submit').click(function (){
         oldCode = $('#old-pwd').val();
         newCodeOne = $('#pwd-one').val();
         newCodeTwo = $('#pwd-two').val();
         modifyUserCode(oldCode,newCodeOne,newCodeTwo);
    })
})