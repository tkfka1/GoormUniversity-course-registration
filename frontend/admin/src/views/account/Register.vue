<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';

import { useAdminStore, useAlertStore } from '@/stores';
import { router } from '@/router';

const schema = Yup.object().shape({
    adminId: Yup.string()
        .required('교원번호를 입력하세요'),
    name: Yup.string()
        .required('이름을 입력하세요'),
    password: Yup.string()
        .required('비밀번호를 입력하세요')
        .min(6, '비밀번호는 최소 6자리 이상이어야 합니다.')
});

async function onSubmit(values) {
    const adminStore = useAdminStore();
    const alertStore = useAlertStore();
    try {
        await adminStore.register(values);
        await router.push('/account/login');
        alertStore.success('가입이 성공적으로 완료되었습니다.');
    } catch (error) { 
        alertStore.error(error);
    }
}
</script>

<template>
    <div class="card m-3">
        <h4 class="card-header">회원가입</h4>
        <div class="card-body">
            <Form @submit="onSubmit" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
                <div class="form-group">
                    <label>교원번호</label>
                    <Field name="adminId" type="text" class="form-control" :class="{ 'is-invalid': errors.adminId }" />
                    <div class="invalid-feedback">{{ errors.adminId }}</div>
                </div>
                <div class="form-group">
                    <label>이름</label>
                    <Field name="name" type="text" class="form-control" :class="{ 'is-invalid': errors.name }" />
                    <div class="invalid-feedback">{{ errors.name }}</div>
                </div>
                <div class="form-group">
                    <label>비밀번호</label>
                    <Field name="password" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
                    <div class="invalid-feedback">{{ errors.password }}</div>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary" :disabled="isSubmitting">
                        <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                        가입하기
                    </button>
                    <router-link to="login" class="btn btn-link">취소</router-link>
                </div>
            </Form>
        </div>
    </div>
</template>
