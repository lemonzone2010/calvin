(function($) {
	$.fn.swapClass = function(c1, c2) {
		return this.removeClass(c1).addClass(c2);
	}

	$.fn.switchClass = function(c1, c2) {
		if (this.hasClass(c1)) {
			return this.swapClass(c1, c2);
		} else {
			return this.swapClass(c2, c1);
		}
	}



})