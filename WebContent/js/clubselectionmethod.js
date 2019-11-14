$(document).ready(function(){
    // 設定按鈕
    $("button#settingbtn").click(function(){
        // 隱藏第一頁
        $("#ClubTime").hide();
        // 隱藏第二頁
        $("#Model").slideDown();
        // 左側Tab動畫設定：寬度、透明度、顏色、文字改變(hover change)
        $("#timeselecttab").animate({
            width: "10%", 
            opacity:"0.5"
        },250)
        .css({"background-color":"#333"})
        .text("<< 返回")
        .hover(function(){
            $(this).css({"opacity":"1"});
        },function(){
            $(this).css({"opacity":"0.5"});
        });
        // 中央Tab設定
        $("#modselecttab").animate({
            marginLeft:"19%", 
            opacity:"1"
        },250);
        // 邊框動畫
        $(".tabcontent").animate({
            borderTopWidth:"7px",
            borderLeftWidth: "0px"
        },250);
    });

    // 點擊返回
    $("#timeselecttab").click(function(){
        $("#Model").slideUp(250);
        $("#ClubTime").show();
        $("#timeselecttab").animate({
            width: "25%",
            opacity:"1"
        },250)
        .css({"background-color":"#3FBF9A"})
        .text("線上選社時間")
        .unbind('mouseenter mouseleave');      
        $("#modselecttab").animate({
            marginLeft:"4%",
            opacity:"0.1"
        },250);

        $(".tabcontent").animate({
            borderTopWidth:"0px",
            borderLeftWidth: "7px"
        },250);
    });

    //選社模式選擇
    $("input").click(function(e){
        var  target = $(e.target);
        //選擇分發選社
        if (target.is("input#dspach") && target.is(":checked")) {
            $("#minvalue").css({"opacity":"1"});
            $("#minvalue input").addClass('borderanimation');
            $("#minvalue input").prop('disabled',false);
        }
        //選擇即時選社
        else if(target.is("input#intime") && target.is(":checked")){
            $("#minvalue").css({"opacity":"0.2"});
            $("#minvalue input").removeClass('borderanimation');
            $("#minvalue input").prop('disabled', true);
        }
    });

    // 社團年級設定類別原型
    var Classlimit = function(gradeId,gradeIdText){
        this.gradeId = gradeId;
        this.gradeIdText = gradeIdText;
        this.classes = $("#classlimit " + this.gradeId + " input");
        this.checked = $("#classlimit " + this.gradeId +" input:checked");
        this.checkedText ="";
    };
    Classlimit.prototype.getClassNumber = function () {
        return classes.length;
    };
    Classlimit.prototype.getCheckedNumber = function(){
        return checked.length;
    };
    Classlimit.prototype.printSelection = function(){
        if (this.checked.length === this.classes.length) {
            $(this.gradeId +"ckbox").prop("checked",true);
            $("#classlimit.result span"+ this.gradeId +"result").text(this.gradeIdText);
        } else if (this.checked.length <= 0) {
            $("#classlimit.result span"+ this.gradeId +"result").text("尚未選取"+this.gradeIdText);
        } else if (this.checked.length > 0 && this.checked.length < this.classes.length && this.checked !== undefined) {
            $(this.gradeId +"ckbox").prop("checked",false);

            for(var i=0;i < this.checked.length; i++){
                this.checkedText += this.checked.eq(i).siblings("label").text()+" ";
            }
            $("#classlimit.result span"+ this.gradeId +"result").text(this.checkedText);
        }
    }

    updateClassV2();
    // 顯示年級選項動畫
    $("#classlimit.result a").click(function(e){
        e.preventDefault();
        $("#classlimit .selection").slideToggle(250);
        $("#classlimit i").toggleClass("rotate");
    });
    //點擊年級班級事件
    $('#classlimit input[type=checkbox]').click(function(event) {
        if($(this).is("#G1ckbox")){
            $("#G1 input").prop("checked" , $("#G1ckbox").prop("checked"));
        } else if ($(this).is("#G2ckbox")) {
            $("#G2 input").prop("checked" , $("#G2ckbox").prop("checked"));
        } else if ($(this).is("#G3ckbox")) {
            $("#G3 input").prop("checked" , $("#G3ckbox").prop("checked"));
        }
        updateClassV2();
    });
    //更新班級年級顯示
    function updateClassV2() {
        var resultText=" ";
        var G1 = new Classlimit("#G1","一年級");
        var G2 = new Classlimit("#G2","二年級");
        var G3 = new Classlimit("#G3","三年級");
        G1.printSelection();
        G2.printSelection();
        G3.printSelection();
    }
    var resultTr = $(".resulttable tbody tr");
    var checkboxesinput = $(".resulttable tbody tr td input");
    //選取全部班級
    $('.resulttable th input[name=ckall]').click(function(event) {
        var checkboxesinput = $(".resulttable tr td input");
        var checkAll = $(this).prop('checked');
        checkboxesinput.prop('checked', checkAll);
    });
    //點擊Tr勾選checkbox

    resultTr.click(function(event){
        var thischeckbox = $(this).find("input");
        if (event.target !== thischeckbox.get(0)) {
            thischeckbox.prop('checked',!thischeckbox.prop("checked"));
        }
        cosole.log("Click!");
    });

    $('#copyselectsetting').hide();
    //複製設定顯示
    $('#usecopysetting').click(function(){
        if ($(this).prop('checked')) {
            $('#copyselectsetting').show();
        }
        else{
            $('#copyselectsetting').hide();
        }
    });   
});