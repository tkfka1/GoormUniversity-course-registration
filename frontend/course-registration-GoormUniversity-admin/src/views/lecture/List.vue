<script setup>
import { storeToRefs } from 'pinia';

import { useLectureStore } from '@/stores';

const lectureStore = useLectureStore();
const { lecture } = storeToRefs(lectureStore);

lectureStore.getAll();

// console.log("list : lecture")

</script>

<template>
    <h1>강의 목록</h1>
    <router-link to="/lecture/add" class="btn btn-sm btn-success mb-2">강의 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 30%">이름</th>
                <th style="width: 30%">전공</th>
                <th style="width: 30%">학점</th>
                <th style="width: 10%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="lecture.length">
                <tr v-for="user in lecture" :key="user.id">
                    <td>{{ user.name }}</td>
                    <td>{{ user.major.name }}</td>
                    <td>{{ user.credit }}</td>
                    <td style="white-space: nowrap">
                        <router-link :to="`/lecture/class/${user.id}`" class="btn btn-sm btn-secondary mr-1">분반목록</router-link>
                        <router-link :to="`/lecture/edit/${user.id}`" class="btn btn-sm btn-primary mr-1">수정</router-link>
                        <button @click="lectureStore.delete(user.id)" class="btn btn-sm btn-danger btn-delete-user" :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="lecture.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="lecture.error">
                <td colspan="4">
                    <div class="text-danger">Error loading lecture: {{lecture.error}}</div>
                </td>
            </tr>            
        </tbody>
    </table>
</template>
