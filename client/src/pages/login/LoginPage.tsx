import { loginFx } from '@/shared/auth-controller/auth';
import { useState } from 'react';
import { Link } from 'react-router-dom';

export const LoginPage: React.FC = () => {
    const [error, setError] = useState<string>();

    const passCheck = (
        errorMsg: string,
        predicate: (v: HTMLInputElement) => boolean,
        ...args: HTMLInputElement[]
    ) => {
        const checkPassed = !args.some(predicate);
        checkPassed ? setError('') : setError(errorMsg);
        return checkPassed;
    };

    const emptinessPredicate = (el: HTMLInputElement) => {
        return el.value.trim() === '';
    };

    const handleSubmtLogin = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const eventTarget = e.target as any;
        passCheck(
            'Заполните все поля',
            emptinessPredicate,
            eventTarget.username,
            eventTarget.password
        ) &&
            loginFx({
                username: eventTarget.username.value,
                password: eventTarget.password.value,
            });
    };

    const handleSubmitRegister = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const eventTarget = e.target as any;
        passCheck(
            'Заполните все поля',
            emptinessPredicate,
            eventTarget.username,
            eventTarget.password,
            eventTarget.passwordRepeat
        ) &&
            passCheck(
                'Пароли не совпадают',
                (v: HTMLInputElement) => {
                    return v.value !== eventTarget.password.value;
                },
                eventTarget.passwordRepeat
            );
    };

    return (
        <>
            <Link style={{ textDecoration: 'none' }} to="/">
                Home page
            </Link>
            <form onSubmit={handleSubmtLogin}>
                <h4>Войдите в аккаунт</h4>
                <input name="username" type="text" placeholder="username" />
                <input name="password" type="password" placeholder="password" />
                <button>Log in</button>
            </form>
            <form onSubmit={handleSubmitRegister}>
                <h4>Зарегестрируйтесь</h4>
                <input name="username" type="text" placeholder="username" />
                <input name="password" type="password" placeholder="password" />
                <input name="passwordRepeat" type="password" placeholder="repeat password" />
                <button>Register</button>
            </form>
            <p style={{ color: 'red' }}>{error}</p>
        </>
    );
};
