const getMenuTree = () => {
    $.ajax({
        url: '/api/menus',
        method: 'GET',
        success: (response) => {
            console.log(response);
        },
        error: (error) => {
            window.location.href = error.responseJSON.loginUrl;
        }
    });
}

$(document).ready(() => {
    getMenuTree();
});