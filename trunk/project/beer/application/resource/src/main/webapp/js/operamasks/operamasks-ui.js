/*!
 * jQuery UI 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI
 */
(function( $, undefined ) {

// prevent duplicate loading
// this is only a problem because we proxy existing functions
// and we don't want to double proxy them
$.ui = $.ui || {};
if ( $.ui.version ) {
	return;
}

$.extend( $.ui, {
	version: "1.8.15",

	keyCode: {
		ALT: 18,
		BACKSPACE: 8,
		CAPS_LOCK: 20,
		COMMA: 188,
		COMMAND: 91,
		COMMAND_LEFT: 91, // COMMAND
		COMMAND_RIGHT: 93,
		CONTROL: 17,
		DELETE: 46,
		DOWN: 40,
		END: 35,
		ENTER: 13,
		ESCAPE: 27,
		HOME: 36,
		INSERT: 45,
		LEFT: 37,
		MENU: 93, // COMMAND_RIGHT
		NUMPAD_ADD: 107,
		NUMPAD_DECIMAL: 110,
		NUMPAD_DIVIDE: 111,
		NUMPAD_ENTER: 108,
		NUMPAD_MULTIPLY: 106,
		NUMPAD_SUBTRACT: 109,
		PAGE_DOWN: 34,
		PAGE_UP: 33,
		PERIOD: 190,
		RIGHT: 39,
		SHIFT: 16,
		SPACE: 32,
		TAB: 9,
		UP: 38,
		WINDOWS: 91 // COMMAND
	}
});

// plugins
$.fn.extend({
	propAttr: $.fn.prop || $.fn.attr,

	_focus: $.fn.focus,
	focus: function( delay, fn ) {
		return typeof delay === "number" ?
			this.each(function() {
				var elem = this;
				setTimeout(function() {
					$( elem ).focus();
					if ( fn ) {
						fn.call( elem );
					}
				}, delay );
			}) :
			this._focus.apply( this, arguments );
	},

	scrollParent: function() {
		var scrollParent;
		if (($.browser.msie && (/(static|relative)/).test(this.css('position'))) || (/absolute/).test(this.css('position'))) {
			scrollParent = this.parents().filter(function() {
				return (/(relative|absolute|fixed)/).test($.curCSS(this,'position',1)) && (/(auto|scroll)/).test($.curCSS(this,'overflow',1)+$.curCSS(this,'overflow-y',1)+$.curCSS(this,'overflow-x',1));
			}).eq(0);
		} else {
			scrollParent = this.parents().filter(function() {
				return (/(auto|scroll)/).test($.curCSS(this,'overflow',1)+$.curCSS(this,'overflow-y',1)+$.curCSS(this,'overflow-x',1));
			}).eq(0);
		}

		return (/fixed/).test(this.css('position')) || !scrollParent.length ? $(document) : scrollParent;
	},

	zIndex: function( zIndex ) {
		if ( zIndex !== undefined ) {
			return this.css( "zIndex", zIndex );
		}

		if ( this.length ) {
			var elem = $( this[ 0 ] ), position, value;
			while ( elem.length && elem[ 0 ] !== document ) {
				// Ignore z-index if position is set to a value where z-index is ignored by the browser
				// This makes behavior of this function consistent across browsers
				// WebKit always returns auto if the element is positioned
				position = elem.css( "position" );
				if ( position === "absolute" || position === "relative" || position === "fixed" ) {
					// IE returns 0 when zIndex is not specified
					// other browsers return a string
					// we ignore the case of nested elements with an explicit value of 0
					// <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
					value = parseInt( elem.css( "zIndex" ), 10 );
					if ( !isNaN( value ) && value !== 0 ) {
						return value;
					}
				}
				elem = elem.parent();
			}
		}

		return 0;
	},

	disableSelection: function() {
		return this.bind( ( $.support.selectstart ? "selectstart" : "mousedown" ) +
			".ui-disableSelection", function( event ) {
				event.preventDefault();
			});
	},

	enableSelection: function() {
		return this.unbind( ".ui-disableSelection" );
	}
});

$.each( [ "Width", "Height" ], function( i, name ) {
	var side = name === "Width" ? [ "Left", "Right" ] : [ "Top", "Bottom" ],
		type = name.toLowerCase(),
		orig = {
			innerWidth: $.fn.innerWidth,
			innerHeight: $.fn.innerHeight,
			outerWidth: $.fn.outerWidth,
			outerHeight: $.fn.outerHeight
		};

	function reduce( elem, size, border, margin ) {
		$.each( side, function() {
			size -= parseFloat( $.curCSS( elem, "padding" + this, true) ) || 0;
			if ( border ) {
				size -= parseFloat( $.curCSS( elem, "border" + this + "Width", true) ) || 0;
			}
			if ( margin ) {
				size -= parseFloat( $.curCSS( elem, "margin" + this, true) ) || 0;
			}
		});
		return size;
	}

	$.fn[ "inner" + name ] = function( size ) {
		if ( size === undefined ) {
			return orig[ "inner" + name ].call( this );
		}

		return this.each(function() {
			$( this ).css( type, reduce( this, size ) + "px" );
		});
	};

	$.fn[ "outer" + name] = function( size, margin ) {
		if ( typeof size !== "number" ) {
			return orig[ "outer" + name ].call( this, size );
		}

		return this.each(function() {
			$( this).css( type, reduce( this, size, true, margin ) + "px" );
		});
	};
});

// selectors
function focusable( element, isTabIndexNotNaN ) {
	var nodeName = element.nodeName.toLowerCase();
	if ( "area" === nodeName ) {
		var map = element.parentNode,
			mapName = map.name,
			img;
		if ( !element.href || !mapName || map.nodeName.toLowerCase() !== "map" ) {
			return false;
		}
		img = $( "img[usemap=#" + mapName + "]" )[0];
		return !!img && visible( img );
	}
	return ( /input|select|textarea|button|object/.test( nodeName )
		? !element.disabled
		: "a" == nodeName
			? element.href || isTabIndexNotNaN
			: isTabIndexNotNaN)
		// the element and all of its ancestors must be visible
		&& visible( element );
}

function visible( element ) {
	return !$( element ).parents().andSelf().filter(function() {
		return $.curCSS( this, "visibility" ) === "hidden" ||
			$.expr.filters.hidden( this );
	}).length;
}

$.extend( $.expr[ ":" ], {
	data: function( elem, i, match ) {
		return !!$.data( elem, match[ 3 ] );
	},

	focusable: function( element ) {
		return focusable( element, !isNaN( $.attr( element, "tabindex" ) ) );
	},

	tabbable: function( element ) {
		var tabIndex = $.attr( element, "tabindex" ),
			isTabIndexNaN = isNaN( tabIndex );
		return ( isTabIndexNaN || tabIndex >= 0 ) && focusable( element, !isTabIndexNaN );
	}
});

// support
$(function() {
	var body = document.body,
		div = body.appendChild( div = document.createElement( "div" ) );

	$.extend( div.style, {
		minHeight: "100px",
		height: "auto",
		padding: 0,
		borderWidth: 0
	});

	$.support.minHeight = div.offsetHeight === 100;
	$.support.selectstart = "onselectstart" in div;

	// set display to none to avoid a layout bug in IE
	// http://dev.jquery.com/ticket/4014
	body.removeChild( div ).style.display = "none";
});





// deprecated
$.extend( $.ui, {
	// $.ui.plugin is deprecated.  Use the proxy pattern instead.
	plugin: {
		add: function( module, option, set ) {
			var proto = $.ui[ module ].prototype;
			for ( var i in set ) {
				proto.plugins[ i ] = proto.plugins[ i ] || [];
				proto.plugins[ i ].push( [ option, set[ i ] ] );
			}
		},
		call: function( instance, name, args ) {
			var set = instance.plugins[ name ];
			if ( !set || !instance.element[ 0 ].parentNode ) {
				return;
			}
	
			for ( var i = 0; i < set.length; i++ ) {
				if ( instance.options[ set[ i ][ 0 ] ] ) {
					set[ i ][ 1 ].apply( instance.element, args );
				}
			}
		}
	},
	
	// will be deprecated when we switch to jQuery 1.4 - use jQuery.contains()
	contains: function( a, b ) {
		return document.compareDocumentPosition ?
			a.compareDocumentPosition( b ) & 16 :
			a !== b && a.contains( b );
	},
	
	// only used by resizable
	hasScroll: function( el, a ) {
	
		//If overflow is hidden, the element might have extra content, but the user wants to hide it
		if ( $( el ).css( "overflow" ) === "hidden") {
			return false;
		}
	
		var scroll = ( a && a === "left" ) ? "scrollLeft" : "scrollTop",
			has = false;
	
		if ( el[ scroll ] > 0 ) {
			return true;
		}
	
		// TODO: determine which cases actually cause this to happen
		// if the element doesn't have the scroll set, see if it's possible to
		// set the scroll
		el[ scroll ] = 1;
		has = ( el[ scroll ] > 0 );
		el[ scroll ] = 0;
		return has;
	},
	
	// these are odd functions, fix the API or move into individual plugins
	isOverAxis: function( x, reference, size ) {
		//Determines when x coordinate is over "b" element axis
		return ( x > reference ) && ( x < ( reference + size ) );
	},
	isOver: function( y, x, top, left, height, width ) {
		//Determines when x, y coordinates is over "b" element
		return $.ui.isOverAxis( y, top, height ) && $.ui.isOverAxis( x, left, width );
	}
});

})( jQuery );
/*!
 * jQuery UI Widget 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Widget
 */
(function( $, undefined ) {

// jQuery 1.4+
if ( $.cleanData ) {
	var _cleanData = $.cleanData;
	$.cleanData = function( elems ) {
		for ( var i = 0, elem; (elem = elems[i]) != null; i++ ) {
			$( elem ).triggerHandler( "remove" );
		}
		_cleanData( elems );
	};
} else {
	var _remove = $.fn.remove;
	$.fn.remove = function( selector, keepData ) {
		return this.each(function() {
			if ( !keepData ) {
				if ( !selector || $.filter( selector, [ this ] ).length ) {
					$( "*", this ).add( [ this ] ).each(function() {
						$( this ).triggerHandler( "remove" );
					});
				}
			}
			return _remove.call( $(this), selector, keepData );
		});
	};
}

$.widget = function( name, base, prototype ) {
	var namespace = name.split( "." )[ 0 ],
		fullName;
	name = name.split( "." )[ 1 ];
	fullName = namespace + "-" + name;

	if ( !prototype ) {
		prototype = base;
		base = $.Widget;
	}

	// create selector for plugin
	$.expr[ ":" ][ fullName ] = function( elem ) {
		return !!$.data( elem, name );
	};

	$[ namespace ] = $[ namespace ] || {};
	$[ namespace ][ name ] = function( options, element ) {
		// allow instantiation without initializing for simple inheritance
		if ( arguments.length ) {
			this._createWidget( options, element );
		}
	};

	var basePrototype = new base();
	// we need to make the options hash a property directly on the new instance
	// otherwise we'll modify the options hash on the prototype that we're
	// inheriting from
//	$.each( basePrototype, function( key, val ) {
//		if ( $.isPlainObject(val) ) {
//			basePrototype[ key ] = $.extend( {}, val );
//		}
//	});
	basePrototype.options = $.extend( true, {}, basePrototype.options );
	$[ namespace ][ name ].prototype = $.extend( true, basePrototype, {
		namespace: namespace,
		widgetName: name,
		widgetEventPrefix: $[ namespace ][ name ].prototype.widgetEventPrefix || name,
		widgetBaseClass: fullName
	}, prototype );

	$.widget.bridge( name, $[ namespace ][ name ] );
};

$.widget.bridge = function( name, object ) {
	$.fn[ name ] = function( options ) {
		var isMethodCall = typeof options === "string",
			args = Array.prototype.slice.call( arguments, 1 ),
			returnValue = this;

		// allow multiple hashes to be passed on init
		options = !isMethodCall && args.length ?
			$.extend.apply( null, [ true, options ].concat(args) ) :
			options;

		// prevent calls to internal methods
		if ( isMethodCall && options.charAt( 0 ) === "_" ) {
			return returnValue;
		}

		if ( isMethodCall ) {
			this.each(function() {
				var instance = $.data( this, name ),
					methodValue = instance && $.isFunction( instance[options] ) ?
						instance[ options ].apply( instance, args ) :
						instance;
				// TODO: add this back in 1.9 and use $.error() (see #5972)
//				if ( !instance ) {
//					throw "cannot call methods on " + name + " prior to initialization; " +
//						"attempted to call method '" + options + "'";
//				}
//				if ( !$.isFunction( instance[options] ) ) {
//					throw "no such method '" + options + "' for " + name + " widget instance";
//				}
//				var methodValue = instance[ options ].apply( instance, args );
				if ( methodValue !== instance && methodValue !== undefined ) {
					returnValue = methodValue;
					return false;
				}
			});
		} else {
			this.each(function() {
				var instance = $.data( this, name );
				if ( instance ) {
					instance.option( options || {} )._init();
				} else {
					$.data( this, name, new object( options, this ) );
				}
			});
		}

		return returnValue;
	};
};

$.Widget = function( options, element ) {
	// allow instantiation without initializing for simple inheritance
	if ( arguments.length ) {
		this._createWidget( options, element );
	}
};

$.Widget.prototype = {
	widgetName: "widget",
	widgetEventPrefix: "",
	options: {
		disabled: false
	},
	_createWidget: function( options, element ) {
		// $.widget.bridge stores the plugin instance, but we do it anyway
		// so that it's stored even before the _create function runs
		$.data( element, this.widgetName, this );
		this.element = $( element );
		this.options = $.extend( true, {},
			this.options,
			this._getCreateOptions(),
			options );

		var self = this;
		this.element.bind( "remove." + this.widgetName, function() {
			self.destroy();
		});

		this._create();
		this._trigger( "create" );
		this._init();
	},
	_getCreateOptions: function() {
		return $.metadata && $.metadata.get( this.element[0] )[ this.widgetName ];
	},
	_create: function() {},
	_init: function() {},

	destroy: function() {
		this.element
			.unbind( "." + this.widgetName )
			.removeData( this.widgetName );
		this.widget()
			.unbind( "." + this.widgetName )
			.removeAttr( "aria-disabled" )
			.removeClass(
				this.widgetBaseClass + "-disabled " +
				"ui-state-disabled" );
	},

	widget: function() {
		return this.element;
	},

	option: function( key, value ) {
		var options = key;

		if ( arguments.length === 0 ) {
			// don't return a reference to the internal hash
			return $.extend( {}, this.options );
		}

		if  (typeof key === "string" ) {
			if ( value === undefined ) {
				return this.options[ key ];
			}
			options = {};
			options[ key ] = value;
		}

		this._setOptions( options );

		return this;
	},
	_setOptions: function( options ) {
		var self = this;
		$.each( options, function( key, value ) {
			self._setOption( key, value );
		});

		return this;
	},
	_setOption: function( key, value ) {
		this.options[ key ] = value;

		if ( key === "disabled" ) {
			this.widget()
				[ value ? "addClass" : "removeClass"](
					this.widgetBaseClass + "-disabled" + " " +
					"ui-state-disabled" )
				.attr( "aria-disabled", value );
		}

		return this;
	},

	enable: function() {
		return this._setOption( "disabled", false );
	},
	disable: function() {
		return this._setOption( "disabled", true );
	},

	_trigger: function( type, event, data ) {
		var callback = this.options[ type ];

		event = $.Event( event );
		event.type = ( type === this.widgetEventPrefix ?
			type :
			this.widgetEventPrefix + type ).toLowerCase();
		data = data || {};

		// copy original event properties over to the new event
		// this would happen if we could call $.event.fix instead of $.Event
		// but we don't have a way to force an event to be fixed multiple times
		if ( event.originalEvent ) {
			for ( var i = $.event.props.length, prop; i; ) {
				prop = $.event.props[ --i ];
				event[ prop ] = event.originalEvent[ prop ];
			}
		}

		this.element.trigger( event, data );

		return !( $.isFunction(callback) &&
			callback.call( this.element[0], event, data ) === false ||
			event.isDefaultPrevented() );
	}
};

})( jQuery );
/*!
 * jQuery UI Mouse 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Mouse
 *
 * Depends:
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

$.widget("ui.mouse", {
	options: {
		cancel: ':input,option',
		distance: 1,
		delay: 0
	},
	_mouseInit: function() {
		var self = this;

		this.element
			.bind('mousedown.'+this.widgetName, function(event) {
				return self._mouseDown(event);
			})
			.bind('click.'+this.widgetName, function(event) {
				if (true === $.data(event.target, self.widgetName + '.preventClickEvent')) {
				    $.removeData(event.target, self.widgetName + '.preventClickEvent');
					event.stopImmediatePropagation();
					return false;
				}
			});

		this.started = false;
	},

	// TODO: make sure destroying one instance of mouse doesn't mess with
	// other instances of mouse
	_mouseDestroy: function() {
		this.element.unbind('.'+this.widgetName);
	},

	_mouseDown: function(event) {
		// don't let more than one widget handle mouseStart
		// TODO: figure out why we have to use originalEvent
		event.originalEvent = event.originalEvent || {};
		if (event.originalEvent.mouseHandled) { return; }

		// we may have missed mouseup (out of window)
		(this._mouseStarted && this._mouseUp(event));

		this._mouseDownEvent = event;

		var self = this,
			btnIsLeft = (event.which == 1),
			elIsCancel = (typeof this.options.cancel == "string" ? $(event.target).closest(this.options.cancel).length : false);
		if (!btnIsLeft || elIsCancel || !this._mouseCapture(event)) {
			return true;
		}

		this.mouseDelayMet = !this.options.delay;
		if (!this.mouseDelayMet) {
			this._mouseDelayTimer = setTimeout(function() {
				self.mouseDelayMet = true;
			}, this.options.delay);
		}

		if (this._mouseDistanceMet(event) && this._mouseDelayMet(event)) {
			this._mouseStarted = (this._mouseStart(event) !== false);
			if (!this._mouseStarted) {
				event.preventDefault();
				return true;
			}
		}

		// Click event may never have fired (Gecko & Opera)
		if (true === $.data(event.target, this.widgetName + '.preventClickEvent')) {
			$.removeData(event.target, this.widgetName + '.preventClickEvent');
		}

		// these delegates are required to keep context
		this._mouseMoveDelegate = function(event) {
			return self._mouseMove(event);
		};
		this._mouseUpDelegate = function(event) {
			return self._mouseUp(event);
		};
		$(document)
			.bind('mousemove.'+this.widgetName, this._mouseMoveDelegate)
			.bind('mouseup.'+this.widgetName, this._mouseUpDelegate);

		event.preventDefault();
		event.originalEvent.mouseHandled = true;
		return true;
	},

	_mouseMove: function(event) {
		// IE mouseup check - mouseup happened when mouse was out of window
		if ($.browser.msie && !(document.documentMode >= 9) && !event.button) {
			return this._mouseUp(event);
		}

		if (this._mouseStarted) {
			this._mouseDrag(event);
			return event.preventDefault();
		}

		if (this._mouseDistanceMet(event) && this._mouseDelayMet(event)) {
			this._mouseStarted =
				(this._mouseStart(this._mouseDownEvent, event) !== false);
			(this._mouseStarted ? this._mouseDrag(event) : this._mouseUp(event));
		}

		return !this._mouseStarted;
	},

	_mouseUp: function(event) {
		$(document)
			.unbind('mousemove.'+this.widgetName, this._mouseMoveDelegate)
			.unbind('mouseup.'+this.widgetName, this._mouseUpDelegate);

		if (this._mouseStarted) {
			this._mouseStarted = false;

			if (event.target == this._mouseDownEvent.target) {
			    $.data(event.target, this.widgetName + '.preventClickEvent', true);
			}

			this._mouseStop(event);
		}

		return false;
	},

	_mouseDistanceMet: function(event) {
		return (Math.max(
				Math.abs(this._mouseDownEvent.pageX - event.pageX),
				Math.abs(this._mouseDownEvent.pageY - event.pageY)
			) >= this.options.distance
		);
	},

	_mouseDelayMet: function(event) {
		return this.mouseDelayMet;
	},

	// These are placeholder methods, to be overriden by extending plugin
	_mouseStart: function(event) {},
	_mouseDrag: function(event) {},
	_mouseStop: function(event) {},
	_mouseCapture: function(event) { return true; }
});

})(jQuery);
/*
 * jQuery UI Position 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Position
 */
(function( $, undefined ) {

$.ui = $.ui || {};

var horizontalPositions = /left|center|right/,
	verticalPositions = /top|center|bottom/,
	center = "center",
	_position = $.fn.position,
	_offset = $.fn.offset;

$.fn.position = function( options ) {
	if ( !options || !options.of ) {
		return _position.apply( this, arguments );
	}

	// make a copy, we don't want to modify arguments
	options = $.extend( {}, options );

	var target = $( options.of ),
		targetElem = target[0],
		collision = ( options.collision || "flip" ).split( " " ),
		offset = options.offset ? options.offset.split( " " ) : [ 0, 0 ],
		targetWidth,
		targetHeight,
		basePosition;

	if ( targetElem.nodeType === 9 ) {
		targetWidth = target.width();
		targetHeight = target.height();
		basePosition = { top: 0, left: 0 };
	// TODO: use $.isWindow() in 1.9
	} else if ( targetElem.setTimeout ) {
		targetWidth = target.width();
		targetHeight = target.height();
		basePosition = { top: target.scrollTop(), left: target.scrollLeft() };
	} else if ( targetElem.preventDefault ) {
		// force left top to allow flipping
		options.at = "left top";
		targetWidth = targetHeight = 0;
		basePosition = { top: options.of.pageY, left: options.of.pageX };
	} else {
		targetWidth = target.outerWidth();
		targetHeight = target.outerHeight();
		basePosition = target.offset();
	}

	// force my and at to have valid horizontal and veritcal positions
	// if a value is missing or invalid, it will be converted to center 
	$.each( [ "my", "at" ], function() {
		var pos = ( options[this] || "" ).split( " " );
		if ( pos.length === 1) {
			pos = horizontalPositions.test( pos[0] ) ?
				pos.concat( [center] ) :
				verticalPositions.test( pos[0] ) ?
					[ center ].concat( pos ) :
					[ center, center ];
		}
		pos[ 0 ] = horizontalPositions.test( pos[0] ) ? pos[ 0 ] : center;
		pos[ 1 ] = verticalPositions.test( pos[1] ) ? pos[ 1 ] : center;
		options[ this ] = pos;
	});

	// normalize collision option
	if ( collision.length === 1 ) {
		collision[ 1 ] = collision[ 0 ];
	}

	// normalize offset option
	offset[ 0 ] = parseInt( offset[0], 10 ) || 0;
	if ( offset.length === 1 ) {
		offset[ 1 ] = offset[ 0 ];
	}
	offset[ 1 ] = parseInt( offset[1], 10 ) || 0;

	if ( options.at[0] === "right" ) {
		basePosition.left += targetWidth;
	} else if ( options.at[0] === center ) {
		basePosition.left += targetWidth / 2;
	}

	if ( options.at[1] === "bottom" ) {
		basePosition.top += targetHeight;
	} else if ( options.at[1] === center ) {
		basePosition.top += targetHeight / 2;
	}

	basePosition.left += offset[ 0 ];
	basePosition.top += offset[ 1 ];

	return this.each(function() {
		var elem = $( this ),
			elemWidth = elem.outerWidth(),
			elemHeight = elem.outerHeight(),
			marginLeft = parseInt( $.curCSS( this, "marginLeft", true ) ) || 0,
			marginTop = parseInt( $.curCSS( this, "marginTop", true ) ) || 0,
			collisionWidth = elemWidth + marginLeft +
				( parseInt( $.curCSS( this, "marginRight", true ) ) || 0 ),
			collisionHeight = elemHeight + marginTop +
				( parseInt( $.curCSS( this, "marginBottom", true ) ) || 0 ),
			position = $.extend( {}, basePosition ),
			collisionPosition;

		if ( options.my[0] === "right" ) {
			position.left -= elemWidth;
		} else if ( options.my[0] === center ) {
			position.left -= elemWidth / 2;
		}

		if ( options.my[1] === "bottom" ) {
			position.top -= elemHeight;
		} else if ( options.my[1] === center ) {
			position.top -= elemHeight / 2;
		}

		// prevent fractions (see #5280)
		position.left = Math.round( position.left );
		position.top = Math.round( position.top );

		collisionPosition = {
			left: position.left - marginLeft,
			top: position.top - marginTop
		};

		$.each( [ "left", "top" ], function( i, dir ) {
			if ( $.ui.position[ collision[i] ] ) {
				$.ui.position[ collision[i] ][ dir ]( position, {
					targetWidth: targetWidth,
					targetHeight: targetHeight,
					elemWidth: elemWidth,
					elemHeight: elemHeight,
					collisionPosition: collisionPosition,
					collisionWidth: collisionWidth,
					collisionHeight: collisionHeight,
					offset: offset,
					my: options.my,
					at: options.at
				});
			}
		});

		if ( $.fn.bgiframe ) {
			elem.bgiframe();
		}
		elem.offset( $.extend( position, { using: options.using } ) );
	});
};

$.ui.position = {
	fit: {
		left: function( position, data ) {
			var win = $( window ),
				over = data.collisionPosition.left + data.collisionWidth - win.width() - win.scrollLeft();
			position.left = over > 0 ? position.left - over : Math.max( position.left - data.collisionPosition.left, position.left );
		},
		top: function( position, data ) {
			var win = $( window ),
				over = data.collisionPosition.top + data.collisionHeight - win.height() - win.scrollTop();
			position.top = over > 0 ? position.top - over : Math.max( position.top - data.collisionPosition.top, position.top );
		}
	},

	flip: {
		left: function( position, data ) {
			if ( data.at[0] === center ) {
				return;
			}
			var win = $( window ),
				over = data.collisionPosition.left + data.collisionWidth - win.width() - win.scrollLeft(),
				myOffset = data.my[ 0 ] === "left" ?
					-data.elemWidth :
					data.my[ 0 ] === "right" ?
						data.elemWidth :
						0,
				atOffset = data.at[ 0 ] === "left" ?
					data.targetWidth :
					-data.targetWidth,
				offset = -2 * data.offset[ 0 ];
			position.left += data.collisionPosition.left < 0 ?
				myOffset + atOffset + offset :
				over > 0 ?
					myOffset + atOffset + offset :
					0;
		},
		top: function( position, data ) {
			if ( data.at[1] === center ) {
				return;
			}
			var win = $( window ),
				over = data.collisionPosition.top + data.collisionHeight - win.height() - win.scrollTop(),
				myOffset = data.my[ 1 ] === "top" ?
					-data.elemHeight :
					data.my[ 1 ] === "bottom" ?
						data.elemHeight :
						0,
				atOffset = data.at[ 1 ] === "top" ?
					data.targetHeight :
					-data.targetHeight,
				offset = -2 * data.offset[ 1 ];
			position.top += data.collisionPosition.top < 0 ?
				myOffset + atOffset + offset :
				over > 0 ?
					myOffset + atOffset + offset :
					0;
		}
	}
};

// offset setter from jQuery 1.4
if ( !$.offset.setOffset ) {
	$.offset.setOffset = function( elem, options ) {
		// set position first, in-case top/left are set even on static elem
		if ( /static/.test( $.curCSS( elem, "position" ) ) ) {
			elem.style.position = "relative";
		}
		var curElem   = $( elem ),
			curOffset = curElem.offset(),
			curTop    = parseInt( $.curCSS( elem, "top",  true ), 10 ) || 0,
			curLeft   = parseInt( $.curCSS( elem, "left", true ), 10)  || 0,
			props     = {
				top:  (options.top  - curOffset.top)  + curTop,
				left: (options.left - curOffset.left) + curLeft
			};
		
		if ( 'using' in options ) {
			options.using.call( elem, props );
		} else {
			curElem.css( props );
		}
	};

	$.fn.offset = function( options ) {
		var elem = this[ 0 ];
		if ( !elem || !elem.ownerDocument ) { return null; }
		if ( options ) { 
			return this.each(function() {
				$.offset.setOffset( this, options );
			});
		}
		return _offset.call( this );
	};
}

}( jQuery ));
/*
 * jQuery UI Draggable 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Draggables
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.mouse.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

$.widget("ui.draggable", $.ui.mouse, {
	widgetEventPrefix: "drag",
	options: {
		addClasses: true,
		appendTo: "parent",
		axis: false,
		connectToSortable: false,
		containment: false,
		cursor: "auto",
		cursorAt: false,
		grid: false,
		handle: false,
		helper: "original",
		iframeFix: false,
		opacity: false,
		refreshPositions: false,
		revert: false,
		revertDuration: 500,
		scope: "default",
		scroll: true,
		scrollSensitivity: 20,
		scrollSpeed: 20,
		snap: false,
		snapMode: "both",
		snapTolerance: 20,
		stack: false,
		zIndex: false
	},
	_create: function() {

		if (this.options.helper == 'original' && !(/^(?:r|a|f)/).test(this.element.css("position")))
			this.element[0].style.position = 'relative';

		(this.options.addClasses && this.element.addClass("ui-draggable"));
		(this.options.disabled && this.element.addClass("ui-draggable-disabled"));

		this._mouseInit();

	},

	destroy: function() {
		if(!this.element.data('draggable')) return;
		this.element
			.removeData("draggable")
			.unbind(".draggable")
			.removeClass("ui-draggable"
				+ " ui-draggable-dragging"
				+ " ui-draggable-disabled");
		this._mouseDestroy();

		return this;
	},

	_mouseCapture: function(event) {

		var o = this.options;

		// among others, prevent a drag on a resizable-handle
		if (this.helper || o.disabled || $(event.target).is('.ui-resizable-handle'))
			return false;

		//Quit if we're not on a valid handle
		this.handle = this._getHandle(event);
		if (!this.handle)
			return false;
		
		$(o.iframeFix === true ? "iframe" : o.iframeFix).each(function() {
			$('<div class="ui-draggable-iframeFix" style="background: #fff;"></div>')
			.css({
				width: this.offsetWidth+"px", height: this.offsetHeight+"px",
				position: "absolute", opacity: "0.001", zIndex: 1000
			})
			.css($(this).offset())
			.appendTo("body");
		});

		return true;

	},

	_mouseStart: function(event) {

		var o = this.options;

		//Create and append the visible helper
		this.helper = this._createHelper(event);

		//Cache the helper size
		this._cacheHelperProportions();

		//If ddmanager is used for droppables, set the global draggable
		if($.ui.ddmanager)
			$.ui.ddmanager.current = this;

		/*
		 * - Position generation -
		 * This block generates everything position related - it's the core of draggables.
		 */

		//Cache the margins of the original element
		this._cacheMargins();

		//Store the helper's css position
		this.cssPosition = this.helper.css("position");
		this.scrollParent = this.helper.scrollParent();

		//The element's absolute position on the page minus margins
		this.offset = this.positionAbs = this.element.offset();
		this.offset = {
			top: this.offset.top - this.margins.top,
			left: this.offset.left - this.margins.left
		};

		$.extend(this.offset, {
			click: { //Where the click happened, relative to the element
				left: event.pageX - this.offset.left,
				top: event.pageY - this.offset.top
			},
			parent: this._getParentOffset(),
			relative: this._getRelativeOffset() //This is a relative to absolute position minus the actual position calculation - only used for relative positioned helper
		});

		//Generate the original position
		this.originalPosition = this.position = this._generatePosition(event);
		this.originalPageX = event.pageX;
		this.originalPageY = event.pageY;

		//Adjust the mouse offset relative to the helper if 'cursorAt' is supplied
		(o.cursorAt && this._adjustOffsetFromHelper(o.cursorAt));

		//Set a containment if given in the options
		if(o.containment)
			this._setContainment();

		//Trigger event + callbacks
		if(this._trigger("start", event) === false) {
			this._clear();
			return false;
		}

		//Recache the helper size
		this._cacheHelperProportions();

		//Prepare the droppable offsets
		if ($.ui.ddmanager && !o.dropBehaviour)
			$.ui.ddmanager.prepareOffsets(this, event);

		this.helper.addClass("ui-draggable-dragging");
		this._mouseDrag(event, true); //Execute the drag once - this causes the helper not to be visible before getting its correct position
		
		//If the ddmanager is used for droppables, inform the manager that dragging has started (see #5003)
		if ( $.ui.ddmanager ) $.ui.ddmanager.dragStart(this, event);
		
		return true;
	},

	_mouseDrag: function(event, noPropagation) {

		//Compute the helpers position
		this.position = this._generatePosition(event);
		this.positionAbs = this._convertPositionTo("absolute");

		//Call plugins and callbacks and use the resulting position if something is returned
		if (!noPropagation) {
			var ui = this._uiHash();
			if(this._trigger('drag', event, ui) === false) {
				this._mouseUp({});
				return false;
			}
			this.position = ui.position;
		}

		if(!this.options.axis || this.options.axis != "y") this.helper[0].style.left = this.position.left+'px';
		if(!this.options.axis || this.options.axis != "x") this.helper[0].style.top = this.position.top+'px';
		if($.ui.ddmanager) $.ui.ddmanager.drag(this, event);

		return false;
	},

	_mouseStop: function(event) {

		//If we are using droppables, inform the manager about the drop
		var dropped = false;
		if ($.ui.ddmanager && !this.options.dropBehaviour)
			dropped = $.ui.ddmanager.drop(this, event);

		//if a drop comes from outside (a sortable)
		if(this.dropped) {
			dropped = this.dropped;
			this.dropped = false;
		}
		
		//if the original element is removed, don't bother to continue if helper is set to "original"
		if((!this.element[0] || !this.element[0].parentNode) && this.options.helper == "original")
			return false;

		if((this.options.revert == "invalid" && !dropped) || (this.options.revert == "valid" && dropped) || this.options.revert === true || ($.isFunction(this.options.revert) && this.options.revert.call(this.element, dropped))) {
			var self = this;
			$(this.helper).animate(this.originalPosition, parseInt(this.options.revertDuration, 10), function() {
				if(self._trigger("stop", event) !== false) {
					self._clear();
				}
			});
		} else {
			if(this._trigger("stop", event) !== false) {
				this._clear();
			}
		}

		return false;
	},
	
	_mouseUp: function(event) {
		if (this.options.iframeFix === true) {
			$("div.ui-draggable-iframeFix").each(function() { 
				this.parentNode.removeChild(this); 
			}); //Remove frame helpers
		}
		
		//If the ddmanager is used for droppables, inform the manager that dragging has stopped (see #5003)
		if( $.ui.ddmanager ) $.ui.ddmanager.dragStop(this, event);
		
		return $.ui.mouse.prototype._mouseUp.call(this, event);
	},
	
	cancel: function() {
		
		if(this.helper.is(".ui-draggable-dragging")) {
			this._mouseUp({});
		} else {
			this._clear();
		}
		
		return this;
		
	},

	_getHandle: function(event) {

		var handle = !this.options.handle || !$(this.options.handle, this.element).length ? true : false;
		$(this.options.handle, this.element)
			.find("*")
			.andSelf()
			.each(function() {
				if(this == event.target) handle = true;
			});

		return handle;

	},

	_createHelper: function(event) {

		var o = this.options;
		var helper = $.isFunction(o.helper) ? $(o.helper.apply(this.element[0], [event])) : (o.helper == 'clone' ? this.element.clone().removeAttr('id') : this.element);

		if(!helper.parents('body').length)
			helper.appendTo((o.appendTo == 'parent' ? this.element[0].parentNode : o.appendTo));

		if(helper[0] != this.element[0] && !(/(fixed|absolute)/).test(helper.css("position")))
			helper.css("position", "absolute");

		return helper;

	},

	_adjustOffsetFromHelper: function(obj) {
		if (typeof obj == 'string') {
			obj = obj.split(' ');
		}
		if ($.isArray(obj)) {
			obj = {left: +obj[0], top: +obj[1] || 0};
		}
		if ('left' in obj) {
			this.offset.click.left = obj.left + this.margins.left;
		}
		if ('right' in obj) {
			this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
		}
		if ('top' in obj) {
			this.offset.click.top = obj.top + this.margins.top;
		}
		if ('bottom' in obj) {
			this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
		}
	},

	_getParentOffset: function() {

		//Get the offsetParent and cache its position
		this.offsetParent = this.helper.offsetParent();
		var po = this.offsetParent.offset();

		// This is a special case where we need to modify a offset calculated on start, since the following happened:
		// 1. The position of the helper is absolute, so it's position is calculated based on the next positioned parent
		// 2. The actual offset parent is a child of the scroll parent, and the scroll parent isn't the document, which means that
		//    the scroll is included in the initial calculation of the offset of the parent, and never recalculated upon drag
		if(this.cssPosition == 'absolute' && this.scrollParent[0] != document && $.ui.contains(this.scrollParent[0], this.offsetParent[0])) {
			po.left += this.scrollParent.scrollLeft();
			po.top += this.scrollParent.scrollTop();
		}

		if((this.offsetParent[0] == document.body) //This needs to be actually done for all browsers, since pageX/pageY includes this information
		|| (this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() == 'html' && $.browser.msie)) //Ugly IE fix
			po = { top: 0, left: 0 };

		return {
			top: po.top + (parseInt(this.offsetParent.css("borderTopWidth"),10) || 0),
			left: po.left + (parseInt(this.offsetParent.css("borderLeftWidth"),10) || 0)
		};

	},

	_getRelativeOffset: function() {

		if(this.cssPosition == "relative") {
			var p = this.element.position();
			return {
				top: p.top - (parseInt(this.helper.css("top"),10) || 0) + this.scrollParent.scrollTop(),
				left: p.left - (parseInt(this.helper.css("left"),10) || 0) + this.scrollParent.scrollLeft()
			};
		} else {
			return { top: 0, left: 0 };
		}

	},

	_cacheMargins: function() {
		this.margins = {
			left: (parseInt(this.element.css("marginLeft"),10) || 0),
			top: (parseInt(this.element.css("marginTop"),10) || 0),
			right: (parseInt(this.element.css("marginRight"),10) || 0),
			bottom: (parseInt(this.element.css("marginBottom"),10) || 0)
		};
	},

	_cacheHelperProportions: function() {
		this.helperProportions = {
			width: this.helper.outerWidth(),
			height: this.helper.outerHeight()
		};
	},

	_setContainment: function() {

		var o = this.options;
		if(o.containment == 'parent') o.containment = this.helper[0].parentNode;
		if(o.containment == 'document' || o.containment == 'window') this.containment = [
			o.containment == 'document' ? 0 : $(window).scrollLeft() - this.offset.relative.left - this.offset.parent.left,
			o.containment == 'document' ? 0 : $(window).scrollTop() - this.offset.relative.top - this.offset.parent.top,
			(o.containment == 'document' ? 0 : $(window).scrollLeft()) + $(o.containment == 'document' ? document : window).width() - this.helperProportions.width - this.margins.left,
			(o.containment == 'document' ? 0 : $(window).scrollTop()) + ($(o.containment == 'document' ? document : window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top
		];

		if(!(/^(document|window|parent)$/).test(o.containment) && o.containment.constructor != Array) {
		        var c = $(o.containment);
			var ce = c[0]; if(!ce) return;
			var co = c.offset();
			var over = ($(ce).css("overflow") != 'hidden');

			this.containment = [
				(parseInt($(ce).css("borderLeftWidth"),10) || 0) + (parseInt($(ce).css("paddingLeft"),10) || 0),
				(parseInt($(ce).css("borderTopWidth"),10) || 0) + (parseInt($(ce).css("paddingTop"),10) || 0),
				(over ? Math.max(ce.scrollWidth,ce.offsetWidth) : ce.offsetWidth) - (parseInt($(ce).css("borderLeftWidth"),10) || 0) - (parseInt($(ce).css("paddingRight"),10) || 0) - this.helperProportions.width - this.margins.left - this.margins.right,
				(over ? Math.max(ce.scrollHeight,ce.offsetHeight) : ce.offsetHeight) - (parseInt($(ce).css("borderTopWidth"),10) || 0) - (parseInt($(ce).css("paddingBottom"),10) || 0) - this.helperProportions.height - this.margins.top  - this.margins.bottom
			];
			this.relative_container = c;

		} else if(o.containment.constructor == Array) {
			this.containment = o.containment;
		}

	},

	_convertPositionTo: function(d, pos) {

		if(!pos) pos = this.position;
		var mod = d == "absolute" ? 1 : -1;
		var o = this.options, scroll = this.cssPosition == 'absolute' && !(this.scrollParent[0] != document && $.ui.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);

		return {
			top: (
				pos.top																	// The absolute mouse position
				+ this.offset.relative.top * mod										// Only for relative positioned nodes: Relative offset from element to offset parent
				+ this.offset.parent.top * mod											// The offsetParent's offset without borders (offset + border)
				- ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ) * mod)
			),
			left: (
				pos.left																// The absolute mouse position
				+ this.offset.relative.left * mod										// Only for relative positioned nodes: Relative offset from element to offset parent
				+ this.offset.parent.left * mod											// The offsetParent's offset without borders (offset + border)
				- ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ) * mod)
			)
		};

	},

	_generatePosition: function(event) {

		var o = this.options, scroll = this.cssPosition == 'absolute' && !(this.scrollParent[0] != document && $.ui.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);
		var pageX = event.pageX;
		var pageY = event.pageY;

		/*
		 * - Position constraining -
		 * Constrain the position to a mix of grid, containment.
		 */

		if(this.originalPosition) { //If we are not dragging yet, we won't check for options
		         var containment;
		         if(this.containment) {
				 if (this.relative_container){
				     var co = this.relative_container.offset();
				     containment = [ this.containment[0] + co.left,
						     this.containment[1] + co.top,
						     this.containment[2] + co.left,
						     this.containment[3] + co.top ];
				 }
				 else {
				     containment = this.containment;
				 }

				if(event.pageX - this.offset.click.left < containment[0]) pageX = containment[0] + this.offset.click.left;
				if(event.pageY - this.offset.click.top < containment[1]) pageY = containment[1] + this.offset.click.top;
				if(event.pageX - this.offset.click.left > containment[2]) pageX = containment[2] + this.offset.click.left;
				if(event.pageY - this.offset.click.top > containment[3]) pageY = containment[3] + this.offset.click.top;
			}

			if(o.grid) {
				//Check for grid elements set to 0 to prevent divide by 0 error causing invalid argument errors in IE (see ticket #6950)
				var top = o.grid[1] ? this.originalPageY + Math.round((pageY - this.originalPageY) / o.grid[1]) * o.grid[1] : this.originalPageY;
				pageY = containment ? (!(top - this.offset.click.top < containment[1] || top - this.offset.click.top > containment[3]) ? top : (!(top - this.offset.click.top < containment[1]) ? top - o.grid[1] : top + o.grid[1])) : top;

				var left = o.grid[0] ? this.originalPageX + Math.round((pageX - this.originalPageX) / o.grid[0]) * o.grid[0] : this.originalPageX;
				pageX = containment ? (!(left - this.offset.click.left < containment[0] || left - this.offset.click.left > containment[2]) ? left : (!(left - this.offset.click.left < containment[0]) ? left - o.grid[0] : left + o.grid[0])) : left;
			}

		}

		return {
			top: (
				pageY																// The absolute mouse position
				- this.offset.click.top													// Click offset (relative to the element)
				- this.offset.relative.top												// Only for relative positioned nodes: Relative offset from element to offset parent
				- this.offset.parent.top												// The offsetParent's offset without borders (offset + border)
				+ ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ))
			),
			left: (
				pageX																// The absolute mouse position
				- this.offset.click.left												// Click offset (relative to the element)
				- this.offset.relative.left												// Only for relative positioned nodes: Relative offset from element to offset parent
				- this.offset.parent.left												// The offsetParent's offset without borders (offset + border)
				+ ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ))
			)
		};

	},

	_clear: function() {
		this.helper.removeClass("ui-draggable-dragging");
		if(this.helper[0] != this.element[0] && !this.cancelHelperRemoval) this.helper.remove();
		//if($.ui.ddmanager) $.ui.ddmanager.current = null;
		this.helper = null;
		this.cancelHelperRemoval = false;
	},

	// From now on bulk stuff - mainly helpers

	_trigger: function(type, event, ui) {
		ui = ui || this._uiHash();
		$.ui.plugin.call(this, type, [event, ui]);
		if(type == "drag") this.positionAbs = this._convertPositionTo("absolute"); //The absolute position has to be recalculated after plugins
		return $.Widget.prototype._trigger.call(this, type, event, ui);
	},

	plugins: {},

	_uiHash: function(event) {
		return {
			helper: this.helper,
			position: this.position,
			originalPosition: this.originalPosition,
			offset: this.positionAbs
		};
	}

});

$.extend($.ui.draggable, {
	version: "1.8.15"
});

$.ui.plugin.add("draggable", "connectToSortable", {
	start: function(event, ui) {

		var inst = $(this).data("draggable"), o = inst.options,
			uiSortable = $.extend({}, ui, { item: inst.element });
		inst.sortables = [];
		$(o.connectToSortable).each(function() {
			var sortable = $.data(this, 'sortable');
			if (sortable && !sortable.options.disabled) {
				inst.sortables.push({
					instance: sortable,
					shouldRevert: sortable.options.revert
				});
				sortable.refreshPositions();	// Call the sortable's refreshPositions at drag start to refresh the containerCache since the sortable container cache is used in drag and needs to be up to date (this will ensure it's initialised as well as being kept in step with any changes that might have happened on the page).
				sortable._trigger("activate", event, uiSortable);
			}
		});

	},
	stop: function(event, ui) {

		//If we are still over the sortable, we fake the stop event of the sortable, but also remove helper
		var inst = $(this).data("draggable"),
			uiSortable = $.extend({}, ui, { item: inst.element });

		$.each(inst.sortables, function() {
			if(this.instance.isOver) {

				this.instance.isOver = 0;

				inst.cancelHelperRemoval = true; //Don't remove the helper in the draggable instance
				this.instance.cancelHelperRemoval = false; //Remove it in the sortable instance (so sortable plugins like revert still work)

				//The sortable revert is supported, and we have to set a temporary dropped variable on the draggable to support revert: 'valid/invalid'
				if(this.shouldRevert) this.instance.options.revert = true;

				//Trigger the stop of the sortable
				this.instance._mouseStop(event);

				this.instance.options.helper = this.instance.options._helper;

				//If the helper has been the original item, restore properties in the sortable
				if(inst.options.helper == 'original')
					this.instance.currentItem.css({ top: 'auto', left: 'auto' });

			} else {
				this.instance.cancelHelperRemoval = false; //Remove the helper in the sortable instance
				this.instance._trigger("deactivate", event, uiSortable);
			}

		});

	},
	drag: function(event, ui) {

		var inst = $(this).data("draggable"), self = this;

		var checkPos = function(o) {
			var dyClick = this.offset.click.top, dxClick = this.offset.click.left;
			var helperTop = this.positionAbs.top, helperLeft = this.positionAbs.left;
			var itemHeight = o.height, itemWidth = o.width;
			var itemTop = o.top, itemLeft = o.left;

			return $.ui.isOver(helperTop + dyClick, helperLeft + dxClick, itemTop, itemLeft, itemHeight, itemWidth);
		};

		$.each(inst.sortables, function(i) {
			
			//Copy over some variables to allow calling the sortable's native _intersectsWith
			this.instance.positionAbs = inst.positionAbs;
			this.instance.helperProportions = inst.helperProportions;
			this.instance.offset.click = inst.offset.click;
			
			if(this.instance._intersectsWith(this.instance.containerCache)) {

				//If it intersects, we use a little isOver variable and set it once, so our move-in stuff gets fired only once
				if(!this.instance.isOver) {

					this.instance.isOver = 1;
					//Now we fake the start of dragging for the sortable instance,
					//by cloning the list group item, appending it to the sortable and using it as inst.currentItem
					//We can then fire the start event of the sortable with our passed browser event, and our own helper (so it doesn't create a new one)
					this.instance.currentItem = $(self).clone().removeAttr('id').appendTo(this.instance.element).data("sortable-item", true);
					this.instance.options._helper = this.instance.options.helper; //Store helper option to later restore it
					this.instance.options.helper = function() { return ui.helper[0]; };

					event.target = this.instance.currentItem[0];
					this.instance._mouseCapture(event, true);
					this.instance._mouseStart(event, true, true);

					//Because the browser event is way off the new appended portlet, we modify a couple of variables to reflect the changes
					this.instance.offset.click.top = inst.offset.click.top;
					this.instance.offset.click.left = inst.offset.click.left;
					this.instance.offset.parent.left -= inst.offset.parent.left - this.instance.offset.parent.left;
					this.instance.offset.parent.top -= inst.offset.parent.top - this.instance.offset.parent.top;

					inst._trigger("toSortable", event);
					inst.dropped = this.instance.element; //draggable revert needs that
					//hack so receive/update callbacks work (mostly)
					inst.currentItem = inst.element;
					this.instance.fromOutside = inst;

				}

				//Provided we did all the previous steps, we can fire the drag event of the sortable on every draggable drag, when it intersects with the sortable
				if(this.instance.currentItem) this.instance._mouseDrag(event);

			} else {

				//If it doesn't intersect with the sortable, and it intersected before,
				//we fake the drag stop of the sortable, but make sure it doesn't remove the helper by using cancelHelperRemoval
				if(this.instance.isOver) {

					this.instance.isOver = 0;
					this.instance.cancelHelperRemoval = true;
					
					//Prevent reverting on this forced stop
					this.instance.options.revert = false;
					
					// The out event needs to be triggered independently
					this.instance._trigger('out', event, this.instance._uiHash(this.instance));
					
					this.instance._mouseStop(event, true);
					this.instance.options.helper = this.instance.options._helper;

					//Now we remove our currentItem, the list group clone again, and the placeholder, and animate the helper back to it's original size
					this.instance.currentItem.remove();
					if(this.instance.placeholder) this.instance.placeholder.remove();

					inst._trigger("fromSortable", event);
					inst.dropped = false; //draggable revert needs that
				}

			};

		});

	}
});

$.ui.plugin.add("draggable", "cursor", {
	start: function(event, ui) {
		var t = $('body'), o = $(this).data('draggable').options;
		if (t.css("cursor")) o._cursor = t.css("cursor");
		t.css("cursor", o.cursor);
	},
	stop: function(event, ui) {
		var o = $(this).data('draggable').options;
		if (o._cursor) $('body').css("cursor", o._cursor);
	}
});

$.ui.plugin.add("draggable", "opacity", {
	start: function(event, ui) {
		var t = $(ui.helper), o = $(this).data('draggable').options;
		if(t.css("opacity")) o._opacity = t.css("opacity");
		t.css('opacity', o.opacity);
	},
	stop: function(event, ui) {
		var o = $(this).data('draggable').options;
		if(o._opacity) $(ui.helper).css('opacity', o._opacity);
	}
});

$.ui.plugin.add("draggable", "scroll", {
	start: function(event, ui) {
		var i = $(this).data("draggable");
		if(i.scrollParent[0] != document && i.scrollParent[0].tagName != 'HTML') i.overflowOffset = i.scrollParent.offset();
	},
	drag: function(event, ui) {

		var i = $(this).data("draggable"), o = i.options, scrolled = false;

		if(i.scrollParent[0] != document && i.scrollParent[0].tagName != 'HTML') {

			if(!o.axis || o.axis != 'x') {
				if((i.overflowOffset.top + i.scrollParent[0].offsetHeight) - event.pageY < o.scrollSensitivity)
					i.scrollParent[0].scrollTop = scrolled = i.scrollParent[0].scrollTop + o.scrollSpeed;
				else if(event.pageY - i.overflowOffset.top < o.scrollSensitivity)
					i.scrollParent[0].scrollTop = scrolled = i.scrollParent[0].scrollTop - o.scrollSpeed;
			}

			if(!o.axis || o.axis != 'y') {
				if((i.overflowOffset.left + i.scrollParent[0].offsetWidth) - event.pageX < o.scrollSensitivity)
					i.scrollParent[0].scrollLeft = scrolled = i.scrollParent[0].scrollLeft + o.scrollSpeed;
				else if(event.pageX - i.overflowOffset.left < o.scrollSensitivity)
					i.scrollParent[0].scrollLeft = scrolled = i.scrollParent[0].scrollLeft - o.scrollSpeed;
			}

		} else {

			if(!o.axis || o.axis != 'x') {
				if(event.pageY - $(document).scrollTop() < o.scrollSensitivity)
					scrolled = $(document).scrollTop($(document).scrollTop() - o.scrollSpeed);
				else if($(window).height() - (event.pageY - $(document).scrollTop()) < o.scrollSensitivity)
					scrolled = $(document).scrollTop($(document).scrollTop() + o.scrollSpeed);
			}

			if(!o.axis || o.axis != 'y') {
				if(event.pageX - $(document).scrollLeft() < o.scrollSensitivity)
					scrolled = $(document).scrollLeft($(document).scrollLeft() - o.scrollSpeed);
				else if($(window).width() - (event.pageX - $(document).scrollLeft()) < o.scrollSensitivity)
					scrolled = $(document).scrollLeft($(document).scrollLeft() + o.scrollSpeed);
			}

		}

		if(scrolled !== false && $.ui.ddmanager && !o.dropBehaviour)
			$.ui.ddmanager.prepareOffsets(i, event);

	}
});

$.ui.plugin.add("draggable", "snap", {
	start: function(event, ui) {

		var i = $(this).data("draggable"), o = i.options;
		i.snapElements = [];

		$(o.snap.constructor != String ? ( o.snap.items || ':data(draggable)' ) : o.snap).each(function() {
			var $t = $(this); var $o = $t.offset();
			if(this != i.element[0]) i.snapElements.push({
				item: this,
				width: $t.outerWidth(), height: $t.outerHeight(),
				top: $o.top, left: $o.left
			});
		});

	},
	drag: function(event, ui) {

		var inst = $(this).data("draggable"), o = inst.options;
		var d = o.snapTolerance;

		var x1 = ui.offset.left, x2 = x1 + inst.helperProportions.width,
			y1 = ui.offset.top, y2 = y1 + inst.helperProportions.height;

		for (var i = inst.snapElements.length - 1; i >= 0; i--){

			var l = inst.snapElements[i].left, r = l + inst.snapElements[i].width,
				t = inst.snapElements[i].top, b = t + inst.snapElements[i].height;

			//Yes, I know, this is insane ;)
			if(!((l-d < x1 && x1 < r+d && t-d < y1 && y1 < b+d) || (l-d < x1 && x1 < r+d && t-d < y2 && y2 < b+d) || (l-d < x2 && x2 < r+d && t-d < y1 && y1 < b+d) || (l-d < x2 && x2 < r+d && t-d < y2 && y2 < b+d))) {
				if(inst.snapElements[i].snapping) (inst.options.snap.release && inst.options.snap.release.call(inst.element, event, $.extend(inst._uiHash(), { snapItem: inst.snapElements[i].item })));
				inst.snapElements[i].snapping = false;
				continue;
			}

			if(o.snapMode != 'inner') {
				var ts = Math.abs(t - y2) <= d;
				var bs = Math.abs(b - y1) <= d;
				var ls = Math.abs(l - x2) <= d;
				var rs = Math.abs(r - x1) <= d;
				if(ts) ui.position.top = inst._convertPositionTo("relative", { top: t - inst.helperProportions.height, left: 0 }).top - inst.margins.top;
				if(bs) ui.position.top = inst._convertPositionTo("relative", { top: b, left: 0 }).top - inst.margins.top;
				if(ls) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l - inst.helperProportions.width }).left - inst.margins.left;
				if(rs) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r }).left - inst.margins.left;
			}

			var first = (ts || bs || ls || rs);

			if(o.snapMode != 'outer') {
				var ts = Math.abs(t - y1) <= d;
				var bs = Math.abs(b - y2) <= d;
				var ls = Math.abs(l - x1) <= d;
				var rs = Math.abs(r - x2) <= d;
				if(ts) ui.position.top = inst._convertPositionTo("relative", { top: t, left: 0 }).top - inst.margins.top;
				if(bs) ui.position.top = inst._convertPositionTo("relative", { top: b - inst.helperProportions.height, left: 0 }).top - inst.margins.top;
				if(ls) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l }).left - inst.margins.left;
				if(rs) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r - inst.helperProportions.width }).left - inst.margins.left;
			}

			if(!inst.snapElements[i].snapping && (ts || bs || ls || rs || first))
				(inst.options.snap.snap && inst.options.snap.snap.call(inst.element, event, $.extend(inst._uiHash(), { snapItem: inst.snapElements[i].item })));
			inst.snapElements[i].snapping = (ts || bs || ls || rs || first);

		};

	}
});

$.ui.plugin.add("draggable", "stack", {
	start: function(event, ui) {

		var o = $(this).data("draggable").options;

		var group = $.makeArray($(o.stack)).sort(function(a,b) {
			return (parseInt($(a).css("zIndex"),10) || 0) - (parseInt($(b).css("zIndex"),10) || 0);
		});
		if (!group.length) { return; }
		
		var min = parseInt(group[0].style.zIndex) || 0;
		$(group).each(function(i) {
			this.style.zIndex = min + i;
		});

		this[0].style.zIndex = min + group.length;

	}
});

$.ui.plugin.add("draggable", "zIndex", {
	start: function(event, ui) {
		var t = $(ui.helper), o = $(this).data("draggable").options;
		if(t.css("zIndex")) o._zIndex = t.css("zIndex");
		t.css('zIndex', o.zIndex);
	},
	stop: function(event, ui) {
		var o = $(this).data("draggable").options;
		if(o._zIndex) $(ui.helper).css('zIndex', o._zIndex);
	}
});

})(jQuery);
/*
 * jQuery UI Droppable 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Droppables
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	jquery.ui.mouse.js
 *	jquery.ui.draggable.js
 */
(function( $, undefined ) {

$.widget("ui.droppable", {
	widgetEventPrefix: "drop",
	options: {
		accept: '*',
		activeClass: false,
		addClasses: true,
		greedy: false,
		hoverClass: false,
		scope: 'default',
		tolerance: 'intersect'
	},
	_create: function() {

		var o = this.options, accept = o.accept;
		this.isover = 0; this.isout = 1;

		this.accept = $.isFunction(accept) ? accept : function(d) {
			return d.is(accept);
		};

		//Store the droppable's proportions
		this.proportions = { width: this.element[0].offsetWidth, height: this.element[0].offsetHeight };

		// Add the reference and positions to the manager
		$.ui.ddmanager.droppables[o.scope] = $.ui.ddmanager.droppables[o.scope] || [];
		$.ui.ddmanager.droppables[o.scope].push(this);

		(o.addClasses && this.element.addClass("ui-droppable"));

	},

	destroy: function() {
		var drop = $.ui.ddmanager.droppables[this.options.scope];
		for ( var i = 0; i < drop.length; i++ )
			if ( drop[i] == this )
				drop.splice(i, 1);

		this.element
			.removeClass("ui-droppable ui-droppable-disabled")
			.removeData("droppable")
			.unbind(".droppable");

		return this;
	},

	_setOption: function(key, value) {

		if(key == 'accept') {
			this.accept = $.isFunction(value) ? value : function(d) {
				return d.is(value);
			};
		}
		$.Widget.prototype._setOption.apply(this, arguments);
	},

	_activate: function(event) {
		var draggable = $.ui.ddmanager.current;
		if(this.options.activeClass) this.element.addClass(this.options.activeClass);
		(draggable && this._trigger('activate', event, this.ui(draggable)));
	},

	_deactivate: function(event) {
		var draggable = $.ui.ddmanager.current;
		if(this.options.activeClass) this.element.removeClass(this.options.activeClass);
		(draggable && this._trigger('deactivate', event, this.ui(draggable)));
	},

	_over: function(event) {

		var draggable = $.ui.ddmanager.current;
		if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return; // Bail if draggable and droppable are same element

		if (this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
			if(this.options.hoverClass) this.element.addClass(this.options.hoverClass);
			this._trigger('over', event, this.ui(draggable));
		}

	},

	_out: function(event) {

		var draggable = $.ui.ddmanager.current;
		if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return; // Bail if draggable and droppable are same element

		if (this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
			if(this.options.hoverClass) this.element.removeClass(this.options.hoverClass);
			this._trigger('out', event, this.ui(draggable));
		}

	},

	_drop: function(event,custom) {

		var draggable = custom || $.ui.ddmanager.current;
		if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return false; // Bail if draggable and droppable are same element

		var childrenIntersection = false;
		this.element.find(":data(droppable)").not(".ui-draggable-dragging").each(function() {
			var inst = $.data(this, 'droppable');
			if(
				inst.options.greedy
				&& !inst.options.disabled
				&& inst.options.scope == draggable.options.scope
				&& inst.accept.call(inst.element[0], (draggable.currentItem || draggable.element))
				&& $.ui.intersect(draggable, $.extend(inst, { offset: inst.element.offset() }), inst.options.tolerance)
			) { childrenIntersection = true; return false; }
		});
		if(childrenIntersection) return false;

		if(this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
			if(this.options.activeClass) this.element.removeClass(this.options.activeClass);
			if(this.options.hoverClass) this.element.removeClass(this.options.hoverClass);
			this._trigger('drop', event, this.ui(draggable));
			return this.element;
		}

		return false;

	},

	ui: function(c) {
		return {
			draggable: (c.currentItem || c.element),
			helper: c.helper,
			position: c.position,
			offset: c.positionAbs
		};
	}

});

$.extend($.ui.droppable, {
	version: "1.8.15"
});

$.ui.intersect = function(draggable, droppable, toleranceMode) {

	if (!droppable.offset) return false;

	var x1 = (draggable.positionAbs || draggable.position.absolute).left, x2 = x1 + draggable.helperProportions.width,
		y1 = (draggable.positionAbs || draggable.position.absolute).top, y2 = y1 + draggable.helperProportions.height;
	var l = droppable.offset.left, r = l + droppable.proportions.width,
		t = droppable.offset.top, b = t + droppable.proportions.height;

	switch (toleranceMode) {
		case 'fit':
			return (l <= x1 && x2 <= r
				&& t <= y1 && y2 <= b);
			break;
		case 'intersect':
			return (l < x1 + (draggable.helperProportions.width / 2) // Right Half
				&& x2 - (draggable.helperProportions.width / 2) < r // Left Half
				&& t < y1 + (draggable.helperProportions.height / 2) // Bottom Half
				&& y2 - (draggable.helperProportions.height / 2) < b ); // Top Half
			break;
		case 'pointer':
			var draggableLeft = ((draggable.positionAbs || draggable.position.absolute).left + (draggable.clickOffset || draggable.offset.click).left),
				draggableTop = ((draggable.positionAbs || draggable.position.absolute).top + (draggable.clickOffset || draggable.offset.click).top),
				isOver = $.ui.isOver(draggableTop, draggableLeft, t, l, droppable.proportions.height, droppable.proportions.width);
			return isOver;
			break;
		case 'touch':
			return (
					(y1 >= t && y1 <= b) ||	// Top edge touching
					(y2 >= t && y2 <= b) ||	// Bottom edge touching
					(y1 < t && y2 > b)		// Surrounded vertically
				) && (
					(x1 >= l && x1 <= r) ||	// Left edge touching
					(x2 >= l && x2 <= r) ||	// Right edge touching
					(x1 < l && x2 > r)		// Surrounded horizontally
				);
			break;
		default:
			return false;
			break;
		}

};

/*
	This manager tracks offsets of draggables and droppables
*/
$.ui.ddmanager = {
	current: null,
	droppables: { 'default': [] },
	prepareOffsets: function(t, event) {

		var m = $.ui.ddmanager.droppables[t.options.scope] || [];
		var type = event ? event.type : null; // workaround for #2317
		var list = (t.currentItem || t.element).find(":data(droppable)").andSelf();

		droppablesLoop: for (var i = 0; i < m.length; i++) {

			if(m[i].options.disabled || (t && !m[i].accept.call(m[i].element[0],(t.currentItem || t.element)))) continue;	//No disabled and non-accepted
			for (var j=0; j < list.length; j++) { if(list[j] == m[i].element[0]) { m[i].proportions.height = 0; continue droppablesLoop; } }; //Filter out elements in the current dragged item
			m[i].visible = m[i].element.css("display") != "none"; if(!m[i].visible) continue; 									//If the element is not visible, continue

			if(type == "mousedown") m[i]._activate.call(m[i], event); //Activate the droppable if used directly from draggables

			m[i].offset = m[i].element.offset();
			m[i].proportions = { width: m[i].element[0].offsetWidth, height: m[i].element[0].offsetHeight };

		}

	},
	drop: function(draggable, event) {

		var dropped = false;
		$.each($.ui.ddmanager.droppables[draggable.options.scope] || [], function() {

			if(!this.options) return;
			if (!this.options.disabled && this.visible && $.ui.intersect(draggable, this, this.options.tolerance))
				dropped = dropped || this._drop.call(this, event);

			if (!this.options.disabled && this.visible && this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
				this.isout = 1; this.isover = 0;
				this._deactivate.call(this, event);
			}

		});
		return dropped;

	},
	dragStart: function( draggable, event ) {
		//Listen for scrolling so that if the dragging causes scrolling the position of the droppables can be recalculated (see #5003)
		draggable.element.parentsUntil( "body" ).bind( "scroll.droppable", function() {
			if( !draggable.options.refreshPositions ) $.ui.ddmanager.prepareOffsets( draggable, event );
		});
	},
	drag: function(draggable, event) {

		//If you have a highly dynamic page, you might try this option. It renders positions every time you move the mouse.
		if(draggable.options.refreshPositions) $.ui.ddmanager.prepareOffsets(draggable, event);

		//Run through all droppables and check their positions based on specific tolerance options
		$.each($.ui.ddmanager.droppables[draggable.options.scope] || [], function() {

			if(this.options.disabled || this.greedyChild || !this.visible) return;
			var intersects = $.ui.intersect(draggable, this, this.options.tolerance);

			var c = !intersects && this.isover == 1 ? 'isout' : (intersects && this.isover == 0 ? 'isover' : null);
			if(!c) return;

			var parentInstance;
			if (this.options.greedy) {
				var parent = this.element.parents(':data(droppable):eq(0)');
				if (parent.length) {
					parentInstance = $.data(parent[0], 'droppable');
					parentInstance.greedyChild = (c == 'isover' ? 1 : 0);
				}
			}

			// we just moved into a greedy child
			if (parentInstance && c == 'isover') {
				parentInstance['isover'] = 0;
				parentInstance['isout'] = 1;
				parentInstance._out.call(parentInstance, event);
			}

			this[c] = 1; this[c == 'isout' ? 'isover' : 'isout'] = 0;
			this[c == "isover" ? "_over" : "_out"].call(this, event);

			// we just moved out of a greedy child
			if (parentInstance && c == 'isout') {
				parentInstance['isout'] = 0;
				parentInstance['isover'] = 1;
				parentInstance._over.call(parentInstance, event);
			}
		});

	},
	dragStop: function( draggable, event ) {
		draggable.element.parentsUntil( "body" ).unbind( "scroll.droppable" );
		//Call prepareOffsets one final time since IE does not fire return scroll events when overflow was caused by drag (see #5003)
		if( !draggable.options.refreshPositions ) $.ui.ddmanager.prepareOffsets( draggable, event );
	}
};

})(jQuery);
/*
 * jQuery UI Resizable 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Resizables
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.mouse.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

$.widget("ui.resizable", $.ui.mouse, {
	widgetEventPrefix: "resize",
	options: {
		alsoResize: false,
		animate: false,
		animateDuration: "slow",
		animateEasing: "swing",
		aspectRatio: false,
		autoHide: false,
		containment: false,
		ghost: false,
		grid: false,
		handles: "e,s,se",
		helper: false,
		maxHeight: null,
		maxWidth: null,
		minHeight: 10,
		minWidth: 10,
		zIndex: 1000
	},
	_create: function() {

		var self = this, o = this.options;
		this.element.addClass("om-resizable");

		$.extend(this, {
			_aspectRatio: !!(o.aspectRatio),
			aspectRatio: o.aspectRatio,
			originalElement: this.element,
			_proportionallyResizeElements: [],
			_helper: o.helper || o.ghost || o.animate ? o.helper || 'om-resizable-helper' : null
		});

		//Wrap the element if it cannot hold child nodes
		if(this.element[0].nodeName.match(/canvas|textarea|input|select|button|img/i)) {

			//Opera fix for relative positioning
			if (/relative/.test(this.element.css('position')) && $.browser.opera)
				this.element.css({ position: 'relative', top: 'auto', left: 'auto' });

			//Create a wrapper element and set the wrapper to the new current internal element
			this.element.wrap(
				$('<div class="om-wrapper" style="overflow: hidden;"></div>').css({
					position: this.element.css('position'),
					width: this.element.outerWidth(),
					height: this.element.outerHeight(),
					top: this.element.css('top'),
					left: this.element.css('left')
				})
			);

			//Overwrite the original this.element
			this.element = this.element.parent().data(
				"resizable", this.element.data('resizable')
			);

			this.elementIsWrapper = true;

			//Move margins to the wrapper
			this.element.css({ marginLeft: this.originalElement.css("marginLeft"), marginTop: this.originalElement.css("marginTop"), marginRight: this.originalElement.css("marginRight"), marginBottom: this.originalElement.css("marginBottom") });
			this.originalElement.css({ marginLeft: 0, marginTop: 0, marginRight: 0, marginBottom: 0});

			//Prevent Safari textarea resize
			this.originalResizeStyle = this.originalElement.css('resize');
			this.originalElement.css('resize', 'none');

			//Push the actual element to our proportionallyResize internal array
			this._proportionallyResizeElements.push(this.originalElement.css({ position: 'static', zoom: 1, display: 'block' }));

			// avoid IE jump (hard set the margin)
			this.originalElement.css({ margin: this.originalElement.css('margin') });

			// fix handlers offset
			this._proportionallyResize();

		}

		this.handles = o.handles || (!$('.om-resizable-handle', this.element).length ? "e,s,se" : { n: '.om-resizable-n', e: '.om-resizable-e', s: '.om-resizable-s', w: '.om-resizable-w', se: '.om-resizable-se', sw: '.om-resizable-sw', ne: '.om-resizable-ne', nw: '.om-resizable-nw' });
		if(this.handles.constructor == String) {

			if(this.handles == 'all') this.handles = 'n,e,s,w,se,sw,ne,nw';
			var n = this.handles.split(","); this.handles = {};

			for(var i = 0; i < n.length; i++) {

				var handle = $.trim(n[i]), hname = 'om-resizable-'+handle;
				var axis = $('<div class="om-resizable-handle ' + hname + '"></div>');

				// increase zIndex of sw, se, ne, nw axis
				//TODO : this modifies original option
				if(/sw|se|ne|nw/.test(handle)) axis.css({ zIndex: ++o.zIndex });

				//TODO : What's going on here?
				if ('se' == handle) {
					axis.addClass('om-icon om-icon-gripsmall-diagonal-se');
				};

				//Insert into internal handles object and append to element
				this.handles[handle] = '.om-resizable-'+handle;
				this.element.append(axis);
			}

		}

		this._renderAxis = function(target) {

			target = target || this.element;

			for(var i in this.handles) {

				if(this.handles[i].constructor == String)
					this.handles[i] = $(this.handles[i], this.element).show();

				//Apply pad to wrapper element, needed to fix axis position (textarea, inputs, scrolls)
				if (this.elementIsWrapper && this.originalElement[0].nodeName.match(/textarea|input|select|button/i)) {

					var axis = $(this.handles[i], this.element), padWrapper = 0;

					//Checking the correct pad and border
					padWrapper = /sw|ne|nw|se|n|s/.test(i) ? axis.outerHeight() : axis.outerWidth();

					//The padding type i have to apply...
					var padPos = [ 'padding',
						/ne|nw|n/.test(i) ? 'Top' :
						/se|sw|s/.test(i) ? 'Bottom' :
						/^e$/.test(i) ? 'Right' : 'Left' ].join("");

					target.css(padPos, padWrapper);

					this._proportionallyResize();

				}

				//TODO: What's that good for? There's not anything to be executed left
				if(!$(this.handles[i]).length)
					continue;

			}
		};

		//TODO: make renderAxis a prototype function
		this._renderAxis(this.element);

		this._handles = $('.om-resizable-handle', this.element)
			.disableSelection();

		//Matching axis name
		this._handles.mouseover(function() {
			if (!self.resizing) {
				if (this.className)
					var axis = this.className.match(/om-resizable-(se|sw|ne|nw|n|e|s|w)/i);
				//Axis, default = se
				self.axis = axis && axis[1] ? axis[1] : 'se';
			}
		});

		//If we want to auto hide the elements
		if (o.autoHide) {
			this._handles.hide();
			$(this.element)
				.addClass("om-resizable-autohide")
				.hover(function() {
					if (o.disabled) return;
					$(this).removeClass("om-resizable-autohide");
					self._handles.show();
				},
				function(){
					if (o.disabled) return;
					if (!self.resizing) {
						$(this).addClass("om-resizable-autohide");
						self._handles.hide();
					}
				});
		}

		//Initialize the mouse interaction
		this._mouseInit();

	},

	destroy: function() {

		this._mouseDestroy();

		var _destroy = function(exp) {
			$(exp).removeClass("om-resizable om-resizable-disabled om-resizable-resizing")
				.removeData("resizable").unbind(".resizable").find('.om-resizable-handle').remove();
		};

		//TODO: Unwrap at same DOM position
		if (this.elementIsWrapper) {
			_destroy(this.element);
			var wrapper = this.element;
			wrapper.after(
				this.originalElement.css({
					position: wrapper.css('position'),
					width: wrapper.outerWidth(),
					height: wrapper.outerHeight(),
					top: wrapper.css('top'),
					left: wrapper.css('left')
				})
			).remove();
		}

		this.originalElement.css('resize', this.originalResizeStyle);
		_destroy(this.originalElement);

		return this;
	},

	_mouseCapture: function(event) {
		var handle = false;
		for (var i in this.handles) {
			if ($(this.handles[i])[0] == event.target) {
				handle = true;
			}
		}

		return !this.options.disabled && handle;
	},

	_mouseStart: function(event) {

		var o = this.options, iniPos = this.element.position(), el = this.element;

		this.resizing = true;
		this.documentScroll = { top: $(document).scrollTop(), left: $(document).scrollLeft() };

		// bugfix for http://dev.jquery.com/ticket/1749
		if (el.is('.ui-draggable') || (/absolute/).test(el.css('position'))) {
			el.css({ position: 'absolute', top: iniPos.top, left: iniPos.left });
		}

		//Opera fixing relative position
		if ($.browser.opera && (/relative/).test(el.css('position')))
			el.css({ position: 'relative', top: 'auto', left: 'auto' });

		this._renderProxy();

		var curleft = num(this.helper.css('left')), curtop = num(this.helper.css('top'));

		if (o.containment) {
			curleft += $(o.containment).scrollLeft() || 0;
			curtop += $(o.containment).scrollTop() || 0;
		}

		//Store needed variables
		this.offset = this.helper.offset();
		this.position = { left: curleft, top: curtop };
		this.size = this._helper ? { width: el.outerWidth(), height: el.outerHeight() } : { width: el.width(), height: el.height() };
		this.originalSize = this._helper ? { width: el.outerWidth(), height: el.outerHeight() } : { width: el.width(), height: el.height() };
		this.originalPosition = { left: curleft, top: curtop };
		this.sizeDiff = { width: el.outerWidth() - el.width(), height: el.outerHeight() - el.height() };
		this.originalMousePosition = { left: event.pageX, top: event.pageY };

		//Aspect Ratio
		this.aspectRatio = (typeof o.aspectRatio == 'number') ? o.aspectRatio : ((this.originalSize.width / this.originalSize.height) || 1);

	    var cursor = $('.om-resizable-' + this.axis).css('cursor');
	    $('body').css('cursor', cursor == 'auto' ? this.axis + '-resize' : cursor);

		el.addClass("om-resizable-resizing");
		this._propagate("start", event);
		return true;
	},

	_mouseDrag: function(event) {

		//Increase performance, avoid regex
		var el = this.helper, o = this.options, props = {},
			self = this, smp = this.originalMousePosition, a = this.axis;

		var dx = (event.pageX-smp.left)||0, dy = (event.pageY-smp.top)||0;
		var trigger = this._change[a];
		if (!trigger) return false;

		// Calculate the attrs that will be change
		var data = trigger.apply(this, [event, dx, dy]), ie6 = $.browser.msie && $.browser.version < 7, csdif = this.sizeDiff;

		// Put this in the mouseDrag handler since the user can start pressing shift while resizing
		this._updateVirtualBoundaries(event.shiftKey);
		if (this._aspectRatio || event.shiftKey)
			data = this._updateRatio(data, event);

		data = this._respectSize(data, event);

		// plugins callbacks need to be called first
		this._propagate("resize", event);

		el.css({
			top: this.position.top + "px", left: this.position.left + "px",
			width: this.size.width + "px", height: this.size.height + "px"
		});

		if (!this._helper && this._proportionallyResizeElements.length)
			this._proportionallyResize();

		this._updateCache(data);

		// calling the user callback at the end
		this._trigger('resize', event, this.ui());

		return false;
	},

	_mouseStop: function(event) {

		this.resizing = false;
		var o = this.options, self = this;

		if(this._helper) {
			var pr = this._proportionallyResizeElements, ista = pr.length && (/textarea/i).test(pr[0].nodeName),
				soffseth = ista && $.ui.hasScroll(pr[0], 'left') /* TODO - jump height */ ? 0 : self.sizeDiff.height,
				soffsetw = ista ? 0 : self.sizeDiff.width;

			var s = { width: (self.helper.width()  - soffsetw), height: (self.helper.height() - soffseth) },
				left = (parseInt(self.element.css('left'), 10) + (self.position.left - self.originalPosition.left)) || null,
				top = (parseInt(self.element.css('top'), 10) + (self.position.top - self.originalPosition.top)) || null;

			if (!o.animate)
				this.element.css($.extend(s, { top: top, left: left }));

			self.helper.height(self.size.height);
			self.helper.width(self.size.width);

			if (this._helper && !o.animate) this._proportionallyResize();
		}

		$('body').css('cursor', 'auto');

		this.element.removeClass("om-resizable-resizing");

		this._propagate("stop", event);

		if (this._helper) this.helper.remove();
		return false;

	},

    _updateVirtualBoundaries: function(forceAspectRatio) {
        var o = this.options, pMinWidth, pMaxWidth, pMinHeight, pMaxHeight, b;

        b = {
            minWidth: isNumber(o.minWidth) ? o.minWidth : 0,
            maxWidth: isNumber(o.maxWidth) ? o.maxWidth : Infinity,
            minHeight: isNumber(o.minHeight) ? o.minHeight : 0,
            maxHeight: isNumber(o.maxHeight) ? o.maxHeight : Infinity
        };

        if(this._aspectRatio || forceAspectRatio) {
            // We want to create an enclosing box whose aspect ration is the requested one
            // First, compute the "projected" size for each dimension based on the aspect ratio and other dimension
            pMinWidth = b.minHeight * this.aspectRatio;
            pMinHeight = b.minWidth / this.aspectRatio;
            pMaxWidth = b.maxHeight * this.aspectRatio;
            pMaxHeight = b.maxWidth / this.aspectRatio;

            if(pMinWidth > b.minWidth) b.minWidth = pMinWidth;
            if(pMinHeight > b.minHeight) b.minHeight = pMinHeight;
            if(pMaxWidth < b.maxWidth) b.maxWidth = pMaxWidth;
            if(pMaxHeight < b.maxHeight) b.maxHeight = pMaxHeight;
        }
        this._vBoundaries = b;
    },

	_updateCache: function(data) {
		var o = this.options;
		this.offset = this.helper.offset();
		if (isNumber(data.left)) this.position.left = data.left;
		if (isNumber(data.top)) this.position.top = data.top;
		if (isNumber(data.height)) this.size.height = data.height;
		if (isNumber(data.width)) this.size.width = data.width;
	},

	_updateRatio: function(data, event) {

		var o = this.options, cpos = this.position, csize = this.size, a = this.axis;

		if (isNumber(data.height)) data.width = (data.height * this.aspectRatio);
		else if (isNumber(data.width)) data.height = (data.width / this.aspectRatio);

		if (a == 'sw') {
			data.left = cpos.left + (csize.width - data.width);
			data.top = null;
		}
		if (a == 'nw') {
			data.top = cpos.top + (csize.height - data.height);
			data.left = cpos.left + (csize.width - data.width);
		}

		return data;
	},

	_respectSize: function(data, event) {

		var el = this.helper, o = this._vBoundaries, pRatio = this._aspectRatio || event.shiftKey, a = this.axis,
				ismaxw = isNumber(data.width) && o.maxWidth && (o.maxWidth < data.width), ismaxh = isNumber(data.height) && o.maxHeight && (o.maxHeight < data.height),
					isminw = isNumber(data.width) && o.minWidth && (o.minWidth > data.width), isminh = isNumber(data.height) && o.minHeight && (o.minHeight > data.height);

		if (isminw) data.width = o.minWidth;
		if (isminh) data.height = o.minHeight;
		if (ismaxw) data.width = o.maxWidth;
		if (ismaxh) data.height = o.maxHeight;

		var dw = this.originalPosition.left + this.originalSize.width, dh = this.position.top + this.size.height;
		var cw = /sw|nw|w/.test(a), ch = /nw|ne|n/.test(a);

		if (isminw && cw) data.left = dw - o.minWidth;
		if (ismaxw && cw) data.left = dw - o.maxWidth;
		if (isminh && ch)	data.top = dh - o.minHeight;
		if (ismaxh && ch)	data.top = dh - o.maxHeight;

		// fixing jump error on top/left - bug #2330
		var isNotwh = !data.width && !data.height;
		if (isNotwh && !data.left && data.top) data.top = null;
		else if (isNotwh && !data.top && data.left) data.left = null;

		return data;
	},

	_proportionallyResize: function() {

		var o = this.options;
		if (!this._proportionallyResizeElements.length) return;
		var element = this.helper || this.element;

		for (var i=0; i < this._proportionallyResizeElements.length; i++) {

			var prel = this._proportionallyResizeElements[i];

			if (!this.borderDif) {
				var b = [prel.css('borderTopWidth'), prel.css('borderRightWidth'), prel.css('borderBottomWidth'), prel.css('borderLeftWidth')],
					p = [prel.css('paddingTop'), prel.css('paddingRight'), prel.css('paddingBottom'), prel.css('paddingLeft')];

				this.borderDif = $.map(b, function(v, i) {
					var border = parseInt(v,10)||0, padding = parseInt(p[i],10)||0;
					return border + padding;
				});
			}

			if ($.browser.msie && !(!($(element).is(':hidden') || $(element).parents(':hidden').length)))
				continue;

			prel.css({
				height: (element.height() - this.borderDif[0] - this.borderDif[2]) || 0,
				width: (element.width() - this.borderDif[1] - this.borderDif[3]) || 0
			});

		};

	},

	_renderProxy: function() {

		var el = this.element, o = this.options;
		this.elementOffset = el.offset();

		if(this._helper) {

			this.helper = this.helper || $('<div style="overflow:hidden;"></div>');

			// fix ie6 offset TODO: This seems broken
			var ie6 = $.browser.msie && $.browser.version < 7, ie6offset = (ie6 ? 1 : 0),
			pxyoffset = ( ie6 ? 2 : -1 );

			this.helper.addClass(this._helper).css({
				width: this.element.outerWidth() + pxyoffset,
				height: this.element.outerHeight() + pxyoffset,
				position: 'absolute',
				left: this.elementOffset.left - ie6offset +'px',
				top: this.elementOffset.top - ie6offset +'px',
				zIndex: ++o.zIndex //TODO: Don't modify option
			});

			this.helper
				.appendTo("body")
				.disableSelection();

		} else {
			this.helper = this.element;
		}

	},

	_change: {
		e: function(event, dx, dy) {
			return { width: this.originalSize.width + dx };
		},
		w: function(event, dx, dy) {
			var o = this.options, cs = this.originalSize, sp = this.originalPosition;
			return { left: sp.left + dx, width: cs.width - dx };
		},
		n: function(event, dx, dy) {
			var o = this.options, cs = this.originalSize, sp = this.originalPosition;
			return { top: sp.top + dy, height: cs.height - dy };
		},
		s: function(event, dx, dy) {
			return { height: this.originalSize.height + dy };
		},
		se: function(event, dx, dy) {
			return $.extend(this._change.s.apply(this, arguments), this._change.e.apply(this, [event, dx, dy]));
		},
		sw: function(event, dx, dy) {
			return $.extend(this._change.s.apply(this, arguments), this._change.w.apply(this, [event, dx, dy]));
		},
		ne: function(event, dx, dy) {
			return $.extend(this._change.n.apply(this, arguments), this._change.e.apply(this, [event, dx, dy]));
		},
		nw: function(event, dx, dy) {
			return $.extend(this._change.n.apply(this, arguments), this._change.w.apply(this, [event, dx, dy]));
		}
	},

	_propagate: function(n, event) {
		$.ui.plugin.call(this, n, [event, this.ui()]);
		(n != "resize" && this._trigger(n, event, this.ui()));
	},

	plugins: {},

	ui: function() {
		return {
			originalElement: this.originalElement,
			element: this.element,
			helper: this.helper,
			position: this.position,
			size: this.size,
			originalSize: this.originalSize,
			originalPosition: this.originalPosition
		};
	}

});

$.extend($.ui.resizable, {
	version: "1.8.15"
});

/*
 * Resizable Extensions
 */

$.ui.plugin.add("resizable", "alsoResize", {

	start: function (event, ui) {
		var self = $(this).data("resizable"), o = self.options;

		var _store = function (exp) {
			$(exp).each(function() {
				var el = $(this);
				el.data("resizable-alsoresize", {
					width: parseInt(el.width(), 10), height: parseInt(el.height(), 10),
					left: parseInt(el.css('left'), 10), top: parseInt(el.css('top'), 10),
					position: el.css('position') // to reset Opera on stop()
				});
			});
		};

		if (typeof(o.alsoResize) == 'object' && !o.alsoResize.parentNode) {
			if (o.alsoResize.length) { o.alsoResize = o.alsoResize[0]; _store(o.alsoResize); }
			else { $.each(o.alsoResize, function (exp) { _store(exp); }); }
		}else{
			_store(o.alsoResize);
		}
	},

	resize: function (event, ui) {
		var self = $(this).data("resizable"), o = self.options, os = self.originalSize, op = self.originalPosition;

		var delta = {
			height: (self.size.height - os.height) || 0, width: (self.size.width - os.width) || 0,
			top: (self.position.top - op.top) || 0, left: (self.position.left - op.left) || 0
		},

		_alsoResize = function (exp, c) {
			$(exp).each(function() {
				var el = $(this), start = $(this).data("resizable-alsoresize"), style = {}, 
					css = c && c.length ? c : el.parents(ui.originalElement[0]).length ? ['width', 'height'] : ['width', 'height', 'top', 'left'];

				$.each(css, function (i, prop) {
					var sum = (start[prop]||0) + (delta[prop]||0);
					if (sum && sum >= 0)
						style[prop] = sum || null;
				});

				// Opera fixing relative position
				if ($.browser.opera && /relative/.test(el.css('position'))) {
					self._revertToRelativePosition = true;
					el.css({ position: 'absolute', top: 'auto', left: 'auto' });
				}

				el.css(style);
			});
		};

		if (typeof(o.alsoResize) == 'object' && !o.alsoResize.nodeType) {
			$.each(o.alsoResize, function (exp, c) { _alsoResize(exp, c); });
		}else{
			_alsoResize(o.alsoResize);
		}
	},

	stop: function (event, ui) {
		var self = $(this).data("resizable"), o = self.options;

		var _reset = function (exp) {
			$(exp).each(function() {
				var el = $(this);
				// reset position for Opera - no need to verify it was changed
				el.css({ position: el.data("resizable-alsoresize").position });
			});
		};

		if (self._revertToRelativePosition) {
			self._revertToRelativePosition = false;
			if (typeof(o.alsoResize) == 'object' && !o.alsoResize.nodeType) {
				$.each(o.alsoResize, function (exp) { _reset(exp); });
			}else{
				_reset(o.alsoResize);
			}
		}

		$(this).removeData("resizable-alsoresize");
	}
});

$.ui.plugin.add("resizable", "animate", {

	stop: function(event, ui) {
		var self = $(this).data("resizable"), o = self.options;

		var pr = self._proportionallyResizeElements, ista = pr.length && (/textarea/i).test(pr[0].nodeName),
					soffseth = ista && $.ui.hasScroll(pr[0], 'left') /* TODO - jump height */ ? 0 : self.sizeDiff.height,
						soffsetw = ista ? 0 : self.sizeDiff.width;

		var style = { width: (self.size.width - soffsetw), height: (self.size.height - soffseth) },
					left = (parseInt(self.element.css('left'), 10) + (self.position.left - self.originalPosition.left)) || null,
						top = (parseInt(self.element.css('top'), 10) + (self.position.top - self.originalPosition.top)) || null;

		self.element.animate(
			$.extend(style, top && left ? { top: top, left: left } : {}), {
				duration: o.animateDuration,
				easing: o.animateEasing,
				step: function() {

					var data = {
						width: parseInt(self.element.css('width'), 10),
						height: parseInt(self.element.css('height'), 10),
						top: parseInt(self.element.css('top'), 10),
						left: parseInt(self.element.css('left'), 10)
					};

					if (pr && pr.length) $(pr[0]).css({ width: data.width, height: data.height });

					// propagating resize, and updating values for each animation step
					self._updateCache(data);
					self._propagate("resize", event);

				}
			}
		);
	}

});

$.ui.plugin.add("resizable", "containment", {

	start: function(event, ui) {
		var self = $(this).data("resizable"), o = self.options, el = self.element;
		var oc = o.containment,	ce = (oc instanceof $) ? oc.get(0) : (/parent/.test(oc)) ? el.parent().get(0) : oc;
		if (!ce) return;

		self.containerElement = $(ce);

		if (/document/.test(oc) || oc == document) {
			self.containerOffset = { left: 0, top: 0 };
			self.containerPosition = { left: 0, top: 0 };

			self.parentData = {
				element: $(document), left: 0, top: 0,
				width: $(document).width(), height: $(document).height() || document.body.parentNode.scrollHeight
			};
		}

		// i'm a node, so compute top, left, right, bottom
		else {
			var element = $(ce), p = [];
			$([ "Top", "Right", "Left", "Bottom" ]).each(function(i, name) { p[i] = num(element.css("padding" + name)); });

			self.containerOffset = element.offset();
			self.containerPosition = element.position();
			self.containerSize = { height: (element.innerHeight() - p[3]), width: (element.innerWidth() - p[1]) };

			var co = self.containerOffset, ch = self.containerSize.height,	cw = self.containerSize.width,
						width = ($.ui.hasScroll(ce, "left") ? ce.scrollWidth : cw ), height = ($.ui.hasScroll(ce) ? ce.scrollHeight : ch);

			self.parentData = {
				element: ce, left: co.left, top: co.top, width: width, height: height
			};
		}
	},

	resize: function(event, ui) {
		var self = $(this).data("resizable"), o = self.options,
				ps = self.containerSize, co = self.containerOffset, cs = self.size, cp = self.position,
				pRatio = self._aspectRatio || event.shiftKey, cop = { top:0, left:0 }, ce = self.containerElement;

		if (ce[0] != document && (/static/).test(ce.css('position'))) cop = co;

		if (cp.left < (self._helper ? co.left : 0)) {
			self.size.width = self.size.width + (self._helper ? (self.position.left - co.left) : (self.position.left - cop.left));
			if (pRatio) self.size.height = self.size.width / o.aspectRatio;
			self.position.left = o.helper ? co.left : 0;
		}

		if (cp.top < (self._helper ? co.top : 0)) {
			self.size.height = self.size.height + (self._helper ? (self.position.top - co.top) : self.position.top);
			if (pRatio) self.size.width = self.size.height * o.aspectRatio;
			self.position.top = self._helper ? co.top : 0;
		}

		self.offset.left = self.parentData.left+self.position.left;
		self.offset.top = self.parentData.top+self.position.top;

		var woset = Math.abs( (self._helper ? self.offset.left - cop.left : (self.offset.left - cop.left)) + self.sizeDiff.width ),
					hoset = Math.abs( (self._helper ? self.offset.top - cop.top : (self.offset.top - co.top)) + self.sizeDiff.height );

		var isParent = self.containerElement.get(0) == self.element.parent().get(0),
		    isOffsetRelative = /relative|absolute/.test(self.containerElement.css('position'));

		if(isParent && isOffsetRelative) woset -= self.parentData.left;

		if (woset + self.size.width >= self.parentData.width) {
			self.size.width = self.parentData.width - woset;
			if (pRatio) self.size.height = self.size.width / self.aspectRatio;
		}

		if (hoset + self.size.height >= self.parentData.height) {
			self.size.height = self.parentData.height - hoset;
			if (pRatio) self.size.width = self.size.height * self.aspectRatio;
		}
	},

	stop: function(event, ui){
		var self = $(this).data("resizable"), o = self.options, cp = self.position,
				co = self.containerOffset, cop = self.containerPosition, ce = self.containerElement;

		var helper = $(self.helper), ho = helper.offset(), w = helper.outerWidth() - self.sizeDiff.width, h = helper.outerHeight() - self.sizeDiff.height;

		if (self._helper && !o.animate && (/relative/).test(ce.css('position')))
			$(this).css({ left: ho.left - cop.left - co.left, width: w, height: h });

		if (self._helper && !o.animate && (/static/).test(ce.css('position')))
			$(this).css({ left: ho.left - cop.left - co.left, width: w, height: h });

	}
});

$.ui.plugin.add("resizable", "ghost", {

	start: function(event, ui) {

		var self = $(this).data("resizable"), o = self.options, cs = self.size;

		self.ghost = self.originalElement.clone();
		self.ghost
			.css({ opacity: .25, display: 'block', position: 'relative', height: cs.height, width: cs.width, margin: 0, left: 0, top: 0 })
			.addClass('om-resizable-ghost')
			.addClass(typeof o.ghost == 'string' ? o.ghost : '');

		self.ghost.appendTo(self.helper);

	},

	resize: function(event, ui){
		var self = $(this).data("resizable"), o = self.options;
		if (self.ghost) self.ghost.css({ position: 'relative', height: self.size.height, width: self.size.width });
	},

	stop: function(event, ui){
		var self = $(this).data("resizable"), o = self.options;
		if (self.ghost && self.helper) self.helper.get(0).removeChild(self.ghost.get(0));
	}

});

$.ui.plugin.add("resizable", "grid", {

	resize: function(event, ui) {
		var self = $(this).data("resizable"), o = self.options, cs = self.size, os = self.originalSize, op = self.originalPosition, a = self.axis, ratio = o._aspectRatio || event.shiftKey;
		o.grid = typeof o.grid == "number" ? [o.grid, o.grid] : o.grid;
		var ox = Math.round((cs.width - os.width) / (o.grid[0]||1)) * (o.grid[0]||1), oy = Math.round((cs.height - os.height) / (o.grid[1]||1)) * (o.grid[1]||1);

		if (/^(se|s|e)$/.test(a)) {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
		}
		else if (/^(ne)$/.test(a)) {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
			self.position.top = op.top - oy;
		}
		else if (/^(sw)$/.test(a)) {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
			self.position.left = op.left - ox;
		}
		else {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
			self.position.top = op.top - oy;
			self.position.left = op.left - ox;
		}
	}

});

var num = function(v) {
	return parseInt(v, 10) || 0;
};

var isNumber = function(value) {
	return !isNaN(parseInt(value, 10));
};

})(jQuery);
/*
 * jQuery UI Sortable 1.8.15
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Sortables
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.mouse.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

$.widget("ui.sortable", $.ui.mouse, {
	widgetEventPrefix: "sort",
	options: {
		appendTo: "parent",
		axis: false,
		connectWith: false,
		containment: false,
		cursor: 'auto',
		cursorAt: false,
		dropOnEmpty: true,
		forcePlaceholderSize: false,
		forceHelperSize: false,
		grid: false,
		handle: false,
		helper: "original",
		items: '> *',
		opacity: false,
		placeholder: false,
		revert: false,
		scroll: true,
		scrollSensitivity: 20,
		scrollSpeed: 20,
		scope: "default",
		tolerance: "intersect",
		zIndex: 1000
	},
	_create: function() {

		var o = this.options;
		this.containerCache = {};
		this.element.addClass("ui-sortable");

		//Get the items
		this.refresh();

		//Let's determine if the items are being displayed horizontally
		this.floating = this.items.length ? o.axis === 'x' || (/left|right/).test(this.items[0].item.css('float')) || (/inline|table-cell/).test(this.items[0].item.css('display')) : false;

		//Let's determine the parent's offset
		this.offset = this.element.offset();

		//Initialize mouse events for interaction
		this._mouseInit();

	},

	destroy: function() {
		this.element
			.removeClass("ui-sortable ui-sortable-disabled")
			.removeData("sortable")
			.unbind(".sortable");
		this._mouseDestroy();

		for ( var i = this.items.length - 1; i >= 0; i-- )
			this.items[i].item.removeData("sortable-item");

		return this;
	},

	_setOption: function(key, value){
		if ( key === "disabled" ) {
			this.options[ key ] = value;
	
			this.widget()
				[ value ? "addClass" : "removeClass"]( "ui-sortable-disabled" );
		} else {
			// Don't call widget base _setOption for disable as it adds ui-state-disabled class
			$.Widget.prototype._setOption.apply(this, arguments);
		}
	},

	_mouseCapture: function(event, overrideHandle) {

		if (this.reverting) {
			return false;
		}

		if(this.options.disabled || this.options.type == 'static') return false;

		//We have to refresh the items data once first
		this._refreshItems(event);

		//Find out if the clicked node (or one of its parents) is a actual item in this.items
		var currentItem = null, self = this, nodes = $(event.target).parents().each(function() {
			if($.data(this, 'sortable-item') == self) {
				currentItem = $(this);
				return false;
			}
		});
		if($.data(event.target, 'sortable-item') == self) currentItem = $(event.target);

		if(!currentItem) return false;
		if(this.options.handle && !overrideHandle) {
			var validHandle = false;

			$(this.options.handle, currentItem).find("*").andSelf().each(function() { if(this == event.target) validHandle = true; });
			if(!validHandle) return false;
		}

		this.currentItem = currentItem;
		this._removeCurrentsFromItems();
		return true;

	},

	_mouseStart: function(event, overrideHandle, noActivation) {

		var o = this.options, self = this;
		this.currentContainer = this;

		//We only need to call refreshPositions, because the refreshItems call has been moved to mouseCapture
		this.refreshPositions();

		//Create and append the visible helper
		this.helper = this._createHelper(event);

		//Cache the helper size
		this._cacheHelperProportions();

		/*
		 * - Position generation -
		 * This block generates everything position related - it's the core of draggables.
		 */

		//Cache the margins of the original element
		this._cacheMargins();

		//Get the next scrolling parent
		this.scrollParent = this.helper.scrollParent();

		//The element's absolute position on the page minus margins
		this.offset = this.currentItem.offset();
		this.offset = {
			top: this.offset.top - this.margins.top,
			left: this.offset.left - this.margins.left
		};

		// Only after we got the offset, we can change the helper's position to absolute
		// TODO: Still need to figure out a way to make relative sorting possible
		this.helper.css("position", "absolute");
		this.cssPosition = this.helper.css("position");

		$.extend(this.offset, {
			click: { //Where the click happened, relative to the element
				left: event.pageX - this.offset.left,
				top: event.pageY - this.offset.top
			},
			parent: this._getParentOffset(),
			relative: this._getRelativeOffset() //This is a relative to absolute position minus the actual position calculation - only used for relative positioned helper
		});

		//Generate the original position
		this.originalPosition = this._generatePosition(event);
		this.originalPageX = event.pageX;
		this.originalPageY = event.pageY;

		//Adjust the mouse offset relative to the helper if 'cursorAt' is supplied
		(o.cursorAt && this._adjustOffsetFromHelper(o.cursorAt));

		//Cache the former DOM position
		this.domPosition = { prev: this.currentItem.prev()[0], parent: this.currentItem.parent()[0] };

		//If the helper is not the original, hide the original so it's not playing any role during the drag, won't cause anything bad this way
		if(this.helper[0] != this.currentItem[0]) {
			this.currentItem.hide();
		}

		//Create the placeholder
		this._createPlaceholder();

		//Set a containment if given in the options
		if(o.containment)
			this._setContainment();

		if(o.cursor) { // cursor option
			if ($('body').css("cursor")) this._storedCursor = $('body').css("cursor");
			$('body').css("cursor", o.cursor);
		}

		if(o.opacity) { // opacity option
			if (this.helper.css("opacity")) this._storedOpacity = this.helper.css("opacity");
			this.helper.css("opacity", o.opacity);
		}

		if(o.zIndex) { // zIndex option
			if (this.helper.css("zIndex")) this._storedZIndex = this.helper.css("zIndex");
			this.helper.css("zIndex", o.zIndex);
		}

		//Prepare scrolling
		if(this.scrollParent[0] != document && this.scrollParent[0].tagName != 'HTML')
			this.overflowOffset = this.scrollParent.offset();

		//Call callbacks
		this._trigger("start", event, this._uiHash());

		//Recache the helper size
		if(!this._preserveHelperProportions)
			this._cacheHelperProportions();


		//Post 'activate' events to possible containers
		if(!noActivation) {
			 for (var i = this.containers.length - 1; i >= 0; i--) { this.containers[i]._trigger("activate", event, self._uiHash(this)); }
		}

		//Prepare possible droppables
		if($.ui.ddmanager)
			$.ui.ddmanager.current = this;

		if ($.ui.ddmanager && !o.dropBehaviour)
			$.ui.ddmanager.prepareOffsets(this, event);

		this.dragging = true;

		this.helper.addClass("ui-sortable-helper");
		this._mouseDrag(event); //Execute the drag once - this causes the helper not to be visible before getting its correct position
		return true;

	},

	_mouseDrag: function(event) {

		//Compute the helpers position
		this.position = this._generatePosition(event);
		this.positionAbs = this._convertPositionTo("absolute");

		if (!this.lastPositionAbs) {
			this.lastPositionAbs = this.positionAbs;
		}

		//Do scrolling
		if(this.options.scroll) {
			var o = this.options, scrolled = false;
			if(this.scrollParent[0] != document && this.scrollParent[0].tagName != 'HTML') {

				if((this.overflowOffset.top + this.scrollParent[0].offsetHeight) - event.pageY < o.scrollSensitivity)
					this.scrollParent[0].scrollTop = scrolled = this.scrollParent[0].scrollTop + o.scrollSpeed;
				else if(event.pageY - this.overflowOffset.top < o.scrollSensitivity)
					this.scrollParent[0].scrollTop = scrolled = this.scrollParent[0].scrollTop - o.scrollSpeed;

				if((this.overflowOffset.left + this.scrollParent[0].offsetWidth) - event.pageX < o.scrollSensitivity)
					this.scrollParent[0].scrollLeft = scrolled = this.scrollParent[0].scrollLeft + o.scrollSpeed;
				else if(event.pageX - this.overflowOffset.left < o.scrollSensitivity)
					this.scrollParent[0].scrollLeft = scrolled = this.scrollParent[0].scrollLeft - o.scrollSpeed;

			} else {

				if(event.pageY - $(document).scrollTop() < o.scrollSensitivity)
					scrolled = $(document).scrollTop($(document).scrollTop() - o.scrollSpeed);
				else if($(window).height() - (event.pageY - $(document).scrollTop()) < o.scrollSensitivity)
					scrolled = $(document).scrollTop($(document).scrollTop() + o.scrollSpeed);

				if(event.pageX - $(document).scrollLeft() < o.scrollSensitivity)
					scrolled = $(document).scrollLeft($(document).scrollLeft() - o.scrollSpeed);
				else if($(window).width() - (event.pageX - $(document).scrollLeft()) < o.scrollSensitivity)
					scrolled = $(document).scrollLeft($(document).scrollLeft() + o.scrollSpeed);

			}

			if(scrolled !== false && $.ui.ddmanager && !o.dropBehaviour)
				$.ui.ddmanager.prepareOffsets(this, event);
		}

		//Regenerate the absolute position used for position checks
		this.positionAbs = this._convertPositionTo("absolute");

		//Set the helper position
		if(!this.options.axis || this.options.axis != "y") this.helper[0].style.left = this.position.left+'px';
		if(!this.options.axis || this.options.axis != "x") this.helper[0].style.top = this.position.top+'px';

		//Rearrange
		for (var i = this.items.length - 1; i >= 0; i--) {

			//Cache variables and intersection, continue if no intersection
			var item = this.items[i], itemElement = item.item[0], intersection = this._intersectsWithPointer(item);
			if (!intersection) continue;

			if(itemElement != this.currentItem[0] //cannot intersect with itself
				&&	this.placeholder[intersection == 1 ? "next" : "prev"]()[0] != itemElement //no useless actions that have been done before
				&&	!$.ui.contains(this.placeholder[0], itemElement) //no action if the item moved is the parent of the item checked
				&& (this.options.type == 'semi-dynamic' ? !$.ui.contains(this.element[0], itemElement) : true)
				//&& itemElement.parentNode == this.placeholder[0].parentNode // only rearrange items within the same container
			) {

				this.direction = intersection == 1 ? "down" : "up";

				if (this.options.tolerance == "pointer" || this._intersectsWithSides(item)) {
					this._rearrange(event, item);
				} else {
					break;
				}

				this._trigger("change", event, this._uiHash());
				break;
			}
		}

		//Post events to containers
		this._contactContainers(event);

		//Interconnect with droppables
		if($.ui.ddmanager) $.ui.ddmanager.drag(this, event);

		//Call callbacks
		this._trigger('sort', event, this._uiHash());

		this.lastPositionAbs = this.positionAbs;
		return false;

	},

	_mouseStop: function(event, noPropagation) {

		if(!event) return;

		//If we are using droppables, inform the manager about the drop
		if ($.ui.ddmanager && !this.options.dropBehaviour)
			$.ui.ddmanager.drop(this, event);

		if(this.options.revert) {
			var self = this;
			var cur = self.placeholder.offset();

			self.reverting = true;

			$(this.helper).animate({
				left: cur.left - this.offset.parent.left - self.margins.left + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollLeft),
				top: cur.top - this.offset.parent.top - self.margins.top + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollTop)
			}, parseInt(this.options.revert, 10) || 500, function() {
				self._clear(event);
			});
		} else {
			this._clear(event, noPropagation);
		}

		return false;

	},

	cancel: function() {

		var self = this;

		if(this.dragging) {

			this._mouseUp({ target: null });

			if(this.options.helper == "original")
				this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper");
			else
				this.currentItem.show();

			//Post deactivating events to containers
			for (var i = this.containers.length - 1; i >= 0; i--){
				this.containers[i]._trigger("deactivate", null, self._uiHash(this));
				if(this.containers[i].containerCache.over) {
					this.containers[i]._trigger("out", null, self._uiHash(this));
					this.containers[i].containerCache.over = 0;
				}
			}

		}

		if (this.placeholder) {
			//$(this.placeholder[0]).remove(); would have been the jQuery way - unfortunately, it unbinds ALL events from the original node!
			if(this.placeholder[0].parentNode) this.placeholder[0].parentNode.removeChild(this.placeholder[0]);
			if(this.options.helper != "original" && this.helper && this.helper[0].parentNode) this.helper.remove();

			$.extend(this, {
				helper: null,
				dragging: false,
				reverting: false,
				_noFinalSort: null
			});

			if(this.domPosition.prev) {
				$(this.domPosition.prev).after(this.currentItem);
			} else {
				$(this.domPosition.parent).prepend(this.currentItem);
			}
		}

		return this;

	},

	serialize: function(o) {

		var items = this._getItemsAsjQuery(o && o.connected);
		var str = []; o = o || {};

		$(items).each(function() {
			var res = ($(o.item || this).attr(o.attribute || 'id') || '').match(o.expression || (/(.+)[-=_](.+)/));
			if(res) str.push((o.key || res[1]+'[]')+'='+(o.key && o.expression ? res[1] : res[2]));
		});

		if(!str.length && o.key) {
			str.push(o.key + '=');
		}

		return str.join('&');

	},

	toArray: function(o) {

		var items = this._getItemsAsjQuery(o && o.connected);
		var ret = []; o = o || {};

		items.each(function() { ret.push($(o.item || this).attr(o.attribute || 'id') || ''); });
		return ret;

	},

	/* Be careful with the following core functions */
	_intersectsWith: function(item) {

		var x1 = this.positionAbs.left,
			x2 = x1 + this.helperProportions.width,
			y1 = this.positionAbs.top,
			y2 = y1 + this.helperProportions.height;

		var l = item.left,
			r = l + item.width,
			t = item.top,
			b = t + item.height;

		var dyClick = this.offset.click.top,
			dxClick = this.offset.click.left;

		var isOverElement = (y1 + dyClick) > t && (y1 + dyClick) < b && (x1 + dxClick) > l && (x1 + dxClick) < r;

		if(	   this.options.tolerance == "pointer"
			|| this.options.forcePointerForContainers
			|| (this.options.tolerance != "pointer" && this.helperProportions[this.floating ? 'width' : 'height'] > item[this.floating ? 'width' : 'height'])
		) {
			return isOverElement;
		} else {

			return (l < x1 + (this.helperProportions.width / 2) // Right Half
				&& x2 - (this.helperProportions.width / 2) < r // Left Half
				&& t < y1 + (this.helperProportions.height / 2) // Bottom Half
				&& y2 - (this.helperProportions.height / 2) < b ); // Top Half

		}
	},

	_intersectsWithPointer: function(item) {

		var isOverElementHeight = $.ui.isOverAxis(this.positionAbs.top + this.offset.click.top, item.top, item.height),
			isOverElementWidth = $.ui.isOverAxis(this.positionAbs.left + this.offset.click.left, item.left, item.width),
			isOverElement = isOverElementHeight && isOverElementWidth,
			verticalDirection = this._getDragVerticalDirection(),
			horizontalDirection = this._getDragHorizontalDirection();

		if (!isOverElement)
			return false;

		return this.floating ?
			( ((horizontalDirection && horizontalDirection == "right") || verticalDirection == "down") ? 2 : 1 )
			: ( verticalDirection && (verticalDirection == "down" ? 2 : 1) );

	},

	_intersectsWithSides: function(item) {

		var isOverBottomHalf = $.ui.isOverAxis(this.positionAbs.top + this.offset.click.top, item.top + (item.height/2), item.height),
			isOverRightHalf = $.ui.isOverAxis(this.positionAbs.left + this.offset.click.left, item.left + (item.width/2), item.width),
			verticalDirection = this._getDragVerticalDirection(),
			horizontalDirection = this._getDragHorizontalDirection();

		if (this.floating && horizontalDirection) {
			return ((horizontalDirection == "right" && isOverRightHalf) || (horizontalDirection == "left" && !isOverRightHalf));
		} else {
			return verticalDirection && ((verticalDirection == "down" && isOverBottomHalf) || (verticalDirection == "up" && !isOverBottomHalf));
		}

	},

	_getDragVerticalDirection: function() {
		var delta = this.positionAbs.top - this.lastPositionAbs.top;
		return delta != 0 && (delta > 0 ? "down" : "up");
	},

	_getDragHorizontalDirection: function() {
		var delta = this.positionAbs.left - this.lastPositionAbs.left;
		return delta != 0 && (delta > 0 ? "right" : "left");
	},

	refresh: function(event) {
		this._refreshItems(event);
		this.refreshPositions();
		return this;
	},

	_connectWith: function() {
		var options = this.options;
		return options.connectWith.constructor == String
			? [options.connectWith]
			: options.connectWith;
	},
	
	_getItemsAsjQuery: function(connected) {

		var self = this;
		var items = [];
		var queries = [];
		var connectWith = this._connectWith();

		if(connectWith && connected) {
			for (var i = connectWith.length - 1; i >= 0; i--){
				var cur = $(connectWith[i]);
				for (var j = cur.length - 1; j >= 0; j--){
					var inst = $.data(cur[j], 'sortable');
					if(inst && inst != this && !inst.options.disabled) {
						queries.push([$.isFunction(inst.options.items) ? inst.options.items.call(inst.element) : $(inst.options.items, inst.element).not(".ui-sortable-helper").not('.ui-sortable-placeholder'), inst]);
					}
				};
			};
		}

		queries.push([$.isFunction(this.options.items) ? this.options.items.call(this.element, null, { options: this.options, item: this.currentItem }) : $(this.options.items, this.element).not(".ui-sortable-helper").not('.ui-sortable-placeholder'), this]);

		for (var i = queries.length - 1; i >= 0; i--){
			queries[i][0].each(function() {
				items.push(this);
			});
		};

		return $(items);

	},

	_removeCurrentsFromItems: function() {

		var list = this.currentItem.find(":data(sortable-item)");

		for (var i=0; i < this.items.length; i++) {

			for (var j=0; j < list.length; j++) {
				if(list[j] == this.items[i].item[0])
					this.items.splice(i,1);
			};

		};

	},

	_refreshItems: function(event) {

		this.items = [];
		this.containers = [this];
		var items = this.items;
		var self = this;
		var queries = [[$.isFunction(this.options.items) ? this.options.items.call(this.element[0], event, { item: this.currentItem }) : $(this.options.items, this.element), this]];
		var connectWith = this._connectWith();

		if(connectWith) {
			for (var i = connectWith.length - 1; i >= 0; i--){
				var cur = $(connectWith[i]);
				for (var j = cur.length - 1; j >= 0; j--){
					var inst = $.data(cur[j], 'sortable');
					if(inst && inst != this && !inst.options.disabled) {
						queries.push([$.isFunction(inst.options.items) ? inst.options.items.call(inst.element[0], event, { item: this.currentItem }) : $(inst.options.items, inst.element), inst]);
						this.containers.push(inst);
					}
				};
			};
		}

		for (var i = queries.length - 1; i >= 0; i--) {
			var targetData = queries[i][1];
			var _queries = queries[i][0];

			for (var j=0, queriesLength = _queries.length; j < queriesLength; j++) {
				var item = $(_queries[j]);

				item.data('sortable-item', targetData); // Data for target checking (mouse manager)

				items.push({
					item: item,
					instance: targetData,
					width: 0, height: 0,
					left: 0, top: 0
				});
			};
		};

	},

	refreshPositions: function(fast) {

		//This has to be redone because due to the item being moved out/into the offsetParent, the offsetParent's position will change
		if(this.offsetParent && this.helper) {
			this.offset.parent = this._getParentOffset();
		}

		for (var i = this.items.length - 1; i >= 0; i--){
			var item = this.items[i];

			//We ignore calculating positions of all connected containers when we're not over them
			if(item.instance != this.currentContainer && this.currentContainer && item.item[0] != this.currentItem[0])
				continue;

			var t = this.options.toleranceElement ? $(this.options.toleranceElement, item.item) : item.item;

			if (!fast) {
				item.width = t.outerWidth();
				item.height = t.outerHeight();
			}

			var p = t.offset();
			item.left = p.left;
			item.top = p.top;
		};

		if(this.options.custom && this.options.custom.refreshContainers) {
			this.options.custom.refreshContainers.call(this);
		} else {
			for (var i = this.containers.length - 1; i >= 0; i--){
				var p = this.containers[i].element.offset();
				this.containers[i].containerCache.left = p.left;
				this.containers[i].containerCache.top = p.top;
				this.containers[i].containerCache.width	= this.containers[i].element.outerWidth();
				this.containers[i].containerCache.height = this.containers[i].element.outerHeight();
			};
		}

		return this;
	},

	_createPlaceholder: function(that) {

		var self = that || this, o = self.options;

		if(!o.placeholder || o.placeholder.constructor == String) {
			var className = o.placeholder;
			o.placeholder = {
				element: function() {

					var el = $(document.createElement(self.currentItem[0].nodeName))
						.addClass(className || self.currentItem[0].className+" ui-sortable-placeholder")
						.removeClass("ui-sortable-helper")[0];

					if(!className)
						el.style.visibility = "hidden";

					return el;
				},
				update: function(container, p) {

					// 1. If a className is set as 'placeholder option, we don't force sizes - the class is responsible for that
					// 2. The option 'forcePlaceholderSize can be enabled to force it even if a class name is specified
					if(className && !o.forcePlaceholderSize) return;

					//If the element doesn't have a actual height by itself (without styles coming from a stylesheet), it receives the inline height from the dragged item
					if(!p.height()) { p.height(self.currentItem.innerHeight() - parseInt(self.currentItem.css('paddingTop')||0, 10) - parseInt(self.currentItem.css('paddingBottom')||0, 10)); };
					if(!p.width()) { p.width(self.currentItem.innerWidth() - parseInt(self.currentItem.css('paddingLeft')||0, 10) - parseInt(self.currentItem.css('paddingRight')||0, 10)); };
				}
			};
		}

		//Create the placeholder
		self.placeholder = $(o.placeholder.element.call(self.element, self.currentItem));

		//Append it after the actual current item
		self.currentItem.after(self.placeholder);

		//Update the size of the placeholder (TODO: Logic to fuzzy, see line 316/317)
		o.placeholder.update(self, self.placeholder);

	},

	_contactContainers: function(event) {
		
		// get innermost container that intersects with item 
		var innermostContainer = null, innermostIndex = null;		
		
		
		for (var i = this.containers.length - 1; i >= 0; i--){

			// never consider a container that's located within the item itself 
			if($.ui.contains(this.currentItem[0], this.containers[i].element[0]))
				continue;

			if(this._intersectsWith(this.containers[i].containerCache)) {

				// if we've already found a container and it's more "inner" than this, then continue 
				if(innermostContainer && $.ui.contains(this.containers[i].element[0], innermostContainer.element[0]))
					continue;

				innermostContainer = this.containers[i]; 
				innermostIndex = i;
					
			} else {
				// container doesn't intersect. trigger "out" event if necessary 
				if(this.containers[i].containerCache.over) {
					this.containers[i]._trigger("out", event, this._uiHash(this));
					this.containers[i].containerCache.over = 0;
				}
			}

		}
		
		// if no intersecting containers found, return 
		if(!innermostContainer) return; 

		// move the item into the container if it's not there already
		if(this.containers.length === 1) {
			this.containers[innermostIndex]._trigger("over", event, this._uiHash(this));
			this.containers[innermostIndex].containerCache.over = 1;
		} else if(this.currentContainer != this.containers[innermostIndex]) { 

			//When entering a new container, we will find the item with the least distance and append our item near it 
			var dist = 10000; var itemWithLeastDistance = null; var base = this.positionAbs[this.containers[innermostIndex].floating ? 'left' : 'top']; 
			for (var j = this.items.length - 1; j >= 0; j--) { 
				if(!$.ui.contains(this.containers[innermostIndex].element[0], this.items[j].item[0])) continue; 
				var cur = this.items[j][this.containers[innermostIndex].floating ? 'left' : 'top']; 
				if(Math.abs(cur - base) < dist) { 
					dist = Math.abs(cur - base); itemWithLeastDistance = this.items[j]; 
				} 
			} 

			if(!itemWithLeastDistance && !this.options.dropOnEmpty) //Check if dropOnEmpty is enabled 
				return; 

			this.currentContainer = this.containers[innermostIndex]; 
			itemWithLeastDistance ? this._rearrange(event, itemWithLeastDistance, null, true) : this._rearrange(event, null, this.containers[innermostIndex].element, true); 
			this._trigger("change", event, this._uiHash()); 
			this.containers[innermostIndex]._trigger("change", event, this._uiHash(this)); 

			//Update the placeholder 
			this.options.placeholder.update(this.currentContainer, this.placeholder); 
		
			this.containers[innermostIndex]._trigger("over", event, this._uiHash(this)); 
			this.containers[innermostIndex].containerCache.over = 1;
		} 
	
		
	},

	_createHelper: function(event) {

		var o = this.options;
		var helper = $.isFunction(o.helper) ? $(o.helper.apply(this.element[0], [event, this.currentItem])) : (o.helper == 'clone' ? this.currentItem.clone() : this.currentItem);

		if(!helper.parents('body').length) //Add the helper to the DOM if that didn't happen already
			$(o.appendTo != 'parent' ? o.appendTo : this.currentItem[0].parentNode)[0].appendChild(helper[0]);

		if(helper[0] == this.currentItem[0])
			this._storedCSS = { width: this.currentItem[0].style.width, height: this.currentItem[0].style.height, position: this.currentItem.css("position"), top: this.currentItem.css("top"), left: this.currentItem.css("left") };

		if(helper[0].style.width == '' || o.forceHelperSize) helper.width(this.currentItem.width());
		if(helper[0].style.height == '' || o.forceHelperSize) helper.height(this.currentItem.height());

		return helper;

	},

	_adjustOffsetFromHelper: function(obj) {
		if (typeof obj == 'string') {
			obj = obj.split(' ');
		}
		if ($.isArray(obj)) {
			obj = {left: +obj[0], top: +obj[1] || 0};
		}
		if ('left' in obj) {
			this.offset.click.left = obj.left + this.margins.left;
		}
		if ('right' in obj) {
			this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
		}
		if ('top' in obj) {
			this.offset.click.top = obj.top + this.margins.top;
		}
		if ('bottom' in obj) {
			this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
		}
	},

	_getParentOffset: function() {


		//Get the offsetParent and cache its position
		this.offsetParent = this.helper.offsetParent();
		var po = this.offsetParent.offset();

		// This is a special case where we need to modify a offset calculated on start, since the following happened:
		// 1. The position of the helper is absolute, so it's position is calculated based on the next positioned parent
		// 2. The actual offset parent is a child of the scroll parent, and the scroll parent isn't the document, which means that
		//    the scroll is included in the initial calculation of the offset of the parent, and never recalculated upon drag
		if(this.cssPosition == 'absolute' && this.scrollParent[0] != document && $.ui.contains(this.scrollParent[0], this.offsetParent[0])) {
			po.left += this.scrollParent.scrollLeft();
			po.top += this.scrollParent.scrollTop();
		}

		if((this.offsetParent[0] == document.body) //This needs to be actually done for all browsers, since pageX/pageY includes this information
		|| (this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() == 'html' && $.browser.msie)) //Ugly IE fix
			po = { top: 0, left: 0 };

		return {
			top: po.top + (parseInt(this.offsetParent.css("borderTopWidth"),10) || 0),
			left: po.left + (parseInt(this.offsetParent.css("borderLeftWidth"),10) || 0)
		};

	},

	_getRelativeOffset: function() {

		if(this.cssPosition == "relative") {
			var p = this.currentItem.position();
			return {
				top: p.top - (parseInt(this.helper.css("top"),10) || 0) + this.scrollParent.scrollTop(),
				left: p.left - (parseInt(this.helper.css("left"),10) || 0) + this.scrollParent.scrollLeft()
			};
		} else {
			return { top: 0, left: 0 };
		}

	},

	_cacheMargins: function() {
		this.margins = {
			left: (parseInt(this.currentItem.css("marginLeft"),10) || 0),
			top: (parseInt(this.currentItem.css("marginTop"),10) || 0)
		};
	},

	_cacheHelperProportions: function() {
		this.helperProportions = {
			width: this.helper.outerWidth(),
			height: this.helper.outerHeight()
		};
	},

	_setContainment: function() {

		var o = this.options;
		if(o.containment == 'parent') o.containment = this.helper[0].parentNode;
		if(o.containment == 'document' || o.containment == 'window') this.containment = [
			0 - this.offset.relative.left - this.offset.parent.left,
			0 - this.offset.relative.top - this.offset.parent.top,
			$(o.containment == 'document' ? document : window).width() - this.helperProportions.width - this.margins.left,
			($(o.containment == 'document' ? document : window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top
		];

		if(!(/^(document|window|parent)$/).test(o.containment)) {
			var ce = $(o.containment)[0];
			var co = $(o.containment).offset();
			var over = ($(ce).css("overflow") != 'hidden');

			this.containment = [
				co.left + (parseInt($(ce).css("borderLeftWidth"),10) || 0) + (parseInt($(ce).css("paddingLeft"),10) || 0) - this.margins.left,
				co.top + (parseInt($(ce).css("borderTopWidth"),10) || 0) + (parseInt($(ce).css("paddingTop"),10) || 0) - this.margins.top,
				co.left+(over ? Math.max(ce.scrollWidth,ce.offsetWidth) : ce.offsetWidth) - (parseInt($(ce).css("borderLeftWidth"),10) || 0) - (parseInt($(ce).css("paddingRight"),10) || 0) - this.helperProportions.width - this.margins.left,
				co.top+(over ? Math.max(ce.scrollHeight,ce.offsetHeight) : ce.offsetHeight) - (parseInt($(ce).css("borderTopWidth"),10) || 0) - (parseInt($(ce).css("paddingBottom"),10) || 0) - this.helperProportions.height - this.margins.top
			];
		}

	},

	_convertPositionTo: function(d, pos) {

		if(!pos) pos = this.position;
		var mod = d == "absolute" ? 1 : -1;
		var o = this.options, scroll = this.cssPosition == 'absolute' && !(this.scrollParent[0] != document && $.ui.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);

		return {
			top: (
				pos.top																	// The absolute mouse position
				+ this.offset.relative.top * mod										// Only for relative positioned nodes: Relative offset from element to offset parent
				+ this.offset.parent.top * mod											// The offsetParent's offset without borders (offset + border)
				- ($.browser.safari && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ) * mod)
			),
			left: (
				pos.left																// The absolute mouse position
				+ this.offset.relative.left * mod										// Only for relative positioned nodes: Relative offset from element to offset parent
				+ this.offset.parent.left * mod											// The offsetParent's offset without borders (offset + border)
				- ($.browser.safari && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ) * mod)
			)
		};

	},

	_generatePosition: function(event) {

		var o = this.options, scroll = this.cssPosition == 'absolute' && !(this.scrollParent[0] != document && $.ui.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);

		// This is another very weird special case that only happens for relative elements:
		// 1. If the css position is relative
		// 2. and the scroll parent is the document or similar to the offset parent
		// we have to refresh the relative offset during the scroll so there are no jumps
		if(this.cssPosition == 'relative' && !(this.scrollParent[0] != document && this.scrollParent[0] != this.offsetParent[0])) {
			this.offset.relative = this._getRelativeOffset();
		}

		var pageX = event.pageX;
		var pageY = event.pageY;

		/*
		 * - Position constraining -
		 * Constrain the position to a mix of grid, containment.
		 */

		if(this.originalPosition) { //If we are not dragging yet, we won't check for options

			if(this.containment) {
				if(event.pageX - this.offset.click.left < this.containment[0]) pageX = this.containment[0] + this.offset.click.left;
				if(event.pageY - this.offset.click.top < this.containment[1]) pageY = this.containment[1] + this.offset.click.top;
				if(event.pageX - this.offset.click.left > this.containment[2]) pageX = this.containment[2] + this.offset.click.left;
				if(event.pageY - this.offset.click.top > this.containment[3]) pageY = this.containment[3] + this.offset.click.top;
			}

			if(o.grid) {
				var top = this.originalPageY + Math.round((pageY - this.originalPageY) / o.grid[1]) * o.grid[1];
				pageY = this.containment ? (!(top - this.offset.click.top < this.containment[1] || top - this.offset.click.top > this.containment[3]) ? top : (!(top - this.offset.click.top < this.containment[1]) ? top - o.grid[1] : top + o.grid[1])) : top;

				var left = this.originalPageX + Math.round((pageX - this.originalPageX) / o.grid[0]) * o.grid[0];
				pageX = this.containment ? (!(left - this.offset.click.left < this.containment[0] || left - this.offset.click.left > this.containment[2]) ? left : (!(left - this.offset.click.left < this.containment[0]) ? left - o.grid[0] : left + o.grid[0])) : left;
			}

		}

		return {
			top: (
				pageY																// The absolute mouse position
				- this.offset.click.top													// Click offset (relative to the element)
				- this.offset.relative.top												// Only for relative positioned nodes: Relative offset from element to offset parent
				- this.offset.parent.top												// The offsetParent's offset without borders (offset + border)
				+ ($.browser.safari && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ))
			),
			left: (
				pageX																// The absolute mouse position
				- this.offset.click.left												// Click offset (relative to the element)
				- this.offset.relative.left												// Only for relative positioned nodes: Relative offset from element to offset parent
				- this.offset.parent.left												// The offsetParent's offset without borders (offset + border)
				+ ($.browser.safari && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ))
			)
		};

	},

	_rearrange: function(event, i, a, hardRefresh) {

		a ? a[0].appendChild(this.placeholder[0]) : i.item[0].parentNode.insertBefore(this.placeholder[0], (this.direction == 'down' ? i.item[0] : i.item[0].nextSibling));

		//Various things done here to improve the performance:
		// 1. we create a setTimeout, that calls refreshPositions
		// 2. on the instance, we have a counter variable, that get's higher after every append
		// 3. on the local scope, we copy the counter variable, and check in the timeout, if it's still the same
		// 4. this lets only the last addition to the timeout stack through
		this.counter = this.counter ? ++this.counter : 1;
		var self = this, counter = this.counter;

		window.setTimeout(function() {
			if(counter == self.counter) self.refreshPositions(!hardRefresh); //Precompute after each DOM insertion, NOT on mousemove
		},0);

	},

	_clear: function(event, noPropagation) {

		this.reverting = false;
		// We delay all events that have to be triggered to after the point where the placeholder has been removed and
		// everything else normalized again
		var delayedTriggers = [], self = this;

		// We first have to update the dom position of the actual currentItem
		// Note: don't do it if the current item is already removed (by a user), or it gets reappended (see #4088)
		if(!this._noFinalSort && this.currentItem.parent().length) this.placeholder.before(this.currentItem);
		this._noFinalSort = null;

		if(this.helper[0] == this.currentItem[0]) {
			for(var i in this._storedCSS) {
				if(this._storedCSS[i] == 'auto' || this._storedCSS[i] == 'static') this._storedCSS[i] = '';
			}
			this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper");
		} else {
			this.currentItem.show();
		}

		if(this.fromOutside && !noPropagation) delayedTriggers.push(function(event) { this._trigger("receive", event, this._uiHash(this.fromOutside)); });
		if((this.fromOutside || this.domPosition.prev != this.currentItem.prev().not(".ui-sortable-helper")[0] || this.domPosition.parent != this.currentItem.parent()[0]) && !noPropagation) delayedTriggers.push(function(event) { this._trigger("update", event, this._uiHash()); }); //Trigger update callback if the DOM position has changed
		if(!$.ui.contains(this.element[0], this.currentItem[0])) { //Node was moved out of the current element
			if(!noPropagation) delayedTriggers.push(function(event) { this._trigger("remove", event, this._uiHash()); });
			for (var i = this.containers.length - 1; i >= 0; i--){
				if($.ui.contains(this.containers[i].element[0], this.currentItem[0]) && !noPropagation) {
					delayedTriggers.push((function(c) { return function(event) { c._trigger("receive", event, this._uiHash(this)); };  }).call(this, this.containers[i]));
					delayedTriggers.push((function(c) { return function(event) { c._trigger("update", event, this._uiHash(this));  }; }).call(this, this.containers[i]));
				}
			};
		};

		//Post events to containers
		for (var i = this.containers.length - 1; i >= 0; i--){
			if(!noPropagation) delayedTriggers.push((function(c) { return function(event) { c._trigger("deactivate", event, this._uiHash(this)); };  }).call(this, this.containers[i]));
			if(this.containers[i].containerCache.over) {
				delayedTriggers.push((function(c) { return function(event) { c._trigger("out", event, this._uiHash(this)); };  }).call(this, this.containers[i]));
				this.containers[i].containerCache.over = 0;
			}
		}

		//Do what was originally in plugins
		if(this._storedCursor) $('body').css("cursor", this._storedCursor); //Reset cursor
		if(this._storedOpacity) this.helper.css("opacity", this._storedOpacity); //Reset opacity
		if(this._storedZIndex) this.helper.css("zIndex", this._storedZIndex == 'auto' ? '' : this._storedZIndex); //Reset z-index

		this.dragging = false;
		if(this.cancelHelperRemoval) {
			if(!noPropagation) {
				this._trigger("beforeStop", event, this._uiHash());
				for (var i=0; i < delayedTriggers.length; i++) { delayedTriggers[i].call(this, event); }; //Trigger all delayed events
				this._trigger("stop", event, this._uiHash());
			}
			return false;
		}

		if(!noPropagation) this._trigger("beforeStop", event, this._uiHash());

		//$(this.placeholder[0]).remove(); would have been the jQuery way - unfortunately, it unbinds ALL events from the original node!
		this.placeholder[0].parentNode.removeChild(this.placeholder[0]);

		if(this.helper[0] != this.currentItem[0]) this.helper.remove(); this.helper = null;

		if(!noPropagation) {
			for (var i=0; i < delayedTriggers.length; i++) { delayedTriggers[i].call(this, event); }; //Trigger all delayed events
			this._trigger("stop", event, this._uiHash());
		}

		this.fromOutside = false;
		return true;

	},

	_trigger: function() {
		if ($.Widget.prototype._trigger.apply(this, arguments) === false) {
			this.cancel();
		}
	},

	_uiHash: function(inst) {
		var self = inst || this;
		return {
			helper: self.helper,
			placeholder: self.placeholder || $([]),
			position: self.position,
			originalPosition: self.originalPosition,
			offset: self.positionAbs,
			item: self.currentItem,
			sender: inst ? inst.element : null
		};
	}

});

$.extend($.ui.sortable, {
	version: "1.8.15"
});

})(jQuery);
/**
 * $('#divId').omPanel({ width : 400, height : 400, noHeader : false, title :
 * 'PanelTitle1', content : 'This is panel Content' // innerHTML //collapsible :
 * true,
 * 
 * });
 */
(function() {

    var defaultConfig = {
        title : null,
        iconCls : null,
        width : "auto",
        height : "auto",
        left : null,
        top : null,
        cls : null,
        headerCls : null,
        bodyCls : null,
        style : {},
        href : null,
        cache : true,
        fit : false,
        border : true, //TODO
        doSize : true,
        noHeader : false,
        clickExpand : false,
        content : null,
        url : null,
        collapsible : false,
        minimizable : false,
        maximizable : false,
        closable : false,
        collapsed : false,
        minimized : false,
        maximized : false,
        closed : false,
        tools : [],
        lazyLoad : false,
        loadingMessage : "Loading...",
        extractor : function(_67) {
            var _68 = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
            var _69 = _68.exec(_67);
            if (_69) {
                return _69[1];
            } else {
                return _67;
            }
        },
        onLoad : function() {
        },
        onBeforeOpen : function() {
        },
        onOpen : function() {
        },
        onBeforeClose : function() {
        },
        onClose : function() {
        },
        onBeforeDestroy : function() {
        },
        onDestroy : function() {
        },
        onResize : function(_6a, _6b) {
        },
        onMove : function(_6c, top) {
        },
        onMaximize : function() {
        },
        onRestore : function() {
        },
        onMinimize : function() {
        },
        onBeforeCollapse : function() {
        },
        onBeforeExpand : function() {
        },
        onCollapse : function() {
        },
        onExpand : function() {
        }
    };
    function wrapInPanel(self) {
        var panel = $(self).addClass('om-panel-body').wrap(
                '<div class="om-panel"></div>').parent();
        return panel;
    };

    function render(self) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        var styles = panel.attr('style');
        if ((styles && styles.indexOf('width') != -1 )|| options.width == 'auto') {
        	//just use it, do nothing 
        } else if (options.width == 'fit') {
        	panel.css('width', '100%');
        } else if ( !!options.width ){
        	//other height value, use it anyway
            panel.css('width', options.width);
        }
        if ((styles && styles.indexOf('height') != -1 )|| options.height == 'auto') {
        	//just use it, do nothing 
        } else if (options.height == 'fit') {
        	panel.css('height', '100%');
        } else if ( !!options.height ){
        	//other height value, use it anyway
            panel.css('height', options.height);
        }
        renderHeader(self);
        renderBody(self);
    };

    function renderHeader(self) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        if (options.title && !options.noHeader) {
            var header = $('<div class="om-panel-header"></div>').prependTo(
                    panel);
            var title = $('<div class="om-panel-title"></div>')
                    .appendTo(header).html(options.title);
            
            if (options.clickExpand) {
                header.css('cursor', 'pointer');
            }
            if (options.iconCls) {
                title.addClass('om-panel-with-icon');
                var icon = $('<div class="om-panel-icon"></div>').appendTo(
                        header);
                icon.addClass(options.iconCls);
            }
            var tools = $('<div class="om-panel-tool"></div>').appendTo(header);
            var closeIcon, minIcon, maxIcon, collapseIcon;
            if (contain(options.tools, 'collapse')) {
                minIcon = $('<div class="om-panel-tool-collapse"></div>')
                        .prependTo(tools);
            }
            if (contain(options.tools, 'min')) {
                minIcon = $('<div class="om-panel-tool-min"></div>').prependTo(
                        tools);
            }
            if (contain(options.tools, 'max')) {
                maxIcon = $('<div class="om-panel-tool-max"></div>').prependTo(
                        tools);
            }
            if (options.closable || contain(options.tools, 'close')) {
                closeIcon = $('<div class="om-panel-tool-close"></div>')
                        .prependTo(tools);
            }
        }

        function contain(array, key) {
            var i = array.length;
            while (i--)
                if (array[i] == key) {
                    return true;
                }
            return false;
        };

    };
    function renderBody(self) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        var body = panel.find('>.om-panel-body');
        
        if (options.noHeader || !options.title) {
            body.addClass('om-panel-body-noheader');
        } else {
            body.removeClass('om-panel-body-noheader');
        }
        // apply content
        if (!options.lazyLoad ) {
            _reload(self);
        }
        // border
        if (options.border == false) {
            body.addClass('om-panel-body-noborder');
        } else {
            body.removeClass('om-panel-body-noborder');
        }
        
        // adjust height.
        var styles = body.attr('style');
        if (( styles && styles.indexOf('height') != -1 ) || options.height == 'auto' ) {
        	//just leave it , do nothing
        } else {
        	// fit the parent
        	var pwm = body.outerHeight() - body.height();// paddings + margins + line-width
        	body.css('height', panel.innerHeight() - panel.find('>.om-panel-header').outerHeight() - pwm);
        }
        // adjust margins
        panel.css({
            'marginTop' : body.css('marginTop'),
            'marginRight' : body.css('marginRight'),
            'marginBottom' : body.css('marginBottom'),
            'marginLeft' : body.css('marginLeft')
        });
        body.css({
            'marginTop' : 0,
            'marginRight' : 0,
            'marginBottom' : 0,
            'marginLeft' : 0
        });

    };

    function afterRender(self) {
        // handle panel status
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        if (options.closed == true) {
            $(self).omPanel('close');
        } else if (options.maximized == true) {
            $(self).omPanel('maximize');
        } else if (options.minimized == true) {
            $(self).omPanel('minimize');
        }
        if (options.collapsed == true) {
            $(self).omPanel('collapse');
        }
        
    };

    function buildEvent(self) {
        // attachEvents
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        var tools = panel.find('>div.om-panel-header >div.om-panel-tool');
        tools.find('.om-panel-tool-close').click(function() {
            $(self).omPanel('close', true);
            return false;
        });
        tools.find('.om-panel-tool-max').click(function() {
            alert('not implemented yet!');
            return false;
        });
        var minimize = tools.find('.om-panel-tool-min').click(function() {
            alert('not implemented yet!');
            return false;
        });
        tools.find('.om-panel-tool-collapse').click(function() {
            if (options.collapsed == true) {
                $(self).omPanel('expand', true);
            } else {
                $(self).omPanel('collapse', true);
            }
            return false;
        });
        //click header to expand or collapse
        if (options.clickExpand) {
            panel.find('>div.om-panel-header').click(function() {
                if (options.collapsed == true) {
                    $(self).omPanel('expand', true);
                } else {
                    $(self).omPanel('collapse', true);
                }
                return false;
            });
        }
        
    };

    // private methods
    // show and hide
    function _show(self, anim, speed) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        if (options.onBeforeOpen.call(self) == false) {
            return false;
        }
        var isLoaded = $.data(self, "panel").isLoaded;
        if (!isLoaded) {
            _reload(self);
        }
        if (anim == true) {
            panel.fadeIn(speed || 'normal', function() {
                options.closed = false;
                options.minimized = false;
                options.onOpen.call(self);
            });
        } else {
            panel.show();
            options.closed = false;
            options.minimized = false;
            options.onOpen.call(self);
        }

    };
    function _hide(self, anim, speed) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        if (options.onBeforeClose.call(self) == false) {
            return false;
        }
        if (anim == true) {
            panel.fadeOut(speed || 'normal', function() {
                options.closed = true;
                options.onClose.call(self);
            });
        } else {
            panel.hide();
            options.closed = true;
            options.onClose.call(self);
        }

    };

    // collapse and expand
    function _collapse(self, anim, speed) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        var collapse = panel.find('>div.om-panel-header div.om-panel-tool-collapse');
        if (collapse.length == 0) {
            return;
        }
        if (options.onBeforeCollapse.call(self) == false) {
            return false;
        }

        collapse.removeClass('om-panel-tool-collapse').addClass(
                'om-panel-tool-expand');
        if (anim) {
            panel.find('>div.om-panel-body').slideUp(speed || 'slow', function() {
                options.collapsed = true;
                options.onCollapse.call(self);
            });
        } else {
            panel.find('div.om-panel-body').hide();
            options.collapsed = true;
            options.onCollapse.call(self);
        }

    };
    function _expand(self, anim, speed) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        var expand = panel.find('div.om-panel-tool-expand');
        if (expand.length == 0) {
            reutrn;
        }
        if (options.onBeforeExpand.call(self) == false) {
            return false;
        }

        expand.removeClass('om-panel-tool-expand').addClass(
                'om-panel-tool-collapse');

        if (anim) {
            panel.find('div.om-panel-body').slideDown(speed || 'normal', function() {
                options.collapsed = false;
                options.onExpand.call(self);
            });
        } else {
            panel.find('div.om-panel-body').show();
            options.collapsed = false;
            options.onExpand.call(self);
        }

    };

    // title and content
    function _setTitle(self, title) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        options.title = title;
        panel.find('div.om-panel-title').html(title);
    };
    function _setDataSource(self, ds, isAjax) {
        if (!ds) {
            return false;
        }
        var options = $.data(self, 'panel').options;
        options.content = options.url = null;
        isAjax == true ? options.url = ds : options.content = ds;
        $.data(self, 'panel').isLoaded = false;
    };
    
    //使用options.content和options.url重新装载数据
    function _reload(self) {
        var data = $.data(self, 'panel');
        var options = data.options,
            panel = data.panel;
        var body = panel.find('>.om-panel-body');
        if ($.data(self, 'panel').isLoading ){
            return;
        };
        $.data(self, 'panel').isLoading = true;
        if (!!$.trim(options.content)) {
            //content 为文本
            body.empty();
            body.html(options.content);
            $.data(self, 'panel').isLoaded = true;
            $.data(self, 'panel').isLoading = false;
            options.onLoad.call(self);
        } else if (options.url) {
            body.load(options.url, function(res,status,xhr){
                if (status == 'success'){
                    $.data(self, "panel").isLoaded = true;
                    $.data(self, 'panel').isLoading = false;
                    options.onLoad.call(self);
                }
            });
        } else {
            $.data(self, 'panel').isLoading = false;
        }
    }

    // ---end private methods

    // public methods
    var publicMethods = {
        options : function(p) {
            var options = $.data(this[0], "panel").options;
            if (p) {
                $.extend(options, p);
            }
            return options;
        },
        panel : function() {
            return $.data(this[0], "panel").panel;
        },
        header : function() {
            return $.data(this[0], "panel").panel.find(">div.om-panel-header");
        },
        body : function() {
            return $.data(this[0], "panel").panel.find(">div.om-panel-body");
        },
        setTitle : function(title) {
            return this.each(function() {
                _setTitle(this, title);
            });
        },
        setDataSource : function(ds, isAjax) {
            return this.each(function() {
                _setDataSource(this, ds, isAjax);
            });
        },
        open : function(anim, speed) {
            return this.each(function() {
                _show(this, anim, speed);
            });
        },
        close : function(anim, speed) {
            return this.each(function() {
                _hide(this, anim, speed);
            });
        },
        collapse : function(anim, speed) {
            return this.each(function() {
                _collapse(this, anim, speed);
            });
        },
        expand : function(anim, speed) {
            return this.each(function() {
                _expand(this, anim, speed);
            });
        },

        reload : function(ds, isAjax) {
            return this.each(function() {
                $.data(this, "panel").isLoaded = false;
                _setDataSource(this, ds, isAjax);
                _reload(this);
            });
        },
        
        destroy : function(_60) {
            return this.each(function() {
                _30(this, _60);
            });
        },
        resize : function(_62) {
            return this.each(function() {
                resize(this, _62);
            });
        },
        move : function(_63) {
            return this.each(function() {
                _a(this, _63);
            });
        },
        maximize : function() {
            return this.each(function() {
                _2a(this);
            });
        },
        minimize : function() {
            return this.each(function() {
                _46(this);
            });
        },
        restore : function() {
            return this.each(function() {
                _4a(this);
            });
        }

    };

    $.fn.omPanel = function(p) {
        if (p && typeof (p) == 'string') {
            if (publicMethods[p]) {
                return publicMethods[p].apply(this, Array.prototype.slice.call(
                        arguments, 1));
            }
            return null;
        }
        return this.each(function() {
            var pData = $.data(this, 'panel');
            var options;
            if (pData) {
                $.extend(pData.options, p);
            } else {
                options = $.extend({}, defaultConfig, p);
                pData = $.data(this, 'panel', {
                    options : options,
                    panel : wrapInPanel(this),
                    isLoaded : false,
                    isLoading : false
                });
            }
            render(this);
            afterRender(this);
            buildEvent(this);
        });
    };

})(jQuery);/*
 * operamasks-ui validate 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 */

;(function($) {

/**
 * @class
 * <div>
 * <b>简介</b><br/>
 * validate() 可以方便地对Html Form进行校验. 
 * </div>
 * <div>
 *  <pre>
 * 1、本校验框架提供了丰富的校验规则，满足常用的校验需求。
 *    内置的校验规则有required, min, max, minlength, maxlength, email,url,date,number, 
 *    creditcard,accept(验证可以输入的后缀),equalTo(和已有的属性做对比，对密码重新输入适用)
 *    
 * 2、提供灵活的校验信息展现风格，你可以让信息展现在校验元素尾部，也可以让它展现在你想要的任何div里面，
 *    对于空间比较吃紧的form表单，也可以采用鼠标移动到提示图标悬浮显示提示信息的方式。这些主要通过配置
 *    errorPlacement(定制错误信息显示)、showErrors(自定义消息处理，onblur/keyup触发，针对当前元素)
 *    只要你够强大，它就有多强大。
 *  
 * 3、提供了灵活的接口用于增加校验规则，当我们提供的校验规则不能满足您的需求的时候，你可以自定义校验
 *    规则，通过$.validator.addMethod("hasreg", function(value) {return true/false;},"message")的方式实现
 *   </pre>
 * </div>
 * <b>如何使用</b><br/>
 * 1、基本结构
 *    <pre>
 *      $("#reg").validate({
 *         onkeyup : true,
 *         rules : {
 *           password : {
                        required : true,
                        minlength : 5
             }
 *         },
 *         messages : {
 *           password : {
                        required : '密码必须输入',
                        minlength : '密码不能小于5位'
             }
 *         }
 *      });
 *     
 *    上面就是基本的校验结构，reg是需要校验的form的id，password是需要校验的字段的name
 *    (<span style="color:red;">必须配置name，否则将不能校验</span>)rules和messages是成对出现的，如果只配置了rules没有配置messages，
 *    系统将使用默认提示信息。
 * 2、自定义校验规则
 *      $.validator.addMethod("hasreg", function(value) {
 *              return value != 'admin';
 *       }, '此用户已经被注册');
 *       //模拟用户名是否已经被注册，真实的场景应该是使用ajax向后台发送请求，然后后台返回结果，
 *       //需要注意的是必须使用同步请求，异步请求将无效。
 *    
 *    现在就已经有了一个新的rules “hasreg”，它的使用同系统内置的规则一样，在需要校验
 *    的字段里面配置hasreg:true，因为messages已经配置，故可以不写。
 * 3、自定义信息展示
 *    框架默认的是在内容后面增加一个&lt;label for="username" generated="true" class="error"&gt;请输入用
 *    户名&lt;/label&gt;，同时在校验的元素上面增加class="error"属性，如果你想要一些效果，请编写error样式。
 *    如果这种提示方式不是你想要的，或者你不希望它来包装你的提示信息，你需要用到两个方法
 *    errorPlacement、showErrors鉴于这两个方法的复杂性和强自定义性，下面对他们做一个详细的说明。
 *    1)、errorPlacement
 *        错误信息展示，有两个参数error和element，error是生成的错误对象；element为当前元素。
 *        errorPlacement : function(error, element) { 
 *                   if(error){    //error存在的时候
 *                       $('#showMsg').html(error); //showMsg为你自定义的信息显示区域的id。
 *                   }
 *               }
 *     2)、showErrors
 *         它负责处理事件，当错误产生的时候它会将错误信息显示出来，错误消除时它负责将错误信息隐藏
 *         如果自定义了错误展示，而另外一部分还是默认的行为，则请调用this.defaultShowErrors()
 *         以便默认行为生效，它也有两个参数，分别是errorMap和errorList，是所有错误的集合。
 *         showErrors: function(errorMap, errorList) {
 *                   if(errorList && errorList.length > 0){  //如果存在错误信息
 *                       $.each(errorList,function(index,obj){ //遍历错误信息 ，index为错误信息的索引号，
 *                                                             //obj为当前错误信息对象
 *                           var msg = this.message;           //获取当前错误信息文本
 *                           //这里编写一些代码处理你的错误信息
 *                      });
 *                   }else{
 *                       //错误信息已经消除，此处要写隐藏错误信息的代码，
 *                       //通过this.currentElements获取当前对象
 *                   }
 *                   this.defaultShowErrors();  //如果还有默认的行为，请调用它。
 *               }
 * </pre>
 * 
 * @name validate
 * @constructor
 * @param options 标准config对象
 */
$.extend($.fn, {
	// http://docs.jquery.com/Plugins/Validation/validate
	validate: function( options ) {

		// if nothing is selected, return nothing; can't chain anyway
		if (!this.length) {
			options && options.debug && window.console && console.warn( "nothing selected, can't validate, returning nothing" );
			return;
		}

		// check if a validator for this form was already created
		var validator = $.data(this[0], 'validator');
		if ( validator ) {
			return validator;
		}

		validator = new $.validator( options, this[0] );
		$.data(this[0], 'validator', validator);

		if ( validator.settings.onsubmit ) {

			// allow suppresing validation by adding a cancel class to the submit button
			this.find("input, button").filter(".cancel").click(function() {
				validator.cancelSubmit = true;
			});

			// when a submitHandler is used, capture the submitting button
			if (validator.settings.submitHandler) {
				this.find("input, button").filter(":submit").click(function() {
					validator.submitButton = this;
				});
			}

			// validate the form on submit
			this.submit( function( event ) {
				if ( validator.settings.debug )
					// prevent form submit to be able to see console output
					event.preventDefault();

				function handle() {
					if ( validator.settings.submitHandler ) {
						if (validator.submitButton) {
							// insert a hidden input as a replacement for the missing submit button
							var hidden = $("<input type='hidden'/>").attr("name", validator.submitButton.name).val(validator.submitButton.value).appendTo(validator.currentForm);
						}
						validator.settings.submitHandler.call( validator, validator.currentForm );
						if (validator.submitButton) {
							// and clean up afterwards; thanks to no-block-scope, hidden can be referenced
							hidden.remove();
						}
						return false;
					}
					return true;
				}

				// prevent submit for invalid forms or custom submit handlers
				if ( validator.cancelSubmit ) {
					validator.cancelSubmit = false;
					return handle();
				}
				if ( validator.form() ) {
					if ( validator.pendingRequest ) {
						validator.formSubmitted = true;
						return false;
					}
					return handle();
				} else {
					validator.focusInvalid();
					return false;
				}
			});
		}

		return validator;
	},
	// http://docs.jquery.com/Plugins/Validation/valid
	valid: function() {
        if ( $(this[0]).is('form')) {
            return this.validate().form();
        } else {
            var valid = true;
            var validator = $(this[0].form).validate();
            this.each(function() {
				valid &= validator.element(this);
            });
            return valid;
        }
    },
	// attributes: space seperated list of attributes to retrieve and remove
	removeAttrs: function(attributes) {
		var result = {},
			$element = this;
		$.each(attributes.split(/\s/), function(index, value) {
			result[value] = $element.attr(value);
			$element.removeAttr(value);
		});
		return result;
	},
	// http://docs.jquery.com/Plugins/Validation/rules
	rules: function(command, argument) {
		var element = this[0];

		if (command) {
			var settings = $.data(element.form, 'validator').settings;
			var staticRules = settings.rules;
			var existingRules = $.validator.staticRules(element);
			switch(command) {
			case "add":
				$.extend(existingRules, $.validator.normalizeRule(argument));
				staticRules[element.name] = existingRules;
				if (argument.messages)
					settings.messages[element.name] = $.extend( settings.messages[element.name], argument.messages );
				break;
			case "remove":
				if (!argument) {
					delete staticRules[element.name];
					return existingRules;
				}
				var filtered = {};
				$.each(argument.split(/\s/), function(index, method) {
					filtered[method] = existingRules[method];
					delete existingRules[method];
				});
				return filtered;
			}
		}

		var data = $.validator.normalizeRules(
		$.extend(
			{},
			$.validator.metadataRules(element),
			$.validator.classRules(element),
			$.validator.attributeRules(element),
			$.validator.staticRules(element)
		), element);

		// make sure required is at front
		if (data.required) {
			var param = data.required;
			delete data.required;
			data = $.extend({required: param}, data);
		}

		return data;
	}
});

// Custom selectors
$.extend($.expr[":"], {
	// http://docs.jquery.com/Plugins/Validation/blank
	blank: function(a) {return !$.trim("" + a.value);},
	// http://docs.jquery.com/Plugins/Validation/filled
	filled: function(a) {return !!$.trim("" + a.value);},
	// http://docs.jquery.com/Plugins/Validation/unchecked
	unchecked: function(a) {return !a.checked;}
});

// constructor for validator
$.validator = function( options, form ) {
	this.settings = $.extend( true, {}, $.validator.defaults, options );
	this.currentForm = form;
	this.init();
};

$.validator.format = function(source, params) {
	if ( arguments.length == 1 )
		return function() {
			var args = $.makeArray(arguments);
			args.unshift(source);
			return $.validator.format.apply( this, args );
		};
	if ( arguments.length > 2 && params.constructor != Array  ) {
		params = $.makeArray(arguments).slice(1);
	}
	if ( params.constructor != Array ) {
		params = [ params ];
	}
	$.each(params, function(i, n) {
		source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
	});
	return source;
};

$.extend($.validator, {

	defaults: {
	    /**
	     * 键值对的校验错误信息.键是元素的name属性，值是错误信息的组合对象。<br/>
	     * @name validate#messages
	     * @type JSON
	     * @default {}
	     * @example
	     * $(".selector").validate({
         *  rules: {
         *    name: {
         *      required: true,
         *      minlength: 2
         *    }
         *  },
         *  messages: {
         *    name: {
         *      required: "We need your email address to contact you",
         *      minlength: jQuery.format("At least {0} characters required!")
         *      //这里的{0}就是minlength定义的2
         *    }
         *  }
         *})
	     */
		messages: {},
		/**
         * 对错误消息进行分组配置，通过errorPlacement处理分组消息的显示。<br/>
         * @name validate#groups
         * @type JSON
         * @default {}
         * @example
         * $("#myform").validate({
         *     groups: {
         *       username: "fname lname"
         *     },
         *     errorPlacement: function(error, element) {
         *        if (element.attr("name") == "fname" 
         *                    || element.attr("name") == "lname" ){
         *          error.insertAfter("#lastname");
         *        }else{
         *          error.insertAfter(element);
         *        }
         *      },
         *    }) //将fname和lname的错误信息统一显示在lastname元素后面
         */
		groups: {},
		
		/**
         * 键值对的校验规则.键是元素的name属性，值是校验规则的组合对象，每一个规则都可以绑定一个依赖对象，<br/>
         * 通过depends设定，只有依赖对象成立才会执行验证<br/>
         * @name validate#rules
         * @type JSON
         * @default {}
         * @example
         * $(".selector").validate({
         *  rules: {
         *    contact: {
         *      required: true,
         *      email: { 
         *        depends: function(element) {
         *          return $("#contactform_email:checked")
         *          //email校验的前提是contactform_email被选中
         *        }
         *      }
         *    }
         *  }
         *})
         */
		rules: {},
		
		/**
         * 指定校验错误显示标签的class名称，此class也将添加在校验的元素上面。<br/>
         * @name validate#errorClass
         * @type String
         * @default 'error'
         * @example
         * $(".selector").validate({
         *      errorClass: "invalid"
         *   })
         */
		errorClass: 'error',
		
		/**
         * 指定校验成功没有任何错误后加到元素的class名称<br/>
         * @name validate#validClass
         * @type String
         * @default 'valid'
         * @example
         * $(".selector").validate({
         *      validClass: "success"
         *   })
         */
		validClass: 'valid',
		
		/**
		 * 指定校验成功没有任何错误后加到提示元素上面的样式名称<br/>
		 * 和validClass的区别是它只加在提示元素上面，而不对校验的对象做任何变动。
		 * @name validate#success
		 * @type String
		 * @default 无
		 * @example
		 * $(".selector").validate({
		 *      success: "valid"
		 *   })
		 */
		success: 'valid',
		
		/**
         * 指定显示校验错误信息的html标签名称<br/>
         * @name validate#errorElement
         * @type String
         * @default 'label'
         * @example
         * $(".selector").validate({
         *      errorElement: "em"
         *   })
         */
		errorElement: 'label',
		
		/**
         * 校验错误的时候是否将聚焦元素。<br/>
         * @name validate#focusInvalid
         * @type Boolean
         * @default true
         * @example
         * $(".selector").validate({
         *      focusInvalid: false
         *   })
         */
		focusInvalid: true,
		
		/**
         * 获得焦点的时候是否清除错误提示，这种清除是针对所有元素的，<br/>
         * 如果设置为true，则必须将focusInvalid设置为false，否则将没有校验效果。
         * @name validate#focusCleanup
         * @type Boolean
         * @default false
         * @example
         * $(".selector").validate({
         *      focusInvalid: false, //必须设置
         *      focusCleanup: true
         *   })
         */
		focusCleanup: false,
		
		/**
         * 包含错误信息的容器，根据校验结果隐藏或者显示错误容器。<br />
         * 与 errorLabelContainer 属性的区别是这个属性一般包括后者。
         * @name validate#errorContainer
         * @type Object
         * @default $( [] )
         * @example
         * $("#myform").validate({
         *      errorContainer: "#messageBox1, #messageBox2", 
         *      //可以配置多个容器，这里的messageBox2元素没有被包装处理，只是错误发生的时候显示和隐藏此元素
         *      errorLabelContainer: "#messageBox1 ul",
         *      wrapper: "li",
         *   })
         */
		errorContainer: $( [] ),
		
		/**
         * 显示错误信息的容器，根据校验结果隐藏或者显示错误容器。
         * @name validate#errorLabelContainer
         * @type Object
         * @default $( [] )
         * @example
         * $("#myform").validate({
         *      errorLabelContainer: "#messageBox",
         *      wrapper: "li",
         *   })
         *   //messageBox为容器的id
         */
		errorLabelContainer: $( [] ),
		
		/**
         * 是否在提交时校验表单，如果设置为false，则提交的时候不校验表单，<br/>
         * 但是其它keyup、onblur等事件校验不受影响.
         * @name validate#onsubmit
         * @type Boolean
         * @default true
         * @example
         * $(".selector").validate({
         *      onsubmit: false
         *   })
         */
		onsubmit: true,
		
		/**
         * 校验时忽略指定的元素，可以配置需要校验的元素id和样式名称等jquery识别的选择器。
         * @name validate#ignore
         * @type String
         * @default null
         * @example
         * $("#myform").validate({
         *      ignore: ".ignore" 
         *      //此处还可以配置input[type='password']、#id等jquery的选择器
         *   })
         */
		ignore: [],
		ignoreTitle: false,
		
		onfocusin: function(element) {
			this.lastActive = element;

			// hide error label and remove error class on focus if enabled
			if ( this.settings.focusCleanup && !this.blockFocusCleanup ) {
				this.settings.unhighlight && this.settings.unhighlight.call( this, element, this.settings.errorClass, this.settings.validClass );
				this.addWrapper(this.errorsFor(element)).hide();
			}
		},
		
		/**
         * 在blur事件发生时是否进行校验，如果没有输入任何值，则将忽略校验。
         * @name validate#onfocusout
         * @type Boolean
         * @default true
         * @example
         * $(".selector").validate({
         *      onfocusout: false
         *   })
         */
		onfocusout: function(element) {
			if ( !this.checkable(element) && (element.name in this.submitted || !this.optional(element)) ) {
				this.element(element);
			}
		},
		
		/**
         * 在keyup事件发生时是否进行校验。
         * @name validate#onkeyup
         * @type Boolean
         * @default true
         * @example
         * $(".selector").validate({
         *      onkeyup: false
         *   })
         */
		onkeyup: function(element) {
			if ( element.name in this.submitted || element == this.lastElement ) {
				this.element(element);
			}
		},
		
		/**
         * 在checkbox和radio的click事件发生后是否进行校验。
         * @name validate#onclick
         * @type Boolean
         * @default true
         * @example
         * $(".selector").validate({
         *      onclick: false
         *   })
         */
		onclick: function(element) {
			// click on selects, radiobuttons and checkboxes
			if ( element.name in this.submitted )
				this.element(element);
			// or option elements, check parent select in that case
			else if (element.parentNode.name in this.submitted)
				this.element(element.parentNode);
		},
		highlight: function(element, errorClass, validClass) {
			if (element.type === 'radio') {
				this.findByName(element.name).addClass(errorClass).removeClass(validClass);
			} else {
				$(element).addClass(errorClass).removeClass(validClass);
			}
		},
		unhighlight: function(element, errorClass, validClass) {
			if (element.type === 'radio') {
				this.findByName(element.name).removeClass(errorClass).addClass(validClass);
			} else {
				$(element).removeClass(errorClass).addClass(validClass);
			}
		}
		
		/**
         * 定制错误信息显示的回调方法。该方法有两个参数，第一个参数是错误信息的元素，第二个是触发校验错误的源元素。
         * @name validate#errorPlacement
         * @type Function
         * @default 无
         * @example
         * $("#myform").validate({
         *     errorPlacement: function(error, element) {
         *        error.appendTo( element.parent("td").next("td") );
         *      }
         *    })
         */

		/**
         * 定制校验通过后表单提交前的回调方法，用来替换默认提交，一般是AJax提交方式需要使用到。
         * @type Function
         * @name validate#submitHandler
         * @param form 当前表单对象
         * @example
         * $(".selector").validate({
         *      submitHandler: function(form) {
         *       $(form).ajaxSubmit();
         *      }
         *   })
         */
         
         /**
         * 定制表单提交但校验不通过的回调方法。
         * @type Function
         * @name validate#invalidHandler
         * @param form 当前表单对象
         * @param validator 当前校验器对象
         * @example
         * $(".selector").validate({
         *   invalidHandler: function(form, validator) {
         *     var errors = validator.numberOfInvalids();
         *     if (errors) {
         *       var message = errors == 1
         *         ? 'You missed 1 field. It has been highlighted'
         *         : 'You missed ' + errors + ' fields. They have been highlighted';
         *       $("div.error span").html(message);
         *       $("div.error").show();
         *     } else {
         *       $("div.error").hide();
         *     }
         *   }
         *})
         */   
		
		/**
         * 校验选中的表单
         * @name validate#validate
         * @function
         * @returns 当前form的校验对象
         * @example
         *   $("#myform").validate({
         *      //options
         *   });
         */
		
		/**
         * 检查表单是否通过校验
         * @name validate#valid
         * @function
         * @returns Boolean
         * @example
         *   $("#myform").validate();
         *   $("a.check").click(function() {
         *     alert("Valid: " + $("#myform").valid());
         *     return false;
         *   });
         */
		
		/**
         * 针对选中的元素，动态添加删除校验规则的方法，有rules( "add", rules ) 和rules( "remove", [rules] )两种
         * @name validate#rules
         * @function
         * @returns rules Object{Options}
         * @example
         *  $('#username').rules('add',{
         *       minlength:5,
         *       messages: {
         *           minlength: jQuery.format("Please, at least {0} characters are necessary")
         *       }
         *   });
         *   
         * $("#myinput").rules("remove", "min max"); //remove可以配置多个rule，空格隔开
         */
		
		/**
         * 触发表单校验
         * @name validate#form
         * @function
         * @returns Boolean
         * @example
         *  $("#myform").validate().form()
         */
		
		/**
         * 校验选中的element
         * @name validate#element
         * @param element
         * @function
         * @returns Boolean
         * @example
         *  $("#myform").validate().element( "#myselect" );
         */
		
		/**
         * 重置表单，调用此方法将去掉所有提示信息
         * @name validate#resetForm
         * @function
         * @returns 无
         * @example
         *  var validator = $("#myform").validate();
         *  validator.resetForm();
         */
		
		/**
		 *  添加并显示提示信息
		 * @name validate#showErrors
		 * @function
		 * @param Object
		 * @returns 无
		 * @example
		 * var validator = $("#myform").validate();
         * validator.showErrors({"firstname": "I know that your firstname is Pete, Pete!"});
		 */
		
		/**
		 *  统计没有通过校验的元素个数
		 * @name validate#numberOfInvalids
		 * @function
		 * @returns Integer
		 * @example
		 * var validator = $("#myform").validate();
		 * return validator.numberOfInvalids();
		 */
	},

	// http://docs.jquery.com/Plugins/Validation/Validator/setDefaults
	setDefaults: function(settings) {
		$.extend( $.validator.defaults, settings );
	},

	messages: {
		required: "This field is required.",
		remote: "Please fix this field.",
		email: "Please enter a valid email address.",
		url: "Please enter a valid URL.",
		date: "Please enter a valid date.",
		number: "Please enter a valid number.",
		digits: "Please enter only digits.",
		equalTo: "Please enter the same value again.",
		accept: "Please enter a value with a valid extension.",
		maxlength: $.validator.format("Please enter no more than {0} characters."),
		minlength: $.validator.format("Please enter at least {0} characters."),
		rangelength: $.validator.format("Please enter a value between {0} and {1} characters long."),
		range: $.validator.format("Please enter a value between {0} and {1}."),
		max: $.validator.format("Please enter a value less than or equal to {0}."),
		min: $.validator.format("Please enter a value greater than or equal to {0}.")
	},

	autoCreateRanges: false,

	prototype: {

		init: function() {
			this.labelContainer = $(this.settings.errorLabelContainer);
			this.errorContext = this.labelContainer.length && this.labelContainer || $(this.currentForm);
			this.containers = $(this.settings.errorContainer).add( this.settings.errorLabelContainer );
			this.submitted = {};
			this.valueCache = {};
			this.pendingRequest = 0;
			this.pending = {};
			this.invalid = {};
			this.reset();

			var groups = (this.groups = {});
			$.each(this.settings.groups, function(key, value) {
				$.each(value.split(/\s/), function(index, name) {
					groups[name] = key;
				});
			});
			var rules = this.settings.rules;
			$.each(rules, function(key, value) {
				rules[key] = $.validator.normalizeRule(value);
			});

			function delegate(event) {
				var validator = $.data(this[0].form, "validator"),
					eventType = "on" + event.type.replace(/^validate/, "");
				validator.settings[eventType] && validator.settings[eventType].call(validator, this[0] );
			}
			$(this.currentForm)
				.validateDelegate(":text, :password, :file, select, textarea", "focusin focusout keyup", delegate)
				.validateDelegate(":radio, :checkbox, select, option", "click", delegate);

			if (this.settings.invalidHandler)
				$(this.currentForm).bind("invalid-form.validate", this.settings.invalidHandler);
		},

		// http://docs.jquery.com/Plugins/Validation/Validator/form
		form: function() {
			this.checkForm();
			$.extend(this.submitted, this.errorMap);
			this.invalid = $.extend({}, this.errorMap);
			if (!this.valid())
				$(this.currentForm).triggerHandler("invalid-form", [this]);
			this.showErrors();
			return this.valid();
		},

		checkForm: function() {
			this.prepareForm();
			for ( var i = 0, elements = (this.currentElements = this.elements()); elements[i]; i++ ) {
				this.check( elements[i] );
			}
			return this.valid();
		},

		// http://docs.jquery.com/Plugins/Validation/Validator/element
		element: function( element ) {
			element = this.clean( element );
			this.lastElement = element;
			this.prepareElement( element );
			this.currentElements = $(element);
			var result = this.check( element );
			if ( result ) {
				delete this.invalid[element.name];
			} else {
				this.invalid[element.name] = true;
			}
			if ( !this.numberOfInvalids() ) {
				// Hide error containers on last error
				this.toHide = this.toHide.add( this.containers );
			}
			this.showErrors();
			return result;
		},

		// http://docs.jquery.com/Plugins/Validation/Validator/showErrors
		showErrors: function(errors) {
			if(errors) {
				// add items to error list and map
				$.extend( this.errorMap, errors );
				this.errorList = [];
				for ( var name in errors ) {
					this.errorList.push({
						message: errors[name],
						element: this.findByName(name)[0]
					});
				}
				// remove items from success list
				this.successList = $.grep( this.successList, function(element) {
					return !(element.name in errors);
				});
			}
			this.settings.showErrors
				? this.settings.showErrors.call( this, this.errorMap, this.errorList )
				: this.defaultShowErrors();
		},

		// http://docs.jquery.com/Plugins/Validation/Validator/resetForm
		resetForm: function() {
			if ( $.fn.resetForm )
				$( this.currentForm ).resetForm();
			this.submitted = {};
			this.prepareForm();
			this.hideErrors();
			this.elements().removeClass( this.settings.errorClass );
		},

		numberOfInvalids: function() {
			return this.objectLength(this.invalid);
		},

		objectLength: function( obj ) {
			var count = 0;
			for ( var i in obj )
				count++;
			return count;
		},

		hideErrors: function() {
			this.addWrapper( this.toHide ).hide();
		},

		valid: function() {
			return this.size() == 0;
		},

		size: function() {
			return this.errorList.length;
		},

		focusInvalid: function() {
			if( this.settings.focusInvalid ) {
				try {
					$(this.findLastActive() || this.errorList.length && this.errorList[0].element || [])
					.filter(":visible")
					.focus()
					// manually trigger focusin event; without it, focusin handler isn't called, findLastActive won't have anything to find
					.trigger("focusin");
				} catch(e) {
					// ignore IE throwing errors when focusing hidden elements
				}
			}
		},

		findLastActive: function() {
			var lastActive = this.lastActive;
			return lastActive && $.grep(this.errorList, function(n) {
				return n.element.name == lastActive.name;
			}).length == 1 && lastActive;
		},

		elements: function() {
			var validator = this,
				rulesCache = {};

			// select all valid inputs inside the form (no submit or reset buttons)
			return $(this.currentForm)
			.find("input, select, textarea")
			.not(":submit, :reset, :image, [disabled]")
			.not( this.settings.ignore )
			.filter(function() {
				!this.name && validator.settings.debug && window.console && console.error( "%o has no name assigned", this);

				// select only the first element for each name, and only those with rules specified
				if ( this.name in rulesCache || !validator.objectLength($(this).rules()) )
					return false;

				rulesCache[this.name] = true;
				return true;
			});
		},

		clean: function( selector ) {
			return $( selector )[0];
		},

		errors: function() {
			return $( this.settings.errorElement + "." + this.settings.errorClass, this.errorContext );
		},

		reset: function() {
			this.successList = [];
			this.errorList = [];
			this.errorMap = {};
			this.toShow = $([]);
			this.toHide = $([]);
			this.currentElements = $([]);
		},

		prepareForm: function() {
			this.reset();
			this.toHide = this.errors().add( this.containers );
		},

		prepareElement: function( element ) {
			this.reset();
			this.toHide = this.errorsFor(element);
		},

		check: function( element ) {
			element = this.clean( element );

			// if radio/checkbox, validate first element in group instead
			if (this.checkable(element)) {
				element = this.findByName( element.name ).not(this.settings.ignore)[0];
			}

			var rules = $(element).rules();
			var dependencyMismatch = false;
			for (var method in rules ) {
				var rule = { method: method, parameters: rules[method] };
				try {
					var result = $.validator.methods[method].call( this, element.value.replace(/\r/g, ""), element, rule.parameters );

					// if a method indicates that the field is optional and therefore valid,
					// don't mark it as valid when there are no other rules
					if ( result == "dependency-mismatch" ) {
						dependencyMismatch = true;
						continue;
					}
					dependencyMismatch = false;

					if ( result == "pending" ) {
						this.toHide = this.toHide.not( this.errorsFor(element) );
						return;
					}

					if( !result ) {
						this.formatAndAdd( element, rule );
						return false;
					}
				} catch(e) {
					this.settings.debug && window.console && console.log("exception occured when checking element " + element.id
						 + ", check the '" + rule.method + "' method", e);
					throw e;
				}
			}
			if (dependencyMismatch)
				return;
			if ( this.objectLength(rules) )
				this.successList.push(element);
			return true;
		},

		// return the custom message for the given element and validation method
		// specified in the element's "messages" metadata
		customMetaMessage: function(element, method) {
			if (!$.metadata)
				return;

			var meta = this.settings.meta
				? $(element).metadata()[this.settings.meta]
				: $(element).metadata();

			return meta && meta.messages && meta.messages[method];
		},

		// return the custom message for the given element name and validation method
		customMessage: function( name, method ) {
			var m = this.settings.messages[name];
			return m && (m.constructor == String
				? m
				: m[method]);
		},

		// return the first defined argument, allowing empty strings
		findDefined: function() {
			for(var i = 0; i < arguments.length; i++) {
				if (arguments[i] !== undefined)
					return arguments[i];
			}
			return undefined;
		},

		defaultMessage: function( element, method) {
			return this.findDefined(
				this.customMessage( element.name, method ),
				this.customMetaMessage( element, method ),
				// title is never undefined, so handle empty string as undefined
				!this.settings.ignoreTitle && element.title || undefined,
				$.validator.messages[method],
				"<strong>Warning: No message defined for " + element.name + "</strong>"
			);
		},

		formatAndAdd: function( element, rule ) {
			var message = this.defaultMessage( element, rule.method ),
				theregex = /\$?\{(\d+)\}/g;
			if ( typeof message == "function" ) {
				message = message.call(this, rule.parameters, element);
			} else if (theregex.test(message)) {
				message = jQuery.format(message.replace(theregex, '{$1}'), rule.parameters);
			}
			this.errorList.push({
				message: message,
				element: element
			});

			this.errorMap[element.name] = message;
			this.submitted[element.name] = message;
		},

		/**
         * 显示错误信息的外层标签名称.
         * @name validate#wrapper
         * @type String
         * @default 无
         * @example
         * $(".selector").validate({
         *      wrapper: "li"
         *   })
         */
		addWrapper: function(toToggle) {
			if ( this.settings.wrapper )
				toToggle = toToggle.add( toToggle.parent( this.settings.wrapper ) );
			return toToggle;
		},

		defaultShowErrors: function() {
			for ( var i = 0; this.errorList[i]; i++ ) {
				var error = this.errorList[i];
				this.settings.highlight && this.settings.highlight.call( this, error.element, this.settings.errorClass, this.settings.validClass );
				this.showLabel( error.element, error.message );
			}
			if( this.errorList.length ) {
				this.toShow = this.toShow.add( this.containers );
			}
			if (this.settings.success) {
				for ( var i = 0; this.successList[i]; i++ ) {
					this.showLabel( this.successList[i] );
				}
			}
			if (this.settings.unhighlight) {
				for ( var i = 0, elements = this.validElements(); elements[i]; i++ ) {
					this.settings.unhighlight.call( this, elements[i], this.settings.errorClass, this.settings.validClass );
				}
			}
			this.toHide = this.toHide.not( this.toShow );
			this.hideErrors();
			this.addWrapper( this.toShow ).show();
		},

		validElements: function() {
			return this.currentElements.not(this.invalidElements());
		},

		invalidElements: function() {
			return $(this.errorList).map(function() {
				return this.element;
			});
		},

		showLabel: function(element, message) {
			var label = this.errorsFor( element );
			if ( label.length ) {
				// refresh error/success class
				label.removeClass().addClass( this.settings.errorClass );

				// check if we have a generated label, replace the message then
				label.attr("generated") && label.html(message);
			} else {
				// create label
				label = $("<" + this.settings.errorElement + "/>")
					.attr({"for":  this.idOrName(element), generated: true})
					.addClass(this.settings.errorClass)
					.html(message || "");
				if ( this.settings.wrapper ) {
					// make sure the element is visible, even in IE
					// actually showing the wrapped element is handled elsewhere
					label = label.hide().show().wrap("<" + this.settings.wrapper + "/>").parent();
				}
				if ( !this.labelContainer.append(label).length )
					this.settings.errorPlacement
						? this.settings.errorPlacement(label, $(element) )
						: label.insertAfter(element);
			}
			if ( !message && this.settings.success ) {
				label.text("");
				typeof this.settings.success == "string"
					? label.addClass( this.settings.success )
					: this.settings.success( label );
			}
			this.toShow = this.toShow.add(label);
		},

		errorsFor: function(element) {
			var name = this.idOrName(element);
    		return this.errors().filter(function() {
				return $(this).attr('for') == name;
			});
		},

		idOrName: function(element) {
			return this.groups[element.name] || (this.checkable(element) ? element.name : element.id || element.name);
		},

		checkable: function( element ) {
			return /radio|checkbox/i.test(element.type);
		},

		findByName: function( name ) {
			// select by name and filter by form for performance over form.find("[name=...]")
			var form = this.currentForm;
			return $(document.getElementsByName(name)).map(function(index, element) {
				return element.form == form && element.name == name && element  || null;
			});
		},

		getLength: function(value, element) {
			switch( element.nodeName.toLowerCase() ) {
			case 'select':
				return $("option:selected", element).length;
			case 'input':
				if( this.checkable( element) )
					return this.findByName(element.name).filter(':checked').length;
			}
			return value.length;
		},

		depend: function(param, element) {
			return this.dependTypes[typeof param]
				? this.dependTypes[typeof param](param, element)
				: true;
		},

		dependTypes: {
			"boolean": function(param, element) {
				return param;
			},
			"string": function(param, element) {
				return !!$(param, element.form).length;
			},
			"function": function(param, element) {
				return param(element);
			}
		},

		optional: function(element) {
			return !$.validator.methods.required.call(this, $.trim(element.value), element) && "dependency-mismatch";
		},

		startRequest: function(element) {
			if (!this.pending[element.name]) {
				this.pendingRequest++;
				this.pending[element.name] = true;
			}
		},

		stopRequest: function(element, valid) {
			this.pendingRequest--;
			// sometimes synchronization fails, make sure pendingRequest is never < 0
			if (this.pendingRequest < 0)
				this.pendingRequest = 0;
			delete this.pending[element.name];
			if ( valid && this.pendingRequest == 0 && this.formSubmitted && this.form() ) {
				$(this.currentForm).submit();
				this.formSubmitted = false;
			} else if (!valid && this.pendingRequest == 0 && this.formSubmitted) {
				$(this.currentForm).triggerHandler("invalid-form", [this]);
				this.formSubmitted = false;
			}
		},

		previousValue: function(element) {
			return $.data(element, "previousValue") || $.data(element, "previousValue", {
				old: null,
				valid: true,
				message: this.defaultMessage( element, "remote" )
			});
		}

	},

	classRuleSettings: {
		required: {required: true},
		email: {email: true},
		url: {url: true},
		date: {date: true},
		dateISO: {dateISO: true},
		dateDE: {dateDE: true},
		number: {number: true},
		numberDE: {numberDE: true},
		digits: {digits: true},
		creditcard: {creditcard: true}
	},

	addClassRules: function(className, rules) {
		className.constructor == String ?
			this.classRuleSettings[className] = rules :
			$.extend(this.classRuleSettings, className);
	},

	classRules: function(element) {
		var rules = {};
		var classes = $(element).attr('class');
		classes && $.each(classes.split(' '), function() {
			if (this in $.validator.classRuleSettings) {
				$.extend(rules, $.validator.classRuleSettings[this]);
			}
		});
		return rules;
	},

	attributeRules: function(element) {
		var rules = {};
		var $element = $(element);

		for (var method in $.validator.methods) {
			var value = $element.attr(method);
			if (value) {
				rules[method] = value;
			}
		}

		// maxlength may be returned as -1, 2147483647 (IE) and 524288 (safari) for text inputs
		if (rules.maxlength && /-1|2147483647|524288/.test(rules.maxlength)) {
			delete rules.maxlength;
		}

		return rules;
	},

	metadataRules: function(element) {
		if (!$.metadata) return {};

		var meta = $.data(element.form, 'validator').settings.meta;
		return meta ?
			$(element).metadata()[meta] :
			$(element).metadata();
	},

	staticRules: function(element) {
		var rules = {};
		var validator = $.data(element.form, 'validator');
		if (validator.settings.rules) {
			rules = $.validator.normalizeRule(validator.settings.rules[element.name]) || {};
		}
		return rules;
	},

	normalizeRules: function(rules, element) {
		// handle dependency check
		$.each(rules, function(prop, val) {
			// ignore rule when param is explicitly false, eg. required:false
			if (val === false) {
				delete rules[prop];
				return;
			}
			if (val.param || val.depends) {
				var keepRule = true;
				switch (typeof val.depends) {
					case "string":
						keepRule = !!$(val.depends, element.form).length;
						break;
					case "function":
						keepRule = val.depends.call(element, element);
						break;
				}
				if (keepRule) {
					rules[prop] = val.param !== undefined ? val.param : true;
				} else {
					delete rules[prop];
				}
			}
		});

		// evaluate parameters
		$.each(rules, function(rule, parameter) {
			rules[rule] = $.isFunction(parameter) ? parameter(element) : parameter;
		});

		// clean number parameters
		$.each(['minlength', 'maxlength', 'min', 'max'], function() {
			if (rules[this]) {
				rules[this] = Number(rules[this]);
			}
		});
		$.each(['rangelength', 'range'], function() {
			if (rules[this]) {
				rules[this] = [Number(rules[this][0]), Number(rules[this][1])];
			}
		});

		if ($.validator.autoCreateRanges) {
			// auto-create ranges
			if (rules.min && rules.max) {
				rules.range = [rules.min, rules.max];
				delete rules.min;
				delete rules.max;
			}
			if (rules.minlength && rules.maxlength) {
				rules.rangelength = [rules.minlength, rules.maxlength];
				delete rules.minlength;
				delete rules.maxlength;
			}
		}

		// To support custom messages in metadata ignore rule methods titled "messages"
		if (rules.messages) {
			delete rules.messages;
		}

		return rules;
	},

	// Converts a simple string to a {string: true} rule, e.g., "required" to {required:true}
	normalizeRule: function(data) {
		if( typeof data == "string" ) {
			var transformed = {};
			$.each(data.split(/\s/), function() {
				transformed[this] = true;
			});
			data = transformed;
		}
		return data;
	},

	// http://docs.jquery.com/Plugins/Validation/Validator/addMethod
	addMethod: function(name, method, message) {
		$.validator.methods[name] = method;
		$.validator.messages[name] = message != undefined ? message : $.validator.messages[name];
		if (method.length < 3) {
			$.validator.addClassRules(name, $.validator.normalizeRule(name));
		}
	},

	methods: {

		// http://docs.jquery.com/Plugins/Validation/Methods/required
		required: function(value, element, param) {
			// check if dependency is met
			if ( !this.depend(param, element) )
				return "dependency-mismatch";
			switch( element.nodeName.toLowerCase() ) {
			case 'select':
				// could be an array for select-multiple or a string, both are fine this way
				var val = $(element).val();
				return val && val.length > 0;
			case 'input':
				if ( this.checkable(element) )
					return this.getLength(value, element) > 0;
			default:
				return $.trim(value).length > 0;
			}
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/remote
		remote: function(value, element, param) {
			if ( this.optional(element) )
				return "dependency-mismatch";

			var previous = this.previousValue(element);
			if (!this.settings.messages[element.name] )
				this.settings.messages[element.name] = {};
			previous.originalMessage = this.settings.messages[element.name].remote;
			this.settings.messages[element.name].remote = previous.message;

			param = typeof param == "string" && {url:param} || param;

			if ( this.pending[element.name] ) {
				return "pending";
			}
			if ( previous.old === value ) {
				return previous.valid;
			}

			previous.old = value;
			var validator = this;
			this.startRequest(element);
			var data = {};
			data[element.name] = value;
			$.ajax($.extend(true, {
				url: param,
				mode: "abort",
				port: "validate" + element.name,
				dataType: "json",
				data: data,
				success: function(response) {
					validator.settings.messages[element.name].remote = previous.originalMessage;
					var valid = response === true;
					if ( valid ) {
						var submitted = validator.formSubmitted;
						validator.prepareElement(element);
						validator.formSubmitted = submitted;
						validator.successList.push(element);
						validator.showErrors();
					} else {
						var errors = {};
						var message = response || validator.defaultMessage( element, "remote" );
						errors[element.name] = previous.message = $.isFunction(message) ? message(value) : message;
						validator.showErrors(errors);
					}
					previous.valid = valid;
					validator.stopRequest(element, valid);
				}
			}, param));
			return "pending";
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/minlength
		minlength: function(value, element, param) {
			return this.optional(element) || this.getLength($.trim(value), element) >= param;
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/maxlength
		maxlength: function(value, element, param) {
			return this.optional(element) || this.getLength($.trim(value), element) <= param;
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/rangelength
		rangelength: function(value, element, param) {
			var length = this.getLength($.trim(value), element);
			return this.optional(element) || ( length >= param[0] && length <= param[1] );
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/min
		min: function( value, element, param ) {
			return this.optional(element) || value >= param;
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/max
		max: function( value, element, param ) {
			return this.optional(element) || value <= param;
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/range
		range: function( value, element, param ) {
			return this.optional(element) || ( value >= param[0] && value <= param[1] );
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/email
		email: function(value, element) {
			// contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
			return this.optional(element) || /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/url
		url: function(value, element) {
			// contributed by Scott Gonzalez: http://projects.scottsplayground.com/iri/
			return this.optional(element) || /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/date
		date: function(value, element) {
			return this.optional(element) || !/Invalid|NaN/.test(new Date(value));
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/number
		number: function(value, element) {
			return this.optional(element) || /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/digits
		digits: function(value, element) {
			return this.optional(element) || /^\d+$/.test(value);
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/accept
		accept: function(value, element, param) {
			param = typeof param == "string" ? param.replace(/,/g, '|') : "png|jpe?g|gif";
			return this.optional(element) || value.match(new RegExp(".(" + param + ")$", "i"));
		},

		// http://docs.jquery.com/Plugins/Validation/Methods/equalTo
		equalTo: function(value, element, param) {
			// bind to the blur event of the target in order to revalidate whenever the target field is updated
			// TODO find a way to bind the event just once, avoiding the unbind-rebind overhead
			var target = $(param).unbind(".validate-equalTo").bind("blur.validate-equalTo", function() {
				$(element).valid();
			});
			return value == target.val();
		}
	}

});

// deprecated, use $.validator.format instead
$.format = $.validator.format;

})(jQuery);

// ajax mode: abort
// usage: $.ajax({ mode: "abort"[, port: "uniqueport"]});
// if mode:"abort" is used, the previous request on that port (port can be undefined) is aborted via XMLHttpRequest.abort()
;(function($) {
	var pendingRequests = {};
	// Use a prefilter if available (1.5+)
	if ( $.ajaxPrefilter ) {
		$.ajaxPrefilter(function(settings, _, xhr) {
			var port = settings.port;
			if (settings.mode == "abort") {
				if ( pendingRequests[port] ) {
					pendingRequests[port].abort();
				}
				pendingRequests[port] = xhr;
			}
		});
	} else {
		// Proxy ajax
		var ajax = $.ajax;
		$.ajax = function(settings) {
			var mode = ( "mode" in settings ? settings : $.ajaxSettings ).mode,
				port = ( "port" in settings ? settings : $.ajaxSettings ).port;
			if (mode == "abort") {
				if ( pendingRequests[port] ) {
					pendingRequests[port].abort();
				}
				return (pendingRequests[port] = ajax.apply(this, arguments));
			}
			return ajax.apply(this, arguments);
		};
	}
})(jQuery);

// provides cross-browser focusin and focusout events
// IE has native support, in other browsers, use event caputuring (neither bubbles)

// provides delegate(type: String, delegate: Selector, handler: Callback) plugin for easier event delegation
// handler is only called when $(event.target).is(delegate), in the scope of the jquery-object for event.target
;(function($) {
	// only implement if not provided by jQuery core (since 1.4)
	// TODO verify if jQuery 1.4's implementation is compatible with older jQuery special-event APIs
	if (!jQuery.event.special.focusin && !jQuery.event.special.focusout && document.addEventListener) {
		$.each({
			focus: 'focusin',
			blur: 'focusout'
		}, function( original, fix ){
			$.event.special[fix] = {
				setup:function() {
					this.addEventListener( original, handler, true );
				},
				teardown:function() {
					this.removeEventListener( original, handler, true );
				},
				handler: function(e) {
					arguments[0] = $.event.fix(e);
					arguments[0].type = fix;
					return $.event.handle.apply(this, arguments);
				}
			};
			function handler(e) {
				e = $.event.fix(e);
				e.type = fix;
				return $.event.handle.call(this, e);
			}
		});
	};
	$.extend($.fn, {
		validateDelegate: function(delegate, type, handler) {
			return this.bind(type, function(event) {
				var target = $(event.target);
				if (target.is(delegate)) {
					return handler.apply(target, arguments);
				}
			});
		}
	});
})(jQuery);
/*
 * operamasks-ui omAccordion 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 *  om-panel.js
 */
;(function( $, undefined ) {

$.widget( "om.omAccordion", {
    options: {
        width:"auto",
        height:"auto",
        oriention:"vertical",
        switchMode:"click",
        switchDuration:300,
        active:0
    },

    _create: function(){
        var accordion = this.element;
        var styles = accordion.attr("style");
        var self = this;
        var options = this.options;
        if ((styles && styles.indexOf('width') != -1 )|| options.width == 'auto') {
        } else if (options.width == 'fit') {
            accordion.css('width', '100%');
        } else if ( !!options.width ){
            accordion.css('width', options.width);
        }
        if ((styles && styles.indexOf('height') != -1 )|| options.height == 'auto') {
        } else if (options.height == 'fit') {
            accordion.css('height', '100%');
        } else if ( !!options.height ){
            accordion.css('height', options.height);
        }
        accordion.addClass("om-wigdet om-accordion");
        var headers = accordion.find("ul:first");
        var anchors = headers.find("a");
        var panels = [];
        anchors.each(function(n){
            var anchor = $(this), panel;
            var href = anchor.attr("href"); 
            //在ie7中, href属性是绝对路径
            if ($.browser.msie && parseInt($.browser.version) == 7) {
                if (anchor[0].pathname && href.lastIndexOf(anchor[0].pathname) != -1){
                    var pathname = anchor[0].pathname;
                    href = href.substring(href.lastIndexOf(pathname) + pathname.length );
                }
            }
            var cfg = {
                    title : anchor.text(),
                    tools : ['collapse'],
                    iconCls: anchor.attr("iconCls"),
                    clickExpand : true,
                    collapsed : options.active != n,
                    border : true
            };
            var target = $('>' + href, accordion);
            //target要么指向一个当前页面的div,要么是一个url
            if (!!(target[0])) {
                cfg.content = target.html();
                panels.push(self._createPanel(target[0], cfg));
            } else {
                cfg.url = anchor.attr('href');
                panels.push(self._createPanel($('<div></div>')[0], cfg));
            }
        }); 
        $.data(accordion , "panels" , panels);
        
        this._renderPanels();
        headers.remove();
    },
    _init: function(){
        var accordion = this.element;
        var options = this.options;
        var panels= $.data(accordion , "panels");
        var i = panels.length;
        if(i > 0){
            this._mergeOverlapBorder();
            this._setPanelBodyHeight();
        }
        this._buildEvent();
        
        
    },
    _createPanel: function(target, config){
        $(target).omPanel(config);
        return target;      
    },
    _renderPanels: function () {
        var accordion = this.element;
        var panels = $.data(accordion , "panels");
        //detach all sub divs
        accordion.children().detach();
        
        for(var i=0, len = panels.length; i<len; i++) {
            $(panels[i]).omPanel('panel').appendTo(accordion);
        }
    },
    _mergeOverlapBorder: function(){
        var accordion = this.element;
        var panels = $.data(accordion , "panels");
        var len = panels.length;
        if( len > 1){
            for(var i=1; i<len; i++) {
                $(panels[i]).omPanel('header').addClass("om-panel-border-top-ignore");
            }
        }
    },
    _setPanelBodyHeight: function() {
        var panels = $.data(this.element , "panels");
        var headerHeight = $(panels[0]).omPanel('header').outerHeight();
        for(var i=0 , len=panels.length; i<len; i++){
            var panelBody = $(panels[i]).omPanel('body');
            panelBody.outerHeight(this.element.innerHeight()-len * headerHeight+(len-1));
        }
    }, 
    _buildEvent: function() {
        var panels = $.data(this.element , "panels");
        var accordion = this.element;
        var options = this.options;
        accordion.children().find('>div.om-panel-header').each(function(n){
            var header = $(this);
            //清除panel的事件
            header.unbind();
            //清除小工具的事件
            header.find('div.om-panel-tool').children().unbind();
            //添加accordion的header click事件
            header.bind('click.omaccordions', function(){
                if ( n == options.active ) {
                    //点在当前展开的panel上了
                } else {
                    //点在其他panel上了
                    $(panels[n]).omPanel('expand', true, 'fast');
                    $(panels[options.active]).omPanel('collapse', true, 'fast');
                    options.active = n;
                }
            });
        });
    }
});
})( jQuery );/*
 * operamasks-ui omAjaxSubmit 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 */
;(function($) {

/*
	Usage Note:
	-----------
	Do not use both omAjaxSubmit and ajaxForm on the same form.  These
	functions are intended to be exclusive.  Use omAjaxSubmit if you want
	to bind your own submit handler to the form.  For example,

	$(document).ready(function() {
		$('#myForm').bind('submit', function(e) {
			e.preventDefault(); // <-- important
			$(this).omAjaxSubmit({
				target: '#output'
			});
		});
	});

	Use ajaxForm when you want the plugin to manage all the event binding
	for you.  For example,

	$(document).ready(function() {
		$('#myForm').ajaxForm({
			target: '#output'
		});
	});

	When using ajaxForm, the omAjaxSubmit function will be invoked for you
	at the appropriate time.
*/

/**
 * @name omAjaxSubmit
 * @author 陈界
 * @class
 * <div>
 * omAjaxSubmit() 提供使用ajax方式提交HTML form的一种机制.本插件会监听表单的submit事件,<br/>
 * 覆盖传统的submit事件监听器,而使用ajax方式来处理submit事件.在表单提交之前,本插件会收集<br/>
 * 所有的表单字段,并将之序列化后附加在ajax请求的数据域(data)中.支持所有标准的html可提交的<br/>
 * 表单元素.
 * </div><br/>
 * <b>事件回调</b><br/>
 * <div>
 * 通过丰富的配置参数,omAjaxSubmit可以高度自定制.同时提供多个事件回调函数,在每一次完整<br/>
 * 的表单提交的过程中,用户有时机能够对提交的请求进行修改.
 * </div>
 * <pre>beforeSerialize:</pre>
 * <div style="text-indent:2em;">在form序列化之前执行的回调函数.在获取所有form表单字段的值之前,该函数提供了一个操作form的时机</div>
 * <pre>beforeSubmit:</pre>
 * <div style="text-indent:2em;">在form被提交之前执行的回调函数.该函数提供了一个时机来执行预提交的逻辑,比如表单校验</div><br/>
 * <b>工具方法</b><br/>
 * <div>omAjaxSubmit还提供了一系列静态工具方法,用于方便地操作表单及其字段.</div>
 * <pre>$.fn.formToArray()</pre>
 * <div style="text-indent:2em;">将表单所有元素转换成key/value的数组,例如[{name:'username', value:'jack'},{name:'password', value:'secret'}],<br/>
 * 注意:该数组将作为参数传递给beforeSubmit函数</div>
 * <pre>$.fn.formSerialize()</pre>
 * <div style="text-indent:2em;">将表单数据序列化成可提交的字符串,例如name1=value1&amp;name2=value2</div>
 * <pre>$.fn.fieldSerialize()</pre>
 * <div style="text-indent:2em;">将表单所有元素序列化成可提交的字符串,例如name1=value1&amp;name2=value2.</div>
 * <pre>$.fn.fieldValue()</pre>
 * <div style="text-indent:2em;">获取当前元素(或元素数组)的值</div>
 * <pre>$.fieldValue(successful)</pre>
 * <div style="text-indent:2em;">静态工具方法,用于获取元素的值,参数successful的意义同上.</div>
 * <pre>$.fn.clearForm()</pre>
 * <div style="text-indent:2em;">清空当前表单各个元素的值</div>
 * <pre>$.fn.clearFields()</pre>
 * <div style="text-indent:2em;">清空当前元素(或元素数组)的值</div>
 * <pre>$.fn.resetForm()</pre>
 * <div style="text-indent:2em;">重置当前表单各个元素的值</div>
 * <pre>$.fn.enable(b)</pre>
 * <div style="text-indent:2em;">设置当前元素(或元素数组)的使能状态</div>
 * <pre>$.fn.selected(selected)</pre>
 * <div style="text-indent:2em;">设置当前元素(或元素数组)的选中状态</div><br/>
 * <b>示例</b><br/>
 * <pre>
 *  $(document).ready(function() {
 *      $('#myForm').bind('submit', function(e) {
 *          e.preventDefault(); //阻止form默认的提交行为.
 *              $(this).omAjaxSubmit(//使用ajax提交.
 *                  {
 *                      target: '#output'
 *                  }
 *              );
 *      });
 *  });
 * 
 * </pre>
 * @constructor
 * @param options 标准config对象
 * @example
 * 	$('#formId').omAjaxSubmit({target: '#output'});
 */
$.fn.omAjaxSubmit = function(options) {
	// fast fail if nothing selected (http://dev.jquery.com/ticket/2752)
	if (!this.length) {
		log('omAjaxSubmit: skipping submit process - no element selected');
		return this;
	}
	
	var method, action, url, $form = this;

	if (typeof options == 'function') {
		options = { success: options };
	}

	method = this.attr('method');
	action = this.attr('action');

	url = (typeof action === 'string') ? $.trim(action) : '';
	url = url || window.location.href || '';
	if (url) {
		// clean url (don't include hash vaue)
		url = (url.match(/^([^#]+)/)||[])[1];
	}

	options = $.extend(true, {
        /**
         * 表单提交的url
         * @name omAjaxSubmit#url
         * @type String
         * @default form的action属性值
         */
		url:  url,
        /**
         * 当表单提交成功并取到响应时,执行的回调函数.
         * @event
         * @name omAjaxSubmit#success
         * @param responseText 响应的文本.具体的取值根据options中的dataType有关,请参考dataType属性的说明文档.
         * @param statusText 响应的状态,在该回调中,常见的取值为success
         * @param xhr XMLHttpRequest对象
         * @param $form 经过jQuery包装的form对象
         * @type Function
         * @default undefined
         */
		success: $.ajaxSettings.success,
        /**
         * 表单的提交方法，取值为：'GET' 或者 'POST'.
         * @name omAjaxSubmit#method
         * @type String
         * @default 'GET'
         */
		method: method || 'GET',
        /**
         * iframe的src属性值,该属性在页面中有iframe的时候才用得到,通常此时form中有文件需要上传.<br/>
         * 默认值是about:blank ,如果当前页面地址使用 https 协议,则该值为javascript:false
         * @blocked
         */
		iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank'
	}, options);

	// hook for manipulating the form data before it is extracted;
	// convenient for use with rich editors like tinyMCE or FCKEditor
	var veto = {};
	this.trigger('form-pre-serialize', [this, options, veto]);
	if (veto.veto) {
		log('omAjaxSubmit: submit vetoed via form-pre-serialize trigger');
		return this;
	}

    /**
     * 在form序列化之前执行的回调函数.在获取form表单元素的值之前,该函数提供了一个操作form的时机.<br/>
     * 该函数接受2个参数<br/>
     * @name omAjaxSubmit#beforeSerialize
     * @event
     * @param $form form对应的jQuery对象
     * @param options 传给ajaxSubmit的options对象
     * @return false 取消form的提交
     * @example
     * beforeSerialize: function($form, options) { 
     *     // return false to cancel submit                  
     * }
     */
	// provide opportunity to alter form data before it is serialized
	if (options.beforeSerialize && options.beforeSerialize(this, options) === false) {
		log('omAjaxSubmit: submit aborted via beforeSerialize callback');
		return this;
	}

    /**
     * 
     * 是否以严格的语义化顺序提交form表单元素.同时,设置了该属性会忽略表单中的image标签.<br/>
     * 该属性一般不用设置(默认为false).只有当你的服务器对semantic order有严格要求,<br/>
     * 并且你的表单中含有image时,你才需要设置它为true<br/>
     * @blocked
     */
	var n,v,a = this.formToArray(options.semantic);
    /**
     * ajax提交中的附加数据,以JSON的形式组成(key/value).如果value是数组,将会被展开;如果value是函数,将会被求值.
     * @type JSON
     * @name omAjaxSubmit#data
     * @example
     * data: { key1: 'value1', key2: 'value2' }
     */
	if (options.data) {
		options.extraData = options.data;
		for (n in options.data) {
			if(options.data[n] instanceof Array) {
				for (var k in options.data[n]) {
					a.push( { name: n, value: options.data[n][k] } );
				}
			}
			else {
				v = options.data[n];
				v = $.isFunction(v) ? v() : v; // if value is fn, invoke it
				a.push( { name: n, value: v } );
			}
		}
	}

    /**
     * 在form被提交之前执行的回调函数.该函数提供了一个时机来执行预提交的逻辑,或者可以用来进行校验表单元素.<br/>
     * 该函数接受3个参数:arr, $form, options.<br/>
     * 若函数返回false,则会取消form的提交<br/>
     * @name omAjaxSubmit#beforeSubmit
     * @type Function
     * @event
     * @param arr 一个数字,包含form所有字段的key/value值
     * @param $form form对应的jQuery对象
     * @param options 传递给ajaxSubmit的options参数
     * @return false 取消提交表单
     * @example
     * beforeSubmit: function(arr, $form, options) { 
     *     // The array of form data takes the following form: 
     *     // [ { name: 'username', value: 'jresig' }, { name: 'password', value: 'secret' } ] 
     *     // return false to cancel submit                  
     * }
     */
	// give pre-submit callback an opportunity to abort the submit
	if (options.beforeSubmit && options.beforeSubmit(a, this, options) === false) {
		log('omAjaxSubmit: submit aborted via beforeSubmit callback');
		return this;
	}

	// fire vetoable 'validate' event
	this.trigger('form-submit-validate', [a, this, options, veto]);
	if (veto.veto) {
		log('omAjaxSubmit: submit vetoed via form-submit-validate trigger');
		return this;
	}

	var q = $.param(a);

	if (options.method.toUpperCase() == 'GET') {
		options.url += (options.url.indexOf('?') >= 0 ? '&' : '?') + q;
		options.data = null;  // data is null for 'get'
	}
	else {
		options.data = q; // data is the query string for 'post'
	}

    var callbacks = [];
    /**
     * 在form成功提交后,是否将form字段重置
     * @name omAjaxSubmit#resetForm
     * @type Boolean
     * @default null
     */
	if (options.resetForm) {
		callbacks.push(function() { $form.resetForm(); });
	}
    /**
     * 在form成功提交后,是否将form字段清空<br/>
     * @name omAjaxSubmit#clearForm
     * @type Boolean
     * @default null
     */
	if (options.clearForm) {
		callbacks.push(function() { $form.clearForm(); });
	}

    /**
     * 响应的数据格式,可选的取值为'xml', 'script', 'json'或者null. 该选项表明了响应将要被如何处理.<br/>
     * 与jQuery.httpData一一对应,其各种取值情况处理如下:<br/>
     * <pre>
     *      'xml':	响应将会被认为是xml格式的,并作为第一个参数传递给success回调函数
     *      'json':	响应将会被认为是json格式的,其将会被求值,结果将会作为第一个参数传递给success回调函数.
     *      'script':响应将会被认为是js脚本,其将在全局上下文中被执行.
     * </pre>
     * @name omAjaxSubmit#dataType
     * @type String
     * @default undefined
     * @example 
     * dataType : 'json'
     */
    /**
     * 指定了一个更新区域,该区域将会被ajax响应更新.<br/>
     * 该值可以是DOM元素,jQuery对象,或者一个可以被jQuery选择到的选择器.
     * @name omAjaxSubmit#target
     * @type DOM, jQuery, or String
     * @default undefined
     */
	// perform a load on the target only if dataType is not provided
	if (!options.dataType && options.target) {
		var oldSuccess = options.success || function(){};
		callbacks.push(function(data) {
            /**
             * 可选配置,是否替换target指定的区域.<br/>
             * 设为true将会整体替换target对应的DOM节点,设为false将只会替换节点的内容.<br/>
             * @name omAjaxSubmit#replaceTarget
             * @type Boolean 
             * @default false
             */
			var fn = options.replaceTarget ? 'replaceWith' : 'html';
			$(options.target)[fn](data).each(oldSuccess, arguments);
		});
	}
	else if (options.success) {
		callbacks.push(options.success);
	}

	options.success = function(data, status, xhr) { // jQuery 1.4+ passes xhr as 3rd arg
		var context = options.context || options;   // jQuery 1.4+ supports scope context 
		for (var i=0, max=callbacks.length; i < max; i++) {
			callbacks[i].apply(context, [data, status, xhr || $form, $form]);
		}
	};

	// are there files to upload?
	var fileInputs = $('input:file', this).length > 0;
	var mp = 'multipart/form-data';
	var multipart = ($form.attr('enctype') == mp || $form.attr('encoding') == mp);

    /**
     * 是否总是将form的响应指向一个iframe,该属性在有文件上传的情况下有用.
     * @blocked
     */
	// options.iframe allows user to force iframe mode
	// 06-NOV-09: now defaulting to iframe mode if file input is detected
   if (options.iframe !== false && (fileInputs || options.iframe || multipart)) {
	   // hack to fix Safari hang (thanks to Tim Molendijk for this)
	   // see:  http://groups.google.com/group/jquery-dev/browse_thread/thread/36395b7ab510dd5d
	   if (options.closeKeepAlive) {
		   $.get(options.closeKeepAlive, function() { fileUpload(a); });
		}
	   else {
		   fileUpload(a);
		}
   }
   else {
		// IE7 massage (see issue 57)
		if ($.browser.msie && method == 'get') { 
			var ieMeth = $form[0].getAttribute('method');
			if (typeof ieMeth === 'string')
				options.method = ieMeth;
		}
		$.ajax(options);
   }

	// fire 'notify' event
	this.trigger('form-submit-notify', [this, options]);
	return this;


	// private function for handling file uploads (hat tip to YAHOO!)
	function fileUpload(a) {
		var form = $form[0], el, i, s, g, id, $io, io, xhr, sub, n, timedOut, timeoutHandle;
        var useProp = !!$.fn.prop;

        if (a) {
        	// ensure that every serialized input is still enabled
          	for (i=0; i < a.length; i++) {
                el = $(form[a[i].name]);
                el[ useProp ? 'prop' : 'attr' ]('disabled', false);
          	}
        }

		if ($(':input[name=submit],:input[id=submit]', form).length) {
			// if there is an input with a name or id of 'submit' then we won't be
			// able to invoke the submit fn on the form (at least not x-browser)
			alert('Error: Form elements must not have name or id of "submit".');
			return;
		}
		
		s = $.extend(true, {}, $.ajaxSettings, options);
		s.context = s.context || s;
		id = 'jqFormIO' + (new Date().getTime());
		/**
		 * 指定一个iframe元素.当本插件处理有文件上传的form时,一般会临时创建一个隐藏的iframe来接收响应.<br/>
		 * 配置该属性,用户可以使用一个已存在的iframe,而不是使用临时iframe.<br/>
		 * 注意使用该属性后,本插件不会再去尝试处理服务器的响应.<br/>
		 * @blocked
         */
		if (s.iframeTarget) {
			$io = $(s.iframeTarget);
			n = $io.attr('name');
			if (n == null)
			 	$io.attr('name', id);
			else
				id = n;
		}
		else {
			$io = $('<iframe name="' + id + '" src="'+ s.iframeSrc +'" />');
			$io.css({ position: 'absolute', top: '-1000px', left: '-1000px' });
		}
		io = $io[0];


		xhr = { // mock object
			aborted: 0,
			responseText: null,
			responseXML: null,
			status: 0,
			statusText: 'n/a',
			getAllResponseHeaders: function() {},
			getResponseHeader: function() {},
			setRequestHeader: function() {},
			abort: function(status) {
				var e = (status === 'timeout' ? 'timeout' : 'aborted');
				log('aborting upload... ' + e);
				this.aborted = 1;
				$io.attr('src', s.iframeSrc); // abort op in progress
				xhr.error = e;
				s.error && s.error.call(s.context, xhr, e, status);
				g && $.event.trigger("ajaxError", [xhr, s, e]);
				s.complete && s.complete.call(s.context, xhr, e);
			}
		};

		g = s.global;
		// trigger ajax global events so that activity/block indicators work like normal
		if (g && ! $.active++) {
			$.event.trigger("ajaxStart");
		}
		if (g) {
			$.event.trigger("ajaxSend", [xhr, s]);
		}

		if (s.beforeSend && s.beforeSend.call(s.context, xhr, s) === false) {
			if (s.global) {
				$.active--;
			}
			return;
		}
		if (xhr.aborted) {
			return;
		}

		// add submitting element to data if we know it
		sub = form.clk;
		if (sub) {
			n = sub.name;
			if (n && !sub.disabled) {
				s.extraData = s.extraData || {};
				s.extraData[n] = sub.value;
				if (sub.type == "image") {
					s.extraData[n+'.x'] = form.clk_x;
					s.extraData[n+'.y'] = form.clk_y;
				}
			}
		}
		
		var CLIENT_TIMEOUT_ABORT = 1;
		var SERVER_ABORT = 2;

		function getDoc(frame) {
			var doc = frame.contentWindow ? frame.contentWindow.document : frame.contentDocument ? frame.contentDocument : frame.document;
			return doc;
		}
		
		// take a breath so that pending repaints get some cpu time before the upload starts
		function doSubmit() {
			// make sure form attrs are set
			var t = $form.attr('target'), a = $form.attr('action');

			// update form attrs in IE friendly way
			form.setAttribute('target',id);
			if (!method) {
				form.setAttribute('method', 'POST');
			}
			if (a != s.url) {
				form.setAttribute('action', s.url);
			}

			// ie borks in some cases when setting encoding
			if (! s.skipEncodingOverride && (!method || /post/i.test(method))) {
				$form.attr({
					encoding: 'multipart/form-data',
					enctype:  'multipart/form-data'
				});
			}

			// support timout
			if (s.timeout) {
				timeoutHandle = setTimeout(function() { timedOut = true; cb(CLIENT_TIMEOUT_ABORT); }, s.timeout);
			}
			
			// look for server aborts
			function checkState() {
				try {
					var state = getDoc(io).readyState;
					log('state = ' + state);
					if (state.toLowerCase() == 'uninitialized')
						setTimeout(checkState,50);
				}
				catch(e) {
					log('Server abort: ' , e, ' (', e.name, ')');
					cb(SERVER_ABORT);
					timeoutHandle && clearTimeout(timeoutHandle);
					timeoutHandle = undefined;
				}
			}

			// add "extra" data to form if provided in options
			var extraInputs = [];
			try {
				if (s.extraData) {
					for (var n in s.extraData) {
						extraInputs.push(
							$('<input type="hidden" name="'+n+'" />').attr('value',s.extraData[n])
								.appendTo(form)[0]);
					}
				}

				if (!s.iframeTarget) {
					// add iframe to doc and submit the form
					$io.appendTo('body');
	                io.attachEvent ? io.attachEvent('onload', cb) : io.addEventListener('load', cb, false);
				}
				setTimeout(checkState,15);
				form.submit();
			}
			finally {
				// reset attrs and remove "extra" input elements
				form.setAttribute('action',a);
				if(t) {
					form.setAttribute('target', t);
				} else {
					$form.removeAttr('target');
				}
				$(extraInputs).remove();
			}
		}
        /**
         * 强制同步,如果设置了该属性,在form提交时将立即进行文件上传,否则在提交表单后会延迟10毫秒再进行文件上传.<br/>
         * 在延迟的这一短暂时间里,浏览器有机会更新DOM结构,比如想要向用户展示"请稍后..."的提示.<br/>
         * 显示这些的提示是需要时间来更新DOM结构的.暂停这一会儿时间,再真正提交表单,可以增加易用性.<br/>
         * @blocked
         */
		if (s.forceSync) {
			doSubmit();
		}
		else {
			setTimeout(doSubmit, 10); // this lets dom updates render
		}

		var data, doc, domCheckCount = 50, callbackProcessed;

		function cb(e) {
			if (xhr.aborted || callbackProcessed) {
				return;
			}
			try {
				doc = getDoc(io);
			}
			catch(ex) {
				log('cannot access response document: ', ex);
				e = SERVER_ABORT;
			}
			if (e === CLIENT_TIMEOUT_ABORT && xhr) {
				xhr.abort('timeout');
				return;
			}
			else if (e == SERVER_ABORT && xhr) {
				xhr.abort('server abort');
				return;
			}

			if (!doc || doc.location.href == s.iframeSrc) {
				// response not received yet
				if (!timedOut)
					return;
			}
            io.detachEvent ? io.detachEvent('onload', cb) : io.removeEventListener('load', cb, false);

			var status = 'success', errMsg;
			try {
				if (timedOut) {
					throw 'timeout';
				}

				var isXml = s.dataType == 'xml' || doc.XMLDocument || $.isXMLDoc(doc);
				log('isXml='+isXml);
				if (!isXml && window.opera && (doc.body == null || doc.body.innerHTML == '')) {
					if (--domCheckCount) {
						// in some browsers (Opera) the iframe DOM is not always traversable when
						// the onload callback fires, so we loop a bit to accommodate
						log('requeing onLoad callback, DOM not available');
						setTimeout(cb, 250);
						return;
					}
					// let this fall through because server response could be an empty document
					//log('Could not access iframe DOM after mutiple tries.');
					//throw 'DOMException: not available';
				}

				//log('response detected');
                var docRoot = doc.body ? doc.body : doc.documentElement;
                xhr.responseText = docRoot ? docRoot.innerHTML : null;
				xhr.responseXML = doc.XMLDocument ? doc.XMLDocument : doc;
				if (isXml)
					s.dataType = 'xml';
				xhr.getResponseHeader = function(header){
					var headers = {'content-type': s.dataType};
					return headers[header];
				};
                // support for XHR 'status' & 'statusText' emulation :
                if (docRoot) {
                    xhr.status = Number( docRoot.getAttribute('status') ) || xhr.status;
                    xhr.statusText = docRoot.getAttribute('statusText') || xhr.statusText;
                }

				var dt = s.dataType || '';
				var scr = /(json|script|text)/.test(dt.toLowerCase());
				if (scr || s.textarea) {
					// see if user embedded response in textarea
					var ta = doc.getElementsByTagName('textarea')[0];
					if (ta) {
						xhr.responseText = ta.value;
                        // support for XHR 'status' & 'statusText' emulation :
                        xhr.status = Number( ta.getAttribute('status') ) || xhr.status;
                        xhr.statusText = ta.getAttribute('statusText') || xhr.statusText;
					}
					else if (scr) {
						// account for browsers injecting pre around json response
						var pre = doc.getElementsByTagName('pre')[0];
						var b = doc.getElementsByTagName('body')[0];
						if (pre) {
							xhr.responseText = pre.textContent ? pre.textContent : pre.innerHTML;
						}
						else if (b) {
							xhr.responseText = b.innerHTML;
						}
					}
				}
				else if (s.dataType == 'xml' && !xhr.responseXML && xhr.responseText != null) {
					xhr.responseXML = toXml(xhr.responseText);
				}

                try {
                    data = httpData(xhr, s.dataType, s);
                }
                catch (e) {
                    status = 'parsererror';
                    xhr.error = errMsg = (e || status);
                }
			}
			catch (e) {
				log('error caught: ',e);
				status = 'error';
                xhr.error = errMsg = (e || status);
			}

			if (xhr.aborted) {
				log('upload aborted');
				status = null;
			}

            if (xhr.status) { // we've set xhr.status
                status = (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304) ? 'success' : 'error';
            }

			// ordering of these callbacks/triggers is odd, but that's how $.ajax does it
			if (status === 'success') {
				s.success && s.success.call(s.context, data, 'success', xhr);
				g && $.event.trigger("ajaxSuccess", [xhr, s]);
			}
            else if (status) {
				if (errMsg == undefined)
					errMsg = xhr.statusText;
				s.error && s.error.call(s.context, xhr, status, errMsg);
				g && $.event.trigger("ajaxError", [xhr, s, errMsg]);
            }

			g && $.event.trigger("ajaxComplete", [xhr, s]);

			if (g && ! --$.active) {
				$.event.trigger("ajaxStop");
			}

			s.complete && s.complete.call(s.context, xhr, status);

			callbackProcessed = true;
			if (s.timeout)
				clearTimeout(timeoutHandle);

			// clean up
			setTimeout(function() {
				if (!s.iframeTarget)
					$io.remove();
				xhr.responseXML = null;
			}, 100);
		}

		var toXml = $.parseXML || function(s, doc) { // use parseXML if available (jQuery 1.5+)
			if (window.ActiveXObject) {
				doc = new ActiveXObject('Microsoft.XMLDOM');
				doc.async = 'false';
				doc.loadXML(s);
			}
			else {
				doc = (new DOMParser()).parseFromString(s, 'text/xml');
			}
			return (doc && doc.documentElement && doc.documentElement.nodeName != 'parsererror') ? doc : null;
		};
		var parseJSON = $.parseJSON || function(s) {
			return window['eval']('(' + s + ')');
		};

		var httpData = function( xhr, type, s ) { // mostly lifted from jq1.4.4

			var ct = xhr.getResponseHeader('content-type') || '',
				xml = type === 'xml' || !type && ct.indexOf('xml') >= 0,
				data = xml ? xhr.responseXML : xhr.responseText;

			if (xml && data.documentElement.nodeName === 'parsererror') {
				$.error && $.error('parsererror');
			}
			if (s && s.dataFilter) {
				data = s.dataFilter(data, type);
			}
			if (typeof data === 'string') {
				if (type === 'json' || !type && ct.indexOf('json') >= 0) {
					data = parseJSON(data);
				} else if (type === "script" || !type && ct.indexOf("javascript") >= 0) {
					$.globalEval(data);
				}
			}
			return data;
		};
	}
};

/**
 * ajaxForm() provides a mechanism for fully automating form submission.
 *
 * The advantages of using this method instead of omAjaxSubmit() are:
 *
 * 1: This method will include coordinates for <input type="image" /> elements (if the element
 *	is used to submit the form).
 * 2. This method will include the submit element's name/value data (for the element that was
 *	used to submit the form).
 * 3. This method binds the submit() method to the form for you.
 *
 * The options argument for ajaxForm works exactly as it does for omAjaxSubmit.  ajaxForm merely
 * passes the options argument along after properly binding events for submit elements and
 * the form itself.
 */
$.fn.ajaxForm = function(options) {
	// in jQuery 1.3+ we can fix mistakes with the ready state
	if (this.length === 0) {
		var o = { s: this.selector, c: this.context };
		if (!$.isReady && o.s) {
			log('DOM not ready, queuing ajaxForm');
			$(function() {
				$(o.s,o.c).ajaxForm(options);
			});
			return this;
		}
		// is your DOM ready?  http://docs.jquery.com/Tutorials:Introducing_$(document).ready()
		log('terminating; zero elements found by selector' + ($.isReady ? '' : ' (DOM not ready)'));
		return this;
	}

	return this.ajaxFormUnbind().bind('submit.form-plugin', function(e) {
		if (!e.isDefaultPrevented()) { // if event has been canceled, don't proceed
			e.preventDefault();
			$(this).omAjaxSubmit(options);
		}
	}).bind('click.form-plugin', function(e) {
		var target = e.target;
		var $el = $(target);
		if (!($el.is(":submit,input:image"))) {
			// is this a child element of the submit el?  (ex: a span within a button)
			var t = $el.closest(':submit');
			if (t.length == 0) {
				return;
			}
			target = t[0];
		}
		var form = this;
		form.clk = target;
		if (target.type == 'image') {
			if (e.offsetX != undefined) {
				form.clk_x = e.offsetX;
				form.clk_y = e.offsetY;
			} else if (typeof $.fn.offset == 'function') { // try to use dimensions plugin
				var offset = $el.offset();
				form.clk_x = e.pageX - offset.left;
				form.clk_y = e.pageY - offset.top;
			} else {
				form.clk_x = e.pageX - target.offsetLeft;
				form.clk_y = e.pageY - target.offsetTop;
			}
		}
		// clear form vars
		setTimeout(function() { form.clk = form.clk_x = form.clk_y = null; }, 100);
	});
};

// ajaxFormUnbind unbinds the event handlers that were bound by ajaxForm
$.fn.ajaxFormUnbind = function() {
	return this.unbind('submit.form-plugin click.form-plugin');
};

/**
 * formToArray() gathers form element data into an array of objects that can
 * be passed to any of the following ajax functions: $.get, $.post, or load.
 * Each object in the array has both a 'name' and 'value' property.  An example of
 * an array for a simple login form might be:
 *
 * [ { name: 'username', value: 'jresig' }, { name: 'password', value: 'secret' } ]
 *
 * It is this array that is passed to pre-submit callback functions provided to the
 * omAjaxSubmit() and ajaxForm() methods.
 */
$.fn.formToArray = function(semantic) {
	var a = [];
	if (this.length === 0) {
		return a;
	}

	var form = this[0];
	var els = semantic ? form.getElementsByTagName('*') : form.elements;
	if (!els) {
		return a;
	}

	var i,j,n,v,el,max,jmax;
	for(i=0, max=els.length; i < max; i++) {
		el = els[i];
		n = el.name;
		if (!n) {
			continue;
		}

		if (semantic && form.clk && el.type == "image") {
			// handle image inputs on the fly when semantic == true
			if(!el.disabled && form.clk == el) {
				a.push({name: n, value: $(el).val()});
				a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
			}
			continue;
		}

		v = $.fieldValue(el, true);
		if (v && v.constructor == Array) {
			for(j=0, jmax=v.length; j < jmax; j++) {
				a.push({name: n, value: v[j]});
			}
		}
		else if (v !== null && typeof v != 'undefined') {
			a.push({name: n, value: v});
		}
	}

	if (!semantic && form.clk) {
		// input type=='image' are not found in elements array! handle it here
		var $input = $(form.clk), input = $input[0];
		n = input.name;
		if (n && !input.disabled && input.type == 'image') {
			a.push({name: n, value: $input.val()});
			a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
		}
	}
	return a;
};

/**
 * Serializes form data into a 'submittable' string. This method will return a string
 * in the format: name1=value1&amp;name2=value2
 */
$.fn.formSerialize = function(semantic) {
	//hand off to jQuery.param for proper encoding
	return $.param(this.formToArray(semantic));
};

/**
 * Serializes all field elements in the jQuery object into a query string.
 * This method will return a string in the format: name1=value1&amp;name2=value2
 */
$.fn.fieldSerialize = function(successful) {
	var a = [];
	this.each(function() {
		var n = this.name;
		if (!n) {
			return;
		}
		var v = $.fieldValue(this, successful);
		if (v && v.constructor == Array) {
			for (var i=0,max=v.length; i < max; i++) {
				a.push({name: n, value: v[i]});
			}
		}
		else if (v !== null && typeof v != 'undefined') {
			a.push({name: this.name, value: v});
		}
	});
	//hand off to jQuery.param for proper encoding
	return $.param(a);
};

/**
 * Returns the value(s) of the element in the matched set.  For example, consider the following form:
 *
 *  <form><fieldset>
 *	  <input name="A" type="text" />
 *	  <input name="A" type="text" />
 *	  <input name="B" type="checkbox" value="B1" />
 *	  <input name="B" type="checkbox" value="B2"/>
 *	  <input name="C" type="radio" value="C1" />
 *	  <input name="C" type="radio" value="C2" />
 *  </fieldset></form>
 *
 *  var v = $(':text').fieldValue();
 *  // if no values are entered into the text inputs
 *  v == ['','']
 *  // if values entered into the text inputs are 'foo' and 'bar'
 *  v == ['foo','bar']
 *
 *  var v = $(':checkbox').fieldValue();
 *  // if neither checkbox is checked
 *  v === undefined
 *  // if both checkboxes are checked
 *  v == ['B1', 'B2']
 *
 *  var v = $(':radio').fieldValue();
 *  // if neither radio is checked
 *  v === undefined
 *  // if first radio is checked
 *  v == ['C1']
 *
 * The successful argument controls whether or not the field element must be 'successful'
 * (per http://www.w3.org/TR/html4/interact/forms.html#successful-controls).
 * The default value of the successful argument is true.  If this value is false the value(s)
 * for each element is returned.
 *
 * Note: This method *always* returns an array.  If no valid value can be determined the
 *	   array will be empty, otherwise it will contain one or more values.
 */
$.fn.fieldValue = function(successful) {
	for (var val=[], i=0, max=this.length; i < max; i++) {
		var el = this[i];
		var v = $.fieldValue(el, successful);
		if (v === null || typeof v == 'undefined' || (v.constructor == Array && !v.length)) {
			continue;
		}
		v.constructor == Array ? $.merge(val, v) : val.push(v);
	}
	return val;
};

/**
 * Returns the value of the field element.
 */
$.fieldValue = function(el, successful) {
	var n = el.name, t = el.type, tag = el.tagName.toLowerCase();
	if (successful === undefined) {
		successful = true;
	}

	if (successful && (!n || el.disabled || t == 'reset' || t == 'button' ||
		(t == 'checkbox' || t == 'radio') && !el.checked ||
		(t == 'submit' || t == 'image') && el.form && el.form.clk != el ||
		tag == 'select' && el.selectedIndex == -1)) {
			return null;
	}

	if (tag == 'select') {
		var index = el.selectedIndex;
		if (index < 0) {
			return null;
		}
		var a = [], ops = el.options;
		var one = (t == 'select-one');
		var max = (one ? index+1 : ops.length);
		for(var i=(one ? index : 0); i < max; i++) {
			var op = ops[i];
			if (op.selected) {
				var v = op.value;
				if (!v) { // extra pain for IE...
					v = (op.attributes && op.attributes['value'] && !(op.attributes['value'].specified)) ? op.text : op.value;
				}
				if (one) {
					return v;
				}
				a.push(v);
			}
		}
		return a;
	}
	return $(el).val();
};

/**
 * Clears the form data.  Takes the following actions on the form's input fields:
 *  - input text fields will have their 'value' property set to the empty string
 *  - select elements will have their 'selectedIndex' property set to -1
 *  - checkbox and radio inputs will have their 'checked' property set to false
 *  - inputs of type submit, button, reset, and hidden will *not* be effected
 *  - button elements will *not* be effected
 */
$.fn.clearForm = function() {
	return this.each(function() {
		$('input,select,textarea', this).clearFields();
	});
};

/**
 * Clears the selected form elements.
 */
$.fn.clearFields = $.fn.clearInputs = function() {
	var re = /^(?:color|date|datetime|email|month|number|password|range|search|tel|text|time|url|week)$/i; // 'hidden' is not in this list
	return this.each(function() {
		var t = this.type, tag = this.tagName.toLowerCase();
		if (re.test(t) || tag == 'textarea') {
			this.value = '';
		}
		else if (t == 'checkbox' || t == 'radio') {
			this.checked = false;
		}
		else if (tag == 'select') {
			this.selectedIndex = -1;
		}
	});
};

/**
 * Resets the form data.  Causes all form elements to be reset to their original value.
 */
$.fn.resetForm = function() {
	return this.each(function() {
		// guard against an input with the name of 'reset'
		// note that IE reports the reset function as an 'object'
		if (typeof this.reset == 'function' || (typeof this.reset == 'object' && !this.reset.nodeType)) {
			this.reset();
		}
	});
};

/**
 * Enables or disables any matching elements.
 */
$.fn.enable = function(b) {
	if (b === undefined) {
		b = true;
	}
	return this.each(function() {
		this.disabled = !b;
	});
};

/**
 * Checks/unchecks any matching checkboxes or radio buttons and
 * selects/deselects and matching option elements.
 */
$.fn.selected = function(select) {
	if (select === undefined) {
		select = true;
	}
	return this.each(function() {
		var t = this.type;
		if (t == 'checkbox' || t == 'radio') {
			this.checked = select;
		}
		else if (this.tagName.toLowerCase() == 'option') {
			var $sel = $(this).parent('select');
			if (select && $sel[0] && $sel[0].type == 'select-one') {
				// deselect all other options
				$sel.find('option').selected(false);
			}
			this.selected = select;
		}
	});
};

// helper fn for console logging
function log() {
	var msg = '[jquery.form] ' + Array.prototype.join.call(arguments,'');
	if (window.console && window.console.log) {
		window.console.log(msg);
	}
	else if (window.opera && window.opera.postError) {
		window.opera.postError(msg);
	}
};

})(jQuery);/*
 * operamasks-ui omButton 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 */
;(function($) {
	/**
     * @name omButton
     * @author 罗业刚
     * @class 按钮组件。类似于html中的button、input[type=submit]、input[type=button]，使用背景图片实现圆角，支持icon，可以只显示icon。<br/><br/>
     * <b>特点：</b><br/>
     * <ol>
     *      <li>实现圆角</li>
     *      <li>支持左icon和右icon，可同时出现左右icon，也可以只显示icon不显示label</li>
     *      <li>按钮文字数目不限，也可以任意设置按钮宽度</li>
     *      <li>支持input[type=button]、input[type=submit]、input[type=reset]、button、a四种标签形式</li>
     * </ol>
     * 
     * <b>示例：</b><br/>
     * <pre>
     * &lt;script type="text/javascript" &gt;
     * $(document).ready(function() {
     *     $('#bnt').omButton({
     *         icons : {primary:'images/help.png',secondary:'images/edit_add.png'},
     *         width : 150,
     *         disabled : 'disabled',
     *         onClick : function(event){
     *             // do something
     *         }
     *     });
     * });
     * &lt;/script&gt;
     * 
     * &lt;input id="btn" type="submit" /&gt;
     * </pre>
     * 
     * @constructor
     * @description 构造函数. 
     * @param p 标准config对象：{}
     */
    $.widget('om.omButton', {
        options: {
        	/**
        	 * 按钮是否可用的标识，页面要判断按钮是否可用，可以使用$('#id').attr('disabled')获取。
             * @type String 
             * @default null 
             * @example
             * disabled : 'disabled' 
             * return 无 
             */
            disabled : null ,
            /**
             * 按钮的label值，label值可以写到dom元素里面，也可以设置的属性里面，设置到属性里面的优先级最高
             * @type String 
             * @default null 
             * @example
             * label : 'apusic'
             * return 无 
             */
            label : null ,
            /**
        	 * 按钮icon图标路径，分为左icon：primary和右icon：secondary，也可以两个都设置。
             * @type Object 
             * @default null 
             * @example
             * icons : {primary:'images/help.png',secondary:'images/edit_add.png'}
             * return 无 
             */
            icons: {
    			primary: null,
    			secondary: null
    		},
    		/**
        	 * 按钮宽度，按钮宽度会随着按钮文字的多少自动改变，也可以设置固定宽度，设置固定宽度之后按钮将不会随文字的多少而改变
             * @type Number 
             * @default null 
             * @example
             * width : 150
             * return 无 
             */
            width : null ,
            /**
        	 * 按钮的点击事件，你可以托管给组建，你也可以不设置任何事件，不影响原来的监听体系
             * @type Function 
             * @default null 
             * @example
             * onClick : function(){
             *    //do something
             * }
             * return 无 
             */
            onClick : null
        },
        _create:function(){
            var self = this;
            var disabledState = this.options.disabled;
            if ( typeof disabledState != "boolean" ) {
                disabledState = this.element.propAttr( "disabled" );
    		}
            this._determineButtonType();
            var options = this.options;
            if ( this.buttonElement.attr('disabled') == 'disabled' || options.disabled == 'disabled') {
    			options.disabled = true;
    		}
            this._initButton();
            if(options.disabled){
            	self._addClass('disabled');
            	this.buttonElement.css('cursor','default');
            }
            var newButtonElement = this.buttonElement.parent().parent();
            newButtonElement.bind( 'mouseenter.button',function( event ){
            					if ( options.disabled ) {
            						return false;
            					}
            					self._addClass('hover');
            				}).bind( "mouseleave.button", function( event ) {
            					if ( options.disabled ) {
            						return false;
            					}
            					self._removeClass('hover');
            				}).bind( "click.button", function( event ){
            					if ( options.disabled ) {
            						event.preventDefault();
            						event.stopImmediatePropagation();
            						return false;
            					}
            				}).bind( "mousedown.button", function( event ) {
            					if ( options.disabled ) {
            						return false;
            					}
            					self._addClass('active');
            				    self._removeClass('focus');
            					var onClick = options.onClick;
        		                if ( onClick && onClick( event ) === false ) {
        		                    return;
        		                }
            				})
            				.bind( "mouseup.button", function( event ) {
            					if ( options.disabled ) {
            						return false;
            					}
            					self._addClass('focus');
            					self._removeClass('active');
            				})
            				.bind( "keydown.button", function(event) {
            					if ( options.disabled ) {
            						return false;
            					}
            					if ( event.keyCode == $.ui.keyCode.SPACE || event.keyCode == $.ui.keyCode.ENTER ) {
            						self._addClass('active');
            					}
            					if( event.keyCode == $.ui.keyCode.SPACE){
            						var onClick = options.onClick;
            		                if ( onClick && onClick( event ) === false ) {
            		                    return;
            		                }
            					}
            				})
            				.bind( "keyup.button", function() {
            					self._removeClass('active');
            				});
	            this.element.bind( "focus", function( event ) {
								if ( options.disabled ) {
									return false;
								}
							}).bind( "blur", function( event ) {
	        					if ( options.disabled ) {
	        						return false;
	        					}
	        					self._removeClass('focus');
	        				});
        },
        /**
         * 设置按钮为可用状态
         * @name omButton#enable
         * @function
         * @param 无
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('#btn').omButton('enable');
         */
        enable : function(){
            this._removeClass('disabled');
            this.options.disabled = false;
            this.buttonElement.css('cursor','pointer');
            return this.element.removeAttr('disabled');
        },
        /**
         * 设置按钮为不可用状态
         * @name omButton#disable
         * @function
         * @param 无
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('#btn').omButton('disable');
         */
        disable : function(){
        	this._addClass('disabled');
            this.options.disabled = true;
            this.buttonElement.css('cursor','default');
            return this.element.attr('disabled', 'disabled');
        },
        /**
         * 触发托管给按钮的点击事件，如果没有托管任何事件给按钮则此方法无响应
         * @name omButton#onClick
         * @function
         * @param 无
         * @returns 无
         * @example
         * $('#btn').omButton('onClick');
         */
        onClick : function(){
        	if(!this.options.disabled && this.options.onClick){
	        		this.options.onClick();
            }
        },
        /**
         * 设置按钮的label属性
         * @name omButton#changeLabel
         * @function
         * @param label 新的label属性值
         * @returns 无
         * @example
         * $('#btn').omButton('changeLabel','按钮label');
         */
        changeLabel : function(label){
            if(this.type == 'a'){
            	this.buttonElement.text(label) ;
            }else if( this.type == 'input' ){
            	this.buttonElement.val(label) ;
            }else if ( this.type == 'button' ){
            	this.buttonElement.html(label) ;
            }
        },
        /**
         * 设置按钮的icon属性
         * @name omButton#changeIcons
         * @function
         * @param icons 新icon属性值，如果只改变一个则另外一个不要设置
         * @returns 无
         * @example
         * $('#btn').omButton('changeIcons',{primary:'images/help.png',secondary:'images/edit_add.png'});
         */
        changeIcons : function( icons ){
        	if( !this.options.disabled ){
	            if( icons ){
	            	icons.primary?this.buttonElement.css( 'backgroundImage','url( '+icons.primary+' )' ):null;
	            	icons.secondary?this.buttonElement.next().attr( 'src',icons.secondary ):null;
	            }
            }
        },
        _addClass : function( state ){
        	this.buttonElement.parent().parent().addClass( 'om-state-'+state );
        },
        _removeClass : function( state ){
        	this.buttonElement.parent().parent().removeClass( 'om-state-'+state );
        },
        _initButton : function(){
        	var options = this.options;
        	var wrapperSpan = $( '<span>' ).addClass( 'om-btn om-state-default' ),
        	    leftSpan = $( '<span>' ).addClass( 'om-btn-bg om-btn-left' ),
        	    centerSpan = $( '<span>' ).addClass( 'om-btn-bg om-btn-center' ),
        	    rightSpan = $( '<span>' ).addClass( 'om-btn-bg om-btn-right' );
        	if( options.width > 10 ){
        		centerSpan.css( 'width',parseInt( options.width )-10 );
        	}
        	var buttonElement = this.buttonElement.addClass( 'om-btn-txt' );
        	if( this.type == 'a' || this.type == 'button' ){
        	    buttonElement.html( this._getLabel() );
        	}else{
        	    buttonElement.val( this._getLabel() );
        	}
        	if( options.icons.primary ){
        	    if( this._getLabel() ){
        	        buttonElement.addClass( 'om-btn-icon' ).css( 'background-image','url('+options.icons.primary+')' );
        	    }else{
        	        buttonElement.addClass( 'om-btn-only-icon' ).css('background-image','url('+options.icons.primary+')' );
        	    }
        	}
        	buttonElement.css( {'background-position':'left center','background-repeat':'no-repeat'} );
        	buttonElement.wrap( wrapperSpan );
        	leftSpan.insertBefore( buttonElement );
        	rightSpan.insertAfter( buttonElement );
        	buttonElement.wrap( centerSpan );
        	if( options.icons.secondary ){
        	    if( this._getLabel() != '' ){
        	        $( '<img>' ).attr( 'src',options.icons.secondary ).css( {'vertical-align':'text-top','padding-left':'3px'} ).insertAfter( buttonElement );
        	    }else{
        	        $( '<img>' ).attr( 'src',options.icons.secondary ).css( 'vertical-align','text-top' ).insertAfter( buttonElement );
        	    }
            }
        },
        _getLabel : function(){
        	return this.options.label || this.buttonElement.html() || this.buttonElement.text() || this.buttonElement.val();
        },
        _determineButtonType: function() {
    		if ( this.element.is("input") ) {
    			this.type = "input";
    		}  else if ( this.element.is("a") ) {
    			this.type = "a";
    		} else if ( this.element.is('button') ){
    			this.type = "button";
    		}
    		this.buttonElement = this.element;
    	}
    });
})(jQuery);/*
 * operamasks-ui omCalendar 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 */
/**
 * @author 林晓民
 * @name omCalendar
 * @param p 标准config对象 {minDate : new Date(2011, 7, 5), maxDate : new Date(2011, 7, 15)}
 * @class 日历控件。默认通过绑定一个输入域来触发展示，同时也可以直接显示在页面上。<br /> 
 * 日历控件提供了一系列配置项参数和常用的组件api，尽量考虑到各种不同的应用场景。请注意，此控件支持的年份从100到10000。<br /><br/>
 * <b>工具方法:</b>
 * <pre>$.calendar.formatDate(date, formatter)</pre>
 * <p>将传入的日期对象date按照formatter格式化成一个string并返回.</p>
 * <pre>$.calendar.parseDate(date_string, formatter)</pre>
 * <p>传入的string遵循formatter格式,此时提取日期信息,构建并返回日期对象.</p>
 * <p>两个方法都接收一个格式字符串参数formater，大概像这样："yy-m-d H:i"，<br />
 * 在该字符串中每个字母(如 yy , m 等等)都有严格的定义，如下所示：<br /></p>
 * <b>日期格式定义:</b>
 * <pre>
 * y:   年份(取后面2位字符),<br />
 * yy:  年份(用4位字符表示),<br />
 * m:   月份(数字表示,最少1个字符表示),<br />
 * mm:  月份(数字表示,最少2个字符表示,不足时在前面添加0),<br />
 * d:   日期(最少1个字符表示),<br />
 * dd:  日期(最少2个字符表示,不足时在前面添加0),<br />
 * h:   小时(12小时制,最少2个字符表示,不足时在前面添加0,取值范围00~11),<br />
 * H:   小时(24小时制,最少2个字符表示,不足时在前面添加0,取值范围00~23),<br />
 * g:   小时(12小时制,最少1个字符表示,取值范围0~11),<br />
 * G:   小时(24小时制,最少1个字符表示,取值范围0~23),<br />
 * i:   分钟(最少2个字符表示,取值范围00~59),<br />
 * s:   秒钟(最少2个字符表示,取值范围00~59),<br />
 * u:   毫秒(最少3个字符表示,取值范围000~999),<br />
 * D:   星期的简写(如 Sun, Sat等等),<br />
 * DD:  星期的全名(如 Sunday, Saturday等等),<br />
 * M:   月份的简写(如 Jan, Feb 等等),<br />
 * MM:  月份的全名(如 January, February等),<br />
 * a:   上午下午(小写 am & pm),<br />
 * A:   上午下午(大写 AM & PM),<br />
 * </pre>
 */
;(function($) {
	function Calendar(trigger, config) {
        this._init(trigger, config);
    }
	
	$.extend(Calendar.prototype, {

		/**
		 * 日历构造函数
		 * @method 	_init
		 * @param { string }	selector
		 * @param { string }	config
		 * @private
		 */
        _init: function(selector, config) {
            var self = this,con = selector;
            self.id = self.C_Id = self._stamp(con);
            self._buildParam(config);

            /*
             self.con  日历的容器
             self.id   传进来的id
             self.C_Id 永远代表日历容器的ID
             */
            if (!self.popup) {
                self.con = con;
            } else {
                self.trigger = con;
                //self.trigger仍然指向input,con却成了input的父节点span
                con = $('<span></span>').insertAfter(selector).wrapInner(selector);
                selector.after('<span class="om-calendar-trigger om-icon om-icon-calendar"></span>');
                self.con = $('<div></div>');
                $('body').append(self.con);
				self.C_Id = self._stamp(self.con);
                self.con.css({
                    'top':'0px',
                    'position':'absolute',
                    'background':'white',
                    'visibility':'hidden'
                });
                con.addClass("om-calendar om-widget om-state-default");
                
                var defaultVal = self.trigger.val();
                if ($.trim(defaultVal) !='') {
                    try {
                        var inputDate = $.calendar.parseDate(defaultVal, self.dateFormat);
                        inputDate && (self.date = inputDate);
                    } catch (e) {
                    }
                }
            }

            self.render();
            self._buildEvent();
            //只有当日历控件带有input时才可以禁用或者不可编辑
            if (self.popup) {
                if (self.readOnly || !self.editable) {
                    self.trigger.attr('readonly', 'readOnly').unbind();
                    self.trigger.next().addClass('om-state-disabled').unbind();
                }
                if (self.disabled) {
                    self.trigger.attr("disabled", true);
                    self.trigger.next().addClass('om-state-disabled').unbind();
                }
            }
            return this;
        },

        render: function(o) {
            var self = this,
                i = 0,
                _prev,_next,_oym;

            o = o || {};
            self._parseParam(o);
            self.ca = [];

            self.con.addClass('om-calendar-list-wrapper om-widget om-clearfix multi-' + self.pages + ' om-widget-content');
            self.con.html('');

            for (i = 0,_oym = [self.year,self.month]; i < self.pages; i++) {
                if (i === 0) {
                    _prev = true;
                } else {
                    _prev = false;
                    _oym = self._computeNextMonth(_oym);
                }
                _next = i == (self.pages - 1);
                self.ca.push(new self.Page({
                    year:_oym[0],
                    month:_oym[1],
                    prevArrow:_prev,
                    nextArrow:_next,
                    showTime:self.showTime
                }, self));


                self.ca[i].render();
            }
            return this;

        },

		/**
		 * 用以给容器打上id的标记,容器有id则返回
		 * @method _stamp
		 * @param { JQuery-Node }
		 * @return { string }
		 * @private
		 */
		_stamp: function(el){
			if(el.attr('id') === undefined || el.attr('id')===''){
				el.attr('id','K_'+ new Date().getTime());
			}
			return el.attr('id');
		},

        /**
         * 计算d天的前几天或者后几天，返回date
		 * @method _showdate
		 * @private
         */
        _showdate: function(n, d) {
            var uom = new Date(d - 0 + n * 86400000);
            uom = uom.getFullYear() + "/" + (uom.getMonth() + 1) + "/" + uom.getDate();
            return new Date(uom);
        },

        /**
         * 创建日历外框的事件
		 * @method _buildEvent
		 * @private
         */
        _buildEvent: function() {
            var self = this;
            if (!self.popup){
				return this;
			}
            //点击空白
            //flush event
            for (var i = 0; i < self.EV.length; i++) {
                if (self.EV[i] !== undefined) {
                    self.EV[i].unbind(".omCalendar");
                }
            }
            self.EV[0] = $('body').bind('click.omCalendar', function(e) {
                //TODO e.target是裸的节点，这句不得不加，虽然在逻辑上并无特殊语义
                e.target = $(e.target);
                //点击到日历上
                if (e.target.attr('id') === self.C_Id){
					return;
				}
                if ((e.target.hasClass('om-next') || e.target.hasClass('om-prev')) && 
                    e.target[0].tagName === 'A'){
					return;
				}
                //点击到月选择器上
                if(e.target[0].tagName == "OPTION") {
                    var monthSelected = false;
                    $("#"+ self.C_Id +" select option").each(function(){
                        if(this == e.target[0]) {
                            monthSelected = true;
                            return false;
                        }
                    });
                    if(monthSelected) {
                        return;
                    }
                }
                
                //点击在trigger上
                if (e.target.attr('id') == self.id || e.target.siblings().attr('id') == self.id){
                    return;
				}

				if(self.con.css('visibility') == 'hidden') return ;
				var inRegion = function(dot,r){
					if(dot[0]> r[0].x && dot[0]<r[1].x && dot[1] > r[0].y && dot[1] < r[1].y){
						return true;
					}else{
						return false;
					}
				};

				/*
                if (!S.DOM.contains(S.one('#' + self.C_Id), e.target)) {
				*/
				if(!inRegion([e.pageX,e.pageY],[
								{
									x:self.con.offset().left,
									y:self.con.offset().top
								},
								{
									x:self.con.offset().left + self.con.width(),
									y:self.con.offset().top + self.con.height()
								}])){
                    self.hide();
                }
            });
            //点击触点
            for (i = 0; i < self.triggerType.length; i++) {
                var inputTrigger = self.trigger || self.con || $("#" + self.id);
                self.EV[1] = inputTrigger.bind(self.triggerType[i] + ".omCalendar", function(e) {
                    e.target = $(e.target);
                    e.preventDefault();
                    //如果focus和click同时存在的hack
                    var a = self.triggerType;
                    if ($.inArray('click', a) >= 0 && $.inArray('focus', a) >= 0) {//同时含有
                        if (e.type == 'focus') {
                            self.toggle();
                        }
                    } else if ($.inArray('click', a) >= 0 && $.inArray('focus', a) < 0) {//只有click
                        if (e.type == 'click') {
                            self.toggle();
                        }
                    } else if ($.inArray('click', a) < 0 && $.inArray('focus', a) >= 0) {//只有focus
                        setTimeout(function() {//为了跳过document.onclick事件
                            self.toggle();
                        }, 170);
                    } else {
                        self.toggle();
                    }

                });
            }
            self.EV[2] = inputTrigger.bind('focus.omCalendar', function(){
                //$(this).addClass('om-calendar-focus');
                $(this).parent().addClass('om-state-hover');
                $(this).parent().addClass('om-state-active');
                //$(this).parent().addClass('om-span-field-focus');
            });
            self.EV[3] = inputTrigger.bind('blur.omCalendar', function(){
                //$(this).removeClass('om-calendar-focus');
                $(this).parent().removeClass('om-state-hover');
                $(this).parent().removeClass('om-state-active');
                //$(this).parent().removeClass('om-span-field-focus');
            });
            
            var iconTrigger = inputTrigger.next();
            self.EV[4] = iconTrigger.bind('click.omCalendar', function(){
                inputTrigger.trigger('focus');
                self.show();
            });
            
            self.EV[5] = iconTrigger.bind('mouseover.omCalendar', function(){
                $(this).parent().addClass('om-state-hover');
            });
            
            self.EV[6] = iconTrigger.bind('mouseout.omCalendar', function(){
                !self.isVisible() && $(this).parent().removeClass('om-state-hover');
            });
            /*
            self.EV[7] = iconTrigger.bind('mousedown.omCalendar', function(){
                $(this).addClass('om-trigger-press');
            });
            
            self.EV[8] = iconTrigger.bind('mouseup.omCalendar', function(){
                $(this).removeClass('om-trigger-press');
            });
            */
            return this;
        },

		/**
		 * 改变日历是否显示的状态
		 * @mathod toggle
		 */
        toggle: function() {
            var self = this;
            if (!self.isVisible()) {
                self.show();
            } else {
                self.hide();
            }
        },
        
        isVisible : function() {
            var self = this;
            if (self.con.css('visibility') == 'hidden') {
                return false;
            } 
            return true;
        },

        /**
         * 显示日历
		 * @method show
         */
        show: function() {
            var self = this;
            self.con.css('visibility', '');
            var _x = self.trigger.parent().offset().left,
                //KISSY得到DOM的width是innerWidth，这里期望得到outterWidth
                height = self.trigger.parent().offsetHeight || self.trigger.parent().outerHeight(),
                _y = self.trigger.parent().offset().top + height;
            self.con.css('left', _x.toString() + 'px');
            self.con.css('top', _y.toString() + 'px');
            return this;
        },

        /**
         * 隐藏日历
		 * @method hide
         */
        hide: function() {
            var self = this;
            self.con.css('visibility', 'hidden');
            $('.om-trigger-press').removeClass('om-trigger-press');
            return this;
        },

        /**
         * 创建参数列表
		 * @method _buildParam
		 * @private
         */
        _buildParam: function(o) {
            var self = this;
            if (o === undefined || o === null) {
                o = { };
            }

            function setParam(def, key) {
                var v = o[key];
                // null在这里是“占位符”，用来清除参数的一个道具
                self[key] = (v === undefined || v === null) ? def : v;
            }

			//这种处理方式不错
            var defaults = {
                /**
                 * 默认显示日期.
                 * @name omCalendar#date
                 * @type Date
                 * @default 当前日期
                 * @example 
                 *   $("input").omCalendar({date : new Date(2012, 0, 1)});
                 */
                date:        new Date(),
                
                /**
                 * 起始星期.值为0-6的数字，分别代表星期天到星期六.默认起始星期是周日.
                 * @name omCalendar#startDay
                 * @type Number
                 * @default 0
                 * @example
                 *   $("input").omCalendar({startDay : 1});
                 */
                startDay:    0,
                
                /**
                 * 显示日历的页数.默认为1页.
                 * @name omCalendar#pages
                 * @type Number
                 * @default 1
                 * @example
                 *   $("input").omCalendar({pages : 3});
                 */
                pages:       1,
                closable:    true,
                rangeSelect: false,
                
                /**
                 * 最小日期.默认为空.
                 * @name omCalendar#minDate
                 * @type Date
                 * @default 无
                 * @example
                 *   $("input").omCalendar({minDate : new Date(2010, 0, 1)});
                 */
                minDate:  false,
                
                /**
                 * 最大日期.默认为空.
                 * @name omCalendar#maxDate
                 * @type Date
                 * @default 无
                 * @example
                 *   $("input").omCalendar({maxDate : new Date(2010, 0, 1)});
                 */
                maxDate:    false,
                multiSelect: false,
                navigator:   true,
                
                /**
                 * 是否是弹出式日历，默认为true。
                 * @name omCalendar#popup
                 * @type Boolean
                 * @default true
                 * @example
                 *   <div id="container" />
                 *   $("#container").omCalendar({popup : false});
                 */
                popup:       true,
                
                /**
                 * 是否显示时间.默认不显示.
                 * @name omCalendar#showTime
                 * @type Boolean
                 * @default false
                 * @example
                 *   $("input").omCalendar({showTime : true});
                 */
                showTime:    false,
                triggerType: ['click'],
                
                /**
                 * 选择日期后触发.
                 * @event
                 * @name omCalendar#onSelect
                 * @type Function
                 * @default emptyFn
                 * @param 选中的日期
                 * @example
                 *   $("input").omCalendar({onSelect : function(date) {alert(date);}});
                 */
                onSelect:    function(d) {}, 
                
                /**
                 * 不可选的星期.数组类型，里面的元素为 0-6，分别代表星期天到星期六.
                 * @name omCalendar#disabledDays
                 * @type Array[Number]
                 * @default []
                 * @param 
                 * @example
                 *   将周六和周日设为不可用:
                 *     $("input").omCalendar({disabledDays : [0, 6]});
                 */
                disabledDays : [], 
                
                /**
                 * 设置过滤不可选日期方法.
                 * @name omCalendar#disabledFn
                 * @type Function
                 * @default 无
                 * @param 未经过滤的日期
                 * @example 
                 * 大于当月10号的日期不可用
                 * $("input").omCalendar({disabledFn : function disFn(date) {
                 *            var nowMiddle = new Date();
                 *             nowMiddle.setDate(10);
                 *             if (date > nowMiddle) {
                 *              return false;
                 *             }
                 *          }});
                 */
                disabledFn : function(d) {}, 
                
                /**
                 * 是否禁用组件
                 * @name omCalendar#disabled
                 * @type Boolean
                 * @default false
                 * @example
                 * $("input").omCalendar({disabled : false});
                 */
                disabled : false,
                
                /**
                 * 组件是否只读
                 * @name omCalendar#readOnly
                 * @type Boolean
                 * @default false
                 * @example
                 * $("input").omCalendar({readOnly : false});
                 */
                readOnly : false,
                
                /**
                 * 组件是否可编辑
                 * @name omCalendar#editable
                 * @type Boolean
                 * @default false
                 * @example
                 * $("input").omCalendar({editable : true});
                 */
                editable : true,
                
                /**
                 * 日期格式化。具体的日期格式化参数请参考预览页签。如果设置showtime:false，默认取值为 'yy-mm-dd'，否则为 'yy-mm-dd H:i:s'。
                 * @name omCalendar#dateFormat
                 * @type String
                 * @default 'yy-mm-dd H:i:s'
                 * @example
                 * $("input").omCalendar({dateFormat : 'yy-mm-dd'});
                 */
                dateFormat : false
            };
			
			for (var i in defaults) {
				setParam(defaults[i], i);
			}

			// 支持用户传进来一个string
			if(typeof o.triggerType === 'string'){
				o.triggerType = [o.triggerType];
			}

            setParam(self.date, 'selected');
            if(o.startDay){
				self.startDay = (7 - o.startDay) % 7;
			}

            if (!self.dateFormat) {
                self.dateFormat = self.showTime ?  "yy-mm-dd H:i:s" : "yy-mm-dd"; 
            }
            
            if (o.range !== undefined && o.range !== null) {
                var s = self._showdate(1, new Date(o.range.start.getFullYear() + '/' + (o.range.start.getMonth() + 1) + '/' + (o.range.start.getDate())));
                var e = self._showdate(1, new Date(o.range.end.getFullYear() + '/' + (o.range.end.getMonth() + 1) + '/' + (o.range.end.getDate())));
                self.range = {
                    start:s,
                    end:e
                };
            } else {
                self.range = {
                    start:null,
                    end:null
                };
            }
            self.EV = [];
            return this;
        },

        /**
         * 过滤参数列表
		 * @method _parseParam
		 * @private
         */
        _parseParam: function(o) {
            var self = this,i;
            if (o === undefined || o === null) {
                o = {};
            }
            for (i in o) {
                self[i] = o[i];
            }
            self._handleDate();
            return this;
        },

        /**
         * 模板函数
		 * @method _templetShow
		 * @private
         */
        _templetShow: function(templet, data) {
            var str_in,value_s,i,m,value,par;
            if (data instanceof Array) {
                str_in = '';
                for (i = 0; i < data.length; i++) {
                    str_in += arguments.callee(templet, data[i]);
                }
                templet = str_in;
            } else {
                value_s = templet.match(/{\$(.*?)}/g);
                if (data !== undefined && value_s !== null) {
                    for (i = 0,m = value_s.length; i < m; i++) {
                        par = value_s[i].replace(/({\$)|}/g, '');
                        value = (data[par] !== undefined) ? data[par] : '';
                        templet = templet.replace(value_s[i], value);
                    }
                }
            }
            return templet;
        },

        /**
         * 处理日期
		 * @method _handleDate
		 * @private
         */
        _handleDate: function() {
            var self = this,
            date = self.date;
            self.weekday = date.getDay() + 1;//星期几 //指定日期是星期几
            self.day = date.getDate();//几号
            self.month = date.getMonth();//月份
            self.year = date.getFullYear();//年份
            return this;
        },

        //get标题
        _getHeadStr: function(year, month) {
            return year.toString() + '年' + (Number(month) + 1).toString() + '月';
        },

        //月加
        _monthAdd: function() {
            var self = this;
            if (self.month == 11) {
                self.year++;
                self.month = 0;
            } else {
                self.month++;
            }
            self.date = new Date(self.year.toString() + '/' + (self.month + 1).toString() + '/' + self.day.toString());
            return this;
        },

        //月减
        _monthMinus: function() {
            var self = this;
            if (self.month === 0) {
                self.year--;
                self.month = 11;
            } else {
                self.month--;
            }
            self.date = new Date(self.year.toString() + '/' + (self.month + 1).toString() + '/' + self.day.toString());
            return this;
        },

        //裸算下一个月的年月,[2009,11],年:fullYear，月:从0开始计数
        _computeNextMonth: function(a) {
            var _year = a[0],
                _month = a[1];
            if (_month == 11) {
                _year++;
                _month = 0;
            } else {
                _month++;
            }
            return [_year,_month];
        },

        //处理日期的偏移量
        _handleOffset: function() {
            var self = this,
                data = ['日','一','二','三','四','五','六'],
                temp = '<span>{$day}</span>',
                offset = self.startDay,
                day_html = '',
                a = [];
            for (var i = 0; i < 7; i++) {
                a[i] = {
                    day:data[(i - offset + 7) % 7]
                };
            }
            day_html = self._templetShow(temp, a);

            return {
                day_html:day_html
            };
        },

        //处理起始日期,d:Date类型
        _handleRange: function(d) {
            var self = this,t;
            if ((self.range.start === null && self.range.end === null ) || (self.range.start !== null && self.range.end !== null)) {
                self.range.start = d;
                self.range.end = null;
                self.render();
            } else if (self.range.start !== null && self.range.end === null) {
                self.range.end = d;
                if (self.range.start.getTime() > self.range.end.getTime()) {
                    t = self.range.start;
                    self.range.start = self.range.end;
                    self.range.end = t;
                }
                self.fire('rangeSelect', self.range);
                self.render();
            }
            return this;
        }
    });
	
	$.extend(Calendar.prototype, {
		Page: function(config, father) {

            //属性
            this.father = father;
            this.month = Number(config.month);
            this.year = Number(config.year);
            this.prevArrow = config.prevArrow;
            this.nextArrow = config.nextArrow;
            this.node = null;
            this.timmer = null;//时间选择的实例
            this.id = '';
            this.EV = [];
            this.html = [
                '<div class="om-cal-box" id="{$id}">',
                '<div class="om-cal-hd om-widget-header">',
                '<a href="javascript:void(0);" class="om-prev {$prev}"><span class="om-icon om-icon-circle-triangle-w">Prev</span></a>',
                '<a href="javascript:void(0);" class="om-title">{$title}</a>',
                '<a href="javascript:void(0);" class="om-next {$next}"><span class="om-icon om-icon-circle-triangle-e">Next</span></a>',
                '</div>',
                '<div class="om-cal-bd">',
                '<div class="om-whd">',
                /*
                 '<span>日</span>',
                 '<span>一</span>',
                 '<span>二</span>',
                 '<span>三</span>',
                 '<span>四</span>',
                 '<span>五</span>',
                 '<span>六</span>',
                 */
                father._handleOffset().day_html,
                '</div>',
                '<div class="om-dbd om-clearfix">',
                '{$ds}',
                /*
                 <a href="" class="om-null">1</a>
                 <a href="" class="om-state-disabled">3</a>
                 <a href="" class="om-selected">1</a>
                 <a href="" class="om-today">1</a>
                 <a href="">1</a>
                 */
                '</div>',
                '</div>',
                '<div class="om-setime om-state-default hidden">',
                '</div>',
                '<div class="om-cal-ft {$showtime}">',
                '<div class="om-cal-time om-state-default">',
                '时间：00:00 &hearts;',
                '</div>',
                '</div>',
                '<div class="om-selectime om-state-default hidden">',//<!--用以存放点选时间的一些关键值-->',
                '</div>',
                '</div><!--#om-cal-box-->'
            ].join("");
            this.nav_html = [
                '<p>',
                '月',
                '<select value="{$the_month}">',
                '<option class="m1" value="1">01</option>',
                '<option class="m2" value="2">02</option>',
                '<option class="m3" value="3">03</option>',
                '<option class="m4" value="4">04</option>',
                '<option class="m5" value="5">05</option>',
                '<option class="m6" value="6">06</option>',
                '<option class="m7" value="7">07</option>',
                '<option class="m8" value="8">08</option>',
                '<option class="m9" value="9">09</option>',
                '<option class="m10" value="10">10</option>',
                '<option class="m11" value="11">11</option>',
                '<option class="m12" value="12">12</option>',
                '</select>',
                '</p>',
                '<p>',
                '年',
                '<input type="text" value="{$the_year}" onfocus="this.select()"/>',
                '</p>',
                '<p>',
                '<button class="ok">确定</button><button class="cancel">取消</button>',
                '</p>'
            ].join("");


            //方法
            //常用的数据格式的验证
            this.Verify = function() {

                var isDay = function(n) {
                    if (!/^\d+$/i.test(n)){
						return false;
					}
                    n = Number(n);
                    return !(n < 1 || n > 31);

                },
                    isYear = function(n) {
                        if (!/^\d+$/i.test(n)){
							return false;
						}
                        n = Number(n);
                        return !(n < 100 || n > 10000);

                    },
                    isMonth = function(n) {
                        if (!/^\d+$/i.test(n)){
							return false;
						}
                        n = Number(n);
                        return !(n < 1 || n > 12);


                    };

                return {
                    isDay:isDay,
                    isYear:isYear,
                    isMonth:isMonth

                };

            };

            /**
             * 渲染子日历的UI
             */
            this._renderUI = function() {
                var cc = this,_o = {},ft;
                cc.HTML = '';
                _o.prev = '';
                _o.next = '';
                _o.title = '';
                _o.ds = '';
                if (!cc.prevArrow) {
                    _o.prev = 'hidden';
                }
                if (!cc.nextArrow) {
                    _o.next = 'hidden';
                }
                if (!cc.father.showtime) {
                    _o.showtime = 'hidden';
                }
                _o.id = cc.id = 'om-cal-' + Math.random().toString().replace(/.\./i, '');
                _o.title = cc.father._getHeadStr(cc.year, cc.month);
                cc.createDS();
                _o.ds = cc.ds;
                cc.father.con.append(cc.father._templetShow(cc.html, _o));
                cc.node = $('#' + cc.id);
                if (cc.father.showTime) {
                    ft = cc.node.find('.om-cal-ft');
                    ft.removeClass('hidden');
                    cc.timmer = new cc.father.TimeSelector(ft, cc.father);
                }
                return this;
            };
            /**
             * 创建子日历的事件
             */
            this._buildEvent = function() {
                var cc = this,i,
                    con = $('#' + cc.id);
                //flush event
                for (i = 0; i < cc.EV.length; i++) {
                    if (typeof cc.EV[i] != 'undefined') {
                        cc.EV[i].unbind(".omCalednar");
                    }
                }

                cc.EV[0] = con.find('div.om-dbd').bind('click', function(e) {
                    //e.preventDefault();
                    e.target = $(e.target);
                    if (e.target.hasClass('om-null')){
						return;
					}
                    if (e.target.hasClass('om-state-disabled')){
						return;
					}
                    var selected = Number(e.target.html());
					//如果当天是30日或者31日，设置2月份就会出问题
                    var d = new Date('2010/01/01');
                    d.setYear(cc.year);
                    d.setMonth(cc.month);
                    d.setDate(selected);
                    //self.callback(d);
                    //datetime的date
                    cc.father.dt_date = d;
                    
                    if (!cc.father.showTime) {
                        cc.father.onSelect.call(cc.father.con, d);
                    }
                    
                    if (cc.father.popup && !cc.father.showTime) {
                        if (cc.father.closable) {
                            cc.father.hide();
                        }
                        var dateStr = $.calendar.formatDate(cc.father.dt_date, cc.father.dateFormat);
                        $(cc.father.trigger).val(dateStr);
                    }
                    
                    if (cc.father.rangeSelect) {
                        cc.father._handleRange(d);
                    }
                    cc.father.render({selected:d});
                }).find('a').bind('mouseover',function(e){
                    $(this).addClass('om-state-hover om-state-nobd');
                }).bind('mouseout',function(e){
                    $(this).removeClass('om-state-hover');
                    if($(this).hasClass('om-state-highlight') || $(this).hasClass('om-state-active')){
                        return;
                    } else {
                        $(this).removeClass('om-state-nobd');
                    }
                });
                //向前
                cc.EV[1] = con.find('a.om-prev').bind('click', function(e) {
                    e.preventDefault();
                    cc.father._monthMinus().render();
                    cc.father.con.trigger('monthChange', {
                        date:new Date(cc.father.year + '/' + (cc.father.month + 1) + '/01')
                    });

                });
                //向后
                cc.EV[2] = con.find('a.om-next').bind('click', function(e) {
                    e.preventDefault();
                    cc.father._monthAdd().render();
                    cc.father.con.trigger('monthChange', {
                        date:new Date(cc.father.year + '/' + (cc.father.month + 1) + '/01')
                    });
                });
                if (cc.father.navigator) {
                    cc.EV[3] = con.find('a.om-title').bind('click', function(e) {
                        try {
                            cc.timmer.hidePopup();
                            e.preventDefault();
                        } catch(exp) {
                        }
                        e.target = $(e.target);
                        var setime_node = con.find('.om-setime');
                        setime_node.html('');
                        var in_str = cc.father._templetShow(cc.nav_html, {
                            the_month:cc.month + 1,
                            the_year:cc.year
                        });
                        setime_node.html(in_str);
                        setime_node.find("option:[value=" + (cc.month + 1) + "]").attr("selected", "selected");
                        setime_node.removeClass('hidden');
                        con.find('input').bind('keydown', function(e) {
                            e.target = $(e.target);
                            if (e.keyCode == 38) {//up
                                e.target.val(Number(e.target.val()) + 1);
                                e.target[0].select();
                            }
                            if (e.keyCode == 40) {//down
                                e.target.val(Number(e.target.val()) - 1);
                                e.target[0].select();
                            }
                            if (e.keyCode == 13) {//enter
                                var _month = con.find('.om-setime select').val();
                                var _year = con.find('.om-setime input').val();
                                con.find('.om-setime').addClass('hidden');
                                if (!cc.Verify().isYear(_year)){
									return;
								}
                                if (!cc.Verify().isMonth(_month)){
									return;
								}
                                cc.father.render({
                                    date:new Date(_year + '/' + _month + '/01')
                                });
                                cc.father.con.trigger('monthChange', {
                                    date:new Date(_year + '/' + _month + '/01')
                                });
                            }
                        });
                    }).bind("mouseover", function(e){
                        $(this).addClass("om-state-hover");
                    }).bind("mouseout", function(e){
                        $(this).removeClass("om-state-hover");
                    });
                    cc.EV[4] = con.find('.om-setime').bind('click', function(e) {
                        e.preventDefault();
                        e.target = $(e.target);
                        if (e.target.hasClass('ok')) {
                            var _month = $(this).find('select').val(),
                                _year = $(this).find('input').val();
                            $(this).addClass('hidden');
                            if (!cc.Verify().isYear(_year)){
								return;
							}
                            if (!cc.Verify().isMonth(_month)){
								return;
							}
                            cc.father.render({
                                date:new Date(_year + '/' + _month + '/01')
                            });
                            cc.father.con.trigger('monthChange', {
                                date:new Date(_year + '/' + _month + '/01')
                            });
                        } else if (e.target.hasClass('cancel')) {
                            $(this).addClass('hidden');
                        }
                    });
                }
                return this;

            };
            /**
             * 得到当前子日历的node引用
             */
            this._getNode = function() {
                var cc = this;
                return cc.node;
            };
            /**
             * 得到某月有多少天,需要给定年来判断闰年
             */
            this._getNumOfDays = function(year, month) {
                return 32 - new Date(year, month - 1, 32).getDate();
            };
            /**
             * 生成日期的html
             */
            this.createDS = function() {
                var cc = this,
                    s = '',
                    startweekday = (new Date(cc.year + '/' + (cc.month + 1) + '/01').getDay() + cc.father.startDay + 7) % 7,//当月第一天是星期几
                    k = cc._getNumOfDays(cc.year, cc.month + 1) + startweekday,
                    i, _td_s;
                
                
                var _dis_days = [];
                for (i = 0; i < cc.father.disabledDays.length; i++) {
                    _dis_days[i] = cc.father.disabledDays[i] % 7;
                }

                for (i = 0; i < k; i++) {
                    //prepare data {{
                    if (/532/.test($.browser.webkit)) {//hack for chrome
                        _td_s = new Date(cc.year + '/' + Number(cc.month + 1) + '/' + (i + 1 - startweekday).toString());
                    } else {
                        _td_s = new Date(cc.year + '/' + Number(cc.month + 1) + '/' + (i + 2 - startweekday).toString());
                    }
                    var _td_e = new Date(cc.year + '/' + Number(cc.month + 1) + '/' + (i + 1 - startweekday).toString());
                    //prepare data }}
                    if (i < startweekday) {//null
                        s += '<a href="javascript:void(0);" class="om-null">0</a>';
                    } else if ($.inArray((i + cc.father.startDay) % 7, _dis_days) >= 0) {
                        s += '<a href="javascript:void(0);" class="om-state-disabled">' + (i - startweekday + 1) + '</a>';
                    } else if (cc.father.disabledFn(_td_e) === false) {
                        s += '<a href="javascript:void(0);" class="om-state-disabled">' + (i - startweekday + 1) + '</a>';
                    } else if (cc.father.minDate instanceof Date &&
                        new Date(cc.year + '/' + (cc.month + 1) + '/' + (i + 2 - startweekday)).getTime() < (cc.father.minDate.getTime() + 1)) {//disabled
                        s += '<a href="javascript:void(0);" class="om-state-disabled">' + (i - startweekday + 1) + '</a>';

                    } else if (cc.father.maxDate instanceof Date &&
                        new Date(cc.year + '/' + (cc.month + 1) + '/' + (i + 1 - startweekday)).getTime() > cc.father.maxDate.getTime()) {//disabled
                        s += '<a href="javascript:void(0);" class="om-state-disabled">' + (i - startweekday + 1) + '</a>';

                    } else if ((cc.father.range.start !== null && cc.father.range.end !== null) && //日期选择范围
                        (  _td_s.getTime() >= cc.father._showdate(1,cc.father.range.start).getTime() && _td_e.getTime() < cc.father._showdate(1,cc.father.range.end).getTime())) {

                        if (i == (startweekday + (new Date()).getDate() - 1) &&
                            (new Date()).getFullYear() == cc.year &&
                            (new Date()).getMonth() == cc.month) {//今天并被选择
                            s += '<a href="javascript:void(0);" class="om-state-highlight om-state-nobd">' + (i - startweekday + 1) + '</a>';
                        } else {
                            s += '<a href="javascript:void(0);">' + (i - startweekday + 1) + '</a>';
                        }

                    } else if (i == (startweekday + (new Date()).getDate() - 1) &&
                        (new Date()).getFullYear() == cc.year  &&
                        (new Date()).getMonth() == cc.month) {//today
                        s += '<a href="javascript:void(0);" class="om-state-highlight om-state-nobd">' + (i - startweekday + 1) + '</a>';

                    } else if (i == (startweekday + cc.father.selected.getDate() - 1) &&
                        cc.month == cc.father.selected.getMonth() &&
                        cc.year == cc.father.selected.getFullYear()) {//selected
                        s += '<a href="javascript:void(0);" class="om-state-active om-state-nobd">' + (i - startweekday + 1) + '</a>';
                    } else {//other
                        s += '<a href="javascript:void(0);">' + (i - startweekday + 1) + '</a>';
                    }
                }
                if (k % 7 !== 0) {
                    for (i = 0; i < (7 - k % 7); i++) {
                        s += '<a href="javascript:void(0);" class="om-null">0</a>';
                    }
                }
                cc.ds = s;
                return this;
            };
            /**
             * 渲染
             */
            this.render = function() {
                var cc = this;
                cc._renderUI();
                cc._buildEvent();
                return this;
            };


        }//Page constructor over
    });
	
	$.extend(Calendar.prototype, {
        /**
         * 时间选择构造器
         * @constructor Calendar.TimerSelector
         * @param {object} ft ,timer所在的容器
         * @param {object} father 指向Calendar实例的指针，需要共享父框的参数
         */
        TimeSelector:function(ft, father) {
            //属性
            this.father = father;
            this.fcon = ft.parent('.om-cal-box');
            this.popupannel = this.fcon.find('.om-selectime');//点选时间的弹出层
            if (typeof father.date == 'undefined') {//确保初始值和当前时间一致
                father.date = new Date();
            }
            this.time = father.date;
            this.status = 's';//当前选择的状态，'h','m','s'依次判断更新哪个值
            this.ctime = $('<div class="om-cal-time om-state-default">时间：<span class="h">h</span>:<span class="m">m</span>:<span class="s">s</span><!--{{arrow--><div class="cta"><button class="u om-icon om-icon-triangle-1-n"></button><button class="d om-icon om-icon-triangle-1-s"></button></div><!--arrow}}--></div>');
            this.button = $('<button class="ct-ok om-state-default">确定</button>');
            //小时
            this.h_a = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23'];
            //分钟
            this.m_a = ['00','10','20','30','40','50'];
            //秒
            this.s_a = ['00','10','20','30','40','50'];


            //方法
            /**
             * 创建相应的容器html，值均包含在a中
             * 参数：要拼装的数组
             * 返回：拼好的innerHTML,结尾还要带一个关闭的a
             *
             */
            this.parseSubHtml = function(a) {
                var in_str = '';
                for (var i = 0; i < a.length; i++) {
                    in_str += '<a href="javascript:void(0);" class="om-cal-item">' + a[i] + '</a>';
                }
                in_str += '<a href="javascript:void(0);" class="x">x</a>';
                return in_str;
            };
            /**
             * 显示om-selectime容器
             * 参数，构造好的innerHTML
             */
            this.showPopup = function(instr) {
                var self = this;
                this.popupannel.html(instr);
                this.popupannel.removeClass('hidden');
                var status = self.status;
                var active_cls = "om-state-active om-state-nobd";
                self.ctime.find('span').removeClass(active_cls);
                switch (status) {
                    case 'h':
                        self.ctime.find('.h').addClass(active_cls);
                        break;
                    case 'm':
                        self.ctime.find('.m').addClass(active_cls);
                        break;
                    case 's':
                        self.ctime.find('.s').addClass(active_cls);
                        break;
                }
            };
            /**
             * 隐藏om-selectime容器
             */
            this.hidePopup = function() {
                this.popupannel.addClass('hidden');
            };
            /**
             * 不对其做更多的上下文假设，仅仅根据time显示出来
             */
            this.render = function() {
                var self = this;
                var h = self.get('h');
                var m = self.get('m');
                var s = self.get('s');
                self.father._time = self.time;
                self.ctime.find('.h').html(h);
                self.ctime.find('.m').html(m);
                self.ctime.find('.s').html(s);
                return self;
            };
            //这里的set和get都只是对time的操作，并不对上下文做过多假设
            /**
             * set(status,v)
             * h:2,'2'
             */
            this.set = function(status, v) {
                var self = this;
                v = Number(v);
                switch (status) {
                    case 'h':
                        self.time.setHours(v);
                        break;
                    case 'm':
                        self.time.setMinutes(v);
                        break;
                    case 's':
                        self.time.setSeconds(v);
                        break;
                }
                self.render();
            };
            /**
             * get(status)
             */
            this.get = function(status) {
                var self = this;
                var time = self.time;
                switch (status) {
                    case 'h':
                        return time.getHours();
                    case 'm':
                        return time.getMinutes();
                    case 's':
                        return time.getSeconds();
                }
            };

            /**
             * add()
             * 状态值代表的变量增1
             */
            this.add = function() {
                var self = this;
                var status = self.status;
                var v = self.get(status);
                v++;
                self.set(status, v);
            };
            /**
             * minus()
             * 状态值代表的变量增1
             */
            this.minus = function() {
                var self = this;
                var status = self.status;
                var v = self.get(status);
                v--;
                self.set(status, v);
            };


            //构造
            this._init = function() {
                var self = this;
                ft.html('').append(self.ctime);
                ft.append(self.button);
                self.render();
				//TODO:
                self.popupannel.bind('click', function(e) {
                    var el = $(e.target);
                    if (el.hasClass('x')) {//关闭
                        self.hidePopup();
                    } else if (el.hasClass('om-cal-item')) {//点选一个值
                        var v = Number(el.html());
                        self.set(self.status, v);
                        self.hidePopup();
                    }
                });
                //确定的动作
                self.button.bind('click', function() {
                    //初始化读取父框的date
                    var d = typeof self.father.dt_date == 'undefined' ? self.father.date : self.father.dt_date;
                    d.setHours(self.get('h'));
                    d.setMinutes(self.get('m'));
                    d.setSeconds(self.get('s'));
                    self.father.onSelect.call(self.father.trigger, d);
                    if (self.father.popup) {
                        var dateStr = $.calendar.formatDate(d, self.father.dateFormat);
                        $(self.father.trigger).val(dateStr);
                        if (self.father.closable) {
                            self.father.hide();
                        }
                    }
                });
                //ctime上的键盘事件，上下键，左右键的监听
                //TODO 考虑是否去掉
                self.ctime.bind('keyup', function(e) {
                    if (e.keyCode == 38 || e.keyCode == 37) {//up or left
                        //e.stopPropagation();
                        e.preventDefault();
                        self.add();
                    }
                    if (e.keyCode == 40 || e.keyCode == 39) {//down or right
                        //e.stopPropagation();
                        e.preventDefault();
                        self.minus();
                    }
                });
                //上的箭头动作
                self.ctime.find('.u').bind('click', function() {
                    self.hidePopup();
                    self.add();
                });
                //下的箭头动作
                self.ctime.find('.d').bind('click', function() {
                    self.hidePopup();
                    self.minus();
                });
                //弹出选择小时
                self.ctime.find('.h').bind('click', function() {
                    var in_str = self.parseSubHtml(self.h_a);
                    self.status = 'h';
                    self.showPopup(in_str);
                });
                //弹出选择分钟
                self.ctime.find('.m').bind('click', function() {
                    var in_str = self.parseSubHtml(self.m_a);
                    self.status = 'm';
                    self.showPopup(in_str);
                });
                //弹出选择秒
                self.ctime.find('.s').bind('click', function() {
                    var in_str = self.parseSubHtml(self.s_a);
                    self.status = 's';
                    self.showPopup(in_str);
                });
            };
            this._init();
        }

    });
	
	var docloaded = false;
    $(document).ready(function() {
        docloaded = true;
    });
	
    var publicMethods = {
            /**
             * 将控件设置为不可用.会将input设置为不可用.
             * @name omCalendar#disable
             * @function
             * @returns jQuery对象
             * @example
             * $('input').omCalendar('disable');
             */
            disable : function() {
                return this.each(function() {
                    $(this).attr("disabled", true);
                    $(this).next().addClass('om-state-disabled').unbind();
                });
            }, 
            
            /**
             * 将控件设置为可用状态.会将input设置为可用状态.
             * @name omCalendar#enable
             * @function
             * @returns jQuery对象
             * @example
             * $('input').omCalendar('enable');
             */
            enable : function() {
                return this.each(function() {
                    $(this).attr("disabled", false);
                    $(this).next().removeClass('om-state-disabled');
                    this.calendar._buildEvent();
                });
            },
            
            /**
             * 获取控件选中的日期.
             * @name omCalendar#getDate
             * @function
             * @returns Date
             * @example
             * var date = $('input').omCalendar('getDate');
             */
            getDate : function(){
                var target = this[0];
                if (target && target.calendar) {
                    return target.calendar.selected;
                }
            }, 
           
            /**
             * 设置控件选中的日期.
             * @name omCalendar#setDate
             * @function
             * @param Date
             * @returns jQuery 对象
             * @example
             * $('input').omCalendar('setDate', new Date(2012, 0, 1));
             */
            setDate : function(d) {
                return this.each(function() {
                    if (this.calendar) {
                        this.calendar.render({selected : d, date : d});
                    }
                });
            }
        };
    
	$.fn.omCalendar = function(p) {
	    if (p && typeof(p) == 'string') {
            if (publicMethods[p]) {
                return publicMethods[p].apply(this, Array.prototype.slice.call(arguments, 1));
            }
            return null;
        }
        
        return this.each(function() {
            if (!docloaded) {
                $(this).hide();
                var t = this;
                $(document).ready(function() {
                    this.calendar = new Calendar($(this), p);
                });
            } else {
                this.calendar = new Calendar($(this), p);
            }
        });
	};
	
	$.calendar = $.calendar || {};
	
	$.extend($.calendar, {
        leftPad : function (val, size, ch) {
            var result = new String(val);
            if(!ch) {
                ch = " ";
            }
            while (result.length < size) {
                result = ch + result;
            }
            return result.toString();
        }
    });
	$.extend($.calendar, {
	    getShortDayName : function(day){
            return $.calendar.dayMaps[day][0];
        },
        getDayName : function (day) { 
            return $.calendar.dayMaps[day][1];
        },
	    
        getShortMonthName : function(month){
            return $.calendar.monthMaps[month][0];
	    },
	    getMonthName : function (month) { 
	        return $.calendar.monthMaps[month][1];
	    },
	    dayMaps : [
            ['Sun', 'Sunday'],
	        ['Mon', 'Monday'],
	        ['Tue', 'Tuesday'],
	        ['Wed', 'Wednesday'],
	        ['Thu', 'Thursday'],
	        ['Fri', 'Friday'],
	        ['Sat', 'Saturday']
	    ],
	    monthMaps : [
            ['Jan', 'January'],
            ['Feb', 'February'],
            ['Mar', 'March'],
            ['Apr', 'April'],
            ['May', 'May'],
            ['Jun', 'June'],
            ['Jul', 'July'],
            ['Aug', 'August'],
            ['Sep', 'September'],
            ['Oct', 'October'],
            ['Nov', 'November'],
            ['Dec', 'December']
        ],
        /**
         * g: getter method
         * s: setter method
         * r: regExp
         */
        formatCodes : {
	        //date
	        d: {
	            g: "this.getDate()", //date of month (no leading zero)
	            s: "this.setDate({param})",
	            r: "(0[1-9]|[1-2][0-9]|3[0-1]|[1-9])"
	        },
	        dd: {
	            g: "$.calendar.leftPad(this.getDate(), 2, '0')", //date of month (two digit)
	            s: "this.setDate(parseInt('{param}', 10))",
	            r: "(0[1-9]|[1-2][0-9]|3[0-1]|[1-9])"
	        },
	         
	        //month
	        m: {
	            g: "(this.getMonth() + 1)", // get month in one digits, no leading zero
	            s: "this.setMonth(parseInt('{param}', 10))",
	            r: "(0[1-9]|1[0-2]|[1-9])"
	        },
	        mm: { 
	            g: "$.calendar.leftPad(this.getMonth() + 1, 2, '0')",//two digits
	            s: "this.setMonth(parseInt('{param}', 10))",
	            r: "(0[1-9]|1[0-2]|[1-9])"
	        },
	        
	        //year
	        y: {
	            g: "('' + this.getFullYear()).substring(2, 4)", // get year in 2 digits
	            s: "this.setFullYear(parseInt('20{param}', 10))",
	            r: "(\\d{2})"
	        },
	        yy: {
	            g: "this.getFullYear()", // get year in 4 digits
	            s: "this.setFullYear(parseInt('{param}', 10))",
	            r: "(\\d{4})"
	        },
	        
	        //hour
	        h: {
	            g: "$.calendar.leftPad((this.getHours() % 12) ? this.getHours() % 12 : 12, 2, '0')",// 12 hours, two digits
	            s: "this.setHours(parseInt('{param}', 10))", // TODO,need to be fixed
	            r: "(0[0-9]|1[0-1])" // 00~11
	        },
            H: {
                g: "$.calendar.leftPad(this.getHours(), 2, '0')",   //24 hours, two digits
                s: "this.setHours(parseInt('{param}', 10))",
                r: "([0-1][0-9]|2[0-3])"   //00~23
            },
            g: {
                g: "((this.getHours() % 12) ? this.getHours() % 12 : 12)", // 12 hours, no leading 0
                s: "this.setHours(parseInt('{param}', 10))", // TODO,need to be fixed
                r: "([0-9]|1[0-1])" // 0, 1, 2, 3, ..., 11
            },
            G: {
                g: "this.getHours()",   // 24 hours, no leading 0
                s: "this.setHours(parseInt('{param}', 10))",
                r: "([0-9]|1[0-9]|2[0-3])" //0, 1, 2, 3, ..., 23
            },
            
            //minute
            i: {
                g: "$.calendar.leftPad(this.getMinutes(), 2, '0')", // get minute (two digits)
                s: "this.setMinutes(parseInt('{param}', 10))",
                r: "([0-5][0-9])" //00, 01, 02, ..., 59
            }, 
            
            //second
            s: {
                g: "$.calendar.leftPad(this.getSeconds(), 2, '0')", // get seconds (two digits)
                s: "this.setSeconds(parseInt('{param}', 10))",
                r: "([0-5][0-9])" //00, 01, 02, ..., 59
            },
            
            // millisecond
            u: {
                g: "$.calendar.leftPad(this.getMilliseconds(), 3, '0')",
                s: "this.setMilliseconds(parseInt('{param}', 10))",
                r: "(\\d{1,3})" //0, 1, 2, ..., 999
            },
            
            //localised names
            D: {
                g: "$.calendar.getShortDayName(this.getDay())", // get localised short day name
                s: "",
                r: ""
            },
            DD: {
                g: "$.calendar.getDayName(this.getDay())", // get localised long day name
                s: "",
                r: ""
            },
            
            M: {
                g: "$.calendar.getShortMonthName(this.getMonth())", // get localised short month name
                s: "",
                r: ""
            },
            MM: {
                g: "$.calendar.getMonthName(this.getMonth())", // get localised long month name
                s:"",
                r:""
            },
            
            //am & pm
            a: {
                g: "(this.getHours() < 12 ? 'am' : 'pm')", // am pm
                s: "",
                r: ""
            },
            A: {
                g: "(this.getHours() < 12 ? 'AM' : 'PM')", // AM PM
                s: "",
                r: ""
            }
        }
	});
	$.extend($.calendar, {
		
	    formatDate : function(date, formatter){
	        if (!date || !formatter) {
	            return null;
	        }
	        if (!(Object.prototype.toString.call(date) === '[object Date]')) {
	            return null;
	        }
	        var i, fi , result = '', literal = false;
	        for (i = 0; i < formatter.length ; i++ ) {
	            fi = formatter.charAt(i);
	            fi_next = formatter.charAt(i + 1);
	            if (fi == "'") {
	                literal = !literal;
	                continue;
	            }
	            if (!literal && $.calendar.formatCodes[fi + fi_next]) {
	                fi = new Function("return " + $.calendar.formatCodes[fi + fi_next].g).call(date);
	                i ++;
	            } else if (!literal && $.calendar.formatCodes[fi]) {
	                fi = new Function("return " + $.calendar.formatCodes[fi].g).call(date);
	            }
	            result += fi;
	        }
	        return result;
	        
	    },
	    parseDate : function(date_string, formatter){
	        if (!date_string || !formatter) {
	            return null;
	        }
	        if (!(Object.prototype.toString.call(date_string) === '[object String]')) {
	            return null;
	        }
	        var setterArr = [], i, fi, $fci = null, m_result;
	        for (i = 0 ; i < formatter.length; i ++) {
	            fi = formatter.charAt(i);
	            fi_next = formatter.charAt(i + 1);
	            if ($.calendar.formatCodes[fi + fi_next]) {
	                $fci = $.calendar.formatCodes[fi + fi_next];
                    i ++;
                } else if ($.calendar.formatCodes[fi]) {
                    $fci = $.calendar.formatCodes[fi];
                } else {
                    continue;
                }
	            m_result = date_string.match(new RegExp($fci.r));
	            if (!m_result) {
	                // your string and your formmatter is not matched!
	                return null;
	            }
	            setterArr.push($fci.s.replace('{param}', m_result[0]));
	            date_string = date_string.substring(m_result.index + m_result[0].length);
	            if (!(formatter.charAt(i + 1) == "" && date_string == "") && (formatter.charAt(i + 1) !== date_string[0])) {
	                // your string and your formmatter is not matched!
                    return null;
                }
	        }
	        var date = new Date();
	        new Function(setterArr.join(";")).call(date);
	        return date;
	    }
	});
	
})(jQuery);/*
 * operamasks-ui omCombo 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 */
;(function($) {
    var _valueKey='_value';
    
    // Array.prototype.indexOf is added in JavaScript v1.6.
    // IE8 only support JavaScript v1.3. So added it to make this component support IE.
    if(!Array.prototype.indexOf){
        Array.prototype.indexOf=function(item){
            var len=this.length;
            for(var i=0;i<len;i++){
                if(this[i]===item){
                    return i;
                }
            }
            return -1;
        };
    }
	/**
     * @name omCombo
     * @author 周发志
     * @class 下拉输入框组件。类似于html中的select，但是可以输入，可以过滤，可以使用远程数据。<br/><br/>
     * <b>特点：</b><br/>
     * <ol>
     *      <li>可以使用本地数据源，也可以使用远程数据源</li>
     *      <li>支持下拉框的缓加载（第一次显示时才初始化下拉框中的内容）</li>
     *      <li>用户可定制下拉框中数据的显示效果</li>
     *      <li>用户可定制选择后回填到输入框的文字</li>
     *      <li>用户可定制选择后组件的value值</li>
     *      <li>用户可定制下拉框的宽度和最大高度</li>
     *      <li>具有边输入边过滤的功能，也可定制过滤的算法</li>
     *      <li>提供丰富的事件</li>
     * </ol>
     * 
     * <b>示例：</b><br/>
     * <pre>
     * &lt;script type="text/javascript" >
     * $(document).ready(function() {
     *     $('#combo').omCombo({
     *         dataSource:[
     *                 {text:'Java',value:1},
     *                 {text:'JavaScript',value:2},
     *                 {text:'C',value:3},
     *                 {text:'PHP',value:4},
     *                 {text:'ASP',value:5}
     *         ],
     *         optionField:function(data,index){
     *             return '&lt;font color="red">'+index+'：&lt;/font>'+data.text+'('+data.value+')';
     *         },
     *         emptyText:'select one option!',
     *         value:1,
     *         editable:false,
     *         lazyLoad:true,
     *         listMaxHeight:40
     *     });
     * });
     * &lt;/script>
     * 
     * &lt;input id="combo"/>
     * </pre>
     * 
     * @constructor
     * @description 构造函数. 
     * @param p 标准config对象：{}
     */
	$.widget('om.omCombo', {
	    options: 
	       /** @lends omCombo#*/
	       {
                /**
                 * 将JSON对象中哪个字段作为option的text，可以指定为JSON的一个属性，也可以指定一个function来自己决定如何显示option的text。<br/><br/> 
                 * <ul><li>对于[{"text":"Java语言","value":"java"},{"text":"C语言","value":"c"},{"text":"C#语言","value":"c#"}]这样的对象,此属性可以不设置，将采用默认值'text'。</li>
                 * <li>对于[{"label":"Java语言","id":"java"},{"label":"C语言","id":"c"},{"label":"C#语言","id":"c#"}]这样的对象,此属性可以设置为'label'。</li>
                 * <li>对于[{"name":"深圳","abbreviation":"sz","code":"0755"},{"name":"武汉","abbreviation":"wh","code":"027"},{"name":"北京","abbreviation":"bj","code":"010"}]这样的对象,此属性可以设置为</li></ul>
                 * @type String or Function 
                 * @default 'text' 
                 * @example
                 * optionField:function(data,index){ 
                 *   return data.name+'('+ data.abbreviation+')'; 
                 * }  
                 * // 最后options分显示成"深圳(sz)、武汉(wh)、北京(bj)"这样的。
                 * // 当然也可以写成其它东西，如下面的代码可以实现options为左图片右文字的情况
                 * // return '&lt;img src="options.jpg" style="float:left"/>&lt;span style="float:right">' + data.value+'&lt;/span>'; 
                 */
                optionField : 'text',
                /**
                 * JSON对象中哪个字段作为option的value属性，可以指定为JSON的一个属性，也可以指定一个function来自己决定如何显示option的value<br/><br/>
                 * <ul>
                 *   <li>[{"text":"Java语言","value":"java"},{"text":"C语言","value":"c"},{"text":"C#语言","value":"c#"}] 将采用默认值'value'。</li>
                 *   <li>[{"label":"Java语言","id":"java"},{"label":"C语言","id":"c"},{"label":"C#语言","id":"c#"}] 此属性可以设置为'id'。</li>
                 *   <li>[{"name":"深圳","abbreviation":"sz","code":"0755"},{"name":"武汉","abbreviation":"wh","code":"027"},{"name":"北京","abbreviation":"bj","code":"010"}]此属性可以设置为'code'</li>
                 * </ul>
                 * @type String , Function
                 * @default 'value'
                 * @example
                 * 如果JSON数据为
                 * [
                 *   {"name":"深圳","abbreviation":"sz","code":"0755"},
                 *   {"name":"武汉","abbreviation":"wh","code":"027"}
                 * ]
                 * function(data,index){
                 *    return data.code+'(' + data.abbreviation+')';
                 * } 
                 *  最后各options的值分别是"0755(sz)、027(wh)、010(bj)"这样的。
                 *  当然也可以写成其它复杂的东西，如return data.code.substring(1); 
                 *  以实现将区号前面的0去掉作为option的value这样的功能。
                 */
                valueField : 'value',
                /**
                 * 组件宽度。可以使用一切可使用的东西，如'100px'、'10%'、'10pt'、'15em'、'auto'
                 * @type String
                 * @default 'auto'
                 */
                width : 'auto',
                /**
                 * 是否禁用该组件。如果禁用，则不可以输入，form提交时也将忽略这个输入框。
                 * @type Boolean
                 * @default false
                 */
                disabled : false,
                /**
                 * 组件是否只读。如果是只读，则不可以输入，不可以通过下拉框选择一个option，form提交时将包含这个输入框。
                 * @type Boolean
                 * @default false
                 */
                readOnly : false,
                /**
                 * 组件是否可以输入。设成false时不可以输入，但可以从下拉框里选择一个option。
                 * @type Boolean
                 * @default true
                 */
                editable : true,
                /**
                 * 是否延迟加载下拉框里的条目，设成true时页面显示时不加载下拉框里的条目，第一次展开下拉框时才加载
                 * @type Boolean
                 * @default false
                 */
                lazyLoad : false,
                /**
                 * 组件的下拉框的最大高度，设成'auto'时高度不固定，有多少条目就显示多高；设成50时表示下拉框最大高度50px，如果超过这个则显示垂直滚动条。<b>注意：由于浏览器的限制，这个属性的最小值是31，如果小于这个值时将看不到垂直滚动条。</b>
                 * @type Number
                 * @default 300
                 */
                listMaxHeight : 300,
                /**
                 * 组件的下拉框的宽度是自动扩展。设成false时下拉框的宽度将与输入框宽度保持一致；设成true时下拉框宽度将等于最宽的那个条目的宽度
                 * @type Boolean
                 * @default false
                 */
                listAutoWidth : false,
                /**
                 * 是否边输入边过滤下拉框。设成true时下拉框中将仅显示与输入框当前值匹配（匹配算法由filterStrategy决定）的条目。
                 * @type Boolean
                 * @default true
                 */
                autoFilter : true,
                /**
                 * 边输入边过滤下拉框时采用的过滤算法。<b>注意：仅当autoFilter不为false时该属性才有效果</b><br/>
                 * 默认值为'first'表示从左边匹配（相当于startWith），即下拉框的条目的label以输入框的值开头的才会显示。<br/>
                 * 设为'last'表示从右边匹配（相当于endWith），即下拉框的条目的label以输入框的值结尾的才会显示。<br/>
                 * 设为'anywhere'表示从任意位置匹配（相当于contains），即下拉框的条目的label只要出现过与输入框的值一样的都会显示。<br/>
                 * 也可以设为一个自定义function，该function返回true表示匹配成功，将会显示在下拉列表中，返回true则不显示。示例代码如下：
                 * @type String,Function
                 * @default 'first'
                 * @example
                 * 此属性可以设置为'first' 或 'last' 或 'anywhere' 或 
                 * function(text,record){ 
                 *      var reg=new RegExp(text); 
                 *      //只要当前记录的postCode属性或idCardNo属性中包含输入框的值就算匹配成功
                 *      return reg.test(record.postCode) || reg.test(record.idCradNo); 
                 * } 
                 */
                filterStrategy : 'first',
                /**
                 * 边输入边过滤下拉框情况下过滤的延迟时间（单位：毫秒）。如果设成300则表示在300毫秒内输入连续按键多次，则只进行最后一次按键的过滤。<b>注意：仅当autoFilter不为false时该属性才有效果</b>
                 * @type Number
                 * @default 500
                 */
                filterDelay : 500
                /**
                 * 数据源属性，可以设置为后台获取数据的URL，比如dataSource : '/operamasks-ui/getCountryNameServlet.json'
                 * 也可以设置为静态数据，数据必须为Json格式数组，比如dataSource : [{"value":"001","text":"张三"},{"value":"002","text":"李四"}]
                 * @name omCombo#dataSource
                 * @type Array[JSON]
                 * @default 无
                 * @example
                 * dataSource : '/operamasks-ui/getCountryNameServlet.json' 
                 * 或者
                 * dataSource : [{"value":"001","text":"张三"},{"value":"002","text":"李四"}]
                 */
                /**
                 * 当input框的值为空时，input框里出现提示消息。当input框得到焦点或者input框的值不为空时这个提示消息会自动消失
                 * @name omCombo#emptyText
                 * @default 无
                 * @type String
                 */
                /**
                 * combo组件的初始值，<b>如果设置了value属性的值则lazyLoad属性将会被强制转换为false</b>
                 * @name omCombo#value
                 * @default 无
                 * @type String
                 */
                /**
                 * 填充下拉框内容的function。设置此属性时表示用户要自己接管从records到下拉框的显示过程，用户拿到所有的records然后自己填充下拉框里的内容，最后返回一个JQuery元素集合，集合里的每个元素表示一个option，按上下键选择时将会在这个集合的元素间循环高亮。
                 * @name omCombo#listProvider
                 * @type Function
                 * @default 无
                 * @returns {jQuery Array} 应该返回一个jQuery数组，里面的每个元素表示下拉框里的一个option（如下示例中下拉框里是一个table，tabody中的每个tr表示一个option，所以返回container.find('tbody tr')）。
                 * @example
                 * listProvider:function(container,records){ 
                 *      $('&lt;table&gt;').appendTo(container);
                 *      records.each(function(){ 
                 *          $('&lt;tr&gt;&lt;td&gt;'+this.text+'&lt;td&gt;&lt;/tr&gt;').appendTo(container); 
                 *      }); 
                 *      $('&lt;/table>').appendTo(container);
                 *      return container.find('tbody tr'); //tbody中每个tr表示一个option，而thead中的tr表示表头，不是option
                 *  } 
                 */
                 /**
                 * 将JSON对象的哪个字段作为显示到input框的文字。可以指定为JSON的一个属性，也可以指定一个function来自己决定显示什么文字到input框。<b>注意：这里的内容在选择一个option后会直接显示在input框里，所以只能显示普通字符串，不能使用html</b>
                 * @name omCombo#inputField
                 * @type String or Function
                 * @default 'text'
                 * @example
                 * //以JSON对象的userName属性值作为显示到输入框的文字
                 * inputField:'userName'
                 * 
                 * //自定义一个Function来决定以什么作为显示到输入框的文字
                 * inputField:function(data,index){ 
                 *      return data.text+'('+data.value+')';
                 * } 
                 */
                 /**
                 * omCombo的输入框的内容发生变化时的回调函数。
                 * @event
                 * @param target 当前输入框对象
                 * @param newValue 选择的新值
                 * @param oldValue 原来的值
                 * @name omCombo#onValueChange
                 * @type Function
                 * @example
                 * onValueChange:function(target,newValue,oldValue){ 
                 *      //do something
                 *  } 
                 */
                 /**
                 * 以Ajax方式加载下拉列表中的内容出错时的回调函数。可以在这里进行一些处理，比如以人性化的方式提示用户。
                 * @event
                 * @param target 当前输入框对象
                 * @param url 取数的url
                 * @param errorThrown  错误对象
                 * @param request request对象
                 * @name omCombo#onError
                 * @type Function
                 * @example
                 * onError:function(target,url,errorThrown,request){ 
                 *      alert('An error occurred while load records from URL "'+url+'",the error message is:'+errorThrown.message);
                 *  } 
                 */
                 /**
                 * Ajax响应回来时执行的方法。
                 * @event
                 * @param text 输入框里当前文本
                 * @param data Ajax请求返回的数据
                 * @name omCombo#onSuccess
                 * @type Function
                 * @example
                 * onSuccess:function(text,data){
                 *     if(data.length==0){
                 *          $('#txt').omSuggestion('showMessage','没有数据！');
                 *     } 
                 * }
                 */
        },
        _init:function(){
            var options = this.options,
                inputEl = this.element,
                source = options.dataSource;
            if (options.width != 'auto') {
                var span = inputEl.parent().width(options.width);
                inputEl.width(span.innerWidth() - inputEl.next().outerWidth() - inputEl.outerWidth() + inputEl.width());
            }
            /*if (!options.listAutoWidth) {
                $.data(inputEl, 'dropList').width(inputEl.parent().width());
            }*/
            options.disabled ? inputEl.attr('disabled', true) : inputEl.removeAttr('disabled');
            (options.readOnly || !options.editable) ? inputEl.attr('readonly', 'readOnly') : inputEl.removeAttr('readonly');
            
            if (!options.lazyLoad) {
                //load data immediately
                this._toggleLoading(inputEl, 'add');
                if(source && typeof source == 'string'){
                    this._ajaxLoad(inputEl, source);
                }else if(source && typeof source == 'object'){
                    this._loadData(inputEl, source);
                    this._toggleLoading(inputEl, 'remove');
                }else{
                    //neither records nor remote url was found
                    this.dataHasLoaded = true;
                    this._toggleLoading(inputEl, 'remove');
                }
                
            } else {
                this.dataHasLoaded = false;
            }
            var unusable = options.disabled || options.readOnly;
            
            if (unusable) {
                $.data(inputEl, 'expandTrigger').addClass('om-state-disabled');
            } else {
                this._bindEvent();
            }
        },
        _create:function(){
            var options = this.options;
            if (!options.inputField) {
                options.inputField = options.optionField;
            }
            //由于在lazyLoad=false的情况下设置value时无法显示正确的fieldText
            if (typeof options.value !== 'undefined') {
                options.lazyLoad = false;
            }
            var inputEl = this.element;
            this._refeshEmptyText(options.emptyText, inputEl);
            inputEl.attr('autocomplete', 'off');
            var span = $('<span class="om-combo om-widget om-state-default"></span>').insertAfter(inputEl).wrapInner(inputEl);
            var trigger = $('<span class="om-combo-trigger om-icon om-icon-carat-1-s"></span>').appendTo(span);
            var dropList = $($('<div class="om-widget"><div class="om-widget-content om-droplist"></div></div>').css('position', 'absolute').appendTo(document.body).children()[0]).hide();
            $.data(inputEl, 'combo', this);
            $.data(inputEl, 'expandTrigger', trigger);
            $.data(inputEl, 'dropList', dropList);
        },
        /**
         * 重新加载下拉框里的数据，一般用于级联combo功能。
         * @name omCombo#setData
         * @function
         * @param arg records（JSON数组）或url
         * @returns {jQuery Object} jQuery对象
         * @example
         * //用一个固定的JSON数组来重新加载combo的下拉列表
         * $('#productCombo').omCombo('setData',[
         *      {"text":'Apusic Server',"value":"aas"},
         *      {"text":'Apusic OperaMasks SDK',"value":"aom"},
         *      {"text":'Apusic OperaMasks Studio',"value":"studio"}
         * ]);
         * 
         * //通过一个url来发送Ajax请求重新加载combo的下拉列表
         * $('#cityCombo').omCombo('setData',"../data/cityData.do?province="+$('#cityCombo').omCombo('value'));
         */
        setData:function(param){
            var self = this;
            var inputEl = self.element;
            self.options.value = '';
            self._toggleLoading(inputEl, 'add');
            if (typeof param === 'string') {
                self._ajaxLoad(inputEl, param);
            } else {
                self._loadData(inputEl, param);
                self._toggleLoading(inputEl, 'remove');
            }
        },
        /**
         * 获取combo的数据源,返回一个JSON数组.注意该数组和下拉项数组不是等同的,但存在一一对应的关系:前者经过格式化后能转变成后者.
         * @name omCombo#getData
         * @function
         * @param 无参数
         * @returns 如果combo中有数据,则返回combo的数据源(一个由所有记录组成的JSON数组);否则返回undefined
         * @example
         * //获取combo的数据源
         * var store = $('#productCombo').omCombo('getData');
         * 
         */
        getData:function(){
            //如果已经存在dataSource则直接取出
            var returnValue = this.options.dataSource;
            return (typeof returnValue == 'object') ? returnValue : null;
        },
        /**
         * 得到或设置combo的value值。
         * @name omCombo#value
         * @param v 设置的值，不设置表示获取值
         * @type Function
         * @returns 如果没有参数时表示getValue()返回combo的value值。如果有参数时表示setValue(newValue)返回jQuery对象
         */
         value:function(v){
             if (typeof v === 'undefined') {
                 var value = $(this.element).attr(_valueKey);
                 return value ? value : '';
             } else {
                 this._setValue(this.element, v);
                 return this;
             }
         },
        /**
         * 禁用组件。
         * @name omCombo#disable
         * @function
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('mycombo').omCombo('disable');
         */
        disable:function(){
            var input=this.element;
            //distroy event listening
            input.attr('disabled', true).unbind();
            $.data(input, 'expandTrigger').addClass('om-state-disabled').unbind();
        },
        /**
         * 启用组件。
         * @name omCombo#enable
         * @function
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('mycombo').omCombo('enable');
         */
        enable:function(){
            var input=this.element;
            input.removeAttr('disabled').unbind();
            $.data(input, 'expandTrigger').removeClass('om-state-disabled').unbind();
            //rebuild event listening
            this._bindEvent();
        },
        //private
        _bindEvent:function(){
            var self = this;
            var options = this.options;
            var input = this.element;
            var dropList = $.data(input, 'dropList');
            var expandTrigger = $.data(input, 'expandTrigger');
            var emptyText = options.emptyText;
            var isFocus = false;
            input.focus(function(){
                isFocus = true;
                $('.om-droplist').hide(); //hide all other dropLists
                input.parent('span').addClass('om-state-hover');
                //input.addClass('om-span-field-focus');
                //input.parent('span').
                //expandTrigger.addClass('om-state-hover');
                self._refeshEmptyText(emptyText, input);
                if (!self.dataHasLoaded) {
                    self._toggleLoading(input, 'add');
                    if (typeof(options.dataSource) == 'object') {
                        self._loadData(input, options.dataSource);
                        self._toggleLoading(input, 'remove');
                    } else if (typeof(options.dataSource) == 'string') {
                        self._ajaxLoad(input, options.dataSource);
                    } else {
                        //neither records nor remote url was found
                        self.dataHasLoaded = true;
                        self._toggleLoading(input, 'remove');
                    }
                }
                if (!options.disabled && !options.readOnly) {
                    self._showDropList(input);
                }
            }).blur(function(e){
                isFocus = false;
                input.parent('span').removeClass('om-state-hover');
                input.removeClass('om-combo-focus');
                //expandTrigger.removeClass('om-trigger-hover');
                if (!options.disabled && !options.readOnly) {
                    if (self.hasManualInput) {
                        //如果有手工输入过值，在blur时检查是否是合法的值，如果不是要清除不合法的输入并还原成输入前的值
                        self.hasManualInput = false;
                        var text = input.val();
                        if (text !== '') {
                            var allInputText = $.data(input, 'allInputText');
                            var allValues = $.data(input, 'allValues');
                            var index = allInputText.indexOf(text);
                            if (index > -1) {
                                self._setValue(input, allValues[index]);
                            } else {
                                var value = input.attr(_valueKey);
                                index = allValues.indexOf(value);
                                if (index > -1) {
                                    input.val(allInputText[index]);
                                } else {
                                    input.val('');
                                }
                            }
                        }
                    }
                    self._refeshEmptyText(emptyText, input);
                    $(document).click(function(){
                        dropList.hide();
                    });
                }
            }).keyup(function(e){
                var key = e.keyCode;
                switch (key) {
                    case 40: //down
                        self._selectNext();
                        break;
                    case 38: //up
                        self._selectPrev();
                        break;
                    case 13: //enter
                        self._backfill();
                        break;
                    case 27: //esc
                        dropList.hide();
                        break;
                    case 9: //tab
                        //only trigger the blur event
                        break;
                    default:
                        //fiter功能
                        self.hasManualInput = true;
                        if (!options.disabled && !options.readOnly && options.editable && options.autoFilter) {
                            if (window._omcomboFilterTimer) {
                                clearTimeout(window._omcomboFilterTimer);
                            }
                            window._omcomboFilterTimer = setTimeout(function(){
                                dropList.show();
                                self._doFilter(input);
                            }, options.filterDelay);
                        }
                }
            });
            
            expandTrigger.click(function(){
                !expandTrigger.hasClass('om-loading') && input.focus();
            }).hover(function(){
                !expandTrigger.hasClass('om-loading') && $(this).parent().addClass('om-state-hover');
            }, function(){
                !expandTrigger.hasClass('om-loading') && !isFocus && $(this).parent().removeClass('om-state-hover');
            }).mousedown(function(){
                !expandTrigger.hasClass('om-loading') && $(this).parent().addClass('om-state-active');
            }).mouseup(function(){
                !expandTrigger.hasClass('om-loading') && $(this).parent().removeClass('om-state-active');
            });
        },
        _showDropList:function(inputEl){
            var allItems = this._getAllOptionsBeforeFiltered(inputEl).removeClass('om-helper-hidden om-state-hover');
            if(allItems.size()<=0){ //如果下拉框没有数据
                return;
            }
            var options = this.options;
            var dropList = $.data(inputEl, 'dropList').scrollTop(0).css('height','auto');
            if (options.listMaxHeight != 'auto' && dropList.height() > options.listMaxHeight) {
                dropList.height(options.listMaxHeight).css('overflow','auto');
            }
            var valuedItem;
            var nowValue = inputEl.attr(_valueKey);
            if (nowValue !== '') {
                var allValues = $.data(inputEl, 'allValues');
                var index = allValues?allValues.indexOf(nowValue):-1;
                if (index > -1) {
                    valuedItem = $(dropList.find('.om-combo-list-row').get(index)).addClass('om-state-hover');
                }
            }
            var dropListContainer = dropList.parent(), span = inputEl.parent();
            if (!options.listAutoWidth) {
                dropListContainer.width(span.outerWidth());
            }
            var inputPos = span.offset();
            dropListContainer.css({
                'left': inputPos.left,
                'top': inputPos.top + span.outerHeight()
            });
            dropList.show();
            if (valuedItem) { //自动滚动滚动条到高亮的行
                dropList.scrollTop($(valuedItem).offset().top - dropList.offset().top);
            }
            $(document).unbind('click');
        },
        _toggleLoading:function(inputEl , type){
            if(!this.options.disabled){
                if (type == 'add') {
                    $.data(inputEl,'expandTrigger').removeClass('om-icon-carat-1-s').addClass('om-loading ');
                } else if (type == 'remove') {
                    $.data(inputEl,'expandTrigger').removeClass('om-loading').addClass('om-icon-carat-1-s');
                }
            }
        },
        _ajaxLoad:function(inputEl,url){
            var self=this;
            var text = inputEl.val();
            var options = this.options;
            $.ajax({
                url: url,
                method: 'POST',
                dataType: 'json',
                success: function(data){
                    var onSuccess = options.onSuccess;
                    if (onSuccess && onSuccess(text, data) === false) {
                        return;
                    }
                    self._loadData(inputEl, data);
                    self._toggleLoading(inputEl, 'remove');
                    self.dataHasLoaded = true;
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    var options = $.data(inputEl, 'combo').options;
                    self.dataHasLoaded = true; // 必须设置为true，否则在lazyLoad为true的时候会陷入死循环
                    if (options.onError) {
                        options.onError(inputEl, url, errorThrown, XMLHttpRequest);
                    } else {
                        self._toggleLoading(inputEl, 'remove');
                        throw new Error('An error occurred while load records from URL "' + url + '",the error message is:' + errorThrown.message);
                    }
                }
            });
        },
        _loadData:function(inputEl,records){
            var combo = $.data(inputEl, 'combo');
            var options = combo.options;
            options.dataSource = records;
            combo.dataHasLoaded = true;
            //build all inputText
            var inputField = options.inputField;
            var allInputText = [];
            if (typeof inputField === 'string') {
                $(records).each(function(){
                    allInputText.push(this[inputField]);
                });
            } else {
                $(records).each(function(index){
                    allInputText.push(inputField(this, index));
                });
            }
            $.data(inputEl, 'allInputText', allInputText);
            //build all value
            var valueField = options.valueField;
            var allValues = [];
            if (typeof valueField === 'string') {
                $(records).each(function(){
                    allValues.push(this[valueField]);
                });
            } else {
                $(records).each(function(index){
                    allValues.push(valueField(this, index));
                });
            }
            $.data(inputEl, 'allValues', allValues);
            //build dropList
            var dropList = $.data(inputEl, 'dropList').empty();
            if (options.listProvider) {
                var selectableOptions = options.listProvider(dropList, records);
                if (selectableOptions) {
                    selectableOptions.each(function(){
                        $(this).addClass('om-combo-list-row');
                    });
                }
            } else {
                var optionField = options.optionField;
                var innerHtml = '';
                var self = this;
                if (typeof optionField === 'string') {
                    $(records).each(function(index){
                        innerHtml += self._wrapText(this[options.optionField]);
                    });
                } else {
                    $(records).each(function(index){
                        innerHtml += self._wrapText(options.optionField(this, index));
                    });
                }
                if (innerHtml) {
                    $(innerHtml).appendTo(dropList);
                    dropList.show().css('height','auto');
                    if (options.listMaxHeight != 'auto' && dropList.height() > options.listMaxHeight) {
                        dropList.height(options.listMaxHeight).css('overflow','auto');
                    }
                    if(!inputEl.parent().hasClass('om-state-hover')){
                        dropList.hide();
                    }
                }
            }
           
            if (options.value) {
                this._setValue(inputEl, options.value);
            }
            this._refeshEmptyText(options.emptyText, inputEl);
            this._bindEventsToList(inputEl);
        },
        _bindEventsToList:function(inputEl){
            var items = this._getAllOptionsBeforeFiltered(inputEl);
            var self = this;
            items.hover(function(){
                items.removeClass('om-state-hover');
                $(this).addClass('om-state-hover');
            }, function(){
                $(this).removeClass('om-state-hover');
            }).click(function(){
                self._backfill();
            });
        },
        _wrapText:function(text) {
            return '<div class="om-combo-list-row">' + text + '</div>';
        },
        _setValue:function(inputEl,value){
            var input = $(inputEl);
            var oldValue = input.attr(_valueKey);
            if(value == oldValue){ //不需要处理，因为现在的值与将来的值是一样的
                return;
            }
            var allValues = $.data(inputEl, 'allValues');
            var index = allValues.indexOf(value);
            $.data(inputEl, 'dropList').find('.om-combo-list-row').removeClass('om-state-hover');
            if (index > -1) {
                var inputText = $.data(inputEl, 'allInputText')[index];
                input.attr(_valueKey, value).val(inputText);
            } else {
                input.attr(_valueKey, '').val('');
                value = '';
            }
            
            var options = $.data(inputEl, 'combo').options;
            options.value = value;
            // trigger onValueChange event
            if (options.onValueChange) {
                options.onValueChange(inputEl, value, oldValue);
            }
            //refresh the emptyText
            this._refeshEmptyText(options.emptyText, input);
        },
        _selectPrev:function(){
            var inputEl = this.element;
            var dropList = $.data(inputEl, 'dropList');
            var all = this._getAllOptionsAfterFiltered(inputEl);
            var nowIndex = all.index(dropList.find('.om-state-hover'));
            var currentItem = $(all[nowIndex]);
            if (nowIndex === 0) {
                nowIndex = all.length;
            } else if (nowIndex == -1) {
                nowIndex = all.length;
            }
            var preNeighborItem = $(all[nowIndex - 1]);
            this._highLisghtAndScrollTo(currentItem,preNeighborItem,dropList);
        },
        _selectNext:function(){
            var inputEl = this.element;
            var dropList = $.data(inputEl, 'dropList');
            if (dropList.css('display') == 'none') {
                this._showDropList(inputEl);
                return;
            }
            var all = this._getAllOptionsAfterFiltered(inputEl);
            var nowIndex = all.index(dropList.find('.om-state-hover'));
            var currentItem = $(all[nowIndex]);
            if (nowIndex == all.length - 1) {
                nowIndex = -1;
            }
            var nextNeighbor = $(all[nowIndex + 1]);
            this._highLisghtAndScrollTo(currentItem,nextNeighbor,dropList);
        },
        _highLisghtAndScrollTo: function(currentItem, targetItem, dropList){
            currentItem.removeClass('om-state-hover');
            targetItem.addClass('om-state-hover');
            if (targetItem.position().top <= 0) {
                dropList.scrollTop(dropList.scrollTop() + targetItem.position().top);
            } else if (targetItem.position().top + targetItem.outerHeight() > dropList.height()) {
                dropList.scrollTop(dropList.scrollTop() + targetItem.position().top + targetItem.outerHeight() - dropList.height());
            }
        },
        _backfill:function(){
            var inputEl = this.element;
            var dropList = $.data(inputEl, 'dropList');
            if (dropList.css('display') == 'none') {
                return;
            }
            var nowIndex = dropList.find('.om-state-hover').index();
            if (nowIndex > -1) {
                var value = $.data(inputEl, 'allValues')[nowIndex];
                this._setValue(inputEl, value);
            }
            dropList.hide();
        },
        _getAllOptionsBeforeFiltered:function(inputEl){
            var dropList=$.data(inputEl, 'dropList');
            return dropList.find('.om-combo-list-row');
        },
        _getAllOptionsAfterFiltered:function(inputEl){
            var dropList=$.data(inputEl, 'dropList');
            return dropList.find('.om-combo-list-row').not(dropList.find('.om-helper-hidden'));
        },
        _doFilter:function(){
            var inputEl = this.element;
            var options = $.data(inputEl, 'combo').options;
            var records = options.dataSource;
            var filterStrategy = options.filterStrategy;
            var text = inputEl.val();
            var items = this._getAllOptionsBeforeFiltered(inputEl);
            var allInputText = $.data(inputEl, 'allInputText');
            var self = this;
            var needShow=false;
            $(records).each(function(index){
                if (self._filetrPass(filterStrategy, text, records[index], allInputText[index])) {
                    $(items.get(index)).removeClass('om-helper-hidden');
                    needShow=true;
                } else {
                    $(items.get(index)).addClass('om-helper-hidden');
                }
            });
            var dropList = $.data(inputEl, 'dropList').css('height','auto');
            //过滤后重新计算下拉框的高度，看是否需要出现滚动条
            if (options.listMaxHeight != 'auto' && dropList.height() > options.listMaxHeight) {
                dropList.height(options.listMaxHeight).css('overflow','auto');
            }
            if(!needShow){
                dropList.hide();
            }
        },
        _filetrPass:function(filterStrategy,text,record,inputText){
            if (text === '') {
                return true;
            }
            if (typeof filterStrategy === 'function') {
                return filterStrategy(text, record);
            } else {
                if (filterStrategy === 'first') {
                    return inputText.indexOf(text) === 0;
                } else if (filterStrategy === 'anywhere') {
                    return inputText.indexOf(text) > -1;
                } else if (filterStrategy === 'last') {
                    var i = inputText.lastIndexOf(text);
                    return i > -1 && i + text.length == inputText.length;
                } else {
                    return false;
                }
            }
        },
        _refeshEmptyText: function(emptyText, inputEl){
            if (emptyText) {
                if (inputEl.val() === '') {
                    inputEl.val(emptyText).addClass('om-empty-text');
                } else {
                    inputEl.removeClass('om-empty-text');
                }
            } else {
                return;
            }
        }
	});
})(jQuery);/*
 * operamasks-ui omDialog 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 *  jquery.ui.button.js
 *  jquery.ui.draggable.js
 *  jquery.ui.mouse.js
 *  jquery.ui.position.js
 *  jquery.ui.resizable.js
 */
(function( $, undefined ) {

var uiDialogClasses =
		'om-dialog ' +
		'om-widget ' +
		'om-widget-content ' +
		'om-corner-all ',
	sizeRelatedOptions = {
		buttons: true,
		height: true,
		maxHeight: true,
		maxWidth: true,
		minHeight: true,
		minWidth: true,
		width: true
	},
	resizableRelatedOptions = {
		maxHeight: true,
		maxWidth: true,
		minHeight: true,
		minWidth: true
	},
	// support for jQuery 1.3.2 - handle common attrFn methods for dialog
	attrFn = $.attrFn || {
		val: true,
		css: true,
		html: true,
		text: true,
		data: true,
		width: true,
		height: true,
		offset: true,
		click: true
	};

	/**
     * @name omDialog
     * @author 林晓民
     * @class 对话框组件。可以放置html。<br/><br/>
     * <b>特点：</b><br/>
     * <ol>
     *      <li>方便地自定义按钮</li>
     * </ol>
     * 
     * <b>示例：</b><br/>
     * <pre>
     * &lt;script type="text/javascript" >
     * $(document).ready(function() {
     *     $('#dialog').omDialog({
     *     });
     * });
     * &lt;/script>
     * 
     * &lt;div id="dialog"/>
     * </pre>
     * 
     * @constructor
     * @description 构造函数. 
     * @param p 标准config对象：{}
     */
$.widget("om.omDialog", {
	options: /** @lends omDialog#*/ {
	    /**
         * 对话框创建完成后是否自动打开.
         * @type Boolean
         * @default true
         */
		autoOpen: true,
		
		/**
         * 指定组件下方的按钮。此配置项为json数组。每个json对象具有 <code>text</code> 属性(配置按钮文字)
         * 和 <code>click</code> 属性配置按钮触发时的回调方法。
         * @type Array
         * @default []
         */
		buttons: {},
		
		/**
		 * 用户按下 Esc 键时是否关闭对话框。
		 * @type Boolean
		 * @default true
		 */
		closeOnEscape: true,
		closeText: 'close',
		
		/**
		 * 对话框的样式
		 * @type String
		 * @default ''
		 */
		dialogClass: '',
		
		/**
		 * 组件是否可拖动
		 * @type Boolean
		 * @default true
		 */
		draggable: true,
		
		hide: null,
		
		/**
		 * 组件的高度
		 * @type Number
		 * @default 'auto'
		 */
		height: 'auto',
		
		/**
		 * 组件最大高度
		 * @type Number
		 * @default null
		 */
		maxHeight: false,
		
		/**
		 * 组件最大宽度
		 * @type Number
		 * @default null
		 */
		maxWidth: false,
		
		/**
		 * 组件最小高度
		 * @type Number
		 * @default 150
		 */
		minHeight: 150,
		
		/**
		 * 组件最小宽度
		 * @type Number
		 * @default 150
		 */
		minWidth: 150,
		
		/**
		 * 是否模态窗口
		 * @type Boolean
		 * @default false
		 */
		modal: false,
		position: {
			my: 'center',
			at: 'center',
			collision: 'fit',
			// ensure that the titlebar is never outside the document
			using: function(pos) {
				var topOffset = $(this).css(pos).offset().top;
				if (topOffset < 0) {
					$(this).css('top', pos.top - topOffset);
				}
			}
		},
		
		/**
		 * 是否可改变大小
		 * @type Boolean
		 * @default true
		 */
		resizable: true,
		show: null,
		
		stack: true,
		
		/**
		 * 对话框的标题
		 * @type String
		 * @default ''
		 */
		title: '',
		
		/**
		 * 组件的宽度
		 * @type Number
		 * @default 300
		 */
		width: 300,
		zIndex: 1000
		
		/**
         * 在对话框打开时触发
         * @event
         * @name omDialog#onOpen
         * @type Function
         * @default emptyFn
         * @example
         *   $("select").omDialog({onOpen : function() {doSomething...}});
         */
		
		/**
         * 在对话框关闭时触发
         * @event
         * @name omDialog#onClose
         * @type Function
         * @default emptyFn
         * @example
         *   $("select").omDialog({onClose : function() {doSomething...}});
         */
		
		/**
         * 在对话框关闭前触发
         * @event
         * @name omDialog#onBeforeClose
         * @type Function
         * @default emptyFn
         * @example
         *   $("select").omDialog({onBeforeClose : function() {doSomething...}});
         */
	},

	_create: function() {
		this.originalTitle = this.element.attr('title');
		// #5742 - .attr() might return a DOMElement
		if ( typeof this.originalTitle !== "string" ) {
			this.originalTitle = "";
		}

		this.options.title = this.options.title || this.originalTitle;
		var self = this,
			options = self.options,

			title = options.title || '&#160;',
			titleId = $.om.omDialog.getTitleId(self.element),

			uiDialog = (self.uiDialog = $('<div></div>'))
				.appendTo(document.body)
				.hide()
				.addClass(uiDialogClasses + options.dialogClass)
				.css({
					zIndex: options.zIndex
				})
				// setting tabIndex makes the div focusable
				// setting outline to 0 prevents a border on focus in Mozilla
				.attr('tabIndex', -1).css('outline', 0).keydown(function(event) {
					if (options.closeOnEscape && event.keyCode &&
						event.keyCode === $.ui.keyCode.ESCAPE) {
						
						self.close(event);
						event.preventDefault();
					}
				})
				.attr({
					role: 'dialog',
					'aria-labelledby': titleId
				})
				.mousedown(function(event) {
					self.moveToTop(false, event);
				}),

			uiDialogContent = self.element
				.show()
				.removeAttr('title')
				.addClass(
					'om-dialog-content ' +
					'om-widget-content')
				.appendTo(uiDialog),

			uiDialogTitlebar = (self.uiDialogTitlebar = $('<div></div>'))
				.addClass(
					'om-dialog-titlebar ' +
					'om-widget-header ' +
					'om-corner-all ' +
					'om-helper-clearfix'
				)
				.prependTo(uiDialog),

			uiDialogTitlebarClose = $('<a href="#"></a>')
				.addClass(
					'om-dialog-titlebar-close ' +
					'om-corner-all'
				)
				.attr('role', 'button')
				.hover(
					function() {
						uiDialogTitlebarClose.addClass('om-state-hover');
					},
					function() {
						uiDialogTitlebarClose.removeClass('om-state-hover');
					}
				)
				.focus(function() {
					uiDialogTitlebarClose.addClass('om-state-focus');
				})
				.blur(function() {
					uiDialogTitlebarClose.removeClass('om-state-focus');
				})
				.click(function(event) {
					self.close(event);
					return false;
				})
				.appendTo(uiDialogTitlebar),

			uiDialogTitlebarCloseText = (self.uiDialogTitlebarCloseText = $('<span></span>'))
				.addClass(
					'om-icon ' +
					'om-icon-closethick'
				)
				.text(options.closeText)
				.appendTo(uiDialogTitlebarClose),

			uiDialogTitle = $('<span></span>')
				.addClass('om-dialog-title')
				.attr('id', titleId)
				.html(title)
				.prependTo(uiDialogTitlebar);

		uiDialogTitlebar.find("*").add(uiDialogTitlebar).disableSelection();

		if (options.draggable && $.fn.draggable) {
			self._makeDraggable();
		}
		if (options.resizable && $.fn.resizable) {
			self._makeResizable();
		}

		self._createButtons(options.buttons);
		self._isOpen = false;

		if ($.fn.bgiframe) {
			uiDialog.bgiframe();
		}
	},

	_init: function() {
		if ( this.options.autoOpen ) {
			this.open();
		}
	},

	destroy: function() {
		var self = this;
		
		if (self.overlay) {
			self.overlay.destroy();
		}
		self.uiDialog.hide();
		self.element
			.unbind('.dialog')
			.removeData('dialog')
			.removeClass('om-dialog-content om-widget-content')
			.hide().appendTo('body');
		self.uiDialog.remove();

		if (self.originalTitle) {
			self.element.attr('title', self.originalTitle);
		}

		return self;
	},

	widget: function() {
		return this.uiDialog;
	},

	/**
     * 关闭对话框.
     * @name omDialog#close
     * @function
     * @returns 支持链式操作，返回JQuery对象
     * @example
     * var store = $('.selector').omDialog('close');
     * 
     */
	close: function(event) {
		var self = this,
			maxZ, thisZ;
		
		if (false === self._trigger('onBeforeClose', event)) {
			return;
		}

		if (self.overlay) {
			self.overlay.destroy();
		}
		self.uiDialog.unbind('keypress.om-dialog');

		self._isOpen = false;

		if (self.options.hide) {
			self.uiDialog.hide(self.options.hide, function() {
				self._trigger('onClose', event);
			});
		} else {
			self.uiDialog.hide();
			self._trigger('onClose', event);
		}

		$.om.omDialog.overlay.resize();

		// adjust the maxZ to allow other modal dialogs to continue to work (see #4309)
		if (self.options.modal) {
			maxZ = 0;
			$('.om-dialog').each(function() {
				if (this !== self.uiDialog[0]) {
					thisZ = $(this).css('z-index');
					if(!isNaN(thisZ)) {
						maxZ = Math.max(maxZ, thisZ);
					}
				}
			});
			$.om.omDialog.maxZ = maxZ;
		}

		return self;
	},

	/**
     * 判断对话框是否已打开
     * @name omDialog#isOpen
     * @function
     * @returns 如果对话框已打开返回true, 否则返回false
     * @example
     * var isOpen = $('.selector').omDialog('isOpen');
     * 
     */
	isOpen: function() {
		return this._isOpen;
	},

	// the force parameter allows us to move modal dialogs to their correct
	// position on open
	moveToTop: function(force, event) {
		var self = this,
			options = self.options,
			saveScroll;

		if ((options.modal && !force) ||
			(!options.stack && !options.modal)) {
			return self._trigger('onFocus', event);
		}

		if (options.zIndex > $.om.omDialog.maxZ) {
			$.om.omDialog.maxZ = options.zIndex;
		}
		if (self.overlay) {
			$.om.omDialog.maxZ += 1;
			self.overlay.$el.css('z-index', $.om.omDialog.overlay.maxZ = $.om.omDialog.maxZ);
		}

		//Save and then restore scroll since Opera 9.5+ resets when parent z-Index is changed.
		//  http://ui.jquery.com/bugs/ticket/3193
		saveScroll = { scrollTop: self.element.scrollTop(), scrollLeft: self.element.scrollLeft() };
		$.om.omDialog.maxZ += 1;
		self.uiDialog.css('z-index', $.om.omDialog.maxZ);
		self.element.attr(saveScroll);
		self._trigger('onFocus', event);

		return self;
	},

	/**
     * 打开对话框.
     * @name omDialog#open
     * @function
     * @returns 支持链式操作，返回JQuery对象
     * @example
     * var store = $('.selector').omDialog('open');
     * 
     */
	open: function() {
		if (this._isOpen) { return; }

		var self = this,
			options = self.options,
			uiDialog = self.uiDialog;

		self.overlay = options.modal ? new $.om.omDialog.overlay(self) : null;
		self._size();
		self._position(options.position);
		uiDialog.show(options.show);
		self.moveToTop(true);

		// prevent tabbing out of modal dialogs
		if (options.modal) {
			uiDialog.bind('keypress.om-dialog', function(event) {
				if (event.keyCode !== $.ui.keyCode.TAB) {
					return;
				}

				var tabbables = $(':tabbable', this),
					first = tabbables.filter(':first'),
					last  = tabbables.filter(':last');

				if (event.target === last[0] && !event.shiftKey) {
					first.focus(1);
					return false;
				} else if (event.target === first[0] && event.shiftKey) {
					last.focus(1);
					return false;
				}
			});
		}

		// set focus to the first tabbable element in the content area or the first button
		// if there are no tabbable elements, set focus on the dialog itself
		$(self.element.find(':tabbable').get().concat(
			uiDialog.find('.om-dialog-buttonpane :tabbable').get().concat(
				uiDialog.get()))).eq(0).focus();

		self._isOpen = true;
		self._trigger('onOpen');

		return self;
	},

	_createButtons: function(buttons) {
		var self = this,
			hasButtons = false,
			uiDialogButtonPane = $('<div></div>')
				.addClass(
					'om-dialog-buttonpane ' +
					'om-widget-content ' +
					'om-helper-clearfix'
				),
			uiButtonSet = $( "<div></div>" )
				.addClass( "om-dialog-buttonset" )
				.appendTo( uiDialogButtonPane );

		// if we already have a button pane, remove it
		self.uiDialog.find('.om-dialog-buttonpane').remove();

		if (typeof buttons === 'object' && buttons !== null) {
			$.each(buttons, function() {
				return !(hasButtons = true);
			});
		}
		if (hasButtons) {
			$.each(buttons, function(name, props) {
				props = $.isFunction( props ) ?
					{ click: props, text: name } :
					props;
				var button = $('<button type="button"></button>')
					.click(function() {
						props.click.apply(self.element[0], arguments);
					})
					.appendTo(uiButtonSet);
				// can't use .attr( props, true ) with jQuery 1.3.2.
				$.each( props, function( key, value ) {
					if ( key === "click" ) {
						return;
					}
					if ( key in attrFn ) {
						button[ key ]( value );
					} else {
						button.attr( key, value );
					}
				});
				if ($.fn.omButton) {
					button.omButton();
				}
			});
			uiDialogButtonPane.appendTo(self.uiDialog);
		}
	},

	_makeDraggable: function() {
		var self = this,
			options = self.options,
			doc = $(document),
			heightBeforeDrag;

		function filteredUi(ui) {
			return {
				position: ui.position,
				offset: ui.offset
			};
		}

		self.uiDialog.draggable({
			cancel: '.om-dialog-content, .om-dialog-titlebar-close',
			handle: '.om-dialog-titlebar',
			containment: 'document',
			start: function(event, ui) {
				heightBeforeDrag = options.height === "auto" ? "auto" : $(this).height();
				$(this).height($(this).height()).addClass("om-dialog-dragging");
				self._trigger('onDragStart', event, filteredUi(ui));
			},
			drag: function(event, ui) {
				self._trigger('onDrag', event, filteredUi(ui));
			},
			stop: function(event, ui) {
				options.position = [ui.position.left - doc.scrollLeft(),
					ui.position.top - doc.scrollTop()];
				$(this).removeClass("om-dialog-dragging").height(heightBeforeDrag);
				self._trigger('onDragStop', event, filteredUi(ui));
				$.om.omDialog.overlay.resize();
			}
		});
	},

	_makeResizable: function(handles) {
		handles = (handles === undefined ? this.options.resizable : handles);
		var self = this,
			options = self.options,
			// .ui-resizable has position: relative defined in the stylesheet
			// but dialogs have to use absolute or fixed positioning
			position = self.uiDialog.css('position'),
			resizeHandles = (typeof handles === 'string' ?
				handles	:
				'n,e,s,w,se,sw,ne,nw'
			);

		function filteredUi(ui) {
			return {
				originalPosition: ui.originalPosition,
				originalSize: ui.originalSize,
				position: ui.position,
				size: ui.size
			};
		}

		self.uiDialog.resizable({
			cancel: '.om-dialog-content',
			containment: 'document',
			alsoResize: self.element,
			maxWidth: options.maxWidth,
			maxHeight: options.maxHeight,
			minWidth: options.minWidth,
			minHeight: self._minHeight(),
			handles: resizeHandles,
			start: function(event, ui) {
				$(this).addClass("om-dialog-resizing");
				self._trigger('onResizeStart', event, filteredUi(ui));
			},
			resize: function(event, ui) {
				self._trigger('onResize', event, filteredUi(ui));
			},
			stop: function(event, ui) {
				$(this).removeClass("om-dialog-resizing");
				options.height = $(this).height();
				options.width = $(this).width();
				self._trigger('onResizeStop', event, filteredUi(ui));
				$.om.omDialog.overlay.resize();
			}
		})
		.css('position', position)
		.find('.om-resizable-se').addClass('om-icon om-icon-grip-diagonal-se');
	},

	_minHeight: function() {
		var options = this.options;

		if (options.height === 'auto') {
			return options.minHeight;
		} else {
			return Math.min(options.minHeight, options.height);
		}
	},

	_position: function(position) {
		var myAt = [],
			offset = [0, 0],
			isVisible;

		if (position) {
			// deep extending converts arrays to objects in jQuery <= 1.3.2 :-(
	//		if (typeof position == 'string' || $.isArray(position)) {
	//			myAt = $.isArray(position) ? position : position.split(' ');

			if (typeof position === 'string' || (typeof position === 'object' && '0' in position)) {
				myAt = position.split ? position.split(' ') : [position[0], position[1]];
				if (myAt.length === 1) {
					myAt[1] = myAt[0];
				}

				$.each(['left', 'top'], function(i, offsetPosition) {
					if (+myAt[i] === myAt[i]) {
						offset[i] = myAt[i];
						myAt[i] = offsetPosition;
					}
				});

				position = {
					my: myAt.join(" "),
					at: myAt.join(" "),
					offset: offset.join(" ")
				};
			} 

			position = $.extend({}, $.om.omDialog.prototype.options.position, position);
		} else {
			position = $.om.omDialog.prototype.options.position;
		}

		// need to show the dialog to get the actual offset in the position plugin
		isVisible = this.uiDialog.is(':visible');
		if (!isVisible) {
			this.uiDialog.show();
		}
		this.uiDialog
			// workaround for jQuery bug #5781 http://dev.jquery.com/ticket/5781
			.css({ top: 0, left: 0 })
			.position($.extend({ of: window }, position));
		if (!isVisible) {
			this.uiDialog.hide();
		}
	},

	_setOptions: function( options ) {
		var self = this,
			resizableOptions = {},
			resize = false;

		$.each( options, function( key, value ) {
			self._setOption( key, value );
			
			if ( key in sizeRelatedOptions ) {
				resize = true;
			}
			if ( key in resizableRelatedOptions ) {
				resizableOptions[ key ] = value;
			}
		});

		if ( resize ) {
			this._size();
		}
		if ( this.uiDialog.is( ":data(resizable)" ) ) {
			this.uiDialog.resizable( "option", resizableOptions );
		}
	},

	_setOption: function(key, value){
		var self = this,
			uiDialog = self.uiDialog;

		switch (key) {
			case "buttons":
				self._createButtons(value);
				break;
			case "closeText":
				// ensure that we always pass a string
				self.uiDialogTitlebarCloseText.text("" + value);
				break;
			case "dialogClass":
				uiDialog
					.removeClass(self.options.dialogClass)
					.addClass(uiDialogClasses + value);
				break;
			case "disabled":
				if (value) {
					uiDialog.addClass('om-dialog-disabled');
				} else {
					uiDialog.removeClass('om-dialog-disabled');
				}
				break;
			case "draggable":
				var isDraggable = uiDialog.is( ":data(draggable)" );
				if ( isDraggable && !value ) {
					uiDialog.draggable( "destroy" );
				}
				
				if ( !isDraggable && value ) {
					self._makeDraggable();
				}
				break;
			case "position":
				self._position(value);
				break;
			case "resizable":
				// currently resizable, becoming non-resizable
				var isResizable = uiDialog.is( ":data(resizable)" );
				if (isResizable && !value) {
					uiDialog.resizable('destroy');
				}

				// currently resizable, changing handles
				if (isResizable && typeof value === 'string') {
					uiDialog.resizable('option', 'handles', value);
				}

				// currently non-resizable, becoming resizable
				if (!isResizable && value !== false) {
					self._makeResizable(value);
				}
				break;
			case "title":
				// convert whatever was passed in o a string, for html() to not throw up
				$(".om-dialog-title", self.uiDialogTitlebar).html("" + (value || '&#160;'));
				break;
		}

		$.Widget.prototype._setOption.apply(self, arguments);
	},

	_size: function() {
		/* If the user has resized the dialog, the .ui-dialog and .ui-dialog-content
		 * divs will both have width and height set, so we need to reset them
		 */
		var options = this.options,
			nonContentHeight,
			minContentHeight,
			isVisible = this.uiDialog.is( ":visible" );

		// reset content sizing
		this.element.show().css({
			width: 'auto',
			minHeight: 0,
			height: 0
		});

		if (options.minWidth > options.width) {
			options.width = options.minWidth;
		}

		// reset wrapper sizing
		// determine the height of all the non-content elements
		nonContentHeight = this.uiDialog.css({
				height: 'auto',
				width: options.width
			})
			.height();
		minContentHeight = Math.max( 0, options.minHeight - nonContentHeight );
		
		if ( options.height === "auto" ) {
			// only needed for IE6 support
			if ( $.support.minHeight ) {
				this.element.css({
					minHeight: minContentHeight,
					height: "auto"
				});
			} else {
				this.uiDialog.show();
				var autoHeight = this.element.css( "height", "auto" ).height();
				if ( !isVisible ) {
					this.uiDialog.hide();
				}
				this.element.height( Math.max( autoHeight, minContentHeight ) );
			}
		} else {
			this.element.height( Math.max( options.height - nonContentHeight, 0 ) );
		}

		if (this.uiDialog.is(':data(resizable)')) {
			this.uiDialog.resizable('option', 'minHeight', this._minHeight());
		}
	}
});

$.extend($.om.omDialog, {
	version: "0.0.7",

	uuid: 0,
	maxZ: 0,

	getTitleId: function($el) {
		var id = $el.attr('id');
		if (!id) {
			this.uuid += 1;
			id = this.uuid;
		}
		return 'ui-dialog-title-' + id;
	},

	overlay: function(dialog) {
		this.$el = $.om.omDialog.overlay.create(dialog);
	}
});

$.extend($.om.omDialog.overlay, {
	instances: [],
	// reuse old instances due to IE memory leak with alpha transparency (see #5185)
	oldInstances: [],
	maxZ: 0,
	events: $.map('focus,mousedown,mouseup,keydown,keypress,click'.split(','),
		function(event) { return event + '.dialog-overlay'; }).join(' '),
	create: function(dialog) {
		if (this.instances.length === 0) {
			// prevent use of anchors and inputs
			// we use a setTimeout in case the overlay is created from an
			// event that we're going to be cancelling (see #2804)
			setTimeout(function() {
				// handle $(el).dialog().dialog('close') (see #4065)
				if ($.om.omDialog.overlay.instances.length) {
					$(document).bind($.om.omDialog.overlay.events, function(event) {
						// stop events if the z-index of the target is < the z-index of the overlay
						// we cannot return true when we don't want to cancel the event (#3523)
						if ($(event.target).zIndex() < $.om.omDialog.overlay.maxZ) {
							return false;
						}
					});
				}
			}, 1);

			// allow closing by pressing the escape key
			$(document).bind('keydown.dialog-overlay', function(event) {
				if (dialog.options.closeOnEscape && event.keyCode &&
					event.keyCode === $.ui.keyCode.ESCAPE) {
					
					dialog.close(event);
					event.preventDefault();
				}
			});

			// handle window resize
			$(window).bind('resize.dialog-overlay', $.om.omDialog.overlay.resize);
		}

		var $el = (this.oldInstances.pop() || $('<div></div>').addClass('om-widget-overlay'))
			.appendTo(document.body)
			.css({
				width: this.width(),
				height: this.height()
			});

		if ($.fn.bgiframe) {
			$el.bgiframe();
		}

		this.instances.push($el);
		return $el;
	},

	destroy: function($el) {
		var indexOf = $.inArray($el, this.instances);
		if (indexOf != -1){
			this.oldInstances.push(this.instances.splice(indexOf, 1)[0]);
		}

		if (this.instances.length === 0) {
			$([document, window]).unbind('.dialog-overlay');
		}

		$el.remove();
		
		// adjust the maxZ to allow other modal dialogs to continue to work (see #4309)
		var maxZ = 0;
		$.each(this.instances, function() {
			maxZ = Math.max(maxZ, this.css('z-index'));
		});
		this.maxZ = maxZ;
	},

	height: function() {
		var scrollHeight,
			offsetHeight;
		// handle IE 6
		if ($.browser.msie && $.browser.version < 7) {
			scrollHeight = Math.max(
				document.documentElement.scrollHeight,
				document.body.scrollHeight
			);
			offsetHeight = Math.max(
				document.documentElement.offsetHeight,
				document.body.offsetHeight
			);

			if (scrollHeight < offsetHeight) {
				return $(window).height() + 'px';
			} else {
				return scrollHeight + 'px';
			}
		// handle "good" browsers
		} else {
			return $(document).height() + 'px';
		}
	},

	width: function() {
		var scrollWidth,
			offsetWidth;
		// handle IE
		if ( $.browser.msie ) {
			scrollWidth = Math.max(
				document.documentElement.scrollWidth,
				document.body.scrollWidth
			);
			offsetWidth = Math.max(
				document.documentElement.offsetWidth,
				document.body.offsetWidth
			);

			if (scrollWidth < offsetWidth) {
				return $(window).width() + 'px';
			} else {
				return scrollWidth + 'px';
			}
		// handle "good" browsers
		} else {
			return $(document).width() + 'px';
		}
	},

	resize: function() {
		/* If the dialog is draggable and the user drags it past the
		 * right edge of the window, the document becomes wider so we
		 * need to stretch the overlay. If the user then drags the
		 * dialog back to the left, the document will become narrower,
		 * so we need to shrink the overlay to the appropriate size.
		 * This is handled by shrinking the overlay before setting it
		 * to the full document size.
		 */
		var $overlays = $([]);
		$.each($.om.omDialog.overlay.instances, function() {
			$overlays = $overlays.add(this);
		});

		$overlays.css({
			width: 0,
			height: 0
		}).css({
			width: $.om.omDialog.overlay.width(),
			height: $.om.omDialog.overlay.height()
		});
	}
});

$.extend($.om.omDialog.overlay.prototype, {
	destroy: function() {
		$.om.omDialog.overlay.destroy(this.$el);
	}
});

}(jQuery));
/*
 * operamasks-ui omGrid 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 */
/*
 * 在原有基础上扩展功能:
 * 1.checkbox列
 * 2.index列
 * 3.列允许指定renderer函数
 * 4.允许通过调用xx[0].grid._resizeTo(w,h)来动态的改变大小
 * 5.分页条显示快速定位链接(只显示5个)
 * 6.limit允许使用'auto',根据容器高度来决定显示的行数
 * 7.添加fillEmptyRows属性,如果当页数据没填满容器高度,继续填充空白行
 * 8.添加onRowClick, onRowDblClick事件
 * 9.添加onClientSort客户端排序功能,并允许指定自定义的onClientSort函数
 * 修复原有的bug:
 * 1.有两处p.colMolel拼写错误导致无法自定义列axis
 * 2.ctrl多选失效
 * 3.高度计算错误
 */
/**
 * @name omGrid
 * @author 陈界，罗业刚
 * @class 富客户端表格组件，轻量级，支持列移动及列大小改变，支持ajax方式装载xml格式的数据.<br/>
 * 客户端从服务器端取到数据后即将其展示在表格中，服务器返回的数据格式一般用标准json格式，<br/><br/>
 * <b>数据格式:</b><br/>
 * 如下示例展示了服务器传到前台的数据格式<br/>
 * <pre>
 * {"total":1184, "page":1, "rows":
 *     [
 *         {"address":"CZ88.NET ","city":"IANA保留地址","end":"0.255.255.255","id":"1","start":"0.0.0.0"},
 *         {"address":"CZ88.NET ","city":"澳大利亚","end":"1.0.0.255","id":"2","start":"1.0.0.0"},
 *         {"address":"电信 ","city":"福建省","end":"1.0.3.255","id":"3","start":"1.0.1.0"},
 *         {"address":"CZ88.NET ","city":"泰国","end":"1.0.255.255","id":"9","start":"1.0.128.0"},
 *         {"address":"CZ88.NET ","city":"日本","end":"1.0.31.255","id":"6","start":"1.0.16.0"},
 *         {"address":"电信 ","city":"广东省","end":"1.0.63.255","id":"7","start":"1.0.32.0"},
 *         {"address":"CZ88.NET ","city":"澳大利亚","end":"1.0.7.255","id":"4","start":"1.0.4.0"},
 *         {"address":"CZ88.NET ","city":"日本","end":"1.0.127.255","id":"8","start":"1.0.64.0"}
 *     ]
 * }
 * </pre>
 * 在每一次返回的数据中，如下几项是必须存在的，<br/>
 * 1. total:当前总共有多少条记录<br/>
 * 2. page:当前在第几页<br/>
 * 3. rows:当前页的数据，为一个对象字面量数组，每个元素中的字段都与定义表格式的列模型colModel中的name属性一一对应，这样客户端拿到数据后才知道如何进行填充.<br/><br/>
 * <b>两种工作模式:</b><br/>
 * 
 *  <li> 精确计算总数模式</li>
 *  	<p>&nbsp;&nbsp;omGrid大部分情况工作在该模式下面，第一次加载数据就马上可以得到totalCount总数信息，立即构造出表格分页器并存在一个页面跳转输入框。</p>
 *  <li> 延迟计算总数模式</li> 
 *  	<p>&nbsp;&nbsp;有些情况下，表格数据可能通过几个不同的系统组合而成，或者数据量非常巨大，计算总数可能需要较长的时间，此时omGrid可提供先展示前几页数据，后取总数的能力。保证了较好的用户体验。
 *  	做到该功能只需要增加一个配置即可：lazyTotalUrl ,该配置属性告诉omGrid，当页面加载完成后，接下来需要根据该url发送一个ajax请求的服务器，
 *  	该请求的响应将被作为总数写在omGridd分页条上，显然这种模式下的分页条跟之前那个模式下的分页条是不一样的。
 *  	通常会赋给它一个跟原先的那个求数url不一样的值，服务器另起一个专门计算总数的线程应该比较好控制。<p>
 * <b>特点：</b><br/>
 * 
 * 	1. 可配置延迟计算总数模式<br/>
 *  2. 可配置checkbox列<br/>
 *  3. 可配置index列<br/>
 *  4. 列渲染<br/>
 *  5. 自定义客户端排序函数<br/>
 *  6. ajax取数<br/>
 *  7. 分页功能<br/>
 *  8. 列可隐藏<br/>
 *  
 * 
 * @constructor
 * @description 构造函数. 
 * @param p 标准config对象：{width:300, height:300}
 * @example
 * $('griddiv').omGrid({width:300, height:300});
 */
(function($) {
	$.addFlex = function(t, p) {
		if (t.grid){
			return false; // return if already exist
		}
		/**
		 * @lends omGrid#
		 */
		p = $.extend({ // apply default properties
			/**
			 * 表格高度，单位为px，可取值为'auto'，表格高度根据每行的高度自动计算.
			 * @default 200
		     * @type Number
		     * @example
		     * $('.selector').omGrid({height : 300});
		     */
			height : 200, // 表格的高度
			/**
			 * 表格宽度，单位为px，可取值为'auto'，表格宽度根据每列的宽度自动计算.
		     * @type Number
		     * @default 'auto'
		     * @example
		     * $('.selector').omGrid({width : 600});
		     */
			width : 'auto', // 表格宽度
			/**
			 * 是否显示斑纹效果(即奇数行和偶数行使用不同的背景色).
		     * @type Boolean
		     * @default true
		     * @example
		     * $('.selector').omGrid({striped : true});
		     */
			striped : true, // 是否显示斑纹效果(即奇数行和偶数行使用不同的背景色)
			/**
			 * 是否显示表格竖分隔条.
		     * @type Boolean
		     * @default false
		     * @example
		     * $('.selector').omGrid({noVStripe : false});
		     */
			noVStripe : false,// 表格竖分隔条
			/**
			 * 列的最小宽度，定义在colModel对应的列上面
		     * @type Number
		     * @default 30
		     * @example
		     * $('.selector').omGrid({minWidth : 30});
		     */
			minWidth : 30, // 最少列宽
			/**
			 * 默认列宽.
		     * @type Number
		     * @default 60
		     * @example
		     * $('.selector').omGrid({defaultColWidth : 60});
		     */
			defaultColWidth : 60,// 如果不指定某个列的宽度,其列宽会被设置成此值
			/**
			 * 列的最小高度.
		     * @type Number 
		     * @default 80
		     * @example
             * $('.selector').omGrid({minHeight : 80});
		     */
			minHeight : 80, // 当表格resize时，表格的最小高度
			/**
			 * 是否要长出调整大小的handle.
		     * @type Boolean 
		     * @default false
		     * @deprecate
		     * @example
             * $('.selector').omGrid({resizable : false});
		     */
			resizable : false, // 是否要长出调整大小的handle,不建议使用
			/**
			 * ajax取数方式对应的url地址.
		     * @type String
		     * @default 无
		     * @example
		     * //下面的示例设置的url，表示将请求服务器的griddata.do这个action，同时附带有请求参数。
		     * //该action最终必须返回一段具有特定格式的json数据，omGrid拿到该数据即可用来填充表格。
		     * //json数据格式可参考文档的Overview页签。
		     * $('.selector').omGrid({url:'griddata.do?method=fast'});
		     */
			url : false, // 取数的url
			/**
			 * 数据发送方式，取值为：'POST'，'GET'.
		     * @type String
		     * @default 'POST'
		     * @example
             * $('.selector').omGrid({method : 'POST'});
		     */
			method : 'POST', // data sending method
			/**
			 * 是否显示分页工具条
		     * @type Boolean
		     * @default true
		     * @example
		     * //通过下面的配置,会在表格的下方长出分页工具条
		     * $('.selector').omGrid({paged : true});
		     */
			paged : true,// 是否显示分页工具条
			
			/**
			 * 当omGrid工作在lazy模式时,则必须指定lazyTotalUrl,用来请求大约条数,以便计算分页信息(注意该url只会被请求一次).在这一次请求，该请求必需返回一个json格式为{"total":5}的数据。
		     * @type String
		     * @default false
		     * @example
		     * //当表格第一页加载完成，omGrid会根据该url去发送一个ajax请求到服务器，去请求totalCount；请求的响应将被作为总数写在分页条上。
		     * $('.selector').omGrid({lazyTotalUrl:'griddata.do?method=lazyTotal'});
		     */
			lazyTotalUrl : false,// 当omGrid工作在lazy模式时,则必须指定lazyTotalUrl,用来请求大约条数,以便计算分页信息(注意该url只会被请求一次)
			/**
			 * 是否不允许文字自动换行，如果设置为true，文字超出列宽度部分将隐藏，如果未false，将换行显示出所有数据
		     * @type Boolean 
		     * @default true
		     * @example
             * $('.selector').omGrid({noWrap : true});
		     */
			noWrap : true,// 是否不允许文字自动换行
			/**
			 * 默认当前页.
		     * @type Number
		     * @default 1
		     * @example
             * $('.selector').omGrid({page : 1});
		     */
			page : 1, // current page
			/**
			 * 总页数.
		     * @type Number
		     * @default 1
		     * @example
             * $('.selector').omGrid({total : 1});
		     */
			total : 1, // total pages
			/**
			 * 是否显示一个可控制每页显示条数的选择器，该属性在limit='auto'时失效.
		     * @type Boolean
		     * @default false
		     * @example
             * $('.selector').omGrid({useRp : false});
		     */
			useRp : false, // 是否显示一个可控制每页显示条数的select,该属性在limit='auto'时失效
			/**
			 * 每页数据条数，可以指定为auto来根据容器高度自动计算数据量，但为auto的时候则行内不允许换行.
		     * @type Number
		     * @default 15
		     * @example
             * $('.selector').omGrid({limit : 15});
		     */
			limit : 15, // 每页数据条数,可以指定为auto来根据容器高度自动计算数据量,但为auto的时候则行内不允许换行
			/**
			 * 允许每页显示的结果数.当limit为非auto时，该属性才起作用
		     * @type Array[Number]
		     * @default [10, 15, 20, 30, 50]
		     * @example
             * $('.selector').omGrid({rpOptions : [ 10, 15, 20, 30, 50 ]});
		     */
			rpOptions : [ 10, 15, 20, 30, 50 ], // allowed per-page values
			/**
			 * 是否包含标题.
		     * @type Boolean
		     * @default false
		     * @example
             * $('.selector').omGrid({title : false});
		     */
			title : false,

			/**
			 * 当前页和总页数信息.
		     * @type String
		     * @default '共 {total} 条数据，显示第 {from} 条到 {to} 条'
		     * @example
             * $('.selector').omGrid({pageStat : '共 {total} 条数据，显示第 {from} 条到 {to} 条'});
		     */
			pageStat : (p.lazyTotalUrl?'大约 ':'共 ') + '{total} 条数据，显示第 {from} 条到 {to} 条',
			/**
			 * 无数据时的提示信息.
		     * @type String
		     * @default '没有数据'
		     * @example
             * $('.selector').omGrid({emptyMsg : '没有数据'});
		     */
			emptyMsg : '没有数据',
	
			/**
			 * 连接失败的提示信息.
		     * @type String
		     * @default '连接失败'
		     * @example
             * $('.selector').omGrid({errorMsg : '连接失败'});
		     */
			errorMsg : '连接失败',
			/**
			 * 查询的条件.每一次与服务器的交互都会带上两个参数(query,qtype),当用户需要有数据要传递给服务器的时候该属性会有用处.<br/>
             * 一般用于查询表格
             * @blocked
             * @type String
             * @default 无
             * @example
             * $('.selector').omGrid({query : ''});
		     */
			query : '',
			/**
			 * 查询的类型.每一次与服务器的交互都会带上两个参数(query,qtype),当用户需要有数据要传递给服务器的时候该属性会有用处.<br/>
			 * 一般用于查询表格
			 * @blocked
			 * @type String
             * @default 无
             * @example
             * $('.selector').omGrid({qtype : ''});
		     */
			qtype : '',
			/**
			 * 至少需要显示列数目，当设置为2的时候，如果只有两列，将不能再执行隐藏列操作
		     * @type Number
		     * @default 1
		     * @example
             * $('.selector').omGrid({minColToggle : 1});
		     */
			minColToggle : 1, // minimum allowed column to be hidden
			/**
             * 装载数据时显示的文本.
             * @type String
             * @default '正在加载数据，请稍候...'
             * @example
             * $('.selector').omGrid({loadingMsg : '正在加载数据，请稍候...'});
             */
            loadingMsg : '正在加载数据，请稍候...',
            /**
             * 分页文本.
             * @type String
             * @default '共 {totalPage} 页，跳转至第 {index} 页'
             * @example
             * $('.selector').omGrid({pageText : '共 {totalPage} 页，跳转至第 {index} 页'});
             */
            pageText : '共 {totalPage} 页，跳转至第 {index} 页',
			
			/**
			 * 是否显示列隐藏按钮.
		     * @type Boolean
		     * @default true
		     * @example
             * $('.selector').omGrid({showToggleBtn : true});
		     */
			showToggleBtn : true, // show or hide column toggle popup
			/**
			 * 一个数组,指定有哪些按钮.toolbar的可替代属性，buttons属性可提供默认样式，toolbar则完全自定制
		     * @type Array[JSON]
		     * @default false
		     * @example
		     * var btns = [ {
			 *		name : '删除',
			 *		bclass : 'deleteClass', // button样式
			 *		imgclass : 'deleteImg',  // 图片样式
			 *		click:function(e){}, //响应事件
			 *	}, {
			 *		name : '新建',
			 *		bclass : 'createClass',
			 *		imgclass : 'createImg'
			 *	}, {
			 *		name : '下载',
			 *		bclass : 'downloadClass',
			 *		imgclass : 'downloadImg'
			 *	} ]
			 *
			 * $(".selector").omGrid({
			 * 		buttons : btns
			 * });
		     */
			buttons : false,// 一个数组,指定有哪些按钮
			/**
			 * 某个div的id，可以被jQuery选择到具体DOM节点，将被剪切到grid作为工具条。buttons的可替代属性。
			 * @type String
			 * @default 无
			 * @example
			 * $(".selector").omGrid({
			 * 		toolbar : "divId"
			 * });
			 *  
			 * &lt;div id="divId"&gt;
			 * 	   &lt;button id="delete"&gt;删除&lt;/button&gt;
			 * 	   &lt;button id="create"&gt;新增&lt;/button&gt;
			 * 	   &lt;input id="queryText" &gt;&lt;/input&gt;
			 *     &lt;button id="query"&gt;查询&lt;/button&gt;
			 * &lt;/div&gt;
			 * 
			 */
			toolbar : false,// 某个div的id，可以被jQuery选择到具体DOM节点，将被剪切到grid作为工具条
			/**
			 * 一个数组，指定该表格有哪些列，必须值。每一个元素都是一个对象字面量，定义了该列的各个属性，这些属性包括:
			 * header : 表头文字。
			 * name : 与数据模型对应的字段。
			 * align : 列文字对齐方式，可以为'left'、'center'、'right'之中的一个。
			 * sortable : 该列是否支持客户端排序。
			 * renderer : 列的渲染函数,接受2个参数，v表示当前值，row表示当前行号。
			 * width : 列的宽度,取值为Number或者'autoExpand'.注意只能有一个列被设置为'autoExpand'属性。
			 * autoExpandMin : 当表格宽度较小时,该列宽度自动扩展的最小值。
			 * autoExpandMax : 当表格宽度较大时,该列宽度自动扩展的最大值。
			 * hide : 是否隐藏当前列。 
			 * @type Array[JSON]
			 * @default false
			 * @example
			 * 
			 * var colM_city = {
			 * 		header : '地区',  		//表头文字
			 * 		name : 'city',			//与数据模型对应的字段
			 * 		width : 120,			//列宽,可设置具体数字，也可设置为'autoExpand'，表示自动扩展
			 * 		align : 'left',			//列文字对齐
			 * 		sortable : true,		//是否可排序
			 * 		renderer : function(v, row) {   //列渲染函数，接受2个参数，v表示当前值，row表示当前行号
			 * 		    return v;
			 * 		}
			 * 	};
			 * var colM_address = {
			 *		header : '地址',
			 *		name : 'address',
			 *		align : 'left',
			 *		width : 'autoExpand',
			 *		autoExpandMin : 150,
			 *		sortable : true
			 *	};
			 * $(".selector").omGrid({
			 * 	    colModel : [ colM_city, colM_address ]
			 * });
			 */
			colModel : false,
			/**
			 * 是否在加载的时候显示蒙版.
		     * @type Boolean
		     * @default true
		     * @example
             * $('.selector').omGrid({loadMask : true});
		     */
			loadMask : true,// 是否在加载的时候显示蒙版
			
			
			// 是否自动加载数据，如果设置成false，则不会加载数据，需要手动加载
			autoLoad : true,  //由于暂未提供reload()方法加载数据，故不提倡使用此属性.
			/**
			 * 加载数据的时候出现的蒙板的透明度，数字越小透明度越大，取值区间为0.1-1。
		     * @type Number
		     * @default 0.5
		     * @example
             * $('.selector').omGrid({blockOpacity : 0.5});
		     */
			blockOpacity : 0.5,
			/**
			 * 一个在加载服务器端数据成功后(页面渲染前)执行的方法 ,用来进行对原始数据进行处理,比如修改原始数据.
			 * @event
			 * @type Function
			 * @default function that return the original parameter
			 * @param data 传入的json数据，包括了page、rows、total属性，其中的rows为数据数组
			 * @example
			 * //可以通过data.rows[i].Property获取具体某行某列数据
			 * $(".selector").omGrid({
			 * 	preProcess : function(data){
			 * 		for(var i=0;i &lt; data.rows.length;i++){
			 * 			data.rows[i].start='operamasks';
			 * 		}
			 * 		return data;
			 * 	}
			 * });
			 * //上面例子将所有start属性的值改为operamasks
			 */
			preProcess : function(data) {
				return data; 
			},
			/**
			 * 是否采用客户端排序,该属性可以配置成一个function(data, sortName, sortOrder, grid, propertyCfg),
			 * 即自定义的排序函数，最终要返回data.也可以配置成true,表示采用默认的字典顺序进行排序.
			 * 当指定了该属性时，必须同时设置sortName属性,omGrid才知道对那一列进行排序
			 * @event
			 * @type Function
			 * @param data  数据
			 * @param sortName  排序字段
			 * @param sortOrder 排序顺序
			 * @param grid 当前omGrid对象
			 * @param propertyCfg 当前omGrid的配置项
			 * @default true
			 * @returns 如果配置成了一个function，则必须返回排序后的data
			 * @example
			 * //下面的代码将id为9的数据放到了第一位
		     * 	$(".selector").omGrid({
		     * 		onClientSort: function(data, sortName, sortOrder, grid, propertyCfg){
			 *			for(var i=0;i &lt; data.rows.length;i++){
			 *	 			if(data.rows[i].id == 9){
			 *					var temp = data.rows[i];
			 *					data.rows[i] = data.rows[0];
			 *					data.rows[0] = temp;
			 *				}
			 *			}
			 *			return data;
			 *  	} 
		     * 	});
			 */
			onClientSort : true,
			/**
			 * 列拉动时执行的方法
			 * @event
		     * @param dcoln 本身所在顺序号
		     * @param dcolt 目标地数序号
		     * @param grid 当前grid对象
		     * @type Function
		     * @default emptyFn
		     * @example
		     * 	$(".selector").omGrid({
		     * 		onDragCol:function(dcoln,dcolt,grid){
		     * 			alert('column dragged!');
		     * 		}
		     * 	});
		     */
			onDragCol : function(dcoln, dcolt, grid) {
			},//列拉动时执行的方法
			/**
			 * 隐藏和显示某一列的时候执行的方法
		     * @event
		     * @param cid 操作的列的顺序码
		     * @param visible 隐藏还是显示.true表示显示，false为隐藏
		     * @type Function
		     * @default emptyFn
		     * @example
		     * 	$(".selector").omGrid({
		     * 		onToggleCol:function(cid,visible){
		     * 			alert('你让第 ' + cid + ' 列' + visible?'显示':'隐藏' + '了！');
		     * 		}
		     * 	});
		     * 
		     */
			onToggleCol : function(cid,visible) {
			},//隐藏和显示某一列的时候执行的方法
			/**
			 * 更新数据成功后执行的方法 .
		     * @event
		     * @type Function
		     * @param grid grid对象
		     * @default false
		     * @example
		     * 	$(".selector").omGrid({
		     * 		onSuccess:function(grid){
		     * 			alert('ajax successed!');
		     * 		}
		     * 	});
		     */
			onSuccess : false,
			/**
			 * 更新数据异常后执行的方法 .错误信息为jQuery.ajax返回的异常信息，可参考jQuery.ajax官方文档.
		     * @event
		     * @param XmlHttpRequest XMLHttpRequest 对象
		     * @param textStatus 错误信息
		     * @param errorThrown （可选）捕获的异常对象
		     * @example
		     * 	$(".selector").omGrid({
		     * 		onError:function(){
		     * 			alert('error occured');
		     * 		}
		     * 	});
		     */
			onError : function(){
				return ;
			},
			/**
			 * 当某一行双击时执行的方法 .
			 * @event
			 * @param e 事件对象
			 * @default emptyFn
			 * @param rowData 当前行的数据，为json格式
			 * @param grid 当前的表格对象
		     * @example
		     * 	$(".selector").omGrid({
		     * 		onRowDblClick:function(){
		     * 			alert('row double click event happened');
		     * 		}
		     * 	});
			 */
			onRowDblClick : function(e, rowData, grid) {/* do nothing */
			},// 当某一行双击时触发的函数
			/**
			 * 当某一行单击时执行的方法 .
			 * @event
			 * @param e 事件对象
			 * @param rowData 当前行的数据，为json格式
			 * @param grid 当前的表格对象
			 * @default emptyFn
			 * @returns 如果同时不允许该行被选中，则必须显示返回一个false；返回其他值或者没有返回值将导致该行被选中.
			 * @example
		     * 	$(".selector").omGrid({
		     * 		onRowClick:function(e, rowData, grid){
		     * 			var rtn = [];
		     * 			for (var p in rowData){
		     * 				rtn.push(p+':'+rowData[a]);
		     * 			} 
		     * 			alert(rtn.join(' '));
		     * 			return true;
		     * 		}
		     * 	});
			 */
			onRowClick : function(e, rowData, grid) {
			},// 当某一行单击时触发的函数,如果reutrn false则该行不会再被允许选择
			/**
			 * 当选择行的时候触发事件.
			 * @event
			 * @param grid 当前的表格对象
			 * @default emptyFn
			 */
			onRowSelect : function(grid) {/* do nothing */
			},// 当选择的行改变的时候触发事件
			/**
			 * 当重新计算数据时执行的方法.
		     * @event
		     * @type Function
		     * @default false
		     * @returns 如果在该方法中全部自行完成了计算数据并更新表格的操作，此时可以返回false；否则需要返回true，执行默认的计算数据的逻辑
		     */
			onSubmit : false, // using a custom _populate function
			/**
			 * 是否单选.
		     * @type Boolean
		     * @default false
		     */
			singleSelect : false,
			/**
			 * 是否显示序列号.
		     * @type Boolean
		     * @default false
		     */
			showIndex : false,// @Blee,是否显示序号
			/**
			 * 是否显示checkbox，如果指定了singleSelect=true则该属性失效.
		     * @type Boolean
		     * @default false
		     */
			showCheckbox : false,// @Blee,是否显示checkbox,如果指定了singleSelect=true则该属性失效
			/**
			 * 如果查询出来的数据小于limit数，是否需要填充空白行，仅在limit='auto'的时候该属性有效.
		     * @type Boolean
		     * @default true
		     */
			fillEmptyRows : true,
			/**
			 * 无限滚动表格，当滚动条滚动到最底端的时候会再次发起取数请求，并将数据增量加在原数据后面，
			 * 不能和paged同时使用，设置了此属性为true，则分页条将消失。
			 * @type Boolean
		     * @default false
			 */
			scrollLoad:false
		}, p);
		var baseclasses='om-grid om-widget om-widget-content';
		var temp = $('<div></div>').addClass(baseclasses).insertBefore($(t));
		if (p.width != 'auto') {
		    temp.width(p.width);
		    p.width = temp.width();
		}
		if (p.height != 'auto') {
		    temp.height(p.height);
		    p.height = temp.height();
		}
		temp.remove();
		var inprogress = false; //是否在取数进程中 add by luoyg
		if(p.scrollLoad){
			var scrollLoad  = document.createElement("input"); //记录blog的当前页数
			$(scrollLoad).attr('class','scrollLoad').attr('type','hidden').val(1);
			$(t).append(scrollLoad);
		}
		
		if (p.singleSelect) {
			p.showCheckbox = false;
		}
		if (p.limit == 'auto') {
			p.autoLimit = true;
		}
		p.dataType = 'json';// 只支持json
		$(t).show() // show if hidden
		.attr({
			cellPadding : 0,
			cellSpacing : 0,
			border : 0
		}) // remove padding and spacing
		.removeAttr('width'); // remove width properties
		// create grid class
		var g = {
			hset : {},
			_rePosDrag : function() {
				var cdleft = 0 - this.hDiv.scrollLeft;
				if (this.hDiv.scrollLeft > 0)
					cdleft -= Math.floor(p.cgwidth / 2);
				$(g.cDrag).css({
					top : g.hDiv.offsetTop + 1
				});
				var cdpad = this.cdpad;
				$('div', g.cDrag).hide();
				$('thead tr:first th:visible', this.hDiv).each(function() {
					var n = $('thead tr:first th:visible', g.hDiv).index(this);
					var cdpos = parseInt($(this).width());
//					if (cdleft == 0)
//						cdleft -= Math.floor(p.cgwidth / 2);
//					cdpos = cdpos + cdleft + cdpad;
//					if (isNaN(cdpos)) {
//						cdpos = 0;
//					}
					cdpos = $(this).position().left+cdpos-Math.floor(p.cgwidth/2);
					$('div:eq(' + n + ')', g.cDrag).css({
						'left' : cdpos + 'px'
					}).show();
					cdleft = cdpos;
				});
			},
			_fixHeight : function(newH) {
				newH = false;
				if (!newH)
					newH = $(g.bDiv).height();
				var hdHeight = $(this.hDiv).height();
				$('div', this.cDrag).each(function() {
					$(this).height(newH + hdHeight);
				});
				var nd = parseInt($(g.nDiv).height());
				if (nd > newH){
					$(g.nDiv).height(newH).width(200);
				}
				else {
					$(g.nDiv).height('auto').width('auto');
				}
				$(g.block).css({
					height : newH
					//marginBottom : (newH * -1)
				});
				var hrH = g.bDiv.offsetTop + newH;
				if (p.height != 'auto' && p.resizable)
					hrH = g.vDiv.offsetTop;
				$(g.rDiv).css({
					height : hrH
				});
				// @Blee修复高度计算错误的bug
				var bHeight = (p.height == 'auto') ? 'auto' : (p.height
						- $(g.tDiv).height() - $(g.hDiv).height()
						- $(g.pDiv).height() - $(g.vDiv).height() - 8)
						+ 'px';
				$(g.bDiv).css({
					height : bHeight
				});
				$('div', g.cDrag).css({
					height : $(g.bDiv).height() + $(g.hDiv).height()
				});
			},
			_dragStart : function(dragtype, e, obj) { // default drag function
				// start
				if (dragtype == 'colresize') {// column resize
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					var n = $('div', this.cDrag).index(obj);
					var ow = $('th:visible div:eq(' + n + ')', this.hDiv)
							.width();
					$(obj).addClass('dragging').siblings().hide();
					$(obj).prev().addClass('dragging').show();
					this.colresize = {
						startX : e.pageX,
						ol : parseInt(obj.style.left),
						ow : ow,
						n : n
					};
					$('body').css('cursor', 'col-resize');
				} else if (dragtype == 'vresize') {// table resize
					var hgo = false;
					$('body').css('cursor', 'row-resize');
					if (obj) {
						hgo = true;
						$('body').css('cursor', 'col-resize');
					}
					this.vresize = {
						h : p.height,
						sy : e.pageY,
						w : p.width,
						sx : e.pageX,
						hgo : hgo
					};
				} else if (dragtype == 'colMove') {// column header drag
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					this.hset = $(this.hDiv).offset();
					this.hset.right = this.hset.left
							+ $('table', this.hDiv).width();
					this.hset.bottom = this.hset.top
							+ $('table', this.hDiv).height();
					this.dcol = obj;
					this.dcoln = $('th', this.hDiv).index(obj);
					this.colCopy = document.createElement("div");
					this.colCopy.className = "colCopy";
					this.colCopy.innerHTML = obj.innerHTML;
					if ($.browser.msie) {
						this.colCopy.className = "colCopy ie";
					}
					$(this.colCopy).css({
						'position' : 'absolute',
						'float' : 'left',
						'display' : 'none',
						'textAlign' : obj.align
					});
					$('body').append(this.colCopy);
					$(this.cDrag).hide();
				}
				$('body').noSelect();
			},
			_dragMove : function(e) {
				if (this.colresize) {// column resize
					var n = this.colresize.n;
					var diff = e.pageX - this.colresize.startX;
					var nleft = this.colresize.ol + diff;
					var nw = this.colresize.ow + diff;
					if (nw > p.minWidth) {
						$('div:eq(' + n + ')', this.cDrag).css('left', nleft);
						this.colresize.nw = nw;
					}
				} else if (this.vresize) {// table resize
					var v = this.vresize;
					var y = e.pageY;
					var diff = y - v.sy;
					if (!p.defwidth)
						p.defwidth = p.width;
					if (p.width != 'auto' && !p.nohresize && v.hgo) {
						var x = e.pageX;
						var xdiff = x - v.sx;
						var newW = v.w + xdiff;
						if (newW > p.defwidth) {
							this.gDiv.style.width = newW + 'px';
							p.width = newW;
						}
					}
					var newH = v.h + diff;
					if ((newH > p.minHeight || p.height < p.minHeight)
							&& !v.hgo) {
						this.bDiv.style.height = newH + 'px';
						p.height = newH;
						this._fixHeight(newH);
					}
					v = null;
				} else if (this.colCopy) {
					$(this.dcol).addClass('thMove').removeClass('thOver om-state-hover');
					if (e.pageX > this.hset.right || e.pageX < this.hset.left
							|| e.pageY > this.hset.bottom
							|| e.pageY < this.hset.top) {
						// this._dragEnd();
						$('body').css('cursor', 'move');
					} else {
						$('body').css('cursor', 'pointer');
					}
					$(this.colCopy).css({
						top : e.pageY + 10,
						left : e.pageX + 20,
						display : 'block'
					});
				}
			},
			_dragEnd : function() {
				if (this.colresize) {
					var n = this.colresize.n;
					var nw = this.colresize.nw;
					$('th:visible div:eq(' + n + ')', this.hDiv).css('width', nw);
					$('tr', this.bDiv).each(
							function() {
								$('td:visible div:eq(' + n + ')', this).css('width', nw);
							});
					this.hDiv.scrollLeft = this.bDiv.scrollLeft;
					$('div:eq(' + n + ')', this.cDrag).siblings().show();
					$('.dragging', this.cDrag).removeClass('dragging');
					this._rePosDrag();
					this._fixHeight();
					this.colresize = false;
				} else if (this.vresize) {
					this.vresize = false;
				} else if (this.colCopy) {
					$(this.colCopy).remove();
					if (this.dcolt != null) {
						if (this.dcoln > this.dcolt)
							$('th:eq(' + this.dcolt + ')', this.hDiv).before(this.dcol);
						else
							$('th:eq(' + this.dcolt + ')', this.hDiv).after(this.dcol);
						this._switchCol(this.dcoln, this.dcolt);
						$(this.cdropleft).remove();
						$(this.cdropright).remove();
						this._rePosDrag();
						p.onDragCol(this.dcoln, this.dcolt,p);
					}
					this.dcol = null;
					this.hset = null;
					this.dcoln = null;
					this.dcolt = null;
					this.colCopy = null;
					$('.thMove', this.hDiv).removeClass('thMove');
					$(this.cDrag).show();
				}
				$('body').css('cursor', 'default');
				$('body').noSelect(false);
			},
			_resizeTo : function(width, height) {
				this.gDiv.style.width = width + 'px';
				p.width = width;
				this.bDiv.style.height = height + 'px';
				p.height = height;
				// 重新调整列宽
				var already = 0;
				var fullCol = null;
				var max = p.width == 'auto' ? $(t).parent().width() : p.width;;
				var n = 0, index = 0;
				$('thead tr:first th:visible', g.hDiv).each(function() {
					var th = $(this);
					if (th.data('autoExpand')) {
						fullCol = th;
						index = n;
					} else {
						already += (th.width() + 2);
					}
					n++;
				});
				if (fullCol) {
					var w = max - already;
					if (w <= fullCol.data('autoExpandMin')) {
						w = fullCol.data('autoExpandMin');
					}
					if (!p.autoLimit) {
						w -= 18;
					}
					$('th:visible div:eq(' + index + ')', this.hDiv).css('width', w - 17);
					$('tr', this.bDiv).each(
							function() {
								$('td:visible div:eq(' + index + ')', this).css('width', w - 17);
							});
				}
				this._fixHeight(height);
			},
			_toggleCol : function(cid, visible) {
				var ncol = $("th[axis='col" + cid + "']", this.hDiv)[0];
				var n = $('thead th', g.hDiv).index(ncol);
				var cb = $('input[value=' + cid + ']', g.nDiv)[0];
				if (visible == null) {
					visible = ncol.hidden;
				}
				if ($('input:checked', g.nDiv).length < p.minColToggle && !visible) {
					return false;
				}
				if (visible) {
					ncol.hidden = false;
					$(ncol).show();
					cb.checked = true;
				} else {
					ncol.hidden = true;
					$(ncol).hide();
					cb.checked = false;
				}
				$('tbody tr', t).each(function() {
					if (visible) {
						$('td:eq(' + n + ')', this).show();
					} else {
						$('td:eq(' + n + ')', this).hide();
					}
				});
				this._rePosDrag();
				p.onToggleCol(cid, visible);
				return visible;
			},
			_switchCol : function(cdrag, cdrop) { // switch columns
				$('tbody tr', t).each(
						function() {
							if (cdrag > cdrop)
								$('td:eq(' + cdrop + ')', this).before($('td:eq(' + cdrag + ')', this));
							else
								$('td:eq(' + cdrop + ')', this).after($('td:eq(' + cdrag + ')', this));
						});
				// switch order in nDiv
				if (cdrag > cdrop) {
					$('tr:eq(' + cdrop + ')', this.nDiv).before($('tr:eq(' + cdrag + ')', this.nDiv));
				} else {
					$('tr:eq(' + cdrop + ')', this.nDiv).after($('tr:eq(' + cdrag + ')', this.nDiv));
				}
				if ($.browser.msie && $.browser.version < 7.0) {
					$('tr:eq(' + cdrop + ') input', this.nDiv)[0].checked = true;
				}
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
			},
			_scroll : function() {
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				this._rePosDrag();
				//如果是滚动加载型表格 add by luoyg  ---------begin
				if(p.scrollLoad){  //新增scrollLoad属性
					var loadingdiv =  document.createElement('div'); 
					var loadingimg = document.createElement('img');
					$(loadingimg).addClass('loadingImg').css('display','block');
					loadingdiv.align = 'center';
					var $loading = $(loadingdiv);
				  	  	$loading.append(loadingimg);
				  	    $loading.css({
				  	    	position : 'relative',
				  	    	bottom : 10
						});
				  $(this.bDiv).scroll(function(){
					  var nDivHight = $(".bDiv").height();
					  var nScrollHight = $(this)[0].scrollHeight;
			          var nScrollTop = $(this)[0].scrollTop;
				      if(nScrollTop + nDivHight >= (parseInt(nScrollHight))){ //滚动条到底部了
				    	  if(!inprogress){
				    		  inprogress = true;
				        	  $('.bDiv').append($loading);
				        	 $.ajax({
									type: p.method,
									url: p.url,
									data: [
									   {
									        name:'start',
									        value:p.limit * $('input.scrollLoad', this).val()
									    },{
									        name: 'limit',
									        value: p.limit
									    },{
									        name : '_time_stamp_',
									        value : new Date().getTime()
									    }
									],
									dataType: p.dataType,
									success: function (data) {
										$loading.detach(); //去掉loading
										if(data.rows.length > 0){
											g._addData(data);
										}else{
											$('.bDiv').scrollTop($('.bDiv').scrollTop() - 15); //隐藏滚动图标占用的高度
										}
										inprogress = false;
										$('.scrollLoad').val(parseInt($('.scrollLoad').val())+1); //分页记录
									},
									error: function (XMLHttpRequest, textStatus, errorThrown) {
										try {
											$('.pPageStat', g.pDiv).html(p.errorMsg);
											$('.pPageStat', g.pDiv).css('color', 'red');
											if (p.loadMask) {
												$(g.block).remove();
											}
											if (typeof(p.onError) == 'function') {
												p.onError(XMLHttpRequest, textStatus, errorThrown);
												$loading.detach(); //去掉loading
											}
										} catch (e) {
											
										} finally {
											return false;
										}
										
									}
								});  
								
				        	  //$(this)[0].scrollTop  = parseInt($(this)[0].scrollHeight) - parseInt($(".bDiv").height()) + 20;
				    	  }
				      }
				 });
			 }
				//----------end
			},
			_getSelections : function() {
				var result = [];
				$('tbody tr', g.bDiv).each(function() {
					if ($(this).hasClass('om-state-highlight')) {
						result.push($(this).data('data'));
					}
				});
				return result;
			},
			_setSelections : function(arr, colId) {
			    if (Object.prototype.toString.call(arr) !== "[object Array]") {
			        var tmp = [];
			        tmp.push(arr);
			        arr = tmp;
			    }
			    $('tbody tr', g.bDiv).removeClass('om-state-highlight');
			    $('tbody tr', g.bDiv).each(function() {
			        for (var i = 0; i < arr.length ; i ++) {
			            if ($(this).find('td[abbr=' + colId + ']').text() == arr[i]) {
	                        $(this).addClass('om-state-highlight');
	                        if($(this).hasClass('erow')) {
	                            $(this).removeClass('erow');
	                        }
	                    }
	                }
                });
			},
			_getButtons : function() {
				if (p.buttons) {
					return $('div.fbutton span', g.tDiv);
				}
			},
			//如果是scrollLoad则不删除原有的data，否则会删除原有的data
			_addData : function(data) { // parse data
				if (p.dataType == 'json') {
					data = $.extend({
						rows : [],
						page : 0,
						total : 0
					}, data);
				}
				g.data = data;
				data = p.preProcess(data);
				this.loading = false;
				if (!data) {
					$('.pPageStat', this.pDiv).html(p.errorMsg).css('color','red');
					return false;
				}
				p.total = data.total;
				p.pagelen = data.rows.length;
				if (p.total == 0) {
					if (!p.lazyTotalUrl) {// 如果不是延时加载分页信息则直接认为没有数据
						$('tr, a, td, div', t).unbind();
						$(t).empty();
						p.pages = 1;
						p.page = 1;
						this._buildPager();
						$('.pPageStat', this.pDiv).html(p.emptyMsg).css('color', 'red');
						$('.pReload', this.pDiv).removeClass('loading');
						if (p.loadMask) {
						    $(g.block).remove();
						}
						return false;
					}
					if (data.page >= 2) {
						p.pages = data.page + 3;
					} else {
						p.pages = 5;
					}
				} else {
					p.pages = Math.ceil(p.total / p.limit);
				}
				p.page = data.page;
				if (!p.lazyTotalUrl) {// 构建分页器的需要
					$('.pReload', this.pDiv).removeClass('loading');
				}
				this._buildPager();
				// build new body
				var tbody; //如果是博客型表格则查找原来的tbogdy add by luoyg
				if(p.scrollLoad){
					tbody = $('.bDiv tbody');
					if(tbody.length == 0){
						tbody = document.createElement('tbody');
					}
				}else{
					tbody = document.createElement('tbody');
				}
				
				
				if (p.dataType == 'json') {
					var rows = data.rows;
					if (p.fillEmptyRows && p.autoLimit) {// 填充空白行
						if (rows.length < (p.limit + 1)) {// 加1是为了防止最底部有可能留出的空隙不美观
							for ( var i = rows.length; i < p.limit + 1; i++) {
								rows.push({
									emptyId : 'emptyId' + i
								});
							}
						}
					}
					$.each(rows,function(i, row) {
						var tr = document.createElement('tr');
						if (i % 2 && p.striped) {
							tr.className = 'erow';
						}
						$('thead tr:first th', g.hDiv).each(function() {
							// add cell
							var td = document.createElement('td');
							var idx = $(this).attr('axis').substr(3);
							td.align = this.align;
							if (row.emptyId) {
								td.innerHTML = '';
								$(td).css('height','25px');
								$(tr).addClass('emptyTr');
							} else {
								// If the json
								// elements
								// aren't named
								// (which is
								// typical), use
								// numeric order
								// @Blee,修改row.cell[inx]为row[inx],使用row.cell[inx]会造成json格式过于复杂
								if ('index' === idx) {
									td.innerHTML = (p.page - 1) * p.limit + i + 1;
								} else if ('checkbox' === idx) {
									td.innerHTML = '<span class="checkbox"/>';
								} else if (typeof row[idx] != "undefined") {
									td.innerHTML = (row[idx] != null) ? row.cell[idx]: '';// null-check
									// for
									// Opera-browser
								} else {
									// @Blee,添加renderer支持
									if (typeof (p.colModel[idx].renderer) == 'function') {
										var fn = p.colModel[idx].renderer;
										var result = fn(row[p.colModel[idx].name] , row);
										if (typeof (result) != 'undefined') {
											if (p.autoLimit && typeof (result) == 'string') {
												td.innerHTML = result.replace(/<br\/>/g,'');
											} else {
												td.innerHTML = result;
											}
										}
									} else {
										if (typeof (row[p.colModel[idx].name]) != 'undefined') {
											if (p.autoLimit && typeof (row[p.colModel[idx].name]) == 'string') {
												td.innerHTML = row[p.colModel[idx].name].replace(/<br\/>/g,'');
											} else {
												td.innerHTML = row[p.colModel[idx].name];
											}
										}
									}
								}

								$(tr).data('data', row);
							}
							$(td).attr('abbr',$(this).attr('abbr'));
							$(tr).append(td);
							td = null;
						});
						if ($('thead', this.gDiv).length < 1) {// handle
							// if grid has no headers
							for (idx = 0; idx < cell.length; idx++) {
								var td = document.createElement('td');
								// If the json elements aren't
								// named (which is typical), use
								// numeric order
								if (typeof row.cell[idx] != "undefined") {
									td.innerHTML = (row.cell[idx] != null) ? row.cell[idx]: '';// null-check
									// for Opera-browser
								} else {
									td.innerHTML = row.cell[p.colModel[idx].name];
								}
								$(tr).append(td);
								td = null;
							}
						}
						$(tbody).append(tr);
						tr = null;
					});
				}
				$('tr', t).unbind();
				if(!p.scrollLoad){ //如果不是滚动加载型表格就清空 add by luoyg
					$(t).empty();
				}
				//如果有checkbox列则勾选框需要重置
				$("th[axis='colcheckbox'] span.selected", g.hDiv).removeClass('selected');
				$(t).append(tbody);
				this._addCellProp();
				this._addRowProp();
				this._rePosDrag();
				tbody = null;
				data = null;
				i = null;
				if (typeof(p.onSuccess) == 'function') {
					p.onSuccess(this);
				}
				if (p.loadMask) {
					$(g.block).remove();
				}
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				if ($.browser.opera) {
					$(t).css('visibility', 'visible');
				}
			},
			/**
			 * @author chenjie
			 * @param data 输入数据(json格式)
			 * @param sortName 排序列的name
			 * @param sortOrder 顺序(非必须，默认为asc)
			 * @returns 输出排序后的数据
			 */
			_sortData : function(data, sortName, sortOrder) {
				for (var i = 0 ;i < data.rows.length; i++ ){
					if (data.rows[i].emptyId) {
						data.rows.splice(i,1);
						i --;
					}
				}
				var sortOrder = sortOrder || p.sortOrder || 'asc';
				if (typeof (p.onClientSort) == 'function') {
					return p.onClientSort(data, sortName, sortOrder, g, p);
				} else if (p.onClientSort == true) {
					var sort = (sortOrder == 'asc' ? 1 : -1);
					var renderer;
					if (p.colModel) {
						for (var i = 0; i < p.colModel.length; i ++) {
							if (p.colModel[i].name == sortName) {
								renderer = p.colModel[i].renderer;
								break;
							}
						} 
					}
					data.rows.sort(function(a, b) {
						var ah = renderer ? renderer(a[sortName], a)
								: a[sortName];
						var bh = renderer ? renderer(b[sortName], b)
								: b[sortName];
						if (ah == bh) {
							return 0;
						} else if (ah < bh) {
							return -1 * sort;
						} else {
							return 1 * sort;
						}
					});
					return data;
				}
			},

			_changeSort : function(th) { // change sortOrder
				if (this.loading) {
					return true;
				}
				$(g.nDiv).hide();
				$(g.nBtn).hide();
				if (p.sortName == $(th).attr('abbr')) {
					if (p.sortOrder == 'asc') {
						p.sortOrder = 'desc';
					} else {
						p.sortOrder = 'asc';
					}
				} else {
					p.sortOrder = 'asc';
				}
				$(th).addClass('sorted').siblings().removeClass('sorted');
				$('.sdesc', this.hDiv).removeClass('sdesc');
				$('.sasc', this.hDiv).removeClass('sasc');
				$('div', th).addClass('s' + p.sortOrder);
				p.sortName = $(th).attr('abbr');

				if (p.onClientSort) {
					g._addData(g._sortData(g.data, p.sortName));
				} else {
					this._populate();
				}
			},

			// @Blee,构造分页器,应当在此实现懒加载的分页
			_buildPager : function() { // rebuild pager based on new properties
				$('.pageLink', this.pDiv).html(this._calculateLink());
				if (!p.lazyTotalUrl) {
					$('.pcontrol input', this.pDiv).val(p.page);
					$('.pcontrol span', this.pDiv).html(p.pages);
					var r1 = (p.page - 1) * p.limit + 1;
					var r2 = r1 + p.limit - 1;
					if (p.total < r2) {
						r2 = p.total;
					}
					var stat = p.pageStat;
					stat = stat.replace(/{from}/, r1);
					stat = stat.replace(/{to}/, r2);
					stat = stat.replace(/{total}/, p.total);
					$('.pPageStat', this.pDiv).html(stat);
				} else if (!p.aboutTotal) {// 如果该参数不存在,则从服务器上加载
					if (!p.lazyTotalUrl) {
						throw '未指定lazyTotalUrl';
					}
					$('.pPageStat', this.pDiv).html('正在计算总数...');
					this.loading = true;
					$.ajax({
						url : p.lazyTotalUrl,
						method : p.method,
						dataType : p.dataType,
						data : [{
						    name : '_time_stamp_',
						    value : new Date().getTime()
						}],
						success : function(data) {
							p.aboutTotal = data.total;
							g._buildAboutPage();
							$('select', g.pDiv).attr('disabled','');//让rpOptions可以下拉选择
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							try {
								if (typeof(p.onError) == 'function'){
									p.onError(XMLHttpRequest, textStatus, errorThrown);
								}
							} catch (e) {
								//do nothing 
							} finally {
								return false;
							}
						}
					});
				} else {
					g._buildAboutPage();
				}
				if (p.useRp && !p.autoLimit) {
				    g._buildLimitOptions();
				    if (this.loading) {
				        $('select', g.pDiv).attr('disabled', 'disabled');
				    }
				}
			},
			_buildAboutPage : function() {
				this.loading = false;
				$('.pReload', this.pDiv).removeClass('loading');
				var r1 = (p.page - 1) * p.limit + 1;
				var r2 = r1 + p.pagelen - 1;
				var stat = p.pageStat;
				stat = stat.replace(/{from}/, r1);
				stat = stat.replace(/{to}/, r2);
				stat = stat.replace(/{total}/, p.aboutTotal);
				$('.pPageStat', this.pDiv).html(stat);
			},
			_buildLimitOptions : function(){
			    var opt = '', sel = '';
                for ( var nx = 0; nx < p.rpOptions.length; nx++) {
                    if (p.limit == p.rpOptions[nx])
                        sel = 'selected="selected"';
                    else
                        sel = '';
                    opt += "<option value='" + p.rpOptions[nx] + "' " + sel
                            + " >每页" + p.rpOptions[nx] + "条</option>";
                }
                $('.pGroup:first', g.pDiv)
                        .html(
                                "<select name='rp'>"
                                        + opt
                                        + "</select></div> <div class='btnseparator'>");
                $('select', g.pDiv).change(function() {
                    if (p.onRpChange) {
                        p.onRpChange(+this.value);
                    } else {
                        p.newp = 1;
                        p.limit = +this.value;
                        g._populate();
                    }
                });
			},
			_calculateLink : function() {
				var r = [];
				if (p.pages <= 1) {
					r.push(1);
				} else if (p.page <= 2) {
					for ( var i = 1; i <= 5 && i <= p.pages; i++) {
						r.push(i);
					}
				} else if (p.pages - p.page < 2) {
					var first = (p.pages - 5 + 1) < 1 ? 1 : (p.pages - 5 + 1);
					for ( var i = first; i <= p.pages; i++) {
						r.push(i);
					}
				} else {
					r = [ p.page - 2, p.page - 1, p.page, p.page + 1,
							p.page + 2 ];
				}
				var html = '';
				for ( var i = 0; i < r.length; i++) {
					html = (html + '<a href="javascript:" class="'
							+ (r[i] == p.page ? 'now-page-link' : '') + '">'
							+ r[i] + '</a>');
				}
				html = $(html);
				html.click(function() {
					g._changePage($(this).text());
				});
				return html;
			},
			_populate : function() { // get latest data
				if (this.loading) {
					return true;
				}
				if (p.onSubmit) {
					var gh = p.onSubmit();
					if (!gh) {
						return false;
					}
				}
				if (p.autoLimit) {
					p.limit = Math.floor($(g.bDiv).height() / 26);
				}
				this.loading = true;
				if (!p.url) {
					return false;
				}
				$('.pPageStat', this.pDiv).html(p.loadingMsg);
				$('.pReload', this.pDiv).addClass('loading');
				$(g.block).css({
					//top : g.gDiv.offsetTop,
					height : $(g.gDiv).height(),
					width : $(g.gDiv).width()
				});
				if (p.loadMask) {
					$(this.gDiv).prepend(g.block);
				}
				if ($.browser.opera) {
					$(t).css('visibility', 'hidden');
				}
				if (p.page > p.pages) {
				    p.page = p.pages;
				}
				if (!p.newp) {
					p.newp = p.page;
				}
				var param = [ {
					name : 'start',
					value : p.limit * (p.newp - 1)
				}, {
					name : 'limit',
					value : p.paged ? p.limit : 0
				}, {
					name : 'sortName',
					value : p.sortName
				}, {
					name : 'sortOrder',
					value : p.sortOrder 
				}, {
					name : 'query',
					value : p.query
				}, {
					name : 'qtype',
					value : p.qtype
				}, {
				    name : '_time_stamp_',
				    value : new Date().getTime()
				} ];
				if (p.params) {
					for ( var pi = 0; pi < p.params.length; pi++) {
						param[param.length] = p.params[pi];
					}
				}
				$.ajax({
					type : p.method,
					url : p.url,
					data : param,
					dataType : p.dataType,
					success : function(data) {
						if (p.onClientSort && p.sortName) {
							data = g._sortData(data,p.sortName);
						}
						g._addData(data);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						try {
							$('.pPageStat', g.pDiv).html(p.errorMsg).css('color','red');
							if (p.loadMask) {
								$(g.block).remove();
							}
							$('.pReload', g.pDiv).removeClass('loading');
							if (typeof(p.onError) == 'function') {
								p.onError(XMLHttpRequest, textStatus, errorThrown);
							}
						} catch (e) {
							// do nothing 
						} finally {
							return false;
						}
						
 					}
				});
			},
			_setParams : function(params) {
				p.params = params || {};
				p.newp = 1;
				this._populate();
			},
			_doSearch : function() {
				p.query = $('input[name=q]', g.sDiv).val();
				p.qtype = $('select[name=qtype]', g.sDiv).val();
				p.newp = 1;
				this._populate();
			},
			_changePage : function(ctype) { // change page
				if (this.loading) {
					return true;
				}
				switch (ctype) {
				case 'first':
					p.newp = 1;
					break;
				case 'prev':
					if (p.page > 1) {
						p.newp = parseInt(p.page) - 1;
					}
					break;
				case 'next':
					if (p.page < p.pages) {
						p.newp = parseInt(p.page) + 1;
					}
					break;
				case 'last':
					p.newp = p.pages;
					break;
				case 'input':
					var nv = parseInt($('.pcontrol input', this.pDiv).val());
					if (isNaN(nv)) {
						nv = 1;
					}
					if (nv < 1) {
						nv = 1;
					} else if (nv > p.pages) {
						nv = p.pages;
					}
					$('.pcontrol input', this.pDiv).val(nv);
					p.newp = nv;
					break;
				default:
					if (/\d/.test(ctype)) {
						if (isNaN(nv)) {
							nv = 1;
						}
						var nv = parseInt(ctype);
						;
						if (nv < 1) {
							nv = 1;
						} else if (nv > p.pages) {
							nv = p.pages;
						}
						$('.pcontrol input', this.pDiv).val(nv);
						p.newp = nv;
					}
				}
				if (p.newp == p.page) {
					return false;
				}
				if (p.changepage) {
					p.changepage(p.newp);
				} else {
					this._populate();
				}
			},
			_addCellProp : function() {
				$('tbody tr td', g.bDiv).each(function() {
				  if($(this.children).length == 0 || $(this.children)[0].tagName != 'DIV'){   //add by luoyg 博客类型列表是累加形式，故前面的行依据处理过样式，无需再处理
					var tdDiv = document.createElement('div');
					var n = $('td', $(this).parent()).index(this);
					var pth = $('th:eq(' + n + ')', g.hDiv).get(0);
					if (pth != null) {
						if (p.sortName == $(pth).attr('abbr') && p.sortName) {
							this.className = 'sorted';
						}
						$(tdDiv).css({
							textAlign : pth.align,
							width : $('div:first', pth)[0].style.width
						});
						if (pth.hidden) {
							$(this).css('display', 'none');
						}
					}
					if (p.noWrap == false) {
						$(tdDiv).css('white-space', 'normal');
					}
					if (this.innerHTML == '') {
						this.innerHTML = '&nbsp;';
					}
					tdDiv.innerHTML = this.innerHTML;
					var prnt = $(this).parent()[0];
					var pid = false;
					if (prnt.id) {
						pid = prnt.id.substr(3);
					}
					if (pth != null) {
						if (pth.process)
							pth.process(tdDiv, pid);
					}
					$(this).empty().append(tdDiv).removeAttr('width'); // wrap
					// content
				  }
				});
			},
			_getCellDim : function(obj) {// get cell prop for editable event
				var ht = parseInt($(obj).height());
				var pht = parseInt($(obj).parent().height());
				var wt = parseInt(obj.style.width);
				var pwt = parseInt($(obj).parent().width());
				var top = obj.offsetParent.offsetTop;
				var left = obj.offsetParent.offsetLeft;
				var pdl = parseInt($(obj).css('paddingLeft'));
				var pdt = parseInt($(obj).css('paddingTop'));
				return {
					ht : ht,
					wt : wt,
					top : top,
					left : left,
					pdl : pdl,
					pdt : pdt,
					pht : pht,
					pwt : pwt
				};
			},
			// row: tr 
			// state: hover, erow, highlight
			_changeRowState : function(row, state, ctrlkey) {
			    if (state == 'hover') {
			        
			    } else if(state == 'erow') {
			        
			    } else if(state == 'highlight') {
			        if (row.hasClass('om-state-highlight')) {
			            row.removeClass('om-state-highlight');
                        if (p.singleSelect || !ctrlkey) {
                            row.siblings().removeClass('om-state-highlight');
                            if(p.striped) {
                                row.parent().find("tr:odd").addClass('erow');
                            }
                        } else {
                            if(p.striped) {
                                row.parent().find("tr:odd").filter(row).addClass('erow');
                            }
                        }
                    } else {
                        row.addClass('om-state-highlight');
                        if (p.singleSelect || !ctrlkey) {
                            row.siblings().removeClass('om-state-highlight');
                            if (p.striped) {
                                row.parent().find("tr:odd").addClass('erow');
                            }
                        }
                        row.removeClass('erow');
                    }
			    }
			},
			_addRowProp : function() {
				$('tbody tr', g.bDiv).each(function() {
					var hCheckbox = $('span.checkbox',$(this).closest('div').siblings('.hDiv'));
					if ($(this).hasClass('emptyTr')) {
						return;
					}
					$(this).click(function(e) {
						var obj = (e.target || e.srcElement);
						if (obj.href || obj.type)
							return true;
						var rst = p.onRowClick(e, $(this).data('data'), g);
						if (rst === false) {
							return true;
						}
						g._changeRowState($(this), 'highlight', e.ctrlKey);
						hCheckbox.addClass('selected');
						if (!$(this).hasClass('om-state-highlight') || $(this).siblings().not('.om-state-highlight').not('.emptyTr').length != 0){
							hCheckbox.removeClass('selected');
						}
						p.onRowSelect(g);
					}).mousedown(function(e) {
						if (e.shiftKey) {
							$(this).toggleClass('om-state-highlight');
							hCheckbox.addClass('selected');
							if (!$(this).hasClass('om-state-highlight') || $(this).siblings().not('.om-state-highlight').not('.emptyTr').length != 0){
								hCheckbox.removeClass('selected');
							}
							p.onRowSelect(g);
							g.multisel = true;
							this.focus();
							$(g.gDiv).noSelect();
						}
					}).mouseup(function() {
						if (g.multisel) {
							g.multisel = false;
							$(g.gDiv).noSelect(false);
						}
					}).hover(function(e) {
					    if($(this).hasClass('erow')){
                            $(this).removeClass('erow');
                            $(this).addClass('erow-hover');
                        }
                        $(this).addClass('om-state-hover');
						if (g.multisel) {
							$(this).toggleClass('om-state-highlight');
							hCheckbox.addClass('selected');
							if (!$(this).hasClass('om-state-highlight') || $(this).siblings().not('.om-state-highlight').not('.emptyTr').length != 0){
								hCheckbox.removeClass('selected');
							}
							p.onRowSelect(g);
						}
					}, function() {
					    if($(this).hasClass('erow-hover') && !$(this).hasClass('om-state-highlight')){
                            $(this).removeClass('erow-hover');
                            $(this).addClass('erow');
                        }
                        $(this).removeClass('om-state-hover');
					}).dblclick(function(e) {
						try {
							p.onRowDblClick(e, $(this).data('data'), g);
						} catch (e) {
							if (typeof(p.onError) == 'function')
								p.onError(e);
						}
					});
					if (p.showCheckbox) {
						var me = $(this);
						$('span.checkbox', this).click(function(e) {
							me.toggleClass('om-state-highlight');
							hCheckbox.addClass('selected');
							if (!me.hasClass('om-state-highlight') || me.siblings().not('.om-state-highlight').not('.emptyTr').length != 0){
								hCheckbox.removeClass('selected');
							}
							p.onRowSelect(g); 
							e.stopPropagation();
						});
					}
				});
			},
			pager : 0
		};
		if (p.colModel) { // create model if any
			thead = document.createElement('thead');
			var tr = document.createElement('tr');
			// @Blee,允许将col设置为autoExpand而自动充满
			var fullCol = null;
			var max = p.width == 'auto' ? $(t).parent().width() : p.width;
			var already = 0;
			if (p.showIndex) {// 构造序号列
				var th = document.createElement('th');
				$(th).attr('axis', 'colindex');
				$(th).attr('width', 25);
				$(th).attr('align', 'center');
				$(tr).append($(th));
				already += 37;
				/*
				if (p.showCheckbox) {
					$(th).css('border-right', 'none');
				}
				*/
			}
			if (p.showCheckbox) {// 构造checkbox列
				var th = document.createElement('th');
				th.innerHTML = '<span class="checkbox"/>';
				$(th).attr('axis', 'colcheckbox');
				$(th).attr('width', 17);
				$(th).attr('align', 'center');
				$(tr).append($(th));
				already += 29;
				/*
				if (p.showIndex) {
					$(th).css('border-left', 'none');
				}
				*/
			}
			for ( var i = 0; i < p.colModel.length; i++) {
				var cm = p.colModel[i];
				var th = document.createElement('th');
				th.innerHTML = cm.header;
				if (cm.sortable) {
				    $(th).attr('sortable', true);
				}
				if (cm.name) {
				    $(th).attr('abbr', cm.name);
				}
				$(th).attr('axis', 'col' + i);
				if (cm.align) {
					th.align = cm.align;
				}
				if (cm.width) {
					if (cm.width == 'autoExpand') {
						if (fullCol) {
							throw '不能指定两个autoExpand列!';
						}
						fullCol = $(th);
						fullCol.data('autoExpandMin', cm.autoExpandMin
								|| p.defaultColWidth);
					} else {
						$(th).attr('width', cm.width);
						if (!cm.hide) {
							already += (cm.width + 12);
						}
					}
				} else {
					$(th).attr('width', p.defaultColWidth);
					if (!cm.hide) {
						already += (p.defaultColWidth + 12);
					}
				}
				if (cm.hide) {
					th.hidden = true;
				}
				if (cm.process) {
					th.process = cm.process;
				}
				$(tr).append(th);
			}
			if (fullCol) {
				var w = max - already;
				if (w <= fullCol.data('autoExpandMin')) {
					w = fullCol.data('autoExpandMin');
				}
				if (!p.autoLimit) {
					w -= 18;
				}
				fullCol.attr('width', w - 17);// 15为应有的padding等,20为滚动条预留宽度
				fullCol.data('autoExpand', 'yes');
				fullCol.data('autoExpandMin', fullCol.data('autoExpandMin'));
			}
			$(thead).append(tr);
			$(t).prepend(thead);
		} // end if p.colmodel
		// init divs
		g.gDiv = document.createElement('div'); // create global container
		g.mDiv = document.createElement('div'); // create title container
		g.hDiv = document.createElement('div'); // create header container
		g.bDiv = document.createElement('div'); // create body container
		g.vDiv = document.createElement('div'); // create grip
		g.rDiv = document.createElement('div'); // create horizontal resizer
		g.cDrag = document.createElement('div'); // create column drag
		g.block = document.createElement('div'); // creat blocker
		g.nDiv = document.createElement('div'); // create column show/hide popup
		g.nBtn = document.createElement('div'); // create column show/hide
		// button
		g.iDiv = document.createElement('div'); // create editable layer
		g.tDiv = document.createElement('div'); // create toolbar
		g.sDiv = document.createElement('div');
		g.pDiv = document.createElement('div'); // create pager container
		if (!p.paged || p.scrollLoad) { //如果设置为了无限滚动表格，则自动屏蔽分页工具条
			g.pDiv.style.display = 'none';
		}
		g.hTable = document.createElement('table');
		g.gDiv.className = baseclasses;
		if (p.width != 'auto') {
			g.gDiv.style.width = p.width + 'px';
		}
		// add conditional classes
		if ($.browser.msie) {
			$(g.gDiv).addClass('ie');
		}
		if (p.noVStripe) {
			$(g.gDiv).addClass('novstripe');
		}
		$(t).before(g.gDiv);
		$(g.gDiv).append(t);
		// set toolbar
		if (p.buttons) {
			g.tDiv.className = 'tDiv om-widget-header';
			var tDiv2 = document.createElement('div');
			tDiv2.className = 'tDiv2';
			for ( var i = 0; i < p.buttons.length; i++) {
				var btn = p.buttons[i];
				if (!btn.separator) {
					var btnDiv = document.createElement('div');
					btnDiv.className = 'fbutton';
					//不设置imgclass的话将不显示图片
					if (btn.imgclass){
						btnDiv.innerHTML = " <div> <span class='tbIcon'></span><span class='tbText'>"
								+ btn.name + "</span></div>";
						$('span.tbIcon', btnDiv).addClass(btn.imgclass);
				    }else{
				    	btnDiv.innerHTML = " <div><span class='tbText'>"
							+ btn.name + "</span></div>";
				    }
					if (btn.bclass)
						$('span', btnDiv).addClass(btn.bclass);
					btnDiv.click = btn.click;
					btnDiv.name = btn.name;
					if (btn.click) {
						$(btnDiv).click(function() {
							this.click(this.name, g, $(btnDiv).find('span').get(0));
						});
					}
					$(tDiv2).append(btnDiv);
					if ($.browser.msie && $.browser.version < 7.0) {
						$(btnDiv).hover(function() {
							$(this).addClass('fbOver');
						}, function() {
							$(this).removeClass('fbOver');
						});
					}
				} else {
					$(tDiv2).append("<div class='btnseparator'></div>");
				}
			}
			$(g.tDiv).append(tDiv2);
			$(g.tDiv).append("<div style='clear:both'></div>");
			$(g.gDiv).prepend(g.tDiv);
		}
		if (p.toolbar) {
			if (!$('#'+p.toolbar)) {
				return;
			}
			g.tDiv.className = 'tDiv om-widget-header';
			var tDiv2 = $('#'+p.toolbar);
			$(g.tDiv).append(tDiv2.clone(true).addClass("tDiv2"));
			tDiv2.remove();
			$(g.gDiv).prepend(g.tDiv);
		}
		g.hDiv.className = 'hDiv om-state-default';
		$(t).before(g.hDiv);
		g.hTable.cellPadding = 0;
		g.hTable.cellSpacing = 0;
		$(g.hDiv).append('<div class="hDivBox"></div>');
		$('div', g.hDiv).append(g.hTable);
		var thead = $("thead:first", t).get(0);
		if (thead){
			$(g.hTable).append(thead);
		}
		thead = null;
		var ci = 0;
		$('thead tr:first th', g.hDiv).each(function() {
			var thdiv = document.createElement('div');
			if ($(this).attr('sortable') && !p.scrollLoad) {
				$(this).click(function(e) {
					if (!$(this).hasClass('thOver')){
						$(this).addClass('thOver om-state-hover');
						return false;
					}
					var obj = (e.target || e.srcElement);
					if (obj.href || obj.type)
						return true;
					g._changeSort(this);
				});
				if ($(this).attr('abbr') == p.sortName) {
					this.className = 'sorted';
					thdiv.className = 's' + p.sortOrder;
				}
			}
			if (this.hidden) {
				$(this).hide();
			}
			if (!p.colModel) {
				$(this).attr('axis', 'col' + ci++);
			}
			$(thdiv).css({
				textAlign : this.align,
				height : '16px',
				width : this.width + 'px'
			});
			$(thdiv).addClass('checkboxheader');
			thdiv.innerHTML = this.innerHTML;
			$(this).empty().append(thdiv).removeAttr('width').mousedown(function(e) {
				var axis = $(this).attr('axis');
				if (axis == 'colindex' || axis == 'colcheckbox') {
					return;
				}
				g._dragStart('colMove', e, this);
			}).hover(function() {
				var axis = $(this).attr('axis');
				if (axis == 'colindex' || axis == 'colcheckbox') {
					return;
				}
				if (!g.colresize && !$(this).hasClass('thMove') && !g.colCopy) {
					$(this).addClass('thOver om-state-hover');
				}
				if ($(this).attr('abbr') != p.sortName && !g.colCopy && !g.colresize && $(this).attr('sortable')) {
					$('div', this).addClass('s' + p.sortOrder);
				} else if ($(this).attr('abbr') == p.sortName && 
						!g.colCopy && !g.colresize && $(this).attr('abbr')) {
					var no = (p.sortOrder == 'asc') ? 'desc' : 'asc';
					$('div', this).removeClass('s' + p.sortOrder).addClass('s' + no);
				}
				if (g.colCopy) {
					var n = $('th', g.hDiv).index(this);
					if (n == g.dcoln) {
						return false;
					}
					if (n < g.dcoln) {
						$(this).append(g.cdropleft);
					} else {
						$(this).append(g.cdropright);
					}
					g.dcolt = n;
				} else if (!g.colresize) {
					var nv = $('th:visible',g.hDiv).index(this);
					var onl = parseInt($('div:eq(' + nv + ')',g.cDrag).css('left'));
					var nw = jQuery(g.nBtn).outerWidth();
					var nl = onl - nw + Math .floor(p.cgwidth / 2);
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					$(g.nBtn).css({
						'left' : nl,
						top : g.hDiv.offsetTop
					}).show();
					var ndw = parseInt($(g.nDiv).width());
					$(g.nDiv).css({
						top : g.bDiv.offsetTop - 3
					});
					if ((nl + ndw) > $(g.gDiv).width()) {
						$(g.nDiv).css('left',onl - ndw + 1);
					} else {
						$(g.nDiv).css('left',nl);
					}
					if ($(this).hasClass('sorted')) {
						$(g.nBtn).addClass('srtd');
					} else {
						$(g.nBtn).removeClass('srtd');
					}
				}
			},
			function() {
				$(this).removeClass('thOver om-state-hover');
				if ($(this).attr('abbr') != p.sortName) {
					$('div', this).removeClass('s' + p.sortOrder);
				} else if ($(this).attr('abbr') == p.sortName) {
					var no = (p.sortOrder == 'asc') ? 'desc' : 'asc';
					$('div', this).addClass('s' + p.sortOrder).removeClass('s' + no);
				}
				if (g.colCopy) {
					$(g.cdropleft).remove();
					$(g.cdropright).remove();
					g.dcolt = null;
				}
			}); // wrap content
		});
		if (p.showCheckbox) {
			$('thead tr:first span.checkbox', g.hDiv).click(function() {
				$(this).toggleClass('selected');
				if ($(this).hasClass('selected')) {
					$('tbody tr', g.bDiv).filter(function() {
						return !$(this).hasClass('emptyTr');
					}).addClass('om-state-highlight');
				} else {
					$('tbody tr', g.bDiv).removeClass('om-state-highlight');
				}
				p.onRowSelect(g);
			});
		}
		// set bDiv
		g.bDiv.className = 'bDiv';
		if (p.autoLimit) {
			$(g.bDiv).css('overflow-y', 'hidden');
		}
		$(t).before(g.bDiv);
		$(g.bDiv).css({
			height : (p.height == 'auto') ? 'auto' : p.height + "px"
		}).scroll(function(e) {
			g._scroll();
		}).append(t);
		if (p.height == 'auto') {
			$('table', g.bDiv).addClass('autoht');
		}
		// add td & row properties
		g._addCellProp();
		g._addRowProp();
		// set cDrag
		var cdcol = $('thead tr:first th:first', g.hDiv).get(0);
		if (cdcol != null) {
			g.cDrag.className = 'cDrag';
			g.cdpad = 0;
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderLeftWidth'))) ? 0
					: parseInt($('div', cdcol).css('borderLeftWidth')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderRightWidth'))) ? 0
					: parseInt($('div', cdcol).css('borderRightWidth')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingLeft'))) ? 0
					: parseInt($('div', cdcol).css('paddingLeft')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingRight'))) ? 0
					: parseInt($('div', cdcol).css('paddingRight')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderLeftWidth'))) ? 0
					: parseInt($(cdcol).css('borderLeftWidth')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderRightWidth'))) ? 0
					: parseInt($(cdcol).css('borderRightWidth')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingLeft'))) ? 0
					: parseInt($(cdcol).css('paddingLeft')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingRight'))) ? 0
					: parseInt($(cdcol).css('paddingRight')));
			$(g.bDiv).before(g.cDrag);
			var cdheight = $(g.bDiv).height();
			var hdheight = $(g.hDiv).height();
			$(g.cDrag).css({
				top : -hdheight + 'px'
			});
			$('thead tr:first th', g.hDiv).each(function() {
				var cgDiv = document.createElement('div');
				$(g.cDrag).append(cgDiv);
				if (!p.cgwidth) {
					p.cgwidth = $(cgDiv).width();
				}
				$(cgDiv).css({
					height : cdheight + hdheight
				});
				if ('colcheckbox' == $(this).attr('axis')) {// checkbox列不可以调整大小
					$(cgDiv).addClass('checkboxcol');
					return;
				}
				if ('colindex' == $(this).attr('axis')) {
					$(cgDiv).addClass('indexcol');
					return;
				}
				$(cgDiv).mousedown(function(e) {
					g._dragStart('colresize', e, this);
				});
				if ($.browser.msie && $.browser.version < 7.0) {
					g._fixHeight($(g.gDiv).height());
					$(cgDiv).hover(function() {
						g._fixHeight();
						$(this).addClass('dragging');
					}, function() {
						if (!g.colresize)
							$(this).removeClass('dragging');
					});
				}
			});
		}
		// add strip
		if (p.striped) {
			$('tbody tr:odd', g.bDiv).addClass('erow');
		}
		if (p.resizable && p.height != 'auto') {
			g.vDiv.className = 'vGrip';
			$(g.vDiv).mousedown(function(e) {
				g._dragStart('vresize', e);
			}).html('<span></span>');
			$(g.bDiv).after(g.vDiv);
		}
		if (p.resizable && p.width != 'auto' && !p.nohresize) {
			g.rDiv.className = 'hGrip';
			$(g.rDiv).mousedown(function(e) {
				g._dragStart('vresize', e, true);
			}).html('<span></span>').css('height', $(g.gDiv).height());
			if ($.browser.msie && $.browser.version < 7.0) {
				$(g.rDiv).hover(function() {
					$(this).addClass('hgOver');
				}, function() {
					$(this).removeClass('hgOver');
				});
			}
			$(g.gDiv).append(g.rDiv);
		}
		// 分页工具条
		if (p.paged) {
			g.pDiv.className = 'pDiv om-state-default';
			g.pDiv.innerHTML = '<div class="pDiv2"></div>';
			$(g.bDiv).after(g.pDiv);
			var separator = '<div class="btnseparator"/>';
			var toPreGroup = '<div class="pGroup"><div class="pFirst pButton"><span class="om-icon om-icon-seek-start"></span></div><div class="pPrev pButton"><span class="om-icon om-icon-seek-prev"></span></div></div> ';
			var lazyToPreGroup = '<div class="pGroup"><div class="pPrev pButton"><span></span></div></div> ';
			var pageLinkGroup = '<div class="pGroup"><span class="pageLink"></span></div>';
			var toNextGroup = '<div class="pGroup"><div class="pNext pButton"><span class="om-icon om-icon-seek-next"></span></div><div class="pLast pButton"><span class="om-icon om-icon-seek-end"></span></div></div>';
			var lazyToNextGroup = '<div class="pGroup"><div class="pNext pButton"><span></span></div></div>';
			var pageText = p.pageText;
			pageText = pageText.replace(/{totalPage}/, '<span>1</span>');
			pageText = pageText.replace(/{index}/, '<input type="text" size="4" value="1" />');
			var jumpGroup = '<div class="pGroup"><span class="pcontrol">' + pageText + '</span></span></div>';
			var refreshGroup = '<div class="pGroup"> <div class="pReload pButton"><span class="om-icon om-icon-refresh"></span></div> </div>';
			var stateGroup = '<div class="pGroup"><span class="pPageStat"></span></div>';
			var html = [ p.lazyTotalUrl ? lazyToPreGroup : toPreGroup, separator,
					pageLinkGroup, separator,
					p.lazyTotalUrl ? lazyToNextGroup : toNextGroup, separator,
					p.lazyTotalUrl ? '' : jumpGroup, p.lazyTotalUrl ? '' : separator,
					refreshGroup, separator, stateGroup ].join('');
			$('div', g.pDiv).html(html);
			$('.pReload', g.pDiv).click(function() {
				g._populate();
			});
			$('.pFirst', g.pDiv).click(function() {
				g._changePage('first');
			});
			$('.pPrev', g.pDiv).click(function() {
				g._changePage('prev');
			});
			$('.pNext', g.pDiv).click(function() {
				g._changePage('next');
			});
			$('.pLast', g.pDiv).click(function() {
				g._changePage('last');
			});
			$('.pcontrol input', g.pDiv).keydown(function(e) {
				if (e.keyCode == 13)
					g._changePage('input');
			});
			
				$('.pButton', g.pDiv).hover(function() {
					$(this).addClass('om-state-hover');
				}, function() {
					$(this).removeClass('om-state-hover');
				});
			if (p.useRp && !p.autoLimit) {
				var opt = '', sel = '';
				for ( var nx = 0; nx < p.rpOptions.length; nx++) {
					if (p.limit == p.rpOptions[nx])
						sel = 'selected="selected"';
					else
						sel = '';
					opt += "<option value='" + p.rpOptions[nx] + "' " + sel
							+ " >每页" + p.rpOptions[nx] + "条</option>";
				}
				$('.pDiv2', g.pDiv)
						.prepend(
								"<div class='pGroup'><select name='rp'>"
										+ opt
										+ "</select></div> <div class='btnseparator'></div>");
				$('select', g.pDiv).change(function() {
					if (p.onRpChange) {
						p.onRpChange(+this.value);
					} else {
						p.newp = 1;
						p.limit = +this.value;
						g._populate();
					}
				});
			}
			// add search button
			if (p.searchItems) {
				$('.pDiv2', g.pDiv).prepend("<div class='pGroup'> <div class='pSearch pButton'><span>" +
						"</span></div> </div>  " +
						"<div class='btnseparator'></div>");
				$('.pSearch', g.pDiv).click(function() {
					$(g.sDiv).slideToggle('fast',function() {
						$('.sDiv:visible input:first', g.gDiv).trigger('focus');
					});
				});
				// add search box
				g.sDiv.className = 'sDiv om-state-header';
				var sitems = p.searchItems;
				var sopt = '', sel = '';
				for ( var s = 0; s < sitems.length; s++) {
					if (p.qtype == '' && sitems[s].isdefault == true) {
						p.qtype = sitems[s].name;
						sel = 'selected="selected"';
					} else {
						sel = '';
					}
					sopt += "<option value='" + sitems[s].name + "' " + sel
							+ " >" + sitems[s].display
							+ "&nbsp;&nbsp;</option>";
				}
				if (p.qtype == '') {
					p.qtype = sitems[0].name;
				}
				$(g.sDiv).append(
						"<div class='sDiv2'>" + p.findtext
								+ " <input type='text' value='" + p.query
								+ "' size='30' name='q' class='qsbox' /> "
								+ " <select name='qtype'>" + sopt
								+ "</select></div>");
				// Split into separate selectors because of bug in jQuery 1.3.2
				$('input[name=q]', g.sDiv).keydown(function(e) {
					if (e.keyCode == 13) {
						g._doSearch();
					}
				});
				$('select[name=qtype]', g.sDiv).keydown(function(e) {
					if (e.keyCode == 13) {
						g._doSearch();
					}
				});
				$('input[value=Clear]', g.sDiv).click(function() {
					$('input[name=q]', g.sDiv).val('');
					p.query = '';
					g._doSearch();
				});
				$(g.bDiv).after(g.sDiv);
			}
		}
		$(g.pDiv, g.sDiv).append("<div style='clear:both'></div>");
		// add title
		if (p.title) {
			g.mDiv.className = 'mDiv om-widget-header';
			g.mDiv.innerHTML = '<div class="ftitle">' + p.title + '</div>';
			$(g.gDiv).prepend(g.mDiv);
			if (p.showTableToggleBtn) {
				$(g.mDiv)
						.append(
								'<div class="ptogtitle" title="Minimize/Maximize Table"><span></span></div>');
				$('div.ptogtitle', g.mDiv).click(function() {
					$(g.gDiv).toggleClass('hideBody');
					$(this).toggleClass('vsble');
				});
			}
		}
		// setup cdrops
		g.cdropleft = document.createElement('span');
		g.cdropleft.className = 'cdropleft';
		g.cdropright = document.createElement('span');
		g.cdropright.className = 'cdropright';
		// add block
		g.block.className = 'gBlock';
		var gh = $(g.bDiv).height();

		$(g.block).append('<div align="center" />');
		$("div", g.block).css('margin-top', gh / 2);
		$("div", g.block).append('<img class="loadingImg" />');
		$("img", g.block).css("display","block");
		
		var gtop = g.bDiv.offsetTop;
		$(g.block).css({
			width : g.bDiv.style.width,
			height : gh,
			background : 'white',
			//marginBottom : (gh * -1),
			zIndex : 1, 
			//top : gtop,
			left : '0px'
		});
		$(g.block).fadeTo(0, p.blockOpacity);
		// add column control
		if ($('th', g.hDiv).length) {
			g.nDiv.className = 'nDiv';
			g.nDiv.innerHTML = "<table cellpadding='0' cellspacing='0'><tbody></tbody></table>";
			$(g.nDiv).css({
				marginBottom : (gh * -1),
				display : 'none',
				top : gtop - 3
			}).noSelect();
			var cn = 0;
			$('th div', g.hDiv).each(function() {
				var axis = $(this).parent().attr('axis');
				if ('colcheckbox' == axis || 'colindex' == axis) {
					return;
				}
				var kcol = $("th[axis='col" + cn + "']", g.hDiv)[0];
				if (typeof (kcol) == 'undefined') {
					return;
				}
				var chk = 'checked="checked"';
				if (kcol.style.display == 'none') {
					chk = '';
				}
				$('tbody', g.nDiv).append(
						'<tr><td class="ndcol1"><input type="checkbox" '
						+ chk + ' class="togCol" value="'
						+ (cn++)
						+ '" /></td><td class="ndcol2">'
						+ this.innerHTML + '</td></tr>'
				);
			});
			if ($.browser.msie && $.browser.version < 7.0)
				$('tr', g.nDiv).hover(function() {
					$(this).addClass('ndcolover');
				}, function() {
					$(this).removeClass('ndcolover');
				});
			$('td.ndcol2', g.nDiv).click(function() {
				if ($('input:checked', g.nDiv).length <= p.minColToggle
						&& $(this).prev().find('input')[0].checked)
					return false;
				return g._toggleCol($(this).prev().find('input').val());
			});
			$('input.togCol', g.nDiv).click(function() {
				if ($('input:checked', g.nDiv).length < p.minColToggle
						&& this.checked == false)
					return false;
				$(this).parent().next().trigger('click');
			});
			$(g.gDiv).prepend(g.nDiv);
			$(g.nBtn).addClass('nBtn').html('<div></div>').attr('title', $.omGrid.lang.showColumnsMsg).click(function() {
				$(g.nDiv).toggle();
				return true;
			});
			if (p.showToggleBtn) {
				$(g.gDiv).prepend(g.nBtn);
			}
		}
		// add date edit layer
		$(g.iDiv).addClass('iDiv').css({
			display : 'none'
		});
		$(g.bDiv).append(g.iDiv);
		// add omGrid events
		$(g.bDiv).hover(function() {
			$(g.nDiv).hide();
			$(g.nBtn).hide();
		}, function() {
			if (g.multisel) {
				g.multisel = false;
			}
		});
		$(g.gDiv).hover(function() {
		}, function() {
			$(g.nDiv).hide();
			$(g.nBtn).hide();
		});
		// add document events
		$(document).mousemove(function(e) {
			g._dragMove(e);
		}).mouseup(function(e) {
			g._dragEnd();
		}).hover(function() {
		}, function() {
			g._dragEnd();
		});
		// browser adjustments
		if ($.browser.msie && $.browser.version < 7.0) {
			$('.hDiv,.bDiv,.mDiv,.pDiv,.vGrip,.tDiv, .sDiv', g.gDiv).css({
				width : '100%'
			});
			$(g.gDiv).addClass('ie6');
			if (p.width != 'auto') {
				$(g.gDiv).addClass('ie6fullwidthbug');
			}
		}
		g._rePosDrag();
		g._fixHeight();
		// make grid functions accessible
		t.p = p;
		t.grid = g;
		// load data
		if (p.url && p.autoLoad) {
			g._populate();
		}
		return t;
	};
	var docloaded = false;
	$(document).ready(function() {
		docloaded = true;
	});
	
	var publicMethods = {
	     //reload the data from server
	    /**
	     * 重新从远程加载表格数据
	     * @name omGrid#reload
	     * @function
	     * @returns jQuery对象
	     * @example
	     * //根据现有的url重新加载远程数据
	     * $('.selector).omGrid('reload');  //注意reload没有传入参数
	     */
		reload : function(){
		    var self = this[0];
		    if (!self.grid) {
                throw '表格尚未初始化.';
                return this;
            }
		    self.grid._populate();
		    return this;
		},
		//reload the data in client
		/**
		 * 根据当前grid数据模型中的数据刷新grid
		 * @name omGrid#refresh
		 * @function
		 * @returns jQuery对象
		 * @example
		 * //根据当前grid数据模型中的数据，重新刷新grid
		 * $('.selector').omGrid('refresh');//注意refresh没有传入参数
		 * 
		 */
		refresh : function(){
		    var self = this[0];
		    if (!self.grid) {
                throw '表格尚未初始化.';
                return this;
            }
		    if ($(self.grid.block)) {
		        $(self.grid.block).css({
		            top : 0,
		            left : 0
		        });
		        $(self).closest('.om-grid').append(self.grid.block);
		    }
		    self.grid._addData(self.grid.data);//调用addData
		    return this;
		},
		repaint : function(){
		    //null
		    return this;
		},
		//add new properties to config options
		/**
		 * 更新表格的配置属性.该方法除了更新配置属性外不会有更多操作(比如更新url后不会触发从服务器端取数的操作)
		 * @name omGrid#options
		 * @function
		 * @param p 新的配置对象,为JSON格式.该对象会覆盖初始化配置对象的相关属性
		 * @returns jQuery对象
		 * @example
		 * //更换grid的url属性.然后显示调用reload方法更新表格数据.
		 * $('.selector').omGrid('options', {
		 *     url : 'newgriddata.do?'
		 * });
		 * $('.selector').omGrid('reload');
		 */
		options : function(p){
		    var self = this[0];
		    if (!self.grid || !self.p) {
                throw '表格尚未初始化.';
                return this;
            }
		    $.extend(self.p, p);
		    return this;
		},
		//toggle column's visibility
		/**
		 * 设置某一列的隐藏状态
		 * @name omGrid#toggleCol
		 * @function
		 * @param cid 表格的列id(从0开始计数)
		 * @param visible 显示的状态 true为显示 false为隐藏
		 * @returns jQuery对象
		 * @example
		 * $('.selector').omGrid('toggleCol', 3, false);//将第四列隐藏
		 */
		toggleCol : function(cid, visible){
		    var self = this[0];
		    if (!self.grid) {
                throw '表格尚未初始化.';
                return this;
            }
		    self.grid._toggleCol(cid, visible);
		    return this;
		},
		//replace grid with new datas,datas is an array
		/**
		 * 设置表格数据,既可以是本地数据,也可以是远程数据.通过该方法grid会立即刷新数据.
		 * @name omGrid#setData
		 * @function
		 * @param datas 一个url或者一个JSON数组
		 * @returns jQuery对象
		 * @example
		 * //通过一个固定的JSON数组来重新加载grid
		 * $('.selector').omGrid('setData',[
         *      {"address":"CZ88.NET ","city":"IANA保留地址","end":"0.255.255.255","id":"1","start":"0.0.0.0"},
         *      {"address":"CZ88.NET ","city":"澳大利亚","end":"1.0.0.255","id":"2","start":"1.0.0.0"},
         *      {"address":"电信 ","city":"福建省","end":"1.0.3.255","id":"3","start":"1.0.1.0"},
         *      {"address":"CZ88.NET ","city":"泰国","end":"1.0.255.255","id":"9","start":"1.0.128.0"},
         *      {"address":"CZ88.NET ","city":"日本","end":"1.0.31.255","id":"6","start":"1.0.16.0"},
         *      {"address":"电信 ","city":"广东省","end":"1.0.63.255","id":"7","start":"1.0.32.0"},
         *      {"address":"CZ88.NET ","city":"澳大利亚","end":"1.0.7.255","id":"4","start":"1.0.4.0"},
         *      {"address":"CZ88.NET ","city":"日本","end":"1.0.127.255","id":"8","start":"1.0.64.0"}
         *  ]);
         *  //通过一个url来发送Ajax请求重新加载combo的下拉列表
         *  $('.selector').omGrid('setData', 'newgriddata.do?method=fast');
		 */
		setData : function(datas){
		    var self = this[0];
		    if (!self.grid) {
		        throw '表格尚未初始化.';
		        return this;
		    }
		    if (typeof(datas) === 'string') {
		        self.p.url = datas;
		        self.grid.reload();
		    } else {
		        self.grid.data.total = datas.length;
		        self.grid.data.page = datas.page ? datas.page : 1;
		        self.grid.data.rows = datas;
		        self.grid._addData(self.grid.data);
		    }
		    return this;
		},
		//get
		/**
         * 获取grid的数据源,返回一个JSON对象datas.
         * 该datas对象的total属性代表了总记录数,注意远程数据(根据url取得)和本地数据源(客户端数组)的总记录数是不一样的;
         * 该datas对象的page属性代表了当前页是第几页.
         * 该data对象的rows属性对应一个JSON数组.其代表了当前页所有的数据,而不是所有页的数据,这点区别只存在于远程数据的情况.
         * @name omGrid#getData
         * @function
         * @returns 如果grid中有数据,则返回grid的数据源(一个JSON对象,由当前页所有记录组成的JSON数组),当存在多页数据时,该方法只返回当前页的数据.如果grid中没有数据,则直接返回undefined
         * @example
         * //获取grid的数据源
         * var store = $('.selector').omGrid('getData');
         * 
         * 
         */
		getData : function() {
		    var self = this[0];
		    if (!self.grid) {
		        throw '表格尚未初始化.';
                return this;
            }
		    if (self.grid.data){
		        return self.grid.data;
		    }
		    return this;
		},
		//append new datas to grid, datas is an array
		/**
		 * 将本地数据拼接到grid末尾.不支持远程数据的拼接
		 * @name omGrid#appendData
		 * @function
		 * @param datas 一个JSON格式的数组,即将被拼接到grid末尾的数据
		 * @returns jQuery对象
		 * @example
		 * //将数据拼接到grid末尾
		 * $('.selector').omGrid('setData',[
         *      {"address":"天朝 ","city":"京都","end":"1.0.63.255","id":"7","start":"1.0.32.0"},
         *      {"address":"天朝 ","city":"中原","end":"1.0.7.255","id":"4","start":"1.0.4.0"},
         *      {"address":"CZ88.NET ","city":"日本","end":"1.0.127.255","id":"8","start":"1.0.64.0"}
         *  ]);
		 * 
		 */
		appendData : function(datas){
		    var self = this[0];
		    if (!self.grid) {
                throw '表格尚未初始化.';
                return this;
            }
		    self.grid.data.total += datas.length;
		    self.grid.data.rows = self.grid.data.rows.concat(datas);
		    self.grid._addData(self.grid.data);
		    return this;
		},
		/**
		 * 返回表格中被选中行的data数据组成的数组.即一个JSON数组.
		 * @name omGrid#getSelections
		 * @function
		 * @returns 选中行的data数组(JSON数组)
		 * @example 
		 * //取出选中行的data数组
         * $('.selector').omGrid('getSelections');
		 */
		getSelections : function() {
		    var self = this[0];
		    if (!self.grid) {
		        throw '表格尚未初始化.';
                return this;
		    }
		    return self.grid._getSelections();
		}, 
		/**
		 * 根据colId指定表格的列,根据valArray指定的该列的值数组,去选中表格中相关的行.
		 * @name omGrid#setSelections
		 * @function
		 * @param valArray 表格某一列的值数组,类型为Array,如果只有一个值,也可以直接写一个String.
		 * @param colId  表格的列id,如果不设置,默认是字符串'id'
		 * @returns jQuery对象
		 * @example
		 * //将表格address列中,值为'CZ88.NET'的行设置为选中状态
         * $('.selector').omGrid('setSelections', ['CZ88.NET'], 'address');
         *                            
		 */
		setSelections : function(valArray, colId) {
		    var self = this[0];
            if (!self.grid) {
                throw '表格尚未初始化.';
                return this;
            }
            self.grid._setSelections(valArray, colId ? colId : 'id');
            return this;
		}
	};
	
	$.fn.omGrid = function(p) {
		if (p && typeof(p) == 'string') {
			if (publicMethods[p]) {
			    try {
			        return publicMethods[p].apply(this, Array.prototype.slice.call(arguments, 1));
			    } catch (e) {
			        var emsg = e + ' cannot invoke the methd:' + p;
			        throw emsg;
			    }
			}
			return null;
		}
		
		return this.each(function() {
			if (!docloaded) {
				$(this).hide();
				var t = this;
				$(document).ready(function() {
					$.addFlex(t, p);
				});
			} else {
				$.addFlex(this, p);
			}
		});
	}; // end omGrid
	$.extend($,{omGrid:{}});
    $.omGrid.lang={
            /**
             * 显示列按钮的提示信息.
             * @type String
             * @default '显示列'
             */
            showColumnsMsg : '显示列'
    };
//	$.fn.flexReload = function(p) { // function to reload grid
//		return this.each(function() {
//			if (this.grid && this.p.url)
//				this.grid._populate();
//		});
//	}; // end flexReload
//	$.fn.flexOptions = function(p) { // function to update general options
//		return this.each(function() {
//			if (this.grid)
//				$.extend(this.p, p);
//		});
//	}; // end flexOptions
//	$.fn.flexToggleCol = function(cid, visible) { // function to reload grid
//		return this.each(function() {
//			if (this.grid)
//				this.grid._toggleCol(cid, visible);
//		});
//	}; // end flexToggleCol
//	$.fn.flexAddData = function(data) { // function to add data to grid
//		return this.each(function() {
//			if (this.grid)
//				this.grid._addData(data);
//		});
//	};
	$.fn.noSelect = function(p) { // no select plugin by me :-)
		var prevent = (p == null) ? true : p;
		if (prevent) {
			return this.each(function() {
				if ($.browser.msie || $.browser.safari)
					$(this).bind('selectstart', function() {
						return false;
					});
				else if ($.browser.mozilla) {
					$(this).css('MozUserSelect', 'none');
					$('body').trigger('focus');
				} else if ($.browser.opera)
					$(this).bind('mousedown', function() {
						return false;
					});
				else
					$(this).attr('unselectable', 'on');
			});
		} else {
			return this.each(function() {
				if ($.browser.msie || $.browser.safari)
					$(this).unbind('selectstart');
				else if ($.browser.mozilla)
					$(this).css('MozUserSelect', 'inherit');
				else if ($.browser.opera)
					$(this).unbind('mousedown');
				else
					$(this).removeAttr('unselectable', 'on');
			});
		}
	}; // end noSelect
})(jQuery);/*
 * operamasks-ui ${file_name} 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 *  jquery.ui.mouse.js
 *  jquery.ui.draggable.js
 *  jquery.ui.position.js
 */
 
(function( $, undefined ) {
	 var tmpl = '<div class="om-messageBox om-widget om-widget-content om-corner-all" tabindex="-1">'+
	                '<div class="om-messageBox-titlebar om-widget-header om-corner-all om-helper-clearfix">'+
	                    '<span class="om-messageBox-title"></span>'+
	                    '<a href="#" class="om-messageBox-titlebar-close om-corner-all"><span class="om-icon om-icon-closethick"></span></a>' +
	                '</div>'+
	                '<div class="om-messageBox-content om-widget-content">'+
	                    '<table><tr vailgn="top">' +
	                        '<td class="om-messageBox-imageTd"><div class="om-messageBox-image"/>&nbsp;</td>' +
	                        '<td class="om-message-content-html"></td>' +
	                    '</tr></table>'+
	                '</div>'+
	                '<div class="om-messageBox-buttonpane om-widget-content om-helper-clearfix">'+
	                    '<div class="om-messageBox-buttonset"></div>'+
	                '</div>'+
	            '</div>';
	var close = function(messageBox, mask, handler, value){
	    if (messageBox.hasClass('om-messageBox-waiting')) {
	        return;
	    }
	    handler ? handler(value) : jQuery.noop();
	    messageBox.remove();
	    mask.remove();
	};
    var _show = function(config){
        var onClose = config.onClose;
        var messageBox = $(tmpl).appendTo(document.body).css('z-index', 9999).position({
            of:window,
            collision: 'fit'
        }).draggable({
            containment: 'document',
            handle: '.om-messageBox-titlebar'
        }).hide().keydown(function(event){
            if (event.keyCode && event.keyCode === $.ui.keyCode.ESCAPE) {
                close(messageBox, mask, null, false);
                event.preventDefault();
            }
        });
        var mask = $('<div class="om-widget-overlay"/>').appendTo(document.body).show().height(Math.max($(this).height(), $(document.body).height()));
        messageBox.find('span.om-messageBox-title').html(config.title).next().hover(function(){
            $(this).addClass('om-state-hover');
        }, function(){
            $(this).removeClass('om-state-hover');
        }).focus(function(){
            $(this).addClass('om-state-focus');
        }).blur(function(){
            $(this).removeClass('om-state-focus');
        }).click(function(event){
            close(messageBox, mask, null, false);
            return false;
        });
        messageBox.find('div.om-messageBox-image').addClass('om-messageBox-image-' + config.type);
        var content = config.content;
        if (config.type == 'prompt') {
            content = content || '';
            content += '<input id="om-messageBox-prompt-input" type="text"/>';
        }
        messageBox.find('td.om-message-content-html').html(content);
        var buttonSet = messageBox.find('div.om-messageBox-buttonset');
        switch (config.type) {
            case 'confirm':
                buttonSet.html('<button>确定</button><button>取消</button>').children().first().click(function(){
                    close(messageBox, mask, onClose, true);
                }).next().click(function(){
                    close(messageBox, mask, onClose, false);
                });
                break;
            case 'prompt':
                buttonSet.html('<button>确定</button><button>取消</button>').children().first().click(function(){
                    var returnValue = onClose ? onClose($('#om-messageBox-prompt-input').val()) : jQuery.noop();
                    if (returnValue !== false) {
                        messageBox.remove();
                        mask.remove();
                    }
                }).next().click(function(){
                    close(messageBox, mask, onClose, false);
                });
                break;
            case 'waiting':
                messageBox.addClass('om-messageBox-waiting');
                mask.addClass('om-messageBox-waiting');
                break;
            default:
                buttonSet.html('<button>确定</button>').children().first().click(function(){
                    close(messageBox, mask, onClose, true);
                });
        }
        messageBox.show().focus();
    };
     /**
      * @name omMessageBox
      * @author 周发志
      * @class
      * omMessageBox用于提供弹出窗口提示，类似于JavaScript中使用alert()、confirm()、prompt()函数时出现的那种弹出提示窗。<br/><br/>
      * <br/>
      * <h2>有以下特点：</h2><br/>
      * <ul>
      *     <li>有较好的浏览器兼容性</li>
      *     <li>可以定义标题、内容，标题和内容可以使用html代码</li>
      *     <li>标题栏有关闭按钮，也可以按Esc键关闭</li>
      *     <li>支持丰富的提示（图标不同）</li>
      *     <li>可以监听关闭事件</li>
      * </ul>
      * <br/>
      * <h2>提供了以下工具方法：</h2><br/>
      * <ul>
      *     <li>
      *         <b>$.omMessageBox.alert(config)</b><br/>
      *         弹出一个Alert提示，仅有一个“确定”按钮。其中config有以下配置项：<br/>
      *         <ul style="margin-left:40px">
      *             <li>type：alert提示的类型，类型不同时弹出窗口左边的图标会不同。String类型，可选的值有'alert'、'success'、'error'、'question'、'warning'。默认值为'alert'。</li>
      *             <li>title：弹出窗口的标题文字，String类型，可以使用普通字符串，也可以使用html代码。默认值为'提示'。</li>
      *             <li>content：弹出窗口的提示内容，String类型，可以使用普通字符串，也可以使用html代码。无默认值。</li>
      *             <li>onClose：弹出窗口关闭时的回调函数，Function类型，点击"确定"按钮来关闭弹出窗口时，Function的参数value值为true，按ESC键关闭弹出窗口时，Function的参数value值为false。无默认值。</li>
      *         </ul>
      *         <br/>使用方式如下：<br/>
      *         <pre>
      *             $.omMessageBox.alert({
      *                 type:'error',
      *                 title:'失败',
      *                 content:'不能删除&lt;font color="red">admin&lt;/font>用户',
      *                 onClose:function(value){
      *                     alert('do something');
      *                 }
      *             });
      *         </pre>
      *     </li>
      *     <li>
      *         <b>$.omMessageBox.confirm(config)</b><br/>
      *         弹出一个Confirm提示，有“确定”和“取消”按钮。其中config有以下配置项：<br/>
      *         <ul style="margin-left:40px">
      *             <li>title：弹出窗口的标题文字，String类型，可以使用普通字符串，也可以使用html代码。默认值为'确认'。</li>
      *             <li>content：弹出窗口的提示内容，String类型，可以使用普通字符串，也可以使用html代码。无默认值。</li>
      *             <li>onClose：弹出窗口关闭时的回调函数，Function类型，点击"确定"按钮来关闭弹出窗口时，Function的参数value值为true，点击“取消”按钮或按ESC键关闭弹出窗口时，Function的参数value值为false。无默认值。</li>
      *         </ul>
      *         <br/>使用方式如下：<br/>
      *         <pre>
      *             $.omMessageBox.confirm({
      *                 title:'确认删除',
      *                 content:'删除用户后，它所有的发帖和回帖将同时删除（不可恢复），你确定要这样做吗？',
      *                 onClose:function(value){
      *                     alert(value?'开始删除操作':'不删除了');
      *                 }
      *             });
      *         </pre>
      *     </li>
      *     <li>
      *         <b>$.omMessageBox.prompt(config)</b><br/>
      *         弹出一个Prompt提示，有一个输入框和“确定”和“取消”按钮。其中config有以下配置项：
      *         <ul style="margin-left:40px">
      *             <li>title：弹出窗口的标题文字，String类型，可以使用普通字符串，也可以使用html代码。默认值为'请输入'。</li>
      *             <li>content：弹出窗口的提示内容，String类型，可以使用普通字符串，也可以使用html代码。无默认值。</li>
      *             <li>onClose：弹出窗口关闭时的回调函数，Function类型，点击"确定"按钮来关闭弹出窗口时，Function的参数value值为用户在输入框里输入的字符串（一定是字符串），点击“取消”按钮或按ESC键关闭弹出窗口时，Function的参数value值为false。无默认值。<b>注意：在此方法中返回false将会阻止弹出窗口关闭。</b></li>
      *         </ul>
      *         <br/>使用方式如下：<br/>
      *         <pre>
      *             $.omMessageBox.prompt({
      *                 title:'商品数量',
      *                 content:'请输入你要购买的商品的数量（你的余额最多只能购买12千克）：',
      *                 onClose:function(value){
      *                     if(value===false){ //按了取消或ESC
      *                         alert('取消购买');
      *                         return;
      *                     }
      *                     if(value==''){
      *                         alert('数量不能为空');
      *                         return false; //不关闭弹出窗口
      *                     }
      *                     if(value-0+'' !== value){
      *                         alert('只能输入数字');
      *                         return false;  //不关闭弹出窗口
      *     `               }
      *                     if(value&lt;0 || value&gt;12){
      *                         alert('请输入0-12之间的数字（可带小数）');
      *                         return false; //不关闭弹出窗口
      *                     }else{
      *                         alert('开始购买'+value+'千克商品');
      *                     }
      *                 }
      *             });
      *         </pre>
      *     </li>
      *     <li>
      *         <b>$.omMessageBox.waiting(config | 'close')</b><br/>
      *         弹出一个Prompt提示，有一个输入框和“确定”和“取消”按钮。该提示窗口没有关闭按钮，也不可以按ESC关闭。如果参数是'close'时表示关闭上次弹出的Waiting提示窗口。如果是config时表示要弹出一个Waiting提示窗口，其中config有以下配置项：
      *         <ul style="margin-left:40px">
      *             <li>title：弹出窗口的标题文字，String类型，可以使用普通字符串，也可以使用html代码。默认值为'请稍候'。</li>
      *             <li>content：弹出窗口的提示内容，String类型，可以使用普通字符串，也可以使用html代码。无默认值。</li>
      *         </ul>
      *         <br/>使用方式如下：<br/>
      *         <pre>
      *             //弹出提示
      *             $.omMessageBox.waiting({
      *                 title:'请稍候',
      *                 content:'服务器正在处理您的请求，请稍候...',
      *             });
      * 
      *             //关闭提示
      *             $.omMessageBox.waiting('close');
      *         </pre>
      *     </li>
      * </ul>
      */
    $.omMessageBox = {
        alert: function(config){
            config.title = config.title || '提示';
            config.type = config.type || 'alert';
            _show(config);
        },
        confirm: function(config){
            config.title = config.title || '确认';
            config.type = 'confirm';
            _show(config);
        },
        prompt: function(config){
            config.title = config.title || '请输入';
            config.type = 'prompt';
            _show(config);
        },
        waiting: function(config){
            if (config === 'close') {
                $('.om-messageBox-waiting').remove();
                return;
            }
            config.title = config.title || '请等待';
            config.type = 'waiting';
            _show(config);
        }
    };
}(jQuery));/*
 * operamasks-ui omNumberField 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 */
(function($) {
    
    // 设置小数精度
    var fixPrecision = function(value, e, p) {
        var v = value.indexOf(".");
        if (p.decimalPrecision == -1 || value == "." || !value ||(value == "-" && p.allowNegative&& !p.readOnly) 
                || v == -1 && !isNaN(value)) {
            return value;
        }
        if (isNaN(value)) {
            for (; isNaN(value);) {
                value = value.substring(0, value.length - 1);
                if (value == "." || value == "-") {
                    break;
                }
            }
            return value;
        }
        if((v==value.length-1)&&!p.allowDecimals){
            value = value.substring(0, value.length - 1);
         }
        value=value.substring(0,value.indexOf(".")+p.decimalPrecision+1);
        return value;
        
    };

    /** 
     * @name omNumberField
     * @author 王璠
     * @class 数字输入框组件，只能输入数字，字符自动过滤掉.<br/>
     * @constructor
     * @description 构造函数. 
     * @param p 标准config对象：{}
     * @example
     * $('numberFielddiv').omNumberField({decimalPrecision:3});
     */
    $.widget("om.omNumberField", {
        options: /** @lends omNumberField.prototype */ 
        {
            /**
             * 是否允许输入小数.
             * @default true
             * @type Boolean
             * @example
             * $('#input').omNumberField({allowDecimals:true});
             */
            allowDecimals: true,  //是否允许输入小数
            /**
             * 是否允许输入负数.
             * @default true
             * @type Boolean
             * @example
             * $('#input').omNumberField({allowNegative:true});
             */
            allowNegative: true,  //是否允许输入负数
            /**
             * 精确到小数点后几位.
             * @default 2
             * @type Number
             * @example
             * $('#input').omNumberField({decimalPrecision:2});
             */
            decimalPrecision: 2, //精确到小数点后几位
            /**
             * 是否可用.
             * @default false
             * @type Boolean
             * @example
             * $('#input').omNumberField({disabled:true});
             */
            disabled: false,
            /**
             * 是否只读.
             * @default false
             * @type Boolean
             * @example
             * $('#input').omNumberField({readOnly:true});
             */
            readOnly: false            
        },

        _create : function() {
            // 允许输入的字符
            var options = this.options;
            this.element.addClass('om-state-default om-state-nobg');

            if (typeof options.disabled !== "boolean") {
                this.options.disabled = this.element.prop("disabled");
            }

            if (typeof options.readOnly !== "boolean") {
                this.options.readOnly = this.element.attr("readOnly");
            }

            if (this.element.is(":disabled")) {
                this.options.disabled = true;
            }
            this.element.bind('keypress',function(e) {
                if (e.which == null && (e.charCode != null || e.keyCode != null)) {
                    e.which = e.charCode != null ? e.charCode : e.keyCode;
                }
                var k = e.which;
                if (k === 8 || (k == 46 && e.button == -1) || k === 0) {
                    return;
                }

                var character = String.fromCharCode(k);
                var value = $(this).val()+character;
                var allowed = $.data(this, "allowed");
                if (allowed.indexOf(character) === -1
                        || ($(this).val().indexOf(".") !== -1 && character == ".")||character=="-"&&value!="-") {
                    e.preventDefault();
                }
                
                var v = value.indexOf(".");
                if (options.decimalPrecision == -1 || !value ||value == "."||
                        (value == "-" && options.allowNegative) || v == -1 && !isNaN(value)) {
                    return ;
                }
                var index = value.indexOf(".")+options.decimalPrecision+1;
                if(isNaN(value)||index < value.length){
                    e.preventDefault();
                }
                
            }).bind('keyup', function(e) {
               $(this).val(fixPrecision($(this).val(), e, options));
            }).bind('focus',function(){
               $(this).addClass('om-state-focus');
            }).bind('blur',function(){
               $(this).removeClass('om-state-focus');
            });

            this._setOption("disabled", options.disabled);
        },

        _setOption : function(key, value) {
            $.Widget.prototype._setOption.apply(this, arguments);
            this._buildAllowChars();
            if (key === "disabled") {
                if (value) {
                    this.element.prop("disabled", true);
                } else {
                    this.element.prop("disabled", false);
                }
                return;
            }
        },

        _buildAllowChars : function() {
            var allowed = "0123456789";

            // 允许输入的字符
            if (this.options.allowDecimals) {
                allowed = allowed + ".";
            }
            if (this.options.allowNegative) {
                allowed = allowed + "-";
            }
            if (this.options.readOnly) {
                allowed = "";
            }
            $.data(this.element[0], "allowed", allowed);
        }
        /**
         * 禁用组件
         * @name omNumberField#disable
         * @function
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('#input').omNumberField("disable")
         */

        /**
         * 启用组件
         * @name omNumberField#enable
         * @function
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('#input').omNumberField("enable")
         */
    });
})(jQuery);/*
 * operamasks-ui omProgressbar 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 */
(function( $, undefined ) {

$.widget( "om.omProgressbar", {
	options: {
		value: 0,
		max: 100
	},

	min: 0,

	_create: function() {
		this.element
			.addClass( "om-progressbar om-widget om-widget-content om-corner-all" )
			.attr({
				role: "progressbar",
				"aria-valuemin": this.min,
				"aria-valuemax": this.options.max,
				"aria-valuenow": this._value()
			});

		this.valueDiv = $( "<div class='om-progressbar-value om-widget-header om-corner-left'></div>" )
			.appendTo( this.element );

		this.oldValue = this._value();
		this._refreshValue();
	},

	destroy: function() {
		this.element
			.removeClass( "om-progressbar om-widget om-widget-content om-corner-all" )
			.removeAttr( "role" )
			.removeAttr( "aria-valuemin" )
			.removeAttr( "aria-valuemax" )
			.removeAttr( "aria-valuenow" );

		this.valueDiv.remove();

		$.Widget.prototype.destroy.apply( this, arguments );
	},

	value: function( newValue ) {
		if ( newValue === undefined ) {
			return this._value();
		}

		this._setOption( "value", newValue );
		return this;
	},

	_setOption: function( key, value ) {
		if ( key === "value" ) {
			this.options.value = value;
			this._refreshValue();
			if ( this._value() === this.options.max ) {
				this._trigger( "complete" );
			}
		}

		$.Widget.prototype._setOption.apply( this, arguments );
	},

	_value: function() {
		var val = this.options.value;
		// normalize invalid value
		if ( typeof val !== "number" ) {
			val = 0;
		}
		return Math.min( this.options.max, Math.max( this.min, val ) );
	},

	_percentage: function() {
		return 100 * this._value() / this.options.max;
	},

	_refreshValue: function() {
		var value = this.value();
		var percentage = this._percentage();

		if ( this.oldValue !== value ) {
			this.oldValue = value;
			this._trigger( "change" );
		}

		this.valueDiv
			.toggle( value > this.min )
			.toggleClass( "om-corner-right", value === this.options.max )
			.width( percentage.toFixed(0) + "%" );
		this.element.attr( "aria-valuenow", value );
	}
});

$.extend( $.om.omProgressbar, {
	version: "0.0.7"
});

})( jQuery );
/*
 * operamasks-ui ${file_name} 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 */
;(function($){
    var suggestionRowClass='om-suggestion-list-row';
    var suggestionHighLightClass='om-state-hover';
    /**
     * @name omSuggestion
     * @author 周发志
     * @class 
     * &nbsp;&nbsp;&nbsp;&nbsp;Ajax提示组件。类似于google首页的搜索功能（在输入的同时下拉框里给出可用的提示，用户可以从里面选择一个）。<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;将该功能添加到一个input输入框上，将允许用户在输入的同时可以快速地查找和选择所要的内容。当输入框得到焦点并输入字符时，该组件会将用户输入的内容以Ajax方式发送到服务器进行处理，服务器处理完后返回一个数据集，客户端将数据集显示成一个可选列表，用户可以从可选列表中很方便地选择自己所要查找的东西。<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;目前该组件主要用于从远程URL取得数据（如果是本地数据的话，可以使用omCombo组件，它也有边输入边过滤的功能）。一般用于从大量数据中进行查找的场合，如百度搜索、google搜索、taobao商品搜索、邮件系统快速输入收件人。<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;该组件有客户端缓存的功能，如输入a开始Ajax查找；再输入b（输入框内容是ab）再次Ajax查找；再删除b（输入框内容是a）将不进行Ajax查找，因为缓存中已经有key=a的缓存内容，将直接根据缓存内容来重构可选列表而不发送Ajax请求从服务器取数。如果不需要缓存可以将cacheSize参数设成0。如果要清除缓存可以调用clearCache()方法。<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;实际应用中一般都要控制可选列表框中记录的数目（如百度搜索、google搜索、taobao商品搜索的可选列表中记录数都为10，有道搜索的可选列表中记录数为8），这个要由服务器进行控制，服务器返回数据时请不要返回得太多（比如从数据库中查询时一般使用TOP-N查询）。<br/><br/>
     * <b>特点：</b><br/>
     * <ol>
     *      <li>可以使用普通数组，也可使用JSON数组</li>
     *      <li>支持鼠标操作和键盘操作</li>
     *      <li>支持数据的客户端缓存</li>
     *      <li>提供丰富的事件</li>
     *      <li>用户可定制数据的显示效果</li>
     *      <li>用户可定制请求的发送与处理</li>
     *      <li>支持跨域请求数据</li>
     * </ol><br/>
     * <b>示例：</b><br/>
     * <pre>
     * &lt;script type="text/javascript" >
     * $(document).ready(function() {
     *     $('#input1').omSuggestion({
     *         dataSource:'/suggestion.json',
     *         minChars :3,
     *         listMaxHeight:40
     *     });
     * });
     * &lt;/script>
     * 
     * &lt;input id="input1"/>
     * </pre>
     * @constructor
     * @description 构造函数. 
     * @param p 标准config对象：
     */
    $.widget('om.omSuggestion', {
        options:/** @lends omSuggestion#*/{
            /**
             * 是否禁用该组件。如果禁用，则不可以输入，form提交时也将忽略这个输入框。
             * @type Boolean
             * @default false
             */
            disabled : false,
            /**
             * 组件是否只读。如果是只读，则不可以输入，form提交时将会包含这个输入框。
             * @type Boolean
             * @default false
             */
            readOnly : false,
            /**
             * 发送Suggestion的最小字符数。如果设置成3，则当输入框里的内容长度小于3时不会进行输入提示，大于或等于3时才会进行提示。比如对于电话号码提示时可能会设置成2，当输入1时不提示，输入13时才开始提示。<br/>
             * 该值必须为非负整数，当设成负数时效果赞同于设成1。如果要页面一显示完就开始提示，可以设成0。
             * @type Number
             * @default 1
             */
            minChars : 1,
            /**
             * 发送请求的延迟时间（单位是毫秒）。比如设成300，假设输入时每隔100ms输入一个字符，则快速输入1234时，只会在4输入完成后300ms才进行一次提示。<b>注意：如果此属性值设成0或负数则不会延迟。</b>
             * @type Number
             * @default 500
             */
            delay : 500,
            /**
             * 本地缓存的数目。如果输入1进行一次提示，再输入2时又进行一次提示，再删除2此时如果再发一次请求可能性能不好，因此会对每次请求结果进行缓存，当删除2时在缓存中发现了key为1的缓存内容，因此就不再发请求了而是根据缓存直接计算。<br/>
             * <b>注意：该属性值必须为非负整数。要想禁用缓存，可以把cacheSize设成0</b>。
             * @type Number
             * @default 10
             */
            cacheSize : 10,
            /**
             * Ajax请求时的方式，是GET请求还是POST请求，取值GET'或'POST'
             * @type String
             * @default 'GET'
             */
            method : 'GET',
            /**
             * 下拉框的最大高度（单位是px）。如果不设置则根据下拉框的内容决定，需要多高就显示多高。如果设置了值后，当小于下拉框高度大于该值时就调整到这个设置的高度并出现垂直滚动条。<b>必须是正数（如果是0或负数则忽略此属性）。注意：由于浏览器的限制，这个属性的最小值是31，如果小于这个值时将看不到垂直滚动条。</b>
             * @type Number
             * @default 300
             */
            listMaxHeight : 300,
            /**
             * 发送Ajax请求时代表输入值的参数名。比如url是'fetchData.jsp?type=book'，queryName是'q'，当前输入的值是'abc'，则最终发送请求的url是'fetchData.jsp?type=book&q=abc'。
             * @type String
             * @default 'key'
             */
            queryName : 'key',
            /**
             * Ajax请求是否需要跨域（从本页面所在的网站以外的地方取数）。
             * @type Boolean
             * @default false
             */
            crossDomain : false,
            /**
             * 对Ajax请求的返回值进行预处理。<b>注意：此方法一定要返回处理后的结果。</b><br/>
             * 一个Ajax请求成功（不出错误也不超时）后会先执行onSuccess事件的监听器，如果它返回false则不显示下拉框。如果没有监听器或者监听没有返回false则执行此preProcess预处理，处理结束后开始刷新并显示下拉框。
             * @name omSuggestion#preProcess
             * @type Function
             * @default 无
             * @example
             * //text是输入框的值,data是服务器返回的数据
             * preProcess:function(text,data){
             *     return $(data).each(function(){
             *         this.sex = this.sex==0?'男':'女';
             *     });
             * }
             */
            /**
             * Ajax请求的超时时间。如果请求发出后在一定时间内没有响应回来，就认为是超时了。必须是一个非负整数，如果是负数则忽略此属性
             * @name omSuggestion#timeout
             * @type Number
             * @default 无
             */
            /**
             * Ajax请求的URL路径，所有的请求将由此URL来处理，处理结果必须返回一个JSON数组。
             * @name omSuggestion#dataSource
             * @type URL
             * @default 无
             * @example
             * dataSource:'/operamasks-ui/getData.json'
             */
            /**
             * 下拉框中每行显示内容的转换器。如果返回的JSON数组（如[{"text":"中国","count":123},{"text":"中间件","count":42}]）中每个record内容不是简单的字符串则一定要写这个属性，不然组件不知道如何显示到下拉框中。<br/>
             * 当然如果返回的JSON数组中每个record内容是简单的字符串则可以不写这个属性，当然也可以有这个属性，具体看下面的救命代码：
             * @name omSuggestion#clientFormatter
             * @type Function
             * @default 无
             * @example
             * //对于非普通record一定要写这个属性
             * clientFormatter:function(data,index){
             *         return '&lt;b>'+data.text+'&lt;/b>(共找到'+data.count+'条记录)';
             * }
             * 
             * //对于普通的record也可以写这个属性
             * clientFormatter:function(data,index){
             *         return '&lt;span style="color:red">'+data+'&lt;/span>;
             * }
             */
            /**
             * 下拉框的宽度。必须为数字。<b>不设置时默认与输入框一样宽</b>
             * @name omSuggestion#listWidth
             * @type Number
             * @default 无
             */
            /**
             * 在发送Ajax请求之前执行的方法。<b>注意：return false将会阻止请求发送。无返回值或return true将继续发送请求。</b>
             * @event
             * @param text 输入框里当前文本
             * @example
             * $('#inputID').omSuggestion({
             *         onBeforeSuggest:function(text){
             *                 if(text=='不文明用语'){
             *                         return false;//如果是不文明用语不进行提示
             *                 }else{
             *                         return true;
             *                 } 
             *         }
             * });
             */
            onBeforeSuggest : function(text){/*do nothing*/},
            /**
             * Ajax请求发送后响应回来前执行的方法。
             * @event
             * @param text 输入框里当前文本
             * @example
             * $('#inputID').omSuggestion({
             *         onSuggesting:function(text){
             *                 $('#inputID').omSuggestion('showMessage','正在加载...'); 
             *         }
             * });
             */
            onSuggesting : function(text){/*do nothing*/},
            /**
             * Ajax响应回来时执行的方法。
             * @event
             * @param text 输入框里当前文本
             * @param data Ajax请求返回的数据
             * @example
             * $('#inputID').omSuggestion({
             *         onSuccess:function(text,data){
             *                 if(results.length==0){
             *                         $('#txt').omSuggestion('showMessage','无提示数据！');
             *                 } 
             *         }
             * });
             */
            onSuccess : function(text, data){/*do nothing*/},
            /**
             * Ajax请求出错时执行的方法。
             * @event
             * @param text 输入框里当前文本
             * @param request request对象
             * @example
             * $('#inputID').omSuggestion({
             *         onTimeout:function(text,request){
             *                 if(results.length==0){
             *                         $('#txt').omSuggestion('showMessage','请求超时！');  
             *                 } 
             *         }
             * });
             */
            onTimeout : function(text, request){/*do nothing*/},
            /**
             * Ajax请求出错时执行的方法。
             * @event
             * @param text 输入框里当前文本
             * @param error 错误对象
             * @param request request对象
             * @example
             * $('#inputID').omSuggestion({
             *         onError:function(text, error, reques){
             *                 $('#txt').omSuggestion('showMessage','请求出错。原因：'+erroe.message); 
             *         }
             * });
             */
            onError : function(text, error, request){/*do nothing*/},
            /**
             * 选择下拉框中一个后执行的方法。
             * @event
             * @param text 输入框里当前文本
             * @param rowData 行记录，是Ajax请求返回的数据中的一行
             * @param index 当前行在下拉框所有行中的索引（第一行是0，第二行是1...）
             * @example
             * $('#inputID').omSuggestion({
             *         onSelect:function(rowData,text,index){
             *                 $('#searchbut').click(); //选择完后自动点击“查询”按钮
             *         }
             * });
             */
            onSelect : function(rowData,text,index){/*do nothing*/}
        },
        _create:function(){
            var options = this.options;
            var inputEl = this.element;
            inputEl.addClass('om-suggestion om-state-default om-state-nobg');
            //非法属性值修正
            if(options.minChars<0){
                options.minChars=0;
            }
            if(options.cacheSize<0){
                options.cacheSize=0;
            }
            if(options.delay<0){
                options.delay=0;
            }
            //开始构造
            var dropList = $($('<div class="om-widget"><div class="om-widget-content om-droplist"></div></div>').css('position', 'absolute').appendTo(document.body).children()[0]).hide();
            $.data(inputEl, 'dropList', dropList);
        },
        _init:function(){
            var options = this.options;
            var inputEl = this.element.attr('autocomplete', 'off');
            var dropList = $.data(inputEl, 'dropList');
            if (options.disabled) {
                inputEl.attr('disabled', 'disabled');
            } else {
                inputEl.removeAttr('disabled');
            }
            if (options.readOnly) {
                inputEl.attr('readonly', 'readonly');
            } else {
                inputEl.removeAttr('readonly');
            }
            //绑定按键事件
            var self = this;
            
            inputEl.bind('focus blur',function(){      
                $(this).toggleClass("om-state-focus");
            }).keyup(function(e){
                e = e || window.event;
                var key = e.keyCode;
                switch (key) {
                    case 40: //down
                        if (dropList.css('display') !== 'none') {
                            self._selectNext();
                        } else {
                            if (dropList.find('.' + suggestionRowClass).size() > 0) {
                                dropList.show();
                            }
                        }
                        break;
                    case 38: //up
                        if (dropList.css('display') !== 'none') {
                            self._selectPrev();
                        } else {
                            if (dropList.find('.' + suggestionRowClass).size() > 0) {
                                dropList.show();
                            }
                        }
                        break;
                    case 13: //enter
                        dropList.hide();
                        return false;
                    case 27: //esc
                        dropList.hide();
                        break;
                    case 9: //tab
                        //only trigger the blur event
                        break;
                    default:
                        if (options.disabled || options.readOnly) {
                            return false;
                        }
                        if (options.delay > 0) {
                            var delayTimer = $.data(inputEl, 'delayTimer');
                            if (delayTimer) {
                                clearTimeout(delayTimer);
                            }
                            delayTimer = setTimeout(function(){
                                self._suggest();
                            }, options.delay);
                            $.data(inputEl, 'delayTimer', delayTimer);
                        } else {
                            self._suggest();
                        }
                }
            });
            $(document).click(function(){
                setTimeout(function(){
                    dropList.hide();
                }, 1);
            });
        },
        /**
         * 清空与此组件相关的缓存数据。
         * @name omSuggestion#clearCache
         * @function
         * @returns 无
         * @example
         * $('#txt').omSuggestion('clearCache');
         */
        clearCache:function(){
            $.removeData(this.element,'cache');
        },
        /**
         * 在下拉框中显示一个提示信息（仅用于阅读，不可以通过快捷键或鼠标选择它）。
         * @name omSuggestion#showMessage
         * @function
         * @param message 要显示在下拉框中的消息
         * @example
         * $('#txt').omSuggestion('showMessage','请求数据出错');
         */
        showMessage: function(message){
            var inputEl = this.element;
            var dropList = $.data(inputEl, 'dropList').empty().css('height','auto');
            $('<div>' + message + '<div>').appendTo(dropList);
            dropList.parent().css('left', inputEl.offset().left).css('top',inputEl.offset().top+inputEl.outerHeight());
            var listWidth = this.options.listWidth;
            if (!listWidth) {//没有定义
                dropList.parent().width(inputEl.outerWidth());
            } else if (listWidth !== 'auto') {
                dropList.parent().width(listWidth);
            }
            dropList.show();
            var listMaxHeight = this.options.listMaxHeight;
            if(listMaxHeight !== 'auto'){
                if(dropList.height() > listMaxHeight){
                    dropList.height(listMaxHeight).css('overflow','auto');
                }
            }
            return this;
        },
        /**
         * 禁用组件。
         * @name omSuggestion#disable
         * @function
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('myinput').omSuggestion('disable');
         */
        disable:function(){
            return this.element.attr('disabled', 'disabled');
        },
        /**
         * 启用组件。
         * @name omSuggestion#enable
         * @function
         * @returns {jQuery Object} jQuery对象
         * @example
         * $('myinput').omSuggestion('enable');
         */
        enable:function(){
            return this.element.removeAttr('disabled');
        },
        /**
         * 设置新的请求地址，新地址表示参数的改变或者地址的改变
         * @name omSuggestion#setData
         * @function
         * @param dataSource
         * @example
         * $('#country').change(function() {
         *   var v = $('#country').val();
         *   $('#txt').omSuggestion("setData","../../../advancedSuggestion.json?contry="+v+"&province=hunan");
         * });
         */
        setData:function(dataSource){
            if(dataSource){
                this.options.dataSource = dataSource;
            }
			if(options.cacheSize > 0){
			    $.removeData(this.elemment, 'cache'); //清空缓存
			}
        },
        /**
         * 获取当前下拉框中的数据（服务器端返回的数据）
         * @name omSuggestion#getData
         * @function
         * @return Array[Json]
         * @example
         * $('#txt').omSuggestion("getData");
         */
        getData:function(){
            var returnValue = $.data(this.element, 'records');
            return returnValue || null;
        },
        _clear:function(){
            var inputEl = this.element.val('');
            var dropList=$.data(inputEl, 'dropList');
            return dropList.find('.'+suggestionRowClass).removeClass(suggestionHighLightClass);
        },
        _selectNext:function(){
            var inputEl = this.element;
            var dropList = $.data(inputEl, 'dropList');
            var index = dropList.find('.' + suggestionHighLightClass).index();
            var all = this._clear();
            index += 1;
            if (index >= all.size()) {
                index = 0;
            }
            this._scrollToAndSelect(all,index,dropList);
        },
        _selectPrev:function(){
            var inputEl = this.element;
            var dropList=$.data(inputEl, 'dropList');
            var index=dropList.find('.'+suggestionHighLightClass).index();
            var all = this._clear();
            index-=1;
            if(index<0){
                index=all.size()-1;
            }
            this._scrollToAndSelect(all,index,dropList);
        },
        _scrollToAndSelect:function(all,index,dropList){
            var target = $(all.get(index)).addClass(suggestionHighLightClass);
            var targetTop = target.position().top;
            if (targetTop <= 0) {
                //需要向上滚动滚动条
                dropList.scrollTop(dropList.scrollTop() + targetTop);
            } else {
                //需要向下滚动滚动条
                var offset = targetTop + target.outerHeight() - dropList.height();
                if (offset > 0) {
                    dropList.scrollTop(dropList.scrollTop() + offset);
                }
            }
            this._select(index);
        },
        _select:function(index){
            var inputEl = this.element;
            var records=$.data(inputEl, 'records');
            var rowData,text;
            if(records.valueField){
                rowData=records.data[index];
                text=rowData[records.valueField];
            }else{
                rowData=records[index];
                text=rowData;
            }
            inputEl.val(text);
            $.data(inputEl, 'lastStr', text);
            //trigger onSelect handler
            var onSelect=this.options.onSelect;
            if(onSelect){
                onSelect(rowData,text,index);
            }
        },
        _suggest:function(){
            var inputEl = this.element;
            var text = inputEl.val();
            var last = $.data(inputEl, 'lastStr');
            if (last && last === text) {
                return;
            }
            $.data(inputEl, 'lastStr', text);
            var options = this.options;
            var cache = $.data(inputEl, 'cache');
            if (text.length > 0 && text.length >= options.minChars) {
                if (cache) {
                    var data = cache[text];
                    if (data) {//有缓存
                        $.data(inputEl, 'records', data);
                        this._buildDropList(data, text);
                        return;
                    }
                }
                //无缓存
                if (options.onBeforeSuggest) {
                    if (options.onBeforeSuggest(text) === false) {
                        return;
                    }
                }
                var self = this;
                var requestOption = {
                    url: options.dataSource,
                    type: options.method,
                    dataType: options.crossDomain ? 'jsonp':'json',
                    data: {},
                    success: function(data){
                        var onSuccess = options.onSuccess;
                        if (onSuccess && onSuccess(text, data) === false) {
                            return;
                        }
                        var preProcess = options.preProcess;
                        if(preProcess){
                            data = preProcess(text,data);
                        }
                        //如果有preProcess且没有返回值
                        if(typeof data === 'undefined'){
                            data=[];
                        }
                        //cache data
                        if (options.cacheSize > 0) {
                            var cache = $.data(inputEl, 'cache') ||
                            {
                                ___keys: []
                            };
                            var keys = cache.___keys;
                            if (keys.length == options.cacheSize) {
                                //cache满了先去掉一个
                                var k = keys[0];
                                cache.___keys = keys.slice(1);
                                cache[k] = undefined;
                            }
                            cache[text] = data;
                            cache.___keys.push(text);
                            $.data(inputEl, 'cache', cache);
                        }
                        $.data(inputEl, 'records', data);
                        //buildDropList
                        self._buildDropList(data, text);
                    }
                };
                requestOption.data[options.queryName]=text;
                var timeout = options.timeout;
                if (timeout > 0) {
                    $.ajax($.extend({
                        timeout: timeout,
                        error: function(XMLHttpRequest, textStatus, errorThrown){
                            if (textStatus == 'timeout') {
                                var onTimeout = options.onTimeout;
                                if (onTimeout) {
                                    onTimeout(text, XMLHttpRequest);
                                }
                            } else {
                                var onError = options.onError;
                                if (onError) {
                                    onError(text, errorThrown, XMLHttpRequest);
                                }
                            }
                        }
                    }, requestOption));
                } else {
                    $.ajax($.extend({
                        error: function(request, textStatus, error){
                            var onError = options.onError;
                            if (onError) {
                                onError(text, error, request);
                            }
                        }
                    }, requestOption));
                }
                var onSuggesting = options.onSuggesting;
                if (onSuggesting) {
                    onSuggesting(text);
                }
            } else {
                $.data(inputEl, 'dropList').empty().hide();
            }
        },
        _buildDropList:function(records,text){
            var inputEl = this.element;
            var dropList = $.data(inputEl, 'dropList').empty().css('height','auto');
            var isSimple = records.valueField ? false : true;
            var clientFormatter = this.options.clientFormatter;
            var self = this;
            if (isSimple) {
                if (clientFormatter) {
                    $(records).each(function(index){
                        self._addRow(clientFormatter(this, index), dropList);
                    });
                } else {
                    $(records).each(function(index){
                        self._addRow(this, dropList);
                    });
                }
            } else {
                if (clientFormatter) {
                    $(records.data).each(function(index){
                        self._addRow(clientFormatter(this, index), dropList);
                    });
                }
            }
            var all = dropList.find('.' + suggestionRowClass);
            if (all.size() > 0) {
                dropList.parent().css('left', parseInt(inputEl.offset().left)).css('top',inputEl.offset().top+inputEl.outerHeight());
                var listWidth = this.options.listWidth;
                if (!listWidth) {//没有定义
                    dropList.parent().width(inputEl.outerWidth());
                } else if (listWidth !== 'auto') {
                    dropList.parent().width(listWidth);
                }
                all.mouseover(function(){
                    all.removeClass(suggestionHighLightClass);
                    $(this).addClass(suggestionHighLightClass);
                }).click(function(){
                    var index = dropList.find('.' + suggestionHighLightClass).index();
                    self._select(index);
                    dropList.hide();
                });
                dropList.show();
                var listMaxHeight = this.options.listMaxHeight;
                if(listMaxHeight !== 'auto'){
                    if(dropList.height() > listMaxHeight){
                        dropList.height(listMaxHeight).css('overflow','auto');
                    }
                }
                dropList.scrollTop(0);
            }
        },
        _addRow: function(html,dropList){
            //do replace
            $('<div class="' + suggestionRowClass + '">' + html + '</div>').appendTo(dropList);
        }
    });
    
})(jQuery);
/**
 * $.fn.omTabs
 * 将如下html结构转变成一个tab页签布局.
 *      <div id="make-tab">
 *          <ul>
 *              <li>
 *                  <a href="#tab1"></a>
 *              </li>
 *              <li>
 *                  <a href="#tab2"></a>
 *              </li>
 *              <li>
 *                  <a href="#tab3"></a>
 *              </li>
 *          </ul>
 *      	<div id="tab1">
 *          	this is tab1 content
 *      	</div>
 *      	<div id="tab2">
 *          	this is tab2 content
 *      	</div>
 *      	<div id="tab3">
 *          	this is tab3 content
 *      	</div>
 *      </div>
 *          ......//some other stuff
 *      
 * 使用了 $('#make-tab').omTabs() 之后, 最终的dom结构如下所示:
 * 
 *      <div id="make-tab" class="om-tabs">
 *          <div class="om-tabs-headers">
 *              <ul>
 *                  <li>
 *                      <a href="#tab1"></a>
 *                  </li>
 *                  <li>
 *                      <a href="#tab2"></a>
 *                  </li>
 *                  <li>
 *                      <a href="#tab3"></a>
 *                  </li>
 *              </ul>
 *          </div>
 *          <div class="om-tabs-panels">
 *              <div id="tab1">
 *                  this is tab1 content
 *              </div>
 *              <div id="tab2">
 *                  this is tab2 content
 *              </div>
 *              <div id="tab3">
 *                  this is tab3 content
 *              </div>
 *          </div>
 *      
 *      </div>
 * 
 */
(function(){
    var tabIdPrefix = 'om-tabs-' + (((1+Math.random())*0x10000)|0).toString(16).substring(1) + '-',
        id = 0;
    /**
     * class OmPanel, 在target指定的地方根据config生成一个Panel, 该类是$.fn.omPanel包装器
     * param target dom元素,一般指向一个div.
     * param config 生成Panel所需要的配置项,将会被传递给 $.fn.omPanel .
     * 如果当前panel是ajax载入的,则config.url不为空.否则config.content不为空
     * return 原先的target
     */
    function OmPanel (target, config) {
        $(target).omPanel(config);
        return target;
    }
    function makeSketch(self) {
        var tabs = $(self).find('>ul').wrap('<div class="om-tabs-headers om-helper-reset om-helper-clearfix om-widget-header om-corner-all"></div>').parent().parent();
        tabs.addClass('om-tabs om-widget om-widget-content om-corner-all').append('<div class="om-tabs-panels om-widget-content om-corner-bottom"></div>');
        //now we have a sketch, which contains the headers and panels
        return tabs;
    }
    function collectItems(self) {
        var options = $.data(self, 'omtabs').options;
        var items = [];
        $(self).find('>div.om-tabs-headers a').each(function(){
            var anchor = $(this);
            var tabId = anchor.attr('tabId') || anchor.attr('id') || tabIdPrefix + id++ ;
            anchor.attr('tabId', tabId);
            var href = anchor.attr('href');
            //在ie7中, href属性是绝对路径
            if ($.browser.msie && parseInt($.browser.version) == 7) {
                if (anchor[0].pathname && href.lastIndexOf(anchor[0].pathname) != -1){
                    var pathname = anchor[0].pathname;
                    href = href.substring(href.lastIndexOf(pathname) + pathname.length );
                }
            }
            var target = $('>' + href, $(self))[0];
            var cfg = {
                    tabId : tabId,
                    title : anchor.text(),
                    noHeader : true,
                    onLoad : function() {
                        options.onLoadComplete.call(self, tabId);
                    },
                    closed : true,//先全部隐藏.
                    lazyLoad : true, //先全部不加载
                    border : false
            };
            //target要么指向一个当前页面的div,要么是一个url
            !!target ? cfg.content = $(target).html() : cfg.url = anchor.attr('href');
            var item = new OmPanel(target || $('<div></div>')[0], cfg);
            items.push(item);
        });
        // items 是panel的集合.每一个item通过 $(item).omPanel('panel')之后能获取到对应的panel对象
        return items;
    }
    function render(self) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs;
        if (options.width == 'fit') {
        	omtabs.css('width', '100%');
        } else if (options.width != 'auto') {
            omtabs.css('width', options.width);
        }
        if (options.height == 'fit') {
        	omtabs.css('height', '100%');
        } else if (options.height != 'auto') {
            omtabs.css('height', options.height);
        }
        renderHeader(self);
        renderBody(self);
    };
    function renderHeader(self) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs;
        var headers = omtabs.find('>div.om-tabs-headers');
        var lis = headers.find('ul li');
        lis.addClass('om-state-default om-corner-top');
        lis.each(function(n){
            var innera = $(this).find('a:first');
            if ($.browser.msie && parseInt($.browser.version) == 7 ) {
                innera.attr('hideFocus', 'true');
            }
            if (!innera.hasClass('om-tabs-inner')) {
                innera.addClass('om-tabs-inner');
            }
            if (n == options.active) {
                $(this).addClass('om-tabs-selected om-state-active');
                options.activeTabId = innera.attr('tabId');
            }
            //tab width and height. by default, tabWidth=80 tabHeight=25, accept 'auto'
            innera.css({
                'width' : options.tabWidth,
                'height' : options.tabHeight
            });
            if (options.closable) {
                $('<a class="om-icon om-icon-close"></a>').insertAfter(innera);
            }
        });
        var aHeight = lis.find('a').height();
        lis.parent().css({
            'height' : ++ aHeight ,
            'line-height' : aHeight + 'px'
        });
        _checkScroller(self) && _enableScroller(self);
    }
    function renderBody(self) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs,
            items = data.items;
        var panels = omtabs.find('>div.om-tabs-panels');
        //detach all sub divs
        panels.children().detach();
        var styles = omtabs.attr('style'); 
        if (styles && styles.indexOf('height') != -1 ) {
            var omtabsHeight = omtabs.innerHeight(),
                headersHeight = omtabs.find('>div.om-tabs-headers').outerHeight();
            panels.css('height', omtabsHeight - headersHeight);
        }
        if (!options.border) {
            omtabs.css('border-width', '0');
        }
        var i = items.length;
        while( i -- ) {
            var panel = $(items[i]).omPanel('panel');
            panel.prependTo(panels);
        }
    }
    function afterRender(self) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            items = data.items;
        var i = items.length;
        if (!options.lazyLoad) {
            $(items).omPanel('reload');
        }
        while( i -- ) {
            var target = $(items[i]);
            if (i == options.active) {
                target.omPanel('open');
            } else {
                target.omPanel('close');
            }
        }
    }
    function buildEvent(self) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs;
        //close icon
        omtabs.find('>div.om-tabs-headers a.om-icon-close').bind('click.omtabs', function(e){
            var tabid = $(e.target).prev().attr('tabId');
            _close(self, tabid);
            return false;
        });
        // tab click
        if (options.switchMode.indexOf('mouseover') != -1) {
            omtabs.find('>div.om-tabs-headers a.om-tabs-inner').bind('mouseover.omtabs', function() {
                _select(self, $(this).attr('tabId'));
                return false;
            });
        } else if (options.switchMode.indexOf('click') != -1 ) {
            omtabs.find('>div.om-tabs-headers a.om-tabs-inner').bind('click.omtabs', function(){
                _select(self, $(this).attr('tabId'));
                return false;
            });
        }
        if (options.switchMode.indexOf('auto') != -1 ) {
            options.autoInterId = setInterval(function(){
                $(self).omTabs('select', 'next');
            }, options.switchDuration);
        }
        //tab hover
        if ( options.switchMode.indexOf("mouseover") == -1 ) {
        	var lis = omtabs.find('>div.om-tabs-headers li');
			var addState = function( state, el ) {
				if ( el.is( ":not(.om-state-disabled)" ) ) {
					el.addClass( "om-state-" + state );
				}
			};
			var removeState = function( state, el ) {
				el.removeClass( "om-state-" + state );
			};
			lis.bind( "mouseover.omtabs" , function() {
				addState( "hover", $( this ) );
			});
			lis.bind( "mouseout.omtabs", function() {
				removeState( "hover", $( this ) );
			});
		}
        //scroller click
        omtabs.find('>div.om-tabs-headers >span').bind('click.omtabs', function(e) {
            if ($(this).hasClass('om-tabs-scroll-disabled')) {
                return false;
            }
            var scrOffset =  $(this).offset();
            var ul = $(this).parent().find('ul');
            var li = ul.children(':last');
            var dist = li.outerWidth(true);
            var parent = $(this).parent();
            if ($(this).hasClass('om-tabs-scroll-left')) {
                _scroll(self, dist, _scrollCbFn(self));
            }
            if ($(this).hasClass('om-tabs-scroll-right')) {
                _scroll(self, - dist, _scrollCbFn(self));
            }
            return false;
        });
    }
    //remove every events.
    function purgeEvent(self) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs;
        var headers = omtabs.find('>div.om-tabs-headers');

        headers.children().unbind('.omtabs');
        headers.find('>ul >li >a').unbind('.omtabs');
        if (options.autoInterId) {
            clearInterval(options.autoInterId);
        }
    }
    //private methods
    /**
     * 选中特定的页签
     * n 可为页签的索引(从0开始计数),或者页签的tabId TODO n 需要支持first  和 last 表示选中第一个和最后一个
     */
    function _select(self, n) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs,
            items = data.items;
        var ul = omtabs.find('>div.om-tabs-headers ul');
        if ( options.activeTabId == n || options.active == n ) {
            return false;
        }
		n = n || 0;
        var anchor , tid = n;
        if ( n == 'next' ) {
            n = options.active == items.length - 1  ? 0 : options.active + 1 ;
        } else if ( n == 'prev' ) {
            n = options.active == 0 ? items.length - 1 : options.active - 1;
        } 
        if (typeof n == 'number') {
            tid = _getAlter(self, n);
        } else if (typeof n == 'string') {
            n = _getAlter(self, n);
        }
        if (options.onBeforeSelect.call(self, n) == false) {
            return false;
        }
        anchor = ul.find('li a[tabId=' + tid + ']');
        anchor.parent().siblings().removeClass('om-tabs-selected om-state-active');
        anchor.parent().addClass('om-tabs-selected om-state-active');
        options.activeTabId = tid;
        options.active = n;
        var i = items.length;
        while( i-- ) {
            var target = $(items[i]);
            if (target.omPanel('options').tabId == tid) {
                target.omPanel('open', (options.switchEffect != 'none'), options.switchSpeed);
            } else {
                target.omPanel('close');
            }
        }
        //当选中了一个并未完全显示的页签,需要滚动让他完全显示出来
        if (_checkScroller(self)) {
            //stop every animation.
            ul.stop(true, true);
            $(self).clearQueue();
            var lScroller = ul.prev();
            var rScroller = ul.next();
            var lBorder = anchor.parent().offset().left;
            var rBorder = lBorder + anchor.parent().outerWidth(true);
            var lDiff = lScroller.offset().left + lScroller.outerWidth(true) + 4 - lBorder ;
            var rDiff = rScroller.offset().left - rBorder ;
            if (lDiff >= 0) {
                _scroll(self, lDiff, _scrollCbFn(self));
            } else if (rDiff <= 0) {
                _scroll(self, rDiff, _scrollCbFn(self));
            } else {
                _scrollCbFn(self)();
            }
        }
        options.onSelect.call(self, n);
    }
    
    /**
     * 页签索引和tabId的转换器.
     * 如果传入的id为数字,则表示页签的索引,函数返回页签的tabId;如果id为字符串,则表示该页签的tabId,函数返回页签的索引.
     */
    function _getAlter (self, id) {
        var omtabs = $.data(self, 'omtabs').omtabs,
            rt;
        if (typeof id == 'number'){
            rt = omtabs.find('>div.om-tabs-headers li:nth-child(' + ++id + ') a.om-tabs-inner').attr('tabId');
        } else if (typeof id == 'string') {
            omtabs.find('>div.om-tabs-headers li a.om-tabs-inner').each(function(i){
                if ($(this).attr('tabId') == id ) {
                    rt = i;
                    return false;
                }
            });
        }
        return rt;
    }
    /**
     * 返回当前选中的页签的tabId
     */
    function _getSelected(self) {
        var options = $.data(self, 'omtabs').options;
        return options.activeTabId;
    }
    /**
     * 增加一个tab到页签布局中.最后一个参数isAjax指示了ds是否为一个URL
     */
    function _add (self, config/*title, content, url, closable , index*/) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs,
            items = data.items;
        var ul = omtabs.find('>div.om-tabs-headers ul');
        var tabId = tabIdPrefix + id++;
        //调整参数
        config.index = config.index || 'last';
        if (config.index == 'last' || config.index > items.length - 1) {
            config.index = items.length;
        }
        config.title = config.title || 'New Title ' + tabId;
        if (config.content) {
            config.url == undefined;
        } else if (! $.trim(config.url)) {
            config.content = 'New Content ' + tabId;
        }
        if (options.onBeforeAdd.call(self, config/*title, content, url, closable , index*/) == false) {
            return false;
        }
        var nHeader=$('<li class="om-state-default om-corner-top"> </li>');
        var anchor = $('<a class="om-tabs-inner"></a>').html(config.title).attr({
                href : '#' + tabId,
                tabId : tabId
            }).css({
                width : options.tabWidth,
                height : options.tabHeight
            }).appendTo(nHeader);
        if ($.browser.msie && parseInt($.browser.version) == 7) {
            anchor.attr('hideFocus','true');
        }
        if ((config.closable === true) || 
                (config.closable == undefined && options.closable)) {
            anchor.after('<a class="om-icon om-icon-close"></a>');
        }
        var cfg = {
            tabId : tabId,
            noHeader : true,
            closed : true,
            lazyLoad : options.lazyLoad,
            onLoad : function() {
                options.onLoadComplete.call(self, tabId);
            },
            border : false
        };
        
        $.extend(cfg, config);
        var nPanel = new OmPanel($('<div></div>')[0],cfg);
        if (config.index == items.length) {
            items[config.index] = nPanel;
            nHeader.appendTo(ul);
        } else {
            //insert at index
            items.splice(config.index, 0, nPanel);
            ul.children().eq(config.index).before(nHeader);
        }
        //every time we add or close an tab, check if scroller is needed.
        _checkScroller(self) && _enableScroller(self);
        renderBody(self);
        purgeEvent(self);
        buildEvent(self);
        options.onAdd.call(self, cfg);
        _select(self, config.index);
    }
    /**
     * 将index处的页签关闭,如果index指向当前页签,则激活下一页签;如果当前页签是最后一个页签,则激活第一个页签
     * index :页签的位置,可为数字,tabId等. TODO index将要支持prev,  next, first, last
     */
    function _close (self, index) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs,
            items = data.items;
        var headers = omtabs.find('>div.om-tabs-headers');
        var panels = omtabs.find('>div.om-tabs-panels');
        index = index || options.active;
        if (typeof index == 'string') {
            //index is a tabid
            index = _getAlter(self, index);
        }
        if (options.onBeforeClose.call(self, index) == false) {
            return false;
        }
        headers.find('li').eq(index).remove();
        panels.children().eq(index).remove();
        items.splice(index, 1);
        //in case of all tabs are closed, set body height
        if (panels.children().length == 0) {
            panels.css({height : omtabs.innerHeight() - headers.outerHeight()});
        }
        options.onClose.call(self, index);
        if (index == options.active) {
            options.active = -1;
            !items[index] && (index = 0);
            _select(self, index);
//            $(self).omTabs('select', index);
        } else {
            index < options.active && options.active --;
            _checkScroller(self) && _enableScroller(self);
        }
    } 
    /**
     * 如果tab页签总宽度较大，则显示scroll并返回true;否则删除scroll并返回false.
     */
    function _checkScroller(self) {
        var data = $.data(self, 'omtabs'),
            options = data.options,
            omtabs = data.omtabs;
        if (!options.scrollable) {
            return false;
        }
        var ul = omtabs.find('>div.om-tabs-headers ul');
        var totalWidth = 0, flag = false;
        if (ul.hasClass('om-tabs-scrollable')) {
            //先假定没有左右滚动器 ,来计算宽度是否超过.
            flag = true;
            ul.removeClass('om-tabs-scrollable');
        }
        totalWidth += parseInt(ul.css('paddingLeft')) + parseInt(ul.css('paddingRight'));
        if (flag == true) {
            //重新加上滚动器 .
            flag = false;
            ul.addClass('om-tabs-scrollable');
        }
        ul.children().each(function() {
            //计算一个li占用的总宽度
            totalWidth += $(this).outerWidth(true);//sub element's width
        });
        if (totalWidth > ul.parent().innerWidth()) {
            if (!ul.hasClass('om-tabs-scrollable')) {
                var leftScr = $('<span></span>').insertBefore(ul).addClass('om-tabs-scroll-left');
                var rightScr = $('<span></span>').insertAfter(ul).addClass('om-tabs-scroll-right');
                var mgn = (ul.height() - leftScr.height())/2;
                leftScr.add(rightScr).css({ // scroller in vertical center.
                    'marginTop' : mgn,
                    'marginBottom' : mgn
                });
                ul.addClass('om-tabs-scrollable');
            }
            return true;
        } else {
            ul.siblings().remove();
            ul.removeClass('om-tabs-scrollable');
            return false;
        }
    }
    /**
     * 一般滚动之后都需要执行回调_enableScroller设置滚动条的状态,现包装成方法.
     */
    function _scrollCbFn (self) {
        return function(){
            _enableScroller(self);
        };
    }
    /**
     * 根据页签的位置,设置scroller的状态.
     * 当最右边的页签顶住组件右边沿,则右边的scroller应该禁用,表示不能再往右滚动了.
     * 当最左边的页签顶住组件左边沿,则左边的scroller应该禁用,表示不能再往左滚动了.
     */
    function _enableScroller (self) {
        var omtabs = $.data(self, 'omtabs').omtabs;
        var headers = omtabs.find('>div.om-tabs-headers');
        var ul = headers.children('ul');
        var lScroller = ul.prev();
        var rScroller = ul.next();
        var li = ul.children(':last');
        var lBorder = headers.offset().left,
            rBorder = rScroller.offset().left,
            ulLeft = ul.offset().left,
            ulRight = li.offset().left + li.outerWidth(true);
        if (ulLeft < lBorder) {
            lScroller.removeClass('om-tabs-scroll-disabled');
        } else {
            lScroller.addClass('om-tabs-scroll-disabled');
            //_scroll(self, lBorder - ulLeft);
        }
        if (ulRight > rBorder) {
            rScroller.removeClass('om-tabs-scroll-disabled');
        } else {
            rScroller.addClass('om-tabs-scroll-disabled');
            //_scroll(self, rBorder - ulRight);
        }
    }
    /**
     * 将页签头部往右边滑动distance的距离.当distance为负数时,表示往左边滑动;fn为回调函数
     */
    function _scroll(self, distance, fn) {
        var omtabs = $.data(self, 'omtabs').omtabs;
        var ul = omtabs.find('>div.om-tabs-headers ul');
        var li = ul.children(':last');
        if (distance == 0) {
            return;
        }
        var scrOffset = distance > 0 ? ul.prev().offset() : ul.next().offset();
        var queuedFn = function(next) {
            if (distance > 0 && ul.prev().hasClass('.om-tabs-scroll-disabled') ||
                    distance < 0 && ul.next().hasClass('.om-tabs-scroll-disabled')){
                ul.stop(true, true);
                $(self).clearQueue();
                return;
            }
            var flag = false;
            //fix distance.
            distance = (distance > 0) ? '+=' + Math.min(scrOffset.left - ul.offset().left, distance) : 
                '-=' + Math.min(li.offset().left + li.outerWidth(true) - scrOffset.left, Math.abs(distance));
            $.data(self, 'omtabs').isScrolling = true;
            ul.animate({
                left : distance + 'px'
            },'normal', 'swing', function() {
                !!fn && fn();
                $.data(self, 'omtabs').isScrolling = false;
                next();
            });
        };
        $(self).queue(queuedFn);
        if( $(self).queue().length == 1 && 
                !$.data(self, 'omtabs').isScrolling){
            $(self).dequeue(); //start queue
        }
    }
    /**
     * 获得当前所有页签的数目
     */
    function _getLength (self) {
        return $.data(self, 'omtabs').items.length;//items is the array of body dom
    }
    /**
     * 重新计算omTabs布局
     */
    function _doLayout(self) {
        _checkScroller(self) && _enableScroller(self);
    }
    /**
     * 设置第n个页签的数据源,如果isAjax为true,则ds将被当做url.否则ds将被当做页签的正文,
     */
    function _setDataSource(self, n, ds, isAjax) {
        var items = $.data(self, 'omtabs').items;//items is the array of body dom
        $(items[n]).omPanel('setDataSource', ds, isAjax);
    }
    /**
     * 重新加载第n个页签,如果该Panel有content,则重新刷新content,如果该Panel有url,则根据url取到最新的内容.
     */
    function _reload(self, n) {
        var items = $.data(self, 'omtabs').items;//items is the array of body dom
        $(items[n]).omPanel('reload');
    }
    
    var publicMethods = {
        disable : function() {
            
        },
        enable : function() {
            
        },
        /**
         * 获得组件的最新的配置属性.如果有参数,则将该参数合并到配置属性中.
         * @name omTabs#options
         * @function
         * @param p
         * @returns 组件的配置属性
         * @example 
         * $('#make-tab').omTabs('options', {
         *     height : 400 
         * });
         */
        options : function(p) {
            var options = $.data(this[0], "omtabs").options;
            if (p) {
                $.extend(options, p);
            }
            return options;
        },
        /**
         * 在index处增加一个tab页签
         * @name omTabs#add
         * @function
         * @param index 新增页签的位置(从0开始计数),可设置为'last' //TODO 'first'
         * @param title 新增页签的标题
         * @param ds 新增页签的数据源,可为普通文本,或者url
         * @param isAjax 数据源是否为一个url
         * @example
         * //在第一个页签的位置新增一个页签,该页签的内容是远程数据
         *  $('#make-tab').omTabs('add', {
         *      title : 'New Tab1',
         *      content : 'New Content1',
         *      closable : false,
         *      index : 0
         *  });
         */
        add : function(config /*title, content, url, closable , index*/) {
            this.each(function(){
                _add(this, config /*title, content, url, closable , index*/);
            });
        },
        
        /**
         * 关闭特定的页签,如果n指向当前页签,则激活下一页签;如果当前页签是最后一个页签,则激活第一个页签.
         * @name omTabs#close
         * @function
         * @param n 要关闭的页签的位置(从0开始计数),或者该页签的tabId(一个全局唯一的字符串). 如果未指定该参数,则默认关闭当前页签.
         * @example
         * //关闭第一个页签
         * $('#make-tab').omTabs('close', 0);
         */
        close : function(n) {
            this.each(function(){
                _close(this, n);
            });
        },
        /**
         * 激活特定的页签
         * @name omTabs#select
         * @function
         * @param n 可为页签的索引(从0开始计数),或者页签的tabId
         * @example
         * //激活第一个页签
         * $('#make-tab').omTabs('select', 0);
         */
        select : function(n) {
            this.each(function(){
                _select(this, n);
            });
        },
        /**
         * 页签索引和tabId的转换器.传入其中的一个值,获取另一个值.
         * @name omTabs#getAlter
         * @function
         * @param id 标识符
         * @returns 如果id为数字,则表示页签的索引,函数返回页签的tabId;如果id为字符串,则表示该页签的tabId,函数返回页签的索引.
         * @example
         * //获取第一个页签的tabId
         * var tabId = $('#make-tab').omTabs('getAlter', 0);
         */
        getAlter : function(id) {
            return _getAlter(this[0], id);
        },
        /**
         * 返回当前选中的页签的tabId
         * @name omTabs#getSelected
         * @function
         * @returns 当前选中页签的tabId
         * @example
         * //获取当前选中页签的tabId
         * var selectedTabId = $('#make-tab').omTabs('getSelected');
         */
        getSelected : function() {
            return _getSelected(this[0]);
        },
        /**
         * 获得所有页签的数目
         * @name omTabs#getLength
         * @function
         * @returns 页签的数目
         * @example
         * //获取页签的总数
         * var total = $('#make-tab').omTabs('getLength');
         */
        getLength : function() {
            return _getLength(this[0]);
        },
        /**
         * 设置第n个页签的数据源,可为普通文本或者url.注意该方法只是会重置一个当前页签是否已被加载的标记,而不负责实际加载数据,
         * 在非懒加载的情况下,需要手动加载数据.在懒加载的情况下,当页签被激活时会检查是否已经加载的标记,从而尝试重新加载内容
         * @name omTabs#setDataSource
         * @function
         * @param index 要设置数据源的页签的索引
         * @param ds 数据源,可为普通文本,或者url,由isAjax确定
         * @parma isAjax 数据源是否是url
         * @example
         * //设置第一个页签的数据源为远程数据
         * $('#make-tab').omTabs('setDataSource', 0, './ajax/content1.html', true);
         */
        setDataSource : function(index, ds, isAjax) {
            this.each(function(){
                _setDataSource(this, index, ds, isAjax);
            });
        },
        /**
         * 根据第n个页签当前的数据源,重新加载该页签
         * @name omTabs#reload
         * @function
         * @param n 页签的索引
         * @example
         * //重新加载第一个页签的内容
         * $('#make-tab').omTabs('reload', 0);
         */
        reload : function(n) {
            this.each(function(){
                _reload(this, n);
            });
        },
        /**
         * 对组件重新布局,主要操作是刷新页签滚动箭头.
         * 如果有必要使用页签滚动箭头,则刷新滚动箭头的状态.如果没必要使用页签滚动箭头,则将存在的删除.
         * @name omTabs#doLayout
         * @function
         * @example
         * //对组件重新布局,如果有必要使用页签滚动箭头,则刷新滚动箭头的状态.
         */
        doLayout : function() {
            this.each(function(){
                _doLayout(this);
            });
        }
    };
    var defaultConfig = /** @lends omTabs#*/{
        /**
         * 页签布局的宽度,可取值为'auto'(默认情况,不做处理),可以取值为'fit',表示适应父容器的大小(width:100%).任何其他的值将被直接赋给width属性
         * @default 'auto'
         * @type Number,String
         * @example
         * $('#make-tab').omTabs({width: 500});
         */
        width : 'auto',
        /**
         * 页签布局的高度,可取值为'auto'(默认情况,不做处理),可以取值为'fit',表示适应父容器的大小(height:100%).任何其他的值将被直接赋给height属性
         * @default 'auto'
         * @type Number,String
         * @example
         * $('#make-tab').omTabs({height: 200});
         */
        height : 'auto',
        /**
         * 是否显示页签正文区的边框
         * @default true
         * @type Boolean
         * @example
         * $('#make-tab').omTabs({border: false});//不显示页签正文区的边框
         */
        border : true,
        /**
         * 单个页签头部的宽度,可取值为'auto'.默认为80像素
         * @default 80
         * @type Number,String
         * @example
         * $('#make-tab').omTabs({tabWidth: 'auto'});
         */
        tabWidth : 80,
        /**
         * 单个页签头部的高度,可取值为'auto'.默认为25像素
         * @default 25
         * @type Number,String
         * @example
         * $('#make-tab').omTabs({tabHeight: 'auto'});
         */
        tabHeight : 25,
        /**
         * 是否禁用组件. (暂时不启用//TODO)
         * //name omTabs#disabled
         * @default false
         * @type Boolean
         * @example
         * $('#make-tab').omTabs({disabled : true});//初始化时禁用组件
         */
        disabled : false,
        /**
         * 当页签超过组件宽度时是否出现左右滚动箭头
         * @default true
         * @type Boolean
         * @example
         * $('#make-tab').omTabs({scrollable: false});//当页签数目较多时不显示滚动箭头,将访问不到未显示的页签.
         */
        scrollable : true,
        /**
         * 页签是否可关闭,当本属性为true时,将在页签头部右边放置一个关闭图标.
         * @default false
         * @type Boolean
         * @example
         * $('#make-tab').omTabs({closable : true});//页签可关闭
         */
        closable : false,
        
        //  暂时不公布
        //  页签头部的位置,可为top和left //TODO 'left'
        // @default 'top'
        // @type String
        // @example
        // $('#make-tab').omTabs({position : 'left'});//页签头部在组件的左边
        //
        position : 'top',
        /**
         * 页签切换的模式.可为click(鼠标点击切换),mouseover(鼠标滑过切换),auto(自动切换).
         * switchMode可为三者的组合,比如{switchMode: 'click,auto'}在自动切换的模式下,仍可以使用鼠标点击切换页签
         * @default 'click'
         * @type String
         * @example
         * $('#make-tab').omTabs({switchMode : 'click,auto'});//在自动切换的模式下,仍可以使用鼠标点击切换页签
         */
        switchMode : 'click',
        /**
         * 页签切换的效果,现在只支持fade 和 none  // TODO 'slide'
         * @default 'fade'
         * @type String
         * @example
         * $('#make-tab').omTabs({switchEffect : 'none'});//切换页签时不需要动画
         */
        switchEffect : 'fade',
        /**
         * 页签切换的响应速度.取值可为 slow, normal, fast
         * @default 'normal'
         * @type String
         * @example
         * $('#make-tab').omTabs({switchSpeed : 'fast'});//切换页签时,快速显示选中的页签.
         */
        switchSpeed : 'normal',
        /**
         * 自动切换页签的时间间隔,单位为毫秒. 该属性在 switchMode 为auto时才生效.
         * @default 1000
         * @type Number
         * @example
         * $('#make-tab').omTabs({switchDuration : 2000});//自动切换页签时,时间间隔为2s
         */
        switchDuration : 1000,
        /**
         * 初始化激活的页签的索引(从0开始计数)
         * @default 0
         * @type Number
         * @example
         * $('#make-tab').omTabs({active : 1});//初始化时选中第二个页签.
         */
        active : 0,
        /**
         * 是否懒加载,当该属性为true时,只有在页签被选中激活时才尝试加载页签正文区
         * @default false
         * @type Boolean
         * @example
         * $('#make-tab').omTabs({lazyLoad : true});
         */
        lazyLoad : false,
        /**
         * 当页签被选中之前执行的方法
         * @event
         * @param n 选中页签的索引,从0开始计数.
         * @default emptyFn 
         * @example
         *  $('#make-tab').omTabs({
         *      onBeforeSelect : function(n) {
         *          alert('tab ' + n + ' will be selected!');
         *      }
         *  });
         */
        onBeforeSelect : function(n) {
        },
        /**
         * 当页签被选中后执行的方法
         * @event
         * @param n 选中页签的索引,从0开始计数.
         * @default emptyFn 
         * @example
         *  $('#make-tab').omTabs({
         *      onSelect : function(n) {
         *          alert('tab ' + n + ' has been selected!');
         *      }
         *  });
         */
        onSelect : function(n) {
        },
        /**
         * 当页签被关闭之前执行的方法
         * @event
         * @param n 被关闭页签的索引,从0开始计数.
         * @default emptyFn 
         * @example
         *  $('#make-tab').omTabs({
         *      onBeforeClose : function(n) {
         *          alert('tab ' + n + ' will be closed!');
         *      }
         *  });
         */
        onBeforeClose : function(n) {
        },
        /**
         * 当页签被关闭之后执行的方法
         * @event
         * @param n 被关闭页签的索引,从0开始计数.
         * @default emptyFn 
         * @example
         *  $('#make-tab').omTabs({
         *      onClose : function(n) {
         *          alert('tab ' + n + ' has been closed!');
         *      }
         *  });
         */
        onClose : function(n) {
        },
        /**
         * 当新页签被添加之后执行的方法
         * @event
         * @default emptyFn 
         * @param config 经过处理的配置项.在调用add新增页签时,传入的配置项参数可能不完整(使用默认值),此处的config就是完整的配置项
         * @example
         *  $('#make-tab').omTabs({
         *      onAdd : function(config) {
         *          console.dir(config);
         *          alert('you have added a tab at position:' + config.index );
         *      }
         *  });
         */
        onAdd : function(config/*title, content, url, closable , index*/) {
        },
        /**
         * 当新页签被添加之前执行的方法
         * @event
         * @default emptyFn 
         * @param config 经过处理的配置项.在调用add新增页签时,传入的配置项参数可能不完整(使用默认值),此处的config就是完整的配置项
         * @example
         *  $('#make-tab').omTabs({
         *      onBeforeAdd : function(config) {
         *          console.dir(config);
         *          alert('you will add a tab at position:' + index );
         *      }
         *  });
         */
        onBeforeAdd : function(config/*title, content, url, closable , index*/) {
        },
        /**
         * 当页签使用ajax方式加载内容，加载完成后执行的方法.
         * @event
         * @default emptyFn
         * @param tabId 刚加载完成的页签的tabId
         * @example
         *  $('#make-tab').omTabs({
         *      onLoadComplete : function(tabId) {
         *          alert(tabId + 'has just been loaded!' );
         *      }
         *  });
         */
        onLoadComplete : function(tabId) {
        }
        
    };
    /**
     * @name omTabs
     * @author 陈界，李聪平
     * @class 页签布局组件,通过简单的配置就能将页面中的信息组合在各个页签中,同时组件提供丰富的事件支持,比如选中页签,关闭页签,添加页签等等.<br/>
     * 支持各个页签以ajax方式加载内容;支持懒加载;支持页签滚动
     * @constructor
     * @description 构造函数
     * @param p 标准config对象：{width:500, height:300}
     * @example
     * $('#make-tab').omTabs({width:500, height:300});
     */
    $.fn.omTabs = function(p) {
        if (p && typeof(p) == 'string') {
            if (publicMethods[p]) {
                return publicMethods[p].apply(this, Array.prototype.slice.call(arguments, 1));
            }
            return null;
        }
        return this.each(function() {
            var tData = $.data(this, 'omtabs');
            var options;
            if (tData) {
                $.extend(tData.options, p);
            } else {
                options = $.extend({}, defaultConfig, p);
                tData = $.data(this, 'omtabs', {
                    options : options
                });
                $.data(this, 'omtabs').omtabs = makeSketch(this);
                $.data(this, 'omtabs').items = collectItems(this);
            }
            render(this);
            afterRender(this);
            buildEvent(this);
        });
    };
})(jQuery);/*
 * operamasks-ui omTree 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 */
/**
     * @name omTree
     * @author 陈海宇，王璠
     * @class 树型组件<br/><br/>
     * treenode json格式为：<br/>
     * <pre>
     * {
     *     id:'node1', // 树结点id，此id为必需属性
     *     text:'node1', // 树结点显示文本  
     *     expanded:true, // 是否默认展开 
     *     classes:'folder', // 树结点样式，默认有folder和file，如果用户自定制为其他，则显示用户自定义样式 
     *     hasChildren: false // 树结点懈加载的情况下，该结点在展开时自动向后台取数
     * } 
     * </pre>
     * <br/>
     * <b>特点：</b><br/>
     * <ol>
     *      <li>可以使用本地数据源，也可以使用远程数据源</li>
     *      <li>支持数据的缓加载</li>
     *      <li>提供丰富的事件</li>
     * </ol>
     * 
     * <b>示例：</b><br/>
     * <pre>
     * &lt;script type="text/javascript" >
     * var data = 
     *       [{
     *           "text": "1. Review of existing structures",
     *           "classes" : "folder",
     *           "id": "n1",
     *           "children":[{
     *               "text": "1.1 jQuery core",
     *               "id": "n11",
     *               "classes": "file"
     *           }, {
     *               "text": "1.2 metaplugins",
     *               "id": "n12",
     *               "classes": "file"
     *           }]
     *       }, {
     *           "text": "2. Wrapper plugins",
     *           "expanded": true,
     *           "classes" : "folder",
     *           "id": "n2",
     *           "children":[{
     *               "text":"2.1 wrapper tips",
     *               "classes": "folder",
     *               "id": "n21",
     *               "expanded": true,
     *               "children": [{
     *                   "id": "n211",
     *                   "text":"2.1.1 wrapper loader tips"
     *               },{
     *                   "id": "n212",
     *                   "text":"2.1.2 wrapper runder tips"
     *               }]
     *           },{
     *               "text":"2.2 tree nodes",
     *               "id": "n22",
     *               "classes": "file"
     *           }]
     *       }, {
     *           "text": "3. Summary",
     *           "id": "n3"
     *       }, {
     *           "text": "4. Questions and answers",
     *           "id": "n4"
     *       }];
     *   $(document).ready(function(){
     *       $("#mytree").omTree({
     *           dataSource : data
     *       });
     *   });
     * &lt;/script>
     * 
     * &lt;ul id="mytree"/>
     * </pre>
     * 
     * @constructor
     * @description 构造函数. 
     * @param options 标准options对象：{}
     */
;(function($) {
    /**
     * treenode: {id:'node1', text:'node1', expanded:true, classes:'folder'}
     */
    $.treeview = {};
    var CLASSES = ($.treeview.classes = {
            open: "open",
            closed: "closed",
            expandable: "expandable",
            expandableHitarea: "expandable-hitarea",
            lastExpandableHitarea: "lastExpandable-hitarea",
            collapsable: "collapsable",
            collapsableHitarea: "collapsable-hitarea",
            lastCollapsableHitarea: "lastCollapsable-hitarea",
            lastCollapsable: "lastCollapsable",
            lastExpandable: "lastExpandable",
            last: "last",
            hitarea: "hitarea"
        });
    
    $.extend($.fn, {
        swapClass: function(c1, c2) {
            var c1Elements = this.filter('.' + c1);
            this.filter('.' + c2).removeClass(c2).addClass(c1);
            c1Elements.removeClass(c1).addClass(c2);
            return this;
        },
        replaceClass: function(c1, c2) {
            return this.filter('.' + c1).removeClass(c1).addClass(c2).end();
        },
        hoverClass: function(className) {
            className = className || "hover";
            return this.hover(function() {
                $(this).addClass(className);
            }, function() {
                $(this).removeClass(className);
            });
        },
        heightToggle: function(animated, callback) {
            animated ?
                this.animate({ height: "toggle" }, animated, callback) :
                this.each(function(){
                    jQuery(this)[ jQuery(this).is(":hidden") ? "show" : "hide" ]();
                    if(callback)
                        callback.apply(this, arguments);
                });
        },
        heightHide: function(animated, callback) {
            if (animated) {
                this.animate({ height: "hide" }, animated, callback);
            } else {
                this.hide();
                if (callback)
                    this.each(callback);                
            }
        }
        
    });
    
    $.widget("om.omTree", {
        // target equal the li elements
        _applyClasses: function(target) {
            var self = this,
                options = this.options;
            target.filter(":last-child:not(ul)").addClass(CLASSES.last);
            target.filter((options.collapsed ? "" : "." + CLASSES.closed) + ":not(." + CLASSES.open + ")").find(">ul").hide();
            
            target.filter("."+CLASSES.last+",."+CLASSES.lastCollapsable)
            .not(target.filter(":last-child:not(ul)")).removeClass(CLASSES.last+" "+CLASSES.lastCollapsable);
            target.filter(":has(>ul)")
                        .find(">span")
                                .add($("a", target))
                                .hoverClass()
                                .end()
                            .end()
                        .has("ul:hidden")
                            .addClass(CLASSES.expandable)
                            .replaceClass(CLASSES.last, CLASSES.lastExpandable)
                            .end()
                        .has("ul:visible")
                            .addClass(CLASSES.collapsable)
                            .replaceClass(CLASSES.last, CLASSES.lastCollapsable)
                            .end()
                        ;
            if (!self.options.prerendered) {
                // create hitarea if not present
                var hitarea = target.find("div." + CLASSES.hitarea);
                if (!hitarea.length)
                    hitarea = target.filter(":has(>ul)").prepend("<div class=\"" + CLASSES.hitarea + "\"/>").find("div." + CLASSES.hitarea);
                hitarea.removeClass().addClass(CLASSES.hitarea).each(function() {
                    var classes = "";
                    $.each($(this).parent().attr("class").split(" "), function() {
                        classes += this + "-hitarea ";
                    });
                    $(this).addClass( classes );
                });
            }
            
            // apply event to hitarea
            target.find("div." + CLASSES.hitarea).click(function() {
                self.toggler.call(this, $(this));
            });
        },
        
        /**
         * get the parent node
         * param: DOM object, from which to search it's parent node 
         */
        _getParentNode :function (target){
            if(target.hasClass("om-tree-node")){
                return $(target).parent().parent();
            }
            return null;
        },
        
        _setParentCheckbox: function (node){
            var pnode = this._getParentNode(node);
            if (pnode){
                var ck = $(pnode).find(">ul").find(">li").find('div.tree-checkbox');
                var allChild = ck.length;
                var full_len = ck.filter(".checkbox_full").length;
                var part_len = ck.filter(".checkbox_part").length;
                pnode.find(">div.tree-checkbox").removeClass("checkbox_full").removeClass("checkbox_part");
                if(full_len == allChild) {
                    pnode.find(">div.tree-checkbox").addClass("checkbox_full");
                } else if(full_len > 0 || part_len > 0) {
                    pnode.find(">div.tree-checkbox").addClass("checkbox_part");
                }
                this._setParentCheckbox(pnode);
            }
        },
        
        _setChildCheckbox : function (node, checked){
            var childck = node.find(">ul").find('.tree-checkbox');
            childck.removeClass('checkbox_part checkbox_full');
            if(checked) {
                childck.addClass("checkbox_full");
            }
        },
        
        // target equal the li elements
        _applyEvents: function(target) {
            var self = this;
            target.find(">span").find(">a")
                    .bind("click", function(event){
                        var node = self.element.data("nodes")[$(this).parent().parent().attr("id")];
                        self._trigger("onClick", null, node);
                        self.select(node);
                    }).bind("dblclick", function(event){
                        var node = self.element.data("nodes")[$(this).parent().parent().attr("id")];
                        self._trigger("onDblClick", null, node);
                    });
            target.find(".tree-checkbox")
                .bind("click", function(event){
                    var node = $(this).parent();
                    self._toggleCheck(node, self.ischeck(node));
                });
            target.filter(":has(>ul)")
                .find(">span")
                    // dblclick to toggle the tree node
                    .unbind("dblclick.treeview")
                    .bind("dblclick.treeview", function(event) {
                        // don't handle click events on children, eg. checkboxes
                        if ( $(event.target, this) )
                            self.toggler.apply($(this).next(), $(this).next());
                        var $this = $(this).parent();
                        if ($this.hasClass("hasChildren")) {
                            var childList = $this.removeClass("hasChildren").find("ul");
                            var init_dataSource = self.element.data("init_dataSource");
                            if(typeof init_dataSource == "string") {
                                self._ajaxLoad(childList, init_dataSource);
                            }
                        }
                    });
        },
        options: /** @lends omTree#*/{
            initExpandLevel: 0,
            /**
             * 数据源属性，可以设置为后台获取数据的URL，比如dataSource : 'treedata.json'
             * 也可以设置为静态数据，数据必须为Json格式数组，比如dataSource : [{"id":"n1","text":"iPhone"},{"id":"n2","text":"iPad"}]；
             * 其中Json格式为
             * <pre>
             * {
             *     id:'node1', // 树结点id，此id为必需属性
             *     text:'node1', // 树结点显示文本  
             *     expanded:true, // 是否默认展开 
             *     classes:'folder', // 树结点样式，默认有folder和file，用户可自定制此样式 
             *     hasChildren: false // 树结点懈加载的情况下，该结点在展开时自动向后台取数
             * }
             * </pre>
             * @type String,Array[JSON]
             * @default 无
             * @example
             * dataSource : 'treedata.json' 
             * 或者
             * dataSource : [{"id":"n1","text":"iPhone"},{"id":"n2","text":"iPad"}]
             */
            lineHover: true,
            /**
             * 树结点是否显示图标.
             * @type Boolean
             * @default true
             * @example
             * $("mytree").omTree({showIcon:false});
             */
            showIcon: true,
            showLine: true,
            /**
             * 是否显示checkbox
             * @type Boolean
             * @default false
             */
            showCheckbox: false,
            /**
             * 是否级联选中，该属性在showCheckbox为true的时候生效
             * @type Boolean
             * @default true
             */
            cascadeCheck: true,
            draggable: false,
            filter: null,
            // before refresh the node ,you can change the node
            nodeFomatter:null
        },
        _create: function() {
            var options = this.options;
            if ( options.toggle ) {
                var callback = options.toggle;
                options.toggle = function() {
                    return callback.apply($(this).parent()[0], arguments);
                };
            }
            var elem = this.element;
            
            elem.data("nodes", []);
            elem.data("selected", "");
            
            // add treeview class to activate styles
            elem.addClass("treeview");
            
        },
        
        // target equal the ul element
        updateNode: function(target) {
            var options = this.options;
            // prepare branches and find all tree items with child lists
            var branches = target.find("li");
            //.prepareBranches(options);
            
            this._applyClasses(branches);
            this._applyEvents(branches);
            
            if(options.control) {
                this._treeController(this, options.control);
            }
        },
        
        // handle toggle event
        toggler: function(target) {
            var self = this;
            target
                .parent()
                // swap classes for hitarea
                .find(">.hitarea")
                    .swapClass( CLASSES.collapsableHitarea, CLASSES.expandableHitarea )
                    .swapClass( CLASSES.lastCollapsableHitarea, CLASSES.lastExpandableHitarea )
                .end()
                // swap classes for parent li
                .swapClass( CLASSES.collapsable, CLASSES.expandable )
                .swapClass( CLASSES.lastCollapsable, CLASSES.lastExpandable )
                // find child lists
                .find( ">ul" )
                // toggle them
                .heightToggle( self.animated, self.toggle );
            if ( self.unique ) {
                target.parent()
                    .siblings()
                    // swap classes for hitarea
                    .find(">.hitarea")
                        .replaceClass( CLASSES.collapsableHitarea, CLASSES.expandableHitarea )
                        .replaceClass( CLASSES.lastCollapsableHitarea, CLASSES.lastExpandableHitarea )
                    .end()
                    .replaceClass( CLASSES.collapsable, CLASSES.expandable )
                    .replaceClass( CLASSES.lastCollapsable, CLASSES.lastExpandable )
                    .find( ">ul" )
                    .heightHide( self.animated, self.toggle );
            }
        },
        
        _init: function() {
            var options = this.options,
                target = this.element,
                source = options.dataSource;
            target.data("init_dataSource", source);
            if(source) {
                if(typeof source == 'string'){
                    this._ajaxLoad(target, source);
                }else if(typeof source == 'object'){
                    for(var i = 0; i < source.length; i++) {
                        this._appendNodes.apply(this, [target, source[i]]);
                    }
                    this.updateNode(target);
                }                
            }
        },
        
        _ajaxLoad:function(target, url){
            var self=this;
            $.ajax({
                url: url,
                method: 'POST',
                dataType: 'json',
                success: function(data){
                    for(var i = 0; i < data.length ; i++) {
                        self._appendNodes.apply(self, [target, data[i]]);
                    }
                    self.setData(data);
                    self.updateNode(target);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    
                }
            });
        },
        /* -------------------- check and select node ------------------- */
        check: function(target) {
            this._toggleCheck($("#" + target.id), false);
        },
        uncheck: function(target) {
            this._toggleCheck($("#" + target.id), true);
        },
        
        // target equal le elem
        _toggleCheck: function(target, checked) {
            var checkbox_item = target.find(">div.tree-checkbox");
            if(checked) {
                checkbox_item
                .removeClass("checkbox_part")
                .removeClass("checkbox_full");
            } else {
                checkbox_item
                .removeClass("checkbox_part")
                .addClass("checkbox_full");
            }
            if(this.options.cascadeCheck) {
                this._setChildCheckbox(target, !checked);
                this._setParentCheckbox(target);
            }
        },
        
        checkAll: function(checked) {
            if(checked) {
                this.element
                    .find(".tree-checkbox")
                    .removeClass("checkbox_part")
                    .addClass("checkbox_full");
            } else {
                this.element
                    .find(".tree-checkbox")
                    .removeClass("checkbox_part")
                    .removeClass("checkbox_full");
            }
        },
        
        ischeck: function(target) {
            var checkbox_item = target.find(">div.tree-checkbox");
            var checked = false;
            if(checkbox_item.hasClass("checkbox_full")) {
                checked = true;
            }
            return checked;
        },
        
        getChecked: function(checked) {
            var self = this,
                nodes = [];
            var filter_config = checked?".checkbox_full":":not(.checkbox_full)";
            this.element
                .find(".tree-checkbox")
                .filter(filter_config).each(function(i,name){
                    nodes.push(self.element.data("nodes")[$(this).parent().attr("id")]);
                });
            return nodes;
        },
        
        select: function(target) {
            var self = this;
            var node = $("#" + target.id);
            var a = $(" >span >a", node);
            a.addClass("selected");
            var oldSelected = self.element.data("selected");
            var curSelected = node.attr("id");
            if(oldSelected != "" && !(oldSelected == curSelected)) {
                $("#" + oldSelected + " >span >a").removeClass("selected");
            }
            self.element.data("selected", curSelected);
            self._trigger("onSelect", null, target);
        },
        unselect: function(target) {
            var self = this;
            var node = $("#" + target.id);
            var a = $(" >span >a", node);
            a.removeClass("selected");
            var oldSelected = self.element.data("selected");
            var curSelected = node.attr("id");
            if( oldSelected == curSelected) {
                self.element.data("selected", "");
            }
        },
        getSelected: function() {
            return this.element.data("nodes")[this.element.data("selected")];
        },
        
        /* -------------------- find node ------------------- */
        findNodes: function(key,value,pNode,deep) {
            var result=[];
            var data = pNode ? pNode.children :this.getData();
            if(data && data.length > 0) {
                for(var i in data){
                  result=this._searchNode.apply(data[i],[key,value, this._searchNode,result,false,deep]);
                }
           }
            return result.length>0?result:null;
        },
        
        findNode: function(key,value,pNode,deep){
            var res,data = pid ? pNode :this.getData();
            if(data && data.length > 0) {
                for(var i in data){
                  res=this._searchNode.apply(data[i],[key,value, this._searchNode,[],true,deep]);
                  if(res != null){
                      return res;
                  }
               }
           }
            return null;
        },
        
        findById : function(id) {
            return this.element.data("nodes")[id];
        },
        
        findNodesBy: function(fn,pNode,deep){
            var res,data = pid ? pNode.children : this.getData();
            var result=[];
            for(var i in data){
                if(fn.call(data[i])===true){
                    result.push(data[i]);
                }
                if(deep&&data[i].children){
                    res = this.findNodesBy(fn, data[i].id, deep);
                    if(res.length>0){
                        result.push(res);
                    }
                }
            }
            return result.length>0?result:null;
        },
               
        findNodeBy: function(fn, pNode, deep){
            var res,data = pid ? pNode.children : this.getData();
            for(var i in data){
                if(fn.call(data[i])===true){
                    return data[i];
                }
                if(deep){
                    res = this.findNodeBy(fn, data[i].id, deep);
                    if(res != null){
                        return res;
                    }
                }
            }
            return null;
         },
         
        _searchNode: function(key,value, _searchNode,result,isSingle,deep) {
            if(isSingle){
                if(this[key]==value)
                return this;
                if(this.children && this.children.length&&deep) {
                    for(var i in this.children){
                        var temp=_searchNode.apply(this.children[i],[key,value,_searchNode,[],true]);
                        if(temp) return temp;
                    }
                }
            }else{
                if(this[key]==value){      
                    result.push(this);
                }
                if(this.children && this.children.length&&deep) {
                    $.each(this.children, _searchNode, [key,value, _searchNode,result,false]);
                }
                return result;
            }
        },
        getParent: function(target) {
            var pid=this.element.data("nodes")["pid"+target.id];
            return this.findById(pid);
        },
        getChildren: function(target) {
            return target.children;
        },
        getData: function() {
            return this.options.dataSource;
        },
        setData: function(data) {
            this.options.dataSource = data;
        },
        /* -------------------- expand and collapse node ------------------- */
        expand: function(target) {
            if(target.id) {
                this._collapseHandler(CLASSES.expandable, $("#" + target.id));
            }
        },
        collapse: function(target) {
            if(target.id) {
                this._collapseHandler(CLASSES.collapsable, $("#" + target.id));
            }
        },
        expandAll: function() {
            this._collapseHandler(CLASSES.expandable, this.element);
        },
        collapseAll: function() {
            this._collapseHandler(CLASSES.collapsable, this.element);
        },
        
        // filter: the class filt by the toggler
        // elem: from witch element
        _collapseHandler: function(filter, target) {
            this.toggler.call(this, $("div." + CLASSES.hitarea, target).filter(function(){
                return filter ? $(this).parent("." + filter).length : true;
            }));
            return false;
        },
        /* -------------------- edit node ------------------- */        
        refresh: function(target) {
            var data = this.getData(target);
            for(var i = 0; i < data.length ; i++) {
                this._appendNodes.apply(this, [target, data[i]]);
            }
        },
        
        // target equal the ul emelemt
        _appendNodes: function(target, node,bNode) {
            var self=this;
            var checkable = self.options.showCheckbox;
            var nodeClass = "om-tree-node " + (checkable?"treenode-checkable":"");
            var current = $("<li class=\'" + nodeClass + "\'/>")
            .attr("id", node.id || "")
            .html((checkable?"<div class='tree-checkbox'/>":"") + "<span><a href='#'>" + node.text + "</a></span>");
            
            if(bNode){
                current.insertBefore($("#"+bNode.id));
            }else{
                current.appendTo(target);
            }
            
            // save the node pointer to the caches
            if(node.id) {
                var caches = this.element.data("nodes");
                caches[node.id] = node;
                var pid=$("#"+node.id).parent().parent("li").attr("id");
                if(pid){
                    caches["pid"+node.id]=pid;
                }
            }
            // if we don't set the classes, that mabey move the class to _applyClasses logic
            if (node.classes) {
                current.children("span").addClass(node.classes);
            }
            if (node.expanded) {
                current.addClass("open");
            }
            if (node.hasChildren || node.children && node.children.length) {
                if(self.options.showIcon) {
                    current.children("span").addClass("folder");
                }
                var branch = $("<ul/>").css("display", node.expanded ? "block" : "none").appendTo(current);
                if (node.hasChildren) {
                    current.addClass("hasChildren");
                }
                if (node.children && node.children.length) {
                    for(var i = 0; i < node.children.length; i++) {
                        this._appendNodes.apply(this, [branch, node.children[i]]);
                    }
                }
            } else {
                if(self.options.showIcon) {
                    current.children("span").addClass("file");
                }
            }
        },
        
        remove: function(target,pNode) {
            var flag,data=pNode?pNode.children:this.getData();
            for(var i in data){
                if(data[i]==target){
                    var ids=[];
                    ids=this._findChildrenId(target, ids);
                    ids.push(target.id);
                    $("#"+target.id).remove();
                    for(var n=0,len=ids.length;n<len;n++){
                        delete this.element.data("nodes")[ids[n]];
                        delete this.element.data("nodes")["pid"+ids[n]];
                    }
                    data.splice(i,1);
                    if(pNode&&pNode.id&&data.length<1){
                        var parentid=this.element.data("nodes")["pid"+pNode.id];
                        $("#"+pNode.id).remove();
                        this._appendNodes($("#"+parentid).children("ul"),pNode);
                        this._applyClasses($("#"+parentid).children("ul").find("li"));
                    }
                    return data;
                }else if(data[i].children){
                    flag=this.remove(target,data[i]);
                    if(flag){
                        data[i].children=flag;
                        return data;
                    }
                }
            }
            return false;
            
        },
        
        _findChildrenId:function(target,ids){
            if(target.children){
                for(var i=0,children=target.children,len=children.length;i<len;i++){
                    ids.push(children[i].id);
                    if(children[i].children){
                        this._findChildrenId(children[i],ids);
                    }
                }
            }
            return ids;
        },
        
        insert: function(target,pNode,bNode) {
            if(bNode){
                pNode=pNode||this.findById(this.element.data("nodes")["pid"+bNode.id]);
            }
            var index,data=pNode?pNode.children:this.getData();
            var parent=pNode?$("#"+pNode.id).children("ul"):this.element;
            if(pNode&&!pNode.children){
                parent=$("#"+pNode.id).parent("ul");
                var spNode=this.findById(this.element.data("nodes")["pid"+pNode.id]);
                this.remove(pNode);
                pNode.children=[];
                pNode.children.push(target);
                target=pNode;
                data=spNode?spNode:this.getData();
            }
            if(bNode&&((index=$.inArray(bNode, data))>=0)){
                this._appendNodes("",target,bNode);
                data.splice(index,0,target);
            }else{
                this._appendNodes(parent, target);
                data.push(target);
            }
            var m=parent.find("li").filter("."+CLASSES.last+",."+CLASSES.lastCollapsable)
            .not(parent.find("li").filter(":last-child:not(ul)"));
            m.removeClass(CLASSES.last+" "+CLASSES.lastCollapsable);
            m.find(" >div").removeClass(CLASSES.lastCollapsableHitarea);
            var tdom=$("#"+target.id).add($("#"+target.id).find("li"));
            this._applyClasses(tdom);
            this._applyEvents(tdom);
            
            //this._applyEvents($("#"+target.id));
        },
        modify: function(target,newNode,pNode) {
            var nextNode=$("#"+target.id).next().first(),bNode;
            pNode=pNode||this.findById(this.element.data("nodes")["pid"+target.id]);
            if(nextNode.is("ul")||nextNode.is("li"))
                bNode=this.findById(nextNode.attr("id"));
            this.remove(target, pNode);
            this.insert(newNode, pNode, bNode);
        },
        /* -------------------- disable and enable node ------------------- */
        disable: function() {
            
        },
        enable: function() {
            
        }
        
        // event
        // onClick, onDblClick, onRrightClick
        // onBeforeLoad, onSuccess, onError
        // onDrag, onDrop
        // onBeforeExpand, onBeforeCollapse
        // onExpand, onCollapse
        // onCheck, onSelect
    });
})(jQuery);