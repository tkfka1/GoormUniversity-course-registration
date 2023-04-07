<script setup>
import axios from 'axios'
</script>

<template>
  <h1>AI 동물판독기</h1>
  <h6>수강신청 기다리느라 지루하시죠?</h6>
  <h6>이미지를 넣으시면 동물의 종류를 판독해드립니다.</h6>

  <div class="main-container">
    <div class="room-deal-information-container">
    <button v-if="files[0]" class="room-deal-information-title btn btn-primary w-100" @click="startML()">판독 시작</button>
    <div v-else class="room-deal-information-title">사진 등록</div>
      <div class="room-picture-notice">
        <ul v-if="animalcode" class="room-write-wrapper">
          <li>
            <h3>이 동물은 : {{ animalname }} 입니다.</h3>
          </li>
          <!-- <li>{{ animalcode }}</li> -->
      </ul>
      <ul v-else class="room-write-wrapper">
          <li>
            사진은 사이즈가 작은사이즈를 권장합니다. (최대 800px)
          </li>
          <li>사진 용량은 10MB 까지 등록이 가능합니다.</li>
      </ul>
    </div>
      <div class="room-file-upload-wrapper">
        <div v-if="!files.length" class="room-file-upload-example-container">
          <div class="room-file-upload-example">
            <div class="room-file-image-example-wrapper">이미지</div>
            <!-- <div class="room-file-notice-item">
                동물사진을 등록해주세요
              </div> -->
            <div class="room-file-notice-item room-file-notice-item-red">
              동물이 아닌 사진을 선택하시면 판독이 되지 않습니다.
            </div>
            <div class="room-file-notice-item room-file-upload-button">
              <div class="image-box">
                <!-- <div class="image-profile">
                <img :src="profileImage" />
                </div>-->
                <label for="file">파일 선택</label>
                <input type="file" id="file" ref="files1" accept="image/jpg,image/png,image/jpeg,image/gif"
                  @change="imageUpload" />
              </div>
            </div>
          </div>
        </div>
        <div v-else class="file-preview-content-container">
          <div class="file-preview-container">
            <div v-for="(file, index) in files" :key="index" class="file-preview-wrapper">
              <div class="file-close-button" accept="image/jpg,image/png,image/jpeg,image/gif" @click="fileDeleteButton"
                :name="file.number">
                x
              </div>
              <img :src="file.preview" />
            </div>
            <div class="file-preview-wrapper-upload">
              <div class="image-box">
                <label for="file">다른 파일 등록</label>
                <input type="file" id="file" ref="files1" @change="imageUpload" />
              </div>
              <!-- <div class="file-close-button" @click="fileDeleteButton" :name="file.number">x</div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
export default {
  el: ".main-container",
  data() {
    return {
      animalcode: '',
      animalname: '',
      files: [], //업로드용 파일
      filesPreview: [],
      uploadImageIndex: 0 // 이미지 업로드를 위한 변수
    }
  },
  methods: {
    imageUpload() {
      this.files = []
      this.filesPreview = []
      let num = -1;
      for (let i = 0; i < this.$refs.files1.files.length; i++) {
        this.files = [
          ...this.files,
          //이미지 업로드
          {
            //실제 파일
            file: this.$refs.files1.files[0],
            //이미지 프리뷰
            preview: URL.createObjectURL(this.$refs.files1.files[0]),
            //삭제및 관리를 위한 number
            number: 0
          }
        ];
        num = i;
      }
      this.uploadImageIndex = num; //이미지 index의 마지막 값 + 1 저장
      // console.log(this.files);
      // console.log(this.filesPreview);
    },
    fileDeleteButton(e) {
      const name = e.target.getAttribute('name');
      this.files = this.files.filter(data => data.number !== Number(name));
      // console.log(this.files);
    },
    startML() {
      const uploadFile = this.files[0].file;
      const formData = new FormData();
      formData.append( "image" , uploadFile);
      axios ({
        method: 'post',
        url: '/mlapp/mlapp',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        // console.log(res);
        this.animalcode = res.data.class_id
        this.animalname = res.data.class_name
      }).catch(err => {
        this.animalname = "판독 실패"
        // console.log(err);
      })
    }
  }
  };
</script>



<style>
.main-container {
  width: 1000px;
  height: 600px;
  margin: 0 auto;
}

.room-deal-information-container {
  margin-top: 50px;
  color: #222222;
  border: 1px solid #dddddd;
}

.room-deal-information-title {
  text-align: center;
  font-size: 18px;
  line-height: 60px;
  border-bottom: 1px solid #dddddd;
}

.room-deal-information-content-wrapper {
  min-height: 50px;
  display: flex;
}

