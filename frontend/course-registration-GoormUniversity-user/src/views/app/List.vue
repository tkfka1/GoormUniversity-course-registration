<script setup>
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import { useTakeLectureStore , useCartLectureStore , useAlertStore , useAuthStore} from '@/stores';
import { fetchWrapper } from '@/helpers';
import { ref } from 'vue';
import { number } from 'yup';

</script>




<template>
    <div class="home">
      <div class="d-flex justify-content-center align-items-center">
        <img src="/img/icons/android-icon-48x48.png" alt="방명록 이미지">
        <h2 class="ml-1 h3">방명록</h2>
      </div>
      <div class="row p-1 mt-4 ml-0 mr-0">
        <input type="text" v-model="context" class="form-control col-9">
        <div class="col-3 p-0">
          <button class="btn btn-primary w-100" @click="writePost">작성</button>
        </div>
      </div>
  
      <ul class="list-group p-1">
        <li v-for="post in posts" v-bind:key="post.id" class="list-group-item">
          {{post.context}}
          <small class="text-danger float-right mt-1" @click="removePost(post)">삭제</small>
        </li>
      </ul>
    </div>
  </template>
  

  
<script>
export default {
  name: 'Home',
  beforeCreate() {
    //방명록 목록 불러오기
    // this.$axios.get(`${this.serverHost}/posts`)
    //   .then( res => {
    //     this.posts = res.data;
    //   })
    //   .catch( err => {
    //     console.error(err);
    //   });
  },
  data() {
    return {
      posts : [],
      context : ''
    }
  },
  methods : {
    writePost() {
      //방명록 등록
      this.$axios.post(`${this.serverHost}/posts`, { context : this.context })
        .then( res => {
          this.posts.push(res.data);
          this.context = '';
        })
        .catch( err => {
          console.error(err);
        });
    },

    removePost(post) {
      this.$axios.delete(`${this.serverHost}/posts/${post.id}`)
        .then( () => {
          this.posts.splice(this.posts.indexOf(post), 1);
        })
        .catch( err => {
          console.error(err);
        })
    }
  }
}
</script>

<style>
  .home {
    padding: 2rem;
    margin: auto;
    border: 2px solid #3d51ba;
    min-width: 320px;
    max-width: 600px;
    height: 100vh;
    overflow: scroll;
  }
</style>