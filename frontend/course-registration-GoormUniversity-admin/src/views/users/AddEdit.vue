<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useUsersStore, useAlertStore, useMajorStore} from '@/stores';
import { router } from '@/router';

const usersStore = useUsersStore();
const alertStore = useAlertStore();
const majorStore = useMajorStore();

const route = useRoute();
const id = route.params.id;

const { major } = storeToRefs(majorStore);

majorStore.getAll();

let title = '학생 추가';
let user = null;
if (id) {
    // edit mode
    title = '정보 수정';
    ({ user } = storeToRefs(usersStore));
    usersStore.getById(id);
    
}

const schema = Yup.object().shape({
    studentId: Yup.string()
        .required('학번을 입력하세요'),
    name: Yup.string()
        .required('이름을 입력하세요'),
    email: Yup.string()
        .required('이메일을 입력하세요'),
    credit: Yup.string()
        .required('수강가능학점을 입력하세요'),
    password: Yup.string()
        .transform(x => x === '' ? undefined : x)
        // password optional in edit mode
        .concat(user ? null : Yup.string().required('비밀번호를 입력하세요'))
        .min(6, '비밀번호는 최소 6자리 이상이어야 합니다.'),
});

async function onSubmit(values) {
    values.grade = String(document.getElementById("grade").value);
    if (document.getElementById("major.id").value){
        values.major.id = String(document.getElementById("major.id").value);
    }
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
            // console.log(values)
            
            message = '학생 정보 업데이트 완료';
        } else {
            await usersStore.register(values);
            message = '학생 추가 완료';
        }
        await router.push('/users');
        alertStore.success(message);
    } catch (error) {
        if (error == "500") {
            alertStore.error("이미 존재하는 학번입니다.");
        }
        else{
            alertStore.error(error);
        }
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
    <template v-if="!(user?.loading || user?.error)">
        <Form @submit="onSubmit" :validation-schema="schema" :initial-values="user" v-slot="{ errors, isSubmitting }">
            <div class="form-row">
                <div class="form-group col">
                    <label>학번</label>
                    <Field name="studentId" type="text" class="form-control" :class="{ 'is-invalid': errors.studentId }" />
                    <div class="invalid-feedback">{{ errors.studentId }}</div>
                </div>
                <div class="form-group col">
                    <label>학년</label>
                    <select id="grade" class="form-control" >
                    <option v-if="user" value="" disabled hidden> {{ user.grade }} </option>
                    <option v-else value="1" disabled hidden> 1 </option>
                        <option value="1"> 1 </option>
                        <option value="2"> 2 </option>
                        <option value="3"> 3 </option>
                        <option value="4"> 4 </option>
                        <option value="5"> 5 </option>
                        <option value="6"> 6 </option>
                        <option value="7"> 7 </option>
                        <option value="8"> 8 </option>
                        <option value="9"> 9 </option>
                    </select>
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
                <div class="form-group col">
                    <label>전공명</label>
                    <br>
                    <select id="major.id" v-model="majorSelected" class="form-control">
                        <option v-if="user" value="" disabled hidden> {{ user.major.name }} </option>
                    <option
                        v-for="item in major"
                        :key="item.name"
                        :value="item.id">
                        {{ item.name }}
                    </option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col">
                    <label>수강가능학점</label>
                    <Field name="credit" type="number" class="form-control" :class="{ 'is-invalid': errors.credit }" />
                    <div class="invalid-feedback">{{ errors.credit }}</div>
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
            <Field name="major.id" type="text" class="form-control" style="visibility: hidden;"/>
            <Field name="grade" type="text" class="form-control" style="visibility: hidden;"/>
            <Field v-if="user" name="haveCredit" type="number" class="form-control" style="visibility: hidden;" />
            <Field v-else name="haveCredit" type="number" value="0" class="form-control" style="visibility: hidden;" />
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

