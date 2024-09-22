const getMenuTree = () => {
    $.ajax({
        url: '/api/menu-tree',
        method: 'GET',
        success: (response) => {
            drawMenuTree(response.data);
            const menuCount = response.data.length;
            $('#menuCount').text(`Total Counts (${menuCount})`);
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
                "variant": "large",
            },
            'data': data,
            "check_callback": true
        },
        "types": {
            'default': {
                'icon': false,
            },
        },
        'plugins': ['dnd', "types", "state"]  // drag-and-drop 플러그인 활성화
    })
}

const getMenuInfo = (menuId) => {
    $.ajax({
        url: `/api/menus/${menuId}`,
        method: 'GET',
        success: function (json) {
            drawMenuInfo(json.data);
        },
        error: function (error) {
            console.error(error);
        },
    });
}

const drawMenuInfo = (menuDetail) => {
    $('#menuId').text(menuDetail.id || 'N/A');
    $('#parentMenuName').text(menuDetail.parentMenuName || 'N/A');
    $('#menuName').text(menuDetail.name || 'N/A');
    $('#menuOrder').text(menuDetail.menuOrder || 'N/A');
    $('#menuIcon').text(menuDetail.icon || 'N/A');

    $('#defaultMessage').hide();
}

const dragAndDrop = (e, data) => {
    console.log(`id : ${data.node.id}, oldParent : ${data.old_parent}, parent : ${data.parent}, oldPosition : ${data.old_position}, position : ${data.position}`);

    let request = JSON.stringify({
        "id": data.node.id,
        "oldParent": data.old_parent,
        "parent": data.parent,
        "oldPosition": data.old_position,
        "position": data.position,
    });

    $.ajax({
        url: `/api/menus/${data.node.id}`,
        method: 'PATCH',
        contentType: 'application/json', //요청이 json형식으로 전송된다는 것을 알리는 헤더.
        data: request,
        success: function (response) {
            console.log(response);
            // window.location.href = "/admin/menus";
        },
        error: function (error) {
            console.error(error)
        },
    })
}

$(document).ready(() => {
    getMenuTree();
    $('#treeExpandBtn').on('click', () => {$('#menuTree').jstree('open_all');});
    $('#treeCollapseBtn').on('click', () => {$('#menuTree').jstree('close_all');});
    $('#menuTree').on("select_node.jstree", (e, _data) => {getMenuInfo(_data.node.id);});
    $('#menuTree').on('move_node.jstree', (e, data) => {dragAndDrop(e, data)});
});
