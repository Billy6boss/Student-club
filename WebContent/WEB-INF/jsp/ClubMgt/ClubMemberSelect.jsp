<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="css/clubstyle.css" /><%-- Club use css --%>
    <title>社團成員</title>
    <jsp:include page="/WEB-INF/jsp/include.jsp" />
    <script type="text/javascript">
        function toggle(source) {
            checkboxes = document.getElementsByName('rgno');
            for(var i=0, n=checkboxes.length;i<n;i++) {
                checkboxes[i].checked = source.checked;
            }
        }
    </script>
    <style>
    @import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);

    body {
        background: url("/StuClub/images/lined_paper.png") repeat;
        font-family: "cwTeXYen", sans-serif;
        line-height: 1;
        font-size: 20px;
    }

    #menberselect{
        width: 100%;
        display: flex;
        margin:10px auto;
    }
    #menberselect .tab{
        border-radius: 15px 0 0 15px;
        border: none;
        margin-bottom: 10px;
        margin-right:0px;
        outline: none;
        padding: 0px;
        text-align: center;
        width: 60px;
        writing-mode: tb-rl;
    }
    #menberresult{
        position: relative;
        padding: 3px 3px;
        
    }
    #menberresult tbody tr{
    	cursor: pointer;
    }
    #addstudent{
        position: absolute;
        bottom: 5px;
        right: 24px;
        float: right;
        opacity: 0.3;
    }
    #addstudent:hover{
        opacity: 1;
    }
    .checked{
    	background-color: #7faeffc7;
    }
