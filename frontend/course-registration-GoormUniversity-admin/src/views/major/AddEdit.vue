<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useMajorStore, useAlertStore } from '@/stores';
import { router } from '@/router';

const majorStore = useMajorStore();
const alertStore = useAlertStore();
const route = useRoute();
const id = route.params.id;

let title = '전공 추가';
let user = null;
if (id) {
    // edit mode
    title = '정보 수정';
    ({ user } = storeToRefs(majorStore));
    majorStore.getById(id);
    
}

const schema = Yup.object().shape({
    name: Yup.string()
        .required('전공을 입력하세요')
});

async function onSubmit(values) {
    try {
        let message;
        if (user) {
            await majorStore.update(user.value.id, values)
            // console.log(values)
            message = '전공 정보 업데이트 완료';
        } else {
            await majorStore.register(values);
            message = '전공 추가 완료';
        }
        await router.push('/major');
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
                    <label>전공명</label>
                    <Field name="name" type="text" class="form-control" :class="{ 'is-invalid': errors.name }" />
                    <div class="invalid-feedback">{{ errors.name }}</div>
                </div>
            </div>
            <div class="form-group">
                <button class="btn btn-primary" :disabled="isSubmitting">
                    <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                    저장
                </button>
                <router-link to="/major" class="btn btn-link">취소</router-link>
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
