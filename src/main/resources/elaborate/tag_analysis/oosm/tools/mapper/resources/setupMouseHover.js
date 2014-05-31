$("<style type='text/css'> .highlight{ border-color: red; border-width: 1px;border-style: solid} </style>").appendTo("head");
$("*").mouseenter(function(event) {
    if (event.target == this) {
        $(this).addClass("highlight");
    }
});
$("*").mouseleave(function() {
    $(this).removeClass("highlight");
});
   