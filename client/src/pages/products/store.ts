import { API_ENDPOINT } from '@/App';
import { createEffect, createStore, sample } from 'effector';

interface PageProps {
    page: number;
    size: number;
}

const $data = createStore<any>(null);

const fetchFx = createEffect(async ({ page, size }: PageProps) => {
    const data = await fetch(`${API_ENDPOINT}/product/list?page=${page}&size=${size}`, {
        method: 'get',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

sample({
    clock: fetchFx.doneData,
    target: $data,
});

export const products = {
    fetchFx,
    $data,
};
