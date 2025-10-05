const publicPaths = [
    '/',
    '/login',
]

export default defineNuxtRouteMiddleware(async (from, to) => {
    if (import.meta.client) {
        if (publicPaths.includes(to.path)) return;

        const auth = useCookie('auth');

        if (auth.value) return;

        try {
            console.log('Query')
            await $fetch('http://localhost/api/v1/auth/check', {
                method: 'get'
            });
            auth.value = 'true';
        } catch {
            navigateTo('/login')
        }
    }
})