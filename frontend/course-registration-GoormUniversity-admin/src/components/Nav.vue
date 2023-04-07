<script setup>
import { useAuthStore } from '@/stores';

const authStore = useAuthStore();
</script>

<template>
    <nav v-show="authStore.user" class="navbar navbar-expand navbar-dark bg-dark">
        <div class="navbar-nav">
            <router-link to="/" class="nav-item nav-link">홈</router-link>
            <router-link to="/admin" class="nav-item nav-link">교직원</router-link>
            <router-link to="/users" class="nav-item nav-link">학생</router-link>
            <router-link to="/professor" class="nav-item nav-link">교수</router-link>
            <router-link to="/major" class="nav-item nav-link">전공</router-link>
            <router-link to="/lecture" class="nav-item nav-link">강의</router-link>
            <router-link to="/openTime" class="nav-item nav-link">신청시간</router-link>
            <button @click="authStore.logout()" class="btn btn-link nav-item nav-link">Logout</button>
        </div>
    </nav>
</template>