import { Layout, List, AddEdit } from '@/views/openTime';

export default {
    path: '/openTime',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit },
    ]
};
