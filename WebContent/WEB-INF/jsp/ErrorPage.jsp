<%@ page contentType="text/html; charset=UTF-8" import="ntpc.ccai.bean.UserData"%>
<script>
<%
    // 失敗的 view: 傳送 error_msg(錯誤訊息)顯示
    // 收集資料
    String error_msg = (String)request.getAttribute("error_msg");   // 錯誤訊息
    UserData ud = (UserData)session.getAttribute("ud"); 
    String url = "/StuClub/Login";
    if (error_msg == null) {
        %>alert('發生不明錯誤，請聯絡系統管理人員');
          location.replace('/');<%
    } else {
        if (error_msg.contains("script")) {
            %></script><%=error_msg%><script><%
        } else {
            %>alert('<%=error_msg%>');<%
        }
        if (ud == null){
            %>location.replace('/');<%
        } else {
            String role_code = ud.getRole_code();
        	if ("std".equals(role_code)) {
                url += "Stu";
            } else {
                if ("tea".equals(role_code)) url += "Tea";
                url += ud.getAdg_code();
            }
        	%>location.replace('<%=url%>');<%
        }
    }
%>
</script>