 window.onresize = function () {
     RootSize();
 }

 function RootSize() {
     var docWidth = document.documentElement.clientWidth;
     var objHtml = document.getElementsByTagName('html')[0];
     objHtml.style.fontSize = docWidth / 7.5 + 'px';
 }
 RootSize();