const getMenuTree = () => {
    $.ajax({
        url: '/api/menus',
        method: 'GET',
        success: (response) => {
            console.log(response);
        },
        error: (error) => {
            console.log(error);
        }
    });
}

$(document).ready(() => {
    getMenuTree();
});