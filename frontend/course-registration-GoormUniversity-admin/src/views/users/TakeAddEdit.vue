<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useUsersStore, useAlertStore, useLectureClassStore, useTakeLectureStore } from '@/stores';
import { router } from '@/router';

const usersStore = useUsersStore();
const alertStore = useAlertStore();
const lectureClassStore = useLectureClassStore();

const route = useRoute();
const lid = route.params.lid;
const id = route.params.id;

const { lectureClass } = storeToRefs(lectureClassStore);

lectureClassStore.getAll();

const takeLectureStore = useTakeLectureStore();
const { take } = storeToRefs(takeLectureStore);
takeLectureStore.getAll();

console.log(lid)


let title = '수업 추가';
let user = null;
if (id) {
    // edit mode
    title = '정보 수정';
    ({ user } = storeToRefs(usersStore));
    usersStore.getById(id);
    
}

const schema = Yup.object().shape({
    user: Yup.object().shape({
        id: Yup.string()
            .required('학번을 입력하세요'),
    }),
});

async function onSubmit(values) {
    values.user.id = lid;
    values.lectureClass.id = document.getElementById("lectureClass").value;
    // console.log(values);
    values = { "id": 18, "classMax": 32, "classMin": 5, "lecture": { "id": 11, "name": "프로그래밍기초", "credit": 2, "major": { "id": 3, "name": "컴퓨터공학과" } }, "professor": { "id": 3, "name": "박교수", "major": { "id": 3, "name": "컴퓨터공학과" } }, "explanation": "43", "period": 4, "week": 1 }
    // console.log(values);
    try {
        let message;
        await takeLectureStore.register(values);
        message = '학생 추가 완료';
        await router.push("`/users/take/${lid}`");
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
    {{ lectureClass }}
    <template v-if="!(user?.loading || user?.error)">
        <Form @submit="onSubmit" :validation-schema="schema" :initial-values="user">
            <div class="form-row">
                <div class="form-group col">
                    <label>강의명</label>
                    <br>
                    <select id="lectureClass" v-model="majorSelected" class="form-control">
                        <option v-if="user" value="" disabled hidden> {{ user.lecture.name }} </option>
                    <option
                        v-for="item in lectureClass"
                        :key="item.name"
                        :value="item.id">
                        {{ item.lecture }} / {{ item.professor }}
                    </option>
                    </select>
                </div>
            </div>
        
            <div class="form-group">
                <button class="btn btn-primary" :disabled="isSubmitting">
                    <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                    저장
                </button>
                <router-link to="/users" class="btn btn-link">취소</router-link>
            </div>
            <Field id="lecture.id" name="lectureClass.id" type="text" class="form-control" style="visibility: hidden;"/>
            <Field id="user.id" name="user.id" type="text" class="form-control" style="visibility: hidden;"/>
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

