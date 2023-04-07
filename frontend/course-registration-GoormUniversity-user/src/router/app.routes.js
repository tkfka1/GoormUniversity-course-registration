import { Layout, App } from '@/views/app';

export default {
    path: '/app',
    component: Layout,
    children: [
        { path: '', component: App }
    ]
};
