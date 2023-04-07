<script setup>
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import { useTakeLectureStore, useLectureClassStore, useAlertStore } from '@/stores';
import { fetchWrapper } from '@/helpers';
import { ref } from 'vue';


const takeLectureStore = useTakeLectureStore();
const { take } = storeToRefs(takeLectureStore);
takeLectureStore.getAll();

const lectureClassStore = useLectureClassStore();
const { lectureClass } = storeToRefs(lectureClassStore);
lectureClassStore.getAll();

const alertStore = useAlertStore();

// 현재 강의 id 가져오기 lid
const route = useRoute();
const lid = route.params.lid;

// 내 정보 가져오기
const name = ref("이름");
const studentId = ref("학번");
const grade = ref(1);
const credit = ref(0);
const haveCredit = ref(0);
const majorId = ref(0);
const majorName = ref("");
// 검색 및 필터 초기화
const searchLectureClassName = ref("");
const searchLectureClassCredit = ref("");
const searchLectureClassWeek = ref("");
const searchLectureClassMajor = ref("");

function myinfo() {
    fetchWrapper.get(`/api/user/auth/${lid}`).then((res) => {
        name.value = res.name;
        studentId.value = res.studentId;
        credit.value = res.credit;
        haveCredit.value = res.haveCredit;
        majorId.value = res.major.id;
        majorName.value = res.major.name;
        grade.value = res.grade;
    });
}
myinfo()

function getUseCredit(num) {
    usecredit.value = usecredit.value - num;
    return num;
}

async function takeLectureClass(id, lid, name, cred) {
    var data = new Object();
    var lectureClass = new Object();
    var user = new Object();
    lectureClass.id = String(id);
    user.id = lid;
    data.lectureClass = lectureClass;
    data.user = user;
    data.credit = cred;

    if (credit.value < haveCredit.value + cred) {
        alertStore.error("학점이 부족합니다.");
        return;
    }

    //같은 강의 다른교수


    // 인원꽉참

    try {
        await takeLectureStore.register(data);
        alertStore.success(name + ' 수강 신청 완료');
    } catch (error) {
        if (error == "500") {
            alertStore.error("이미 수강신청된 강의입니다.");
        } else {
            alertStore.error(error);
        }
    }
    takeLectureStore.getAll();
    lectureClassStore.getAll();
    myinfo();
}

async function delLectureClass(id) {
    await takeLectureStore.delete(id)
    myinfo();
    lectureClassStore.getAll();
    alertStore.success('수강 삭제 완료');
}

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



function searchLectureClassBTN() {
    currentPage = 1;
    searchLectureClassName.value = document.getElementById("searchTakeLectureId").value;
    if (searchLectureClassName.value == "") {
        return;
    }
    lectureClassStore.getAll();
}



function changeSelectCredit(event) {
    currentPage = 1;
    searchLectureClassCredit.value = event.target.value;
    lectureClassStore.getAll();
}

function findCredit(credit) {
    if (searchLectureClassCredit.value == "") {
        return true;
    }
    if (credit == searchLectureClassCredit.value) {
        return true;
    } else {
        return false;
    }
}

function changeSelectMajor(event) {
    currentPage = 1;
    if (event.target.value == "1") {
        searchLectureClassMajor.value = majorName.value;
    }
    else if (event.target.value == "2") {
        searchLectureClassMajor.value = "교양";
    } else {
        searchLectureClassMajor.value = "";
    }
    lectureClassStore.getAll();
}

function findMajorName(majorId) {
    if (searchLectureClassMajor.value == "") {
        return true;
    }
    if (majorId == searchLectureClassMajor.value) {
        return true;
    } else {
        return false;
    }
}

function changeSelectWeek(event) {
    currentPage = 1;
    searchLectureClassWeek.value = event.target.value;
    lectureClassStore.getAll();

}

function findWeek(week) {
    if (searchLectureClassWeek.value == "") {
        return true;
    }
    if (week == searchLectureClassWeek.value) {
        return true;
    } else {
        return false;
    }
}

