import { defineStore } from 'pinia';

import { fetchWrapper } from '@/helpers';
import { useAuthStore } from '@/stores';

export const useCartLectureStore = defineStore({
    id: 'cart',
    state: () => ({
        cart: {},
        user: {}
    }),
    actions: {
        async register(user) {
            await fetchWrapper.post(`/api/cart/auth/signup`, user);
        },
        async getAll() {
            this.cart = { loading: true };
            try {
                this.cart = await fetchWrapper.get(`/api/cart/auth`);  
            } catch (error) {
                this.cart = { error };
            }
        },
        async getById(id) {
            this.user = { loading: true };
            try {
                this.user = await fetchWrapper.get(`/api/cart/auth/${id}`);
            } catch (error) {
                this.user = { error };
            }
        },
        async update(id, params) {
            await fetchWrapper.put(`/api/cart/auth/${id}`, params);

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
            await fetchWrapper.patch(`/api/cart/auth/${id}`, params);

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
            this.cart.find(x => x.id === id).isDeleting = true;

            await fetchWrapper.delete(`/api/cart/auth/${id}`);

            // remove user from list after deleted
            this.cart = this.cart.filter(x => x.id !== id);

            // auto logout if the logged in user deleted their own record
            const authStore = useAuthStore();
            if (id === authStore.user.id) {
                authStore.logout();
            }
        }
    }
});
