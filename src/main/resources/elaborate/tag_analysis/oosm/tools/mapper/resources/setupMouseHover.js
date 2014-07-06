$("<style type='text/css'> .highlight{ border-color: red; border-width: 1px;border-style: solid} </style>").appendTo("head");
$("<input type='hidden' id='xpath' style='width: 100%'></input>").appendTo("body");

function getXPath(node) {
    var path = "";
    var currentNode = node;
    while (currentNode != null) {
        if (currentNode.nodeType == 9) {
            break;
        }
        var count = 1;
        var parentNode = currentNode.parentNode;
        if (parentNode != null) {
            var childNodes = parentNode.childNodes;
            for (var i = 0; i < childNodes.length; i++) {
                var childNode = childNodes.item(i);
                if (childNode.nodeName.toLowerCase() == currentNode.nodeName.toLowerCase()) {
                    if (childNodes.item(i) == currentNode) {
                        break;
                    } else {
                        count++;
                    }
                }
            }
        }
        path = "/" + currentNode.nodeName.toLowerCase() + "[" + count + "]" + path;
        currentNode = parentNode;
    }
    $("#xpath").val(path);
}


var lastTarget = null;
$("*").mousedown(function(event) {
    if (event.target == this) {
        if (lastTarget != null) {
            $(lastTarget).removeClass("highlight");
        }
        $(this).addClass("highlight");
        lastTarget = this;
        getXPath(this);
    }
});

$("*").click(function(event) {
    event.preventDefault();
});

$("input[type='submit']").attr("disabled", "disabled");