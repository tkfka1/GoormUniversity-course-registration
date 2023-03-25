import { Layout, List, AddEdit } from '@/views/users';

export default {
    path: '/users',
    component: Layout,
    meta: { authorization: ["admin"] },
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit }
    ]
};
