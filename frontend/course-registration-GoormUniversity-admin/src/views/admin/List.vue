<script setup>
import { storeToRefs } from 'pinia';

import { useAdminStore } from '@/stores';

const adminStore = useAdminStore();
const { admin } = storeToRefs(adminStore);

adminStore.getAll();
// console.log("listadmin")

</script>

<template>
    <h1>교직원 목록</h1>
    <router-link to="/admin/add" class="btn btn-sm btn-success mb-2">교직원 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 45%">교원번호</th>
                <th style="width: 45%">이름</th>
                <th style="width: 10%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="admin.length">
                <tr v-for="user in admin" :key="user.id">
                    <td>{{ user.adminId }}</td>
                    <td>{{ user.name }}</td>
                    <td style="white-space: nowrap">
                        <router-link :to="`/admin/edit/${user.id}`" class="btn btn-sm btn-primary mr-1">수정</router-link>
                        <button @click="adminStore.delete(user.id)" class="btn btn-sm btn-danger btn-delete-user" :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="admin.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="admin.error">
                <td colspan="4">
                    <div class="text-danger">Error loading admin: {{admin.error}}</div>
                </td>
            </tr>            
        </tbody>
    </table>
</template>
