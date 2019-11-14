<%@ page language="java" contentType="text/html; charset=UTF-8" import="ntpc.ccai.bean.*"%>
<%
	/* 社團系統 - 報表列印
	
	author: tom */
	UserData ud = (UserData) session.getAttribute("ud");
%>
<!DOCTYPE html>
<html>
<head>

<!-- 
      網頁 : 社團報表
      最後更新人員 : 林家正
      最後更新時間 : 107/06/08
  -->

<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width , initial-scale=1.0 , maximum-scale=1.0 , user-scalable=0">
<title>社團報表</title>
    <jsp:include page="/WEB-INF/jsp/include2.jsp" />
<script>

   $(document).ready(function () {
	    hideemptymethod();
        queryClubYear();
        
        
        
	    // 社團改變
		$("#club").change(function(){			
			cleartable();	
			queryClubMember();			
			
		});	
	    
	    // 學年改變
		$("#sbj_year").change(function(){
			cleartable();
			changclubmethod();
			
		});
		// 學期改變
		$("#sbj_sem").change(function(){
			cleartable();
			changclubmethod();	
			
		});
		
	    // 年級改變
	    $("#grade").change(function(){
	    	cleartable();
			changclubmethod();					
		});
	    // 年級班級
	    $("#cls_class").change(function(){
	    	cleartable();
	    	queryClubMember();
		});
		
		//click 查詢
		$("#savebtn").click(function(){
			cleartable();
			queryClubMember();
		});
		
		$("#teacherbtn").click(function(){
			cleartable();
			queryClubMember();
		});
					
		$("#downloadteaPDF").click(function(){
			teachctcodePDF();
		});
		
		//按下ENTER [查詢]  後不觸發 submit 
		document.onkeydown = function (e) {
			   e = e || window.event;
			   if(e.keyCode === 13) {
				   cleartable();
				   queryClubMember();
				   return false;			   
			   }
		}
		/*
		$("#reg_no").keypress(function (e) { 		
		    var key = e.which; //e.which是按键的值		    
		    if (key == 13) {	    	
		    	queryClubMember(); 
		    }
	    });
		
		$("#idno").keypress(function (e) { 		
		    var key = e.which; //e.which是按键的值		    
		    if (key == 13) {	    	
		    	queryClubMember(); 
		    }
	    }); 
		*/
	});
//確認證書 字號有沒有設定  ： 目前沒用到
function chkselectpdf(){
	var chkpdf = $("#exportClass").val() ;
	if(chkpdf == "ClubPDF9" || chkpdf == "ClubPDF10" || chkpdf == "ClubPDF11"){
	if($("#club").val() == '0'){
	 if($("#hidefontnumber").val() == ''){
		queryClubCertificate();  //沒有這個
	}}}
}
   
function cleartable(){
	   $("#tbody1").empty();
	   $("#tbody2").empty();
	   $("#tbody3").empty();
	   $("#tbody4").empty();
	   $("#tbody5").empty();
}
   
function hideemptymethod(){
	$("#downloadclubPDF").hide();
    $("#downloadclassPDF").hide();
    $("#downloadiorPDF").hide();
    $("#downloadteaPDF").hide();
    $("#sbj_year_sem").hide();
    $("#clubfontnumber").hide();
    $("#tbody1").empty();
    $("#tbody2").empty();
    $("#tbody3").empty();
    $("#tbody4").empty();
    $("#tbody5").empty();
}
   
