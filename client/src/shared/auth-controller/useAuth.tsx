import { useUnit } from 'effector-react';
import Cookies from 'js-cookie';
import { useEffect } from 'react';
import { $isAuthenticated, setAuthenticatedEv } from './auth';

export const useAuth = () => {
    const isAuthenticated = useUnit($isAuthenticated);
    useEffect(() => {
        const sessionCookie = Cookies.get('JSESSIONID');
        const isAuthenticated = Boolean(sessionCookie);
        setAuthenticatedEv(isAuthenticated);
    }, []);

    return isAuthenticated;
};
