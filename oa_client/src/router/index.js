import Vue from "vue";
import VueRouter from "vue-router";
import System from "@/pages/System";
import Login from "@/pages/Login";
import Index from "@/components/system/Index"
import Department from "@/components/system/Department";
import Employee from "@/components/system/Employee";
import Account from "@/components/system/Account";

Vue.use(VueRouter)

export default new VueRouter({
    routes: [
        {
            path: '/',
            name: 'home',
            redirect: '/login'
        }, {
            path: '/login',
            name: 'login',
            component: Login
        }, {
            path: '/system',
            name: '/system',
            component: System,
            children: [
                {
                   path: '',
                   redirect: 'index'
                }, {
                    path: 'index',
                    name: 'index',
                    component: Index
                }, {
                    path: 'department',
                    name: 'department',
                    component: Department
                }, {
                    path: 'employee',
                    name: 'employee',
                    component: Employee
                }, {
                    path: 'account',
                    name: 'account',
                    component: Account
                }
            ]
        }
    ]
})