<script setup lang="ts">
import Post from '~/components/post.vue'

const data = [
    {
        id: '1',
        title: 'Title 1',
        text: 'Cillum laborum ea eu adipisicing reprehenderit. Incididunt cupidatat exercitation cillum quis sit duis laborum duis officia id. Adipisicing proident laborum dolore irure. Mollit laboris sint qui quis sint eiusmod sint proident eu ut enim. Ut est culpa cillum id aliqua amet in consectetur officia enim aute aliqua.',
        writer: 'Sergey Holopov'
    },
    {
        id: '2',
        title: 'Title 2',
        text: 'Cillum laborum ea eu adipisicing reprehenderit. Incididunt cupidatat exercitation cillum quis sit duis laborum duis officia id. Adipisicing proident laborum dolore irure. Mollit laboris sint qui quis sint eiusmod sint proident eu ut enim. Ut est culpa cillum id aliqua amet in consectetur officia enim aute aliqua.',
        writer: 'Sergey Holopov'
    },
    {
        id: '3',
        title: 'Title 3',
        text: 'Cillum laborum ea eu adipisicing reprehenderit. Incididunt cupidatat exercitation cillum quis sit duis laborum duis officia id. Adipisicing proident laborum dolore irure. Mollit laboris sint qui quis sint eiusmod sint proident eu ut enim. Ut est culpa cillum id aliqua amet in consectetur officia enim aute aliqua.',
        writer: 'Sergey Holopov'
    },
    {
        id: '4',
        title: 'Title 4',
        text: 'Cillum laborum ea eu adipisicing reprehenderit. Incididunt cupidatat exercitation cillum quis sit duis laborum duis officia id. Adipisicing proident laborum dolore irure. Mollit laboris sint qui quis sint eiusmod sint proident eu ut enim. Ut est culpa cillum id aliqua amet in consectetur officia enim aute aliqua.',
        writer: 'Sergey Holopov'
    },
    {
        id: '5',
        title: 'Title 5',
        text: 'Cillum laborum ea eu adipisicing reprehenderit. Incididunt cupidatat exercitation cillum quis sit duis laborum duis officia id. Adipisicing proident laborum dolore irure. Mollit laboris sint qui quis sint eiusmod sint proident eu ut enim. Ut est culpa cillum id aliqua amet in consectetur officia enim aute aliqua.',
        writer: 'Sergey Holopov'
    },
]
</script>

<template>
    <div class="full:w-1/3 compact:w-2/3 w-full compact:px-0 px-4 py-10 flex flex-col gap-5 items-center">
        <Post 
            v-for="post in data" 
            :key="post.id" 
            :title="post.title" 
            :text="post.text" 
            :writer="post.writer" 
        />
    </div>
</template>