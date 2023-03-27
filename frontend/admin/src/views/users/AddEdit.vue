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

var dictMajor = {}

function majorInDict(id,name){
    dictMajor[id] = name;
}

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
    majorId: Yup.string(),
    credit: Yup.string()
        .required('수강가능학점을 입력하세요'),
    password: Yup.string()
        .transform(x => x === '' ? undefined : x)
        // password optional in edit mode
        .concat(user ? null : Yup.string().required('비밀번호를 입력하세요'))
        .min(6, '비밀번호는 최소 6자리 이상이어야 합니다.')
});

async function onSubmit(values) {
    var select = document.getElementById("majorSelect");
    if (select.value){
        values.majorId = select.value
    }
    else{
        values.majorId = document.getElementById("majorId").value;
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
            console.log(values)
            
            message = '학생 정보 업데이트 완료';
        } else {
            await usersStore.register(values);
            message = '학생 추가 완료';
        }
        await router.push('/users');
        alertStore.success(message);
    } catch (error) {
        alertStore.error(error);
    }
}

// 안됨 고장 vue에서는 안됨
// function initCombobox(){
//     var select = document.getElementById("majorId").value;
//     for (var i = 0; i < document.getElementById('majorSelect').length; i++) {
//         if (document.getElementById('majorSelect').options[i].value == select) {
//             document.getElementById('majorSelect').options[i].selected = true;
//             break;
//         }
//     }
// }


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
<tr v-for="maj in major" :key="maj.id">
{{ majorInDict(maj.id,maj.name) }}
</tr>


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
                    <label>
                        전공
                        <em v-if="user">(비우면 동일한 전공으로 유지)</em>
                        
                    </label><tr></tr>
                    <select id="majorSelect" v-model="majorSelected">
                    <option
                        v-for="item in major"
                        :key="item.name"
                        :value="item.id" selected>
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
            <Field id="majorId" name="majorId" type="text" class="form-control" style="visibility: hidden;"/>
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

