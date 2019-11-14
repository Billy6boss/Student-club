<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" 
import="ntpc.ccai.bean.*, ntpc.bean.*, ntpc.bean.MisFunctionList, ntpc.bean.MisFunction, ntpc.ccai.clubmgt.bean.Club"%>
<%
UserData ud = (UserData)session.getAttribute("ud");
Club club = (Club) session.getAttribute("club");

MisFunctionList mfl = new MisFunctionList();
if (ud != null) {
mfl = ud.getUser_func();
}

    // 功能表分組
int[] Group = new int[]{1, 2, 3, 4, 5};
    // 群組英文名稱
String[] GroupEname = new String[]{"Clubs", "ClubSeletion", "ClubManagement", "ClubExport", "ClubActivity"};
    // 群組中文名稱
String[] GroupCname = new String[]{"社團一覽", "線上選社", "社團管理", "社團報表列印", "社團活動"};
%>

<!--menu -->
<style>
.thisclub{
    background: linear-gradient(to left, #99ccff 0%, #ffffff 71%);
    width: 235px;
}
.thisclub p{
   @import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);
   font-family: "cwTeXYen", sans-serif;
   font-size: 30px;
   font-weight: bold;
   text-align: center;
   margin-bottom: 0;
   margin-top:5px;
}
</style>
<div id="cssmenu">
    <%if (ud != null){ %>
    <ul id="nav">
        <li><a id='ClubMgt' href="ClubMgt"><span>社團系統首頁</span></a></li>
        <%if (club != null) {%>
        <li class="thisclub"><span>目前選擇社團: <p><%= club.getClub_name()%></p></span></li>
        <%} %>
        <%for (int i = 0; i < Group.length; i++) {
        if (club == null && i == 0) {
        continue;
    }
    if (club != null && i > 0) {
    break;
}
int group = Group[i];
String GEname = GroupEname[i] + "Li" ;
String GCname = GroupCname[i];

MisFunctionList submfl = new MisFunctionList();
submfl = mfl.getByFunc_group(group);
if (submfl != null) {%>
<li class='has-sub'><a href="#" id='<%=GEname %>' onclick="menujs('<%=GEname%>')"><span><%=GCname %></span></a>
    <ul>
        <%for (int j = 0; j < submfl.size(); j++) {
        MisFunction func = submfl.get(j);
        String func_ld = func.getFunc_link() + "Li";
        String func_link = func.getFunc_link();
        String func_name = func.getFunc_cname(); 
        %>                            
        <li><a id='<%=func_ld %>' href="<%=func_link%>"><span><%=func_name %></span></a></li> 
        <%} %>             
    </ul>
</li>
<%}
} %>
</ul>
<%} %>       
</div>
<!--menu END-->