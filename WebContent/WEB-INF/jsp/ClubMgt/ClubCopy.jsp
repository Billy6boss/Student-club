<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="css/clubstyle.css" /><%-- Club use css --%>
    <title>社團複製</title>
    <jsp:include page="/WEB-INF/jsp/include.jsp" />
    <style>
    @import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);
    
    body{ 
        background: url("/StuClub/images/lined_paper.png") repeat;
        font-family: "cwTeXYen", sans-serif; 
        line-height: 1; 
        font-size: 21px;
    }
    ol {
        padding-left: 20px;
    }
    ol li{
        font-weight: bold;
    }
    ul{
    	margin-bottom: 0px;
    }
    /*麵包削*/
    .breadcrumb{
        width: 100%;
        position: absolute;
        background:transparent;
        padding:0;
        background-color: #efefef;
        /*font-size: 25px;*/
    }
    
    /*麵包削內箭頭樣式設計*/
    .breadcrumb div{
        background:#3fbf9a;
        position: relative;
        display: inline-block;
        padding: 20px;
        padding-left: 40px;
        text-align:center;
        margin-right: 34px;
        margin-left: 0px;
        font-weight: bold;
        transition: all 0.5s;
        text-shadow:7px 5px 7px gray;
        /*box-shadow: 15px 6px 30px gray;*/
    
    }
    .breadcrumb div:before {
        content: "";
        position: absolute;
        right: -40px;
        bottom: 0;
        width: 0;
        height: 0;
        border-left: 40px solid #3fbf9a;
        border-top: 30px solid transparent;
        border-bottom: 30px solid transparent;
    }
    .breadcrumb #step1:after,#step2:after {
        content: "";
        position: absolute;
        left: 0;
        bottom: 0;
        width: 0;
        height: 0;
        border-left: 40px solid #efefef;
        border-top: 30px solid transparent;
        border-bottom: 30px solid transparent;
    }
    
    
    #step0, #step1.showup, #step2.showup{
        left:0;
    }
    #step1,#step2{
        left:100%;  
    }
    .breadcrumb div.fadeout{
        opacity:0.4;
        color: #8CD8C1;
        text-shadow:0px 0px 0px #444;
    }
    /*整體*/
    .contantbox{
        width: 100%;
        overflow:hidden;
        position: relative;
        height: 570px;
        border-left: 5px solid #3FBF9A;
        /*padding-left: 15px;*/
        /*border-top: 5px solid #3fbf9a;*/
    }
    /*頁面內容*/
    .pagecontant{
        width: 100%;
        margin-top: 100px;
    }
    .page0, .page1, .page2{
        /*border-left: solid 5px #3FBF9A;*/
        width: 98%;
        left: 0px;
        position:absolute;
        background: transparent;
        padding-left: 5px;
    }
    .page1{
        left: 100%;
        text-align: center;
    }
    .page2{
        left: 200%;
        padding: 0px 5px;
    
    }
    .icon{
        border: none;
        vertical-align: -400%;
    }
    </style>
    <script>
        $(document).ready(function(){
            var fromSemester, toSemester,
                movement = "100%",
                nowpage = 0,
                copyselection = $(".page1 .copyselection ul li label"),
                from_year = $('#from_year'),
                from_sem = $('#from_sem'),
                to_year = $('#to_year'),
                to_sem = $('#to_sem'),
                result = '${result}';
                
                
                
            if (result) {
                alert('複製完成，將跳轉回社團系統首頁');
                location.replace('/StuClub/ClubMgt');
                return;
            } else {
                fromSemester = JSON.parse('${fromSemester}');
                toSemester = JSON.parse('${toSemester}');
            }
    
            //下一頁
            function nextPage(argument) {
                nowpage++;
                $(".page0, .page1, .page2").animate({
                    left:"-=" + movement 
                });
                $("#step" + (nowpage-1)).addClass("fadeout");
                $("#step"+ nowpage).addClass("showup");
            }
    
            //上一頁
            function previousPage(argument){
                $(".page0, .page1, .page2").animate({
                    left:"+=" + movement
                });
                $("#step"+ nowpage).removeClass("showup");
                $("#step" + (nowpage-1)).removeClass("fadeout");
                nowpage--;
            }
            
            //第三頁 選取全部
            $('th input[name=ckall]').click(function(event) {
                var checkboxesinput =$("tr td input");
                var checkAll = $(this).prop('checked');
                checkboxesinput.prop('checked', checkAll);
                checkedCount();
            });
    
            //選取計數
            function checkedCount() {       
                var checkednumber = $(".resulttable tbody td input:checked").length;
                $( ".selectedclub" ).text(checkednumber);
            };
    
            //第三頁 tr 點擊選取
            $("#selectclub tbody").on('click','tr',function(){
            	console.log($(this));
                var thischeckbox = $(this).find("input");
                if (event.target !== thischeckbox.get(0)) {//查看點擊的地方是否=input[type=checkbox]
                    thischeckbox.prop('checked',!thischeckbox.prop("checked"));
                    checkedCount();
                }
            });
    
            //複製訊息更新
            function updateCopyinfo(){
                var copyselection = $(".page1 .copyselection ul li input:checked");
                var copyinfo = "";
                var from = $("#from_year option:selected").text() + "學年" + $("#from_sem option:selected").text() + "學期";
                var to = $("#to_year option:selected").text() + "學年" + $("#to_sem option:selected").text() + "學期";
    
                      
                for (var i = 0; i < copyselection.length; i++) {
                    copyinfo += copyselection.eq(i).parents("label").text();
                }
                $(".infobox #from").text(from);
                $(".infobox #to").text(to);

                                                      
            }
    
            //Main------------------------------------------------
            $("#startcopy,#nextpage").click(nextPage);
            $("#back1,#back2").click(previousPage);
            $("#selectclub tbody").on('click','input[type=checkbox]',(checkedCount));
            $("#nextpage").click(updateCopyinfo);
    
    
                  
            $(".page2 #confirmcopy").click(function(e){
                var copyinfo =$(".copyinfo").text();
                                            
                if(window.confirm(copyinfo + " 確認複製？")){
                    //複製..       			
         				
                    $("#ClubCopy_form").submit(function () {
                    	$(".fullscreensping").show();
                    });
                } else {
                    //不複製..
                	e.preventDefault();
                }
            });
            
            function getClubsByYearAndSem() {
                $.ajax({
                    url : 'ClubCopy',
                    type : 'get',
                    data : {
                        'year' : from_year.val(),
                        'sem' : from_sem.val()
                    },
                    dataType : 'json',
                    error : function(data) {
                        alert('查詢社團時發生ajax error：' + data);
                    },
                    success : function(data) {
                        if (data) {
                            var target = $('div.page2 tbody').empty();
                            for (var i = 0, l = data.length; i < l; i++) {
                                var clubSem = data[i];
                                target.append('<tr><td><input name="club_num" type="checkbox" value="' 
                                		+ clubSem.club_num + '"></td><td>' + clubSem.club.clubCategory.cat_name+ '</td>'
                                              + '<td>' + clubSem.club.club_name+ '</td>'
                                              + '<td>' + clubSem.clubMembers.length + '</td></tr>');
                            }
                        } else {
                            alert('查詢社團失敗，請確認該學年期是否已有社團資料。');
                        }
                    }
                });
            }
            
            
            
            function setYear(e) {
                if (!e) return;
                
                var target, sems,
                    isFrom = e.target.name.startsWith('from'),
                    value = e.target.value;
                
                if (isFrom) {
                    target = from_sem;
                    sems = fromSemester[value];
                } else {
                    target = to_sem;
                    sems = toSemester[value];
                }
                
                target.empty();
                if (sems) {
                    for (var i = 0, l = sems.length; i < l; i++) {
                        var sem = sems[i];
                        if (isFrom || !fromSemester[value] || fromSemester[value].indexOf(sem) == -1) { // continue if any club exists in target semester.
                            target.append('<option value="' + sem+ '">' + semToString(sem)+ '</option>');
                        }
                    }
                } else {
                    target.append('<option>無學期可選擇</option>');
                    target.prop('disabled', true);
                }
                
                if (isFrom) getClubsByYearAndSem();
            }
            
            function semToString(s) {
                var result = null;
                switch (s) {
                case '1':
                    result = '上';
                    break;
                case '2':
                    result = '下';
                    break;
                case '3':
                    result = '寒假';
                    break;
                case '4':
                    result = '暑假';
                    break;
                }
                return result;
            }
            
            function init() {
                if (fromSemester && toSemester) {
                    for (var fromYear in fromSemester) {
                        from_year.append('<option value="' + fromYear + '">' + fromYear+ '</option>' );
                    }
					for (var toYear in toSemester) {
					    if (!fromSemester[toYear] || fromSemester[toYear].length < 4) {
					    	to_year.append('<option value="' + toYear + '">' + toYear+ '</option>' );
					    }
                    }
					from_year.change();
					to_year.change();
                } else {
                    alert('查詢學期設定失敗。');
                }
            }
            
            from_year.change(setYear);
            from_sem.change(getClubsByYearAndSem);
            to_year.change(setYear);
            init();
        });
    </script>
