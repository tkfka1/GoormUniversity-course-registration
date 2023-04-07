<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import { fetchWrapper } from '@/helpers';
import { ref } from 'vue';

import { useLectureClassStore, useAlertStore, useProfessorStore } from '@/stores';
import { router } from '@/router';

const lectureClassStore = useLectureClassStore();
const professorStore = useProfessorStore();
const alertStore = useAlertStore();

const { professor } = storeToRefs(professorStore);
professorStore.getAll();

const route = useRoute();
// 분반 id 가져오기
const id = route.params.id;
// 강의 id 가져오기
const lid = route.params.lid;

const lactureName = ref("이름");
const lactureMajor = ref("학번");
const lactureCredit = ref("학점");
const lactureMajorId = ref(2);

fetchWrapper.get(`/api/lecture/auth/${lid}`).then((res) => {
    lactureName.value = res.name;
    lactureMajor.value = res.major.name;
    lactureCredit.value = res.credit;
    lactureMajorId.value = res.major.id;

});

function findLactureMajorId() {
    return lactureMajorId.value;
}

let title = '강의 분반 추가';
let user = null;

if (id) {
    // edit mode
    title = '강의 분반 정보 수정';
    ({ user } = storeToRefs(lectureClassStore));
    lectureClassStore.getById(id);

}


const schema = Yup.object().shape({
    classMin: Yup.string()
        .required('최소인원을 입력하세요'),
    classMax: Yup.string()
        .required('최대인원을 입력하세요'),
    classPeople: Yup.string()
        .required('현재인원을 입력하세요'),
    period: Yup.string()
        .required('교시를 입력하세요'),
});

async function onSubmit(values) {
    values.lecture.id = lid;
    // console.log(values);
    values.week = document.getElementById("week").value;
    if (document.getElementById("professorId").value) {
        values.professor.id = String(document.getElementById("professorId").value);
    }
    try {
        let message;
        if (user) {
            await lectureClassStore.update(user.value.id, values)
            message = '강의 정보 업데이트 완료';
        } else {
            await lectureClassStore.register(values);
            message = '강의 추가 완료';
        }
        await router.push(`/lecture/class/${lid}`);
        alertStore.success(message);
    } catch (error) {
        if (error == "500") {
            alertStore.error("교수명이 지정되지 않았습니다.");
        } else {
            alertStore.error(error);
        }
    }
}
</script>

<script>
export default {
    data() {
        return {
            professorSelected: "",
        };
    },
    mounted() {
        this.myFunction();
    },
    methods: {
        myFunction() {
        }
    },
};
</script>
<template>
    <h1>{{ title }}</h1>
    <br>
    <h3>전공 : {{ lactureMajor }}</h3>
    <h3>강의 : {{ lactureName }}</h3>
    <h3>학점 : {{ lactureCredit }} 학점</h3>
    <template v-if="!(user?.loading || user?.error)">
        <Form @submit="onSubmit" :validation-schema="schema" :initial-values="user" v-slot="{ errors, isSubmitting }">
            <div class="form-row">
                <div class="form-group col">
                    <label>담당교수</label>
                    <template v-if="professor.length">
                        <select v-if="professor" id="professorId" v-model="professorSelected" class="form-control">
                            <option v-if="user" value="" disabled hidden> {{ user.professor.name }} </option>
                            <option value="" disabled hidden> 담당교수 선택 </option>
                            <option v-for="item in professor.filter((u) => u.major.id === findLactureMajorId())"
                                :key="item.id" :value="item.id" selected>
                                {{ item.name }}
                            </option>
                        </select>
                    </template>
                </div>
                <div class="form-group col">
                    <label>요일</label>
                    <select id="week" class="form-control">
                        <option v-if="user" value="" disabled hidden> {{ user.week }} </option>
                        <option v-else value="1" disabled hidden> 월 </option>
                        <option value="1">월</option>
                        <option value="2">화</option>
                        <option value="3">수</option>
                        <option value="4">목</option>
                        <option value="5">금</option>
                        <option value="6">토</option>
                        <option value="7">일</option>
                    </select>
                </div>
                <div class="form-group col">
                    <label>교시</label>
                    <Field name="period" type="text" class="form-control" :class="{ 'is-invalid': errors.period }" />
                    <div class="invalid-feedback">{{ errors.period }}</div>
                </div>

            </div>
            <div class="form-row">
                <div class="form-group col">
                    <label>최소인원</label>
                    <Field name="classMin" type="number" class="form-control" :class="{ 'is-invalid': errors.classMin }" />
                    <div class="invalid-feedback">{{ errors.classMin }}</div>
                </div>
                <div class="form-group col">
                    <label>최대인원</label>
                    <Field name="classMax" type="number" class="form-control" :class="{ 'is-invalid': errors.classMax }" />
                    <div class="invalid-feedback">{{ errors.classMax }}</div>
                </div>
                <div class="form-group col">
                    <label>현재인원</label>
                    <Field v-if="user" name="classPeople" type="number" class="form-control"
                        :class="{ 'is-invalid': errors.classPeople }" />
                    <Field v-else name="classPeople" type="number" class="form-control" value="0"
                        :class="{ 'is-invalid': errors.classPeople }" disabled />
                    <div class="invalid-feedback">{{ errors.classPeople }}</div>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col">
                    <label>설명</label>
                    <Field v-slot="{ field }" name="explanation" :class="{ 'is-invalid': errors.explanation }">
                        <textarea v-bind="field" class="form-control" rows="10" />
                    </Field>
                    <div class="invalid-feedback">{{ errors.explanation }}</div>

                </div>
            </div>
            <div class="form-group">
                <button class="btn btn-primary" :disabled="isSubmitting">
                    <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
                    저장
                </button>
                <router-link :to="`/lecture/class/${lid}`" class="btn btn-link">취소</router-link>
            </div>
            <Field name="week" type="text" class="form-control" style="visibility: hidden;" />
            <Field name="lecture.id" type="text" class="form-control" style="visibility: hidden;" />
            <Field name="professor.id" type="text" class="form-control" style="visibility: hidden;" />
        </Form>


    </template>
    <template v-if="user?.loading">
        <div class="text-center m-5">
            <span class="spinner-border spinner-border-lg align-center"></span>
        </div>
    </template>
    <template v-if="user?.error">
        <div class="text-center m-5">
            <div class="text-danger">Error loading user: {{ user.error }}</div>
        </div>
    </template>
</template>
