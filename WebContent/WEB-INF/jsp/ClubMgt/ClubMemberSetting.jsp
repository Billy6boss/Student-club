<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="css/clubstyle.css" /><%-- Club use css --%>
    <title>社員設定</title>
    <jsp:include page="/WEB-INF/jsp/include.jsp" />
    <script>
    	$(function() {
    	   var addButtons = $('button.savebtn'),
    	       removeButtons = $('button.cancelbtn'),
    	   	   isFirstTime = '${isFirstTime}',
    	   	   updateResult = '${updateResult}';
    	       
    	   function add(event) {
    	       var td = $(this).parent('td');
    	       var temp = td.find('select[id^=cadre]');
    	       var current = td.find('select[name^=cadre]');
    	       if (current.length < 3) {
    	           temp.clone(true).show().attr('name', temp.attr('id')).removeAttr('id').appendTo(td);
    	       }
    	   }   
    	   function remove(event) {
    	       $(this).siblings('select[name^=cadre]:last-child').remove();
    	   }
    	   
    	   addButtons.click(add);
    	   removeButtons.click(remove);
    	   
    	   if (!isFirstTime) {
    	       if ('true' === updateResult) {
    	           alert('修改成功');
    	       } else {
    	           alert('修改失敗，請重新操作');
    	       }
    	   }
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
                <div class="clubbanner">
                    <div class="bgimg" style="background: url('${pageContext.request.contextPath}/ClubImage') no-repeat center;"></div>
                    <div class="semtime">${sbj_year} 學年 / <c:choose>
                        <c:when test="${sbj_sem == '1'.charAt(0)}">上學期</c:when>
                        <c:when test="${sbj_sem == '2'.charAt(0)}">下學期</c:when>
                        <c:when test="${sbj_sem == '3'.charAt(0)}">暑假</c:when>
                        <c:when test="${sbj_sem == '4'.charAt(0)}">寒假</c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose></div>
                    <div class="clubname">社員設定</div>
                    <div class="clubcat">${club.clubCategory.cat_name}</div>
                </div>
                <!-- <div class="tablink">社員設定</div> -->
                <div class="resulttable">
                    <form action="ClubMemberSetting" method="post">
                        <table>
                            <thead>
                                <tr>
                                    <th>職稱(最多三個)</th>
                                    <th>班級</th>
                                    <th>座號</th>
                                    <th>姓名</th>
                                    <th>性別</th>
                                    <th>成績</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${not empty members}">
                                    <c:forEach var="clubMember" items="${members}">
                                        <tr>
                                            <td style="max-width: 140px;text-align: left;">
                                                <button class="savebtn mini" type="button"><i class="fa fa-plus"></i></button>
                                                <button class="cancelbtn mini" type="button"><i class="fa fa-minus"></i></button>
                                                <select id="cadre_${clubMember.rgno}" style="display:none">
                                                    <c:forEach var="cadre" items="${cadres}">
                                                        <option value="${cadre.cadre_num}">${cadre.cadre_name}</option>
                                                    </c:forEach>
                                                </select>
                                                <c:if test="${not empty clubMember.clubMemberCadre}">
                                                    <c:forEach var="memberCadre" items="${clubMember.clubMemberCadre}">
                                                        <select name="cadre_${clubMember.rgno}">
                                                            <c:forEach var="cadre" items="${cadres}">
                                                                <option value="${cadre.cadre_num}" ${memberCadre.cadre_num == cadre.cadre_num ? 'selected' : ''}>${cadre.cadre_name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </c:forEach>
                                                </c:if>
                                            </td>
                                            <td>${clubMember.stuRegister.stuClass.cls_cname}</td>
                                            <td>${clubMember.stuRegister.cls_no}</td>
                                            <td>${clubMember.stuRegister.stuBasis.cname}</td>
                                            <td>
                                                <strong>
                                                    <c:choose>
                                                        <c:when test="${clubMember.stuRegister.stuBasis.sex == 2}"><i style="font-size:20px;color: red;" class="fa"> &#xf221;</i></c:when>
                                                        <c:otherwise> <i style="font-size:20px;color: blue;" class="fa"> &#xf222;</i></c:otherwise>
                                                    </c:choose>
                                               </strong>
                                            </td>
                                            <td><input type="number" name="score_${clubMember.rgno}" value="${clubMember.club_score}" max="100" min="0"></td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                        </table>
                        <div class="btngroup"><input type="submit" class="savebtn" value="更新資料"></div>
                    </form>
                </div>
        </div><%-- box2 END --%>
    </div>
</div>
</div>
</body>
</html>