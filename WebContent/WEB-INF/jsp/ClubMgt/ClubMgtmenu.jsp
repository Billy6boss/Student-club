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
<link rel="stylesheet" href="css/ClubMenustyle.css">
<script>
    $(function() {
        function setYearAndSem(event) {
            $.ajax({
                url : 'ClubMgt',
                type : 'post',
                data : {
                    'sbj_year' : $('select[name=sbj_year]').val(),
                    'sbj_sem' : $('select[name=sbj_sem]').val(),
                    'op' : 'set'
                },
                dataType : 'text',
                error : function(data) {
                    alert('設定學年學期時發生ajax error：' + data);
                },
                success : function(data) {
                	location.replace(location.href);
                }
            });
        }
        function doDelete(event) {
            if (confirm('確認刪除?')) {
                var target = $(this).parents('li');
                var club_num = target.find('input[name=club_num]').val();
                $.ajax({
                    url : 'ClubMgt',
                    type : 'post',
                    data : {
                        'club_num' : club_num,
                        'op' : 'delete'
                    },
                    dataType : 'text',
                    error : function(data) {
                        alert('刪除時發生ajax error：' + data);
                    },
                    success : function(data) {
                        alert(data);
                        if (data === '刪除成功') {
                            target.remove();
                        }
                    }
                });
            }
        }

        function doSubmit(event) {
            $(this).parents('li').find('form[action=ClubMgt]').submit();
        }
        
    	$('input#namesrc').keydown(function(e) {
    		if (e.keyCode === 13) {
    			var search = $('input#search');
        		search.attr('name', 'club_name')
				 		  .val($(this).val());
        		search.parents('form').submit();
    		}
    	});
    	$('input#clubnumber').keydown(function(e) {
    		if (e.keyCode === 13) {
    			var search = $('input#search');
        		search.attr('name', 'club_code')
				 		  .val($(this).val());
        		search.parents('form').submit();
    		}
    	});
    	$('select#clubclass').change(function(e) {
    			var search = $('input#search');
        		search.attr('name', 'cat_num')
				 		  .val($(this).val());
        		search.parents('form').submit();
    	});
    	
    	$('input#searchbtn').click(function() {
    			var search = $('input#search');
    			if(!$.isEmptyObject($('input#clubnumber').val())){
    				search.attr('name', 'club_code').val($('input#clubnumber').val());
    			} else if(!$.isEmptyObject($('input#namesrc').val())){
    				console.log($('input#namesrc').val());
    				search.attr('name', 'club_name').val($('input#namesrc').val());
    			}
    			search.parents('form').submit();
    	});

        $('input[name=delete]').click(doDelete);
        $('a.namelink').click(doSubmit);
        $('select[name^=sbj]').change(setYearAndSem);
        
        //說明文件顯示/隱藏動畫
        $("#arrow").click(function(){
            $("#manuallist").animate({width:'toggle'},350);
            $("#arrow").toggleClass("rotate");
        });
        
    });
