<script setup lang="ts">
import WelcomeItem from './WelcomeItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'

import { ref } from 'vue';
import axios from 'axios';


const count = ref(0)
const eltest = ref("")
const eltestb = function (){
  alert("얼럿 테스트")
  console.log(eltest.value);
  
}

const asiget = function (){
  axios.get("/api/posts")
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
    get 요청
    <el-button type="primary" @click = "asiget">get</el-button>
    <a href="https://pinia.vuejs.org/" target="_blank" rel="noopener">Pinia</a>,
    <a href="https://router.vuejs.org/" target="_blank" rel="noopener">Vue Router</a>,
    <a href="https://test-utils.vuejs.org/" target="_blank" rel="noopener">Vue Test Utils</a>, and
    <a href="https://github.com/vuejs/devtools" target="_blank" rel="noopener">Vue Dev Tools</a>. If
    you need more resources, we suggest paying
    <a href="https://github.com/vuejs/awesome-vue" target="_blank" rel="noopener">Awesome Vue</a>
    a visit.
  </WelcomeItem>
</template>
