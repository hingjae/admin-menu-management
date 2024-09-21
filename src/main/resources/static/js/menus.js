const getMenuTree = () => {
    $.ajax({
        url: '/api/menu-tree',
        method: 'GET',
        contentType: 'application/json',
        success: (response) => {
            drawMenuTree(response.data);
        },
        error: (error) => {
            console.error("Authentication Failure");
        }
    });
}

const drawMenuTree = (data) => {
    $('#menuTree').jstree({
        'core': {
            'animation': 0,
            'themes': {
                "variant" : "large",
            },
            'data': data,
            "check_callback" : true
        },
        "types": {
            'default': {
                'icon': false,
            },
        },
        'plugins': ['dnd', "types", "state"]  // drag-and-drop 플러그인 활성화
    })
}

$(document).ready(() => {
    getMenuTree();
    $('#treeExpandBtn').on('click', function () {$('#menuTree').jstree('open_all');});
    $('#treeCollapseBtn').on('click', function () {$('#menuTree').jstree('close_all');});
});
