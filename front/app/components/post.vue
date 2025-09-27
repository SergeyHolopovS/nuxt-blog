<script setup lang="ts">

const { title, text, writer } = defineProps<{
  title: string,
  text: string,
  writer: string
}>()

</script>

<template>
  <div class="w-full flex gap-2.5 flex-col rounded-xl outline-1 outline-white/50 hover:outline-white duration-200 p-5">
    <h1 class="text-2xl pl-2">{{ title }}</h1>
    <div class="w-full h-px bg-white/30" />
    <p class="compact:text-lg text-md">{{ text }}</p>
    <div class="w-full h-px bg-white/30" />
    <h1 class="w-full text-end pr-2">{{ writer }}</h1>
  </div>
</template>