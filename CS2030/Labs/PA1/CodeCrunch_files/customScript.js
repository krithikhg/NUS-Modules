function enterPressButton(field, button) {
	$(field).keypress(function(e){if(e.which==13)$(button).trigger("click");});
}