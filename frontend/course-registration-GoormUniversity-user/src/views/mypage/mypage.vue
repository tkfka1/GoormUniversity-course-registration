<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useUsersStore, useAlertStore, useMajorStore , useAuthStore} from '@/stores';
import { router } from '@/router';

const usersStore = useUsersStore();
const alertStore = useAlertStore();
const majorStore = useMajorStore();
const authStore = useAuthStore();

const { major } = storeToRefs(majorStore);
majorStore.getAll();

const { user } = storeToRefs(authStore);
const id = user._object.user.id;

let title = '마이페이지';

const us = storeToRefs(usersStore);
usersStore.getById(id);

const schema = Yup.object().shape({
    email: Yup.string()
        .required('이메일을 입력하세요')
});

async function onSubmit(values) {
    console.log(values)
    try {
        let message;
        if (user) {
            if (values.password === undefined){
                values.password = "123456";
                await usersStore.updatePatch(user.value.id, values)
            }
            else{
                await usersStore.update(user.value.id, values)
            }
            console.log(values)
            message = '마이페이지 정보 업데이트 완료';
        }
        await router.push('/');
        alertStore.success(message);
    } catch (error) {
        alertStore.error(error);
    }
}
</script>


<script>
export default {
  data() {
    return {
      majorSelected: "",
    };
  },
  mounted() {
    this.myFunction();
  },
  methods: {
    myFunction() {

    }
  }
};
</script>


<template>

    <h1>{{title}}</h1>

    <template v-if="!(us.user?.loading || us.user?.error)">
        <Form @submit="onSubmit" :validation-schema="schema" :initial-values="us.user" v-slot="{ errors, isSubmitting }">
            <div class="form-row">
                <div class="form-group col">
                    <label>학번</label>
                    <Field name="studentId" type="text" class="form-control" :class="{ 'is-invalid': errors.studentId }" disabled />
                    <div class="invalid-feedback">{{ errors.studentId }}</div>
                </div>
                <div class="form-group col">
                    <label>이름</label>
                    <Field name="name" type="text" class="form-control" :class="{ 'is-invalid': errors.name }" disabled />
                    <div class="invalid-feedback">{{ errors.name }}</div>
                </div>
                <div class="form-group col">
                    <label>학년</label>
                    <Field name="grade" type="text" class="form-control" disabled />
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col">
                    <label>
                        비밀번호
                        <em v-if="us.user">(비우면 동일한 비밀번호로 유지)</em>
                    </label>
                    <Field name="password" type="password" class="form-control" :class="{ 'is-invalid': errors.password }" />
                    <div class="invalid-feedback">{{ errors.password }}</div>
                </div>
                <div class="form-group col">
                    <label>전공</label>
                    <Field name="major.name" type="text" class="form-control" :class="{ 'is-invalid': errors.name }" disabled />
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col">
                    <label>최대학점</label>
                    <Field name="credit" type="number" class="form-control" :class="{ 'is-invalid': errors.credit }" disabled />
                    <div class="invalid-feedback">{{ errors.credit }}</div>
                </div>
                <div class="form-group col">
                    <label>수강학점</label>
                    <Field name="haveCredit" type="number" class="form-control" :class="{ 'is-invalid': errors.haveCredit }" disabled />
                    <div class="invalid-feedback">{{ errors.haveCredit }}</div>
                </div>
                <div class="form-group col">
                    <label>
                        이메일
                    </label>
                    <Field name="email" type="text" class="form-control" :class="{ 'is-invalid': errors.email }" />
                    <div class="invalid-feedback">{{ errors.email }}</div>
                </div>
            </div>
            <div class="form-group">
                <button class="btn btn-primary" :disabled="isSubmitting">
                    <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                    저장
                </button>
                <router-link to="/users" class="btn btn-link">취소</router-link>
            </div>
            <Field name="major.id" type="text" class="form-control" :class="{ 'is-invalid': errors.name }" style="visibility: hidden;"/>
        </Form>
    </template>
    <template v-if="us.user?.loading">
        <div class="text-center m-5">
            <span class="spinner-border spinner-border-lg align-center"></span>
        </div>
    </template>
    <template v-if="us.user?.error">
        <div class="text-center m-5">
            <div class="text-danger">Error loading us.user: {{us.user.error}}</div>
        </div>
    </template>
</template>

