<script setup lang="ts">
import axios from 'axios';
import Writer from '~/components/writer.vue';
import general from '~/middleware/general';
import type { WriterDTO } from '~/types/writers';

definePageMeta({
  middleware: general
})

const auth = useCookie('auth');

const writers = ref<WriterDTO[] | undefined>([]);

if(import.meta.client) {
  try {
    const response = await axios.get<WriterDTO[]>("http://localhost/api/v1/writers", {
      method: 'get',
    });
    console.log(response.data, response.status)
    if (response.data === undefined || response.status !== 200) throw Error();
    writers.value = response.data;
  } catch {
    auth.value = 'false';
    navigateTo('/login');
  }
}

</script>

<template>
    <div class="full:w-1/3 compact:w-2/3 w-full compact:px-0 px-4 py-10 flex flex-col gap-5 items-center">
      <Writer 
        v-if="writers !== undefined"
        v-for="writerElement in writers" 
        :username="writerElement.username" 
        :id="writerElement.id"
        :key="writerElement.id" 
      />
      <h1 class="text-2xl" v-if="writers === undefined">Query error</h1>
    </div>
</template>