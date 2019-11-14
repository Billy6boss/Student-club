/*$( document ).ready(function() {
$('#cssmenu > ul > li > a').click(function() {
  $('#cssmenu li').removeClass('active');
  $(this).closest('li').addClass('active');	
  var checkElement = $(this).next();
  alert($(this).get(0).id);
  alert(checkElement.is('ul'));alert(checkElement.is(':visible'));
  if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
    $(this).closest('li').removeClass('active');
    checkElement.slideUp('normal');
  }
  if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
    $('#cssmenu ul ul:visible').slideUp('normal');
    checkElement.slideDown('normal');
  }
  if($(this).closest('li').find('ul').children().length == 0) {
    return true;
  } else {
    return false;	
  }		
});
});*/
function menujs(li_id){
	  var this_id = $("#"+li_id);
	  $('#cssmenu li').removeClass('active');
	  this_id.closest('li').addClass('active');	
	  var checkElement = this_id.next();
	  if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
		  this_id.closest('li').removeClass('active');
	    checkElement.slideUp('normal');
	  }
	  if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
	    $('#cssmenu ul ul:visible').slideUp('normal');
	    checkElement.slideDown('normal');
	  }
	  if(this_id.closest('li').find('ul').children().length == 0) {
	    return true;
	  } else {
	    return false;	
	  }
}