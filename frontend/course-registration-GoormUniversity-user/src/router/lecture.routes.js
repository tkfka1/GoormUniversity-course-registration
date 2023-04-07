import { Layout, List, AddEdit } from '@/views/lecture';

export default {
    path: '/lecture',
    component: Layout,
    children: [
        { path: '', component: List }
    ]
};
