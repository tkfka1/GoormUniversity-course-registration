<script setup>
import { storeToRefs } from 'pinia';

import { useOpenTimeStore } from '@/stores';

const openTimeStore = useOpenTimeStore();
const { openTime } = storeToRefs(openTimeStore);
openTimeStore.getAll();
</script>


<template>
    <h1>수강신청 가능 시간 목록</h1>
    <router-link to="/openTime/add" class="btn btn-sm btn-success mb-2">시간 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 45%">시작 시간</th>
                <th style="width: 45%">종료 시간</th>
                <th style="width: 10%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="openTime.length">
                <tr v-for="user in openTime" :key="user.id">
                    <td>{{ (user.startTime).replace("T" , " : ") }}</td>
                    <td>{{ (user.endTime).replace("T" , " : ") }}</td>
                    <td style="white-space: nowrap">
                        <router-link :to="`/openTime/edit/${user.id}`" class="btn btn-sm btn-primary mr-1">수정</router-link>
                        <button @click="openTimeStore.delete(user.id)" class="btn btn-sm btn-danger btn-delete-user" :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="openTime.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="openTime.error">
                <td colspan="4">
                    <div class="text-danger">Error loading professor: {{openTime.error}}</div>
                </td>
            </tr>            
        </tbody>
    </table>
</template>
