<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>社團管理</title>
    <jsp:include page="/WEB-INF/jsp/include.jsp" />
    <link type="text/css" rel="stylesheet" href="css/clubstyle.css"   />
    <style>
    @import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);

    /*社團自製樣式設定357 */
    body {
        background: url("/StuClub/images/lined_paper.png") repeat;
        font-family: "cwTeXYen", sans-serif !important;
        line-height: 1;
        font-size: 18px;
    }
    .errormsg{
        position: fixed;
        margin: 5px 0px 5% 5%;
        color: white;
        font-weight: bold;
        width: 90%;
        z-index: 5;
        text-align: center;
        font-size: 20px;
        display: inline-grid;
    }
    .errormsg span {
        background-color: #FF0000B6;
        padding: 10px;
        margin-bottom: 1px;
        cursor: pointer;
        transition: all 0.25s;
    }
    .errormsg span:hover{
        background-color: #FF00004A;
    }
    .errormsg .fa-close{
        float: right;
    }
    .errormsg .fa-warning{
        margin-right: 5px;
    }
    /*導師選擇樣式*/
    #app {
        height: auto;
        border-radius: 4px;
        box-shadow: 0 40px 50px rgba(0, 0, 0, 0.25);
        outline: none;
        font-size: 1em;
    }
    #app input {
        outline: none;
        width:36%;
        border: 0;
        float: left;
        padding: 5px;
    }
    .positionuse{
        position: relative;
        width:100%;
    }
    .suggestbox{
        position: absolute;
        background-color: white;
        top:36px;
        width: 36%;
        max-height: 196px;
        overflow: auto;
        display: none;
    }
    #main .suggestbox li{
        border-top: none;
        border-bottom: 1px solid lightgrey;
        padding-left: 10px;

    }
    .suggestbox li:hover{
        background-color:#118AFFFF; 
    }
    .suggestbox ul {
        margin-bottom: 3px;
    }
    .suggestbox-select{
        background-color:#118AFFFF; 
    }
    .teachertag {
        border-radius: 3px;
        background: #b5aff9;
        float: left;
        margin: 3px;
        padding: 4px;
        font-size: 1em;
        /*vertical-align: middle;*/
        box-shadow: 0px 1px 4px #c6c6c6, 0px 2px 17px #d1d1d1;
    }
    .teachertag a {
        color: #000;
        padding-right: 5px;
        padding-left: 5px;
        padding-top: 5px;
        padding-bottom: 5px;
        margin-right: 5px;
    }
    .teachertag span {
        padding-right: 5px;
        padding-left: 0px;
        padding-top: 5px;
        padding-bottom: 5px;
    }
</style>
<script src="js/clubsetting.js"></script>
<script>
    $(function() {
        var clubtime = {},
        result = '${result}';
        
        $("#addtime").click(function (e) {
            setClubTime($("#date").val(), $("#session").val());
        });

        $("#timereset").click(function (e) {
            if (window.confirm("是否重新選擇時間?")) {
                clubtime = {};
            }
            updateClubtime();
        });
        
        <c:if test="${not empty clubTime}">
        <c:forEach var="time" items="${clubTime}">
        setClubTime('${time.sday}', '${time.speriod}');
        </c:forEach>
        </c:if>
        
        <c:if test="${clubs != null}">
        var insertCheckbox = $('#insert input[type=checkbox]'),
        insertSelect = $('#insert select');
        insertCheckbox.click(function(e) {
            if ($(this).prop('checked')) {
                insertSelect.show();
            } else {
                insertSelect.hide();
            }
        });
        insertSelect.change(function(e) {
            $.ajax({
                url : 'ClubSettings',
                type : 'get',
                data : {
                    'club_num' : insertSelect.val()
                },
                error : function(data) {
                    console.log(data);
                    alert('查詢社團時發生ajax error：' + data);
                },
                success : function(data) {
                    if (data) {
                        alert(data);
                    } else {
                        location.replace(location.href);
                    }
                }
            });
        });
        </c:if>
        
        function setClubTime(sday, speriod) {
            var day = clubtime[sday];
            if (!day) {
                day = clubtime[sday] = {};
            }
            day[speriod] = true;
            updateClubtime();
        }

        function updateClubtime() {
            var text ="",
            print = false,
            result = $("#clubtimeresult"),
            speriod = $('<input type="hidden" name="speriod">');
            result.html("");
            for (var day in clubtime) {
                text = "星期"+ $("#date option[value=" + day + "]").text()+"：";
                for (var session in clubtime[day]) {
                    if (clubtime[day][session]) {
                        text += $('#session option[value=' + session + ']').text() + " ";
                        speriod.clone().val(day + '_' + session).appendTo(result);
                    }
                }
                result.html(result.html() + text + "<br />");
            }
            if (!result.html()) result.html('無');
        }
        
        function handleFile(event) {
            var file = this.files[0], 
                target = $('div.bgimg'), 
                reader = new FileReader();

            function onloadFile(event) {
                target.attr('style','background: url(' + event.target.result + ') no-repeat center');
            }

            reader.onload = onloadFile;
            reader.readAsDataURL(file);
        }
        
        $('input[type=file]').change(handleFile);
        
        if (result) {
            if (result == 'true') {
                alert('修改成功');
            } else {
                alert('新增成功，將跳轉回社團首頁');
                location.replace('${pageContext.request.contextPath}/ClubMgt');
            }
        } else {
            var teacherTags = $('div#tagHere div.teachertag');
            for (var i = 0, l = teacherTags.length; i < l; i++) {
                var tag = teacherTags.eq(i),
                    span = tag.find('span');
                if (!span.text()) {
                    span.text($('ul#sugs > li > input[value="' + tag.find('input[name=staff]').val()+ '"]').parent('li').text());
                }
            }
        }
    });
