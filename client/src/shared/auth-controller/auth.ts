import { API_ENDPOINT } from '@/App';
import { createEffect } from 'effector';

interface AuthProps {
    username: string;
    password: string;
}

export const loginFx = createEffect(async (props: AuthProps) => {
    const res = await fetch(`${API_ENDPOINT}/login`, {
        body: JSON.stringify(props),
        credentials: 'include',
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        },
    });
});
