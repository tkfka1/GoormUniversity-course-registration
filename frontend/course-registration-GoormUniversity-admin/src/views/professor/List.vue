<script setup>
import { storeToRefs } from 'pinia';

import { useProfessorStore } from '@/stores';

const professorStore = useProfessorStore();
const { professor } = storeToRefs(professorStore);

professorStore.getAll();

// console.log("listprofessor")

</script>

<template>
    <h1>교수 목록</h1>
    <router-link to="/professor/add" class="btn btn-sm btn-success mb-2">교수 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 45%">교수명</th>
                <th style="width: 45%">전공명</th>
                <th style="width: 10%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="professor.length">
                <tr v-for="user in professor" :key="user.id">
                    <td>{{ user.name }}</td>
                    <td>{{ user.major.name }}</td>
                    <td style="white-space: nowrap">
                        <router-link :to="`/professor/edit/${user.id}`" class="btn btn-sm btn-primary mr-1">수정</router-link>
                        <button @click="professorStore.delete(user.id)" class="btn btn-sm btn-danger btn-delete-user" :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="professor.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="professor.error">
                <td colspan="4">
                    <div class="text-danger">Error loading professor: {{professor.error}}</div>
                </td>
            </tr>            
        </tbody>
    </table>
</template>
