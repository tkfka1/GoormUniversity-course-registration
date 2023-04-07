<script setup>
import { storeToRefs } from 'pinia';

import { useMajorStore } from '@/stores';

const majorStore = useMajorStore();
const { major } = storeToRefs(majorStore);

majorStore.getAll();

// console.log("listmajor")

</script>

<template>
    <h1>전공 목록</h1>
    <router-link to="/major/add" class="btn btn-sm btn-success mb-2">전공 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 90%">전공 이름</th>
                <th style="width: 10%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="major.length">
                <tr v-for="user in major" :key="user.id">
                    <td>{{ user.name }}</td>
                    <td style="white-space: nowrap">
                        <router-link :to="`/major/edit/${user.id}`" class="btn btn-sm btn-primary mr-1">수정</router-link>
                        <button @click="majorStore.delete(user.id)" class="btn btn-sm btn-danger btn-delete-user" :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="major.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="major.error">
                <td colspan="4">
                    <div class="text-danger">Error loading major: {{major.error}}</div>
                </td>
            </tr>            
        </tbody>
    </table>
</template>
