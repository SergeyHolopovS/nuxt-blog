<template>
  <form @submit="submitHandler"
    class="full:w-1/3 compact:w-2/3 w-full min-h-screen justify-center py-10 compact:px-0 px-5 flex items-center flex-col gap-5">
    <h1 class="text-3xl text-white w-full text-center font-bold">Log in</h1>
    <Input v-model="login" placeholder="Your login..." maxLen="31" required />
    <Input v-model="password" placeholder="Your password..." maxLen="31" required />
    <Submit>
      {{ error ? "Log in error. Try again..." : "Try to log in" }}
    </Submit>
  </form>
</template>

<script lang="ts" setup>
import axios from 'axios';
import Input from '~/components/input.vue';

const login = ref();
const password = ref();
const error = ref<boolean>(false);

const submitHandler = async (e: SubmitEvent) => {
  e.preventDefault();
  try {
    const res = await axios.post('http://localhost:8080/api/v1/auth/login', {
      username: login.value,
      password: password.value,
    });
    if (res.status !== 200) throw new Error();
    error.value = false;
  } catch {
    error.value = true;
  }
}

</script>