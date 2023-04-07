<script setup>
import { storeToRefs } from 'pinia';

import { useUsersStore } from '@/stores';


const usersStore = useUsersStore();
const { users } = storeToRefs(usersStore);

usersStore.getAll();


console.log("listuser");


</script>

<template>



    <h1>학생 목록</h1>
    <router-link to="/users/add" class="btn btn-sm btn-success mb-2">학생 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>학번</th>
                <th>이름</th>
                <th>학년</th>
                <th>전공</th>
                <th>학점</th>
                <th>이메일</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="users.length">
                <tr v-for="user in users" :key="user.id">
                    <td>{{ user.studentId }}</td>
                    <td>{{ user.name }}</td>
                    <td>{{ user.grade  }}</td>
                    <td>{{ user.major.name  }}</td>
                    <td>{{ user.haveCredit }} / {{ user.credit }}</td>
                    <td>{{ user.email }}</td>
                    <td style="white-space: nowrap">
                        <router-link :to="`/users/take/${user.id}`" class="btn btn-sm btn-secondary mr-1">수강목록</router-link>
                        <router-link :to="`/users/edit/${user.id}`" class="btn btn-sm btn-primary mr-1">수정</router-link>
                        <button @click="usersStore.delete(user.id)" class="btn btn-sm btn-danger btn-delete-user" :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="users.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="users.error">
                <td colspan="4">
                    <div class="text-danger">Error loading users: {{users.error}}</div>
                </td>
            </tr>            
        </tbody>
    </table>
</template>
