<script setup lang="ts">
import Writer from '~/components/writer.vue';


const data = [
    {
      id: '1',
      username: 'Writer 1',
    },
    {
      id: '2',
      username: 'Writer 2',
    },
    {
      id: '3',
      username: 'Writer 3',
    },
    {
      id: '4',
      username: 'Writer 4',
    },
    {
      id: '5',
      username: 'Writer 5',
    },
]
</script>

<template>
    <div class="full:w-1/3 compact:w-2/3 w-full compact:px-0 px-4 py-10 flex flex-col gap-5 items-center">
      <Writer 
        v-for="writerElement in data" 
        :username="writerElement.username" 
        :key="writerElement.id" 
      />
    </div>
</template>