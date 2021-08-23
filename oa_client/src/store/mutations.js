import {CLEAR_LOGIN_MSG, SET_LOGIN_MSG} from "@/store/mutations-types";

export default {
    [CLEAR_LOGIN_MSG] (state) {
        state.loginMsg = '';
    },

    [SET_LOGIN_MSG] (state, {msg}) {
        state.loginMsg = msg;
    }
}