.room-deal-informtaion-content-title {
  font-size: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 150px;
  background-color: #f9f9f9;
}

.room-deal-information-content {
  width: 100%;
  font-size: 14px;
}

.room-deal-option-selector {
  display: flex;
  align-items: center;
  padding: 15px;
}

.room-deal-option-item {
  width: 100px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #cccccc;
  border-radius: 5px;
  cursor: pointer;
}

.room-deal-option-item:last-child {
  margin-left: 10px;
}

.room-deal-option-notice {
  margin-left: auto;
  font-size: 14px;
  color: #888888;
}

.room-deal-option-item-deposit {
  margin-left: 10px;
}

.room-deal-information-wrapper {
  display: flex;
  flex-direction: column;
}

.room-deal-information-option {
  padding: 10px;
  display: flex;
  align-items: center;
}

.room-deal-information-option:last-child {
  border-bottom: 1px solid #dddddd;
}

.room-deal-information-item-type {
  font-size: 13px;
  color: #fff;
  background-color: #61b6e5;
  min-width: 50px;
  height: 26px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 3px;
}

.room-deal-information-item-wrapper {
  display: flex;
  align-items: center;
  margin-left: 10px;
  height: 46px;
  width: 100%;
}

.room-deal-information-item-wrapper>input {
  border: 1px solid #dddddd;
  width: 140px;
  height: 100%;
  padding: 0 15px;
  font-size: 15px;
}

.room-deal-inforamtion-won {
  margin: 0 10px;
}

.room-deal-information-example {
  color: #888888;
}

.room-deal-information-option:not(:first-child) {
  margin-top: 10px;
}

.room-deal-inforamtion-divide {
  font-size: 22px;
  margin: 0 8px;
  color: #222222;
  font-weight: 100;
}

.room-deal-close-button-wrapper {
  margin-left: auto;
  cursor: pointer;
}

.room-deal-close-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 22px;
  height: 22px;
  background-color: #666666;
  color: rgb(220, 220, 220);
}

.room-deal-cliked {
  background-color: rgb(235, 235, 235);
  color: rgb(170, 170, 170);
}

.room-file-upload-example {
  height: 100%;
}

.room-write-content-container {
  border-top: 1px solid #dddddd;
  min-height: 260px;
}

.room-picture-notice {
  margin: 20px;
  padding: 20px 40px;
  border: 1px solid #dddddd;
}

.file-preview-content-container {
  height: 100%;
}

.room-file-upload-wrapper {
  margin: 20px;
  border: 1px solid #dddddd;
  background-color: #f4f4f4;
  min-height: 350px;
  font-size: 15px;
  color: #888888;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.room-file-upload-example-container {
  display: flex;
  align-items: center;
  justify-content: center;
  /* height: 100%;
  width: 100%; */
}

.room-file-image-example-wrapper {
  text-align: center;
}

.room-file-notice-item {
  margin-top: 5px;
  text-align: center;
}

.room-file-notice-item-red {
  color: #ef4351;
}

.image-box {
  margin-top: 30px;
  padding-bottom: 20px;
  text-align: center;
}

.image-box input[type='file'] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.image-box label {
  display: inline-block;
  padding: 10px 20px;
  background-color: #232d4a;
  color: #fff;
  vertical-align: middle;
  font-size: 15px;
  cursor: pointer;
  border-radius: 5px;
}

.file-preview-wrapper {
  padding: 10px;
  position: relative;
}

/* .file-preview-wrapper>img {
  position: relative;
  width: 190px;
  height: 130px;
  z-index: 10;
} */

.file-preview-wrapper>img {
  position: relative;
  width: 400px;
  height: 300px;
  z-index: 10;
}

.file-close-button {
  position: absolute;
  /* align-items: center; */
  line-height: 18px;
  z-index: 99;
  font-size: 18px;
  right: 5px;
  top: 10px;
  color: #fff;
  font-weight: bold;
  background-color: #666666;
  width: 20px;
  height: 20px;
  text-align: center;
  cursor: pointer;
}

.file-preview-container {
  height: 100%;
  display: flex;
  flex-wrap: wrap;
}

.file-preview-wrapper-upload {
  margin: 10px;
  padding-top: 20px;
  background-color: #888888;
  width: 190px;
  height: 130px;
}

.room-write-button-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #222222;
}

.room-write-button-wrapper>div {
  width: 160px;
  height: 50px;
  border: 1px solid #dddddd;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  cursor: pointer;
}

.room-write-button {
  margin-left: 15px;
  color: #fff;
  background-color: #1564f9;
}

.room-write-button:hover {
  opacity: 0.8;
}
</style>