//社團方法 TABLE
function changclubmethod(){
	var clubcode = $("#exportClass").val();
	switch(clubcode){
	//搜尋社團成員、社團幹部、社團點名單
    case "ClubPDF1": case "ClubPDF2": case "ClubPDF4": case "ClubPDF7": case "ClubPDF10": case "ClubPDF11":
    	$("#tbody1").empty();
		queryClubData();
    break;
    //輸入 身分證 or 學號
    case "ClubPDF5":
    	$("#cls_class").empty();
    	queryClubData();    
    break;
    //不需要多一層 select 直接呼叫結果
    case "ClubPDF6": 
    	$("#tbody1").empty();
    	queryClubMember();
    break;
    //教師聘書
    case "ClubPDF9": 
    	$("#tbody5").empty();
    	queryClubMember();
    break;
   }
}   
     
	function teachctcodePDF(){
	   var ct_code = $("#ct_code").val();		
	   if(ct_code != 0){
		   data = "ClubExport?exportClass=ClubPDF9&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club_value=" + $("#club_value").val() + "&staff_code=" + $("#staff_code").val() + "&ct_code=" + ct_code;
		   $('form#exportForm').attr('action', data).submit();
	   } else{
		   var fonthead = $("#headfontnumber").val();
		   var fontfoot = $("#footfontnumber").val();
		   data = "exportClass=ClubPDF9&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club_value=" + $("#club_value").val() + "&staff_code=" + $("#staff_code").val() + "&staff_value=" + $("#staff_value").val() + "&ct_code=" + ct_code + "&headfontnumber=" + fonthead + "&footfontnumber=" + fontfoot;
		   
		   if(fonthead != "" && fontfoot!= ""){
			   $.ajax({
				      url : "ClubExport",
				      type : "POST",
				      data: data,
				      cache : false,
				      dataType : "text",
				      error : function() {
				          alert('查詢節次資料失敗，請聯絡系統管理員');
				      },
				      success : function(response) {
				    	  if (response == 'fontnumerror') {
				    		  alert('字號設定[錯誤]。');
				    	  } else if(response == 'fontnumrepeat'){
				    		  alert('字號設定[重複]。');
				    	  } else {
				    		  data = "ClubExport?exportClass=ClubPDF9&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club_value=" + $("#club_value").val() + "&staff_value=" + $("#staff_value").val() + "&idno=" + $("#idno").val() + "&ct_code=" + response;
				    		  $("#teacherbtn").click();
					    	  $('form#exportForm').attr('action', data).submit();
					    	  $('#exportForm').attr('action', 'ClubExport');
				    	  }
				      }//End of success
			    });  			   		   			   
		   } else{
			   alert('未設定報表字號(頭、尾)，請輸入報表字號。');
		   }
	   }
   }  
     
   function StuCertificatePDF(rgno,issue_code){	
	   if(issue_code != 0){
		   data = "ClubExport?exportClass=ClubPDF11&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val() + "&rgno=" + rgno + "&issue_code=" + issue_code;
		   $('form#exportForm').attr('action', data).submit();
	   } else{
		   var fonthead = $("#headfontnumber").val();
		   var fontfoot = $("#footfontnumber").val();
		   data = "exportClass=ClubPDF11&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val() + "&rgno=" + rgno + "&issue_code=" + issue_code + "&headfontnumber=" + fonthead + "&footfontnumber=" + fontfoot;
		   
		   if(fonthead != "" && fontfoot!= ""){
			   $.ajax({
				      url : "ClubExport",
				      type : "POST",
				      data: data,
				      cache : false,
				      dataType : "text",
				      error : function() {
				          alert('查詢節次資料失敗，請聯絡系統管理員');
				      },
				      success : function(response) {
				    	  if (response == 'fontnumerror') {
				    		  alert('字號設定[錯誤]。');
				    	  } else if(response == 'fontnumrepeat'){
				    		  alert('字號設定[重複]。');
				    	  } else {
				    		  data = "ClubExport?exportClass=ClubPDF11&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val() + "&rgno=" + rgno + "&issue_code=" + response;
				    		  $("#club").change();
					    	  $('form#exportForm').attr('action', data).submit();
					    	  $('#exportForm').attr('action', 'ClubExport');
				    	  }
				      }//End of success
			    });  			   		   			   
		   } else{
			   alert('未設定報表字號(頭、尾)，請輸入報表字號。');
		   }
	   }
   }
   
   function StuScoreBestPDF(rgno,ex_code){	 
	   if(ex_code != 0){
	   data = "ClubExport?exportClass=ClubPDF10&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val() + "&rgno=" + rgno + "&ex_code=" + ex_code;
	   $('form#exportForm').attr('action', data).submit();
	   } else{
		   var fonthead = $("#headfontnumber").val();
		   var fontfoot = $("#footfontnumber").val();
		   if(fonthead != "" && fontfoot!= ""){
			   data = "exportClass=ClubPDF10&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val() + "&rgno=" + rgno + "&ex_code=" + ex_code + "&headfontnumber=" + fonthead + "&footfontnumber=" + fontfoot;
			   
			   $.ajax({
				      url : "ClubExport",
				      type : "POST",
				      data: data,
				      cache : false,
				      dataType : "text",
				      error : function() {
				          alert('查詢節次資料失敗，請聯絡系統管理員');
				      },
				      success : function(response) {
				    	  if (response == 'fontnumerror') {
				    		  alert('字號設定[錯誤]。');
				    	  } else if(response == 'fontnumrepeat'){
				    		  alert('字號設定[重複]。');
				    	  } else {
				    		  data = "ClubExport?exportClass=ClubPDF10&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val() + "&rgno=" + rgno + "&ex_code=" + response;
				    		  $("#club").change();
					    	  $('form#exportForm').attr('action', data).submit();
					    	  $('#exportForm').attr('action', 'ClubExport');
				    	  }
				      }//End of success
			    });
			   
		   } else{
			   alert('未設定報表字號(頭、尾)，請輸入報表字號。');
		   }
	   }
   }
   
   
   //目前沒用到
   function studentfontnumcount(){
	   var data  = "exportClass=queryfontnum&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	  console.log(response);
		    	  
		    	  var obj = document.getElementById('hidefontnumber');
                   if(response.length > 0){
                	$("#clubfontnumber").show();   
		    	    obj.value = response.length;
		    	    $("#club").change();
                   }
		      }//End of success
	    });  	 
   }
   
   function teacherfontnumcount(){
	   
   }
   
