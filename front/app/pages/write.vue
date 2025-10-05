<template>
  <form @submit="submitHandler" class="full:w-1/3 compact:w-2/3 w-full py-10 compact:px-0 px-5 flex flex-col gap-10">
    <h1 class="text-3xl w-full text-center font-bold">
      Write new post
    </h1>
    <Input 
      v-model="label" 
      max-len="31" 
      placeholder="New title..." 
      required 
    />
    <textarea 
      v-model="text" 
      placeholder="Amet id et duis ea sunt occaecat officia mollit labore. Aliquip nostrud labore laboris pariatur Lorem. Nostrud voluptate excepteur ea excepteur deserunt occaecat commodo."
      class="focus:placeholder:text-transparent duration-200 w-full h-50 rounded-lg outline-1 outline-white/50 text-white/50 focus:outline-white focus:text-white hover:outline-white hover:text-white p-4 text-lg resize-none" 
      required
    ></textarea>
    <Submit>
      {{ error ? 'Error! Something went wrong!' : 'Create post' }}
    </Submit>
  </form>
</template>

<script lang="ts" setup>
import Input from '~/components/input.vue';
import axios from 'axios';
import Submit from '~/components/submit.vue';

const auth = useCookie('auth');

const label = ref();
const text = ref('');
const error = ref<boolean>(false);

const submitHandler = async (e: SubmitEvent) => {
  e.preventDefault();
  try {
    const res = await axios.post('http://localhost/api/v1/posts', {
      label: label.value,
      text: text.value,
    });
    error.value = res.status !== 200 ? true : false
  } catch (err) {
    auth.value = 'false';
    error.value = true;
    navigateTo('/login')
  }
}

</script>