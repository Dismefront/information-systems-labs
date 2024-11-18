import { API_ENDPOINT } from '@/App'; // Adjust with actual API endpoint
import { createEffect, createEvent, createStore, sample } from 'effector';

interface CoordinatesProps {
    x: number;
    y: number;
}

interface CoordinatesPageProps {
    page: number;
    size: number;
}

interface UpdateCoordinatesProps extends CoordinatesProps {
    id: string;
    opened: boolean;
}

const $data = createStore<any>(null);

const fetchFx = createEffect(async ({ page, size }: CoordinatesPageProps) => {
    const data = await fetch(`${API_ENDPOINT}/coordinates/list?page=${page}&size=${size}`, {
        method: 'get',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

const addFx = createEffect(async (props: CoordinatesProps) => {
    const data = await fetch(`${API_ENDPOINT}/coordinates/create`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(props),
    }).then((res) => res.json());
    return data;
});

const updateFx = createEffect(async (props: UpdateCoordinatesProps) => {
    const data = await fetch(`${API_ENDPOINT}/coordinates/update/${props.id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            x: props.x,
            y: props.y,
        }),
    }).then((res) => res.json());
    return data;
});

const $error = createStore<string>('');
const setErrorEv = createEvent<string>();

const $createPopupOpen = createStore<boolean>(false);
const setCreatePopupPropsEv = createEvent<boolean>();

$createPopupOpen.on(setCreatePopupPropsEv, (_, payload) => payload).on(addFx.doneData, () => false);

sample({
    clock: addFx.failData,
    fn: () => 'Failed to create coordinates due to invalid data',
    target: setErrorEv,
});

sample({
    clock: fetchFx.doneData,
    target: $data,
});

const $updatePopupOpen = createStore<Partial<UpdateCoordinatesProps>>({ opened: false });
const setUpdatePopupPropsEv = createEvent<Partial<UpdateCoordinatesProps>>();

sample({
    clock: updateFx.failData,
    fn: () => 'Failed to update coordinates due to invalid data',
    target: setErrorEv,
});

$updatePopupOpen
    .on(setUpdatePopupPropsEv, (_, payload) => payload)
    .on(updateFx.doneData, () => ({ opened: false }));

$error.on(setErrorEv, (_, payload) => payload).reset(setCreatePopupPropsEv);

export const coordinates = {
    fetchFx,
    $data,
    addFx,
    $error,
    $createPopupOpen,
    setCreatePopupPropsEv,
    updateFx,
    setUpdatePopupPropsEv,
    $updatePopupOpen,
};