</head>
<body>
    <div id="outer">
        <jsp:include page="/WEB-INF/jsp/includeHead.jsp" />  
        <div id="main">		
            <jsp:include page="includeMenu.jsp" />	
            <div id="content">										
                <div id="box2"> 
                    <%--網頁內容寫在這 --%>
                    <p>${ud.sch_cname} ${ud.staff_cname} 您好：</p>
                    <div class="contantbox">
                        <div class="breadcrumb">
                            <div id="step0">社團複製紀錄</div>
                            <div id="step1">1.請選擇學年期</div>
                            <div id="step2">2.請選擇社團</div>
                        </div>
                        <div class="bar"></div>
                        <form id="ClubCopy_form" action="ClubCopy" method="post">
                        <div class="pagecontant">
                            <!-- 首頁 -->
                            <div class="page0">
                                <div class="btngroup">
                                    <button type="button" id="startcopy" class="savebtn"><span>開始社團複製作業</span></button>
                                </div>
                                <fieldset>
                                    <legend>說明</legend>
                                    <ol>
                                        <li>模組管理者可以在學年學期間複製社團資料，以簡化新學期社團管理作業。</li>
                                        <li>社團資料複製可選擇社團資料複製、社員資料複製及教師資料複製。</li>
                                        <li>社員資料複製可將社員資料一併帶入下學期，可省去學生選社作業。</li>
                                    </ol>
                                </fieldset>
                                <div class="tab radiusborder" style="visibility: hidden;">複製紀錄</div>
                                <div class="resulttable fixheight" style="visibility: hidden;">
                                    <table>
                                        <thead class="showallborder">
                                            <tr>
                                                <th>項次</th>
                                                <th>作業項目</th>
                                                <th>作業人員</th>
                                            </tr>
                                        </thead>
                                        <tbody class="showallborder">
                                            <tr>
                                                <td>1</td>
                                                <td>97學年上學期 社團資料複製到 97學年下學期 共複製51筆社團</td>
                                                <td>測試者<br>98.02.12-08:31</td>
                                            </tr>
                                            <tr>
                                                <td>2</td>
                                                <td>98學年上學期 社團資料複製到 98學年下學期 共複製51筆社團</td>
                                                <td>測試者<br>98.02.12-08:31</td>
                                            </tr>
                                            <tr>
                                                <td>3</td>
                                                <td>100學年上學期 社團資料複製到 100學年下學期 共複製100筆社團</td>
                                                <td>測試者<br>107.12.31-08:31</td>
                                            </tr><tr>
                                                <td>4</td>
                                                <td>100學年上學期 社團資料複製到 100學年下學期 共複製100筆社團</td>
                                                <td>測試者<br>107.12.31-08:31</td>
                                            </tr><tr>
                                                <td>5</td>
                                                <td>100學年上學期 社團資料複製到 100學年下學期 共複製100筆社團</td>
                                                <td>測試者<br>107.12.31-08:31</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- 第二頁 -->
                            <div class="page1">
                                <div class="inlinedisplay">
                                    <div id="becpoied" class="hortable yellowtheme autosize">
                                        <div class="tab fullsize yellowtheme">將社團</div>
                                        <table>
                                            <tr>
                                                <td>學年：</td>
                                                <td>
                                                    <select id="from_year" name="from_year"></select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>學期：</td>
                                                <td>
                                                    <select id="from_sem" name="from_sem"></select>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="icon">
                                        <!-- <i class="fa fa-file-text-o" style="font-size:36px;"></i> -->
                                        <span>複製到</span>
                                        <!-- <i class="fa fa-copy" style="font-size:36px;"></i> -->
                                        <br>
                                        <i class="fa fa-long-arrow-right" style="font-size:60px"></i><br>
                                    </div>
                                    <div id="copyto" class="hortable yellowtheme autosize">
                                        <div class="tab fullsize yellowtheme">儲存社團</div>
                                        <table>
                                            <tr>
                                                <td>學年：</td>
                                                <td>
                                                    <select id="to_year" name="to_year"></select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>學期：</td>
                                                <td>
                                                    <select id="to_sem" name="to_sem"></select>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <div class="listtable inlinedisplay yellowtheme">
                                    <div class="tab fullsize yellowtheme">複製選項</div>
                                    <ul>
                                        <li> <label><input type="checkbox" checked disabled> 社團基本資料</label></li>
                                        <li class="hoverbar"><label><input type="checkbox" name="member" value="true"> 社團成員</label></li>
                                        <!--成員說明 -->
                                        <li class="hoverbar"><label><input type="checkbox" name="teaching" value="true"> 社團教師</label></li>
                                        <li class="hoverbar"><label><input type="checkbox" name="time" value="true"> 社團時間</label></li>
                                    </ul>
                                    <!--複製說明 -->
                                </div>
                                <div class="btngroup">
                                    <input id="back1" type="button" class="primarybtn" value="上一步">
                                    <input id="nextpage" type="button" class="primarybtn" value="下一步">
                                </div>
                            </div>
                            <!-- 第三頁 -->
                            <div class="page2">
                                <div class="infobox">
                                    <strong id="from">107學年上學期</strong>
                                    複製到
                                    <strong id="to">107學年下學期</strong>
                                    | 已選擇<strong class="selectedclub">0</strong>個社團
                                </div>
                                
                                <div class="tab radiusborder">社團一覽</div>
                                <div id="selectclub" class="resulttable fixheight checkedable">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th><input name="ckall" type="checkbox"></th>
                                                <th>類別</th>
                                                <th>社團名稱</th>
                                                <th>社團人數</th>
                                            </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                                <div class="btngroup">
                                    <input id="back2" type="button" class="primarybtn" value="上一步">
                                    <input id="confirmcopy" type="submit" class="savebtn" value="確認複製">
                                </div>
                                <div class="fullscreensping">
                                    <!-- <img src="img/loading.png" alt="loading..." class="spin"> -->
                                    <i class="fa fa-refresh spin"></i>
                                </div>
                            </div>
                        </div>
                        </form>
                    </div>
                </div><%-- box2 END --%>
            </div>
        </div>
    </div>
</body>
</html>