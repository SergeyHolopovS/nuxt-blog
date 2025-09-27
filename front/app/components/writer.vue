<template>
  <div class="w-full duration-200 outline-1 opacity-50 outline-white text-white h-15 rounded-lg focus:opacity-100 hover:opacity-100 px-5 flex items-center">
    <div class="w-full flex justify-between items-center">
      <h1 class="text-xl">
        {{ username }}
      </h1>
      <button class="size-8 cursor-pointer duration-200 hover:scale-105 focus:scale-105" @click="">
        <img src="~/assets/svg/delete.svg" alt="delete" />
      </button>
    </div>
  </div>
</template>

<script lang="ts" setup>
const { username } = defineProps<{
  id: string,
  username: string,
}>();

const deleteHandler = async () => {
  
}

</script>