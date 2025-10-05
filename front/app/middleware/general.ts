export default defineNuxtRouteMiddleware(async (from, to) => {
    if(import.meta.client) {
        if (to.path !== '/add-writer') return;

        const auth = useCookie('auth');
        console.log(auth.value)
        if (auth.value !== false || auth.value !== undefined) {
            try {
                const response = await $fetch<boolean>("http://localhost/api/v1/auth/check-admin");
                if (!response) navigateTo('/login');
                console.log(response)
            } catch {
                navigateTo('/login');
            }
        } else navigateTo('/login');
    }
})