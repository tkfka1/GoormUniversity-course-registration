<script setup lang="ts">
import WelcomeItem from './WelcomeItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'

import { ref } from 'vue';
import axios from 'axios';


const count = ref(0)
const eltest = ref("")
const title = ref("")
const content = ref("")

const eltestb = function (){
  alert("얼럿 테스트")
  console.log(eltest.value);
  
}

const asigethello = function (){
  axios.get("/api/gethello")
}

const asiget = function (){
  axios.get("/api/get/1").then((response)=>{
    console.log(response);
    alert(JSON.stringify(response))
  }
  )
}

const asipost = function (){
  axios.post("/api/post",{
    title: title.value,
    content: content.value
  })
}

</script>

<template>
  <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>vue.js3 작동 확인하기</template>

    버튼을 눌러보세요<br>
    <button @click="count += 1" >1증가</button><button @click="count=0">초기화</button>
    <br>
    <p>{{ count }}</p>
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Eliment-Plus 작동 테스트</template>
      <el-input v-model="eltest" type="textarea" rows="2"/>
      <el-button type="primary" @click = "eltestb">완료버튼</el-button>
      <br>
      <p>아래 사진처럼 보여야 함</p>
      <img src="./icons/eltest.png" style="height:100px;" />
      
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Api 테스트</template>

    axios 사용
    <br>
    get 요청 (백엔드 print(helloworld))<br>
    <el-button type="primary" @click = "asigethello">get</el-button><br>
    get 요청 (데이터 베이스 조회 member title, content)<br>
    <el-button type="primary" @click = "asiget">get</el-button><br>
    post 요청 (데이터 베이스에 입력)
    <el-input v-model="title" type="textarea" rows="1"/>
    <el-input v-model="content" type="textarea" rows="1"/>
    <el-button type="primary" @click = "asipost">post</el-button>
  </WelcomeItem>
</template>
