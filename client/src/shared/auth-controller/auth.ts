import { API_ENDPOINT } from '@/App';
import { createEffect, createEvent, createStore, sample } from 'effector';
import Cookies from 'js-cookie';

interface AuthProps {
    username: string;
    password: string;
    passwordRepeat?: string;
}

interface CurrentUser {
    username: string;
    roles: string[];
    id: string;
}

export const $currentUser = createStore<CurrentUser | null>(null);
export const getCurrentUserEv = createEvent();
export const resetCurrentUserEv = createEvent();
export const getCurrentUserFx = createEffect(async (currentUser: CurrentUser | null) => {
    if (currentUser !== null) {
        return currentUser;
    }
    const res = await fetch(`${API_ENDPOINT}/user/get-current`, {
        method: 'get',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include',
    });
    return res.json();
});

$currentUser.on(resetCurrentUserEv, () => null);

sample({
    clock: getCurrentUserEv,
    source: $currentUser,
    target: getCurrentUserFx,
});

sample({
    clock: getCurrentUserFx.doneData,
    target: $currentUser,
});

export const $isAuthenticated = createStore<boolean>(true);
export const setAuthenticatedEv = createEvent<boolean>();

$isAuthenticated.on(setAuthenticatedEv, (_, payload) => payload);

export const loginFx = createEffect(async (props: AuthProps) => {
    const res = await fetch(`${API_ENDPOINT}/login`, {
        body: JSON.stringify(props),
        credentials: 'include',
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    if (res.status === 401) {
        throw new Error('Login or password do not match');
    }
    return res;
});

export const registerFx = createEffect(async (props: AuthProps) => {
    const res = await fetch(`${API_ENDPOINT}/register`, {
        body: JSON.stringify(props),
        credentials: 'include',
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    if (res.status === 409) {
        throw new Error('A user with this username or password already exists');
    }
    return res;
});

export const $authError = createStore<string>('');
$authError
    .on(loginFx.failData, (_, payload) => payload.message)
    .on(registerFx.failData, (_, payload) => payload.message)
    .on(loginFx.doneData, () => '')
    .on(loginFx.doneData, () => '');

export const logoutFx = createEffect(async () => {
    Cookies.set('JSESSIONID', '');
    const res = await fetch(`${API_ENDPOINT}/logout`, {
        credentials: 'include',
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        },
    });
});
