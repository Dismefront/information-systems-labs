import { API_ENDPOINT } from '@/App'; // Adjust with actual API endpoint
import { createEffect, createEvent, createStore, sample } from 'effector';

interface LocationProps {
    x: number;
    y: number;
    z: number;
}

interface LocationPageProps {
    page: number;
    size: number;
}

interface UpdateLocationProps extends LocationProps {
    id: string;
    opened: boolean;
}

const $data = createStore<any>(null);

const fetchFx = createEffect(async ({ page, size }: LocationPageProps) => {
    const data = await fetch(`${API_ENDPOINT}/location/list?page=${page}&size=${size}`, {
        method: 'get',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

const deleteFx = createEffect(async (props: { id: string }) => {
    const data = await fetch(`${API_ENDPOINT}/location/delete/${props.id}`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

const addFx = createEffect(async (props: LocationProps) => {
    const data = await fetch(`${API_ENDPOINT}/location/create`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(props),
    }).then((res) => res.json());
    return data;
});

const updateFx = createEffect(async (props: UpdateLocationProps) => {
    const data = await fetch(`${API_ENDPOINT}/location/update/${props.id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            x: props.x,
            y: props.y,
            z: props.z,
        }),
    }).then((res) => res.json());
    return data;
});

const $error = createStore<string>('');
const setErrorEv = createEvent<string>();

const $createPopupOpen = createStore<boolean>(false);
const setCreatePopupPropsEv = createEvent<boolean>();

$createPopupOpen
    .on(setCreatePopupPropsEv, (_, payload) => payload)
    .on([addFx.doneData, deleteFx.doneData], () => false);

sample({
    clock: addFx.failData,
    fn: () => 'Failed to create location due to invalid data',
    target: setErrorEv,
});

sample({
    clock: fetchFx.doneData,
    target: $data,
});

const $updatePopupOpen = createStore<Partial<UpdateLocationProps>>({ opened: false });
const setUpdatePopupPropsEv = createEvent<Partial<UpdateLocationProps>>();

sample({
    clock: updateFx.failData,
    fn: () => 'Failed to update location due to invalid data',
    target: setErrorEv,
});

$updatePopupOpen
    .on(setUpdatePopupPropsEv, (_, payload) => payload)
    .on(updateFx.doneData, () => ({ opened: false }));

$error.on(setErrorEv, (_, payload) => payload).reset(setCreatePopupPropsEv);

export const locations = {
    fetchFx,
    $data,
    addFx,
    $error,
    $createPopupOpen,
    setCreatePopupPropsEv,
    updateFx,
    setUpdatePopupPropsEv,
    $updatePopupOpen,
    deleteFx,
};
