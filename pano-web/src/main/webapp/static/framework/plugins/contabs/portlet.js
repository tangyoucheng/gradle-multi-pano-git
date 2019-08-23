jQuery(window).on("load",function() {
	"use strict";
	jQuery(".pre-loader").fadeToggle("medium");
});
jQuery(window).on("load resize", function () {
	$(".customscroll").mCustomScrollbar({
		theme: "minimal-dark",
		advanced:{
			autoScrollOnFocus: false,
		},
	});
});
jQuery(document).ready(function(){
	"use strict";
	// Background Image
	jQuery(".bg_img").each( function ( i, elem ) {
		var img = jQuery( elem );
		jQuery(this).hide();
		jQuery(this).parent().css( {
			background: "url(" + img.attr( "src" ) + ") no-repeat center center",
		});
	});

	// click to scroll
	$('.collapse-box').on('shown.bs.collapse', function () {
		$(".customscroll").mCustomScrollbar("scrollTo",$(this));
	});

	// code split
	var entityMap = {
		"&": "&amp;",
		"<": "&lt;",
		">": "&gt;",
		'"': '&quot;',
		"'": '&#39;',
		"/": '&#x2F;'
	};
	function escapeHtml(string) {
		var escapeReg=new RegExp('[&<>\"\'\/]',"g");
//		return String(string).replace(/[&<>"'\/]/g, function (s) {
		return String(string).replace(escapeReg, function (s) {
			return entityMap[s];
		});
	}
	//document.addEventListener("DOMContentLoaded", init, false);
	window.onload = function init()
	{
		var codeblock = document.querySelectorAll("pre code");
		if(codeblock.length)
		{
			for(var i=0, len=codeblock.length; i<len; i++)
			{
				var dom = codeblock[i];
				var html = dom.innerHTML;
				html = escapeHtml(html);
				dom.innerHTML = html;
			}
			$('pre code').each(function(i, block) {
				hljs.highlightBlock(block);
			});
		}
	}

	// form-control on focus add class
	$(".form-control").on('focus',function(){
		$(this).parent().addClass("focus");
	})
	$(".form-control").on('focusout',function(){
		$(this).parent().removeClass("focus");
	})

	// Dropdown Slide Animation
	$('.dropdown').on('show.bs.dropdown', function(e){
		$(this).find('.dropdown-menu').first().stop(true, true).slideDown(300);
	});
	$('.dropdown').on('hide.bs.dropdown', function(e){
		$(this).find('.dropdown-menu').first().stop(true, true).slideUp(200);
	});

	// sidebar menu icon
	$('.menu-icon').on('click', function(){
		$(this).toggleClass('open');
		$('.left-side-bar').toggleClass('open');
	});
	$('.brand-logo').on('click', function(){
		$('.left-side-bar').toggleClass('open');
		
        if($('.left-side-bar').hasClass("open")){
		    $('.left-side-bar').show();
			
			if ($(window).width() > 1200) {
				$('.left-side-bar').css("margin-left", "0px");
				$('.main-container').css("margin-left", "0px");
			}
		} else {
		    $('.left-side-bar').hide();
			
			if ($(window).width() > 1200) {
				$('.left-side-bar').css("margin-left", "-251px");
				$('.main-container').css("margin-left", "-251px");
			}
		}
	});

	var w = $(window).width();
	$(document).on('touchstart click', function(e){
		//if($(e.target).parents('.left-side-bar').length == 0 && !$(e.target).is('.menu-icon, .menu-icon span'))
		//{
		//	$('.left-side-bar').removeClass('open');
		//	$('.menu-icon').removeClass('open');
		//};
	});
	$(window).on('resize', function() {
		var w = $(window).width();
		if ($(window).width() > 1200) {
		    $('.left-side-bar').show();
			$('.left-side-bar').toggleClass('open');
			//$('.menu-icon').removeClass('open');
		} else {
		    $('.left-side-bar').hide();
			$('.left-side-bar').removeClass('open');
			//$('.left-side-bar').css("margin-left", "-251px");
		}
	});


	// sidebar menu Active Class
	$('#accordion-menu').each(function(){
		var vars = window.location.href.split("/").pop();
		$(this).find('a[href="'+vars+'"]').addClass('active');
	});

	

	// var color = $('.btn').data('color');
	// console.log(color);
	// $('.btn').style('color'+color);
	$("[data-color]").each(function() {
		$(this).css('color', $(this).attr('data-color'));
	});
	$("[data-bgcolor]").each(function() {
		$(this).css('background-color', $(this).attr('data-bgcolor'));
	});
	$("[data-border]").each(function() {
		$(this).css('border', $(this).attr('data-border'));
	});

	$("#accordion-menu").vmenuModule({
		Speed: 400,
		autostart: false,
		autohide: true
	});

	if ($(window).width() > 1200) {
		$('.left-side-bar').addClass('open');
	}
});

// sidebar menu accordion
(function($) {
	$.fn.vmenuModule = function(option) {
		var obj,
		item;
		var options = $.extend({
			Speed: 220,
			autostart: true,
			autohide: 1
		},
		option);
		obj = $(this);

		item = obj.find("ul").parent("li").children("a");
		item.attr("data-option", "off");

		item.unbind('click').on("click", function() {
			var a = $(this);
			if (options.autohide) {
				a.parent().parent().find("a[data-option='on']").parent("li").children("ul").slideUp(options.Speed / 1.2,
					function() {
						$(this).parent("li").children("a").attr("data-option", "off");
						$(this).parent("li").removeClass("show");
					})
			}
			if (a.attr("data-option") == "off") {
				a.parent("li").children("ul").slideDown(options.Speed,
					function() {
						a.attr("data-option", "on");
						a.parent('li').addClass("show");
					});
			}
			if (a.attr("data-option") == "on") {
				a.attr("data-option", "off");
				a.parent("li").children("ul").slideUp(options.Speed)
				a.parent('li').removeClass("show");
			}
		});
		if (options.autostart) {
			obj.find("a").each(function() {

				$(this).parent("li").parent("ul").slideDown(options.Speed,
					function() {
						$(this).parent("li").children("a").attr("data-option", "on");
					})
			})
		}
		else{
			obj.find("a.active").each(function() {

				$(this).parent("li").parent("ul").slideDown(options.Speed,
					function() {
						$(this).parent("li").children("a").attr("data-option", "on");
						$(this).parent('li').addClass("show");
					})
			})
		}

	}
})(window.jQuery || window.Zepto);



