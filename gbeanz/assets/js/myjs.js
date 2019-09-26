$(document).ready(function() {
  
  $(window).scroll(function() {
    
    if ($(window).scrollTop() > 150) {
      $('header').css('opacity', '0');
    $('header').css('transition', 'all 1s ease');
    }
  });
});

var position = $(window).scrollTop(); 

// should start at 0

$(window).scroll(function() {
    var scroll = $(window).scrollTop();
    if(scroll > position) {

    } else {
         console.log('scrollUp');
         $('header').css('opacity', '1');
    }
    position = scroll;
});
