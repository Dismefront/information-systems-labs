import { API_ENDPOINT } from '@/App';
import { createEffect } from 'effector';

const fetchFx = createEffect(async () => {
    const data = await fetch(`${API_ENDPOINT}/alive`, {
        method: 'get',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    console.log(data);
});

export const products = {
    fetchFx,
};
