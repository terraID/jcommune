/*
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

var ErrorUtils = {};

/** Add necessary classes to page elements to highlight errors for current design */
ErrorUtils.fixErrorHighlighting = function() {
	$('.help-inline').closest('div.control-group').addClass('error');
}

/** Add required classes to highlight errors in specified input for current design 
 * @param inputOrSelector - either selector of input with errors or input itself 
 * 		(e.g. $('#inputId'))
 */
ErrorUtils.addErrorStyles = function(inputOrSelector) {
	var input = inputOrSelector;
	if (typeof(inputOrSelector) == "string") {
		input = $(inputOrSelector);
	}
	input.closest('div.control-group').addClass('error');
}

/** Remove error classes for specified input for current design 
 * @param inputOrSelector - either selector of input to disable error highlighting
 * 		or input itself (e.g. $('#inputId'))
 */
ErrorUtils.removeErrorStyles = function(inputOrSelector) {
	var input = inputOrSelector;
	if (typeof(inputOrSelector) == "string") {
		input = $(inputOrSelector);
	}
	input.closest('div.control-group').removeClass('error');
} 


/**
 * Make our design compatible with form:errors tag.
 */
$(document).ready(function() {
	ErrorUtils.fixErrorHighlighting();
});
