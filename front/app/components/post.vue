<script setup lang="ts">
import type { PostDTO } from '~/types/posts';


const { label, text, wroteBy } = defineProps<{
  label: string,
  text: string,
  wroteBy: string
}>()

</script>

<template>
  <div class="w-full flex gap-2.5 flex-col rounded-xl outline-1 outline-white/50 hover:outline-white duration-200 p-5">
    <h1 class="text-2xl pl-2">{{ label }}</h1>
    <div class="w-full h-px bg-white/30" />
    <p class="compact:text-lg text-md">{{ text }}</p>
    <div class="w-full h-px bg-white/30" />
    <h1 class="w-full text-end pr-2">{{ wroteBy }}</h1>
  </div>
</template>