<template>
  <div>
    <order-list
      v-if="paymentInfo !== []"
      v-for="(item, i) in listData"
      :key="i"
      :orderListData="listData"
      :idxData="i"
    />
    <pagination
      :pageSetting="pageDataSetting(total, limit, block, this.page)"
      @paging="pagingMethod"
    />
    <div class="order-none" v-if="paymentInfo === []">거래내역이 없습니다.</div>
  </div>
</template>