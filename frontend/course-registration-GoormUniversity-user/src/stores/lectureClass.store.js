import { defineStore } from 'pinia';

import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';

export const useLectureClassStore = defineStore({
    id: 'lectureClass',
    state: () => ({
        lectureClass: {},
        user: {}
    }),
    actions: {
        async register(user) {
            await fetchWrapper.post(`/api/lectureClass/auth/signup`, user);
        },
        async getAll() {
            this.lectureClass = { loading: true };
            try {
                this.lectureClass = await fetchWrapper.get(`/api/lectureClass/auth`);  
            } catch (error) {
                this.lectureClass = { error };
            }
        },
        async getById(id) {
            this.user = { loading: true };
            try {
                this.user = await fetchWrapper.get(`/api/lectureClass/auth/${id}`);
            } catch (error) {
                this.user = { error };
            }
        },
        async update(id, params) {
            await fetchWrapper.put(`/api/lectureClass/auth/${id}`, params);

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
            await fetchWrapper.patch(`/api/lectureClass/auth/${id}`, params);

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
            this.lectureClass.find(x => x.id === id).isDeleting = true;

            await fetchWrapper.delete(`/api/lectureClass/auth/${id}`);

            // remove user from list after deleted
            this.lectureClass = this.lectureClass.filter(x => x.id !== id);

            // auto logout if the logged in user deleted their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                authStore.logout();
            }
        }
    }
});
