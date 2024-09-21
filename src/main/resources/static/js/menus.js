const getMenuTree = () => {
    $.ajax({
        url: '/api/menus',
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
        .on('move_node.jstree', function (e, data) {
            dragAndDrop(data.node.id, data.parent, data.position, data.node.parents.length, data.old_parent);
        });
}

$(document).ready(() => {
    getMenuTree();
});