</script>
</head>
<body>
    <div id="outer">
        <jsp:include page="/WEB-INF/jsp/includeHead.jsp" />
        <div id="main">
            <div align="left">
                <jsp:include page="includeMenu.jsp" />
            </div>
            <div id="content">
                <div id="box2">
                    <%--網頁內容寫在這 --%>
                    <p>
                        ${ud.sch_cname}${ud.staff_cname} 您好：
                    </p>
                    <div class="clubselection">
                        <fieldset>
                            <legend>社團查詢</legend>
                            <div class="timeselection">
                                                                           學年：<select name="sbj_year">
                                    <c:forEach var="year" items="${semesterMap}">
                                        <option value="${year.key}" ${sbj_year == year.key ? 'selected' : ''}>${year.key}</option>
                                    </c:forEach>
                                </select> 
                                                                          學期：<select name="sbj_sem">
                                    <c:forEach var="sem" items="${semesterMap.get(sbj_year)}">
                                        <option value="${sem}" ${sbj_sem == sem ? 'selected' : ''}>
                                            <c:choose>
                                                <c:when test="${sem == '1'.charAt(0)}">上</c:when>
                                                <c:when test="${sem == '2'.charAt(0)}">下</c:when>
                                                <c:when test="${sem == '3'.charAt(0)}">寒假</c:when>
                                                <c:when test="${sem == '4'.charAt(0)}">暑假</c:when>
                                            </c:choose>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="nameseletion">
                                <div id="clubclassselect">
                                    <select class="clubclass" id="clubclass">
                                        <option disabled selected>類別</option>
                                        <c:forEach var="category" items="${categoryMap}">
                                            <option value="${category.key}">${category.value}</option>
                                        </c:forEach>
                                    </select> 
                                    <form action="ClubMgt" method="get">
                                	   <input type="hidden" id="search" />
                                    </form>
                               	    <label for="clubclass" class="namesrc_lable">社團類別</label> 
                                </div>
                                <div id="clubnumberselect">
                                    <input class="clubnumber" id="clubnumber" type="text" placeholder="社團編號...">
                                    <label for="clubnumber" class="namesrc_lable">社團編號</label> 
                                </div>
                                <div id="clubnameselect">
                                    <input id="namesrc" type="text" placeholder="輸入社團名稱..."> 
                                    <label for="namesrc" class="namesrc_lable">社團名稱：</label>
                                </div>
                                <input type="button" id="searchbtn" class="primarybtn icon" value="搜尋"></button>
                            </div>
                        </fieldset>
                    </div>
                    <ul class="clubs">
                        <c:choose>
                            <c:when test="${empty clubList}">
                                <tr>
                                    <td></td>
                                    <td colspan="2">查無資料</td>
                                    <td></td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="clubSem" items="${clubList}">
                                    <li class="club1">
                                        <div class="left">
                                            <div class="number">${clubSem.club.club_code}</div>
                                            <div>
                                                <p class="year">
                                                    <b>${clubSem.sbj_year}年</b>
                                                    / <b> <c:choose>
                                                            <c:when test="${sbj_sem == '1'.charAt(0)}">上學期</c:when>
                                                            <c:when test="${sbj_sem == '2'.charAt(0)}">下學期</c:when>
                                                            <c:when test="${sbj_sem == '3'.charAt(0)}">寒假</c:when>
                                                            <c:when test="${sbj_sem == '4'.charAt(0)}">暑假</c:when>
                                                            <c:otherwise></c:otherwise>
                                                        </c:choose>
                                                    </b>
                                                </p>
                                            </div>
                                            <div class="edit">
                                                <form action="ClubMgt" method="post" enctype="application/x-www-form-urlencoded">
                                                    <input type="submit" name="edit" value="設定" /> 
                                                    <input type="hidden" name="club_num" value="${clubSem.club.club_num}" />
                                                    <input type="hidden" name="op" value="edit" />
                                                </form>
                                            </div>
                                            <div class="dele">
                                                <input type="button" name="delete" value="刪除">
                                            </div>
                                        </div>
                                        <div class="right">
                                            <div class="clubname">
                                                <div class="classtab">${categoryMap.get(clubSem.club.cat_num)}</div>
                                                <a href="#" class="namelink">${clubSem.club.club_name}</a>
                                            </div>
                                            <div class="clubinfo">
                                                <span>${clubSem.club.club_info}</span>
                                            </div>
                                            <div class="clublink">
                                                                                                                          社團連結：<a href="${clubSem.club.url}">${clubSem.club.url}</a>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                    <div id="manual" class="fixarae">
                        <button id="arrow"> > </button>
                        <div id="manuallist">
                            <div id="manual_1">
                                <a href="documents/Operation_StuClub.pdf" target="_blank" style="float:right;"><img src="images/pdficon.png" alt="">操作手冊</a>
                            </div>
                        </div>
                    </div>
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