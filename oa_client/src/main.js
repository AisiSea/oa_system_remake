import '@/styles/reset.css';
import 'element-ui/lib/theme-chalk/index.css';
import '@/styles/element.css';

import Vue from 'vue';
import App from './App';
import axios from "axios";
import ElementUI from 'element-ui';
import router from './router';
import store from './store';

Vue.config.productionTip = false;
Vue.prototype.axios = axios;
Vue.use(ElementUI);

axios.defaults.baseURL = '/api';
axios.defaults.headers.common['Content-Type'] = 'application/json;charset=UTF-8';

axios.interceptors.request.use(function (config) {
  let token = localStorage.getItem('token');
  if (token === null || token === '')
    token = sessionStorage.getItem('token');
  config.headers.token = token;
  return config;
}, function (error) {
  return Promise.reject(error);
});

new Vue({
  router,
  render: h => h(App),
  store
}).$mount('#app');
