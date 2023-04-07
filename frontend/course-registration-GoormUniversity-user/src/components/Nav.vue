<script setup>
import { useAuthStore } from '@/stores';

const authStore = useAuthStore();
</script>

<template>
    <nav v-show="authStore.user" class="navbar navbar-expand navbar-dark bg-dark">
        <div class="navbar-nav">
            <router-link to="/" class="nav-item nav-link">홈</router-link>
            <router-link to="/users" class="nav-item nav-link">수강목록</router-link>
            <router-link to="/lecture" class="nav-item nav-link">강의목록</router-link>
            <router-link to="/mypage" class="nav-item nav-link">내정보</router-link>
            <router-link to="/app" class="nav-item nav-link">동물판독기</router-link>
            <button @click="authStore.logout()" class="btn btn-link nav-item nav-link">로그아웃</button>
        </div>
    </nav>
</template>