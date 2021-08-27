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
                this.pushToLogin(vueObj, '登录过期或无效');
        });
    },

    downloadFile(res) {
        let blob = new Blob([res.data],  { type: 'application/octet-stream;charset=utf-8' });
        let href = window.URL.createObjectURL(blob);  // 创建下载的链接
        let nameStr = res.headers["content-disposition"].split(";")[1].split("filename=")[1];
        let downloadElement = document.createElement('a');

        downloadElement.href = href;
        downloadElement.download = decodeURI(nameStr);             // 下载后文件名
        document.body.appendChild(downloadElement);
        downloadElement.click();                        // 点击下载

        document.body.removeChild(downloadElement);     // 下载完成移除元素
        window.URL.revokeObjectURL(href);               // 释放掉blob对象
    },

}