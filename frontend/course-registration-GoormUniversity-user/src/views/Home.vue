<script setup>
import { storeToRefs } from 'pinia';
import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';
import { ref } from 'vue';

const authStore = useAuthStore();
const { user } = storeToRefs(authStore);

const name = ref("이름");
const studentId = ref("학번");

fetchWrapper.get(`/api/user/auth/${user._object.user.id}`).then((res) => {
    name.value = res.name;
    studentId.value = res.studentId;

});

</script>

<template>
<div class="jumbotron">
    <div v-if="user">
        <h1 class="hh">안녕하세요 {{ name }}님!</h1>
        <br>
        <p class="pp">구름대학 수강신청에 오신것을 환영합니다.</p>

    </div>
</div>
        <p><router-link to="/users">수강신청목록</router-link></p>
        <p><router-link to="/lecture">강의목록</router-link></p>
        <p><router-link to="/mypage">내정보</router-link></p>
        <p><router-link to="/app">동물판독기</router-link></p>

</template>

<style>
.jumbotron {
    position: relative;
    color: rgba(255,255,255,1);
    background-color: transparent;
    padding: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 577px;
    background-image: url("@/assets/background.png");
    background-size: cover;
    background-repeat: no-repeat;


}

.jumbotron::before {
    position: absolute;
    content: "";
    top:0px;
    left:0px;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.4);
}

.hh {
        position: relative;
        font-size: 50px;
        z-index: 100;
    }

.pp {
    position: relative;
    font-size: 30px;
    z-index: 100;
}
</style>