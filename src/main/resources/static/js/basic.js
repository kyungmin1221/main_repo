const host = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();

    if (auth) {
        // 모든 AJAX 요청에 대해 Authorization 헤더 설정
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
    } else {
        // 로그인 페이지로 리디렉션
        window.location.href = host + '/api/user/login-page';
    }
});

function logout() {
    // 토큰 삭제 후 로그인 페이지로 이동
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + '/api/user/login-page';
}

function getToken() {
    let auth = Cookies.get('Authorization');

    // 저장된 토큰이 있는 경우, Bearer 접두어 추가
    if (auth && auth.indexOf('Bearer') === -1) {
        return 'Bearer ' + auth;
    }

    // 토큰이 없거나 이미 Bearer 접두어가 포함된 경우
    return auth || '';
}
