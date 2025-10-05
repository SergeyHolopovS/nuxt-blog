<template>
  <form @submit="submitHandler" class="full:w-1/3 compact:w-2/3 min-h-screen justify-center w-full py-10 compact:px-0 px-5 flex flex-col compact:gap-10 gap-5">
    <h1 class="text-3xl w-full text-center font-bold">
      Add new writer
    </h1>
    <Input 
      v-model="username" 
      max-len="31" 
      placeholder="New writer name..." 
      required 
    />
    <Input 
      v-model="password" 
      max-len="31" 
      placeholder="Password..." 
      required 
    />
    <Submit>
      {{ error ? 'Error! Something went wrong!' : 'Create post' }}
    </Submit>
  </form>
</template>

<script lang="ts" setup>
import axios from 'axios';
import general from '~/middleware/general';

definePageMeta({
  middleware: general
})

const username = ref();
const password = ref();
const error = ref<boolean>(false);

const submitHandler = async (e: SubmitEvent) => {
  e.preventDefault();
  try {
    const res = await axios.post("http://localhost/api/v1/writers", {
      username: username.value,
      password: password.value,
    });
    if (res.status !== 201) throw new Error();
    error.value = false;
  } catch {
    error.value = true;
  }
}

</script>