function findKeyWord(name) {
    if (searchLectureClassName.value == "") {
        return true;
    }
    if (name.indexOf(searchLectureClassName.value) != -1) {
        return true;
    } else {
        return false;
    }
}



let itemsPerPage = 10;
let currentPage = 1;


const pageCount = ref(0)

function pageCountCheck(li) {
    // console.log("pageCount")
    pageCount.value = Math.ceil(li.length / itemsPerPage)
}

function nextPage() {
    // console.log("next")
    if (currentPage < pageCount.value) {
        currentPage++
    }
    lectureClassStore.getAll();
}

function prevPage() {
    // console.log("prev")
    if (currentPage > 1) {
        currentPage--
    }
    lectureClassStore.getAll();
}

function changePage(page) {
    // console.log("change")
    currentPage = page
    lectureClassStore.getAll();
}

function listItem(li) {
    let temp = []
    for (const key in li) {
        // console.log(li[key].lecture.name);
        // 강의 이름
        //li[key].lecture.name
        //findCredit(l.lecture.credit) && findWeek(l.week) && findMajorName(l.lecture.major.name) && findKeyWord(l.lecture.name) )
        if (findCredit(li[key].lecture.credit) && findWeek(li[key].week) && findMajorName(li[key].lecture.major.name) && findKeyWord(li[key].lecture.name)) {
            temp.push(li[key])
        }

    }
    pageCountCheck(temp)
    // console.log(temp)

    const startIndex = (currentPage - 1) * itemsPerPage
    const endIndex = startIndex + itemsPerPage
    return temp.slice(startIndex, endIndex)
}



</script>

