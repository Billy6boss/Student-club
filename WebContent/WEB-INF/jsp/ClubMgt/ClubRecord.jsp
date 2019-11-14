<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
   	<link type="text/css" rel="stylesheet" href="css/clubstyle.css" /><%-- Club use css --%>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>社團紀錄</title>
    <jsp:include page="/WEB-INF/jsp/include.jsp" />
    <style>
    	@import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);

		body{ 
    		background: url("/StuClub/images/lined_paper.png") repeat;
    		font-family: "cwTeXYen", sans-serif; 
   		 	line-height: 1; 
    		font-size: 20px;
		}
    </style>
<script>
    $(function() {
        var error_msg = '${error_msg}',
            edate = $('input[name=cr_edate]');
        function doDelete(event) {
            if (confirm('確認刪除?')) {
                var target = $(this).parents('tr');
                var cr_num = target.find('input[name=cr_num]').val();
                $.ajax({
                    url : 'ClubRecord',
                    type : 'post',
                    data : {
                        'cr_num' : cr_num,
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
        $('button.cancelbtn').click(doDelete);
        if (error_msg) alert(error_msg);
        
        //時間設定限制
        edate.prop('disabled', true);
        $("input[name='cr_sdate']").change(function () {
            var startdate = $(this).val();
            edate.val('');
            edate.prop('min', startdate);
            edate.prop('disabled', false);

        });
        
        $("select[name='level']").change(function(){
        	
        	
        	var reg_no = $("#reg_no").val();
        	var selectedVal = $(this).val();
        	
        	if(selectedVal === '3' && reg_no){
        		 $.ajax({
                     url : 'ClubRecord',
                     type : 'post',
                     data : {
                         'reg_no' : reg_no,
                         'op' : 'getStuCadres'
                     },
                     dataType : 'text',
                     error : function(data) {
                         alert('刪除時發生ajax error：' + data);
                     },
                     success : function(data) {
                         var cader_detail = data.split("：");
                         if (cader_detail[1]) {
                        	 $("#cr_cadre").val("" + cader_detail[1]);
                             $("#cr_cadre_label").text(cader_detail[0]+"："+cader_detail[1]);
                         	 console.log(cader_detail[1]);
                         } else{
                        	 alert(data);
                        	 $(this).val("");
                         }
                     }
                 });		
        	} else if (selectedVal ==='3'){
        		alert("請先輸入學號");
        		$(this).val("");
        		return;
        	} else {
        		$("#cr_cadre_label").text("");
        		$("#cr_cadre").val("");
        	}       
    });
    });
    
    
</script>
</head>
<body>
    <div id="outer">
        <jsp:include page="/WEB-INF/jsp/includeHead.jsp" />  
        <div id="main">       
            <div align="left">            <jsp:include page="includeMenu.jsp" />
            </div>      
            <div id="content">                                                  <div id="box2"> 
                <%--網頁內容寫在這 --%>
                <div class="clubbanner">
                    <c:if test="${not empty club.url}">
                    <div class="bgimg"
                    style="background: url('${pageContext.request.contextPath}/ClubImage') no-repeat center;"></div>
                </c:if>
                <div class="semtime"> ${sbj_year} 學年 / <c:choose>
                        <c:when test="${sbj_sem == '1'.charAt(0)}">上學期</c:when>
                        <c:when test="${sbj_sem == '2'.charAt(0)}">下學期</c:when>
                        <c:when test="${sbj_sem == '3'.charAt(0)}">暑假</c:when>
                        <c:when test="${sbj_sem == '4'.charAt(0)}">寒假</c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose></div>
                <div class="clubname">服務紀錄</div>
                <div class="clubcat">${club.clubCategory.cat_name}</div>
            </div>

            <div id="search" class="bluetheme">
            	<div class="hortable bluetheme">
				<form action="ClubRecord" method="post">
                <table>
                    <tr>
                        <td>姓名：</td>
                        <td><input id="CName" type="text" name="name" value="${param.name}"></td>
                        <td>學號：</td>
                        <td><input id="reg_no" type="number" name="reg_no"></td>
                    </tr>
                    <tr>
                        <td>服務日期：</td>
                        <td><input type="date" name="cr_sdate"> 至 <input type="date" name="cr_edate"></td>
                        <td>服務時數：</td>
                        <td><input id="hour" type="number" name="cr_hours"></td>
                    </tr>
                    <tr>
                        <td>服務事項：</td>
                        <td colspan="3">
                            <select name="level">
                                <option value="">其他服務事項</option>
                                <option value="3">社團幹部紀錄</option>
                            </select>
                            <label id="cr_cadre_label" for="cr_cadre"></label> 
                            <input id="cr_cadre" type="hidden" name="cr_cadre">     
                        </td>
                    </tr>
                    <tr>
                        <td>備註說明：</td>
                        <td colspan="3"><input type="text" name="cr_detail" value="${param.cr_detail}" class="fullsize"></td>
                    </tr>
                </table>          
                <div class="btngroup">
					<input type="submit" class="savebtn" value="新增">
					<input type="reset" class="primarybtn" value="重設">
                </div>
            </form>
            </div>
        </div>
        <div class="resulttable">
            <table>
                <thead>
                    <tr>
                        <th>學號</th>
                        <th>姓名</th>
                        <th>服務時間</th>
                        <th>服務事項</th>
                        <th>時數</th>
                        <th>學校認證</th>
                        <th>刪除</th>
                    </tr>
                </thead>
                <tbody class="showallborder">
                    <c:if test="${not empty records}">
                    <c:forEach var="clubRecord" items="${records}">
                    <tr>
                        <td>${clubRecord.stuRegister.stuBasis.reg_no}</td>
                        <td>${clubRecord.stuRegister.stuBasis.cname}</td>
                        <td>${clubRecord.cr_sdate}~<br>${clubRecord.cr_edate}</td>
                        
                        <td>
                            <c:if test="${not empty clubRecord.cr_cadre}">${clubRecord.cr_cadre}<br/></c:if>
                            ${clubRecord.cr_detail}
                            </td>
                        <td>${clubRecord.cr_hours}</td>
                        <td>${clubRecord.staff.staff_cname} ${clubRecord.cr_time}</td>
                        <td><button class="cancelbtn icon">
                            <i class="fa fa-trash"></i>
                        </button><input type="hidden" name="cr_num" value="${clubRecord.cr_num}"/>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </tbody>
</table>
</div>
</div>
<!-- end id=box2 -->                 
<br class="clear" />
</div><!-- end id=content -->
<br class="clear" />
</div>   <!-- end id=main -->    
</div>
</body>
</html>