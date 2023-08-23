window.onload  = function(){
    //获取显示隐藏按钮DOM对象
    let showBtn = document.getElementById('showBtn');
    //获取显示订单明细DOM对象
    let detailetBox = document.getElementById('detailetBox');
    
    detailetBox.style.display='none';

    showBtn.onclick = function(){
        //判断订单明细DOM对象是否隐藏
        //如果是就显示，else隐藏
        if(detailetBox.style.display=='none'){
            detailetBox.style.display='block';
        }
        else{
            detailetBox.style.display='none';
        }

    }
}