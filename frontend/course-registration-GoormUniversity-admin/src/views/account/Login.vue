<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';

import { useAuthStore } from '@/stores';

const schema = Yup.object().shape({
    adminId: Yup.string().required('교원번호를 입력하세요'),
    password: Yup.string().required('비밀번호를 입력하세요')
});

async function onSubmit(values) {
    // console.log(values);
    const authStore = useAuthStore();
    const { adminId, password } = values;
    await authStore.login(adminId, password);
}
</script>

<template>
    <div class="card m-3">
        <h4 class="card-header">구름대학교 수강신청 시스템(admin)</h4>
        <div class="card-body">
            <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
                <div class="form-group">
                    <label>교원번호</label>
                    <Field name="adminId" type="text" class="form-control" :class="{ 'is-invalid': errors.adminId }" />
                    <div class="invalid-feedback">{{ errors.adminId }}</div>
                </div>
                <div class="form-group">
                    <label>비밀번호</label>
                    <Field name="password" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
                    <div class="invalid-feedback">{{ errors.password }}</div>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary" :disabled="isSubmitting">
                        <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                        로그인
                    </button>
                    <router-link to="register" class="btn btn-link">회원가입</router-link>
                </div>
            </Form>
        </div>
    </div>


</template>
