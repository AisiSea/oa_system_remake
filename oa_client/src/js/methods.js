import axios from "axios";
import Vue from "vue";

export default {
    getToken() {
        let token = localStorage.getItem('token');
        if (token !== null && token !== '')
            return token
        token = sessionStorage.getItem('token');
        return token;
    },

    pushToLogin (vueObj, msg) {
        if (vueObj instanceof Vue) {
            vueObj.$store.dispatch('setLoginMsg', msg);
            vueObj.$router.push('/');
        } else console.error("Param is not a Vue type object");
    },

    checkLogin(vueObj) {
        let token = this.getToken();
        if (token === null || token === '') {
            this.pushToLogin(vueObj, '未登录');
        } else axios({
            method: 'POST',
            url: '/token',
            async: false,
            data: {
                token: token
            }
        }).then(res => {
            if (res.data.state !== vueObj.$store.state.SUCCESS_RESPONSE_STATE)
                this.pushToLogin(vueObj, '登录状态异常');
        });
    }
}