<template>
    <div class="form-row">
        <h3>{{ name }} ({{ majorName }}) {{ grade }}학년</h3>
    </div>
    <div class="form-row">
        <h3>최대학점 : {{ credit }} 수강학점 : {{ haveCredit }}</h3>
    </div>

    <br>
    <div class="form-row">
        <div class="form-group col">
            <h4 style="">수강 목록</h4>
        </div>
    </div>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>강의이름</th>
                <th>전공</th>
                <th>교수이름</th>
                <th>학점</th>
                <th>요일/교시</th>
                <th style="width: 1%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="take.length">
                <tr v-for="user in take.filter((u) => u.user.id === Number(lid))" :key="user.id">
                    <td>{{ user.lectureClass.lecture.name }}</td>
                    <td>{{ user.lectureClass.lecture.major.name }}</td>
                    <td>{{ user.lectureClass.professor.name }}</td>
                    <td>{{ user.lectureClass.lecture.credit }}</td>
                    <td>{{ makeweek(user.lectureClass.week) }} / {{ user.lectureClass.period }}교시</td>
                    <td style="white-space: nowrap">
                        <button @click="openModal(user.id)" class="btn btn-sm btn-secondary mr-1">강의세부</button>
                        <!-- 컴포넌트 MyModal -->
                        <MyModal @close="closeModal(user.id)" v-if="modalEx == user.id">
                            <!-- default 슬롯 콘텐츠 -->
                            <div class="modal-header">
                                <h5 class="modal-title">{{ user.lectureClass.lecture.name }}</h5>
                            </div>
                            <div class="modal-body">

                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">전공</span>
                                        <span type="text" class="form-control">{{ user.lectureClass.lecture.major.name
                                        }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">교수</span>
                                        <span type="text" class="form-control">{{ user.lectureClass.professor.name }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">학점</span>
                                        <span type="text" class="form-control">{{ user.lectureClass.lecture.credit }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">교시</span>
                                        <span type="text" class="form-control">{{ makeweek(user.lectureClass.week) }} / {{
                                            user.lectureClass.period }}교시</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">최소인원</span>
                                        <span type="text" class="form-control">{{ user.lectureClass.classMin }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">인원</span>
                                        <span type="text" class="form-control">{{ user.lectureClass.classPeople }} / {{
                                            user.lectureClass.classMax }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-12">　　　　　　　　　　　　　　　　　　　　　　　　</div>
                                </div>

                                <p>{{ user.lectureClass.explanation }}</p>
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
                        <button @click="delLectureClass(user.id)" class="btn btn-sm btn-danger mr-1"
                            :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm"></span>
                            <span v-else>수강삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="take.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
                </td>
            </tr>
            <tr v-if="take.error">
                <td colspan="4">
                    <div class="text-danger">Error loading take: {{ take.error }}</div>
                </td>
            </tr>
        </tbody>
    </table>

    <br>
    <div class="form-row">
        <div class="form-group col">
            <h4 style="">수강 가능 목록</h4>
        </div>
        <div class="form-group col">
            <select class="custom-select" @change="changeSelectCredit($event)">
                <option value="" selected>학점</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </div>
        <div class="form-group col">
            <select class="custom-select" @change="changeSelectWeek($event)">
                <option value="" selected>전체요일</option>
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
            <select class="custom-select" @change="changeSelectMajor($event)">
                <option value="" selected>전공/교양</option>
                <option value="1">전공</option>
                <option value="2">교양</option>
            </select>
        </div>
        <div class="form-group col">
            <input type="text" class="form-control" id="searchTakeLectureId" placeholder="강의 이름 검색">
        </div>
        <div class="form-group col-md-1 ">

            <button type="button" class="btn btn-success" @click="searchLectureClassBTN">검색</button>
        </div>
    </div>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>강의명</th>
                <th>전공</th>
                <th>담당교수</th>
                <th>학점</th>
                <th>수강인원</th>
                <th>요일/교시</th>
                <th style="width: 1%"></th>
            </tr>
        </thead>

        <tbody>
            <template v-if="lectureClass.length">
                <tr v-for="item in listItem(lectureClass)" :key="item.id">
                    <td>{{ item.lecture.name }}</td>
                    <td>{{ item.lecture.major.name }}</td>
                    <td>{{ item.professor.name }}</td>
                    <td>{{ item.lecture.credit }}</td>
                    <td>{{ item.classPeople }} / {{ item.classMax }}</td>
                    <td>{{ makeweek(item.week) }} / {{ item.period }}교시</td>
                    <td style="white-space: nowrap">
                        <button @click="openModal(item.id)" class="btn btn-sm btn-secondary mr-1">강의세부</button>
                        <!-- 컴포넌트 MyModal -->
                        <MyModal @close="closeModal(item.id)" v-if="modalEx == item.id">
                            <!-- default 슬롯 콘텐츠 -->
                            <div class="modal-header">
                                <h5 class="modal-title">{{ item.lecture.name }}</h5>
                            </div>
                            <div class="modal-body">

                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">전공</span>
                                        <span type="text" class="form-control">{{ item.lecture.major.name }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">교수</span>
                                        <span type="text" class="form-control">{{ item.professor.name }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">학점</span>
                                        <span type="text" class="form-control">{{ item.lecture.credit }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">교시</span>
                                        <span type="text" class="form-control">{{ makeweek(item.week) }} / {{ item.period
                                        }}교시</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">최소인원</span>
                                        <span type="text" class="form-control">{{ item.classMin }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">인원</span>
                                        <span type="text" class="form-control">{{ item.classPeople }} / {{ item.classMax
                                        }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-12">　　　　　　　　　　　　　　　　　　　　　　　　</div>
                                </div>

                                <p>{{ item.explanation }}</p>
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

                        <button @click="takeLectureClass(item.id, lid, item.lecture.name, item.lecture.credit)"
                            class="btn btn-sm btn-primary mr-1" :disabled="item.isDeleting">
                            <span>수강신청</span>
                        </button>
                    </td>
                </tr>

            </template>
        </tbody>
    </table>
    <div style="display: flex; flex-flow: row nowrap; justify-content: center;">
        <div class="pagination">
            <li class="page-item disabled"><button class="page-link" :disabled="currentPage === 1"
                    @click="prevPage">Prev</button></li>
            <li v-for="page in pageCount" :key="page" class="page-item" :class="{ active: currentPage === page }">
                <button class="page-link" @click="changePage(page)">{{ page }}</button>
            </li>
            <li class="page-item"><button class="page-link" :disabled="currentPage === pageCount"
                    @click="nextPage">Next</button></li>
            </div>
    </div>
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