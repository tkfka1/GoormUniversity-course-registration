<script setup>
import { storeToRefs } from 'pinia';
import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';
import { ref } from 'vue';

const authStore = useAuthStore();
const { user } = storeToRefs(authStore);

console.log(user._object.user.id)


const name = ref("이름");
const studentid = ref("학번");
const email = ref("메일");

fetchWrapper.get(`/api/user/${user._object.user.id}`).then((res) => {
    console.log(res);
    name.value = res.name;
    studentid.value = res.studentid;
    email.value = res.email;

});


// const {test} = user._object.user.id




// const test = fetchWrapper.get(`/api/user/${user._object.user.id}`)
// test.then((res) => {

//     email = res.email;
//     studentid = res.studentid;
// });




</script>

<template>
    <div v-if="user">
        <h1>안녕하세요 {{ name }}!</h1>
        <p>구름대학 수강신청에 오신것을 환영합니다.</p>
        <p><router-link to="/users">test1</router-link></p>

        <td>{{ studentid }}</td>
                    <td>{{ name }}</td>
                    <td>{{ email }}</td>

                    <p>dsa</p>    

    </div>

    


</template>
