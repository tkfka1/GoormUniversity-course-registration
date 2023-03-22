<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';


const email = ref("")
const password = ref("")

// const login = function (){
//   axios.post("/api/auth/login",{
//     email: email.value,
//     password: password.value
//   }).then((response)=>{
//     console.log(response);
//     alert(JSON.stringify(response))
//   }).catch(error => {
//         // handle login error
//   })
// }
const login = function (){
  axios.post("/api/auth/login",{
    email: email.value,
    password: password.value
  }).then((response)=>{
    console.log(response);
    alert(JSON.stringify(response))
  }).catch(error => {
    console.error('[axios.interceptors.response] response : ', error.message);
    alert(JSON.stringify(error))
        // handle login error
  })
}


interface LoginData {
    username: string;
    password: string;
}

const loginData = ref<LoginData>({
    username: '',
    password: '',
});

const loginRules = {
    username: [{ required: true, message: 'Please enter your username', trigger: 'blur' }],
    password: [{ required: true, message: 'Please enter your password', trigger: 'blur' }],
};

const loginError = ref(false);

const login = () => {
    loginError.value = false;
    (loginForm.value as any).validate((valid: boolean) => {
        if (valid) {
            // Perform login logic here
        } else {
            loginError.value = true;
        }
    });
};

</script>

<template>
<div class="custom-container">
        <div>
            <h1>Sign In</h1>
  <el-form>
                <el-form-item label="Username">
                    <el-input v-model="email" placeholder="Enter your email"></el-input>
                </el-form-item>
                <el-form-item label="Password">
                    <el-input v-model="password" placeholder="Enter your password" type="password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login">Login</el-button>
                </el-form-item>
            </el-form>
            <el-alert v-if="loginError" title="Invalid login" type="error"
                description="Please check your credentials and try again." />
          </div>

</div>
  
</template>
