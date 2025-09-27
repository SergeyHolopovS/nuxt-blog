<template>
  <input 
    v-model="model" 
    :placeholder="placeholder" 
    class="focus:placeholder:text-transparent duration-200 w-full h-15 rounded-lg outline-1 outline-white/50 text-white/50 focus:outline-white focus:text-white hover:outline-white hover:text-white px-4 text-xl" 
    :maxlength="maxLen" 
    :required="required" 
  />
</template>

<script lang="ts" setup>
const { placeholder, maxLen, required } = defineProps<{
  placeholder: string,
  maxLen: string,
  required: boolean,
}>();
const model = defineModel();
</script>