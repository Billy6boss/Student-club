<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="css/clubstyle.css" /> <!-- Club use css -->
<title>社團批次匯入</title>
<jsp:include page="/WEB-INF/jsp/include.jsp" />
<style>
@import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);
body {
	background: url("/StuClub/images/lined_paper.png") repeat;
	font-family: "cwTeXYen", sans-serif;
	font-size: 21px;
	line-height: 1;
}
.clubImport {
	line-height: 1.5;
	font-size: 21px;
	margin-bottom: 25px;
	padding: 0px 10px;
}
.title {
	background: #3fbf9a;
	font-weight: bold;
	text-align: left;
	margin: 0;
	padding: 5px 12px;
}
.contentBox {
	border: 2px #3fbf9a solid;
	padding: 20px;
}
.dashedLine {
	border-top: 2px dashed #3fbf9a;
	margin: 20px 0;
	overflow: hidden;
}
.clubImport select {
    border-radius: .28571rem;
    font-size: 15px;
    padding: 3px;
}
.clubImport input[type="file"] {
    font-size: 15px;
    margin: 5px 0;
}
.clubImport input[type="submit"] {
    background: #8BB96EFF;
    color: white;
    font-size: 18px;
    font-weight: 700;
    line-height: 1.2142em;
    border-radius: .28571rem;
    border-width: 1px;
    border-style: solid;
    margin: 5px 0;
    padding: 5px 15px;
}
li a {
 	text-decoration: none;
}
</style>
<script>
    $(function() {
        var semesterMap = JSON.parse('${semesterMap}'),
        sbjYear = $('select[name=sbjYear]');
    
        function setYear(e) {
            if (!e) return;
            
            var target = $(this).next('select[name=sbjSem]'),
                sems = semesterMap[e.target.value];
            
            target.empty();
            if (sems) {
                for (var i = 0, l = sems.length; i < l; i++) {
                    var sem = sems[i];
                    target.append('<option value="' + sem+ '">' + semToString(sem)+ '</option>');
                }
            } else {
                target.append('<option>無學期可選擇</option>');
                target.prop('disabled', true);
            }
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
            if (semesterMap) {
                for (var year in semesterMap) {
                    sbjYear.append('<option value="' + year + '">' + year+ '</option>' );
                }
                sbjYear.change();
            } else {
                alert('查詢學期設定失敗。');
            }
        }
        
        sbjYear.change(setYear);
        init();
    });
    
    if(<%=request.getAttribute("success_msg")!=null %>) {
    	alert('<%=request.getAttribute("success_msg") %>');
    }
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
					<p>${ud.sch_cname}${ud.staff_cname}您好：</p>

					<div class="clubImport">
						<p class="title">社團成員/幹部批次匯入</p>
						<div class="contentBox">
							<b>下載範例檔</b><br>
							<form action="ClubImport" method="GET">
								學年：<select name="sbjYear"></select>&nbsp;
								學期：<select name="sbjSem"></select><br>
								<input type="hidden" name="fileaction" value="download" />
								<input type="submit" value="下載" />
							</form>
							<div class="dashedLine"></div>
							<b>資料上傳</b><br>
							<form action="ClubImport" method="POST" enctype="multipart/form-data">
								學年：<select name="sbjYear"></select>&nbsp;
								學期：<select name="sbjSem"></select><br>
								<input type="file" name="file" /><br>
								<input type="submit" value="上傳" />
							</form>
						</div>
					</div>

					<fieldset>
						<legend>說明</legend>
						<ol>
							<li>欄位說明文件：<a target="_blank" href="documents/Explanation_ClubMemberAndCadre.pdf"><img alt="" src="images/icon_explanation.png">社團成員/幹部</a></li>
							<li>欲上傳之範本請務必從此頁面下載。</li>
							<li>Excel範例檔請依照格式說明填入資料，範例檔的欄位名稱有 <b style="color: red;">*</b>者，為必填欄位。</li>
						</ol>
					</fieldset>
				</div>
			</div>
			<%-- box2 END --%>
		</div>
	</div>
</body>
</html>