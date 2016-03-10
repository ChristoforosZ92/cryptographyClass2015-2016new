<%@ page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page import="java.util.Random" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AnonymousVeto</title>
<script src="JS/jquery-2.1.3.js" type="text/javascript"></script>
<script src="JS/jquery-ui.js"  type="text/javascript"></script>
<script src="JS/bootstrap.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="CSS/jquery-ui.theme.css"/>
<link rel="stylesheet" type="text/css" href="CSS/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="CSS/jquery-ui.structure.css"/>
<link rel="stylesheet" type="text/css" href="CSS/bootstraps1.css"/>
</head>
<%response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate"); 
Random r = new Random();
int temp = r.nextInt(30);
if(temp == 0) temp = temp + 1;
long xi = (long) Math.pow(2, temp);
Cookie av_cookie = new Cookie("xi", Long.toString(xi));
av_cookie.setMaxAge(60*60*24);
response.addCookie(av_cookie);
%>

<script>
$(function(){
	$('#errorSelected').hide();
	function errorMessage( txt , elem1 , elem2 )
	{
		elem2.text(txt);
		elem1.show();
	}
	function checkSelected(opt){
		if(undefined != opt.val()) return true;
		errorMessage('Please select an option first!', $('#errorSelected'), $('#errS'));
		return false;
	}
	$(document).on('click', '#submitBtn', function(e)
	{
		$('#errorSelected').hide();
		var valid = true;
		valid = valid && checkSelected($('[name=options]:checked'));
		if(valid)
		{
			$.ajax(
			{
				url: 'SubmitVote',
				type: 'post',
				data: {opt:$('[name=options]:checked').val()},
				success: function(data)
				{
					$('#main').html( data );
				}
			});
	}
	return false;
	});
});
</script>

<body>

<div class="container-fluid">

<div id="main" class="panel panel-primary">

<div class="panel-heading">
    <h3 class="panel-title">Anonymous Veto Example</h3>
</div>
  
<div class="panel-body">

<form id="votingForm" class="form-horizontal">
<fieldset>
<legend>Do you want an unanimous yes or do you want to veto?press no if you want to veto the vote.</legend>

<div class="form-group">
	<label class="col-md-2 control-label">Options</label>
    <div class="col-md-10">
		<div class="radio">
        <label>
			<input type="radio" name="options" id="option1" value="1">
            Yes
        </label>
        </div>
       <div class="radio">
        <label>
			<input type="radio" name="options" id="option2" value="0">
            No
        </label>
        </div>
	</div>
</div>

<div class="form-group">
	<div id="errorSelected" class="alert alert-dismissible alert-danger col-md-3 col-md-offset-2">
  		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  		<span id="errS"></span>
	  </div>
</div>

<div class="form-group">
      <div class="col-md-8 col-md-offset-2">
        <button id="submitBtn" class="btn btn-primary">Vote</button>
      </div>
</div>
</fieldset>
</form>

</div>

</div>

<div class="navbar navbar-inverse navbar-static-bottom">
	<div class="navbar-header col-md-offset-5">
      <a class="navbar-brand" href="about.jsp">AnonymousVeto</a>
    </div>
</div>

</div>

</body>

</html>