import { Layout, Mypage } from '@/views/mypage';

export default {
    path: '/mypage',
    component: Layout,
    children: [
        { path: '', component: Mypage }
    ]
};
