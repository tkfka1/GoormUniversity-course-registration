<script setup lang="ts">
import WelcomeItem from './WelcomeItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'

import { ref } from 'vue';
import axios from 'axios';

const email = ref("")
const password = ref("")



const signup = function (){
  axios.post("/api/auth/signup",{
    email: email.value,
    password: password.value
  }).then((response)=>{
    console.log(response);
    alert(JSON.stringify(response))
  }).catch(error => {
        // handle login error
  })
}


</script>

<template>
  <WelcomeItem>
    <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <el-card>
          <div class="text-center"><h3>회원가입</h3></div>
          <form>
            <div class="form-group">
              <label for="email">이메일:</label>
              <input type="text" class="form-control" id="email" v-model="email">
            </div>
            <div class="form-group">
              <label for="password">비밀번호:</label>
              <input type="password" class="form-control" id="password" v-model="password">
            </div>
            <button type="submit" class="btn btn-primary btn-block" @click.prevent="signup()">회원가입</button>
          </form>
        </el-card>
      </div>
    </div>
  </div>
  </WelcomeItem>
</template>
