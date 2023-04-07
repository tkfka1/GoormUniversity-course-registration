import { defineStore } from 'pinia';

import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';

export const useTakeLectureStore = defineStore({
    id: 'take',
    state: () => ({
        take: {},
        user: {}
    }),
    actions: {
        async register(user) {
            await fetchWrapper.post(`/api/take/auth/signup`, user);
        },
        async getAll() {
            this.take = { loading: true };
            try {
                this.take = await fetchWrapper.get(`/api/take/auth`);  
            } catch (error) {
                this.take = { error };
            }
        },
        async getById(id) {
            this.user = { loading: true };
            try {
                this.user = await fetchWrapper.get(`/api/take/auth/${id}`);
            } catch (error) {
                this.user = { error };
            }
        },
        async update(id, params) {
            await fetchWrapper.put(`/api/take/auth/${id}`, params);

            // update stored user if the logged in user updated their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                // update local storage
                const user = { ...authStore.user, ...params };
                localStorage.setItem('user', JSON.stringify(user));

                // update auth user in pinia state
                authStore.user = user;
            }
        },
        async updatePatch(id, params) {
            await fetchWrapper.patch(`/api/take/auth/${id}`, params);

            // update stored user if the logged in user updated their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                // update local storage
                const user = { ...authStore.user, ...params };
                localStorage.setItem('user', JSON.stringify(user));

                // update auth user in pinia state
                authStore.user = user;
            }
        },
        async delete(id) {
            // add isDeleting prop to user being deleted
            this.take.find(x => x.id === id).isDeleting = true;

            await fetchWrapper.delete(`/api/take/auth/${id}`);

            // remove user from list after deleted
            this.take = this.take.filter(x => x.id !== id);

            // auto logout if the logged in user deleted their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                authStore.logout();
            }
        }
    }
});
