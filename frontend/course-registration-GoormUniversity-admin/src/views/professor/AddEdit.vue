<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useProfessorStore , useAlertStore , useMajorStore } from '@/stores';
import { router } from '@/router';

const professorStore = useProfessorStore();
const alertStore = useAlertStore();
const majorStore = useMajorStore();

const { major } = storeToRefs(majorStore);

majorStore.getAll();

const route = useRoute();
const id = route.params.id;

let title = '교수 추가';
let user = null;

if (id) {
    // edit mode
    title = '정보 수정';
    ({ user } = storeToRefs(professorStore));
    professorStore.getById(id);
 
    
}

const schema = Yup.object().shape({
    name: Yup.string()
        .required('교수를 입력하세요'),
});

async function onSubmit(values) {
    if (document.getElementById("major.id").value){
        values.major.id = String(document.getElementById("major.id").value);
    }
    try {
        let message;
        if (user) {
            await professorStore.update(user.value.id, values)
            // console.log(values)
            message = '교수 정보 업데이트 완료';
        } else {
            await professorStore.register(values);
            message = '교수 추가 완료';
        }
        await router.push('/professor');
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
    <template v-if="!(user?.loading || user?.error)">
        <Form @submit="onSubmit" :validation-schema="schema" :initial-values="user" v-slot="{ errors, isSubmitting }">
            <div class="form-row">
                <div class="form-group col">
                    <label>교수명</label>
                    <Field name="name" type="text" class="form-control" :class="{ 'is-invalid': errors.name }" />
                    <div class="invalid-feedback">{{ errors.name }}</div>
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
            <div class="form-group">
                <button class="btn btn-primary" :disabled="isSubmitting">
                    <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                    저장
                </button>
                <router-link to="/professor" class="btn btn-link">취소</router-link>
            </div>
            <Field id="major.id" name="major.id" type="text" class="form-control" style="visibility: hidden;"/>
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