</script>
</head>
<body>
    <c:if test="${not empty error_msg}">
    <div class="errormsg">
        <c:forEach items="${error_msg}" var="msg">
        <span><i class="fa fa-warning"></i>${msg.value}<i
            class="fa fa-close"></i></span>
        </c:forEach>
    </div>
</c:if>

<div id="outer">
    <jsp:include page="/WEB-INF/jsp/includeHead.jsp" />
    <div id="main">
        <div align="left">
            <jsp:include page="includeMenu.jsp" />
        </div>
        <div id="content">
            <div id="box2">
                <%--網頁內容寫在這 --%>
                <c:if test="${clubs != null}">
                <p id="insert">
                    <input type="checkbox">從現有社團中選擇
                    <select style="display:none">
                        <option>請選擇</option>
                        <c:forEach var="club" items="${clubs}">
                        <option value="${club.club_num}">${club.club_name}</option>
                    </c:forEach>
                </select>
            </p>
        </c:if>
        <form action="ClubSettings" method="post" enctype="multipart/form-data" id="clubsettings">
            <div class="clubbanner">
                <c:if test="${club != null}">
                <div class="bgimg" style="background: url('${pageContext.request.contextPath}/ClubImage') no-repeat center;"></div>
            </c:if>
            <div class="semtime">
                <select disabled>
                    <option>${sbj_year}年</option>
                </select> / 
                <select disabled>
                    <option>
                        <c:choose>
                        <c:when test="${sbj_sem == '1'.charAt(0)}">上學期</c:when>
                        <c:when test="${sbj_sem == '2'.charAt(0)}">下學期</c:when>
                        <c:when test="${sbj_sem == '3'.charAt(0)}">暑假</c:when>
                        <c:when test="${sbj_sem == '4'.charAt(0)}">寒假</c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </option>
            </select>
        </div>
        <div class="clubname">
            <input type="text" name="club_name" placeholder="輸入社團名稱"
            value="${clubSem.club.club_name}" required />
        </div>
        <div class="clubcat">
            <select name="cat_num" required>
                <c:forEach items="${categories}" var="cate">
                <option value="${cate.cat_num}"
                ${clubSem.club.cat_num == cate.cat_num ? 'selected' : ''}>${cate.cat_name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label class="filebtn"><input
            type="file" style="display: none;"
            name="img" accept="image/*" />更新圖片</label>
        </div>
    </div>
    <div class="hortable">
        <div class="tab linear">社團簡介</div>
        <table>
            <tr>
                <td colspan="4" class="info">
                    <textarea id="club_info" name="club_info" maxlength="100" placeholder="輸入社團簡介...">${clubSem.club.club_info}</textarea>
                    <div id="wordlimet" class="suggestingtight"></div>
                </td>
            </tr>
            <tr>
                <td>社團教師：</td>
                <td colspan="3">
                    <div id="app">
                        <div class="positionuse">
                            <input type="text" id="staff_cname" placeholder="按Enter以完成輸入" autocomplete="off"/>
                            <input type="hidden" id="staff_code"/>
                            <div class="suggestbox" id="suggestbox">
                                <ul class="sugs" id="sugs">
                                    <c:if test="${not empty teachers}">
                                    <c:forEach var="staff" items="${teachers}">
                                    <li>${staff.staff_cname}<input type="hidden" value="${staff.staff_code}" /></li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <div class="tagHere" id="tagHere">
                    <c:forEach var="item" items="${teaching}">
                    <div class="teachertag">
                        <input type="hidden" name="staff" value="${item.staff_code}">
                        <a href="#"><i class="fa fa-times" aria-hidden="true"></i></a>
                        <span>${item.staff.staff_cname}</span>
                    </div>
                </c:forEach>
            </div>
        </div>
    </td>
</tr>
<tr>
    <td>社團地點：</td>
    <td colspan="3">
        <input type="text" name="club_room" value="${clubSem.club_room}" placeholder="輸入社團地點" />
    </td>
</tr>
<tr>
    <td>社團時間：</td>
    <td id="clubtime">
        <span>星期</span>
        <select id="date">
            <option value="0">一</option>
            <option value="1">二</option>
            <option value="2">三</option>
            <option value="3">四</option>
            <option value="4">五</option>
            <option value="5">六</option>
            <option value="6">日</option>
        </select>
        <select id="session">
            <c:forEach var="speriodMark" items="${speriodMarks}">
            <option value="${speriodMark.speriod}"><c:if test="${speriodMark.adg_code == 'N'.charAt(0)}">夜</c:if>${speriodMark.sprd_name}</option>
        </c:forEach>
    </select>
    <button id="addtime" class="savebtn icon mini" type="button">
        <i class="fa fa-plus"></i>
    </button><br>
    <span id="clubtimeresult"></span>
    <button id="timereset" class="cancelbtn mini" type="button">重設</button>
</td>
<td>社團編號：</td>
<td><input type="text" name="club_code" value="${clubSem.club.club_code}" placeholder="輸入社團編號"/></td>
</tr>
<tr>
    <td>社團連結網址：</td>
    <td colspan="3"><input class="fullsize" type="url" name="url" value="${clubSem.club.url}" placeholder="輸入社團相關網址"></td>
</tr>
</table>
<div class="tab linear">社團限制</div>
<table class="clubinfo">
    <tr>
        <td>適用年級：</td>
        <td colspan="3">
            <label>
                <input type="checkbox" name="grade_one" value="true" ${clubSem.grade_one ? 'checked' : ''}/>一年級
            </label> 
            <label>
                <input type="checkbox" name="grade_two" value="true" ${clubSem.grade_two ? 'checked' : ''}/>二年級
            </label> 
            <label>
                <input type="checkbox" name="grade_three" value="true" ${clubSem.grade_three ? 'checked' : ''}/>三年級
            </label>
        </td>
    </tr>
    <tr>
        <td>性別取向：</td>
        <td colspan="3">
            <input type="radio" name="sex" value="2" ${clubSem.club.sex == 2 ? 'checked' : ''} />女
            <input type="radio" name="sex" value="1" ${clubSem.club.sex == 1 ? 'checked' : ''} />男
            <input type="radio" name="sex" value="0" ${clubSem.club.sex != 1 && clubSem.club.sex != 2 ? 'checked' : ''} />無
        </td>
    </tr>
    <tr>
        <td>開放線上選社：</td>
        <td colspan="3">
            <input type="radio" name="selectable" value="false" ${!clubSem.selectable ? 'checked' : ''}/>否
            <input type="radio" name="selectable" value="true" ${clubSem.selectable ? 'checked' : ''}/>是
        </td>
    </tr>
</table>
</div>
<div class="btngroup">
    <input type="submit" class="savebtn" value="確認更新" /> 
    <input type="button" class="cancelbtn" onclick="refill()" value="重新填寫" />
    <c:if test="${updateClub != null}">
    <input type="hidden" name="updateClub" value="${updateClub}" />
</c:if>
</div>
</form>
</div>
<!-- end id=box2 -->
<br class="clear" />
</div>
<!-- end id=content -->
<br class="clear" />
</div>
<!-- end id=main -->
</div>
</body>
</html>
