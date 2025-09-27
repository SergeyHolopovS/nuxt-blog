<template>
  <textarea 
    :v-model="model" 
    :placeholder="placeholder" 
    class="focus:placeholder:text-transparent duration-200 w-full h-50 rounded-lg outline-1 outline-white/50 text-white/50 focus:outline-white focus:text-white hover:outline-white hover:text-white p-4 text-lg resize-none" 
    required 
  />
</template>

<script lang="ts" setup>
  const { placeholder, required } = defineProps<{
    placeholder: string,
    required: boolean,
  }>();
  const model = defineModel();
</script>

<style>

</style>