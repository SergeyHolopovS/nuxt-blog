<template>
  <form @submit="submitHandler" class="full:w-1/3 compact:w-2/3 w-full py-10 compact:px-0 px-5 flex flex-col gap-10">
    <h1 class="text-3xl w-full text-center font-bold">
      Write new post
    </h1>
    <Input 
      v-model="title" 
      max-len="31" 
      placeholder="New title..." 
      required 
    />
    <Textarea 
      v-model="text" 
      placeholder="Amet id et duis ea sunt occaecat officia mollit labore. Aliquip nostrud labore laboris pariatur Lorem. Nostrud voluptate excepteur ea excepteur deserunt occaecat commodo."
      required
    />
    <Submit>
      {{ error ? 'Error! Something went wrong!' : 'Create post' }}
    </Submit>
  </form>
</template>

<script lang="ts" setup>
import Input from '~/components/input.vue';
import Textarea from '~/components/textarea.vue';
import axios from 'axios';
import Submit from '~/components/submit.vue';

const title = ref();
const text = ref();
const error = ref<boolean>(false);

const submitHandler = async (e: SubmitEvent) => {
  e.preventDefault();
  try {
    const res = await axios.post('http://localhost:8080/api/v1/posts', {
      title: title.value,
      text: text.value,
    });
    error.value = res.status !== 200 ? true : false
  } catch (err) {
    error.value = true;
  }
}

</script>