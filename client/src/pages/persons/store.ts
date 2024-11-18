import { API_ENDPOINT } from '@/App';
import { createEffect, createEvent, createStore, sample } from 'effector';

interface PageProps {
    page: number;
    size: number;
}

interface NewPersonProps {
    name: string;
    eyeColor: string;
    hairColor: string;
    locationId: string;
    height: string;
    nationality: string;
}

interface UpdatePersonProps extends NewPersonProps {
    id: string;
    opened: boolean;
}

const $data = createStore<any>(null);

const fetchFx = createEffect(async ({ page, size }: PageProps) => {
    const data = await fetch(`${API_ENDPOINT}/person/list?page=${page}&size=${size}`, {
        method: 'get',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

const addFx = createEffect(async (props: NewPersonProps) => {
    const data = await fetch(`${API_ENDPOINT}/person/create`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: props.name,
            locationId: props.locationId,
            eyeColor: props.eyeColor,
            hairColor: props.hairColor,
            height: props.height,
            nationality: props.nationality,
        }),
    }).then((res) => res.json());
    return data;
});

const updateFx = createEffect(async (props: UpdatePersonProps) => {
    const data = await fetch(`${API_ENDPOINT}/person/update/${props.id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: props.name,
            locationId: props.locationId,
            eyeColor: props.eyeColor,
            hairColor: props.hairColor,
            height: props.height,
            nationality: props.nationality,
        }),
    }).then((res) => res.json());
    return data;
});

const $error = createStore<string>('');
const setErrorEv = createEvent();

const $createPopupOpen = createStore<boolean>(false);
const setCreatePopupPropsEv = createEvent<boolean>();

$createPopupOpen.on(setCreatePopupPropsEv, (_, payload) => payload).on(addFx.doneData, () => false);

sample({
    clock: addFx.failData,
    fn: () => 'Невозможно создать, потому что вы ввели некорректные значения',
    target: setErrorEv,
});

sample({
    clock: fetchFx.doneData,
    target: $data,
});

const $updatePopupOpen = createStore<Partial<UpdatePersonProps>>({ opened: false });
const setUpdatePopupPropsEv = createEvent<Partial<UpdatePersonProps>>();

sample({
    clock: updateFx.failData,
    fn: () => 'Невозможно обновить, потому что вы ввели некорректные значения',
    target: setErrorEv,
});

$updatePopupOpen
    .on(setUpdatePopupPropsEv, (_, payload) => payload)
    .on(updateFx.doneData, (data) => ({ ...data, opened: false }));

$error
    .on(setErrorEv, (_, payload) => payload)
    .on(addFx.doneData, () => '')
    .on(updateFx.doneData, () => '')
    .reset(setUpdatePopupPropsEv, setCreatePopupPropsEv);

export const persons = {
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
