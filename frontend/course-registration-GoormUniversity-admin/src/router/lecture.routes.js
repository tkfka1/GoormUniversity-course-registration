import { Layout, List, AddEdit, ClassList , ClassAddEdit } from '@/views/lecture';

export default {
    path: '/lecture',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit },
        { path: 'class/:lid', component: ClassList },
        { path: 'class/:lid/add', component: ClassAddEdit },
        { path: 'class/:lid/edit/:id', component: ClassAddEdit }
    ]
};
