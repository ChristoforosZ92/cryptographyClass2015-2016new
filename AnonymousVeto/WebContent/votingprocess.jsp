<% 
boolean valid = false;
if(null != request.getAttribute("isValid"))valid = Boolean.valueOf(request.getAttribute("isValid").toString());
if(valid){
%>
<div class="panel-heading">
    <h3 class="panel-title">Anonymous Veto Example</h3>
</div>
  
<div class="panel-body">
	<div class="alert alert-dismissible alert-success">
  		<h4>Thanks for voting!</h4>
	</div>
</div>
<%}
else{%>
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
  		<span id="errS">You did something wrong try voting again!</span>
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
<script>
$(function(){
	$('#errorSelected').show();
});
</script>
<%} %>