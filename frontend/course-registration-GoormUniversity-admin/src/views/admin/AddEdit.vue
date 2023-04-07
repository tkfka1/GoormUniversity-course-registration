<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useAdminStore, useAlertStore } from '@/stores';
import { router } from '@/router';

const adminStore = useAdminStore();
const alertStore = useAlertStore();
const route = useRoute();
const id = route.params.id;

let title = '교직원 추가';
let user = null;
if (id) {
    // edit mode
    title = '정보 수정';
    ({ user } = storeToRefs(adminStore));
    adminStore.getById(id);
}

const schema = Yup.object().shape({
    adminId: Yup.string()
        .required('교직원번호를 입력하세요'),
    name: Yup.string()
        .required('이름을 입력하세요'),
    password: Yup.string()
        .transform(x => x === '' ? undefined : x)
        // password optional in edit mode
        .concat(user ? null : Yup.string().required('비밀번호를 입력하세요'))
        .min(6, '비밀번호는 최소 6자리 이상이어야 합니다.')
});

async function onSubmit(values) {
    try {
        let message;
        if (user) {
            if (values.password === undefined){
                values.password = "123456";
                await adminStore.updatePatch(user.value.id, values)
            }
            else{
                await adminStore.update(user.value.id, values)
            }
            
            message = '교직원 정보 업데이트 완료';
        } else {
            await adminStore.register(values);
            message = '교직원 추가 완료';
        }
        await router.push('/admin');
        alertStore.success(message);
    } catch (error) {
        alertStore.error(error);
    }
}
</script>

<template>
    <h1>{{title}}</h1>
    <template v-if="!(user?.loading || user?.error)">
        <Form @submit="onSubmit" :validation-schema="schema" :initial-values="user" v-slot="{ errors, isSubmitting }">
            <div class="form-row">
                <div class="form-group col">
                    <label>교직원번호</label>
                    <Field name="adminId" type="text" class="form-control" :class="{ 'is-invalid': errors.adminId }" />
                    <div class="invalid-feedback">{{ errors.adminId }}</div>
                </div>
                <div class="form-group col">
                    <label>이름</label>
                    <Field name="name" type="text" class="form-control" :class="{ 'is-invalid': errors.name }" />
                    <div class="invalid-feedback">{{ errors.name }}</div>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col">
                    <label>
                        비밀번호
                        <em v-if="user">(비우면 동일한 비밀번호로 유지)</em>
                    </label>
                    <Field name="password" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
                    <div class="invalid-feedback">{{ errors.password }}</div>
                </div>
            </div>
            <div class="form-group">
                <button class="btn btn-primary" :disabled="isSubmitting">
                    <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                    저장
                </button>
                <router-link to="/admin" class="btn btn-link">취소</router-link>
            </div>
        </Form>
    </template>
    <template v-if="user?.loading">
        <div class="text-center m-5">
            <span class="spinner-border spinner-border-lg align-center"></span>
        </div>
    </template>
    <template v-if="user?.error">
        <div class="text-center m-5">
            <div class="text-danger">Error loading user: {{user.error}}</div>
        </div>
    </template>
</template>
