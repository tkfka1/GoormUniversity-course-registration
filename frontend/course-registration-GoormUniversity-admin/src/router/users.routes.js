import { Layout, List, AddEdit , TakeList , TakeAddEdit } from '@/views/users';

export default {
    path: '/users',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit },
        { path: 'take/:lid', component: TakeList },
        { path: 'take/:lid/add', component: TakeAddEdit },
        { path: 'take/:lid/edit/:id', component: TakeAddEdit }
    ]
};
