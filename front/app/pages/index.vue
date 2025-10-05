<script setup lang="ts">
import Post from '~/components/post.vue'
import type { PostDTO } from '~/types/posts';

const { data, error } = await useFetch<PostDTO[], unknown>("http://service:8080/api/v1/posts", {
  method: 'get'
})

</script>

<template>
    <div class="full:w-1/3 compact:w-2/3 w-full compact:px-0 px-4 py-10 flex flex-col gap-5 items-center">
        <Post 
            v-if="error === undefined && data && data?.length > 0"
            v-for="post in data" 
            :key="post.id"
            :label="post.label" 
            :text="post.text" 
            :wrote-by="post.wroteBy.username" 
        />
        <h1 class="text-2xl" v-if="error === undefined && data?.length === 0">No posts yet</h1>
        <h1 class="text-2xl" v-if="error">Query error</h1>
    </div>
</template>