<%-- 2017.09.28-jim-新增Portal Header --%>
$(function(){
	 $("#portal_header").load("/Portal_header.jsp");
});


window.onhelp = function(){return false;}

document.onkeydown = function(theEvent) {
     if (theEvent != null) {
             event = theEvent;
     }
     if (event.altKey || event.ctrlKey) {
             try {                     
                     event.keyCode = 0; 
             } catch(e){}
             return false;
     }
}

//step1 搜尋社團年
function queryClubYear(){   	    		
		var data  = "exportClass=queryClubYear";  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	  console.log(response);
		    	  
		    	  var obj = document.getElementById('sbj_year');
		    	  for(var i = 0 ; i < response.length; i++){			    		  
		    	  obj.options.add(new Option(response[i].sbj_year, response[i].sbj_year , false, false));
		    	  }
		      }//End of success
	    });  	 	
}

//step2 搜尋社團資料
function queryClubData(){   
	selectclubmethod(); 	
}

// 尋找對應的方法(下拉是選單用)
function selectclubmethod(){
	//重設form ation
	$('#exportForm').attr('action', 'ClubExport');
	
	var clubcode = $("#exportClass").val();
	switch(clubcode){
	//搜尋社團成員、社團幹部、社團點名單、社團成員成績
    case "ClubPDF1": case "ClubPDF2": case "ClubPDF4": case "ClubPDF7": case "ClubPDF10": case "ClubPDF11":
    	 $("#club").empty();
 		var data  = "exportClass=queryClub&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val();  		
 	    $.ajax({
 		      url : "ClubExport",
 		      type : "POST",
 		      data: data,
 		      cache : false,
 		      dataType : "json",
 		      error : function() {
 		          alert('查詢節次資料失敗，請聯絡系統管理員');
 		      },
 		      success : function(response) {	  
 		    	      //console.log(response);
 		    	 var obj = document.getElementById('club');		    	 
 		    	 if(response.length > 0){	    		 
 		    	      //obj.options.add(new Option('請選擇',0, false, false));
 		    	  for(var i = 0 ; i < response.length; i++){	    		  
 		    		  //console.log(response[i].club.club_name);		    		  
 		    	      obj.options.add(new Option(response[i].club.club_name,response[i].club_num, false, false));		    	      
 		    	  } 		    	  
 		    	  $("#club").change();
 		    	  
 		    	  /*  目前沒用到
 		    	  if($("#exportClass").val() == "ClubPDF11"){
 		    		 studentfontnumcount();
 		    	  }
 		    	  */
 		    	  }else{
 		    		  obj.options.add(new Option('查無資料',0, false, false));
 		    		  $("#club").change();
 		    	  }
 		    	  
 		      }//End of success
 	    });
    break;
    case "ClubPDF5": 
    	var data  = "exportClass=queryClassClub&sbj_year="+ $("#sbj_year").val() +"&sbj_sem="+ $("#sbj_sem").val() + "&grade=" + $("#grade").val();  		
        $.ajax({
    	      url : "ClubExport",
    	      type : "POST",
    	      data: data,
    	      cache : false,
    	      dataType : "json",
    	      error : function() {
    	          alert('查詢節次資料失敗，請聯絡系統管理員');
    	      },
    	      success : function(response) {	  
    	    	  console.log(response);
    	    	  if(response.length > 0 ){
    	    	  var obj = document.getElementById('cls_class');
    	    	  for(var i = 0 ; i < response.length; i++){		    		  
    	    	      obj.options.add(new Option(response[i].cls_cname, response[i].cls_code , false, false));
    	    	  }
    	    	  $("#cls_class").change();
    	    	  } else{
    	    		  obj.options.add(new Option("尚無班級", "" , false, false));
    	    		  $("#cls_class").change();
    	    	  }
    	      }//End of success
        });  
    break;     
   }
}

//step3 搜尋社團成員資料
function queryClubMember(){   	    		
	selectpdfmethod($("#exportClass").val());
}

