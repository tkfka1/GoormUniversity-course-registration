<script setup>
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import { fetchWrapper } from '@/helpers';
import { ref } from 'vue';

import { useLectureClassStore } from '@/stores';
import { useTakeLectureStore } from '@/stores';

const lectureClassStore = useLectureClassStore();
const { lectureClass } = storeToRefs(lectureClassStore);
lectureClassStore.getAll();

const takeLectureStore = useTakeLectureStore();
const { take } = storeToRefs(takeLectureStore);
takeLectureStore.getAll();

// 현재 강의 id 가져오기 lid
const route = useRoute();
const lid = route.params.lid;

const lactureName = ref("이름");
const lactureMajor = ref("강의");
const lactureCredit = ref("학점");

fetchWrapper.get(`/api/lecture/auth/${lid}`).then((res) => {
    lactureName.value = res.name;
    lactureMajor.value = res.major.name;
    lactureCredit.value = res.credit;

});

function makeweek(week) {
    if (week == 1) {
        return "월";
    } else if (week == 2) {
        return "화";
    } else if (week == 3) {
        return "수";
    } else if (week == 4) {
        return "목";
    } else if (week == 5) {
        return "금";
    } else if (week == 6) {
        return "토";
    } else if (week == 7) {
        return "일";
    }
}

</script>

<template>
    <h1>강의 분반</h1>
    <br>
    <h3>전공 : {{ lactureMajor }}</h3>
    <h3>강의 : {{ lactureName }}</h3>
    <h3>학점 : {{ lactureCredit }} 학점</h3>
    <router-link :to="`/lecture/class/${lid}/add`" class="btn btn-sm btn-success mb-2">분반 추가</router-link>
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 15%">담당교수</th>
                <th style="width: 15%">최소인원</th>
                <th style="width: 15%">강의인원</th>
                <th style="width: 15%">수강인원</th>
                <th style="width: 15%">요일/교시</th>
                <th style="width: 15%">설명</th>
                <th style="width: 10%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="lectureClass.length">
                <tr v-for="user in lectureClass.filter((l) => l.lecture.id === Number(lid))" :key="user.id">
                    <td>{{ user.professor.name }}</td>
                    <td>{{ user.classMin }}명</td>
                    <td>{{ user.classPeople }} / {{ user.classMax }}</td>
                    <td>{{ makeweek(user.week) }} / {{ user.period }}교시</td>
                    <td>{{ user.explanation }}</td>
                    <td style="white-space: nowrap">
                        <button @click="openModal(user.id)" class="btn btn-sm btn-secondary mr-1">강의세부</button>
                        <!-- 컴포넌트 MyModal -->
                        <MyModal @close="closeModal(user.id)" v-if="modalEx == user.id">
                            <!-- default 슬롯 콘텐츠 -->
                            <div class="modal-header">
                                <h5 class="modal-title">{{ user.lecture.name }}</h5>
                            </div>
                            <div class="modal-body">

                                <div class="form-row">
                                    <div class="input-group col-6">
                                    <span class="input-group-text" id="basic-addon1">전공</span>
                                    <span type="text" class="form-control">{{ user.lecture.major.name }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                    <span class="input-group-text" id="basic-addon1">교수</span>
                                    <span type="text" class="form-control">{{ user.professor.name }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                    <span class="input-group-text" id="basic-addon1">학점</span>
                                    <span type="text" class="form-control">{{ user.lecture.credit }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                    <span class="input-group-text" id="basic-addon1">교시</span>
                                    <span type="text" class="form-control">{{ makeweek(user.week) }} / {{ user.period }}교시</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                    <span class="input-group-text" id="basic-addon1">최소인원</span>
                                    <span type="text" class="form-control">{{ user.classMin }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                    <span class="input-group-text" id="basic-addon1">인원</span>
                                    <span type="text" class="form-control">{{ user.classPeople }} / {{ user.classMax }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-12">　　　　　　　　　　　　　　　　　　　　　　　　</div>
                                </div>
                                
                                <p>{{ user.explanation}}</p>

                                <br>


                                <div class="modal-header">
                                <h6 class="modal-title">수강 학생 목록</h6>
                                </div>
                                <div class="form-row">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>학번</th>
                                            <th>이름</th>
                                            <th>학년</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="t in take.filter((l) => l.lectureClass.id === user.id)" :key="t.id">
                                            <td>{{ t.user.studentId }}</td>
                                            <td>{{ t.user.name }}</td>
                                            <td>{{ t.user.grade }}</td>
                                            <th>
                                                <button @click="takeLectureStore.delete(t.id)" class="btn btn-sm btn-danger btn-delete-user"
                                                    :disabled="t.isDeleting">
                                                    <span v-if="t.isDeleting">
                                                        <span class="spinner-border spinner-border-sm" role="status"
                                                            aria-hidden="true"></span>
                                                        Deleting...
                                                    </span>
                                                    <span v-else>삭제</span>
                                                </button>
                                            </th>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" @click="closeModal"
                                    data-bs-dismiss="modal">Close</button>
                                <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
                            </div>
                            <!-- /default -->
                            <!-- footer 슬롯 콘텐츠 -->
                            <!-- /footer -->
                        </MyModal>  
                        <router-link :to="`/lecture/class/${lid}/edit/${user.id}`"
                            class="btn btn-sm btn-primary mr-1">수정</router-link>
                        <button @click="lectureClassStore.delete(user.id)" class="btn btn-sm btn-danger btn-delete-user"
                            :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm">삭제중</span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="lectureClass.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="lectureClass.error">
                <td colspan="4">
                    <div class="text-danger">Error loading lecture: {{ lectureClass.error }}</div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import MyModal from './MyModal.vue'
export default {
    components: { MyModal },
    data() {
        return {
            modalEx: 0,
            message: ''
        }
    },
    methods: {
        openModal(num) {
            this.modalEx = num

        },
        closeModal(num) {
            this.modalEx = 0

        },
        doSend() {
            if (this.message.length > 0) {
                alert(this.message)
                this.message = ''
                this.closeModal()
            } else {
                alert('메시지를 입력해주세요.')
            }
        }
    }
}
</script>