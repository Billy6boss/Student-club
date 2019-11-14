<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
    <html>
    <head>
        <meta name="keywords" content="" />
        <meta name="description" content="" />
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <title>選社設定</title>
        <jsp:include page="/WEB-INF/jsp/include.jsp" />
        <link href="css/clubstyle.css" rel="stylesheet">
        <script type="text/javascript" src="js/clubselectionmethod.js"></script>
    <style>
    /*社團自製樣式設定*/
            .idnumber{
                background: #e6bb51;
            }
            #timeselecttab,#modselecttab{
                margin-right: 5px; 
            }
            #modselecttab{
                cursor: default;
                width: 35%;
                opacity: 0.1;
                margin-left: 4%;
                margin-bottom:0px;
            }
            
            #Model{
                display: none;
            }
            #classlimit.result{
                position: relative;
                width: 75.9%;
            }
            #classlimit a{
                /*background:#066DF379;*/
                color: blue;
            }
            #classlimit .selection{
                box-shadow: 3px 3px 5px grey;
                border: 3px solid #00B100FF;
                position: absolute;
                max-height: 200px;
                margin-top: 3px;
                overflow: auto;
                display: none;
                width: 100%;
                bottom: 35px;
                left: -5px;
            }
            #classlimit i{
                transition: all .25s;
                font-size: 25px;
            }
            #classlimit .rotate{
                transform: rotate(180deg);
            }
            #classlimit .selection table{
                margin:0;
                padding: 0;
                border-top: 10px solid #00B100FF;
                border-bottom:10px solid #00B100FF;
            }
            #classlimit .selection ul{
                margin-bottom: 0px;
            }
            #classlimit .selection ul li{
                display: inline-block;
                padding: 0;
                border: none;
            }
            span#G1result{
                color: #6D916DFF;
            }
            span#G2result{
                color: #D82E4DFF;
            }
            span#G3result{
                color: #000000FF;
            }
            input[type = "datetime-local"]{
                width: 272px;
                font-size: 18px;
            }
            .dspachlabel,.optradiolabel{
                display: block;
                height: 30px;
                width: 100px;
                /*margin: auto 24px auto 5px;*/
                cursor: pointer;
                padding: 5% 0px;
            }
            ol{
                margin-left: 20px
            }
            #minvalue input{
                width: 10%;
            }
            #minvalue{
                transition:all 0.2s;
            }
