import { API_ENDPOINT } from '@/App';
import { createEffect, createStore, sample } from 'effector';

const $data = createStore<any[] | null>(null);

const fetchFx = createEffect(async () => {
    const data = await fetch(`${API_ENDPOINT}/import-history/get-all`, {
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

export const importHistory = {
    fetchFx,
    $data,
};