</style>
<script>
	$(function() {
	    var grade = $('select[name=grade]'),
	        cls = $('select[name=class]'),
	        studentsTbody = $('#students tbody'),
	        membersTbody = $('#members tbody'),
	        cls_num = $('input[name=cls_no]');
	    
	    function getClass(event) {
	        $.ajax({
	            url : 'ClubMemberSelect',
	            type : 'post',
	            data : {
	                'grade' : grade.val(),
	                'op' : 'getClass'
	            },
	            dataType : 'json',
	            error : function(data) {
	                alert('查詢班級時發生ajax error：' + data);
	            },
	            success : function(data) {
	                if (data.error) {
	                	alert(data.error);
    	            } else {
    	                cls.empty();
    	                if (data.length > 0) {
    	                    for (var i = 0, length = data.length; i < length; i++) {
    	                        $('<option value="' + data[i].cls_code+ '">' + data[i].cls_cname + '</option>').appendTo(cls);
    	                    }
    	                }
    	            }
    	        }
    	    });
	    }

	    function getStuByClass(event) {
	        $.ajax({
	            url : 'ClubMemberSelect',
	            type : 'post',
	            data : {
	                'class' : cls.val(),
	                'op' : 'getStuByClass'
	            },
	            dataType : 'json',
	            error : function(data) {
	                alert('依班級查詢學生時發生ajax error：' + data);
	            },
	            success : function(data) {
	                if (data.error) {
	                    alert(data.error);
	                } else {
	                    studentsTbody.empty();
	                    if (data.length > 0) {
	                        for (var i = 0, length = data.length; i < length; i++) {
	                            $('<tr><td><input type="checkbox" name="rgno" value="' + data[i].rgno + '"></td><td>' + data[i].stuBasis.reg_no +'</td><td>' + data[i].stuBasis.cname + '</td></tr>').appendTo(studentsTbody);
	                        }
	                    }
	                }
	            }
	        });
	    }

	    grade.change(getClass);
	    cls.change(getStuByClass);
	    
	    $('input#addstudent').click(function(event) {
	        $.ajax({
	            url : 'ClubMemberSelect',
	            type : 'post',
	            data : $('#students').parents('form').serialize(),
	            dataType : 'json',
	            error : function(data) {
	                alert('新增社員時發生ajax error：' + data);
	            },
	            success : function(data) {
	                alert(data.result);
	                if (data.result === '新增成功') {
	                    location.replace('/StuClub/ClubMemberSelect');
	                }
	            }
	        });
	    });    
	    
	    $('input#cls_no').click(function(event) {
	        $.ajax({
	            url : 'ClubMemberSelect',
	            type : 'post',
	            data : {
	                'cls_num' : cls_num.val(),
	                'op' : 'getStuByClsNo'
	            },
	            dataType : 'json',
	            error : function(data) {
	                alert('依班級座號查詢時發生ajax error：' + data);
	            },
	            success : function(data) {
	                if (data.error) {
	                    alert(data.error);
	                } else {
	                    studentsTbody.empty();
	                    $('<tr><td><input type="checkbox" name="rgno" value="' + data.rgno + '"></td><td>' + data.stuBasis.reg_no +'</td><td>' + data.stuBasis.cname + '</td></tr>').appendTo(studentsTbody);
	                }
	            }
	        });

	    });
	    $('input#reg_no').click(function(event) {
	        $.ajax({
	            url : 'ClubMemberSelect',
	            type : 'post',
	            data : {
	                'reg_no' : $('input[name=reg_no]').val(),
	                'op' : 'getStuByRegNo'
	            },
	            dataType : 'json',
	            error : function(data) {
	                alert('依學號查詢時發生ajax error：' + data);
	            },
	            success : function(data) {
	                if (data.error) {
	                    alert(data.error);
	                } else {
	                    studentsTbody.empty();
	                    $('<tr><td><input type="checkbox" name="rgno" value="' + data.rgno + '"></td><td>' + data.stuBasis.reg_no +'</td><td>' + data.stuBasis.cname + '</td></tr>').appendTo(studentsTbody);
	                }
	            }
	        });   
	    });

	    membersTbody.on('click', 'i[class*=rgno]', function(event) {
	        if (confirm('確認刪除')) {
	            var target = $(this).parents('td');
	            $.ajax({
	                url : 'ClubMemberSelect',
	                type : 'post',
	                data : {
	                    'rgno' : target.find('input[type=hidden]').val(),
	                    'op' : 'delete'
	                },
	                dataType : 'json',
	                error : function(data) {
	                    alert('刪除時發生ajax error：' + data);
	                },
	                success : function(data) {
	                    alert(data.result);
	                    if (data.result === '刪除成功') {
	                        target.parents('tr').remove();
	                    }
	                }
	            }); 
	        }
	    });

	    studentsTbody.on('click', 'tr', function(event){
            var thischeckbox = $(this).find("input");
            if (event.target !== thischeckbox.get(0)) {
                thischeckbox.prop('checked',!thischeckbox.prop("checked"));
            }
            $(this).toggleClass("checked");
        });
	    
	    $('#checkAll').click(function(event) {
	        var checkboxAll = $(this).prop('checked'),
	            resultTr = studentsTbody.find('tr'),
	            checkboxesinput = resultTr.find('input');
	            
	        checkboxesinput.prop('checked', checkboxAll);
	        resultTr.removeClass("checked");
	        if (checkboxAll) {
	            resultTr.addClass("checked");
	        }
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
                    <div class="clubbanner">
                        <div class="bgimg" style="background: url('${pageContext.request.contextPath}/ClubImage') no-repeat center;"></div>
                        <div class="semtime">${sbj_year} 學年 / <c:choose>
                        <c:when test="${sbj_sem == '1'.charAt(0)}">上學期</c:when>
                        <c:when test="${sbj_sem == '2'.charAt(0)}">下學期</c:when>
                        <c:when test="${sbj_sem == '3'.charAt(0)}">暑假</c:when>
                        <c:when test="${sbj_sem == '4'.charAt(0)}">寒假</c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose></div>
                        <div class="clubname">社團成員</div>
                        <div class="clubcat">${club.clubCategory.cat_name}</div>
                    </div>

                    <div id="menberselect">
                        <div class="tab bluetheme">選社團成員</div>
                        <div class="resulttable bluetheme">
                            <fieldset>
                                <legend>學號/班級座號查詢</legend>
                                學號：<input type="text" name="reg_no" style="width: 100px">
                                <input type="button" class="savebtn mini" id="reg_no" value="搜尋">
                                <br>
                                班級座號：<input type="text" name="cls_no" style="width: 100px">
                                <input type="button" class="savebtn mini" id="cls_no" value="搜尋">
                                <br>
                                (範例：36班01號 <i style="font-size:18px" class="fa">&#xf061;</i> 03601)
                            </fieldset>
                            <fieldset>
                                <legend>年級班級查詢</legend>
                                年級<select name="grade">
                                    <option value="1">一年級</option>
                                    <option value="2">二年級</option>
                                    <option value="3">三年級</option>
                                </select>
                                班級<select name="class">
                                    <c:if test="${not empty classes}">
                                    <c:forEach var="cls" items="${classes}">
                                    <option value="${cls.cls_code}">${cls.cls_cname}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </fieldset>
                </div>
                <div id="menberresult" class="resulttable bluetheme checkedable">
                    <div class="scrollable">
                        <form>
                            <table id ="students">
                                <thead>
                                    <tr>
                                        <th><input type="checkbox" id="checkAll"></th>
                                        <th>學號</th>
                                        <th>姓名</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${not empty students}">
                                    <c:forEach var="stu" items="${students}">
                                    <tr>
                                        <td><input type="checkbox" name="rgno" value="${stu.stuBasis.rgno}"></td>
                                        <td>${stu.stuBasis.reg_no}</td>
                                        <td>${stu.stuBasis.cname}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                    <input name="op" type="hidden" value="insert">
                </form>
            </div>
            <input type="button" class="savebtn" id="addstudent" value="新增">
        </div>
    </div>

    <div class="tab radiusborder">已選擇社團成員</div>
    <div class="resulttable">
        <table id="members">
            <thead>
                <tr>
                    <th>班級</th>
                    <th>座號</th>
                    <th>姓名</th>
                    <th>移除</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty members}">
                    <c:forEach var="clubMember" items="${members}">
                        <tr>
                            <td>${clubMember.stuRegister.stuClass.cls_cname}</td>
                            <td>${clubMember.stuRegister.cls_no}</td>
                            <td>${clubMember.stuRegister.stuBasis.cname}</td>
                            <td>
                                <button class="cancelbtn mini">
                                    <i class="fa fa-times rgno"></i>
                                </button>
                                <input type="hidden" value="${clubMember.stuRegister.rgno}" />
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>
<br class="clear">
</div>
<br class="clear">
</div>
</div>
</div>
</body>
</html>