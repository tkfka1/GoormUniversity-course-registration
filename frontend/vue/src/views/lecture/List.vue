<script setup>
import { storeToRefs } from 'pinia';

import { useLectureStore } from '@/stores';

const lectureStore = useLectureStore();
const { lecture } = storeToRefs(lectureStore);

lectureStore.getAll();
</script>

<template>
    <h1>강의목록</h1>
    <router-link to="/lecture/add" class="btn btn-sm btn-success mb-2">강의 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 30%">First Name</th>
                <th style="width: 30%">Last Name</th>
                <th style="width: 30%">Username</th>
                <th style="width: 10%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="lecture.length">
                <tr v-for="user in users" :key="user.id">
                    <td>{{ user.firstName }}</td>
                    <td>{{ user.lastName }}</td>
                    <td>{{ user.username }}</td>
                    <td style="white-space: nowrap">
                        <router-link :to="`/lecture/edit/${lecture.id}`" class="btn btn-sm btn-primary mr-1">Edit</router-link>
                        <button @click="lectureStore.delete(lecture.id)" class="btn btn-sm btn-danger btn-delete-user" :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>Delete</span>
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
                    <div class="text-danger">Error loading lecture: {{users.error}}</div>
                </td>
            </tr>            
        </tbody>
    </table>
</template>
