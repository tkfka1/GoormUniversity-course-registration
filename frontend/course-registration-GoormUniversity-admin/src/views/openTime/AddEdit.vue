<script setup>
import { Form, Field } from 'vee-validate';
import * as Yup from 'yup';
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import {  useAlertStore , useOpenTimeStore } from '@/stores';
import { router } from '@/router';

import { ref , onMounted} from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'


const date = ref();

// For demo purposes assign range from the current date
onMounted(() => {
  const startDate = new Date();
  const endDate = new Date(new Date().setDate(startDate.getDate() + 7));
  date.value = [startDate, endDate];
})






const openTimeStore = useOpenTimeStore();
const alertStore = useAlertStore();

const route = useRoute();
const id = route.params.id;

let title = '시간 추가';
let openTime = null;
if (id) {
    // edit mode
    title = '시간 수정';
    ({ openTime } = storeToRefs(openTimeStore));
    openTimeStore.getById(id);
}

const schema = Yup.object().shape({
    startTime: Yup.string()
        .required('시작시간을 입력하세요'),
    endTime: Yup.string()
        .required('를 입력하세요')
});

async function onSubmit() {

    var d1 = new Date(date.value[0]);
    var d2 = new Date(date.value[1]);
    function pad(n) {
        return n < 10 ? '0' + n : n;
    }
    var d1s = d1.getFullYear() + "-" + pad(d1.getMonth()+1) + "-" + pad(d1.getDate()) + "T" + pad(d1.getHours()) + ":" + pad(d1.getMinutes()) + ":" + "00";
    var d2s = d2.getFullYear() + "-" + pad(d2.getMonth()+1) + "-" + pad(d2.getDate()) + "T" + pad(d2.getHours()) + ":" + pad(d2.getMinutes()) + ":" + "00";

    
    let values = {
        startTime: d1s,
        endTime: d2s
    }
    try {
        let message;
        if (openTime) {
            await openTimeStore.update(id, values)
            message = '시간 정보 업데이트 완료';
        } else {
            await openTimeStore.register(values);
            message = '시간 추가 완료';
        }
        await router.push('/openTime');

    } catch (error) {
        alertStore.error(error);
    }
}

</script>

<template>

    <h1>{{title}}</h1>
    <template v-if="!(openTime?.loading || openTime?.error)">
            <br>
            <div class="form-row">
            <div class="form-group col">
                    <h5>시작시간</h5>
                    <h5>종료시간</h5>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col">
                    <VueDatePicker v-model="date" locale="ko-KR" range />
                </div>

            </div>
            <div class="form-group">
                <button @click="onSubmit()" class="btn btn-primary">
                    저장
                </button>
                <router-link to="/openTime" class="btn btn-link">취소</router-link>
            </div>
    </template>
    <template v-if="openTime?.loading">
        <div class="text-center m-5">
            <span class="spinner-border spinner-border-lg align-center"></span>
        </div>
    </template>
    <template v-if="openTime?.error">
        <div class="text-center m-5">
            <div class="text-danger">Error loading user: {{openTime.error}}</div>
        </div>
    </template>

</template>

