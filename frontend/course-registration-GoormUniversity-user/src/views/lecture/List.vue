<script setup>
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import { useLectureClassStore, useAlertStore, useAuthStore, useTakeLectureStore, useCartLectureStore } from '@/stores';
import { fetchWrapper } from '@/helpers';
import { ref } from 'vue';
import { number } from 'yup';
import { Modal } from 'usemodal-vue3';

// 내 id 가져오기 user._object.user.id
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);

const takeLectureStore = useTakeLectureStore();
const { takeLecture } = storeToRefs(takeLectureStore);
takeLectureStore.getAll();
const cartLectureStore = useCartLectureStore();
const lectureClassStore = useLectureClassStore();
const { lectureClass } = storeToRefs(lectureClassStore);
lectureClassStore.getAll();

const alertStore = useAlertStore();

// 내 정보 가져오기
const myid = ref(0);
const name = ref("이름");
const studentId = ref("학번");
const credit = ref(0);
const grade = ref(0);
const haveCredit = ref(0);
const majorId = ref(0);
const majorName = ref("");
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
    var temp = 0;
    //같은 강의 다른교수
    await fetchWrapper.get(`/api/take/auth/`).then((res) => {
        console.log(res);
        console.log(name)
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
        alertStore.success(name + ' 수강신청 등록 완료');
    } catch (error) {
        if (error == "500") {
            alertStore.error("이미 수강신청이 되어있는 강의입니다.");
        } else {
            alertStore.error(error);
        }
    }
    lectureClassStore.getAll();
    myinfo();
}

async function cartLectureClass(id, lid, name, cred) {
    var data = new Object();
    var lectureClass = new Object();
    var user = new Object();
    lectureClass.id = String(id);
    user.id = lid;
    data.lectureClass = lectureClass;
    data.user = user;
    data.credit = cred;



    try {
        await cartLectureStore.register(data);
        alertStore.success(name + ' 장바구니 등록 완료');
    } catch (error) {
        if (error == "500") {
            alertStore.error("이미 장바구니에 있는 강의입니다");
        } else {
            alertStore.error(error);
        }
    }
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


// 필터링

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

// 페이지네이션

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

function returnPage(page) {
    // console.log(currentPage,page)
    if (page == currentPage) {
        return true;
    }
    else {
        return false;
    }
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


//time
function msToTime(duration) {
    var seconds = parseInt((duration / 1000) % 60)
        , minutes = parseInt((duration / (1000 * 60)) % 60)
        , hours = parseInt((duration / (1000 * 60 * 60)) % 24);

    hours = (hours < 10) ? "0" + hours : hours;
    minutes = (minutes < 10) ? "0" + minutes : minutes;
    seconds = (seconds < 10) ? "0" + seconds : seconds;

    return hours + "시간" + minutes + "분" + seconds + "초";
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
                        <button @click="cartLectureClass(item.id, myid, item.lecture.name, item.lecture.credit)"
                            class="btn btn-sm btn-info mr-1">
                            <span>장바구니</span>
                        </button>
                        <button
                            v-if="(item.lecture.major.id == majorId.valueOf() || item.lecture.major.name == '교양') && item.classMax != item.classPeople && onMarket > 0"
                            @click="takeLectureClass(item.id, myid, item.lecture.name, item.lecture.credit)"
                            class="btn btn-sm btn-primary mr-1" :disabled="item.isDeleting">
                            <span>수강신청</span>
                        </button>
                        <button v-else class="btn btn-sm btn-secondary mr-1" disabled>신청불가</button>
                    </td>
                </tr>

            </template>
        </tbody>
    </table>
    <template v-if="lectureClass.length">
        <div style="display: flex; flex-flow: row nowrap; justify-content: center;">
            <div class="pagination">
                <li class="page-item"><button class="page-link" :disabled="currentPage === 1"
                        @click="prevPage">Prev</button></li>
                <li v-for="page in pageCount" :key="page" class="page-item" :class="{ active: returnPage(page) }">
                    <button class="page-link" @click="changePage(page)">{{ page }}</button>
                </li>
                <li class="page-item"><button class="page-link" :disabled="currentPage === pageCount"
                        @click="nextPage">Next</button></li>
                </div>
        </div>
    </template>
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