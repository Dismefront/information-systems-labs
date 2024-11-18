import { API_ENDPOINT } from '@/App'; // Adjust with actual API endpoint
import { createEffect, createEvent, createStore, sample } from 'effector';

interface AddressProps {
    zipCode: string;
    townId: string;
}

interface AddressPageProps {
    page: number;
    size: number;
}

interface UpdateAddressProps extends AddressProps {
    id: string;
    opened: boolean;
}

const $data = createStore<any>(null);

const fetchFx = createEffect(async ({ page, size }: AddressPageProps) => {
    const data = await fetch(`${API_ENDPOINT}/address/list?page=${page}&size=${size}`, {
        method: 'get',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

const addFx = createEffect(async (props: AddressProps) => {
    const data = await fetch(`${API_ENDPOINT}/address/create`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(props),
    }).then((res) => res.json());
    return data;
});

const updateFx = createEffect(async (props: UpdateAddressProps) => {
    const data = await fetch(`${API_ENDPOINT}/address/update/${props.id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            zipCode: props.zipCode,
            townId: props.townId,
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
    fn: () => 'Failed to create address due to invalid data',
    target: setErrorEv,
});

sample({
    clock: fetchFx.doneData,
    target: $data,
});

const $updatePopupOpen = createStore<Partial<UpdateAddressProps>>({ opened: false });
const setUpdatePopupPropsEv = createEvent<Partial<UpdateAddressProps>>();

sample({
    clock: updateFx.failData,
    fn: () => 'Failed to update address due to invalid data',
    target: setErrorEv,
});

$updatePopupOpen
    .on(setUpdatePopupPropsEv, (_, payload) => payload)
    .on(updateFx.doneData, () => ({ opened: false }));

$error.on(setErrorEv, (_, payload) => payload).reset(setCreatePopupPropsEv);

export const address = {
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
