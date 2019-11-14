<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
    $.ajax({
        url: "/PortalHeader",
        type: "GET",
        cache: false,
        dataType: "text",
        error: function() {
            alert("ajax error");
        },
        success: function(response) {
           $("#portal_header").html(response);
        }
     });
</script>
<div id="portal_header"></div>