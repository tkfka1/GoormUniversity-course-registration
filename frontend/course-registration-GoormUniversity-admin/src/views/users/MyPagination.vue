<template>
    <div class="my-pagination">
      <nav>
        <ul v-show="props.showPagination" class="list-layout">
          <li v-for="page in props.totalPages" :key="page">
            <button
              v-on="props.pageEvents(page)"
              type="button"
              v-bind="props.aProps"
              :class="props.theme.link"
            >
              {{ page }}
            </button>
          </li>
        </ul>
      </nav>
    </div>
  </template>
  
  <script>
  export default {
    name: "MyPagination",
    props: ["props"],
    setup() {},
  };
  </script>
  
  <style scoped>
  .list-layout {
    display: flex;
    align-items: center;
    justify-content: center;
    list-style-type: none;
  }
  .active > .page-link {
    color: #ffffff;
    background-color: #202020;
  }
  </style>
  