</style>
<script>
	$(document).ready(function(){
		var result = false;
		
		result = '${result}';
		
		if(result == 'true'){
			alret("新增成功");
		}
		
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
                    <p>${ud.sch_cname}${ud.staff_cname} 您好：</p>
                    <!-- 頁籤標題按鈕設定 -->
                <button id="timeselecttab" class="tab nomal greentheme">線上選課時間</button>
                <button id="modselecttab" class="tab nomal greentheme">選課模式設定</button>
                <div class="tabcontent">
                    <div id="ClubTime" class="bigtable">
                        <table>
                            <thead>
                                <tr>
                                    <th>使用</th>
                                    <th>項次</th>
                                    <th>學年</th>
                                    <th>學期</th>
                                    <th>線上選社開始時間</th>
                                    <th>線上選社結束時間</th>
                                    <th>設定</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty clubselection}">
                                        <tr>
                                            <td colspan="7">查無資料</td>                                            
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="clubSem" items="${clubselection}">
                                            <tr>
                                                <td><input type="checkbox" id="${clubSem.cs_num}" class="customckbox" name="inuse" ${clubSem.inuse ? 'checked' : ''}/><label for="${clubSem.cs_num}"></label></td>
                                                <td class="idnumber">${clubSem.cs_num}</td>
                                                <td>${clubSem.sbj_year}</td>
                                                <td>${clubSem.sbj_sem}</td>
                                                <td>${clubSem.cs_sdate}</td>
                                                <td>${clubSem.cs_edate}</td>
                                                <td><button id="settingbtn" class="primarybtn icon">設定</button></td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                <tr>
                                    <td><input type="checkbox" id="new" class="customckbox" disabled><label for="new"></label></td>
                                    <td class="idnumber">Next</td>
                                    <td>ThisY</td>
                                    <td>ThisS</td>
                                    <td>--/--/--- --:--</td>
                                    <td>--/--/--- --:--</td>
                                    <td><button id="settingbtn" class="primarybtn icon">新增</button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- 模式設定 -->
                    <div id="Model">
                        <div class="tab fullsize graytheme">
                            <input id="usecopysetting" type="checkbox">
                            複製相同已儲存設定
                            <select id="copyselectsetting">
                                <option>
                                    <div id="idnb">1.</div>
                                    <div id="tarttime">97/09/11-01:00</div>～
                                    <div id="endtime">97/09/11-12:00</div>
                                </option>
                            </select>
                        </div>
                        
                        <div class="bigtable">
                        <form action="ClubSelectionMethod" method="post" id="clubselectionmethod">
                            <table>
                                <thead>
                                    <tr>
                                        <th>項次</th>
                                        <th>學年</th>
                                        <th>學期</th>
                                        <th>線上選社開始時間</th>
                                        <th>線上選社結束時間</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="idnumber">1</td>
                                        <td>${sbj_year}</td>
                                        <td>${sbj_sem }</td>
                                        <td><input type="datetime-local" name="cs_sdate" value="" required></td>
                                        <td><input type="datetime-local" name="cs_edate" required></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>


                        <!-- 設定開始 -->
                        <fieldset>
                            <legend>1. 模式選擇</legend>
                            <div class="hortable">
                                <table>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <label class="dspachlabel"><input type="radio" id='dspach' name="cs_method" value="0">分發選社</label>
                                            </td>
                                            <td>
                                                <label for='dspach'>
                                                    <ol>
                                                        <li>請輸入每個學生選社的最大志願數(若沒有輸入，預設為10)</li>
                                                        <li>學生線上選完社團後，統一由管理者分發作業，依學生的志願順序排序，來分配學生的錄取社團。</li>
                                                        <li>管理者於分發作業完成後，公告學生錄取社團於網站或列印表單通知學生</li>
                                                    </ol>
                                                </label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="optradiolabel"><input type="radio" id='intime' name="cs_method" value="1">即時選社</label>
                                            </td>
                                            <td>
                                                <label for='intime'>
                                                    <ol>
                                                        <li>系統管理者將社團新增完畢、選社時間設定完成後，即可開放學生線上選社。</li>
                                                        <li>學生選時，統便自動醫學生選擇的社團判斷是否還有名額，若有即錄取，否則會請學生在選下一個社團，直到社團或沒有社團可選為止。</li>
                                                        <li>選擇此模式，適用於大部分學生都有電腦網路設備，可同時進行線上選擇作業。</li>
                                                    </ol>
                                                </label>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <legend>2. 限制設定</legend>
                            <div class="hortable">
                                <table>
                                    <tr id="minvalue">
                                        <td>最少志願數</td>
                                        <td colspan="5"><input type="number" name="cs_lower_limit" required></td>
                                    </tr>
                                    <tr>
                                        <td>社團上限人數</td>
                                        <td><input type="number" name="total_limit" required></td>
                                        <td>該社團年級上限人數(選填)</td>
                                        <td><input type="number" name="grade_limit"></td>
                                        <td>該社團班級上限人數(選填)</td>
                                        <td><input type="number" name="class_limit"></td>
                                    </tr>
                                    <tr>
                                        <td>可選擇班級</td>
                                        <td colspan="5" id="classlimit" class="result">
                                            <span id="G1result"></span><br>
                                            <span id="G2result"></span><br>
                                            <span id="G3result"></span><br>
                                            <a href="#">新增班級<i class="fa fa-angle-double-up"></i></a>
                                            <div class="selection">
                                                <table>
                                                    <tbody>
                                                        <tr>
                                                            <td><input id="G1ckbox" type="checkbox" /><label for="G1ckbox">一年級</label></td>
                                                            <td>
                                                                <ul id="G1" class="checkedable">
<!--                                                                 <li><input type="checkbox" id="G1C01"><label for="G1C01">一年01班</label></li> -->
                                                                    <c:forEach var="classG1" items="${stuclassG1}">
                                                                        <c:if test="${classG1.grade == '1'.charAt(0)}">
                                                                            <li>
                                                                                <input type="checkbox" id="${classG1.cls_code}" name="grade_one_limit" value="${classG1.cls_code}">
                                                                                <label for="${classG1.cls_code}">${classG1.cls_cname}</label>
                                                                            </li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </ul>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><input id="G2ckbox" type="checkbox"><label for="G2ckbox">二年級</label></td>
                                                            <td>
                                                                <ul id="G2" class="checkedable">
                                                                    <c:forEach var="classG2" items="${stuclassG2}">
                                                                        <c:if test="${classG2.grade == '2'.charAt(0)}">
                                                                            <li>
                                                                                <input type="checkbox" id="${classG2.cls_code}" name="grade_two_limit" value="${classG2.cls_code}">
                                                                                <label for="${classG2.cls_code}">${classG2.cls_cname}</label>                                
                                                                            </li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </ul>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><input id="G3ckbox" type="checkbox"><label for="G3ckbox">三年級</label></td>
                                                            <td>
                                                                <ul id="G3" class="checkedable">                                    
                                                                    <c:forEach var="classG3" items="${stuclassG3}">
                                                                        <c:if test="${classG3.grade == '3'.charAt(0)}">
                                                                            <li>
                                                                                <input type="checkbox" id="${classG3.cls_code}" name="grade_three_limit" value="${classG3.cls_code }">
                                                                                <label for="${classG3.cls_code}">${classG3.cls_cname}</label>
                                                                            </li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </ul>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <legend>3. 選擇以上設定適用社團</legend>
                            <div class="resulttable scrollable checkedable">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>選取 <input type="checkbox" name="ckall"></th>
                                            <th>社團編號</th>
                                            <th>社團名稱</th>
                                            <th>性別取向</th>
                                            <th>社團是否開放</th>
                                            <th>社團年級限制</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                <c:choose>
                                <c:when test="${empty clubsems}">
                                <tr>
                                    <td colspan="6">此年度無社團</td>                                            
                                </tr>
                            </c:when>
                            <c:otherwise>
                            <c:forEach var="clubSem" items="${clubsems}">
                                           <tr>
                                                <td><input type="checkbox" name="club_num" value="${clubSem.club.club_num}"></td>
                                                <td>${clubSem.club.club_code}</td>
                                                <td>${clubSem.club.club_name}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${clubSem.club.sex == '1'}">男性取向</c:when>
                                                        <c:when test="${clubSem.club.sex == '2'}">女性取向</c:when>
                                                        <c:otherwise>無</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    ${clubSem.selectable ? '開放選社': '不開放選社'}
                                                </td>
                                                <td>
                                                    ${clubSem.grade_one ? '一年級': ''}
                                                    ${clubSem.grade_two  ? '二年級': ''}
                                                    ${clubSem.grade_three ? '三年級': ''}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>
                        <div class="btngroup">
                            <input type="submit" class="savebtn" value="儲存設定">
                            <input type="button" class="cancelbtn" value="取消">
                        </div>
                        </form>
                    </div>
                </div>
        </div><!-- end id=box2 -->                  
        <br class="clear" />
    </div><!-- end id=content -->
    <br class="clear" />
</div>  <!-- end id=main -->    
</div>
</body>
<%-- 
<script>
// <c:forEach var="classG2" items="${stuclass}">
//  $('<li><input type="checkbox" id="${classG2.cls_code}"><label for="${classG2.cls_code}">${classG2.cls_cname}</label></li>').appendTo($('#G${classG2.grade}'));
// </c:forEach>
</script>
--%>
</html>
