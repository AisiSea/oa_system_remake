import {CLEAR_LOGIN_MSG, SET_LOGIN_MSG} from "@/store/mutations-types";

export default {
    clearLoginMsg ({commit}) {
        commit(CLEAR_LOGIN_MSG);
    },

    setLoginMsg ({commit}, msg) {
        commit(SET_LOGIN_MSG, {msg});
    }
}