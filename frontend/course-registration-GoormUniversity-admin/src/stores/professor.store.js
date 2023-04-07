import { defineStore } from 'pinia';

import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';

export const useProfessorStore = defineStore({
    id: 'professor',
    state: () => ({
        professor: {},
        user: {}
    }),
    actions: {
        async register(user) {
            await fetchWrapper.post(`/api/professor/auth/signup`, user);
        },
        async getAll() {
            this.professor = { loading: true };
            try {
                this.professor = await fetchWrapper.get(`/api/professor/auth`);  
            } catch (error) {
                this.professor = { error };
            }
        },
        async getById(id) {
            this.user = { loading: true };
            try {
                this.user = await fetchWrapper.get(`/api/professor/auth/${id}`);
            } catch (error) {
                this.user = { error };
            }
        },
        async update(id, params) {
            await fetchWrapper.put(`/api/professor/auth/${id}`, params);

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
            await fetchWrapper.patch(`/api/professor/auth/${id}`, params);

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
            this.professor.find(x => x.id === id).isDeleting = true;

            await fetchWrapper.delete(`/api/professor/auth/${id}`);

            // remove user from list after deleted
            this.professor = this.professor.filter(x => x.id !== id);

            // auto logout if the logged in user deleted their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                authStore.logout();
            }
        }
    }
});
