import { Layout, List, AddEdit } from '@/views/major';

export default {
    path: '/major',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit }
    ]
};
