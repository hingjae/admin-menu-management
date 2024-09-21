const getMenuTree = () => {
    $.ajax({
        url: '/api/menu-tree',
        method: 'GET',
        contentType: 'application/json',
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
        contentType: 'application/json',
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

$(document).ready(() => {
    getMenuTree();
    $('#treeExpandBtn').on('click', () => {$('#menuTree').jstree('open_all');});
    $('#treeCollapseBtn').on('click', () => {$('#menuTree').jstree('close_all');});
    $('#menuTree').on("select_node.jstree", (e, _data) => {getMenuInfo(_data.node.id);});
});
