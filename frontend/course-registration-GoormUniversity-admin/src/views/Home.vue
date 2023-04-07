<script setup>
import { storeToRefs } from 'pinia';
import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';
import { ref } from 'vue';

const authStore = useAuthStore();
const { user } = storeToRefs(authStore);


const name = ref("이름");
const adminId = ref("학번");

fetchWrapper.get(`/api/admin/auth/${user._object.user.id}`).then((res) => {
    // console.log(res);
    name.value = res.name;
    adminId.value = res.adminId;

});

</script>

<template>
    <div v-if="user">
        <h1>안녕하세요 {{ name }}님!</h1>
        <p>구름대학 수강신청에 오신것을 환영합니다.</p>
        <p><router-link to="/admin">교직원</router-link></p>
        <p><router-link to="/users">학생</router-link></p> 
        <p><router-link to="/professor">교수</router-link></p> 
        <p><router-link to="/major">전공</router-link></p> 
        <p><router-link to="/lecture">강의</router-link></p> 
        <p><router-link to="/openTime">신청시간</router-link></p> 

    </div>




</template>

