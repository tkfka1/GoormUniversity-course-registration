<script setup>
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import { useTakeLectureStore, useCartLectureStore, useAlertStore, useAuthStore } from '@/stores';
import { fetchWrapper } from '@/helpers';
import { ref } from 'vue';
import { number } from 'yup';

// 내 id 가져오기 user._object.user.id
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);

const takeLectureStore = useTakeLectureStore();
const { take } = storeToRefs(takeLectureStore);
takeLectureStore.getAll();

const cartLectureStore = useCartLectureStore();
const { cart } = storeToRefs(cartLectureStore);
cartLectureStore.getAll();

const alertStore = useAlertStore();

// 내 정보 가져오기
const myid = ref(0);
const name = ref("이름");
const studentId = ref("학번");
const credit = ref(0);
const haveCredit = ref(0);
const majorId = ref(0);
const majorName = ref("");
const grade = ref(0);
// 검색 및 필터 초기화
const searchLectureClassName = ref("");
const searchLectureClassCredit = ref("");
const searchLectureClassWeek = ref("");
const searchLectureClassMajor = ref("");
// 시간 초기화
const onMarket = ref(0);
const offMarket = ref(0);
const startTime = ref("");
const endTime = ref("");


function myinfo() {
    fetchWrapper.get(`/api/user/auth/${user._object.user.id}`).then((res) => {
        myid.value = res.id;
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

// time 가져오기
async function timeinfo() {

    var date = new Date();
    await fetchWrapper.get(`/api/time/auth/`).then((res) => {
        if (res.length != 0) {
            for (var i = 0; i < res.length; i++) {
                if (Number(Date.parse(res[i].startTime)) - Number(Date.parse(date)) < 0) {
                    if (Number(Date.parse(res[i].endTime)) - Number(Date.parse(date)) < 0) {
                        return;
                    } else
                        startTime.value = res[i].startTime;
                    endTime.value = res[i].endTime;
                    onMarket.value = Date.parse(res[i].endTime) - Date.parse(date);
                    return;
                } else {
                    onMarket.value = 0;
                    startTime.value = res[0].startTime;
                    endTime.value = res[0].endTime;
                    offMarket.value = Date.parse(res[0].startTime) - Date.parse(date);
                }
            }
        } else {
            onMarket.value = 0;
            startTime.value = "";
            endTime.value = "";
            offMarket.value = 0;
        }
    });
}

timeinfo()

async function takeLectureClass(id, name, cred) {
    var data = new Object();
    var lectureClass = new Object();
    var user = new Object();
    lectureClass.id = String(id);
    user.id = String(myid.value);
    data.lectureClass = lectureClass;
    data.user = user;
    data.credit = cred;

    if (credit.value < haveCredit.value + cred) {
        alertStore.error("학점이 부족합니다.");
        return;
    }


    //같은 강의 다른교수
    var temp = 0;
    await fetchWrapper.get(`/api/take/auth/`).then((res) => {
        for (var i = 0; i < res.length; i++) {
            if (res[i].lectureClass.id == lectureClass.id) {
                alertStore.error("이미 수강신청이 되어있는 강의입니다.");
                temp = 1;
                return;
            }
        }
        for (var i = 0; i < res.length; i++) {
            if (res[i].lectureClass.lecture.name == name) {
                alertStore.error("다른 분반으로 수강신청이 되어있는 강의입니다.");
                temp = 1;
                return;
            }
        }
    });
    if (temp == 1) {
        return;
    }

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
    cartLectureStore.getAll();
    myinfo();
}

async function delLectureClass(id) {
    await takeLectureStore.delete(id)
    myinfo();
    cartLectureStore.getAll();
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
    searchLectureClassName.value = document.getElementById("searchTakeLectureId").value;
    if (searchLectureClassName.value == "") {
        return;
    }
    cartLectureStore.getAll();
}



function changeSelectCredit(event) {
    searchLectureClassCredit.value = event.target.value;
    cartLectureStore.getAll();
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
    if (event.target.value == "1") {
        searchLectureClassMajor.value = majorName.value;
    }
    else if (event.target.value == "2") {
        searchLectureClassMajor.value = "교양";
    } else {
        searchLectureClassMajor.value = "";
    }
    cartLectureStore.getAll();
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
    searchLectureClassWeek.value = event.target.value;
    cartLectureStore.getAll();
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

function makeList() {
    return user._object.user.id;
}


//time
function msToTime(duration) {
    var seconds = parseInt((duration / 1000) % 60)
        , minutes = parseInt((duration / (1000 * 60)) % 60)
        , hours = parseInt((duration / (1000 * 60 * 60)) % 24)
        , days = parseInt((duration / (1000 * 60 * 60 * 24)) % 24);

    days = (days < 10) ? "0" + days : days;
    hours = (hours < 10) ? "0" + hours : hours;
    minutes = (minutes < 10) ? "0" + minutes : minutes;
    seconds = (seconds < 10) ? "0" + seconds : seconds;


    return days + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초";
}


setInterval(function () {
    //  console.log("time")
    // console.log(onMarket.value)
    // console.log(offMarket.value)
    // console.log(startTime.value)
    // console.log(endTime.value)
    // console.log(Date.parse(endTime.value))

    if (offMarket.value == 0) {
        var now = new Date();
        if (endTime.value != "") {
            onMarket.value = Date.parse(endTime.value) - Date.parse(now);
            if (onMarket.value <= 0) {
                console.log("수강신청 끝")
                timeinfo();
            }
        }
    } else {
        var now = new Date();
        offMarket.value = Date.parse(startTime.value) - Date.parse(now);
        if (offMarket.value <= 0) {
            console.log("수강신청 시간")
            timeinfo();
        }
    }
}, 1000)




</script>

<template>
    <div class="form-row">


        <table class="table col-4">
            <thead>
                <tr>
                    <th>이름</th>
                    <th>전공</th>
                    <th>학년</th>
                    <th>학점</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>{{ name }}</td>
                    <td>{{ majorName }}</td>
                    <td>{{ grade }}학년</td>
                    <td>{{ credit }}</td>
                </tr>
            </tbody>

        </table>
        <div v-if="onMarket <= 0" class="col-4">
            <div class="input-group mb-4">
                <div class="input-group-prepend">
                    <div class="input-group-text">수강학점</div>
                </div>
                <div type="text" class="form-control">{{ haveCredit }}</div>
            </div>
            <div class="input-group mb-4">
                <div class="input-group-prepend">
                    <div class="input-group-text">남은시간</div>
                </div>
                <div type="text" class="form-control">{{ msToTime(offMarket) }}</div>
            </div>
        </div>
        <div v-else class="col-4">
            <br>
            <div class="input-group mb-4">
                <h1>　남은 학점 : {{ haveCredit }}</h1>
            </div>
        </div>
        <div v-if="onMarket > 0" class="col-4">
            <div class="input-group mb-4">
                <div class="input-group-prepend">
                    <div class="input-group-text">남은시간</div>
                </div>
                <div type="text" class="form-control">{{ msToTime(onMarket) }}</div>
            </div>
            <div class="input-group mb-4">
                <div class="input-group-prepend">
                    <div class="input-group-text">종료시간</div>
                </div>
                <div type="text" class="form-control">{{ endTime.replace("T", " ").slice(5, 16) }}</div>
            </div>
        </div>
        <div v-else class="col-4">
            <div class="input-group mb-4">
                <div class="input-group-prepend">
                    <div class="input-group-text">오픈시간</div>
                </div>
                <div type="text" class="form-control">{{ startTime.replace("T", " ").slice(5, 16) }}</div>
            </div>
            <div class="input-group mb-4">
                <div class="input-group-prepend">
                    <div class="input-group-text">종료시간</div>
                </div>
                <div type="text" class="form-control">{{ endTime.replace("T", " ").slice(5, 16) }}</div>
            </div>
        </div>
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
                <th>수강인원</th>
                <th>요일/교시</th>
                <th style="width: 1%"></th>
            </tr>
        </thead>
        <tbody>
            <template v-if="take.length">
                <tr v-for="us in take.filter((u) => u.user.id == makeList())" :key="us.id">
                    <td>{{ us.lectureClass.lecture.name }}</td>
                    <td>{{ us.lectureClass.lecture.major.name }}</td>
                    <td>{{ us.lectureClass.professor.name }}</td>
                    <td>{{ us.lectureClass.lecture.credit }}</td>
                    <td>{{ us.lectureClass.classPeople }} / {{ us.lectureClass.classMax }}</td>
                    <td>{{ makeweek(us.lectureClass.week) }} / {{ us.lectureClass.period }}교시</td>
                    <td style="white-space: nowrap">
                        <!-- <router-link :to="`/users/take/${us.id}`" class="btn btn-sm btn-secondary mr-1">강의세부</router-link> -->
                        <button @click="openModal(us.id)" class="btn btn-sm btn-secondary mr-1">강의세부</button>
                        <!-- 컴포넌트 MyModal -->
                        <MyModal @close="closeModal(us.id)" v-if="modalEx == us.id">
                            <!-- default 슬롯 콘텐츠 -->
                            <div class="modal-header">
                                <h5 class="modal-title">{{ us.lectureClass.lecture.name }}</h5>
                            </div>
                            <div class="modal-body">

                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">전공</span>
                                        <span type="text" class="form-control">{{ us.lectureClass.lecture.major.name
                                        }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">교수</span>
                                        <span type="text" class="form-control">{{ us.lectureClass.professor.name }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">학점</span>
                                        <span type="text" class="form-control">{{ us.lectureClass.lecture.credit }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">교시</span>
                                        <span type="text" class="form-control">{{ makeweek(us.lectureClass.week) }} / {{
                                            us.lectureClass.period }}교시</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">최소인원</span>
                                        <span type="text" class="form-control">{{ us.lectureClass.classMin }}</span>
                                    </div>
                                    <div class="input-group col-6">
                                        <span class="input-group-text" id="basic-addon1">인원</span>
                                        <span type="text" class="form-control">{{ us.lectureClass.classPeople }} / {{
                                            us.lectureClass.classMax }}</span>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="input-group col-12">　　　　　　　　　　　　　　　　　　　　　　　　</div>
                                </div>

                                <p>{{ us.lectureClass.explanation }}</p>
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
                        <button @click="delLectureClass(us.id)" class="btn btn-sm btn-danger mr-1"
                            :disabled="user.isDeleting">
                            <span v-if="user.isDeleting" class="spinner-border spinner-border-sm">수강취소</span>
                            <span v-else>수강취소</span>
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
    <br>
    <div class="form-row">
        <div class="form-group col">
            <h4 style="">장바구니</h4>
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
            <template v-if="cart.length">
                <tr v-for="us in cart.filter((c) => findCredit(c.lectureClass.lecture.credit) && findWeek(c.lectureClass.week) && findMajorName(c.lectureClass.lecture.major.name) && findKeyWord(c.lectureClass.lecture.name))"
                    :key="us.id">
                    <!-- <tr v-for="us in cart" :key="us.id" > -->
                    <td>{{ us.lectureClass.lecture.name }}</td>
                    <td>{{ us.lectureClass.lecture.major.name }}</td>
                    <td>{{ us.lectureClass.professor.name }}</td>
                    <td>{{ us.lectureClass.lecture.credit }}</td>
                    <td>{{ us.lectureClass.classPeople }} / {{ us.lectureClass.classMax }}</td>
                    <td>{{ makeweek(us.lectureClass.week) }} / {{ us.lectureClass.period }}교시</td>
                    <td style="white-space: nowrap">
                        <!-- <router-link :to="`/lecture/class/${user._object.user.id}/edit/${user.id}`" class="btn btn-sm btn-secondary mr-1">강의세부</router-link> -->
                        <button
                            v-if="(us.lectureClass.lecture.major.id == majorId.valueOf() || us.lectureClass.lecture.major.name == '교양') && us.lectureClass.classMax != us.lectureClass.classPeople && onMarket > 0"
                            @click="takeLectureClass(us.lectureClass.id, us.lectureClass.lecture.name, us.lectureClass.lecture.credit)"
                            class="btn btn-sm btn-primary mr-1" :disabled="us.isDeleting">
                            <span>수강신청</span>
                        </button>
                        <button v-else class="btn btn-sm btn-secondary mr-1" disabled>신청불가</button>
                        <button @click="cartLectureStore.delete(us.id)" class="btn btn-sm btn-danger btn-delete-user"
                            :disabled="us.isDeleting">
                            <span v-if="cart.isDeleting" class="spinner-border spinner-border-sm">삭제중</span>
                            <span v-else>삭제</span>
                        </button>
                    </td>
                </tr>
            </template>
            <tr v-if="cart.loading">
                <td colspan="4" class="text-center">
                    <span class="spinner-border spinner-border-lg align-center"></span>
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