import { Layout, List, AddEdit } from '@/views/professor';

export default {
    path: '/professor',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit }
    ]
};
