import { API_ENDPOINT } from '@/App';
import { createEffect, createEvent, createStore, sample } from 'effector';

interface SortProps {
    sortBy: string;
    sortDir: 'asc' | 'des';
}

interface PageProps {
    page: number;
    size: number;
}

const $sortProps = createStore<SortProps>({
    sortBy: 'id',
    sortDir: 'des',
});

interface NewProductProps {
    name: string;
    price: string;
    rating: string;
    measureUnit: string;
    number: string;
    ownerId: string;
    manufacturerId: string;
    cost: string;
    locationId: string;
}

interface UpdateProductProps extends NewProductProps {
    id: string;
    opened: boolean;
}

const updSortProps = createEvent<string>();

$sortProps.on(updSortProps, (state, payload) => {
    if (state.sortBy === payload) {
        state.sortDir = state.sortDir === 'asc' ? 'des' : 'asc';
    } else {
        state.sortBy = payload;
        state.sortDir = 'asc';
    }
    return { ...state };
});

const $data = createStore<any>(null);

const fetchFx = createEffect(async ({ page, size, sortBy, sortDir }: PageProps & SortProps) => {
    const data = await fetch(
        `${API_ENDPOINT}/product/list?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`,
        {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        }
    ).then((res) => res.json());
    return data;
});

const addFx = createEffect(async (props: NewProductProps) => {
    const data = await fetch(`${API_ENDPOINT}/product/create`, {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: props.name,
            coordinatesId: props.locationId,
            unitOfMeasure: props.measureUnit,
            manufacturerId: props.manufacturerId,
            price: props.price,
            rating: props.rating,
            partNumber: props.number,
            ownerId: props.ownerId,
            manufactureCost: props.cost,
        }),
    }).then((res) => res.json());
    return data;
});

const updateFx = createEffect(async (props: UpdateProductProps) => {
    const data = await fetch(`${API_ENDPOINT}/product/update/${props.id}`, {
        method: 'put',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: props.name,
            coordinatesId: props.locationId,
            unitOfMeasure: props.measureUnit,
            manufacturerId: props.manufacturerId,
            price: props.price,
            rating: props.rating,
            partNumber: props.number,
            ownerId: props.ownerId,
            manufactureCost: props.cost,
        }),
    }).then((res) => res.json());
    return data;
});

const deleteFx = createEffect(async (props: { id: string }) => {
    const data = await fetch(`${API_ENDPOINT}/product/delete/${props.id}`, {
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
    fn: () => 'Невозможно создать, потому что вы ввели некорректные значения',
    target: setErrorEv,
});

sample({
    clock: fetchFx.doneData,
    target: $data,
});

const $updatePopupOpen = createStore<Partial<UpdateProductProps>>({ opened: false });
const setUpdatePopupPropsEv = createEvent<Partial<UpdateProductProps>>();

sample({
    clock: updateFx.failData,
    fn: () => 'Невозможно обновить, потому что вы ввели некорректные значения',
    target: setErrorEv,
});

$updatePopupOpen
    .on(setUpdatePopupPropsEv, (_, payload) => payload)
    .on([updateFx.doneData], (data) => ({ ...data, opened: false }));

$error
    .on(setErrorEv, (_, payload) => payload)
    .on(addFx.doneData, () => '')
    .on(updateFx.doneData, () => '')
    .reset(setUpdatePopupPropsEv, setCreatePopupPropsEv);

export const products = {
    fetchFx,
    $data,
    addFx,
    $error,
    $createPopupOpen,
    setCreatePopupPropsEv,
    updateFx,
    setUpdatePopupPropsEv,
    $updatePopupOpen,
    $sortProps,
    updSortProps,
    deleteFx,
};
