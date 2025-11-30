import { API_ENDPOINT } from '@/App';
import { createEffect, createEvent, createStore, sample } from 'effector';

interface OrganizationPageProps {
    page: number;
    size: number;
}

interface NewOrganizationProps {
    name: string;
    officialAddressId: string;
    annualTurnover: number;
    employeesCount: number;
    fullName: string;
    postalAddressId: string;
}

interface UpdateOrganizationProps extends NewOrganizationProps {
    id: string;
    opened: boolean;
}

const $data = createStore<any>(null);

const fetchFx = createEffect(async ({ page, size }: OrganizationPageProps) => {
    const data = await fetch(`${API_ENDPOINT}/organization/list?page=${page}&size=${size}`, {
        method: 'get',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

const addFx = createEffect(async (props: NewOrganizationProps) => {
    const data = await fetch(`${API_ENDPOINT}/organization/create`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(props),
    }).then((res) => res.json());
    return data;
});

const updateFx = createEffect(async (props: UpdateOrganizationProps) => {
    const data = await fetch(`${API_ENDPOINT}/organization/update/${props.id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(props),
    }).then((res) => res.json());
    return data;
});

const deleteFx = createEffect(async (props: { id: string }) => {
    const data = await fetch(`${API_ENDPOINT}/organization/delete/${props.id}`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json());
    return data;
});

const $error = createStore<string>('');
const setErrorEv = createEvent();

const $createPopupOpen = createStore<boolean>(false);
const setCreatePopupPropsEv = createEvent<boolean>();

$createPopupOpen
    .on(setCreatePopupPropsEv, (_, payload) => payload)
    .on([addFx.doneData, deleteFx.doneData], () => false);

sample({
    clock: addFx.failData,
    fn: () => 'Failed to create the organization. Please check your inputs.',
    target: setErrorEv,
});

sample({
    clock: fetchFx.doneData,
    target: $data,
});

const $updatePopupOpen = createStore<Partial<UpdateOrganizationProps>>({ opened: false });
const setUpdatePopupPropsEv = createEvent<Partial<UpdateOrganizationProps>>();

sample({
    clock: updateFx.failData,
    fn: () => 'Failed to update the organization. Please check your inputs.',
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

export const organizations = {
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
