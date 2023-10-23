/*********************
//* jQuery Multi Level CSS Menu #2- By Dynamic Drive: http://www.dynamicdrive.com/
//* Last update: Nov 7th, 08': Limit # of queued animations to minmize animation stuttering
//* Menu avaiable at DD CSS Library: http://www.dynamicdrive.com/style/
*********************/
//Update: April 12th, 10: Fixed compat issue with jquery 1.4x
//Specify full URL to down and right arrow images (23 is padding-right to add to top level LIs with drop downs):
var arrowimages={down:['downarrowclass', './cmm/images/down.png', 23], right:['rightarrowclass', './cmm/images/right.png']};
var navName = '';
var jqueryslidemenu={
	animateduration: {over: 200, out: 100}, //duration of slide in/ out animation, in milliseconds	
	buildmenu:function(menuid, arrowsvar){
		jQuery(document).ready(function(){
			$('#'+menuid+' a').click(function(){
				navName = $(this).attr('alt');
				$('.subUl').hide();
				if (menuid == 'slidemenu02' || menuid == 'slidemenu03') {setMyPageTeamTopMenu();
				} else {iniTopMenu();}
			});
			
			function iniTopMenu(){
				$('.topmenu').each(function(){
					if($(this).attr('alt') == navName) {$(this).addClass('topMenuBG');$(this).css('color', '#21f0f8');
					} else {$(this).removeClass('topMenuBG');$(this).css('color', '#ffffff');}
				});
			}			
			// [My page][Team]메뉴가 클릭 됐을때 , 이미지 설정
			function setMyPageTeamTopMenu(){
				$('.mypageTeamMenu').each(function(){
					if($(this).attr('alt') == navName) {$(this).addClass('mypageTeamMenuBG');$(this).css('color', '#FFFFFF');
					} else {$(this).removeClass('mypageTeamMenuBG');$(this).css('color', '#ffffff');}
				});
			}
			
			var $mainmenu=$("#"+menuid+">ul");
			var $headers=$mainmenu.find("ul").parent();
			var topSectionH = 30;
			$headers.each(function(i){
				var $curobj=$(this);
				var $subul=$(this).find('ul:eq(0)');
				var subulw = $subul.outerWidth();
				var subulh = topSectionH + $subul.outerHeight();
				var w = 131+this.offsetWidth;
				this._dimensions={w:w, h:topSectionH, subulw:subulw, subulh:subulh};
				//alert(this._dimensions.w + ":"+this._dimensions.h + ":"+this._dimensions.subulw + ":"+this._dimensions.subulh);
				this.istopheader=$curobj.parents("ul").length==1? true : false;
				$subul.css({top:this.istopheader? this._dimensions.h+"px" : 0});
				$curobj.children("a:eq(0)").css(this.istopheader? {paddingRight: arrowsvar.down[2]} : {}).append(
					'<img src="'+ (this.istopheader? arrowsvar.down[1] : arrowsvar.right[1])
					+'" class="' + (this.istopheader? arrowsvar.down[0] : arrowsvar.right[0])
					+ '" style="border:0;" />'
				);
				$curobj.hover(
					function(e){
						var $targetul=$(this).children("ul:eq(0)");
						var top = topSectionH + $(this).offset().top;
						this._offsets={left:$(this).offset().left, top:top};
						var menuleft_=this.istopheader? 0 : this._dimensions.w;
						//alert('1:'+this._offsets.left+menuleft_+this._dimensions.subulw+',2:'+$(window).width()+',3:'+this.istopheader+',4:'+-this._dimensions.subulw+this._dimensions.w +',5:'+-this._dimensions.w+',6:'+-this._dimensions.w+'7:'+menuleft_)
						//if(this._offsets.left+menuleft_+this._dimensions.subulw>$(window).width()){alert(1);}
						//else{if(this.istopheader){alert(2);}else{alert(this._dimensions.w);}}
						var menuleft=(this._offsets.left+menuleft_+this._dimensions.subulw>$(window).width())? (this.istopheader? -this._dimensions.subulw+this._dimensions.w : -this._dimensions.w) : menuleft_;
						if ($targetul.queue() && $targetul.queue().length<=1) //if 1 or less queued animations
							$targetul.css({left:menuleft+"px", width:this._dimensions.subulw+'px'}).slideDown(jqueryslidemenu.animateduration.over);
					},
					function(e){
						var $targetul=$(this).children("ul:eq(0)");
						$targetul.slideUp(jqueryslidemenu.animateduration.out);
					}
				); //end hover
				$curobj.click(function(){
					$(this).children("ul:eq(0)").hide();
				});
			}); //end $headers.each()
			$mainmenu.find("ul").css({display:'none', visibility:'visible'});
		}); //end document.ready
	}
};

//build menu with ID="myslidemenu" on page:
//jqueryslidemenu.buildmenu("slidemenu", arrowimages);
//jqueryslidemenu.buildmenu("slidemenu02", arrowimages);
