

/*************** COLORS TO BE ERASED WHEN INSTALLING THE THEME ***********/

$(document).ready(function() {   
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "js-plugin/jquery-cookie/jquery.cookie.js";
    $("body").append(s);  

    if($.cookie("css")) {
        $("#colors").attr("href",$.cookie("css"));
    }
    $(".switcher li a").click(function() { 

        $("#colors").attr("href",$(this).attr("href"));
        $.cookie("css",$(this).attr("href"));
        return false;
    });

});

/*************** COLORS TO BE ERASED WHEN INSTALLING THE THEME ***********/




/* MAIN MENU (submenu slide and setting up of a select box on small screen)*/
(function() {
    var $mainMenu = $('#mainMenu').children('ul');
    $mainMenu.on('mouseenter', 'li', function() {
        var $this = $(this),
        $subMenu = $this.children('ul');

        if( $subMenu.length ) $this.addClass('hover');
        else {
            if($this.parent().is($(':gt(1)', $mainMenu))){
                $this.addClass('Shover').stop().hide().fadeIn('slow').end();
            }else{
                $this.addClass('Shover');
            }
        }
        if($this.parent().is($(':gt(1)', $mainMenu))){

            $subMenu.css('display', 'block');
            $subMenu.stop(true, true).animate({
                left:144, 
                opacity:1
            }, 300,'easeOutQuad');
        }else{
            $subMenu.stop(true, true).slideDown('fast','easeInQuad'); 
        }

    }).on('mouseleave', 'li', function() {
        var $nthis = $(this);
        if($nthis.parent().is($(':gt(1)', $mainMenu))){
            $nthis.children('ul').css('left', 130).css('opacity', 0).css('display', 'none');
        }else{
            $nthis.removeClass('hover').removeClass('Shover').children('ul').stop(true, true).hide();
        }
        $subMenu = $nthis.children('ul');
        if( $subMenu.length ) $nthis.removeClass('hover');
        else $nthis.removeClass('Shover');
    }).on('touchend', 'li ul li a', function(e) {
        var el = $(this);
        var link = el.attr('href');
        window.location = link;
    });
    // ul to select
    var optionsList = '<option value="" selected>Navigate...</option>';
    $mainMenu.find('li').each(function() {
        var $this   = $(this),
        $anchor = $this.children('a'),
        depth   = $this.parents('ul').length - 1,
        indent  = '';

        if( depth ) {
            while( depth > 0 ) {
                indent += ' - ';
                depth--;
            }
        }
        optionsList += '<option value="' + $anchor.attr('href') + '">' + indent + ' ' + $anchor.text() + '</option>';
    }).end().after('<select class="responsive-nav">' + optionsList + '</select>');

    $('.responsive-nav').on('change', function() {
        window.location = $(this).val();
    }); 

})();

