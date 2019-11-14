
function refill(){
	if(confirm("確定重新填寫？")){
	location.replace(location.href);
	}
}
    
$(document).ready(function(){
    //隱藏錯誤訊息
    $(".errormsg span").click(function(){
        $(this).hide();
    });

    var staff_cname = $("input#staff_cname"),
        staff_code = $('input#staff_code'),
        $appendHere = $("div#tagHere"),
        currentFocus = -1,
        teacherlist = $(".sugs li");

    //建議選項點擊事件
    $("#suggestbox li").click(function () {
        staff_cname.val($(this).text()).focus();
        staff_code.val($(this).find('input[type=hidden]').val());
    });

    //input 輸入事件
    staff_cname.bind("input",function(event){
        var inputval = $(this).val().toUpperCase();

        if (inputval == '' || inputval == ' ') {
            moveFocus("reset");
        } else {
            $("#suggestbox").show();            
        }
        for (var i = 0; i < teacherlist.length; i++) {
            if(teacherlist[i].innerHTML.toUpperCase().indexOf(inputval) > -1 ) {
                teacherlist[i].style.display = "";
            } else {
                teacherlist[i].style.display = "none";
            }
        }
    });

    //input 方向鍵與Enter觸發
    staff_cname.keydown(function(event){
        var sugs = $("#sugs li:visible");
        var length = sugs.length;
        if (length > 0) {
            if (event.keyCode == 40) { 
                moveFocus("down");

            } else if (event.keyCode == 38) { 
                moveFocus("up");

            } else if (event.keyCode == 13) { 
                event.preventDefault();
                if (currentFocus > -1) {
                    teacherlist[currentFocus].click();
                    moveFocus("reset");
                } else {
                    for (var i = 0; i < length; i++) {
                        var sug = sugs.eq(i);
                        if (sug.text() == this.value) {
                            staff_code.val(sug.find('input[type=hidden]').val());
                            addTag(); 
                            moveFocus("reset");
                            return;
                        }
                    }
                    alert('查無資料');
                }
            } else {
                moveFocus("reset");
            }
        } else {
            if (event.keyCode == 13) {
                event.preventDefault();
                if($(this).val().trim() !== '') {
                    addTag(); 
                    moveFocus("reset");
                }
                return false;
            }
        }
    });

    //建議選項選擇
    function moveFocus(diration){
        var div = $('div#suggestbox');
        teacherlist.removeClass("suggestbox-select");

        if (diration == "up") {
            currentFocus--;
            if(currentFocus < 0) currentFocus = (teacherlist.length -1);
        } else if (diration == "down") {
            currentFocus++;
            if (currentFocus >= teacherlist.length) currentFocus = 0;
        } else if (diration == "reset"){
            currentFocus=-1;
            div.hide();
        }

        if (currentFocus > -1 ) {
            if (teacherlist.eq(currentFocus).is(':hidden')) {
                moveFocus(diration);
            } else {
                var scrollTop = div.scrollTop() / 41; 
                var l = currentFocus - teacherlist.eq(currentFocus).addClass("suggestbox-select").prevAll('li:hidden').size();
                if (l < scrollTop || l > (scrollTop + 4)) {
                    div.scrollTop((l - 4) * 41);
                }
            }
        }
    }

    //標籤新增方法
    function addTag() {
        var $tag = $("<div />"), 
            $a = $("<a href='#' />"), 
            $span = $("<span />"),
            code = staff_code.val(),
            staves = $('div.teachertag input[name=staff]');
        if (code) {
            for (var i = 0, l = staves.length; i < l; i++) {
                if (staves.eq(i).val() == code) {
                    alert('已重複')
                    return;
                }
            }
            $tag.addClass('teachertag');
            $('<i class="fa fa-times" aria-hidden="true"></i>').appendTo($a);
            $span.text(staff_cname.val());
            $('<input type="hidden" name="staff" />').val(code).appendTo($tag);
            $a.appendTo($tag);
            $span.appendTo($tag);
            $tag.appendTo($appendHere);
            staff_cname.val('');
            staff_code.val('');
        } else {
            alert('查無資料');
        }
    }
    
    $('div#tagHere').on('click', 'div.teachertag > a', function(e) {
        e.preventDefault();
        $(this).parent().remove();
    });
    $(document).click(function(){
        moveFocus("reset");
    });
    updateWordLimet($("#club_info"));

    function updateWordLimet(area){
        $('#wordlimet').text('( ' + Math.max(0, 100 - area.val().length) + ' / 100 )');
        if (area.val().length == 100){
            $('#wordlimet').css("color" , "red");
        }
        else
            $('#wordlimet').css("color" , "gray");
    }
    
    $("#club_info").on('input',function(){
        updateWordLimet($(this));
    });
    
    
});