// 尋找對應的成員報表方法
function selectpdfmethod(pdfcode){
	switch(pdfcode){
    case "ClubPDF1": 
    	var data  = "exportClass=queryClubMember&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	 if(response.length > 0){ 	
		    		 $("#downloadclubPDF").show();
		    		  var checkrgno = 0; // 確認 學生是否重複
		    	      var cadrenamecount = 0; // 記錄學生人數	    	        
		    	      for (var i=0;i<response.length;i++){   	    
		    	    	    if(response[i].rgno != checkrgno) { 
		    	    	    	checkrgno = response[i].rgno ;
		    	    			cadrenamecount += 1 ; // 記錄學生人數
		    	    	    } else {
		    	    	    	checkrgno = 0 ;   	    	
		    	    	    }
		    	      }
		    		 
		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>' + cadrenamecount + '人</td></tr>'); 	      		    	  
		    	  }else{
		    		  $("#downloadclubPDF").hide();
		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>查無社團成員。</td></tr>');		    	        
		    	  }
		    	  
		      }//End of success
	    });
    break;
    case "ClubPDF2": 
    	var data  = "exportClass=queryClubCader&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	 if(response.length > 0){ 	
		    		 $("#downloadclubPDF").show();
		    		  var checkrgno = 0; // 確認 學生是否重複
		    	      var cadrenamecount = 0; // 記錄幹部人數	    	        
		    	      for (var i=0;i<response.length;i++){   	    
		    	    	    if(response[i].rgno != checkrgno) { 
		    	    	    	checkrgno = response[i].rgno ;
		    	    			cadrenamecount += 1 ; // 記錄幹部人數
		    	    	    } else {
		    	    	    	checkrgno = 0 ;   	    	
		    	    	    }
		    	      }
		    		 
		    	      $("#tbody1").append('<tr><th>社團幹部人數</th><tr>');
		    	      $("#tbody1").append('<tr><td align=center>' + cadrenamecount + '人</td></tr>'); 	      		    	  
		    	  }else{
		    		  $("#downloadclubPDF").hide();
		    	      $("#tbody1").append('<tr><th>社團幹部人數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>查無社團幹部成員。</td></tr>');
		    	  }
		    	  
		      }//End of success
	    });
    break;
    case "ClubPDF3": 
    	var data  = "exportClass=queryClubCaderExperience&reg_no=" + $("#reg_no").val() + "&idno=" + $("#idno").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	      
		    	 if(response.length > 0){ 	
		    		 
		    		  var checkrgno = 0; // 確認 學生是否重複
		    	      var cadrenamecount = 0; // 記錄幹部人數	    	        
		    	      for (var i=0;i<response.length;i++){   	    
		    	    	    if(response[i].rgno != checkrgno) { 
		    	    	    	checkrgno = response[i].rgno ;
		    	    			cadrenamecount += 1 ; // 記錄幹部人數
		    	    	    } else {
		    	    	    	checkrgno = 0 ;   	    	
		    	    	    }
		    	      }
		    	      var chkcadre = response[0].clubCadre.cadre_name;
		    	      var chgsex =  response[0].stuBasis.sex;
		    	      var truesex = "";
		    	      if(chgsex == 1){
		    	    	  truesex = "男";
		    	      }else{truesex = "女";}	
		    	      
		    	      var week = response[0].stuBasis.birthday.search("星");
		    	      var date = response[0].stuBasis.birthday.substring(0, 9); // 擷取字串(去掉星期)
		    	      $("#tbody2").append('<tr><td rowspan="4"><img src="./ClubImage?rgno='+ response[0].stuBasis.rgno +'" alt="大頭貼" height="150px" width="150px"></td><td>姓名:</td><td>' + response[0].stuBasis.cname + '</td></tr>');
		    	      $("#tbody2").append('<tr><td>班級:</td><td>'+ response[0].stuClass.cls_cname +'</td></tr>');	
		    	      $("#tbody2").append('<tr><td>生日：' + date + '</td><td>學號：'+ response[0].stuBasis.reg_no +'</td></tr>');	
		    	      $("#tbody2").append('<tr><td>入學日期：'+ response[0].stuBasis.cmat_year +'年</td><td>性別：'+ truesex +'</td></tr>');	
		    	     
		    	      $("#tbody3").append('<tr>');
		    	      $("#tbody3").append('<td>服務紀錄</td>');		
		    	      $("#tbody3").append('</tr>');	 
		    	      
		    	      $("#tbody3").append('<tr>');
		    	      if(chkcadre === undefined || chkcadre === ''){
		    	    	  $("#tbody3").append('<td align=center>尚無服務紀錄</td>');
		    	      }else{
		    	          $("#downloadiorPDF").show();
		    	          $("#tbody3").append('<td align=center>共' + response.length + '筆資料</td>');	
		    	      }
		    	      $("#tbody3").append('</tr>');	

		    	  }else{
		    		  $("#downloadiorPDF").hide();
		    	      $("#tbody2").append('<tr><th>查詢結果</th></tr>');
		    	      $("#tbody2").append('<tr><td align=center>查無資料。</td></tr>');		    	           	      
		    	  }
		      }//End of success
	    });
    break;
    case "ClubPDF4": 
    	var data  = "exportClass=queryClubMember&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	 if(response.length > 0){ 	
		    		 $("#downloadclubPDF").show();
		    		  var checkrgno = 0; // 確認 學生是否重複
		    	      var cadrenamecount = 0; // 記錄學生人數	    	        
		    	      for (var i=0;i<response.length;i++){   	    
		    	    	    if(response[i].rgno != checkrgno) { 
		    	    	    	checkrgno = response[i].rgno ;
		    	    			cadrenamecount += 1 ; // 記錄學生人數
		    	    	    } else {
		    	    	    	checkrgno = 0 ;   	    	
		    	    	    }
		    	      }
		    		 
		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>' + cadrenamecount + '人</td></tr>');      		    	  
		    	  }else{
		    		  $("#downloadclubPDF").hide();
		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>查無社團成員。</td></tr>');
		    	  }	    	  
		      }//End of success
	    });
    break;
    case "ClubPDF5": 
    	var data  = "exportClass=queryClassClubTable&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&cls_class=" + $("#cls_class").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	 if(response.length > 0){ 	
		    		 $("#downloadclassPDF").show();
		    		  var checkrgno = 0; // 確認 學生是否重複
		    	      var cadrenamecount = 0; // 記錄學生人數	    	        
		    	      for (var i=0;i<response.length;i++){   	    
		    	    	    if(response[i].stuBasis.rgno != checkrgno) { 
		    	    	    	checkrgno = response[i].stuBasis.rgno ;
		    	    			cadrenamecount += 1 ; // 記錄學生人數
		    	    	    } else {
		    	    	    	checkrgno = 0 ;   	    	
		    	    	    }
		    	      }	    	
		    	      
		    	      $("#tbody4").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody4").append('<tr><td align=center>'+ cadrenamecount +'人，共'+ response.length +'筆資料</td></tr>');		    	         
		    	  }else{
		    		  $("#downloadclassPDF").hide();
		    	      $("#tbody4").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody4").append('<tr><td align=center>查無社團成員。</td></tr>');		    	        
		    	  }	    	  
		      }//End of success
	    });
    break;
    case "ClubPDF6": 
    	$("#tbody1").empty();
    	var data  = "exportClass=queryClubSelectionTable&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {
		    	  if(response.length > 0){
		    	      console.log(response);
		    	      $(".selectclub").show();
		    	      $("#downloadclubPDF").show();

		    	      $("#tbody1").append('<tr><th>社團數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>共'+ response.length +'筆資料</td></tr>');
		    	  }else{
		    		  $(".selectclub").hide();
		    		  $("#downloadclubPDF").hide();
		    	      $("#tbody1").append('<tr><th>社團數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>查無社團。</td></tr>');
		    	  }	    	  
		      }//End of success
	    });
    break;
    case "ClubPDF7": 
    	var data  = "exportClass=queryClubMemberScore&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	 if(response.length > 0){ 	
		    		 $("#downloadclubPDF").show();
		    		  var checkrgno = 0; // 確認 學生是否重複
		    	      var cadrenamecount = 0; // 記錄學生人數	    	        
		    	      for (var i=0;i<response.length;i++){   	    
		    	    	    if(response[i].rgno != checkrgno) { 
		    	    	    	checkrgno = response[i].rgno ;
		    	    			cadrenamecount += 1 ; // 記錄學生人數
		    	    	    } else {
		    	    	    	checkrgno = 0 ;   	    	
		    	    	    }
		    	      }
		    		 
		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>' + cadrenamecount + '人</td></tr>');
		    	  }else{
		    		  $("#downloadclubPDF").hide();

		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');
		    	      $("#tbody1").append('<tr><td align=center>查無社團成員。</td></tr>');
		    	  }	    	  
		      }//End of success		      
	    });
    break;
    case "ClubPDF8": 
    	var data  = "exportClass=queryClubRecord&reg_no=" + $("#reg_no").val() + "&idno=" + $("#idno").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	      
		    	 if(response.length > 0){ 	

		    	      var chgsex =  response[0].stuRegister.stuBasis.sex;
		    	      var truesex = "";
		    	      if(chgsex == 1){
		    	    	  truesex = "男";
		    	      }else{truesex = "女";}		    	      
//ShowImg?op=showStu_photoImg&rgno=
	                  var date = response[0].stuRegister.stuBasis.birthday.substring(0, 9); // 擷取字串(去掉星期)
		    	      $("#tbody2").append('<tr><td rowspan="4"><img src="./ClubImage?rgno='+ response[0].rgno +'" alt="大頭貼" height="150px" width="150px"></td><td>姓名</td><td>' + response[0].stuRegister.stuBasis.cname + '</td></tr>');		              
		    	      $("#tbody2").append('<tr><td>班級</td><td>'+ response[0].stuRegister.stuClass.cls_cname +'</td></tr>');	    	      
		    	      $("#tbody2").append('<tr><td>出生日期：' + date + '</td><td>學號：'+ response[0].stuRegister.stuBasis.reg_no +'</td></tr>');		    	      
		    	      $("#tbody2").append('<tr><td>入學日期：民國'+ response[0].stuRegister.stuBasis.cmat_year +'年</td><td>性別：'+ truesex +'</td></tr>');
	    	     
		    	      $("#tbody3").append('<tr>');
		    	      $("#tbody3").append('<td>服務紀錄</td>');		
		    	      $("#tbody3").append('</tr>');	 	    	      
		    	      $("#tbody3").append('<tr>');
		    	      $("#tbody3").append('<td align=center>共'+ response.length +'筆資料</td>');
		    	      $("#tbody3").append('</tr>');
		    	      
		    	      $("#downloadiorPDF").show();
		    	  }else{
		    		  $("#downloadiorPDF").hide();
		    	      $("#tbody2").append('<tr><th>查詢結果</th><tr>');
		    	      $("#tbody2").append('<tr><td align=center>查無服務紀錄。</td></tr>');		    	            	      
		    	  }
		    	 
		      }//End of success
	    });
    break;
    case "ClubPDF9": 
    	var data  = "exportClass=queryClubTeacher&staff_code=" + $("#staff_code").val() + "&idno=" + $("#idno").val() + "&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	 if(response.length > 0){ 	
		    		 $("#clubfontnumber").show();
		    		 $("#downloadteaPDF").show();

		    	      $("#tbody5").append('<tr><th>字號</th><th>名稱</th><th></th><th></th><th></th></tr>');
		    	      var ct_code = response[0].ct_code;
	    	     		var strfontnum = "";	     		  
	    	     			if(ct_code == "0"){strfontnum = "未設定字號";}
	    	     			else {strfontnum = ct_code;}
		    	      $("#tbody5").append('<tr><td align=center>' + strfontnum + '</td><td align=center>' + response[0].staff.staff_cname + '</td><td align=center><input type="hidden" id="ct_code" name="ct_code" value="'+ response[0].ct_code +'"></td><td align=center><input type="hidden" id="club_value" name="club_value" value="'+ response[0].club_num +'"></td><td align=center><input type="hidden" id="staff_value" name="staff_value" value="'+ response[0].staff.staff_code +'"></td></tr>');		    	        
	    	      		    	  
		    	  }else{
		    		  $("#clubfontnumber").hide();
		    		  $("#downloadteaPDF").hide();

		    	      $("#tbody5").append('<tr><th>查詢結果</th></tr>');
		    	      $("#tbody5").append('<tr><td align=center>查無社團教師。</td></tr>');		    	        
		    	  }	    	  
		      }//End of success
	    });
    	
    break;
    case "ClubPDF10": 
    	var data  = "exportClass=queryClubMemberScoreBest&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val();  		
	    $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);
		    	 if(response.length > 0){ 
		    		 $("#clubfontnumber").show();
		    	      $("#tbody1").append('<tr><th>字號</th><th>姓名</th><th>成績</th><th>下載</th></tr>');	
		    	     	for(var i=0 ; i<response.length ;i++){
		    	     		var ex_code = response[i].clubMember.ex_code;
		    	     		var strfontnum = "";	     		  
		    	     			if(ex_code == "0"){strfontnum = "未設定字號";}
		    	     			else {strfontnum = ex_code;}
		    	     	 	$("#tbody1").append('<tr><td align=center>' + strfontnum + '</td><td align=center>' + response[i].stubasis.cname + '</td><td align=center>' + response[i].clubMember.club_score + '</td><td align=center onclick="StuScoreBestPDF('+ response[i].stubasis.rgno +',\''+ response[i].clubMember.ex_code +'\')"><img src="images/pdficon.png" alt=""></td></tr>');
		    	     	}
		    	      	    	      		    	  
		    	  }else{
		    		  $("#downloadclubPDF").hide();
		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');    	      
		    	      $("#tbody1").append('<tr><td align=center>查無社團成員。</td></tr>');		    	        

		    	  }
		    	  
		      }//End of success 
	    });
    break;
    case "ClubPDF11": 
    	var data  = "exportClass=queryClubMemberScoreBest&sbj_year=" + $("#sbj_year").val() + "&sbj_sem=" + $("#sbj_sem").val() + "&club=" + $("#club").val();  
    	
    	if($("#club").val() != '0' || $("#hidefontnumber").val() == ''){
	     $.ajax({
		      url : "ClubExport",
		      type : "POST",
		      data: data,
		      cache : false,
		      dataType : "json",
		      error : function() {
		          alert('查詢節次資料失敗，請聯絡系統管理員');
		      },
		      success : function(response) {	  
		    	      console.log(response);    	      
		    	 if(response.length > 0){ 	
		    		 $("#clubfontnumber").show();
		    	      $("#tbody1").append('<tr><th>字號</th><th>姓名</th><th>下載</th></tr>');	

		    	     	for(var i=0 ; i<response.length ;i++){
		    	     		var issue_code = response[i].clubMember.issue_code;
		    	     		var strfontnum = "";	  
		    	     		 console.log(issue_code);
		    	     			if(issue_code == "0" || issue_code == 'undefined'){strfontnum = "未設定字號";}
		    	     			else {strfontnum = issue_code;}
		    	      	 		$("#tbody1").append('<tr><td align=center>' + strfontnum + '</td><td align=center>' + response[i].stubasis.cname + '</td><td align=center onclick="StuCertificatePDF('+ response[i].stubasis.rgno+',\''+ response[i].clubMember.issue_code +'\')"><img src="images/pdficon.png" alt=""></td></tr>');	     		  
		    	     	}
		    	      	    	      		    	  
		    	  }else{
		    		  $("#downloadclubPDF").hide();
		    	      $("#tbody1").append('<tr><th>社團人數</th></tr>');    	      
		    	      $("#tbody1").append('<tr><td align=center>查無社團成員。</td></tr>');		    	        
		    	  }
		    	  
		      }//End of success   ["0"].stubasis.rgno
	     });
    	}
    break;
   }
}	
  </script>
