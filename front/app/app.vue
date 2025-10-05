<template>
  <div class="dark">
    <NuxtLayout>
      <NuxtPage />
    </NuxtLayout>
  </div>
</template>

<script lang="ts" setup>
import axios from 'axios';

axios.defaults.baseURL = "http://localhost/api/v1";

</script>