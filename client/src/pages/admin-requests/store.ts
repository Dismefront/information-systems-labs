import { API_ENDPOINT } from '@/App';
import { createEffect, createStore, sample } from 'effector';
import toast from 'react-hot-toast';

const $data = createStore<any[] | null>(null);

const fetchFx = createEffect(async () => {
    const data = await fetch(`${API_ENDPOINT}/requests/list`, {
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

const declineFx = createEffect(async ({ id }: any) => {
    const data = await fetch(`${API_ENDPOINT}/requests/decline/${id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((res) => res.text())
        .then((data) => {
            toast.success(data);
        });
    return data;
});

const acceptFx = createEffect(async ({ id }: any) => {
    const data = await fetch(`${API_ENDPOINT}/requests/accept/${id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((res) => res.text())
        .then((data) => {
            toast.success(data);
        });
    return data;
});

export const adminRequests = {
    fetchFx,
    $data,
    declineFx,
    acceptFx,
};
