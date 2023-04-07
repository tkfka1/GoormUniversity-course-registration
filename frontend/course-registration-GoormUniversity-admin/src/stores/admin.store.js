import { defineStore } from 'pinia';

import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';

export const useAdminStore = defineStore({
    id: 'admin',
    state: () => ({
        admin: {},
        user: {}
    }),
    actions: {
        async register(user) {
            await fetchWrapper.post(`/api/admin/auth/signup`, user);
        },
        async getAll() {
            this.admin = { loading: true };
            try {
                this.admin = await fetchWrapper.get(`/api/admin/auth`);  
            } catch (error) {
                this.admin = { error };
            }
        },
        async getById(id) {
            this.user = { loading: true };
            try {
                this.user = await fetchWrapper.get(`/api/admin/auth/${id}`);
            } catch (error) {
                this.user = { error };
            }
        },
        async update(id, params) {
            await fetchWrapper.put(`/api/admin/auth/${id}`, params);

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
            await fetchWrapper.patch(`/api/admin/auth/${id}`, params);

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
            this.admin.find(x => x.id === id).isDeleting = true;

            await fetchWrapper.delete(`/api/admin/auth/${id}`);

            // remove user from list after deleted
            this.admin = this.admin.filter(x => x.id !== id);

            // auto logout if the logged in user deleted their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                authStore.logout();
            }
        }
    }
});
