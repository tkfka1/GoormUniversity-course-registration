import { defineStore } from 'pinia';

import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';

export const useLectureStore = defineStore({
    id: 'lecture',
    state: () => ({
        lecture: {},
        user: {}
    }),
    actions: {
        async register(user) {
            await fetchWrapper.post(`/api/lecture/auth/signup`, user);
        },
        async getAll() {
            this.lecture = { loading: true };
            try {
                this.lecture = await fetchWrapper.get(`/api/lecture/auth`);  
            } catch (error) {
                this.lecture = { error };
            }
        },
        async getById(id) {
            this.user = { loading: true };
            try {
                this.user = await fetchWrapper.get(`/api/lecture/auth/${id}`);
            } catch (error) {
                this.user = { error };
            }
        },
        async update(id, params) {
            await fetchWrapper.put(`/api/lecture/auth/${id}`, params);

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
            await fetchWrapper.patch(`/api/lecture/auth/${id}`, params);

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
            this.lecture.find(x => x.id === id).isDeleting = true;

            await fetchWrapper.delete(`/api/lecture/auth/${id}`);

            // remove user from list after deleted
            this.lecture = this.lecture.filter(x => x.id !== id);

            // auto logout if the logged in user deleted their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                authStore.logout();
            }
        }
    }
});