</head>

<body onKeyDown="if (event.keyCode == 17) {return false;}">
	<div>
		<!-- container -->
		<div id="outer" >

			<div id="main" >
				<div class="outer leftsidenavbar">
					<jsp:include page="/WEB-INF/jsp/ClubMgt/includeMenu.jsp" />
				</div>
				<!-- content -->
				<div id="content">
					<div id="box2" >
						<%--網頁內容寫在這 --%>					
							<p><%=ud.getSch_cname()%><%=ud.getStaff_cname()%>您好：</p>						
						<form id="exportForm" name="exportForm" method="POST" action="ClubExport">
							<div>
								<div class="pdfselect">
									<p>報表列印</p>
									<div class="select">
										<ul id="pdfulid">
											<li id="pdfulid1"><a id="#club"
												class="pdfselection">社團成員名單 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid2"><a id="#club"
												class="pdfselection">社團幹部名單 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid3"><a id="#student"
												class="pdfselection">社團幹部經歷證明 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid4"><a id="#club"
												class="pdfselection">社團點名單 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid5"><a id="#class"
												class="pdfselection">班級參加社團名單 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid6"><a id="#club"
												class="pdfselection">線上選課一覽表 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid7"><a id="#club"
												class="pdfselection">線上成績一覽表 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid8"><a id="#student"
												class="pdfselection">社團服務時數一覽表 </a><i
												style="font-size: 18px" class="fa">&rArr;</i></li>
											<li id="pdfulid9"><a id="#teacher"
												class="pdfselection">社團教師聘書</a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid10"><a id="#club"
												class="pdfselection">社團學生證書 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
											<li id="pdfulid11"><a id="#club"
												class="pdfselection">社團學生證明書 </a><i style="font-size: 18px"
												class="fa">&rArr;</i></li>
										</ul>
										<input type="hidden" id="exportClass" name="exportClass"
											value="">
									</div>
								</div>
								<!--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  -->
								<div class="pdfresult">

									<p>下載列印預覽</p>
									<div id="sbj_year_sem">
										學年： <select id="sbj_year" name="sbj_year">
										</select> 學期： <select id="sbj_sem" name="sbj_sem">
											<option value="1">上</option>
											<option value="2">下</option>
										</select>
									</div>
									<div id="clubfontnumber">
									  <div class="fontnum">
									       設定字號(頭): <input id='headfontnumber' name='headfontnumber' type="text" style="width: 15%;"><br> 字號(尾): <input id='footfontnumber' name='footfontnumber' type="text" maxlength="10" style="width: 20%;">
									   <input id='hidefontnumber' name='hidefontnumber' type="hidden">
									  </div>
									</div>									
									<div class="pdfclass">																    
										<div class="selectclass">
											年級： <select id="grade" name="grade">
												<option value="1">一年級</option>
												<option value="2">二年級</option>
												<option value="3">三年級</option>
											</select> 選擇班級： <select id="cls_class" name="cls_class">
											</select> <input id="downloadclassPDF" type="submit" value="下載PDF">
										</div>
										<table id='tbody4'></table>
									</div>
									<div class="pdfclub">																
										<div class="selectclub">									
											選擇社團： <select name="club" id="club">
											</select> <input id='downloadclubPDF' type="submit" value="下載PDF">
										</div>
										<table id='tbody1'>
										</table>
									</div>
									
									<div class="pdfstudent">
										<div class="selectstudent">
											學號: <input id='reg_no' name='reg_no' type="text"
												style="width: 100px;"><br> 或 身分證： <input
												id='idno' name='idno' type="text" style="width: 100px;"><br>
											<input id='savebtn' class="savebtn" type="button" value="搜尋">
											&nbsp; <input id='downloadiorPDF' type="submit" class="savebtn"
												value="下載PDF">
										</div>
										<div class="tablediv">
											<table id='tbody2'></table>
											<table id='tbody3'></table>
										</div>
										
									</div>
									<div class="pdfteacher">
										<div class="selectteacher">
											教師號碼: <input id='staff_code' name='staff_code' type="text"
												style="width: 100px;"><br> 或 身分證： <input
												id='idno' name='idno' type="text" style="width: 100px;"><br>
											<input id='teacherbtn' class="savebtn" type="button" value="搜尋">
											&nbsp;  &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;<label id='downloadteaPDF' class="savebtn">下載PDF</label>
										</div>
										<div class="tablediv">
											<table id='tbody5'></table>											
										</div>
									</div>
									
								</div>
								<script>
     $(document).ready(function () {
      // $(".pdfclass, .pdfclub").animate({width:'toggle'},1);
      $(".pdfclass, .pdfclub, .pdfstudent, .pdfteacher").hide();
        

       //PDF列印印選擇----------------------------------------
       
       $("#pdfulid li").click(function(){ 
    	      	 
      //console.log($(this).children().first().attr('id'));
      //console.log($(this).find("a").text());
    	   
    	exportvalue($(this).attr('id'));
    	   
        var href = $(this).children().first().attr('id');
        var title = $(this).children().first().text();
        
        $('.pdfselection').parent("li").removeClass("beenselect"); 
        $('.pdfselection').next(".fa").css("right","60px");

        $(".pdfresult p").text(title);

        $(this).parent("li").addClass("beenselect");
        $(this).next(".fa").css("right","0px");
        
        if (href == "#class"){      
            $(".pdfclass").slideDown( "slow" );
            $(".pdfclub,.pdfstudent,.pdfteacher").hide(); //隱藏 其餘三個
            
            //清空其他資料
        } else if (href =="#club") {
            $(".pdfclub").slideDown( "slow" );
            $(".pdfclass,.pdfstudent,.pdfteacher").hide();           
            //清空其他資料
        } else if (href == "#student") {
            $(".pdfstudent").slideDown("slow");
            $(".pdfclass,.pdfclub,.pdfteacher").hide();
            //清空其他資料
        } else if (href == "#teacher") {
            $(".pdfteacher").slideDown("slow");
            $(".pdfclass,.pdfclub,.pdfstudent").hide();
            //清空其他資料
        }
        
      });
       
      //----------------------------------------------------PDF列印印選擇
      //班別或課程選擇--------------------------------------------------
      /*
      $(".pdfclub table").click(function(){
        $(".pdfclub table").css({display: "none"});
        // $(this).animate({height: "0px"}, 250);
        $(".hiddentable").css({display:"table"});
      });
      */
    });

     function exportvalue(pdfcode){

       switch(pdfcode){
         case 'pdfulid1':         	 
        	 hideemptymethod();
         $("#sbj_year_sem").show(); 
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF1";
         queryClubData();
         break;
         case 'pdfulid2': 
        	 hideemptymethod();
         $("#sbj_year_sem").show(); 
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF2";
         queryClubData();
         break;
         case 'pdfulid3':
        	 hideemptymethod();	 
         $("#sbj_year_sem").hide();
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF3";  
         break;
         case 'pdfulid4':
        	 hideemptymethod();
         $("#sbj_year_sem").show();
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF4"; 
         queryClubData();
         break;
         case 'pdfulid5': 
        	 hideemptymethod();
         $("#sbj_year_sem").show();
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF5";  
         queryClubData();
         break;
         case 'pdfulid6': 
        	 hideemptymethod();
         $("#sbj_year_sem").show();
         $(".selectclub").hide();
         document.getElementById("exportClass").value = "ClubPDF6";       
         queryClubMember();
         break;
         case 'pdfulid7':
        	 hideemptymethod();
         $("#sbj_year_sem").show();
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF7";
         queryClubData();
         break;
         case 'pdfulid8': 
        	 hideemptymethod();
         $("#sbj_year_sem").hide();
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF8";  
         break;
         case 'pdfulid9':
        	 hideemptymethod();
         $("#sbj_year_sem").show();
         document.getElementById("exportClass").value = "ClubPDF9";  
         break;
         case 'pdfulid10':
        	 hideemptymethod();
         $("#sbj_year_sem").show();
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF10";  
         queryClubData();
         break;
         case 'pdfulid11':
        	 hideemptymethod();
         $("#sbj_year_sem").show();
         $(".selectclub").show();
         document.getElementById("exportClass").value = "ClubPDF11";         
         queryClubData();
         break;          
       }
     }
   </script>

								<div id="result"></div>

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
	</div>

</body>

</html>