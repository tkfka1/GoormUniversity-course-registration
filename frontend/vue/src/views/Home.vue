<script setup>
import { storeToRefs } from 'pinia';

import { useAuthStore } from '@/stores';

const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
</script>

<template>
    <div v-if="user">
        <h1>안녕하세요 {{user.firstName}}!</h1>
        <p>구름대학 수강신청에 오신것을 환영합니다.</p>
        <p><router-link to="/users">test1</router-link></p>
    </div>
</template>
